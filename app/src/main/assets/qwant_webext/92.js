"use strict";
(self["webpackChunkqwant_viprivacy"] = self["webpackChunkqwant_viprivacy"] || []).push([[92],{

/***/ 1115:
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {


// EXPORTS
__webpack_require__.d(__webpack_exports__, {
  "e": () => (/* binding */ CheckboxCard)
});

// EXTERNAL MODULE: ./node_modules/react/index.js
var react = __webpack_require__(5506);
// EXTERNAL MODULE: ./node_modules/@qwant/qwant-ponents/src/Card/Card.jsx + 3 modules
var Card = __webpack_require__(3809);
// EXTERNAL MODULE: ./node_modules/@qwant/qwant-ponents/src/Text/Text.jsx + 2 modules
var Text = __webpack_require__(3352);
// EXTERNAL MODULE: ./node_modules/@qwant/qwant-ponents/src/Box/Box.js + 1 modules
var Box = __webpack_require__(5845);
// EXTERNAL MODULE: ./node_modules/@qwant/qwant-ponents/src/Stack/Stack.js + 1 modules
var Stack = __webpack_require__(1981);
// EXTERNAL MODULE: ./node_modules/classnames/index.js
var classnames = __webpack_require__(1613);
var classnames_default = /*#__PURE__*/__webpack_require__.n(classnames);
// EXTERNAL MODULE: ./Extension/src/common/translators/reactTranslator.js
var reactTranslator = __webpack_require__(7103);
// EXTERNAL MODULE: ./node_modules/style-loader/dist/runtime/injectStylesIntoStyleTag.js
var injectStylesIntoStyleTag = __webpack_require__(825);
var injectStylesIntoStyleTag_default = /*#__PURE__*/__webpack_require__.n(injectStylesIntoStyleTag);
// EXTERNAL MODULE: ./node_modules/style-loader/dist/runtime/styleDomAPI.js
var styleDomAPI = __webpack_require__(1450);
var styleDomAPI_default = /*#__PURE__*/__webpack_require__.n(styleDomAPI);
// EXTERNAL MODULE: ./node_modules/style-loader/dist/runtime/insertBySelector.js
var insertBySelector = __webpack_require__(9383);
var insertBySelector_default = /*#__PURE__*/__webpack_require__.n(insertBySelector);
// EXTERNAL MODULE: ./node_modules/style-loader/dist/runtime/setAttributesWithoutAttributes.js
var setAttributesWithoutAttributes = __webpack_require__(874);
var setAttributesWithoutAttributes_default = /*#__PURE__*/__webpack_require__.n(setAttributesWithoutAttributes);
// EXTERNAL MODULE: ./node_modules/style-loader/dist/runtime/insertStyleElement.js
var insertStyleElement = __webpack_require__(6395);
var insertStyleElement_default = /*#__PURE__*/__webpack_require__.n(insertStyleElement);
// EXTERNAL MODULE: ./node_modules/style-loader/dist/runtime/styleTagTransform.js
var styleTagTransform = __webpack_require__(3405);
var styleTagTransform_default = /*#__PURE__*/__webpack_require__.n(styleTagTransform);
// EXTERNAL MODULE: ./node_modules/css-loader/dist/cjs.js??ruleSet[1].rules[6].use[1]!./node_modules/postcss-loader/dist/cjs.js!./node_modules/sass-loader/dist/cjs.js!./Extension/src/pages/common/components/CheckboxCard/CheckboxCard.module.scss
var CheckboxCard_module = __webpack_require__(6721);
;// CONCATENATED MODULE: ./Extension/src/pages/common/components/CheckboxCard/CheckboxCard.module.scss

      
      
      
      
      
      
      
      
      

var options = {};

options.styleTagTransform = (styleTagTransform_default());
options.setAttributes = (setAttributesWithoutAttributes_default());

      options.insert = insertBySelector_default().bind(null, "head");
    
options.domAPI = (styleDomAPI_default());
options.insertStyleElement = (insertStyleElement_default());

var update = injectStylesIntoStyleTag_default()(CheckboxCard_module/* default */.Z, options);




       /* harmony default export */ const CheckboxCard_CheckboxCard_module = (CheckboxCard_module/* default */.Z && CheckboxCard_module/* default.locals */.Z.locals ? CheckboxCard_module/* default.locals */.Z.locals : undefined);

;// CONCATENATED MODULE: ./Extension/src/pages/common/components/CheckboxCard/icon-check.svg
var _g;
function _extends() { _extends = Object.assign ? Object.assign.bind() : function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; }; return _extends.apply(this, arguments); }

var SvgIconCheck = function SvgIconCheck(props) {
  return /*#__PURE__*/react.createElement("svg", _extends({
    viewBox: "0 0 32 32",
    xmlns: "http://www.w3.org/2000/svg",
    role: "img"
  }, props), _g || (_g = /*#__PURE__*/react.createElement("g", {
    fill: "none",
    fillRule: "evenodd"
  }, /*#__PURE__*/react.createElement("rect", {
    fill: "#050506",
    width: 32,
    height: 32,
    rx: 16
  }), /*#__PURE__*/react.createElement("path", {
    d: "M4 4h24v24H4z"
  }), /*#__PURE__*/react.createElement("path", {
    d: "M24.28 11.22a.75.75 0 0 1 0 1.06l-8.586 8.586a2.75 2.75 0 0 1-3.889 0L8.22 17.28a.75.75 0 1 1 1.06-1.06l3.586 3.585a1.25 1.25 0 0 0 1.768 0l8.586-8.585a.75.75 0 0 1 1.06 0z",
    fill: "#FFF"
  }))));
};

/* harmony default export */ const icon_check = ("data:image/svg+xml;base64,PHN2ZyB2aWV3Qm94PSIwIDAgMzIgMzIiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CiAgICA8ZyBmaWxsPSJub25lIiBmaWxsLXJ1bGU9ImV2ZW5vZGQiPgogICAgICAgIDxyZWN0IGZpbGw9IiMwNTA1MDYiIHdpZHRoPSIzMiIgaGVpZ2h0PSIzMiIgcng9IjE2Ii8+CiAgICAgICAgPHBhdGggZD0iTTQgNGgyNHYyNEg0eiIvPgogICAgICAgIDxwYXRoIGQ9Ik0yNC4yOCAxMS4yMmEuNzUuNzUgMCAwIDEgMCAxLjA2bC04LjU4NiA4LjU4NmEyLjc1IDIuNzUgMCAwIDEtMy44ODkgMEw4LjIyIDE3LjI4YS43NS43NSAwIDEgMSAxLjA2LTEuMDZsMy41ODYgMy41ODVhMS4yNSAxLjI1IDAgMCAwIDEuNzY4IDBsOC41ODYtOC41ODVhLjc1Ljc1IDAgMCAxIDEuMDYgMHoiIGZpbGw9IiNGRkYiLz4KICAgIDwvZz4KPC9zdmc+Cg==");
;// CONCATENATED MODULE: ./Extension/src/pages/common/components/CheckboxCard/CheckboxCard.jsx






/**
 * @param {string} title
 * @param {string} description
 * @param {React.ReactNode} icon
 * @param {() => void} onClick
 * @param {?boolean} selected
 * @param {?boolean} isNew
 * @param {?boolean} compact
 */

function CheckboxCard({
  title,
  description,
  selected,
  icon,
  isNew,
  onClick,
  compact
}) {
  return /*#__PURE__*/react.createElement(Card/* Card */.Z, {
    hoverableGrey: true,
    as: "button",
    className: classnames_default()(CheckboxCard_CheckboxCard_module.CheckboxCard, compact && CheckboxCard_CheckboxCard_module.CheckboxCardCompact, selected && CheckboxCard_CheckboxCard_module.CheckboxCardActive),
    onClick: onClick
  }, isNew && /*#__PURE__*/react.createElement(Text/* Text */.xv, {
    raw: true,
    typo: "body-2",
    color: "primary",
    bold: true
  }, /*#__PURE__*/react.createElement(Box/* Box */.x, {
    className: CheckboxCard_CheckboxCard_module.CheckboxCardHeader,
    px: "m",
    py: "xs"
  }, (0,reactTranslator.t)('new'))), /*#__PURE__*/react.createElement(Stack/* Stack */.K, {
    className: CheckboxCard_CheckboxCard_module.CheckboxCardBody,
    gap: compact ? 'xxs' : 'xs',
    p: compact ? 's' : 'm',
    relative: true
  }, !compact && /*#__PURE__*/react.createElement(Stack/* Stack */.K, {
    horizontal: true,
    gap: "xxs",
    center: true,
    pb: "xs",
    className: CheckboxCard_CheckboxCard_module.CheckboxCardIcon
  }, icon), /*#__PURE__*/react.createElement(Text/* Text */.xv, {
    typo: compact ? 'body-1' : 'heading-5',
    as: "h2",
    bold: true
  }, title), /*#__PURE__*/react.createElement(Text/* Text */.xv, {
    typo: "body-2",
    as: "p",
    color: "secondary",
    className: CheckboxCard_CheckboxCard_module.Description
  }, description), selected && /*#__PURE__*/react.createElement(SvgIconCheck, {
    className: CheckboxCard_CheckboxCard_module.CheckboxCardActiveIcon
  })));
}

/***/ }),

/***/ 2092:
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "default": () => (__WEBPACK_DEFAULT_EXPORT__)
/* harmony export */ });
/* harmony import */ var react__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(5506);
/* harmony import */ var mobx_react__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(9901);
/* harmony import */ var _src_pages_services_messenger__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(6451);
/* harmony import */ var _src_common_translators_reactTranslator__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(7103);
/* harmony import */ var _qwant_qwant_ponents__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(1981);
/* harmony import */ var _qwant_qwant_ponents__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(5845);
/* harmony import */ var _qwant_qwant_ponents__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(3352);
/* harmony import */ var _src_pages_common_components_CheckboxCard_CheckboxCard__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(1115);






