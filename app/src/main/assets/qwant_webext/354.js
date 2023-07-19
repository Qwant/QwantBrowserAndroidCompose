"use strict";
(self["webpackChunkqwant_viprivacy"] = self["webpackChunkqwant_viprivacy"] || []).push([[354],{

/***/ 9354:
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

// ESM COMPAT FLAG
__webpack_require__.r(__webpack_exports__);

// EXPORTS
__webpack_require__.d(__webpack_exports__, {
  "default": () => (/* binding */ GlobalStatsView_GlobalStatsView)
});

// EXTERNAL MODULE: ./node_modules/react/index.js
var react = __webpack_require__(5506);
// EXTERNAL MODULE: ./node_modules/mobx-react/dist/mobxreact.esm.js + 17 modules
var mobxreact_esm = __webpack_require__(9901);
// EXTERNAL MODULE: ./node_modules/@qwant/qwant-ponents/src/Flex/Flex.js + 1 modules
var Flex = __webpack_require__(8478);
// EXTERNAL MODULE: ./node_modules/@qwant/qwant-ponents/src/Stack/Stack.js + 1 modules
var Stack = __webpack_require__(1981);
// EXTERNAL MODULE: ./node_modules/@qwant/qwant-ponents/src/Box/Box.js + 1 modules
var Box = __webpack_require__(5845);
// EXTERNAL MODULE: ./node_modules/@qwant/qwant-ponents/src/Text/Text.jsx + 2 modules
var Text = __webpack_require__(3352);
// EXTERNAL MODULE: ./node_modules/@qwant/qwant-ponents/src/Button/Button.jsx + 2 modules
var Button = __webpack_require__(2735);
// EXTERNAL MODULE: ./node_modules/react-icons/ri/index.esm.js
var index_esm = __webpack_require__(8665);
// EXTERNAL MODULE: ./Extension/src/common/translators/reactTranslator.js
var reactTranslator = __webpack_require__(7103);
;// CONCATENATED MODULE: ./node_modules/react-use/esm/useToggle.js

var toggleReducer = function (state, nextValue) {
    return typeof nextValue === 'boolean' ? nextValue : !state;
};
var useToggle = function (initialValue) {
    return (0,react.useReducer)(toggleReducer, initialValue);
};
/* harmony default export */ const esm_useToggle = (useToggle);

// EXTERNAL MODULE: ./Extension/src/background/utils/browser-utils.js
var browser_utils = __webpack_require__(638);
// EXTERNAL MODULE: ./Extension/src/pages/popup/components/shared/Table/Table.jsx + 1 modules
var Table = __webpack_require__(2208);
// EXTERNAL MODULE: ./Extension/src/pages/popup/components/shared/Tile/Tile.jsx + 1 modules
var Tile = __webpack_require__(3404);
;// CONCATENATED MODULE: ./Extension/src/pages/popup/components/GlobalStatsView/empty-stats.svg
var _defs, _g;
function _extends() { _extends = Object.assign ? Object.assign.bind() : function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; }; return _extends.apply(this, arguments); }

var SvgEmptyStats = function SvgEmptyStats(props) {
  return /*#__PURE__*/React.createElement("svg", _extends({
    width: 336,
    height: 160,
    xmlns: "http://www.w3.org/2000/svg",
    xmlnsXlink: "http://www.w3.org/1999/xlink",
    role: "img"
  }, props), _defs || (_defs = /*#__PURE__*/React.createElement("defs", null, /*#__PURE__*/React.createElement("rect", {
    id: "empty-stats_svg__a",
    x: 0,
    y: 0,
    width: 144,
    height: 144,
    rx: 72
  }), /*#__PURE__*/React.createElement("rect", {
    id: "empty-stats_svg__d",
    x: 0,
    y: 0,
    width: 144,
    height: 56,
    rx: 8
  }), /*#__PURE__*/React.createElement("rect", {
    id: "empty-stats_svg__h",
    x: 0,
    y: 0,
    width: 120,
    height: 56,
    rx: 8
  }), /*#__PURE__*/React.createElement("filter", {
    x: "-2.8%",
    y: "-7.1%",
    width: "108.3%",
    height: "121.4%",
    filterUnits: "objectBoundingBox",
    id: "empty-stats_svg__c"
  }, /*#__PURE__*/React.createElement("feMorphology", {
    radius: 2,
    operator: "dilate",
    "in": "SourceAlpha",
    result: "shadowSpreadOuter1"
  }), /*#__PURE__*/React.createElement("feOffset", {
    dx: 4,
    dy: 4,
    "in": "shadowSpreadOuter1",
    result: "shadowOffsetOuter1"
  }), /*#__PURE__*/React.createElement("feComposite", {
    "in": "shadowOffsetOuter1",
    in2: "SourceAlpha",
    operator: "out",
    result: "shadowOffsetOuter1"
  }), /*#__PURE__*/React.createElement("feColorMatrix", {
    values: "0 0 0 0 0.0196078431 0 0 0 0 0.0196078431 0 0 0 0 0.0235294118 0 0 0 0.24 0",
    "in": "shadowOffsetOuter1"
  })), /*#__PURE__*/React.createElement("filter", {
    x: "-3.3%",
    y: "-7.1%",
    width: "110%",
    height: "121.4%",
    filterUnits: "objectBoundingBox",
    id: "empty-stats_svg__g"
  }, /*#__PURE__*/React.createElement("feMorphology", {
    radius: 2,
    operator: "dilate",
    "in": "SourceAlpha",
    result: "shadowSpreadOuter1"
  }), /*#__PURE__*/React.createElement("feOffset", {
    dx: 4,
    dy: 4,
    "in": "shadowSpreadOuter1",
    result: "shadowOffsetOuter1"
  }), /*#__PURE__*/React.createElement("feComposite", {
    "in": "shadowOffsetOuter1",
    in2: "SourceAlpha",
    operator: "out",
    result: "shadowOffsetOuter1"
  }), /*#__PURE__*/React.createElement("feColorMatrix", {
    values: "0 0 0 0 0.0196078431 0 0 0 0 0.0196078431 0 0 0 0 0.0235294118 0 0 0 0.24 0",
    "in": "shadowOffsetOuter1"
  })), /*#__PURE__*/React.createElement("path", {
    d: "M164 0v148H0V0h164zM90 2C50.235 2 18 34.235 18 74s32.235 72 72 72 72-32.235 72-72S129.765 2 90 2z",
    id: "empty-stats_svg__e"
  }))), _g || (_g = /*#__PURE__*/React.createElement("g", {
    fill: "none",
    fillRule: "evenodd"
  }, /*#__PURE__*/React.createElement("path", {
    d: "M0 0h336v160H0z"
  }), /*#__PURE__*/React.createElement("g", {
    transform: "translate(96 8)"
  }, /*#__PURE__*/React.createElement("mask", {
    id: "empty-stats_svg__b",
    fill: "#fff"
  }, /*#__PURE__*/React.createElement("use", {
    xlinkHref: "#empty-stats_svg__a"
  })), /*#__PURE__*/React.createElement("g", {
    mask: "url(#empty-stats_svg__b)"
  }, /*#__PURE__*/React.createElement("g", {
    transform: "translate(36 72)"
  }, /*#__PURE__*/React.createElement("rect", {
    stroke: "#050506",
    strokeWidth: 2,
    fill: "#DED6FF",
    x: -1,
    y: -1,
    width: 146,
    height: 58,
    rx: 8
  }), /*#__PURE__*/React.createElement("rect", {
    fill: "#AC99FF",
    x: 56,
    y: 16,
    width: 72,
    height: 8,
    rx: 4
  }), /*#__PURE__*/React.createElement("rect", {
    fill: "#AC99FF",
    x: 56,
    y: 32,
    width: 24,
    height: 8,
    rx: 4
  }), /*#__PURE__*/React.createElement("path", {
    d: "M28 42.167c7.824 0 14.167-6.343 14.167-14.167S35.824 13.833 28 13.833 13.833 20.176 13.833 28 20.176 42.167 28 42.167z",
    fill: "#AC99FF"
  }), /*#__PURE__*/React.createElement("path", {
    d: "M8 8h40v40H8z"
  }), /*#__PURE__*/React.createElement("path", {
    d: "M28 11.333c9.205 0 16.667 7.462 16.667 16.667 0 9.205-7.462 16.667-16.667 16.667-9.205 0-16.667-7.462-16.667-16.667 0-9.205 7.462-16.667 16.667-16.667zm0 2.5c-7.824 0-14.167 6.343-14.167 14.167S20.176 42.167 28 42.167 42.167 35.824 42.167 28 35.824 13.833 28 13.833zm0 4.584c.69 0 1.25.56 1.25 1.25v7.643c0 .11.044.216.122.294l3.678 3.679a1.25 1.25 0 1 1-1.767 1.767l-3.679-3.678a2.917 2.917 0 0 1-.854-2.062v-7.643c0-.69.56-1.25 1.25-1.25z",
    fill: "#050506"
  }))), /*#__PURE__*/React.createElement("g", {
    mask: "url(#empty-stats_svg__b)"
  }, /*#__PURE__*/React.createElement("g", {
    transform: "translate(-16 16)"
  }, /*#__PURE__*/React.createElement("use", {
    fill: "#000",
    filter: "url(#empty-stats_svg__c)",
    xlinkHref: "#empty-stats_svg__d"
  }), /*#__PURE__*/React.createElement("rect", {
    stroke: "#050506",
    strokeWidth: 2,
    fill: "#DED6FF",
    x: -1,
    y: -1,
    width: 146,
    height: 58,
    rx: 8
  }), /*#__PURE__*/React.createElement("rect", {
    fill: "#AC99FF",
    x: 56,
    y: 16,
    width: 72,
    height: 8,
    rx: 4
  }), /*#__PURE__*/React.createElement("rect", {
    fill: "#AC99FF",
    x: 56,
    y: 32,
    width: 24,
    height: 8,
    rx: 4
  }), /*#__PURE__*/React.createElement("path", {
    d: "M16.419 14.923A5 5 0 0 0 13 19.667v8.896c0 9.17 9.267 14.736 13.233 16.699a3.961 3.961 0 0 0 3.534 0C33.733 43.299 43 37.732 43 28.562v-8.895a5 5 0 0 0-3.419-4.744l-10-3.333a5 5 0 0 0-3.162 0l-10 3.333z",
    fill: "#AC99FF"
  }), /*#__PURE__*/React.createElement("path", {
    d: "M8 8h40v40H8z"
  }), /*#__PURE__*/React.createElement("path", {
    d: "M26.419 11.59a5 5 0 0 1 3.162 0l10 3.333A5 5 0 0 1 43 19.667v8.896c0 9.17-9.267 14.736-13.233 16.699a3.961 3.961 0 0 1-3.534 0C22.267 43.299 13 37.732 13 28.562v-8.895a5 5 0 0 1 3.419-4.744zm.79 2.372-10 3.333a2.5 2.5 0 0 0-1.709 2.372v8.896c0 3.678 1.847 6.761 4.388 9.275 2.544 2.518 5.587 4.26 7.454 5.183a1.46 1.46 0 0 0 1.316 0c1.867-.924 4.91-2.665 7.454-5.183 2.54-2.514 4.388-5.597 4.388-9.275v-8.896a2.5 2.5 0 0 0-1.71-2.372l-10-3.333a2.5 2.5 0 0 0-1.58 0zm6.675 10.384a1.25 1.25 0 0 1 0 1.767l-5.724 5.724a2.583 2.583 0 0 1-3.653 0l-2.39-2.39a1.25 1.25 0 1 1 1.767-1.768l2.39 2.39a.084.084 0 0 0 .118 0l5.724-5.723a1.25 1.25 0 0 1 1.768 0z",
    fill: "#050506"
  })))), /*#__PURE__*/React.createElement("path", {
    d: "M96 80c0 39.765 32.235 72 72 72s72-32.235 72-72-32.235-72-72-72c-16.495 0-31.694 5.547-43.834 14.877",
    stroke: "#050506",
    strokeWidth: 2
  }), /*#__PURE__*/React.createElement("g", {
    transform: "translate(78 6)"
  }, /*#__PURE__*/React.createElement("mask", {
    id: "empty-stats_svg__f",
    fill: "#fff"
  }, /*#__PURE__*/React.createElement("use", {
    xlinkHref: "#empty-stats_svg__e"
  })), /*#__PURE__*/React.createElement("g", {
    mask: "url(#empty-stats_svg__f)"
  }, /*#__PURE__*/React.createElement("g", {
    transform: "translate(2 18)"
  }, /*#__PURE__*/React.createElement("use", {
    fill: "#000",
    filter: "url(#empty-stats_svg__g)",
    xlinkHref: "#empty-stats_svg__h"
  }), /*#__PURE__*/React.createElement("rect", {
    stroke: "#050506",
    strokeWidth: 2,
    fill: "#DED6FF",
    x: -1,
    y: -1,
    width: 122,
    height: 58,
    rx: 8
  })), /*#__PURE__*/React.createElement("g", {
    fill: "#C8CBD0",
    transform: "translate(44 34)"
  }, /*#__PURE__*/React.createElement("rect", {
    width: 66,
    height: 8,
    rx: 4
  }), /*#__PURE__*/React.createElement("rect", {
    y: 16,
    width: 10,
    height: 8,
    rx: 4
  })), /*#__PURE__*/React.createElement("path", {
    d: "M18.419 32.923A5 5 0 0 0 15 37.667v8.896c0 9.17 9.267 14.736 13.233 16.699a3.961 3.961 0 0 0 3.534 0C35.733 61.299 45 55.732 45 46.562v-8.895a5 5 0 0 0-3.419-4.744l-10-3.333a5 5 0 0 0-3.162 0l-10 3.333z",
    fill: "#AC99FF"
  }), /*#__PURE__*/React.createElement("path", {
    d: "M10 26h40v40H10z"
  }), /*#__PURE__*/React.createElement("path", {
    d: "M28.419 29.59a5 5 0 0 1 3.162 0l10 3.333A5 5 0 0 1 45 37.667v8.896c0 9.17-9.267 14.736-13.233 16.699a3.961 3.961 0 0 1-3.534 0C24.267 61.299 15 55.732 15 46.562v-8.895a5 5 0 0 1 3.419-4.744zm.79 2.372-10 3.333a2.5 2.5 0 0 0-1.709 2.372v8.896c0 3.678 1.847 6.761 4.388 9.275 2.544 2.518 5.587 4.26 7.454 5.183a1.46 1.46 0 0 0 1.316 0c1.867-.924 4.91-2.665 7.454-5.183 2.54-2.514 4.388-5.597 4.388-9.275v-8.896a2.5 2.5 0 0 0-1.71-2.372l-10-3.333a2.5 2.5 0 0 0-1.58 0zm6.675 10.384a1.25 1.25 0 0 1 0 1.767l-5.724 5.724a2.583 2.583 0 0 1-3.653 0l-2.39-2.39a1.25 1.25 0 1 1 1.767-1.768l2.39 2.39a.084.084 0 0 0 .118 0l5.724-5.723a1.25 1.25 0 0 1 1.768 0z",
    fill: "#050506"
  }))))));
};

/* harmony default export */ const empty_stats = ("/empty-stats.3942a85d.svg");
;// CONCATENATED MODULE: ./Extension/src/pages/popup/components/GlobalStatsView/disabled-stats.svg
var disabled_stats_defs, disabled_stats_g;
function disabled_stats_extends() { disabled_stats_extends = Object.assign ? Object.assign.bind() : function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; }; return disabled_stats_extends.apply(this, arguments); }

var SvgDisabledStats = function SvgDisabledStats(props) {
  return /*#__PURE__*/React.createElement("svg", disabled_stats_extends({
    width: 336,
    height: 160,
    xmlns: "http://www.w3.org/2000/svg",
    xmlnsXlink: "http://www.w3.org/1999/xlink",
    role: "img"
  }, props), disabled_stats_defs || (disabled_stats_defs = /*#__PURE__*/React.createElement("defs", null, /*#__PURE__*/React.createElement("rect", {
    id: "disabled-stats_svg__a",
    x: 0,
    y: 0,
    width: 144,
    height: 144,
    rx: 72
  }), /*#__PURE__*/React.createElement("rect", {
    id: "disabled-stats_svg__d",
    x: 0,
    y: 0,
    width: 144,
    height: 56,
    rx: 8
  }), /*#__PURE__*/React.createElement("rect", {
    id: "disabled-stats_svg__h",
    x: 0,
    y: 0,
    width: 120,
    height: 56,
    rx: 8
  }), /*#__PURE__*/React.createElement("filter", {
    x: "-2.8%",
    y: "-7.1%",
    width: "108.3%",
    height: "121.4%",
    filterUnits: "objectBoundingBox",
    id: "disabled-stats_svg__c"
  }, /*#__PURE__*/React.createElement("feMorphology", {
    radius: 2,
    operator: "dilate",
    "in": "SourceAlpha",
    result: "shadowSpreadOuter1"
  }), /*#__PURE__*/React.createElement("feOffset", {
    dx: 4,
    dy: 4,
    "in": "shadowSpreadOuter1",
    result: "shadowOffsetOuter1"
  }), /*#__PURE__*/React.createElement("feComposite", {
    "in": "shadowOffsetOuter1",
    in2: "SourceAlpha",
    operator: "out",
    result: "shadowOffsetOuter1"
  }), /*#__PURE__*/React.createElement("feColorMatrix", {
    values: "0 0 0 0 0.0196078431 0 0 0 0 0.0196078431 0 0 0 0 0.0235294118 0 0 0 0.24 0",
    "in": "shadowOffsetOuter1"
  })), /*#__PURE__*/React.createElement("filter", {
    x: "-3.3%",
    y: "-7.1%",
    width: "110%",
    height: "121.4%",
    filterUnits: "objectBoundingBox",
    id: "disabled-stats_svg__g"
  }, /*#__PURE__*/React.createElement("feMorphology", {
    radius: 2,
    operator: "dilate",
    "in": "SourceAlpha",
    result: "shadowSpreadOuter1"
  }), /*#__PURE__*/React.createElement("feOffset", {
    dx: 4,
    dy: 4,
    "in": "shadowSpreadOuter1",
    result: "shadowOffsetOuter1"
  }), /*#__PURE__*/React.createElement("feComposite", {
    "in": "shadowOffsetOuter1",
    in2: "SourceAlpha",
    operator: "out",
    result: "shadowOffsetOuter1"
  }), /*#__PURE__*/React.createElement("feColorMatrix", {
    values: "0 0 0 0 0.0196078431 0 0 0 0 0.0196078431 0 0 0 0 0.0235294118 0 0 0 0.24 0",
    "in": "shadowOffsetOuter1"
  })), /*#__PURE__*/React.createElement("path", {
    d: "M164 0v148H0V0h164zM90 2C50.235 2 18 34.235 18 74s32.235 72 72 72 72-32.235 72-72S129.765 2 90 2z",
    id: "disabled-stats_svg__e"
  }))), disabled_stats_g || (disabled_stats_g = /*#__PURE__*/React.createElement("g", {
    fill: "none",
    fillRule: "evenodd"
  }, /*#__PURE__*/React.createElement("path", {
    d: "M0 0h336v160H0z"
  }), /*#__PURE__*/React.createElement("g", {
    transform: "translate(96 8)"
  }, /*#__PURE__*/React.createElement("mask", {
    id: "disabled-stats_svg__b",
    fill: "#fff"
  }, /*#__PURE__*/React.createElement("use", {
    xlinkHref: "#disabled-stats_svg__a"
  })), /*#__PURE__*/React.createElement("g", {
    mask: "url(#disabled-stats_svg__b)"
  }, /*#__PURE__*/React.createElement("g", {
    transform: "translate(36 72)"
  }, /*#__PURE__*/React.createElement("rect", {
    stroke: "#050506",
    strokeWidth: 2,
    fill: "#E9EAEC",
    x: -1,
    y: -1,
    width: 146,
    height: 58,
    rx: 8
  }), /*#__PURE__*/React.createElement("rect", {
    fill: "#C8CBD0",
    x: 56,
    y: 16,
    width: 72,
    height: 8,
    rx: 4
  }), /*#__PURE__*/React.createElement("rect", {
    fill: "#C8CBD0",
    x: 56,
    y: 32,
    width: 24,
    height: 8,
    rx: 4
  }), /*#__PURE__*/React.createElement("path", {
    d: "M28 42.167c7.824 0 14.167-6.343 14.167-14.167S35.824 13.833 28 13.833 13.833 20.176 13.833 28 20.176 42.167 28 42.167z",
    fill: "#C8CBD0"
  }), /*#__PURE__*/React.createElement("path", {
    d: "M8 8h40v40H8z"
  }), /*#__PURE__*/React.createElement("path", {
    d: "M28 11.333c9.205 0 16.667 7.462 16.667 16.667 0 9.205-7.462 16.667-16.667 16.667-9.205 0-16.667-7.462-16.667-16.667 0-9.205 7.462-16.667 16.667-16.667zm0 2.5c-7.824 0-14.167 6.343-14.167 14.167S20.176 42.167 28 42.167 42.167 35.824 42.167 28 35.824 13.833 28 13.833zm0 4.584c.69 0 1.25.56 1.25 1.25v7.643c0 .11.044.216.122.294l3.678 3.679a1.25 1.25 0 1 1-1.767 1.767l-3.679-3.678a2.917 2.917 0 0 1-.854-2.062v-7.643c0-.69.56-1.25 1.25-1.25z",
    fill: "#050506"
  }))), /*#__PURE__*/React.createElement("g", {
    mask: "url(#disabled-stats_svg__b)"
  }, /*#__PURE__*/React.createElement("g", {
    transform: "translate(-16 16)"
  }, /*#__PURE__*/React.createElement("use", {
    fill: "#000",
    filter: "url(#disabled-stats_svg__c)",
    xlinkHref: "#disabled-stats_svg__d"
  }), /*#__PURE__*/React.createElement("rect", {
    stroke: "#050506",
    strokeWidth: 2,
    fill: "#E9EAEC",
    x: -1,
    y: -1,
    width: 146,
    height: 58,
    rx: 8
  }), /*#__PURE__*/React.createElement("rect", {
    fill: "#C8CBD0",
    x: 56,
    y: 16,
    width: 72,
    height: 8,
    rx: 4
  }), /*#__PURE__*/React.createElement("rect", {
    fill: "#C8CBD0",
    x: 56,
    y: 32,
    width: 24,
    height: 8,
    rx: 4
  }), /*#__PURE__*/React.createElement("path", {
    d: "M16.419 14.923A5 5 0 0 0 13 19.667v8.896c0 9.17 9.267 14.736 13.233 16.699a3.961 3.961 0 0 0 3.534 0C33.733 43.299 43 37.732 43 28.562v-8.895a5 5 0 0 0-3.419-4.744l-10-3.333a5 5 0 0 0-3.162 0l-10 3.333z",
    fill: "#C8CBD0"
  }), /*#__PURE__*/React.createElement("path", {
    d: "M8 8h40v40H8z"
  }), /*#__PURE__*/React.createElement("path", {
    d: "M26.419 11.59a5 5 0 0 1 3.162 0l10 3.333A5 5 0 0 1 43 19.667v8.896c0 9.17-9.267 14.736-13.233 16.699a3.961 3.961 0 0 1-3.534 0C22.267 43.299 13 37.732 13 28.562v-8.895a5 5 0 0 1 3.419-4.744zm.79 2.372-10 3.333a2.5 2.5 0 0 0-1.709 2.372v8.896c0 3.678 1.847 6.761 4.388 9.275 2.544 2.518 5.587 4.26 7.454 5.183a1.46 1.46 0 0 0 1.316 0c1.867-.924 4.91-2.665 7.454-5.183 2.54-2.514 4.388-5.597 4.388-9.275v-8.896a2.5 2.5 0 0 0-1.71-2.372l-10-3.333a2.5 2.5 0 0 0-1.58 0zm6.675 10.384a1.25 1.25 0 0 1 0 1.767l-5.724 5.724a2.583 2.583 0 0 1-3.653 0l-2.39-2.39a1.25 1.25 0 1 1 1.767-1.768l2.39 2.39a.084.084 0 0 0 .118 0l5.724-5.723a1.25 1.25 0 0 1 1.768 0z",
    fill: "#050506"
  })))), /*#__PURE__*/React.createElement("path", {
    d: "M96 80c0 39.765 32.235 72 72 72s72-32.235 72-72-32.235-72-72-72c-16.495 0-31.694 5.547-43.834 14.877",
    stroke: "#050506",
    strokeWidth: 2
  }), /*#__PURE__*/React.createElement("g", {
    transform: "translate(78 6)"
  }, /*#__PURE__*/React.createElement("mask", {
    id: "disabled-stats_svg__f",
    fill: "#fff"
  }, /*#__PURE__*/React.createElement("use", {
    xlinkHref: "#disabled-stats_svg__e"
  })), /*#__PURE__*/React.createElement("g", {
    mask: "url(#disabled-stats_svg__f)"
  }, /*#__PURE__*/React.createElement("g", {
    transform: "translate(2 18)"
  }, /*#__PURE__*/React.createElement("use", {
    fill: "#000",
    filter: "url(#disabled-stats_svg__g)",
    xlinkHref: "#disabled-stats_svg__h"
  }), /*#__PURE__*/React.createElement("rect", {
    stroke: "#050506",
    strokeWidth: 2,
    fill: "#E9EAEC",
    x: -1,
    y: -1,
    width: 122,
    height: 58,
    rx: 8
  })), /*#__PURE__*/React.createElement("g", {
    fill: "#C8CBD0",
    transform: "translate(44 34)"
  }, /*#__PURE__*/React.createElement("rect", {
    width: 66,
    height: 8,
    rx: 4
  }), /*#__PURE__*/React.createElement("rect", {
    y: 16,
    width: 10,
    height: 8,
    rx: 4
  })), /*#__PURE__*/React.createElement("path", {
    d: "M18.419 32.923A5 5 0 0 0 15 37.667v8.896c0 9.17 9.267 14.736 13.233 16.699a3.961 3.961 0 0 0 3.534 0C35.733 61.299 45 55.732 45 46.562v-8.895a5 5 0 0 0-3.419-4.744l-10-3.333a5 5 0 0 0-3.162 0l-10 3.333z",
    fill: "#C8CBD0"
  }), /*#__PURE__*/React.createElement("path", {
    d: "M10 26h40v40H10z"
  }), /*#__PURE__*/React.createElement("path", {
    d: "M28.419 29.59a5 5 0 0 1 3.162 0l10 3.333A5 5 0 0 1 45 37.667v8.896c0 9.17-9.267 14.736-13.233 16.699a3.961 3.961 0 0 1-3.534 0C24.267 61.299 15 55.732 15 46.562v-8.895a5 5 0 0 1 3.419-4.744zm.79 2.372-10 3.333a2.5 2.5 0 0 0-1.709 2.372v8.896c0 3.678 1.847 6.761 4.388 9.275 2.544 2.518 5.587 4.26 7.454 5.183a1.46 1.46 0 0 0 1.316 0c1.867-.924 4.91-2.665 7.454-5.183 2.54-2.514 4.388-5.597 4.388-9.275v-8.896a2.5 2.5 0 0 0-1.71-2.372l-10-3.333a2.5 2.5 0 0 0-1.58 0zm6.675 10.384a1.25 1.25 0 0 1 0 1.767l-5.724 5.724a2.583 2.583 0 0 1-3.653 0l-2.39-2.39a1.25 1.25 0 1 1 1.767-1.768l2.39 2.39a.084.084 0 0 0 .118 0l5.724-5.723a1.25 1.25 0 0 1 1.768 0z",
    fill: "#050506"
  }))))));
};

/* harmony default export */ const disabled_stats = ("/disabled-stats.1f56395e.svg");
// EXTERNAL MODULE: ./Extension/src/pages/popup/helpers/index.js
var helpers = __webpack_require__(6449);
// EXTERNAL MODULE: ./Extension/src/pages/popup/components/shared/Icons/index.jsx + 4 modules
var Icons = __webpack_require__(6483);
;// CONCATENATED MODULE: ./Extension/src/pages/popup/components/GlobalStatsView/useKonami.js

const codes = ['arrowup', 'arrowup', 'arrowdown', 'arrowdown', 'arrowleft', 'arrowright', 'arrowleft', 'arrowright', 'b', 'a'];
const useKonamiCode = () => {
  // State for keeping track of whether key is pressed
  const [konamiCode, setIsKonami] = react.useState(false);

  const resetKonami = () => {
    setIsKonami(false);
  }; // Add event listeners


  react.useEffect(() => {
    let index = 0;

    const onKeyUp = ({
      key
    }) => {
      if (index === codes.length - 1) {
        setIsKonami(true);
      }

      if (codes[index] != null && key.toLowerCase() === codes[index]) {
        index += 1;
      } else {
        index = 0;
        setIsKonami(false);
      }
    };

    window.addEventListener('keyup', onKeyUp);
    return () => {
      window.removeEventListener('keyup', onKeyUp);
    };
  }, []);
  return [konamiCode, resetKonami];
};
// EXTERNAL MODULE: ./node_modules/@babel/runtime/helpers/esm/extends.js
var esm_extends = __webpack_require__(5058);
// EXTERNAL MODULE: ./node_modules/classnames/index.js
var classnames = __webpack_require__(1613);
var classnames_default = /*#__PURE__*/__webpack_require__.n(classnames);
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
// EXTERNAL MODULE: ./node_modules/css-loader/dist/cjs.js??ruleSet[1].rules[6].use[1]!./node_modules/postcss-loader/dist/cjs.js!./node_modules/sass-loader/dist/cjs.js!./Extension/src/pages/popup/components/GlobalStatsView/ActionButton/ActionButton.module.scss
var ActionButton_module = __webpack_require__(9187);
;// CONCATENATED MODULE: ./Extension/src/pages/popup/components/GlobalStatsView/ActionButton/ActionButton.module.scss

      
      
      
      
      
      
      
      
      

var options = {};

options.styleTagTransform = (styleTagTransform_default());
options.setAttributes = (setAttributesWithoutAttributes_default());

      options.insert = insertBySelector_default().bind(null, "head");
    
options.domAPI = (styleDomAPI_default());
options.insertStyleElement = (insertStyleElement_default());

var update = injectStylesIntoStyleTag_default()(ActionButton_module/* default */.Z, options);




       /* harmony default export */ const ActionButton_ActionButton_module = (ActionButton_module/* default */.Z && ActionButton_module/* default.locals */.Z.locals ? ActionButton_module/* default.locals */.Z.locals : undefined);

;// CONCATENATED MODULE: ./Extension/src/pages/popup/components/GlobalStatsView/ActionButton/ActionButton.jsx





function ActionButton({
  children,
  type,
  ...props
}) {
  return /*#__PURE__*/react.createElement(Text/* Text */.xv, {
    color: "secondary",
    typo: "body-2",
    raw: true
  }, /*#__PURE__*/react.createElement(Flex/* Flex */.k, (0,esm_extends/* default */.Z)({
    alignCenter: true,
    center: true,
    as: "button",
    className: classnames_default()(ActionButton_ActionButton_module.ActionButton, type === 'danger' && ActionButton_ActionButton_module.ActionButtonDanger)
  }, props), children));
}
// EXTERNAL MODULE: ./node_modules/css-loader/dist/cjs.js??ruleSet[1].rules[6].use[1]!./node_modules/postcss-loader/dist/cjs.js!./node_modules/sass-loader/dist/cjs.js!./Extension/src/pages/popup/components/GlobalStatsView/GlobalStatsView.module.scss
var GlobalStatsView_module = __webpack_require__(5885);
;// CONCATENATED MODULE: ./Extension/src/pages/popup/components/GlobalStatsView/GlobalStatsView.module.scss

      
      
      
      
      
      
      
      
      

var GlobalStatsView_module_options = {};

GlobalStatsView_module_options.styleTagTransform = (styleTagTransform_default());
GlobalStatsView_module_options.setAttributes = (setAttributesWithoutAttributes_default());

      GlobalStatsView_module_options.insert = insertBySelector_default().bind(null, "head");
    
GlobalStatsView_module_options.domAPI = (styleDomAPI_default());
GlobalStatsView_module_options.insertStyleElement = (insertStyleElement_default());

var GlobalStatsView_module_update = injectStylesIntoStyleTag_default()(GlobalStatsView_module/* default */.Z, GlobalStatsView_module_options);




       /* harmony default export */ const GlobalStatsView_GlobalStatsView_module = (GlobalStatsView_module/* default */.Z && GlobalStatsView_module/* default.locals */.Z.locals ? GlobalStatsView_module/* default.locals */.Z.locals : undefined);

;// CONCATENATED MODULE: ./Extension/src/pages/popup/components/GlobalStatsView/GlobalStatsView.jsx
















const isMac = browser_utils/* browserUtils.isMacOs */.z.isMacOs();
const LIST_SIZE = 5;
const GlobalStatsView = (0,mobxreact_esm/* observer */.Pi)(({
  store
}) => {
  var _store$blockedDomains, _store$blockedDomains2;

  const [isKonami] = useKonamiCode();
  const [showDisableConfirm, toggleShowDisableConfirm] = esm_useToggle(false);
  const [justEnabled, setJustEnabled] = (0,react.useState)(false);
  const annoyanceTime = react.useMemo(() => (0,helpers/* formatAnnoyanceTime */.ah)(store.totalBlocked), [store.totalBlocked]);
  const {
    showGlobalStats
  } = store;
  const domains = ((_store$blockedDomains = store.blockedDomains) === null || _store$blockedDomains === void 0 ? void 0 : (_store$blockedDomains2 = _store$blockedDomains.total) === null || _store$blockedDomains2 === void 0 ? void 0 : _store$blockedDomains2.domains) || [];
  const domainsStr = JSON.stringify(store.blockedDomains);
  (0,react.useEffect)(() => {
    try {
      if (isKonami && navigator && navigator.clipboard) {
        navigator.clipboard.writeText(domainsStr);
      }
    } catch (error) {
      // eslint-disable-next-line no-console
      console.log(error);
    }
  }, [isKonami, domainsStr]);
  const list = Object.keys(domains).map(domain => ({
    domain,
    count: domains[domain]
  })).sort((a, b) => b.count - a.count).slice(0, LIST_SIZE);

  const toggleGlobalStats = () => {
    if (showDisableConfirm) {
      toggleShowDisableConfirm();
    } else {
      setJustEnabled(true);
    }

    store.setShowGlobalStats(!showGlobalStats);

    if (showGlobalStats) {
      store.deleteBlockedDomains();
    }
  };

  const onDelete = () => {
    store.deleteBlockedDomains();
  };

  if (showDisableConfirm) {
    return /*#__PURE__*/react.createElement(DisableConfirmView, {
      onConfirm: toggleGlobalStats
    });
  }

  if (!showGlobalStats) {
    return /*#__PURE__*/react.createElement(DisabledView, {
      onEnable: toggleGlobalStats
    });
  }

  if (list.length === 0) {
    return /*#__PURE__*/react.createElement(EmptyView, {
      justEnabled: justEnabled
    });
  }

  return /*#__PURE__*/react.createElement(Flex/* Flex */.k, {
    column: true,
    between: true,
    takeAvailableSpace: true,
    style: {
      height: 'calc(100vh - 72px)'
    }
  }, /*#__PURE__*/react.createElement(Stack/* Stack */.K, {
    gap: "s"
  }, /*#__PURE__*/react.createElement(Box/* Box */.x, {
    mb: "l"
  }, /*#__PURE__*/react.createElement(Text/* Text */.xv, {
    typo: "heading-5",
    bold: true,
    color: "primary",
    as: "h1"
  }, (0,reactTranslator.t)('global_stats'))), /*#__PURE__*/react.createElement(Stack/* Stack */.K, {
    gap: "s",
    horizontal: true,
    nowrap: true
  }, /*#__PURE__*/react.createElement(Tile/* Tile */.n, {
    icon: Icons/* IconShield */.xq,
    tight: !isMac,
    label: (0,reactTranslator.t)('popup_stats_trackers'),
    value: (0,helpers/* formatCounter */.w8)(store.totalBlocked),
    color: "purple"
  }), /*#__PURE__*/react.createElement(Tile/* Tile */.n, {
    icon: Icons/* IconTime */.qS,
    tight: !isMac,
    label: (0,reactTranslator.t)('popup_stats_time_saved'),
    value: annoyanceTime,
    color: "purple"
  })), /*#__PURE__*/react.createElement("div", {
    style: {
      minHeight: 212
    }
  }, /*#__PURE__*/react.createElement(Table/* Table */.i, {
    list: list
  }))), /*#__PURE__*/react.createElement(Stack/* Stack */.K, {
    gap: "xs"
  }, /*#__PURE__*/react.createElement(ActionButton, {
    type: "danger",
    onClick: toggleShowDisableConfirm
  }, /*#__PURE__*/react.createElement(index_esm/* RiLineChartLine */.dmK, null), /*#__PURE__*/react.createElement("span", null, (0,reactTranslator.t)('global_stats_disable_action'))), /*#__PURE__*/react.createElement(ActionButton, {
    onClick: onDelete
  }, /*#__PURE__*/react.createElement(index_esm/* RiDeleteBinLine */.qy0, null), /*#__PURE__*/react.createElement("span", null, (0,reactTranslator.t)('delete')))));
});

function EmptyView({
  justEnabled
}) {
  return /*#__PURE__*/react.createElement(react.Fragment, null, /*#__PURE__*/react.createElement(Stack/* Stack */.K, {
    gap: "xxs"
  }, /*#__PURE__*/react.createElement(Text/* Text */.xv, {
    typo: "heading-5",
    bold: true,
    color: "primary",
    as: "h1"
  }, (0,reactTranslator.t)('global_stats')), /*#__PURE__*/react.createElement(Text/* Text */.xv, {
    typo: "body-2",
    color: "primary"
  }, (0,reactTranslator.t)(justEnabled ? 'global_stats_enabled_success' : 'global_stats_empty'))), /*#__PURE__*/react.createElement(Flex/* Flex */.k, {
    p: "s",
    column: true,
    alignCenter: true,
    center: true,
    className: GlobalStatsView_GlobalStatsView_module.EmptyState
  }, /*#__PURE__*/react.createElement("img", {
    src: empty_stats,
    alt: ""
  })));
}

function DisabledView({
  onEnable
}) {
  return /*#__PURE__*/react.createElement(react.Fragment, null, /*#__PURE__*/react.createElement(Stack/* Stack */.K, {
    gap: "xxs"
  }, /*#__PURE__*/react.createElement(Text/* Text */.xv, {
    typo: "heading-5",
    bold: true,
    color: "primary",
    as: "h1"
  }, (0,reactTranslator.t)('global_stats')), /*#__PURE__*/react.createElement(Text/* Text */.xv, {
    typo: "body-2",
    color: "primary"
  }, (0,reactTranslator.t)('global_stats_disabled'))), /*#__PURE__*/react.createElement(Flex/* Flex */.k, {
    p: "s",
    column: true,
    alignCenter: true,
    center: true,
    takeAvailableSpace: true,
    className: GlobalStatsView_GlobalStatsView_module.EmptyState
  }, /*#__PURE__*/react.createElement("img", {
    src: disabled_stats,
    alt: ""
  })), /*#__PURE__*/react.createElement(Button/* Button */.z, {
    variant: "primary-black",
    full: true,
    onClick: onEnable
  }, /*#__PURE__*/react.createElement(index_esm/* RiLineChartLine */.dmK, null), (0,reactTranslator.t)('global_stats_enable')));
}

function DisableConfirmView({
  onConfirm
}) {
  return /*#__PURE__*/react.createElement(react.Fragment, null, /*#__PURE__*/react.createElement(Stack/* Stack */.K, {
    gap: "xxs",
    mb: "xl"
  }, /*#__PURE__*/react.createElement(Text/* Text */.xv, {
    typo: "heading-5",
    bold: true,
    color: "primary",
    as: "h1"
  }, (0,reactTranslator.t)('global_stats_disable_title')), /*#__PURE__*/react.createElement(Text/* Text */.xv, {
    typo: "body-2",
    color: "primary"
  }, (0,reactTranslator.t)('global_stats_disable_description'))), /*#__PURE__*/react.createElement(ActionButton, {
    type: "danger",
    full: true,
    onClick: onConfirm
  }, /*#__PURE__*/react.createElement(index_esm/* RiLineChartLine */.dmK, null), (0,reactTranslator.t)('global_stats_disable_action_confirm')));
}

/* harmony default export */ const GlobalStatsView_GlobalStatsView = (GlobalStatsView);

/***/ }),

