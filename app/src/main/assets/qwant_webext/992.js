"use strict";
(self["webpackChunkqwant_viprivacy"] = self["webpackChunkqwant_viprivacy"] || []).push([[992],{

/***/ 2992:
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

// ESM COMPAT FLAG
__webpack_require__.r(__webpack_exports__);

// EXPORTS
__webpack_require__.d(__webpack_exports__, {
  "default": () => (/* binding */ TabStatsView_TabStatsView)
});

// EXTERNAL MODULE: ./node_modules/core-js/modules/es.array.reduce.js
var es_array_reduce = __webpack_require__(9294);
// EXTERNAL MODULE: ./node_modules/react/index.js
var react = __webpack_require__(5506);
// EXTERNAL MODULE: ./node_modules/mobx-react/dist/mobxreact.esm.js + 17 modules
var mobxreact_esm = __webpack_require__(9901);
// EXTERNAL MODULE: ./Extension/src/common/translators/reactTranslator.js
var reactTranslator = __webpack_require__(7103);
// EXTERNAL MODULE: ./Extension/src/background/utils/browser-utils.js
var browser_utils = __webpack_require__(638);
// EXTERNAL MODULE: ./node_modules/@qwant/qwant-ponents/src/Stack/Stack.js + 1 modules
var Stack = __webpack_require__(1981);
// EXTERNAL MODULE: ./node_modules/@qwant/qwant-ponents/src/Text/Text.jsx + 2 modules
var Text = __webpack_require__(3352);
// EXTERNAL MODULE: ./node_modules/@qwant/qwant-ponents/src/Flex/Flex.js + 1 modules
var Flex = __webpack_require__(8478);
// EXTERNAL MODULE: ./Extension/src/pages/popup/components/shared/Table/Table.jsx + 1 modules
var Table = __webpack_require__(2208);
// EXTERNAL MODULE: ./Extension/src/pages/popup/components/shared/Tile/Tile.jsx + 1 modules
var Tile = __webpack_require__(3404);
;// CONCATENATED MODULE: ./Extension/src/pages/popup/components/TabStatsView/empty-stats.svg
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
    x: 96,
    y: 8,
    width: 144,
    height: 144,
    rx: 72
  }), /*#__PURE__*/React.createElement("rect", {
    id: "empty-stats_svg__c",
    x: 0,
    y: 0,
    width: 176,
    height: 112,
    rx: 12
  }), /*#__PURE__*/React.createElement("rect", {
    id: "empty-stats_svg__h",
    x: 0,
    y: 0,
    width: 120,
    height: 112,
    rx: 12
  }), /*#__PURE__*/React.createElement("filter", {
    x: "-2.3%",
    y: "-3.6%",
    width: "106.8%",
    height: "110.7%",
    filterUnits: "objectBoundingBox",
    id: "empty-stats_svg__d"
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
    y: "-3.6%",
    width: "110%",
    height: "110.7%",
    filterUnits: "objectBoundingBox",
    id: "empty-stats_svg__i"
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
    id: "empty-stats_svg__f"
  }))), _g || (_g = /*#__PURE__*/React.createElement("g", {
    fill: "none",
    fillRule: "evenodd"
  }, /*#__PURE__*/React.createElement("path", {
    d: "M0 0h336v160H0z"
  }), /*#__PURE__*/React.createElement("mask", {
    id: "empty-stats_svg__b",
    fill: "#fff"
  }, /*#__PURE__*/React.createElement("use", {
    xlinkHref: "#empty-stats_svg__a"
  })), /*#__PURE__*/React.createElement("g", {
    mask: "url(#empty-stats_svg__b)"
  }, /*#__PURE__*/React.createElement("g", {
    transform: "translate(80 24)"
  }, /*#__PURE__*/React.createElement("path", {
    stroke: "#050506",
    strokeWidth: 2,
    fill: "#E9EAEC",
    d: "M71 111h34v18H71z"
  }), /*#__PURE__*/React.createElement("mask", {
    id: "empty-stats_svg__e",
    fill: "#fff"
  }, /*#__PURE__*/React.createElement("use", {
    xlinkHref: "#empty-stats_svg__c"
  })), /*#__PURE__*/React.createElement("use", {
    fill: "#000",
    filter: "url(#empty-stats_svg__d)",
    xlinkHref: "#empty-stats_svg__c"
  }), /*#__PURE__*/React.createElement("rect", {
    stroke: "#050506",
    strokeWidth: 2,
    fill: "#050506",
    x: -1,
    y: -1,
    width: 178,
    height: 114,
    rx: 12
  }), /*#__PURE__*/React.createElement("path", {
    fill: "#E9EAEC",
    mask: "url(#empty-stats_svg__e)",
    d: "M0 96h176v16H0z"
  }), /*#__PURE__*/React.createElement("path", {
    fill: "#050506",
    mask: "url(#empty-stats_svg__e)",
    d: "M0 96h176v2H0z"
  }), /*#__PURE__*/React.createElement("path", {
    d: "M162 3c3.038 0 5.788 1.231 7.778 3.222A10.966 10.966 0 0 1 173 14v79H3V14c0-3.038 1.231-5.788 3.222-7.778A10.966 10.966 0 0 1 14 3z",
    stroke: "#050506",
    strokeWidth: 2,
    fill: "#D6E5FF",
    mask: "url(#empty-stats_svg__e)"
  }), /*#__PURE__*/React.createElement("rect", {
    stroke: "#050506",
    strokeWidth: 2,
    fill: "#B3E6CC",
    mask: "url(#empty-stats_svg__e)",
    x: 15,
    y: 15,
    width: 146,
    height: 66,
    rx: 4
  }), /*#__PURE__*/React.createElement("path", {
    d: "M156 15c1.38 0 2.63.56 3.536 1.464A4.984 4.984 0 0 1 161 20v9H15v-9c0-1.38.56-2.63 1.464-3.536A4.984 4.984 0 0 1 20 15z",
    stroke: "#050506",
    strokeWidth: 2,
    fill: "#FFF",
    mask: "url(#empty-stats_svg__e)"
  }), /*#__PURE__*/React.createElement("g", {
    mask: "url(#empty-stats_svg__e)"
  }, /*#__PURE__*/React.createElement("g", {
    transform: "translate(20 20)",
    fill: "#A7ACB4"
  }, /*#__PURE__*/React.createElement("circle", {
    cx: 14,
    cy: 2,
    r: 2
  }), /*#__PURE__*/React.createElement("circle", {
    cx: 8,
    cy: 2,
    r: 2
  }), /*#__PURE__*/React.createElement("circle", {
    cx: 2,
    cy: 2,
    r: 2
  }))))), /*#__PURE__*/React.createElement("path", {
    d: "M123.995 136.992C136.164 146.402 151.428 152 168 152c39.765 0 72-32.235 72-72S207.765 8 168 8c-16.495 0-31.694 5.547-43.834 14.877",
    stroke: "#050506",
    strokeWidth: 2
  }), /*#__PURE__*/React.createElement("g", {
    transform: "translate(78 6)"
  }, /*#__PURE__*/React.createElement("mask", {
    id: "empty-stats_svg__g",
    fill: "#fff"
  }, /*#__PURE__*/React.createElement("use", {
    xlinkHref: "#empty-stats_svg__f"
  })), /*#__PURE__*/React.createElement("g", {
    mask: "url(#empty-stats_svg__g)"
  }, /*#__PURE__*/React.createElement("g", {
    transform: "translate(2 18)"
  }, /*#__PURE__*/React.createElement("mask", {
    id: "empty-stats_svg__j",
    fill: "#fff"
  }, /*#__PURE__*/React.createElement("use", {
    xlinkHref: "#empty-stats_svg__h"
  })), /*#__PURE__*/React.createElement("use", {
    fill: "#000",
    filter: "url(#empty-stats_svg__i)",
    xlinkHref: "#empty-stats_svg__h"
  }), /*#__PURE__*/React.createElement("rect", {
    stroke: "#050506",
    strokeWidth: 2,
    fill: "#050506",
    x: -1,
    y: -1,
    width: 122,
    height: 114,
    rx: 12
  }), /*#__PURE__*/React.createElement("path", {
    fill: "#E9EAEC",
    mask: "url(#empty-stats_svg__j)",
    d: "M0 96h176v16H0z"
  }), /*#__PURE__*/React.createElement("path", {
    fill: "#050506",
    mask: "url(#empty-stats_svg__j)",
    d: "M0 96h176v2H0z"
  }), /*#__PURE__*/React.createElement("path", {
    d: "M162 3c3.038 0 5.788 1.231 7.778 3.222A10.966 10.966 0 0 1 173 14v79H3V14c0-3.038 1.231-5.788 3.222-7.778A10.966 10.966 0 0 1 14 3z",
    stroke: "#050506",
    strokeWidth: 2,
    fill: "#D6E5FF",
    mask: "url(#empty-stats_svg__j)"
  }), /*#__PURE__*/React.createElement("rect", {
    stroke: "#050506",
    strokeWidth: 2,
    fill: "#B3E6CC",
    mask: "url(#empty-stats_svg__j)",
    x: 15,
    y: 15,
    width: 146,
    height: 66,
    rx: 4
  }), /*#__PURE__*/React.createElement("path", {
    d: "M156 15c1.38 0 2.63.56 3.536 1.464A4.984 4.984 0 0 1 161 20v9H15v-9c0-1.38.56-2.63 1.464-3.536A4.984 4.984 0 0 1 20 15z",
    stroke: "#050506",
    strokeWidth: 2,
    fill: "#FFF",
    mask: "url(#empty-stats_svg__j)"
  }), /*#__PURE__*/React.createElement("g", {
    mask: "url(#empty-stats_svg__j)"
  }, /*#__PURE__*/React.createElement("g", {
    transform: "translate(20 20)",
    fill: "#A7ACB4"
  }, /*#__PURE__*/React.createElement("circle", {
    cx: 14,
    cy: 2,
    r: 2
  }), /*#__PURE__*/React.createElement("circle", {
    cx: 8,
    cy: 2,
    r: 2
  }), /*#__PURE__*/React.createElement("circle", {
    cx: 2,
    cy: 2,
    r: 2
  }))), /*#__PURE__*/React.createElement("g", {
    mask: "url(#empty-stats_svg__j)"
  }, /*#__PURE__*/React.createElement("path", {
    d: "m86.672 24.21-16.8 5.452c-1.715.557-2.872 2.12-2.872 3.88v14.552c0 6.017 3.104 11.06 7.371 15.172 4.275 4.118 9.388 6.967 12.523 8.478a2.52 2.52 0 0 0 2.212 0c3.135-1.511 8.248-4.36 12.523-8.478 4.267-4.112 7.371-9.155 7.371-15.172V33.542c0-1.76-1.157-3.323-2.872-3.88l-16.8-5.452a4.307 4.307 0 0 0-2.656 0z",
    stroke: "#050506",
    strokeWidth: 2.043,
    fill: "#B3E6CC"
  }), /*#__PURE__*/React.createElement("path", {
    d: "M88 33c-7.18 0-13 5.82-13 13s5.82 13 13 13 13-5.82 13-13-5.82-13-13-13z",
    fill: "#57C78F"
  }), /*#__PURE__*/React.createElement("path", {
    d: "M72 30h32v32H72z"
  }), /*#__PURE__*/React.createElement("path", {
    d: "M88 33c-7.18 0-13 5.82-13 13s5.82 13 13 13 13-5.82 13-13-5.82-13-13-13zM77.414 43c-.27.954-.414 1.96-.414 3s.144 2.046.414 3h4.414a27.51 27.51 0 0 1-.161-3c0-1.03.055-2.035.161-3h-4.414zm.785-2h3.932c.281-1.448.683-2.765 1.184-3.891a11.7 11.7 0 0 1 .716-1.371A11.04 11.04 0 0 0 78.2 41zm5.642 2a25.385 25.385 0 0 0-.174 3c0 1.043.06 2.048.174 3h8.318c.113-.952.174-1.957.174-3s-.061-2.048-.174-3H83.84zm7.987-2h-7.656c.249-1.16.58-2.2.97-3.08.45-1.009.957-1.764 1.471-2.253.51-.484.976-.667 1.387-.667.41 0 .878.183 1.387.667.514.49 1.022 1.244 1.47 2.254.392.88.722 1.92.971 3.079zm2.344 2c.106.965.161 1.97.161 3s-.055 2.035-.161 3h4.414c.27-.954.414-1.96.414-3s-.144-2.046-.414-3h-4.414zm3.629-2h-3.932c-.281-1.448-.683-2.765-1.184-3.891a11.608 11.608 0 0 0-.716-1.371A11.039 11.039 0 0 1 97.8 41zM84.03 56.262A11.04 11.04 0 0 1 78.2 51h3.932c.281 1.448.683 2.765 1.184 3.891.217.488.456.948.716 1.371zm1.112-2.183c-.392-.88-.722-1.92-.971-3.079h7.656c-.249 1.16-.58 2.2-.97 3.08-.45 1.009-.957 1.763-1.471 2.253-.51.484-.976.667-1.387.667-.41 0-.878-.183-1.387-.667-.514-.49-1.022-1.244-1.47-2.254zm7.542.812c.5-1.126.903-2.443 1.184-3.891H97.8a11.04 11.04 0 0 1-5.832 5.262c.26-.423.5-.883.716-1.37z",
    fill: "#050506"
  }))))), /*#__PURE__*/React.createElement("path", {
    d: "m165.995 44.315-17.6 5.68c-2.615.844-4.395 3.234-4.395 5.945v15.158c0 6.384 2.993 12.126 8.339 17.248 3.943 3.778 8.76 6.853 13.639 9.19a4.645 4.645 0 0 0 4.045 0c4.879-2.337 9.695-5.412 13.638-9.19C189.007 83.224 192 77.482 192 71.098V55.94c0-2.71-1.78-5.101-4.395-5.945l-17.6-5.68a6.537 6.537 0 0 0-4.01 0z",
    fill: "#050506",
    fillRule: "nonzero"
  }), /*#__PURE__*/React.createElement("path", {
    d: "M166.609 46.219a4.538 4.538 0 0 1 2.534-.073l.248.073 17.6 5.68c1.728.557 2.915 2.083 3.004 3.83l.005.21v15.159c0 6.268-3.252 11.521-7.722 15.804-3.323 3.183-7.129 5.638-10.213 7.335l-.788.426a51.48 51.48 0 0 1-.75.391l-.708.357c-.228.113-.449.22-.66.322a2.645 2.645 0 0 1-2.317 0l-.325-.157-.685-.339c-.354-.178-.724-.37-1.11-.574l-.787-.426c-3.084-1.697-6.89-4.152-10.213-7.335-4.369-4.186-7.574-9.298-7.717-15.378l-.005-.426V55.94c0-1.764 1.12-3.336 2.804-3.97l.205-.072 17.6-5.68z",
    fill: "#B3E6CC",
    fillRule: "nonzero"
  }), /*#__PURE__*/React.createElement("path", {
    d: "M168 54.375c-8.077 0-14.625 6.548-14.625 14.625S159.923 83.625 168 83.625 182.625 77.077 182.625 69 176.077 54.375 168 54.375z",
    fill: "#57C78F"
  }), /*#__PURE__*/React.createElement("path", {
    d: "M150 51h36v36h-36z"
  }), /*#__PURE__*/React.createElement("path", {
    d: "M168 54.375c-8.077 0-14.625 6.548-14.625 14.625S159.923 83.625 168 83.625 182.625 77.077 182.625 69 176.077 54.375 168 54.375zm-11.91 11.25a12.384 12.384 0 0 0-.465 3.375c0 1.17.162 2.302.466 3.375h4.966a30.95 30.95 0 0 1-.182-3.375c0-1.159.063-2.29.182-3.375h-4.966zm.884-2.25h4.423c.317-1.629.77-3.111 1.332-4.378.244-.549.513-1.066.806-1.542a12.419 12.419 0 0 0-6.56 5.92zm6.348 2.25a28.558 28.558 0 0 0-.197 3.375c0 1.173.069 2.304.197 3.375h9.356a28.57 28.57 0 0 0 .197-3.375 28.57 28.57 0 0 0-.197-3.375h-9.356zm8.985-2.25h-8.614c.28-1.304.652-2.474 1.092-3.464.505-1.136 1.077-1.985 1.655-2.535.572-.545 1.098-.751 1.56-.751.462 0 .988.206 1.56.75.578.551 1.15 1.4 1.655 2.536.44.99.812 2.16 1.092 3.464zm2.636 2.25c.12 1.086.182 2.216.182 3.375 0 1.159-.063 2.29-.182 3.375h4.966a12.39 12.39 0 0 0 .466-3.375c0-1.17-.162-2.302-.466-3.375h-4.966zm4.083-2.25h-4.423c-.317-1.629-.77-3.111-1.332-4.378a13.058 13.058 0 0 0-.806-1.542 12.419 12.419 0 0 1 6.56 5.92zm-15.49 17.17a12.42 12.42 0 0 1-6.562-5.92h4.423c.317 1.629.77 3.111 1.332 4.378.244.549.513 1.066.806 1.542zm1.25-2.456c-.44-.99-.813-2.16-1.093-3.464h8.614c-.28 1.304-.652 2.474-1.092 3.464-.505 1.136-1.077 1.985-1.655 2.535-.572.545-1.098.751-1.56.751-.462 0-.988-.206-1.56-.75-.578-.551-1.15-1.4-1.655-2.536zm8.485.914c.563-1.267 1.015-2.75 1.332-4.378h4.423a12.42 12.42 0 0 1-6.561 5.92c.293-.476.562-.993.806-1.542z",
    fill: "#050506"
  }))));
};

