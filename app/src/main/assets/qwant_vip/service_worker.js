
const nativePort = browser.runtime.connectNative("qwant_browser");


const storageCache = {
    disabledHost: []
}

const initStorageCache = chrome.storage.local.get().then((items) => {
    console.log("QWANT_VIP - init storage")
    Object.assign(storageCache, items);
    // onMessageReceived({action: "askTabStatus"}, nativePort);
});

// Before navigating on the main frame check either the current host is protected or not
chrome.webNavigation.onBeforeNavigate.addListener(async (details) => {
    try {
        // For firefox and android, use details.frameId !== 0.
        // Could be used also for chrome based browser I guess.
        console.log("QWANT_VIP - onBeforeNavigate");
        await initStorageCache;
        console.log("QWANT_VIP - onBeforeNavigate init storage should be done");
        if (details.frameId !== 0) {
            console.log("QWANT_VIP - onBeforeNavigate returned: frameId=" + details.frameId);
            return
        }

        const host = urlToHostname(details.url);
        const hostDisabledIndex = storageCache.disabledHost.indexOf(host);
        console.log("QWANT_VIP - onBeforeNavigate check " + host + " - " + details.url);

        if (hostDisabledIndex > -1) {
            console.log("QWANT_VIP - onBeforeNavigate disable");
            await disableRuleSets();
        } else {
            console.log("QWANT_VIP - onBeforeNavigate enable");
            await enableRuleSets();
        }
    } catch (err) {
        // Ignore
    }
});

// TODO Probably not needed on android
chrome.runtime.onConnect.addListener(async () => {
    console.log("QWANT_VIP - on connect listener")
    try {
        await initStorageCache;
    } catch (e) {
        console.error(e);
    }
});

// TODO Differ set from firefox & android
const ruleset_ids = [
    
    "adblock_warning_removal",
    
    "fanboy_annoyance",
    
    "easy_privacy_optimized",
    
    "filter_unblocking_search_ads_and_self_promotion",
    
];

function tabInfoToStatus(host, enabled) {
    return {
        action: "tabStatus",
        protectionEnabled: enabled,
        tabUrl: host,
    };
}

// Activate or deactivate the extension whenever the active tab changes
chrome.tabs.onActivated.addListener(async (details) => {
    try {
        console.log("QWANT_VIP - tabs.onActivated");
        await initStorageCache;
        console.log("QWANT_VIP - tabs.onActivated init storage should be done");

        const tab = await chrome.tabs.get(details.tabId);
        if (tab.url !== "about:blank") {
            const host = urlToHostname(tab.url);
            const hostDisabledIndex = storageCache.disabledHost.indexOf(host);

            console.log("QWANT_VIP - tabs.onActivated check " + host + " - " + tab.url);

            if (hostDisabledIndex > -1) {
                console.log("QWANT_VIP - tabs.onActivated disable");
                await disableRuleSets();
            } else {
                console.log("QWANT_VIP - tabs.onActivated enable");
                await enableRuleSets();
            }
        } else {
            console.log("QWANT_VIP - tabs.onActivated tab is about:blank");
        }
    } catch (err) {
        // Ignored
    }
});


nativePort.onMessage.addListener(function (msg) {
    onMessageReceived(msg, nativePort);
});


// Activate or deactivate the extension whenever the popup toggle is clicked,
// This will update the block list in chrome storage and reload the page if needed
function onMessageReceived(msg, port) {
    console.log("QWANT_VIP - onMessageReceived");
    getCurrentTab().then(tab => {
        const host = urlToHostname(tab.url);
        const hostDisabledIndex = storageCache.disabledHost.indexOf(host);

        if (!host) {
            // remove notify for android (as it is done within updateIcon)
            console.log("QWANT_VIP - onMessageReceived no host");
            updateIcon(true);
        } else if (msg.action === 'toggleProtection') {
            console.log("QWANT_VIP - onMessageReceived toggleProtection");
            if (hostDisabledIndex > -1) {
                storageCache.disabledHost.splice(hostDisabledIndex, 1)
                enableRuleSets().then(() => {
                    // remove notify for android (as it is done within updateIcon from enable)
                    
                    chrome.tabs.reload(tab.id);
                });
            } else {
                storageCache.disabledHost.push(host)
                disableRuleSets().then(() => {
                    // remove notify for android (as it is done within updateIcon from disable)
                    
                    chrome.tabs.reload(tab.id);
                });
            }
        } else if (msg.action === 'askTabStatus') {
            console.log("QWANT_VIP - onMessageReceived askTabStatus");
            const enabled = !storageCache.disabledHost.includes(host);
            updateIcon(enabled)
            notifyToggled(tabInfoToStatus(host, enabled), port)
        } else {
          console.error('Unknown action', msg)
        }
    })
}

function notifyToggled(response, port) {
  storageCache.disabledHost = removeDuplicates(storageCache.disabledHost)
  chrome.storage.local.set(storageCache);
  port.postMessage(response);
}

async function disableRuleSets() {
    console.log("QWANT_VIP - disable rulesets");
    await updateIcon(false);
    await chrome.declarativeNetRequest.updateEnabledRulesets({
        disableRulesetIds: ruleset_ids,
        enableRulesetIds: [],
    });
}

async function enableRuleSets() {
    console.log("QWANT_VIP - enable rulesets");
    await updateIcon(true);
    await chrome.declarativeNetRequest.updateEnabledRulesets({
        disableRulesetIds: [],
        enableRulesetIds: ruleset_ids,
    });
}

async function getCurrentTab() {
    const queryOptions = {active: true};
    const [tab] = await chrome.tabs.query(queryOptions);
    return tab;
}

function urlToHostname(rawUrl) {
    if (!rawUrl) {
        return;
    }

    const url = new URL(rawUrl);
    return url.host;
}


function removeDuplicates(arr) {
    return arr.filter((item, index) => arr.indexOf(item) === index);
}

async function updateIcon(enabled) {
    
    notifyToggled({
        action: "tabStatus",
        protectionEnabled: enabled
    }, nativePort);
    
}

// @formatter:off

// @formatter:on
