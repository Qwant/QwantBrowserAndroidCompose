"use strict";
(self["webpackChunkqwant_viprivacy"] = self["webpackChunkqwant_viprivacy"] || []).push([[579],{

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

/***/ 1579:
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

// ESM COMPAT FLAG
__webpack_require__.r(__webpack_exports__);

// EXPORTS
__webpack_require__.d(__webpack_exports__, {
  "default": () => (/* binding */ MainView)
});

// EXTERNAL MODULE: ./node_modules/react/index.js
var react = __webpack_require__(5506);
// EXTERNAL MODULE: ./node_modules/mobx-react/dist/mobxreact.esm.js + 17 modules
var mobxreact_esm = __webpack_require__(9901);
// EXTERNAL MODULE: ./node_modules/react-router/dist/index.js + 1 modules
var dist = __webpack_require__(4319);
// EXTERNAL MODULE: ./Extension/src/common/constants.js
var constants = __webpack_require__(9595);
// EXTERNAL MODULE: ./Extension/src/background/extension-api/browser.js
var browser = __webpack_require__(9186);
// EXTERNAL MODULE: ./Extension/src/background/utils/optional-permissions.js
var optional_permissions = __webpack_require__(2501);
// EXTERNAL MODULE: ./node_modules/@qwant/qwant-ponents/src/Stack/Stack.js + 1 modules
var Stack = __webpack_require__(1981);
// EXTERNAL MODULE: ./node_modules/@babel/runtime/helpers/esm/extends.js
var esm_extends = __webpack_require__(5058);
// EXTERNAL MODULE: ./Extension/src/pages/common/components/ThinCard/ThinCard.jsx + 1 modules
var ThinCard = __webpack_require__(2141);
// EXTERNAL MODULE: ./Extension/src/common/translators/reactTranslator.js
var reactTranslator = __webpack_require__(7103);
// EXTERNAL MODULE: ./node_modules/react-icons/ri/index.esm.js
var index_esm = __webpack_require__(8665);
// EXTERNAL MODULE: ./node_modules/react-icons/ai/index.esm.js
var ai_index_esm = __webpack_require__(4385);
// EXTERNAL MODULE: ./node_modules/@qwant/qwant-ponents/src/Flex/Flex.js + 1 modules
var Flex = __webpack_require__(8478);
// EXTERNAL MODULE: ./node_modules/@qwant/qwant-ponents/src/Text/Text.jsx + 2 modules
var Text = __webpack_require__(3352);
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
// EXTERNAL MODULE: ./node_modules/css-loader/dist/cjs.js??ruleSet[1].rules[6].use[1]!./node_modules/postcss-loader/dist/cjs.js!./node_modules/sass-loader/dist/cjs.js!./Extension/src/pages/popup/components/MainLinks/MainLinks.module.scss
var MainLinks_module = __webpack_require__(2940);
;// CONCATENATED MODULE: ./Extension/src/pages/popup/components/MainLinks/MainLinks.module.scss

      
      
      
      
      
      
      
      
      

var options = {};

options.styleTagTransform = (styleTagTransform_default());
options.setAttributes = (setAttributesWithoutAttributes_default());

      options.insert = insertBySelector_default().bind(null, "head");
    
options.domAPI = (styleDomAPI_default());
options.insertStyleElement = (insertStyleElement_default());

var update = injectStylesIntoStyleTag_default()(MainLinks_module/* default */.Z, options);




       /* harmony default export */ const MainLinks_MainLinks_module = (MainLinks_module/* default */.Z && MainLinks_module/* default.locals */.Z.locals ? MainLinks_module/* default.locals */.Z.locals : undefined);

;// CONCATENATED MODULE: ./Extension/src/pages/popup/components/MainLinks/MainLinks.jsx








function MainLinks({
  withStats
}) {
  const navigate = (0,dist/* useNavigate */.s0)();
  const links = (0,react.useMemo)(() => [...(withStats ? [{
    label: (0,reactTranslator.t)('stats'),
    to: () => navigate('/global-stats'),
    icon: index_esm/* RiLineChartLine */.dmK
  }] : []), {
    label: (0,reactTranslator.t)('infos'),
    to: () => navigate('/about'),
    icon: ai_index_esm/* AiOutlineInfoCircle */.ocf
  }, {
    label: (0,reactTranslator.t)('survey'),
    to: (0,reactTranslator.t)('survey_url'),
    icon: index_esm/* RiExternalLinkLine */.uXP
  }], [navigate, withStats]);
  return /*#__PURE__*/react.createElement(ThinCard/* ThinCard */.S, {
    className: MainLinks_MainLinks_module.MainLinks
  }, links.map(l => /*#__PURE__*/react.createElement(Link, (0,esm_extends/* default */.Z)({
    key: l.label
  }, l))));
}

function Link({
  label,
  to,
  icon: IconComponent
}) {
  const isAnchor = typeof to === 'string';

  if (isAnchor) {
    return /*#__PURE__*/react.createElement(Flex/* Flex */.k, {
      as: "a",
      target: "_blank",
      rel: "noreferrer",
      alignCenter: true,
      center: true,
      href: to
    }, /*#__PURE__*/react.createElement(IconComponent, null), /*#__PURE__*/react.createElement(Text/* Text */.xv, {
      typo: "body-2"
    }, label));
  }

  return /*#__PURE__*/react.createElement(Flex/* Flex */.k, {
    as: "button",
    alignCenter: true,
    center: true,
    onClick: to
  }, /*#__PURE__*/react.createElement(IconComponent, null), /*#__PURE__*/react.createElement(Text/* Text */.xv, {
    typo: "body-2"
  }, label));
}
// EXTERNAL MODULE: ./Extension/src/pages/popup/components/LoadingView/LoadingView.jsx + 1 modules
var LoadingView = __webpack_require__(5115);
// EXTERNAL MODULE: ./Extension/src/pages/popup/components/shared/Icons/index.jsx + 4 modules
var Icons = __webpack_require__(6483);
// EXTERNAL MODULE: ./Extension/src/pages/popup/components/shared/Tile/Tile.jsx + 1 modules
var Tile = __webpack_require__(3404);
// EXTERNAL MODULE: ./node_modules/css-loader/dist/cjs.js??ruleSet[1].rules[6].use[1]!./node_modules/postcss-loader/dist/cjs.js!./node_modules/sass-loader/dist/cjs.js!./Extension/src/pages/popup/components/shared/ThinCardLink/ThinCardLink.module.scss
var ThinCardLink_module = __webpack_require__(5290);
;// CONCATENATED MODULE: ./Extension/src/pages/popup/components/shared/ThinCardLink/ThinCardLink.module.scss

      
      
      
      
      
      
      
      
      

var ThinCardLink_module_options = {};

ThinCardLink_module_options.styleTagTransform = (styleTagTransform_default());
ThinCardLink_module_options.setAttributes = (setAttributesWithoutAttributes_default());

      ThinCardLink_module_options.insert = insertBySelector_default().bind(null, "head");
    
ThinCardLink_module_options.domAPI = (styleDomAPI_default());
ThinCardLink_module_options.insertStyleElement = (insertStyleElement_default());

var ThinCardLink_module_update = injectStylesIntoStyleTag_default()(ThinCardLink_module/* default */.Z, ThinCardLink_module_options);




       /* harmony default export */ const ThinCardLink_ThinCardLink_module = (ThinCardLink_module/* default */.Z && ThinCardLink_module/* default.locals */.Z.locals ? ThinCardLink_module/* default.locals */.Z.locals : undefined);

;// CONCATENATED MODULE: ./Extension/src/pages/popup/components/shared/ThinCardLink/ThinCardLink.jsx




/**
 * @param {(MouseEvent) => void} onClick
 * @param {string} title
 * @param {string} label
 */

function ThinCardLink({
  onClick,
  title,
  label
}) {
  return /*#__PURE__*/react.createElement(ThinCard/* ThinCard */.S, {
    p: "s",
    onClick: onClick,
    as: "button",
    className: ThinCardLink_ThinCardLink_module.ThinCardLink
  }, /*#__PURE__*/react.createElement(Flex/* Flex */.k, {
    between: true,
    alignCenter: true
  }, /*#__PURE__*/react.createElement(Text/* Text */.xv, {
    bold: true,
    typo: "body-2",
    color: "primary"
  }, title), /*#__PURE__*/react.createElement(Text/* Text */.xv, {
    typo: "heading-5",
    color: "primary"
  }, /*#__PURE__*/react.createElement(Flex/* Flex */.k, {
    alignCenter: true
  }, /*#__PURE__*/react.createElement(Text/* Text */.xv, {
    typo: "body-2",
    color: "primary"
  }, label), /*#__PURE__*/react.createElement(index_esm/* RiArrowRightSLine */.jfD, null)))));
}
// EXTERNAL MODULE: ./Extension/src/background/utils/browser-utils.js
var browser_utils = __webpack_require__(638);
// EXTERNAL MODULE: ./Extension/src/pages/popup/helpers/index.js
var helpers = __webpack_require__(6449);
// EXTERNAL MODULE: ./node_modules/css-loader/dist/cjs.js??ruleSet[1].rules[6].use[1]!./node_modules/postcss-loader/dist/cjs.js!./node_modules/sass-loader/dist/cjs.js!./Extension/src/pages/popup/components/MainView/GlobalStats/GlobalStats.module.scss
var GlobalStats_module = __webpack_require__(9311);
;// CONCATENATED MODULE: ./Extension/src/pages/popup/components/MainView/GlobalStats/GlobalStats.module.scss

      
      
      
      
      
      
      
      
      

var GlobalStats_module_options = {};

GlobalStats_module_options.styleTagTransform = (styleTagTransform_default());
GlobalStats_module_options.setAttributes = (setAttributesWithoutAttributes_default());

      GlobalStats_module_options.insert = insertBySelector_default().bind(null, "head");
    
GlobalStats_module_options.domAPI = (styleDomAPI_default());
GlobalStats_module_options.insertStyleElement = (insertStyleElement_default());

var GlobalStats_module_update = injectStylesIntoStyleTag_default()(GlobalStats_module/* default */.Z, GlobalStats_module_options);




       /* harmony default export */ const GlobalStats_GlobalStats_module = (GlobalStats_module/* default */.Z && GlobalStats_module/* default.locals */.Z.locals ? GlobalStats_module/* default.locals */.Z.locals : undefined);

;// CONCATENATED MODULE: ./Extension/src/pages/popup/components/MainView/GlobalStats/GlobalStats.jsx










const isMac = browser_utils/* browserUtils.isMacOs */.z.isMacOs();
const GlobalStats = ({
  showGlobalStats,
  totalBlocked
}) => {
  const navigate = (0,dist/* useNavigate */.s0)();
  const annoyanceTime = (0,react.useMemo)(() => (0,helpers/* formatAnnoyanceTime */.ah)(totalBlocked), [totalBlocked]);

  if (!showGlobalStats) {
    return /*#__PURE__*/react.createElement(ThinCardLink, {
      title: (0,reactTranslator.t)('global_stats'),
      label: (0,reactTranslator.t)('global_stats_disabled_short'),
      onClick: () => navigate('/global-stats')
    });
  }

  const items = [{
    label: (0,reactTranslator.t)('popup_stats_blocked_trackers'),
    value: (0,helpers/* formatCounter */.w8)(totalBlocked),
    icon: Icons/* IconShield */.xq
  }, {
    label: (0,reactTranslator.t)('popup_stats_time_saved'),
    value: annoyanceTime,
    icon: Icons/* IconTime */.qS
  }];
  return /*#__PURE__*/react.createElement("div", {
    className: GlobalStats_GlobalStats_module.GlobalStats
  }, items.map(item => /*#__PURE__*/react.createElement(Tile/* Tile */.n, (0,esm_extends/* default */.Z)({
    key: item.label,
    asCard: true,
    color: "purple",
    tight: !isMac
  }, item))));
};
// EXTERNAL MODULE: ./node_modules/@qwant/qwant-ponents/src/Button/Button.jsx + 2 modules
var Button = __webpack_require__(2735);
// EXTERNAL MODULE: ./Extension/src/pages/common/components/CheckboxCard/CheckboxCard.jsx + 2 modules
var CheckboxCard = __webpack_require__(1115);
// EXTERNAL MODULE: ./node_modules/@qwant/qwant-ponents/src/Box/Box.js + 1 modules
var Box = __webpack_require__(5845);
// EXTERNAL MODULE: ./node_modules/classnames/index.js
var classnames = __webpack_require__(1613);
var classnames_default = /*#__PURE__*/__webpack_require__.n(classnames);
// EXTERNAL MODULE: ./node_modules/css-loader/dist/cjs.js??ruleSet[1].rules[6].use[1]!./node_modules/postcss-loader/dist/cjs.js!./node_modules/sass-loader/dist/cjs.js!./Extension/src/pages/popup/components/MainView/ShieldCount/ShieldCount.module.scss
var ShieldCount_module = __webpack_require__(8501);
;// CONCATENATED MODULE: ./Extension/src/pages/popup/components/MainView/ShieldCount/ShieldCount.module.scss

      
      
      
      
      
      
      
      
      

var ShieldCount_module_options = {};

ShieldCount_module_options.styleTagTransform = (styleTagTransform_default());
ShieldCount_module_options.setAttributes = (setAttributesWithoutAttributes_default());

      ShieldCount_module_options.insert = insertBySelector_default().bind(null, "head");
    
ShieldCount_module_options.domAPI = (styleDomAPI_default());
ShieldCount_module_options.insertStyleElement = (insertStyleElement_default());

var ShieldCount_module_update = injectStylesIntoStyleTag_default()(ShieldCount_module/* default */.Z, ShieldCount_module_options);




       /* harmony default export */ const ShieldCount_ShieldCount_module = (ShieldCount_module/* default */.Z && ShieldCount_module/* default.locals */.Z.locals ? ShieldCount_module/* default.locals */.Z.locals : undefined);

;// CONCATENATED MODULE: ./Extension/src/pages/popup/components/MainView/ShieldCount/ShieldCount.jsx





function ShieldCount({
  color = 'green',
  count,
  className,
  ...props
}) {
  return /*#__PURE__*/react.createElement(Box/* Box */.x, (0,esm_extends/* default */.Z)({
    className: classnames_default()(ShieldCount_ShieldCount_module.IconShield, className)
  }, props), /*#__PURE__*/react.createElement("svg", {
    width: "60",
    height: "68",
    viewBox: "0 0 60 68",
    xmlns: "http://www.w3.org/2000/svg"
  }, /*#__PURE__*/react.createElement("path", {
    d: "M26.733 1.53a10.349 10.349 0 0 1 6.534 0L51.934 7.74A10.319 10.319 0 0 1 59 17.529v16.578c0 6.942-2.742 12.82-6.556 17.625-5.812 7.322-14.117 12.124-18.703 14.39a8.4 8.4 0 0 1-7.482 0c-4.586-2.266-12.891-7.068-18.703-14.39C3.742 46.927 1 41.049 1 34.107V17.53a10.319 10.319 0 0 1 7.066-9.788z",
    stroke: `var(--${color}-500)`,
    strokeWidth: "2",
    fill: "#FFF"
  }), /*#__PURE__*/react.createElement("path", {
    d: "M30 2c.997 0 1.994.16 2.952.478L51.618 8.69A9.319 9.319 0 0 1 58 17.53v16.577c0 17.088-17.298 27.46-24.702 31.118a7.438 7.438 0 0 1-3.297.775z",
    fill: `var(--${color}-200)`
  }), /*#__PURE__*/react.createElement("path", {
    d: "M28.524 6.898 9.858 13.109a4.66 4.66 0 0 0-3.191 4.42v16.578c0 6.855 3.448 12.6 8.19 17.284 4.75 4.692 10.43 7.937 13.914 9.658a2.73 2.73 0 0 0 2.458 0c3.484-1.72 9.165-4.966 13.914-9.658 4.742-4.684 8.19-10.43 8.19-17.284V17.53a4.66 4.66 0 0 0-3.19-4.42L31.475 6.898a4.674 4.674 0 0 0-2.952 0z",
    fill: `var(--${color}-100)`
  }), count === undefined && /*#__PURE__*/react.createElement("path", {
    d: "M22.707 23.293a1 1 0 0 0-1.414 1.414L28.586 32l-7.293 7.293a1 1 0 1 0 1.414 1.414L30 33.414l7.293 7.293a1 1 0 1 0 1.414-1.414L31.414 32l7.293-7.293a1 1 0 0 0-1.414-1.414L30 30.586l-7.293-7.293z",
    fill: `var(--${color}-500)`
  })), /*#__PURE__*/react.createElement(Text/* Text */.xv, {
    typo: count > 99 ? 'heading-5' : 'heading-2',
    bold: true,
    center: true,
    className: ShieldCount_ShieldCount_module.IconShieldCount
  }, count > 99 ? '99+' : count));
}
// EXTERNAL MODULE: ./node_modules/css-loader/dist/cjs.js??ruleSet[1].rules[6].use[1]!./node_modules/postcss-loader/dist/cjs.js!./node_modules/sass-loader/dist/cjs.js!./Extension/src/pages/popup/components/MainView/PermissionsMissing/PermissionMissing.module.scss
var PermissionMissing_module = __webpack_require__(5889);
;// CONCATENATED MODULE: ./Extension/src/pages/popup/components/MainView/PermissionsMissing/PermissionMissing.module.scss

      
      
      
      
      
      
      
      
      

var PermissionMissing_module_options = {};

PermissionMissing_module_options.styleTagTransform = (styleTagTransform_default());
PermissionMissing_module_options.setAttributes = (setAttributesWithoutAttributes_default());

      PermissionMissing_module_options.insert = insertBySelector_default().bind(null, "head");
    
PermissionMissing_module_options.domAPI = (styleDomAPI_default());
PermissionMissing_module_options.insertStyleElement = (insertStyleElement_default());

var PermissionMissing_module_update = injectStylesIntoStyleTag_default()(PermissionMissing_module/* default */.Z, PermissionMissing_module_options);




       /* harmony default export */ const PermissionsMissing_PermissionMissing_module = (PermissionMissing_module/* default */.Z && PermissionMissing_module/* default.locals */.Z.locals ? PermissionMissing_module/* default.locals */.Z.locals : undefined);

;// CONCATENATED MODULE: ./Extension/src/pages/popup/components/MainView/PermissionsMissing/PermissionMissing.jsx









const PermissionsMissing = ({
  onRequestPermissions
}) => {
  return /*#__PURE__*/react.createElement("div", null, /*#__PURE__*/react.createElement(Stack/* Stack */.K, {
    gap: "s"
  }, /*#__PURE__*/react.createElement(ThinCard/* ThinCard */.S, {
    className: PermissionsMissing_PermissionMissing_module.PermissionMissing,
    p: "s"
  }, /*#__PURE__*/react.createElement(ShieldCount, {
    color: "grey",
    className: PermissionsMissing_PermissionMissing_module.PermissionMissingShield
  }), /*#__PURE__*/react.createElement(Stack/* Stack */.K, {
    gap: "xxs"
  }, /*#__PURE__*/react.createElement(Text/* Text */.xv, {
    typo: "body-1",
    color: "primary",
    bold: true,
    as: "h2"
  }, (0,reactTranslator.t)('missing_permissions_disabled_title')), /*#__PURE__*/react.createElement(Text/* Text */.xv, {
    typo: "body-2",
    color: "primary",
    as: "p"
  }, (0,reactTranslator.t)('missing_permissions_disabled_description'))), /*#__PURE__*/react.createElement(Button/* Button */.z, {
    className: PermissionsMissing_PermissionMissing_module.PermissionMissingAction,
    onClick: onRequestPermissions,
    variant: "primary-black",
    full: true
  }, /*#__PURE__*/react.createElement(index_esm/* RiShieldCheckLine */.euf, null), (0,reactTranslator.t)('missing_permissions_cta_button_popup'))), /*#__PURE__*/react.createElement(CheckboxCard/* CheckboxCard */.e, {
    selected: true,
    compact: true,
    title: (0,reactTranslator.t)('missing_permissions_default_search_engine_title'),
    description: (0,reactTranslator.t)('missing_permissions_default_search_engine_description')
  })), /*#__PURE__*/react.createElement("div", {
    className: PermissionsMissing_PermissionMissing_module.MainLinks
  }, /*#__PURE__*/react.createElement(MainLinks, null)));
};
;// CONCATENATED MODULE: ./Extension/src/pages/popup/components/MainView/ProtectionLevel/ProtectionLevel.jsx




const getProtectionLevel = ({
  protectionLevel,
  applicationFilteringDisabled
}) => {
  if (applicationFilteringDisabled) return 'disabled';
  return protectionLevel;
};

const ProtectionLevel = ({
  protectionLevel,
  applicationFilteringDisabled,
  onClick
}) => {
  const level = getProtectionLevel({
    protectionLevel,
    applicationFilteringDisabled
  });
  return /*#__PURE__*/react.createElement(ThinCardLink, {
    title: (0,reactTranslator.t)('protection_level'),
    label: level ? (0,reactTranslator.t)(`protection_level_${level}`) : '',
    onClick: onClick
  });
};
// EXTERNAL MODULE: ./node_modules/@qwant/qwant-ponents/src/Card/Card.jsx + 3 modules
var Card = __webpack_require__(3809);
// EXTERNAL MODULE: ./node_modules/react-switch/index.js
var react_switch = __webpack_require__(5637);
// EXTERNAL MODULE: ./node_modules/@formkit/auto-animate/index.mjs
var auto_animate = __webpack_require__(7702);
;// CONCATENATED MODULE: ./Extension/src/pages/common/hooks/useAutoanimate.js


/**
 * AutoAnimate hook for adding dead-simple transitions and animations to react.
 * @param options - Auto animate options or a plugin
 * @returns
 */

function useAutoAnimate(options = {}) {
  const element = (0,react.useRef)(null);
  (0,react.useEffect)(() => {
    if (element.current instanceof HTMLElement) (0,auto_animate/* default */.ZP)(element.current, options);
  }, [element]);
  return [element];
}
// EXTERNAL MODULE: ./node_modules/css-loader/dist/cjs.js??ruleSet[1].rules[6].use[1]!./node_modules/postcss-loader/dist/cjs.js!./node_modules/sass-loader/dist/cjs.js!./Extension/src/pages/popup/components/MainView/ProtectionStatus/ProtectionStatus.module.scss
var ProtectionStatus_module = __webpack_require__(4793);
;// CONCATENATED MODULE: ./Extension/src/pages/popup/components/MainView/ProtectionStatus/ProtectionStatus.module.scss

      
      
      
      
      
      
      
      
      

var ProtectionStatus_module_options = {};

ProtectionStatus_module_options.styleTagTransform = (styleTagTransform_default());
ProtectionStatus_module_options.setAttributes = (setAttributesWithoutAttributes_default());

      ProtectionStatus_module_options.insert = insertBySelector_default().bind(null, "head");
    
ProtectionStatus_module_options.domAPI = (styleDomAPI_default());
ProtectionStatus_module_options.insertStyleElement = (insertStyleElement_default());

var ProtectionStatus_module_update = injectStylesIntoStyleTag_default()(ProtectionStatus_module/* default */.Z, ProtectionStatus_module_options);




       /* harmony default export */ const ProtectionStatus_ProtectionStatus_module = (ProtectionStatus_module/* default */.Z && ProtectionStatus_module/* default.locals */.Z.locals ? ProtectionStatus_module/* default.locals */.Z.locals : undefined);

// EXTERNAL MODULE: ./Extension/src/pages/popup/constants.js
var popup_constants = __webpack_require__(6578);
;// CONCATENATED MODULE: ./Extension/src/pages/popup/components/MainView/ProtectionStatus/ProtectionStatus.jsx











const States = {
  DISABLED: 'disabled',
  ENABLED: 'enabled',
  ALLOWLISTED: 'unavailable'
};
const titles = {
  [States.DISABLED]: 'popup_main_protection_disabled',
  [States.ALLOWLISTED]: 'popup_main_protection_unavailable',
  [States.ENABLED]: 'popup_stats_blocked_elements'
};
const colors = {
  [States.DISABLED]: 'grey',
  [States.ALLOWLISTED]: 'red',
  [States.ENABLED]: 'green'
};

const stopPropagation = e => e.stopPropagation();

const ProtectionStatus = ({
  totalBlockedTab,
  currentSite,
  toggleAllowlisted,
  changeApplicationFilteringDisabled,
  popupState,
  onClick
}) => {
  const isValidURL = (0,helpers/* isWebURL */.o6)(currentSite); // For unreachable site (chrome://), fake the state of the protection to prevent confusion for the user

  const [fakeEnable, setFakeEnable] = react.useState(() => {
    if (isValidURL) return popupState === popup_constants/* POPUP_STATES.APPLICATION_ENABLED */.Z.APPLICATION_ENABLED;
    return true;
  });
  (0,react.useEffect)(() => {
    if (isValidURL) {
      setFakeEnable(popupState === popup_constants/* POPUP_STATES.APPLICATION_ENABLED */.Z.APPLICATION_ENABLED);
    }
  }, [isValidURL, popupState]);
  const switchersMap = {
    [popup_constants/* POPUP_STATES.APPLICATION_ENABLED */.Z.APPLICATION_ENABLED]: {
      handler: () => toggleAllowlisted(),
      state: States.ENABLED
    },
    [popup_constants/* POPUP_STATES.APPLICATION_FILTERING_DISABLED */.Z.APPLICATION_FILTERING_DISABLED]: {
      handler: () => changeApplicationFilteringDisabled(false),
      state: States.DISABLED
    },
    [popup_constants/* POPUP_STATES.APPLICATION_UNAVAILABLE */.Z.APPLICATION_UNAVAILABLE]: {
      handler: () => {
        if (!isValidURL) {
          setFakeEnable(v => !v);
        }
      },
      state: fakeEnable ? States.ENABLED : States.ALLOWLISTED
    },
    [popup_constants/* POPUP_STATES.SITE_IN_EXCEPTION */.Z.SITE_IN_EXCEPTION]: {
      handler: () => {
        if (!isValidURL) {
          setFakeEnable(v => !v);
        }
      },
      state: States.ENABLED
    },
    [popup_constants/* POPUP_STATES.SITE_ALLOWLISTED */.Z.SITE_ALLOWLISTED]: {
      handler: () => toggleAllowlisted(),
      state: States.ALLOWLISTED
    }
  };
  const {
    state
  } = switchersMap[popupState];
  const handleChange = switchersMap[popupState].handler;
  const isDisabled = state === States.DISABLED;
  const isEnabled = state === States.ENABLED || fakeEnable;
  const showDomain = state !== States.DISABLED && isValidURL;
  const showToggle = !isDisabled;
  const className = classnames_default()(ProtectionStatus_ProtectionStatus_module.ProtectionStatus, isDisabled && ProtectionStatus_ProtectionStatus_module.ProtectionStatusDisabled, state === States.ALLOWLISTED && ProtectionStatus_ProtectionStatus_module.ProtectionStatusUnavailable);
  const handleClick = isEnabled ? onClick : null;
  const [animationParent] = useAutoAnimate();
  return /*#__PURE__*/react.createElement(Card/* Card */.Z, {
    ref: animationParent,
    relative: true,
    mt: "xl2",
    className: className,
    onClick: handleClick
  }, /*#__PURE__*/react.createElement(ShieldCount, {
    className: ProtectionStatus_ProtectionStatus_module.ProtectionStatusShield,
    count: isEnabled ? totalBlockedTab : undefined,
    color: colors[state]
  }), isEnabled && /*#__PURE__*/react.createElement(Text/* Text */.xv, {
    typo: "heading-5",
    color: "primary",
    raw: true
  }, /*#__PURE__*/react.createElement(Flex/* Flex */.k, {
    alignCenter: true,
    className: ProtectionStatus_ProtectionStatus_module.ProtectionStatusDetail
  }, /*#__PURE__*/react.createElement(Text/* Text */.xv, {
    typo: "body-2",
    color: "primary"
  }, (0,reactTranslator.t)('popup_main_protection_detail')), /*#__PURE__*/react.createElement(index_esm/* RiArrowRightSLine */.jfD, null))), /*#__PURE__*/react.createElement(Stack/* Stack */.K, {
    gap: "s",
    p: "s",
    as: isEnabled ? 'button' : 'a'
  }, /*#__PURE__*/react.createElement(Stack/* Stack */.K, {
    gap: "xxs"
  }, /*#__PURE__*/react.createElement(Text/* Text */.xv, {
    typo: "heading-5",
    bold: true,
    color: "primary",
    center: true,
    as: "h1"
  }, (0,reactTranslator.t)(titles[state])), showDomain && /*#__PURE__*/react.createElement(Text/* Text */.xv, {
    typo: "body-2",
    color: "primary",
    center: true,
    as: "h2"
  }, (0,reactTranslator.t)('popup_main_nbr_blocked_elements_domain', {
    domain: currentSite
  }))), isEnabled && showToggle && /*#__PURE__*/react.createElement(Text/* Text */.xv, {
    typo: "caption-1",
    color: "primary",
    center: true,
    as: "p"
  }, (0,reactTranslator.t)('popup_main_problem_disable_protection')), isDisabled && /*#__PURE__*/react.createElement(Button/* Button */.z, {
    mt: "s",
    onClick: handleChange,
    variant: "primary-black",
    full: true
  }, /*#__PURE__*/react.createElement(index_esm/* RiShieldCheckLine */.euf, null), (0,reactTranslator.t)('popup_main_protection_enable'))), showToggle && /*#__PURE__*/react.createElement(Text/* Text */.xv, {
    typo: "body-2",
    bold: true,
    raw: true
  }, /*#__PURE__*/react.createElement(Flex/* Flex */.k, {
    onClick: stopPropagation,
    as: "label",
    htmlFor: "switchProtection",
    between: true,
    alignCenter: true,
    className: ProtectionStatus_ProtectionStatus_module.ProtectionStatusFooter,
    gap: "s",
    py: "xs",
    px: "s"
  }, (0,reactTranslator.t)(isEnabled ? 'popup_main_protection_enabled' : 'popup_main_protection_disabled'), /*#__PURE__*/react.createElement(react_switch["default"], {
    checked: isEnabled,
    onChange: handleChange,
    offColor: "#ff5c5f",
    onColor: "#38a870",
    handleDiameter: 20,
    width: 48,
    height: 28,
    checkedHandleIcon: null,
    uncheckedIcon: null,
    uncheckedHandleIcon: null,
    checkedIcon: null,
    id: "switchProtection"
  }))));
};
// EXTERNAL MODULE: ./Extension/src/pages/services/messenger.js
var messenger = __webpack_require__(6451);
// EXTERNAL MODULE: ./node_modules/css-loader/dist/cjs.js??ruleSet[1].rules[6].use[1]!./node_modules/postcss-loader/dist/cjs.js!./node_modules/sass-loader/dist/cjs.js!./Extension/src/pages/popup/components/MainView/MainView.module.scss
var MainView_module = __webpack_require__(4176);
;// CONCATENATED MODULE: ./Extension/src/pages/popup/components/MainView/MainView.module.scss

      
      
      
      
      
      
      
      
      

var MainView_module_options = {};

MainView_module_options.styleTagTransform = (styleTagTransform_default());
MainView_module_options.setAttributes = (setAttributesWithoutAttributes_default());

      MainView_module_options.insert = insertBySelector_default().bind(null, "head");
    
MainView_module_options.domAPI = (styleDomAPI_default());
MainView_module_options.insertStyleElement = (insertStyleElement_default());

var MainView_module_update = injectStylesIntoStyleTag_default()(MainView_module/* default */.Z, MainView_module_options);




       /* harmony default export */ const MainView_MainView_module = (MainView_module/* default */.Z && MainView_module/* default.locals */.Z.locals ? MainView_module/* default.locals */.Z.locals : undefined);

;// CONCATENATED MODULE: ./Extension/src/pages/popup/components/MainView/MainView.jsx
/* eslint-disable max-len */















const Main = (0,mobxreact_esm/* observer */.Pi)(({
  store,
  settingsStore
}) => {
  const navigate = (0,dist/* useNavigate */.s0)();
  const [isLoading, setLoading] = react.useState(false);
  const [hasPermissions, setHasPermissions] = react.useState(null);
  const isReady = !isLoading && store.isInitialDataReceived;
  react.useEffect(() => {
    let timer;

    const checkRequestFilterReady = async () => {
      setLoading(true);
      const response = await messenger/* messenger.sendMessage */.d.sendMessage(constants/* MESSAGE_TYPES.CHECK_REQUEST_FILTER_READY */.oK.CHECK_REQUEST_FILTER_READY);

      if (response !== null && response !== void 0 && response.ready) {
        setLoading(false);
      } else {
        timer = setTimeout(checkRequestFilterReady, 500);
      }
    };

    checkRequestFilterReady();
    return () => {
      if (timer) {
        clearTimeout(timer);
      }
    };
  }, []);
  react.useEffect(() => {
    (async () => {
      setLoading(true);
      const isPermissionsGranted = await (0,optional_permissions/* hasAllOptionalPermissions */.R)();
      setHasPermissions(isPermissionsGranted);
      setLoading(false);
    })();
  }, []);

  const onRequestPermissions = () => {
    browser/* browser.runtime.openOptionsPage */.X.runtime.openOptionsPage();
  };

  if (hasPermissions === false) {
    return /*#__PURE__*/react.createElement(PermissionsMissing, {
      onRequestPermissions: onRequestPermissions
    });
  }

  if (!isReady) {
    return /*#__PURE__*/react.createElement(LoadingView/* LoadingView */.J, null);
  }

  return /*#__PURE__*/react.createElement(Stack/* Stack */.K, {
    gap: "s"
  }, /*#__PURE__*/react.createElement(ProtectionStatus, {
    totalBlockedTab: store.totalBlockedTab,
    currentSite: store.currentSite,
    toggleAllowlisted: store.toggleAllowlisted,
    changeApplicationFilteringDisabled: store.changeApplicationFilteringDisabled,
    popupState: store.popupState,
    onClick: () => navigate('/tab-stats')
  }), /*#__PURE__*/react.createElement(ProtectionLevel, {
    protectionLevel: settingsStore.protectionLevel,
    applicationFilteringDisabled: store.applicationFilteringDisabled,
    onClick: () => navigate('/settings')
  }), /*#__PURE__*/react.createElement(GlobalStats, {
    showGlobalStats: store.showGlobalStats,
    totalBlocked: store.totalBlocked
  }), /*#__PURE__*/react.createElement("div", {
    className: MainView_MainView_module.MainLinks
  }, /*#__PURE__*/react.createElement(MainLinks, {
    withStats: true
  })));
});
/* harmony default export */ const MainView = (Main);

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


/***/ }),

/***/ 2940:
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
___CSS_LOADER_EXPORT___.push([module.id, ".MainLinks-module__MainLinks___BS4Zu{display:flex}.MainLinks-module__MainLinks___BS4Zu a,.MainLinks-module__MainLinks___BS4Zu button{width:100%;color:var(--secondary);height:36px;border-right:1px solid var(--grey-100);line-height:1;gap:var(--spacing-xxs)}.MainLinks-module__MainLinks___BS4Zu a:hover,.MainLinks-module__MainLinks___BS4Zu button:hover{color:var(--primary)}.MainLinks-module__MainLinks___BS4Zu>*:last-child{border-right:none}", "",{"version":3,"sources":["webpack://./Extension/src/pages/popup/components/MainLinks/MainLinks.module.scss"],"names":[],"mappings":"AAAA,qCAAW,YAAY,CAAC,mFAA+B,UAAU,CAAC,sBAAsB,CAAC,WAAW,CAAC,sCAAsC,CAAC,aAAa,CAAC,sBAAsB,CAAC,+FAA2C,oBAAoB,CAAC,kDAAwB,iBAAiB","sourcesContent":[".MainLinks{display:flex}.MainLinks a,.MainLinks button{width:100%;color:var(--secondary);height:36px;border-right:1px solid var(--grey-100);line-height:1;gap:var(--spacing-xxs)}.MainLinks a:hover,.MainLinks button:hover{color:var(--primary)}.MainLinks>*:last-child{border-right:none}"],"sourceRoot":""}]);
// Exports
___CSS_LOADER_EXPORT___.locals = {
	"MainLinks": "MainLinks-module__MainLinks___BS4Zu"
};
/* harmony default export */ const __WEBPACK_DEFAULT_EXPORT__ = (___CSS_LOADER_EXPORT___);


/***/ }),

