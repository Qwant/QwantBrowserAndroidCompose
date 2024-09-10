const storageCache = {
    disabledHost: []
}

const initStorageCache = chrome.storage.local.get().then((items) => {
    Object.assign(storageCache, items);
});

chrome.runtime.onConnect.addListener(async () => {
    try {
        await initStorageCache;
    } catch (e) {
        console.error(e);
    }
});

const ruleset_ids = [
    "adblock_warning_removal",
    "easy_privacy",
    "fanboy_annoyance",
    "online_malicious_url_blocklist",
    "filter_unblocking_search_ads_and_self_promotion",
    "adguard_dns_filters",
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
        const tab = await chrome.tabs.get(details.tabId);
        const host = urlToHostname(tab.url);
        const hostDisabledIndex = storageCache.disabledHost.indexOf(host);

        if (hostDisabledIndex > -1) {
            await disableRuleSets();
        } else {
            await enableRuleSets();
        }
    } catch (err) {
        // Ignored
    }
});

// Activate or deactivate the extension whenever the popup toggle is clicked,
// This will update the bloc list in chrome storage and reload the page if needed
chrome.runtime.onConnect.addListener(async (port) => {
    port.onMessage.addListener(function (msg) {
        getCurrentTab().then(tab => {
            const host = urlToHostname(tab.url);
            const hostDisabledIndex = storageCache.disabledHost.indexOf(host);

            if (!host) {
                notifyToggled(tabInfoToStatus(undefined, true), port)
                chrome.action.setIcon({path: "icon.png"});
            } else if (msg.action === 'toggleProtection') {
                if (hostDisabledIndex > -1) {
                    storageCache.disabledHost.splice(hostDisabledIndex, 1)
                    enableRuleSets().then(() => {
                        notifyToggled(tabInfoToStatus(host, true), port)
                        chrome.tabs.reload(tab.id);
                    });
                } else {
                    storageCache.disabledHost.push(host)
                    disableRuleSets().then(() => {
                        notifyToggled(tabInfoToStatus(host, false), port)
                        chrome.tabs.reload(tab.id);
                    });
                }
            } else if (msg.action === 'askTabStatus') {
                const enabled = !storageCache.disabledHost.includes(host);
                if (enabled) {
                    chrome.action.setIcon({path: "icon.png"});
                } else {
                    chrome.action.setIcon({path: "icon-deactivated.png"});
                }
                notifyToggled(tabInfoToStatus(host, enabled), port)
            } else {
              console.error('Unknown action', msg)
            }
        })
    });
});

function notifyToggled(response, port) {
  storageCache.disabledHost = removeDuplicates(storageCache.disabledHost)
  chrome.storage.local.set(storageCache);
  port.postMessage(response);
}

// Before navigating on the main frame check either the current host is protected or not
chrome.webNavigation.onBeforeNavigate.addListener(async (details) => {
    try {
        if (details.frameType !== "outermost_frame") {
            return
        }

        const host = urlToHostname(details.url);
        const hostDisabledIndex = storageCache.disabledHost.indexOf(host);

        if (hostDisabledIndex > -1) {
            await disableRuleSets();
        } else {
            await enableRuleSets();
        }
    } catch (err) {
        // Ignore
    }
});


async function disableRuleSets() {
    await chrome.action.setIcon({path: "icon-deactivated.png"});
    await chrome.declarativeNetRequest.updateEnabledRulesets({
        disableRulesetIds: ruleset_ids,
        enableRulesetIds: [],
    });
}

async function enableRuleSets() {
    await chrome.action.setIcon({path: "icon.png"});
    await chrome.declarativeNetRequest.updateEnabledRulesets({
        disableRulesetIds: [],
        enableRulesetIds: ruleset_ids,
    });
}

async function getCurrentTab() {
    const queryOptions = {active: true, lastFocusedWindow: true};
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

// @formatter:off

// @formatter:on