/***/ 2208:
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {


// EXPORTS
__webpack_require__.d(__webpack_exports__, {
  "i": () => (/* binding */ Table)
});

// EXTERNAL MODULE: ./node_modules/react/index.js
var react = __webpack_require__(5506);
// EXTERNAL MODULE: ./Extension/src/common/translators/reactTranslator.js
var reactTranslator = __webpack_require__(7103);
// EXTERNAL MODULE: ./node_modules/@qwant/qwant-ponents/src/Text/Text.jsx + 2 modules
var Text = __webpack_require__(3352);
// EXTERNAL MODULE: ./Extension/src/pages/common/components/ThinCard/ThinCard.jsx + 1 modules
var ThinCard = __webpack_require__(2141);
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
// EXTERNAL MODULE: ./node_modules/css-loader/dist/cjs.js??ruleSet[1].rules[6].use[1]!./node_modules/postcss-loader/dist/cjs.js!./node_modules/sass-loader/dist/cjs.js!./Extension/src/pages/popup/components/shared/Table/Table.module.scss
var Table_module = __webpack_require__(6961);
;// CONCATENATED MODULE: ./Extension/src/pages/popup/components/shared/Table/Table.module.scss

      
      
      
      
      
      
      
      
      

var options = {};

options.styleTagTransform = (styleTagTransform_default());
options.setAttributes = (setAttributesWithoutAttributes_default());

      options.insert = insertBySelector_default().bind(null, "head");
    
options.domAPI = (styleDomAPI_default());
options.insertStyleElement = (insertStyleElement_default());

var update = injectStylesIntoStyleTag_default()(Table_module/* default */.Z, options);




       /* harmony default export */ const Table_Table_module = (Table_module/* default */.Z && Table_module/* default.locals */.Z.locals ? Table_module/* default.locals */.Z.locals : undefined);

;// CONCATENATED MODULE: ./Extension/src/pages/popup/components/shared/Table/Table.jsx





const Table = ({
  list
}) => {
  if (!list || !Array.isArray(list) || list.length === 0) {
    return null;
  }

  return /*#__PURE__*/react.createElement(ThinCard/* ThinCard */.S, {
    as: "table",
    className: Table_Table_module.Table
  }, /*#__PURE__*/react.createElement("thead", null, /*#__PURE__*/react.createElement("tr", null, /*#__PURE__*/react.createElement(Text/* Text */.xv, {
    typo: "body-2",
    as: "th",
    bold: true
  }, (0,reactTranslator.t)('popup_stats_table_domains_label')), /*#__PURE__*/react.createElement(Text/* Text */.xv, {
    center: true,
    typo: "body-2",
    as: "th",
    bold: true
  }, (0,reactTranslator.t)('popup_stats_table_trackers_label')))), /*#__PURE__*/react.createElement("tbody", null, list.map(element => /*#__PURE__*/react.createElement("tr", {
    key: element.domain
  }, /*#__PURE__*/react.createElement(Text/* Text */.xv, {
    typo: "body-2",
    raw: true
  }, /*#__PURE__*/react.createElement("td", null, element.domain)), /*#__PURE__*/react.createElement(Text/* Text */.xv, {
    typo: "body-2",
    center: true,
    raw: true
  }, /*#__PURE__*/react.createElement("td", null, element.count))))));
};

/***/ }),

