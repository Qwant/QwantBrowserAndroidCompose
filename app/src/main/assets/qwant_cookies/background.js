
browser.cookies.set({
    url: "https://www.qwant.com",
    name: "omnibar",
    value: "1"
})
.then((cookie) => console.log("omnibar cookie set"))
.catch((err) => console.log("could not set omnibar cookie: ", err));