const SettingsView = (0,mobx_react__WEBPACK_IMPORTED_MODULE_4__/* .observer */ .Pi)(({
  store,
  settingsStore
}) => {
  const {
    applicationFilteringDisabled
  } = store;
  const {
    protectionLevel
  } = settingsStore;
  const [activeLevel, setActiveLevel] = (0,react__WEBPACK_IMPORTED_MODULE_0__.useState)(() => {
    if (applicationFilteringDisabled) return 'disabled';
    return protectionLevel;
  });
  (0,react__WEBPACK_IMPORTED_MODULE_0__.useEffect)(() => {
    if (!activeLevel && !!protectionLevel) {
      setActiveLevel(protectionLevel);
    }
  }, [activeLevel, protectionLevel]);

  const onProtectionDisable = async () => {
    await store.changeApplicationFilteringDisabled(!applicationFilteringDisabled);
  };

  const levelChangeHandler = level => async e => {
    var _window, _window$apm;

    e.preventDefault();
    const transaction = (_window = window) === null || _window === void 0 ? void 0 : (_window$apm = _window.apm) === null || _window$apm === void 0 ? void 0 : _window$apm.startTransaction(`protection-button-click-${level}`);
    setActiveLevel(level);

    if (level === 'disabled') {
      await onProtectionDisable();
      return;
    }

    await store.changeApplicationFilteringDisabled(false);
    _src_pages_services_messenger__WEBPACK_IMPORTED_MODULE_1__/* .messenger.changeProtectionLevel */ .d.changeProtectionLevel(level);
    settingsStore.setProtectionLevel(level);

    if (transaction) {
      transaction.result = 'success';
      transaction.end();
    }
  };

  const levels = ['standard', 'strict', 'disabled'];
  return /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0__.createElement(_qwant_qwant_ponents__WEBPACK_IMPORTED_MODULE_5__/* .Stack */ .K, {
    gap: "s"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0__.createElement(_qwant_qwant_ponents__WEBPACK_IMPORTED_MODULE_6__/* .Box */ .x, {
    mb: "l"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0__.createElement(_qwant_qwant_ponents__WEBPACK_IMPORTED_MODULE_7__/* .Text */ .xv, {
    typo: "heading-4",
    bold: true,
    color: "primary",
    as: "h1"
  }, (0,_src_common_translators_reactTranslator__WEBPACK_IMPORTED_MODULE_2__.t)('protection_level'))), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0__.createElement(_qwant_qwant_ponents__WEBPACK_IMPORTED_MODULE_5__/* .Stack */ .K, {
    gap: "xs"
  }, levels.map(level => /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0__.createElement(_src_pages_common_components_CheckboxCard_CheckboxCard__WEBPACK_IMPORTED_MODULE_3__/* .CheckboxCard */ .e, {
    compact: true,
    key: level,
    title: (0,_src_common_translators_reactTranslator__WEBPACK_IMPORTED_MODULE_2__.t)(`protection_level_${level}`),
    description: (0,_src_common_translators_reactTranslator__WEBPACK_IMPORTED_MODULE_2__.t)(`protection_level_${level}_description`),
    onClick: levelChangeHandler(level),
    selected: activeLevel === level
  }))));
});
/* harmony default export */ const __WEBPACK_DEFAULT_EXPORT__ = (SettingsView);

/***/ }),

