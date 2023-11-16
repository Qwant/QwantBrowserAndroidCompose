
console.log("background script running");

browser.cookies.onChanged.addListener((changeInfo) => {
   console.log(
     `Cookie changed: \n` +
       ` * Cookie: ${JSON.stringify(changeInfo.cookie)}\n` +
       ` * Cause: ${changeInfo.cause}\n` +
       ` * Removed: ${changeInfo.removed}`,
   );
});

browser.cookies.set({
    url: "https://www.qwant.com",
    name: "omnibar",
    value: "1"
}).then((cookie) => console.log("omnibar cookie set")).catch((err) => console.log("could not set cookie: ", err));

const port = browser.runtime.connectNative("browser");

port.onMessage.addListener(response => {
    // Let's just echo the message back
    console.log("message received: ", JSON.stringify(response));
    let audience = "true"
    if (response["piwikOptout"] === true) {
        audience = "false"
    }
    console.log("audience: ", audience);
    browser.cookies.set({
        url: "https://www.qwant.com",
        name: "audience_statistique",
        value: audience
    });
    // port.postMessage(`Received: ${JSON.stringify(response)}`);
});