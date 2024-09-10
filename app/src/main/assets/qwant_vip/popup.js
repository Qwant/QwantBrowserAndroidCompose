const styles = {
  toggleDisabled: "toggle-disabled",
  toggleEnabled: "toggle-enabled",
  hintProtectionEnabled: "hint-text-protection-enabled",
  hintProtectionDisabled: "hint-text-protection-disabled",
};

const port = chrome.runtime.connect();
const checkboxElement = document.getElementById("toggleProtection");
const toggleElement = document.getElementById("toggle");
let lang = "fr";

if (window.matchMedia?.("(prefers-color-scheme:dark)").matches) {
  document.documentElement.setAttribute("data-theme", "dark");
}

chrome.cookies.get(
  { url: "https://www.qwant.com/", name: "theme" },
  async (cookie) => {
    if (cookie && cookie.value === "1") {
      document.documentElement.setAttribute("data-theme", "dark");
    }
  },
);

document.getElementById("about").textContent = chrome.i18n.getMessage("about");
document.getElementById("comment").textContent =
  chrome.i18n.getMessage("comment");

document
  .getElementById("comment-link")
  .setAttribute("href", chrome.i18n.getMessage("comment_link"));
document
  .getElementById("about-link")
  .setAttribute("href", chrome.i18n.getMessage("about_link"));

port.postMessage({ action: "askTabStatus" });

port.onMessage.addListener(function (msg) {
  console.log({ msg });
  if (msg.action === "tabStatus") {
    const status = document.getElementById("protection-status");
    const hint = document.getElementById("hint");

    if (msg.protectionEnabled) {
      toggleElement.classList.remove([styles.toggleDisabled]);
      toggleElement.classList.add([styles.toggleEnabled]);

      status.classList.add([styles.hintProtectionEnabled]);
      status.classList.remove([styles.hintProtectionDisabled]);

      status.textContent = chrome.i18n.getMessage("protectionEnabled");
      hint.textContent = chrome.i18n.getMessage("hintProtected");
    } else {
      toggleElement.classList.remove([styles.toggleEnabled]);

      toggleElement.classList.add([styles.toggleDisabled]);

      status.classList.add([styles.hintProtectionDisabled]);
      status.classList.remove([styles.hintProtectionEnabled]);

      status.textContent = chrome.i18n.getMessage("protectionDisabled");
      hint.textContent = chrome.i18n.getMessage("hintUnprotected");
    }

    checkboxElement.checked = msg.protectionEnabled;
  }
});

checkboxElement.addEventListener("click", () => {
  port.postMessage({ action: "toggleProtection" });
});