/***/ 9311:
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
___CSS_LOADER_EXPORT___.push([module.id, ".GlobalStats-module__GlobalStats___hg8Y9{display:grid;grid-template-columns:1fr 1fr;grid-gap:var(--spacing-s);gap:var(--spacing-s)}", "",{"version":3,"sources":["webpack://./Extension/src/pages/popup/components/MainView/GlobalStats/GlobalStats.module.scss"],"names":[],"mappings":"AAAA,yCAAa,YAAY,CAAC,6BAA6B,CAAC,yBAAmB,CAAnB,oBAAoB","sourcesContent":[".GlobalStats{display:grid;grid-template-columns:1fr 1fr;gap:var(--spacing-s)}"],"sourceRoot":""}]);
// Exports
___CSS_LOADER_EXPORT___.locals = {
	"GlobalStats": "GlobalStats-module__GlobalStats___hg8Y9"
};
/* harmony default export */ const __WEBPACK_DEFAULT_EXPORT__ = (___CSS_LOADER_EXPORT___);


/***/ }),

/***/ 4176:
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
___CSS_LOADER_EXPORT___.push([module.id, ".MainView-module__MainLinks___SfV8S{position:absolute;width:calc(100% - 24px);bottom:12px}", "",{"version":3,"sources":["webpack://./Extension/src/pages/popup/components/MainView/MainView.module.scss"],"names":[],"mappings":"AAAA,oCAAW,iBAAiB,CAAC,uBAAuB,CAAC,WAAW","sourcesContent":[".MainLinks{position:absolute;width:calc(100% - 24px);bottom:12px}"],"sourceRoot":""}]);
// Exports
___CSS_LOADER_EXPORT___.locals = {
	"MainLinks": "MainView-module__MainLinks___SfV8S"
};
/* harmony default export */ const __WEBPACK_DEFAULT_EXPORT__ = (___CSS_LOADER_EXPORT___);