/***/ 6721:
/***/ ((module, __webpack_exports__, __webpack_require__) => {

/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "Z": () => (__WEBPACK_DEFAULT_EXPORT__)
/* harmony export */ });
/* harmony import */ var _node_modules_css_loader_dist_runtime_sourceMaps_js__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(1091);
/* harmony import */ var _node_modules_css_loader_dist_runtime_sourceMaps_js__WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(_node_modules_css_loader_dist_runtime_sourceMaps_js__WEBPACK_IMPORTED_MODULE_0__);
/* harmony import */ var _node_modules_css_loader_dist_runtime_api_js__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(4090);
/* harmony import */ var _node_modules_css_loader_dist_runtime_api_js__WEBPACK_IMPORTED_MODULE_1___default = /*#__PURE__*/__webpack_require__.n(_node_modules_css_loader_dist_runtime_api_js__WEBPACK_IMPORTED_MODULE_1__);
// Imports


var ___CSS_LOADER_EXPORT___ = _node_modules_css_loader_dist_runtime_api_js__WEBPACK_IMPORTED_MODULE_1___default()((_node_modules_css_loader_dist_runtime_sourceMaps_js__WEBPACK_IMPORTED_MODULE_0___default()));
// Module
___CSS_LOADER_EXPORT___.push([module.id, ".CheckboxCard-module__CheckboxCard___oFtlk{overflow:hidden !important}.CheckboxCard-module__CheckboxCard___oFtlk{text-align:left;border-color:var(--grey-200)}.CheckboxCard-module__CheckboxCardHeader___jghOn{background-color:var(--grey-100)}.CheckboxCard-module__CheckboxCardActiveIcon___Jc24y{position:absolute;top:var(--spacing-m);right:var(--spacing-m);width:32px;height:32px}.CheckboxCard-module__CheckboxCardActive___zlyKH{background-color:var(--green-100) !important;border-color:var(--grey-900) !important}.CheckboxCard-module__CheckboxCardActive___zlyKH .CheckboxCard-module__CheckboxCardHeader___jghOn{background-color:var(--green-300)}.CheckboxCard-module__CheckboxCardActive___zlyKH svg{color:var(--green-300)}.CheckboxCard-module__CheckboxCardIcon___Xw3oW{min-height:32px;color:var(--grey-100)}.CheckboxCard-module__CheckboxCardCompact___qMNP0{border-width:1px !important}.CheckboxCard-module__CheckboxCardCompact___qMNP0 .CheckboxCard-module__CheckboxCardBody___ZU8NU{padding-right:44px}.CheckboxCard-module__CheckboxCardCompact___qMNP0 .CheckboxCard-module__CheckboxCardActiveIcon___Jc24y{width:20px;height:20px;top:var(--spacing-s);right:var(--spacing-s)}.CheckboxCard-module__Description___L914I{min-height:36px}", "",{"version":3,"sources":["webpack://./Extension/src/pages/common/components/CheckboxCard/CheckboxCard.module.scss"],"names":[],"mappings":"AAAA,2CAA2D,0BAA0B,CAArF,2CAAc,eAAe,CAAC,4BAAuD,CAAC,iDAAoB,gCAAgC,CAAC,qDAAwB,iBAAiB,CAAC,oBAAoB,CAAC,sBAAsB,CAAC,UAAU,CAAC,WAAW,CAAC,iDAAoB,4CAA4C,CAAC,uCAAuC,CAAC,kGAAwC,iCAAiC,CAAC,qDAAwB,sBAAsB,CAAC,+CAAkB,eAAe,CAAC,qBAAqB,CAAC,kDAAqB,2BAA2B,CAAC,iGAAuC,kBAAkB,CAAC,uGAA6C,UAAU,CAAC,WAAW,CAAC,oBAAoB,CAAC,sBAAsB,CAAC,0CAAa,eAAe","sourcesContent":[".CheckboxCard{text-align:left;border-color:var(--grey-200);overflow:hidden !important}.CheckboxCardHeader{background-color:var(--grey-100)}.CheckboxCardActiveIcon{position:absolute;top:var(--spacing-m);right:var(--spacing-m);width:32px;height:32px}.CheckboxCardActive{background-color:var(--green-100) !important;border-color:var(--grey-900) !important}.CheckboxCardActive .CheckboxCardHeader{background-color:var(--green-300)}.CheckboxCardActive svg{color:var(--green-300)}.CheckboxCardIcon{min-height:32px;color:var(--grey-100)}.CheckboxCardCompact{border-width:1px !important}.CheckboxCardCompact .CheckboxCardBody{padding-right:44px}.CheckboxCardCompact .CheckboxCardActiveIcon{width:20px;height:20px;top:var(--spacing-s);right:var(--spacing-s)}.Description{min-height:36px}"],"sourceRoot":""}]);
// Exports
___CSS_LOADER_EXPORT___.locals = {
	"CheckboxCard": "CheckboxCard-module__CheckboxCard___oFtlk",
	"CheckboxCardHeader": "CheckboxCard-module__CheckboxCardHeader___jghOn",
	"CheckboxCardActiveIcon": "CheckboxCard-module__CheckboxCardActiveIcon___Jc24y",
	"CheckboxCardActive": "CheckboxCard-module__CheckboxCardActive___zlyKH",
	"CheckboxCardIcon": "CheckboxCard-module__CheckboxCardIcon___Xw3oW",
	"CheckboxCardCompact": "CheckboxCard-module__CheckboxCardCompact___qMNP0",
	"CheckboxCardBody": "CheckboxCard-module__CheckboxCardBody___ZU8NU",
	"Description": "CheckboxCard-module__Description___L914I"
};
/* harmony default export */ const __WEBPACK_DEFAULT_EXPORT__ = (___CSS_LOADER_EXPORT___);


/***/ })

}]);