/***/ 9187:
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
___CSS_LOADER_EXPORT___.push([module.id, ".ActionButton-module__ActionButton___Sxnbb{border-radius:8px;border:1px solid var(--grey-200);text-align:center;min-height:36px;width:100%}.ActionButton-module__ActionButton___Sxnbb svg,.ActionButton-module__ActionButton___Sxnbb img{width:16px;height:16px;margin-right:var(--spacing-xxs)}.ActionButton-module__ActionButtonDanger___AHItS{color:var(--red-500) !important}", "",{"version":3,"sources":["webpack://./Extension/src/pages/popup/components/GlobalStatsView/ActionButton/ActionButton.module.scss"],"names":[],"mappings":"AAAA,2CAAc,iBAAiB,CAAC,gCAAgC,CAAC,iBAAiB,CAAC,eAAe,CAAC,UAAU,CAAC,8FAAoC,UAAU,CAAC,WAAW,CAAC,+BAA+B,CAAC,iDAAoB,+BAA+B","sourcesContent":[".ActionButton{border-radius:8px;border:1px solid var(--grey-200);text-align:center;min-height:36px;width:100%}.ActionButton svg,.ActionButton img{width:16px;height:16px;margin-right:var(--spacing-xxs)}.ActionButtonDanger{color:var(--red-500) !important}"],"sourceRoot":""}]);
// Exports
___CSS_LOADER_EXPORT___.locals = {
	"ActionButton": "ActionButton-module__ActionButton___Sxnbb",
	"ActionButtonDanger": "ActionButton-module__ActionButtonDanger___AHItS"
};
/* harmony default export */ const __WEBPACK_DEFAULT_EXPORT__ = (___CSS_LOADER_EXPORT___);