/***/ }),

/***/ 5889:
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
___CSS_LOADER_EXPORT___.push([module.id, ".PermissionMissing-module__PermissionMissing___epu8y{border-color:var(--grey-500) !important;background-color:var(--grey-100) !important}.PermissionMissing-module__PermissionMissing___epu8y{display:grid;grid-template-columns:72px 1fr;grid-gap:var(--spacing-s);gap:var(--spacing-s)}.PermissionMissing-module__PermissionMissing___epu8y .PermissionMissing-module__PermissionMissingShield___cFf24{width:70px;height:70px}.PermissionMissing-module__PermissionMissingAction___Be3Oa{grid-column:1/-1}.PermissionMissing-module__MainLinks___zdTsU{position:absolute;width:calc(100% - 24px);bottom:12px}", "",{"version":3,"sources":["webpack://./Extension/src/pages/popup/components/MainView/PermissionsMissing/PermissionMissing.module.scss"],"names":[],"mappings":"AAAA,qDAAoF,uCAAuC,CAAC,2CAA2C,CAAvK,qDAAmB,YAAY,CAAC,8BAA8B,CAAC,yBAAoB,CAApB,oBAAwG,CAAC,gHAA4C,UAAU,CAAC,WAAW,CAAC,2DAAyB,gBAAgB,CAAC,6CAAW,iBAAiB,CAAC,uBAAuB,CAAC,WAAW","sourcesContent":[".PermissionMissing{display:grid;grid-template-columns:72px 1fr;gap:var(--spacing-s);border-color:var(--grey-500) !important;background-color:var(--grey-100) !important}.PermissionMissing .PermissionMissingShield{width:70px;height:70px}.PermissionMissingAction{grid-column:1/-1}.MainLinks{position:absolute;width:calc(100% - 24px);bottom:12px}"],"sourceRoot":""}]);
// Exports
___CSS_LOADER_EXPORT___.locals = {
	"PermissionMissing": "PermissionMissing-module__PermissionMissing___epu8y",
	"PermissionMissingShield": "PermissionMissing-module__PermissionMissingShield___cFf24",
	"PermissionMissingAction": "PermissionMissing-module__PermissionMissingAction___Be3Oa",
	"MainLinks": "PermissionMissing-module__MainLinks___zdTsU"
};
/* harmony default export */ const __WEBPACK_DEFAULT_EXPORT__ = (___CSS_LOADER_EXPORT___);