/* harmony default export */ const empty_stats = ("/empty-stats.bd8ece6e.svg");
;// CONCATENATED MODULE: ./Extension/src/pages/popup/components/TabStatsView/empty-stats-mobile.svg
var empty_stats_mobile_defs, empty_stats_mobile_g;
function empty_stats_mobile_extends() { empty_stats_mobile_extends = Object.assign ? Object.assign.bind() : function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; }; return empty_stats_mobile_extends.apply(this, arguments); }

var SvgEmptyStatsMobile = function SvgEmptyStatsMobile(props) {
  return /*#__PURE__*/React.createElement("svg", empty_stats_mobile_extends({
    width: 336,
    height: 160,
    xmlns: "http://www.w3.org/2000/svg",
    xmlnsXlink: "http://www.w3.org/1999/xlink",
    role: "img"
  }, props), empty_stats_mobile_defs || (empty_stats_mobile_defs = /*#__PURE__*/React.createElement("defs", null, /*#__PURE__*/React.createElement("path", {
    d: "M96 0h144v80c0 39.765-32.235 72-72 72s-72-32.235-72-72V0z",
    id: "empty-stats-mobile_svg__a"
  }), /*#__PURE__*/React.createElement("path", {
    d: "m107.67 217.001-88.484-.409C8.544 216.543-.045 207.912 0 197.312V19.11A19.16 19.16 0 0 1 5.708 5.561 19.32 19.32 0 0 1 19.363 0l89.423.409a19.316 19.316 0 0 1 13.616 5.67A19.165 19.165 0 0 1 128 19.682l-.954 178.201a19.163 19.163 0 0 1-5.711 13.559 19.324 19.324 0 0 1-13.667 5.56z",
    id: "empty-stats-mobile_svg__d"
  }), /*#__PURE__*/React.createElement("path", {
    d: "M103.515.4 84.037.325c-2.306 0-4.203 1.92-4.212 4.313l-.014 3.097c0 2.393-1.897 4.325-4.213 4.314l-33.671-.154c-2.307 0-4.186-1.957-4.178-4.35l.015-3.098c0-2.393-1.86-4.342-4.178-4.353l-3.873-.03L13.654 0a13.687 13.687 0 0 0-9.636 3.9A13.355 13.355 0 0 0 0 13.392l.017 179.101c-.02 7.41 6.033 13.436 13.53 13.47l88.921.121a13.679 13.679 0 0 0 9.629-3.89 13.352 13.352 0 0 0 4.025-9.478l.924-178.82c.032-7.418-6.024-13.46-13.531-13.495z",
    id: "empty-stats-mobile_svg__e"
  }), /*#__PURE__*/React.createElement("path", {
    d: "M103.515.4 84.037.325c-2.306 0-4.203 1.92-4.212 4.313l-.014 3.097c0 2.393-1.897 4.325-4.213 4.314l-33.671-.154c-2.307 0-4.186-1.957-4.178-4.35l.015-3.098c0-2.393-1.86-4.342-4.178-4.353l-3.873-.03L13.654 0a13.687 13.687 0 0 0-9.636 3.9A13.355 13.355 0 0 0 0 13.392l.017 179.101c-.02 7.41 6.033 13.436 13.53 13.47l88.921.121a13.679 13.679 0 0 0 9.629-3.89 13.352 13.352 0 0 0 4.025-9.478l.924-178.82c.032-7.418-6.024-13.46-13.531-13.495z",
    id: "empty-stats-mobile_svg__g"
  }), /*#__PURE__*/React.createElement("filter", {
    x: "-1.6%",
    y: "-.9%",
    width: "106.2%",
    height: "103.7%",
    filterUnits: "objectBoundingBox",
    id: "empty-stats-mobile_svg__c"
  }, /*#__PURE__*/React.createElement("feOffset", {
    dx: 4,
    dy: 4,
    "in": "SourceAlpha",
    result: "shadowOffsetOuter1"
  }), /*#__PURE__*/React.createElement("feComposite", {
    "in": "shadowOffsetOuter1",
    in2: "SourceAlpha",
    operator: "out",
    result: "shadowOffsetOuter1"
  }), /*#__PURE__*/React.createElement("feColorMatrix", {
    values: "0 0 0 0 0.0196078431 0 0 0 0 0.0196078431 0 0 0 0 0.0235294118 0 0 0 0.24 0",
    "in": "shadowOffsetOuter1"
  })))), empty_stats_mobile_g || (empty_stats_mobile_g = /*#__PURE__*/React.createElement("g", {
    fill: "none",
    fillRule: "evenodd"
  }, /*#__PURE__*/React.createElement("path", {
    d: "M0 0h336v160H0z"
  }), /*#__PURE__*/React.createElement("mask", {
    id: "empty-stats-mobile_svg__b",
    fill: "#fff"
  }, /*#__PURE__*/React.createElement("use", {
    xlinkHref: "#empty-stats-mobile_svg__a"
  })), /*#__PURE__*/React.createElement("g", {
    mask: "url(#empty-stats-mobile_svg__b)"
  }, /*#__PURE__*/React.createElement("g", {
    fillRule: "nonzero",
    transform: "translate(104)"
  }, /*#__PURE__*/React.createElement("use", {
    fill: "#000",
    filter: "url(#empty-stats-mobile_svg__c)",
    xlinkHref: "#empty-stats-mobile_svg__d"
  }), /*#__PURE__*/React.createElement("path", {
    stroke: "#050506",
    strokeWidth: 2,
    d: "m19.359 1 89.424.409c4.85.016 9.494 1.95 12.91 5.377a18.164 18.164 0 0 1 5.308 12.89l-.954 178.202a18.163 18.163 0 0 1-5.414 12.851 18.324 18.324 0 0 1-12.96 5.272l-88.482-.409a18.255 18.255 0 0 1-12.897-5.388A18.107 18.107 0 0 1 1 197.312V19.115A18.16 18.16 0 0 1 6.41 6.273 18.319 18.319 0 0 1 19.36 1z",
    strokeLinejoin: "square",
    fill: "#FFF",
    fillRule: "evenodd"
  })), /*#__PURE__*/React.createElement("g", {
    transform: "translate(109.469 5.47)"
  }, /*#__PURE__*/React.createElement("mask", {
    id: "empty-stats-mobile_svg__f",
    fill: "#fff"
  }, /*#__PURE__*/React.createElement("use", {
    xlinkHref: "#empty-stats-mobile_svg__e"
  })), /*#__PURE__*/React.createElement("use", {
    fill: "#B3E6CC",
    xlinkHref: "#empty-stats-mobile_svg__e"
  }), /*#__PURE__*/React.createElement("path", {
    fill: "#F4F5F6",
    mask: "url(#empty-stats-mobile_svg__f)",
    d: "M1.531.53h114v41h-114z"
  }), /*#__PURE__*/React.createElement("path", {
    fill: "#050506",
    mask: "url(#empty-stats-mobile_svg__f)",
    d: "M-.469 39.53h118v2h-118z"
  }), /*#__PURE__*/React.createElement("rect", {
    stroke: "#050506",
    strokeWidth: 2,
    fill: "#FFF",
    mask: "url(#empty-stats-mobile_svg__f)",
    x: 8.531,
    y: 19.53,
    width: 100,
    height: 14,
    rx: 4
  }), /*#__PURE__*/React.createElement("mask", {
    id: "empty-stats-mobile_svg__h",
    fill: "#fff"
  }, /*#__PURE__*/React.createElement("use", {
    xlinkHref: "#empty-stats-mobile_svg__g"
  })), /*#__PURE__*/React.createElement("path", {
    stroke: "#050506",
    strokeWidth: 2,
    d: "m13.65 1 16.056.064 3.875.029a3.114 3.114 0 0 1 2.239.973c.586.61.944 1.453.944 2.376l-.015 3.098a5.403 5.403 0 0 0 1.5 3.769 5.085 5.085 0 0 0 3.673 1.585l33.672.154a5.119 5.119 0 0 0 3.697-1.56 5.355 5.355 0 0 0 1.52-3.75l.014-3.097a3.375 3.375 0 0 1 .955-2.354 3.148 3.148 0 0 1 2.253-.963l19.477.077a12.637 12.637 0 0 1 8.891 3.687 12.319 12.319 0 0 1 3.645 8.804l-.924 178.819a12.353 12.353 0 0 1-3.725 8.769 12.68 12.68 0 0 1-8.928 3.604l-88.917-.12a12.634 12.634 0 0 1-8.883-3.679 12.317 12.317 0 0 1-3.652-8.792L1 13.396c.01-3.3 1.349-6.458 3.718-8.782A12.686 12.686 0 0 1 13.65 1z"
  }), /*#__PURE__*/React.createElement("g", {
    mask: "url(#empty-stats-mobile_svg__h)",
    fillRule: "nonzero"
  }, /*#__PURE__*/React.createElement("path", {
    d: "m56.526 58.845-17.6 5.68c-2.615.844-4.395 3.234-4.395 5.945v15.158c0 6.384 2.993 12.126 8.34 17.248 3.942 3.778 8.76 6.853 13.638 9.19a4.645 4.645 0 0 0 4.045 0c4.88-2.337 9.695-5.412 13.638-9.19 5.346-5.122 8.34-10.864 8.34-17.248V70.47c0-2.71-1.78-5.1-4.396-5.945l-17.6-5.68a6.537 6.537 0 0 0-4.01 0z",
    fill: "#050506"
  }), /*#__PURE__*/React.createElement("path", {
    d: "M57.14 60.75c.821-.265 1.7-.29 2.534-.074l.248.073 17.6 5.68c1.728.557 2.915 2.083 3.004 3.83l.005.21v15.16c0 6.267-3.252 11.52-7.722 15.803-3.323 3.183-7.129 5.638-10.213 7.335l-.788.426a51.48 51.48 0 0 1-.75.391l-.708.357c-.228.113-.449.22-.66.322a2.645 2.645 0 0 1-2.317 0l-.325-.157-.685-.339c-.354-.178-.724-.37-1.11-.574l-.787-.426c-3.084-1.697-6.89-4.152-10.213-7.335-4.369-4.186-7.574-9.298-7.717-15.378l-.005-.426V70.47c0-1.764 1.12-3.336 2.804-3.97l.205-.072 17.6-5.68v.001z",
    fill: "#B3E6CC"
  }), /*#__PURE__*/React.createElement("path", {
    d: "M58.531 68.905c-8.077 0-14.625 6.548-14.625 14.625s6.548 14.625 14.625 14.625 14.625-6.548 14.625-14.625-6.548-14.625-14.625-14.625z",
    fill: "#57C78F"
  }), /*#__PURE__*/React.createElement("path", {
    d: "M58.531 68.905c8.077 0 14.625 6.548 14.625 14.625s-6.548 14.625-14.625 14.625-14.625-6.548-14.625-14.625 6.548-14.625 14.625-14.625zm4.307 20.25h-8.614c.28 1.304.653 2.474 1.093 3.464.504 1.136 1.076 1.985 1.654 2.536.572.544 1.098.75 1.56.75.462 0 .988-.206 1.56-.75.578-.55 1.15-1.4 1.655-2.536.44-.99.812-2.16 1.092-3.464zm-10.91 0h-4.423a12.42 12.42 0 0 0 6.562 5.92h-.003l-.213-.364a13.33 13.33 0 0 1-.403-.772l-.188-.406c-.562-1.267-1.015-2.749-1.332-4.378zm17.63 0h-4.424c-.317 1.628-.769 3.111-1.332 4.378l-.187.406c-.193.4-.399.78-.619 1.136a12.42 12.42 0 0 0 6.561-5.92zm-17.97-9h-4.967a12.384 12.384 0 0 0-.465 3.375c0 1.17.162 2.302.466 3.375h4.966a30.95 30.95 0 0 1-.182-3.375c0-1.159.063-2.29.182-3.375zm11.621 0h-9.356a28.56 28.56 0 0 0-.197 3.375c0 1.173.07 2.304.197 3.375h9.356c.133-1.12.198-2.247.197-3.375a28.572 28.572 0 0 0-.197-3.375zm7.231 0h-4.966c.12 1.086.182 2.216.182 3.375 0 1.16-.063 2.29-.182 3.375h4.966a12.39 12.39 0 0 0 .466-3.375c0-1.17-.162-2.302-.466-3.375zm-16.374-8.17a12.419 12.419 0 0 0-6.56 5.92h4.422c.317-1.629.77-3.11 1.332-4.378.244-.549.513-1.066.806-1.542zm4.465-.83c-.462 0-.988.206-1.56.751-.578.55-1.15 1.4-1.655 2.535-.44.99-.812 2.16-1.092 3.464h8.614c-.28-1.304-.652-2.474-1.092-3.464-.505-1.136-1.077-1.985-1.655-2.536-.572-.544-1.098-.75-1.56-.75zm4.465.83c.303.496.572 1.011.806 1.542.562 1.267 1.015 2.75 1.332 4.378h4.422a12.419 12.419 0 0 0-6.56-5.92z",
    fill: "#050506"
  })))), /*#__PURE__*/React.createElement("path", {
    d: "M104.718 45.628C99.158 55.842 96 67.552 96 80c0 12.327 3.098 23.93 8.557 34.074C116.71 136.653 140.562 152 168 152c27.403 0 51.231-15.31 63.397-37.841C236.885 103.995 240 92.36 240 80c0-12.724-3.3-24.677-9.092-35.05",
    stroke: "#050506",
    strokeWidth: 2,
    strokeLinecap: "round"
  }))));
};

