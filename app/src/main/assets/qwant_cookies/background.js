
var qwantCookies = {};

browser.cookies
  .set({
    url: "https://www.qwant.com",
    domain: ".qwant.com",
    name: "omnibar",
    value: "1"
  })
  .then((cookie) => console.log(`QWANT_COOKIES - omnibar cookie set ${JSON.stringify(cookie)}`))
  .catch((err) => console.log("QWANT_COOKIES - could not set omnibar cookie: ", err));

let nativePort = browser.runtime.connectNative("qwant_browser");
nativePort.onMessage.addListener(message => {
  console.log(`QWANT_COOKIES - message received from native app: ${JSON.stringify(message)}`);
  if (message['code'] == "restore_cookies") {
    restoreQwantCookies();
  }
});
nativePort.postMessage('{code: "connection_established"}');

function saveCookie(cookie) {
  if (cookie.domain != "auth.qwant.com") {
    console.log(`QWANT_COOKIES - save ${JSON.stringify(cookie)}`);
    if (cookie.name == "ory_kratos_session") {
      nativePort.postMessage('{code: "user_logged", value: true}');
      console.log("QWANT_COOKIES - User is logged in !!!!!");
    }
    qwantCookies[cookie.name] = cookie;
  } else {
    console.log(`QWANT_COOKIES - ignoring cookie from auth.qwant.com domain`);
  }
}

function deleteCookie(cookie) {
  console.log(`QWANT_COOKIES - delete ${JSON.stringify(cookie)}`);
  if (cookie.name == "ory_kratos_session") {
    nativePort.postMessage('{code: "user_logged", value: false}');
    console.log("QWANT_COOKIES - User is logged out !!!!!");
  }
  delete qwantCookies[cookie.name];
}

function restoreQwantCookies() {
  try {
    console.log("QWANT_COOKIES - restore all qwant cookies");
    let promises = []
    for (const cookie of Object.values(qwantCookies)) {
      console.log(`QWANT_COOKIES - restoring ${JSON.stringify(cookie)}`);
      promises.push(browser.cookies.set({
        url: "https://www.qwant.com",
        name: cookie.name,
        value: cookie.value,
        domain: cookie.domain,
        expirationDate: cookie.expirationDate,
        firstPartyDomain: cookie.firstPartyDomain,
        httpOnly: cookie.httpOnly,
        partitionKey: cookie.partitionKey,
        path: cookie.path,
        sameSite: cookie.sameSite,
        secure: cookie.secure,
        storeId: cookie.storeId
      }));
    }
    Promise.all(promises)
      .then((cookies) => {
        console.log(`QWANT_COOKIES - restoring all cookies done`);
        nativePort.postMessage('{code: "restored", value: true}');
      })
      .catch((err) => {
          console.error("QWANT_COOKIES - could not restore cookie: ", err);
          nativePort.postMessage('{code: "restored", value: false}');
      });
  } catch(error) {
    console.error("QWANT_COOKIES - could not restore cookies: ", error);
    nativePort.postMessage('{code: "restored", value: false}');
  }
}

browser.cookies
  .getAll({
    domain: ".qwant.com"
  })
  .then((cookies) => {
    for (const cookie of cookies) {
      console.log(`QWANT_COOKIES - retrieving ${JSON.stringify(cookie)}`);
      saveCookie(cookie);
    }
  })
  .catch((err) => console.log("QWANT_COOKIES - could not retrieve qwant cookies: ", err));

browser.cookies.onChanged.addListener((changeInfo) => {
  if (changeInfo.cause == "explicit") {
    console.log(`QWANT_COOKIES - Cookie changed ${JSON.stringify(changeInfo.cookie)}`)
    if (changeInfo.removed) {
      deleteCookie(changeInfo.cookie)
    } else {
      saveCookie(changeInfo.cookie);
    }
  }
});