/***/ }),

/***/ 4793:
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
___CSS_LOADER_EXPORT___.push([module.id, ".ProtectionStatus-module__ProtectionStatus___pbv9z{border-color:var(--color) !important;background-color:var(--green-100) !important}.ProtectionStatus-module__ProtectionStatus___pbv9z{cursor:pointer;padding-top:32px;--color: var(--green-500)}.ProtectionStatus-module__ProtectionStatusDetail___VOavv{position:absolute;top:var(--spacing-s);right:var(--spacing-s);gap:var(--spacing-xxs);line-height:1}.ProtectionStatus-module__ProtectionStatusDetail___VOavv *{line-height:1}.ProtectionStatus-module__ProtectionStatusDetail___VOavv svg{transition:transform .3s}.ProtectionStatus-module__ProtectionStatusDetail___VOavv:hover svg{transform:translateX(var(--spacing-xxs))}.ProtectionStatus-module__ProtectionStatus___pbv9z button{width:100%;cursor:pointer}.ProtectionStatus-module__ProtectionStatusFooter___ktmfW{background-color:#fff;color:var(--color);border-top:1px solid var(--color);overflow:hidden;border-radius:0 0 8px 8px}.ProtectionStatus-module__ProtectionStatus___pbv9z .ProtectionStatus-module__ProtectionStatusSwitch___kcXDJ,.ProtectionStatus-module__ProtectionStatus___pbv9z .ProtectionStatus-module__ProtectionStatusSwitch___kcXDJ span{width:48px;height:28px}.ProtectionStatus-module__ProtectionStatus___pbv9z .ProtectionStatus-module__ProtectionStatusSwitch___kcXDJ span{border-radius:48px;width:48px;height:28px;background-color:var(--color);border:none}.ProtectionStatus-module__ProtectionStatus___pbv9z .ProtectionStatus-module__ProtectionStatusSwitch___kcXDJ span::before{width:20px;height:20px;border-radius:20px;left:var(--spacing-xxs);top:var(--spacing-xxs)}.ProtectionStatus-module__ProtectionStatusShield___TVHea{position:absolute !important;cursor:pointer !important}.ProtectionStatus-module__ProtectionStatusShield___TVHea{left:calc(50% - 30px);top:-30px}.ProtectionStatus-module__ProtectionStatusDisabled___mf_OG{background-color:var(--grey-100) !important}.ProtectionStatus-module__ProtectionStatusDisabled___mf_OG{--color: var(--grey-500)}.ProtectionStatus-module__ProtectionStatusUnavailable___Pcnja{background-color:var(--red-100) !important}.ProtectionStatus-module__ProtectionStatusUnavailable___Pcnja{--color: var(--red-500)}", "",{"version":3,"sources":["webpack://./Extension/src/pages/popup/components/MainView/ProtectionStatus/ProtectionStatus.module.scss"],"names":[],"mappings":"AAAA,mDAA4E,oCAAoC,CAAC,4CAA4C,CAA7J,mDAAkB,cAAc,CAAC,gBAAgB,CAAC,yBAA2G,CAAC,yDAAwB,iBAAiB,CAAC,oBAAoB,CAAC,sBAAsB,CAAC,sBAAsB,CAAC,aAAa,CAAC,2DAA0B,aAAa,CAAC,6DAA4B,wBAAwB,CAAC,mEAAkC,wCAAwC,CAAC,0DAAyB,UAAU,CAAC,cAAc,CAAC,yDAAwB,qBAAqB,CAAC,kBAAkB,CAAC,iCAAiC,CAAC,eAAe,CAAC,yBAAyB,CAAC,6NAAyF,UAAU,CAAC,WAAW,CAAC,iHAA+C,kBAAkB,CAAC,UAAU,CAAC,WAAW,CAAC,6BAA6B,CAAC,WAAW,CAAC,yHAAuD,UAAU,CAAC,WAAW,CAAC,kBAAkB,CAAC,uBAAuB,CAAC,sBAAsB,CAAC,yDAAwB,4BAA4B,CAAiC,yBAAyB,CAA9G,yDAAqD,qBAAqB,CAAC,SAAmC,CAAC,2DAAmD,2CAA2C,CAA9F,2DAA0B,wBAAoE,CAAC,8DAAqD,0CAA0C,CAA/F,8DAA6B,uBAAkE","sourcesContent":[".ProtectionStatus{cursor:pointer;padding-top:32px;--color: var(--green-500);border-color:var(--color) !important;background-color:var(--green-100) !important}.ProtectionStatusDetail{position:absolute;top:var(--spacing-s);right:var(--spacing-s);gap:var(--spacing-xxs);line-height:1}.ProtectionStatusDetail *{line-height:1}.ProtectionStatusDetail svg{transition:transform .3s}.ProtectionStatusDetail:hover svg{transform:translateX(var(--spacing-xxs))}.ProtectionStatus button{width:100%;cursor:pointer}.ProtectionStatusFooter{background-color:#fff;color:var(--color);border-top:1px solid var(--color);overflow:hidden;border-radius:0 0 8px 8px}.ProtectionStatus .ProtectionStatusSwitch,.ProtectionStatus .ProtectionStatusSwitch span{width:48px;height:28px}.ProtectionStatus .ProtectionStatusSwitch span{border-radius:48px;width:48px;height:28px;background-color:var(--color);border:none}.ProtectionStatus .ProtectionStatusSwitch span::before{width:20px;height:20px;border-radius:20px;left:var(--spacing-xxs);top:var(--spacing-xxs)}.ProtectionStatusShield{position:absolute !important;left:calc(50% - 30px);top:-30px;cursor:pointer !important}.ProtectionStatusDisabled{--color: var(--grey-500);background-color:var(--grey-100) !important}.ProtectionStatusUnavailable{--color: var(--red-500);background-color:var(--red-100) !important}"],"sourceRoot":""}]);
// Exports
___CSS_LOADER_EXPORT___.locals = {
	"ProtectionStatus": "ProtectionStatus-module__ProtectionStatus___pbv9z",
	"ProtectionStatusDetail": "ProtectionStatus-module__ProtectionStatusDetail___VOavv",
	"ProtectionStatusFooter": "ProtectionStatus-module__ProtectionStatusFooter___ktmfW",
	"ProtectionStatusSwitch": "ProtectionStatus-module__ProtectionStatusSwitch___kcXDJ",
	"ProtectionStatusShield": "ProtectionStatus-module__ProtectionStatusShield___TVHea",
	"ProtectionStatusDisabled": "ProtectionStatus-module__ProtectionStatusDisabled___mf_OG",
	"ProtectionStatusUnavailable": "ProtectionStatus-module__ProtectionStatusUnavailable___Pcnja"
};
/* harmony default export */ const __WEBPACK_DEFAULT_EXPORT__ = (___CSS_LOADER_EXPORT___);