/* harmony default export */ const empty_stats_mobile = ("/empty-stats-mobile.199cd2fb.svg");
// EXTERNAL MODULE: ./Extension/src/pages/popup/helpers/index.js
var helpers = __webpack_require__(6449);
// EXTERNAL MODULE: ./Extension/src/pages/popup/components/shared/Icons/index.jsx + 4 modules
var Icons = __webpack_require__(6483);
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
// EXTERNAL MODULE: ./node_modules/css-loader/dist/cjs.js??ruleSet[1].rules[6].use[1]!./node_modules/postcss-loader/dist/cjs.js!./node_modules/sass-loader/dist/cjs.js!./Extension/src/pages/popup/components/TabStatsView/TabStatsView.module.scss
var TabStatsView_module = __webpack_require__(9165);
;// CONCATENATED MODULE: ./Extension/src/pages/popup/components/TabStatsView/TabStatsView.module.scss

      
      
      
      
      
      
      
      
      

var options = {};

options.styleTagTransform = (styleTagTransform_default());
options.setAttributes = (setAttributesWithoutAttributes_default());

      options.insert = insertBySelector_default().bind(null, "head");
    
options.domAPI = (styleDomAPI_default());
options.insertStyleElement = (insertStyleElement_default());

var update = injectStylesIntoStyleTag_default()(TabStatsView_module/* default */.Z, options);




       /* harmony default export */ const TabStatsView_TabStatsView_module = (TabStatsView_module/* default */.Z && TabStatsView_module/* default.locals */.Z.locals ? TabStatsView_module/* default.locals */.Z.locals : undefined);