/***/ }),

/***/ 5885:
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
___CSS_LOADER_EXPORT___.push([module.id, ".GlobalStatsView-module__EmptyState___RcGPe{min-height:calc(100% - 90px)}", "",{"version":3,"sources":["webpack://./Extension/src/pages/popup/components/GlobalStatsView/GlobalStatsView.module.scss"],"names":[],"mappings":"AAAA,4CAAY,4BAA4B","sourcesContent":[".EmptyState{min-height:calc(100% - 90px)}"],"sourceRoot":""}]);
// Exports
___CSS_LOADER_EXPORT___.locals = {
	"EmptyState": "GlobalStatsView-module__EmptyState___RcGPe"
};
/* harmony default export */ const __WEBPACK_DEFAULT_EXPORT__ = (___CSS_LOADER_EXPORT___);


/***/ }),

/***/ 6961:
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
___CSS_LOADER_EXPORT___.push([module.id, ".Table-module__Table___YERaH{border-spacing:0;width:100%}.Table-module__Table___YERaH th{border-bottom:solid 1px var(--grey-200)}.Table-module__Table___YERaH th:first-child{text-align:left}.Table-module__Table___YERaH th,.Table-module__Table___YERaH td{padding:var(--spacing-xs) var(--spacing-s)}.Table-module__Table___YERaH tr:nth-child(2n+1) td{background-color:rgba(233,234,236,.5)}", "",{"version":3,"sources":["webpack://./Extension/src/pages/popup/components/shared/Table/Table.module.scss"],"names":[],"mappings":"AAAA,6BAAO,gBAAgB,CAAC,UAAU,CAAC,gCAAU,uCAAuC,CAAC,4CAAsB,eAAe,CAAC,gEAAoB,0CAA0C,CAAC,mDAA6B,qCAAqC","sourcesContent":[".Table{border-spacing:0;width:100%}.Table th{border-bottom:solid 1px var(--grey-200)}.Table th:first-child{text-align:left}.Table th,.Table td{padding:var(--spacing-xs) var(--spacing-s)}.Table tr:nth-child(2n+1) td{background-color:rgba(233,234,236,.5)}"],"sourceRoot":""}]);
// Exports
___CSS_LOADER_EXPORT___.locals = {
	"Table": "Table-module__Table___YERaH"
};
/* harmony default export */ const __WEBPACK_DEFAULT_EXPORT__ = (___CSS_LOADER_EXPORT___);


/***/ })

}]);