/***/ }),

/***/ 8501:
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
___CSS_LOADER_EXPORT___.push([module.id, ".ShieldCount-module__IconShield____8vLL{position:relative;width:60px;height:68px}.ShieldCount-module__IconShieldCount___wNUQx,.ShieldCount-module__IconShield____8vLL svg{position:absolute;top:0;right:0;bottom:0;left:0;width:100%;height:100%}.ShieldCount-module__IconShieldCount___wNUQx{display:grid;letter-spacing:.5px;align-items:center;justify-items:center;place-items:center;padding-bottom:3px}", "",{"version":3,"sources":["webpack://./Extension/src/pages/popup/components/MainView/ShieldCount/ShieldCount.module.scss"],"names":[],"mappings":"AAAA,wCAAY,iBAAiB,CAAC,UAAU,CAAC,WAAW,CAAC,yFAAiC,iBAAiB,CAAC,KAAO,CAAP,OAAO,CAAP,QAAO,CAAP,MAAO,CAAC,UAAU,CAAC,WAAW,CAAC,6CAAiB,YAAY,CAAC,mBAAmB,CAAC,kBAAkB,CAAlB,oBAAkB,CAAlB,kBAAkB,CAAC,kBAAkB","sourcesContent":[".IconShield{position:relative;width:60px;height:68px}.IconShieldCount,.IconShield svg{position:absolute;inset:0;width:100%;height:100%}.IconShieldCount{display:grid;letter-spacing:.5px;place-items:center;padding-bottom:3px}"],"sourceRoot":""}]);
// Exports
___CSS_LOADER_EXPORT___.locals = {
	"IconShield": "ShieldCount-module__IconShield____8vLL",
	"IconShieldCount": "ShieldCount-module__IconShieldCount___wNUQx"
};
/* harmony default export */ const __WEBPACK_DEFAULT_EXPORT__ = (___CSS_LOADER_EXPORT___);


/***/ }),

/***/ 5290:
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
___CSS_LOADER_EXPORT___.push([module.id, ".ThinCardLink-module__ThinCardLink___b1c3H svg{transition:transform .3s}.ThinCardLink-module__ThinCardLink___b1c3H:hover svg{transform:translateX(var(--spacing-xxs))}", "",{"version":3,"sources":["webpack://./Extension/src/pages/popup/components/shared/ThinCardLink/ThinCardLink.module.scss"],"names":[],"mappings":"AAAA,+CAAkB,wBAAwB,CAAC,qDAAwB,wCAAwC","sourcesContent":[".ThinCardLink svg{transition:transform .3s}.ThinCardLink:hover svg{transform:translateX(var(--spacing-xxs))}"],"sourceRoot":""}]);
// Exports
___CSS_LOADER_EXPORT___.locals = {
	"ThinCardLink": "ThinCardLink-module__ThinCardLink___b1c3H"
};
/* harmony default export */ const __WEBPACK_DEFAULT_EXPORT__ = (___CSS_LOADER_EXPORT___);


/***/ })

}]);