;// CONCATENATED MODULE: ./Extension/src/pages/popup/components/TabStatsView/TabStatsView.jsx













const LIST_SIZE = 7;
const TabStatsView = (0,mobxreact_esm/* observer */.Pi)(({
  store
}) => {
  const totalBlockedDomains = Object.keys(store.blockedDomainsTab).length;
  const {
    currentSite,
    totalBlockedTab = 0
  } = store || {};
  const list = Object.keys(store.blockedDomainsTab).map(domain => ({
    domain,
    count: store.blockedDomainsTab[domain]
  })).sort((a, b) => b.count - a.count).slice(0, LIST_SIZE);
  const domainCount = list.reduce((prev, current) => prev + current.count, 0);
  const count = totalBlockedTab >= domainCount ? totalBlockedTab : domainCount;
  const websiteUrl = (0,helpers/* isWebURL */.o6)(currentSite) ? currentSite : null;
  return /*#__PURE__*/react.createElement(react.Fragment, null, /*#__PURE__*/react.createElement(Stack/* Stack */.K, {
    gap: "s"
  }, /*#__PURE__*/react.createElement(Stack/* Stack */.K, {
    gap: "xxs",
    mb: "l"
  }, /*#__PURE__*/react.createElement(Text/* Text */.xv, {
    typo: "heading-4",
    bold: true,
    color: "primary",
    as: "h1"
  }, (0,reactTranslator.t)('popup_stats_blocked_elements')), /*#__PURE__*/react.createElement(Text/* Text */.xv, {
    typo: "body-2",
    color: "primary"
  }, websiteUrl && (0,reactTranslator.t)('popup_main_nbr_blocked_elements_domain', {
    domain: websiteUrl
  }))), /*#__PURE__*/react.createElement(Stack/* Stack */.K, {
    gap: "s",
    horizontal: true,
    nowrap: true
  }, /*#__PURE__*/react.createElement(Tile/* Tile */.n, {
    icon: Icons/* IconGlobe */.vc,
    value: (0,helpers/* formatCounter */.w8)(totalBlockedDomains),
    label: (0,reactTranslator.t)('popup_stats_table_domains_label')
  }), /*#__PURE__*/react.createElement(Tile/* Tile */.n, {
    icon: Icons/* IconShield */.xq,
    value: (0,helpers/* formatCounter */.w8)(count),
    label: (0,reactTranslator.t)('popup_stats_trackers')
  })), list.length > 0 && /*#__PURE__*/react.createElement(Table/* Table */.i, {
    list: list
  })), list.length === 0 && /*#__PURE__*/react.createElement(Flex/* Flex */.k, {
    p: "s",
    column: true,
    alignCenter: true,
    center: true,
    className: TabStatsView_TabStatsView_module.EmptyState
  }, /*#__PURE__*/react.createElement("img", {
    src: browser_utils/* browserUtils.isMobileQwant */.z.isMobileQwant() ? empty_stats_mobile : empty_stats,
    alt: ""
  })));
});
/* harmony default export */ const TabStatsView_TabStatsView = (TabStatsView);

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

/***/ 9165:
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
___CSS_LOADER_EXPORT___.push([module.id, ".TabStatsView-module__EmptyState___Fxx2y{min-height:calc(100% - 142px)}", "",{"version":3,"sources":["webpack://./Extension/src/pages/popup/components/TabStatsView/TabStatsView.module.scss"],"names":[],"mappings":"AAAA,yCAAY,6BAA6B","sourcesContent":[".EmptyState{min-height:calc(100% - 142px)}"],"sourceRoot":""}]);
// Exports
___CSS_LOADER_EXPORT___.locals = {
	"EmptyState": "TabStatsView-module__EmptyState___Fxx2y"
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