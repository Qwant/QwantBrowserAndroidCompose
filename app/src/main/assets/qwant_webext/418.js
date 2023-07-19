"use strict";
(self["webpackChunkqwant_viprivacy"] = self["webpackChunkqwant_viprivacy"] || []).push([[418],{

/***/ 3809:
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {


// EXPORTS
__webpack_require__.d(__webpack_exports__, {
  "Z": () => (/* binding */ Card)
});

// UNUSED EXPORTS: CardFooter

// EXTERNAL MODULE: ./node_modules/@babel/runtime/helpers/esm/extends.js
var esm_extends = __webpack_require__(5058);
// EXTERNAL MODULE: ./node_modules/classnames/index.js
var classnames = __webpack_require__(1613);
var classnames_default = /*#__PURE__*/__webpack_require__.n(classnames);
// EXTERNAL MODULE: ./node_modules/prop-types/index.js
var prop_types = __webpack_require__(1527);
var prop_types_default = /*#__PURE__*/__webpack_require__.n(prop_types);
// EXTERNAL MODULE: ./node_modules/react/index.js
var react = __webpack_require__(5506);
// EXTERNAL MODULE: ./node_modules/@qwant/qwant-ponents/src/Box/Box.js + 1 modules
var Box = __webpack_require__(5845);
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
// EXTERNAL MODULE: ./node_modules/css-loader/dist/cjs.js??ruleSet[1].rules[6].use[1]!./node_modules/postcss-loader/dist/cjs.js!./node_modules/sass-loader/dist/cjs.js!./node_modules/@qwant/qwant-ponents/src/Ripple/Ripple.module.scss
var Ripple_module = __webpack_require__(9139);
;// CONCATENATED MODULE: ./node_modules/@qwant/qwant-ponents/src/Ripple/Ripple.module.scss

      
      
      
      
      
      
      
      
      

var options = {};

options.styleTagTransform = (styleTagTransform_default());
options.setAttributes = (setAttributesWithoutAttributes_default());

      options.insert = insertBySelector_default().bind(null, "head");
    
options.domAPI = (styleDomAPI_default());
options.insertStyleElement = (insertStyleElement_default());

var update = injectStylesIntoStyleTag_default()(Ripple_module/* default */.Z, options);




       /* harmony default export */ const Ripple_Ripple_module = (Ripple_module/* default */.Z && Ripple_module/* default.locals */.Z.locals ? Ripple_module/* default.locals */.Z.locals : undefined);

;// CONCATENATED MODULE: ./node_modules/@qwant/qwant-ponents/src/Ripple/Ripple.jsx




/**
 * Creates a ripple effect on the children element on click
 *
 * ## Usage
 *
 * ```jsx
 * <Ripple>
 *   <Card style={{ width: 100, height: 100 }} />
 * </Ripple>
 * ```
 */

function Ripple({
  children,
  enabled,
  variant
}) {
  if (!enabled || typeof children !== 'object' || ! /*#__PURE__*/react.isValidElement(children)) {
    return children;
  }

  const ripple = e => {
    const elementOffset = e.currentTarget.getBoundingClientRect();
    const x = e.pageX - elementOffset.x - window.scrollX;
    const y = e.pageY - elementOffset.y - window.scrollY;
    const w = e.currentTarget.offsetWidth * 1.5;
    const ripple = document.createElement('span');
    ripple.className = Ripple_Ripple_module.Ripple;
    ripple.style.left = (x - w * 0.5).toString() + 'px';
    ripple.style.top = (y - w * 0.5).toString() + 'px';
    ripple.style.width = w + 'px';
    ripple.style.height = w + 'px';
    e.currentTarget.appendChild(ripple);
    setTimeout(() => {
      if (ripple.parentNode) {
        ripple.parentNode.removeChild(ripple);
      }
    }, 500);
  };

  return /*#__PURE__*/react.cloneElement(children, {
    onMouseDown: ripple,
    className: classnames_default()(children.props.className, Ripple_Ripple_module.RippleContainer, variant === 'light' && Ripple_Ripple_module.RippleContainerLight)
  });
}
Ripple.propTypes = {
  children: (prop_types_default()).node.isRequired,
  enabled: (prop_types_default()).bool.isRequired,
  variant: prop_types_default().oneOf(['light'])
};
// EXTERNAL MODULE: ./node_modules/css-loader/dist/cjs.js??ruleSet[1].rules[6].use[1]!./node_modules/postcss-loader/dist/cjs.js!./node_modules/sass-loader/dist/cjs.js!./node_modules/@qwant/qwant-ponents/src/Card/Card.module.scss
var Card_module = __webpack_require__(3122);
;// CONCATENATED MODULE: ./node_modules/@qwant/qwant-ponents/src/Card/Card.module.scss

      
      
      
      
      
      
      
      
      

var Card_module_options = {};

Card_module_options.styleTagTransform = (styleTagTransform_default());
Card_module_options.setAttributes = (setAttributesWithoutAttributes_default());

      Card_module_options.insert = insertBySelector_default().bind(null, "head");
    
Card_module_options.domAPI = (styleDomAPI_default());
Card_module_options.insertStyleElement = (insertStyleElement_default());

var Card_module_update = injectStylesIntoStyleTag_default()(Card_module/* default */.Z, Card_module_options);




       /* harmony default export */ const Card_Card_module = (Card_module/* default */.Z && Card_module/* default.locals */.Z.locals ? Card_module/* default.locals */.Z.locals : undefined);

;// CONCATENATED MODULE: ./node_modules/@qwant/qwant-ponents/src/Card/Card.jsx







const Card = /*#__PURE__*/(0,react.forwardRef)(({
  depth = 1,
  noRadius = false,
  hoverable,
  hoverableGrey,
  ripple = false,
  className,
  selected,
  ...props
}, ref) => {
  return /*#__PURE__*/react.createElement(Ripple, {
    enabled: ripple
  }, /*#__PURE__*/react.createElement(Box/* Box */.x, (0,esm_extends/* default */.Z)({
    ref: ref,
    className: classnames_default()(Card_Card_module.Card, Card_Card_module[`Depth${depth}`], selected && Card_Card_module.CardSelected, (hoverable || hoverableGrey) && Card_Card_module.CardHoverable, hoverableGrey && Card_Card_module.CardHoverableGrey, noRadius && Card_Card_module.CardNoRadius, ripple && Card_Card_module.CardWithRipple, className)
  }, props)));
});
function CardFooter({
  children,
  ...props
}) {
  return /*#__PURE__*/react.createElement(Box/* Box */.x, (0,esm_extends/* default */.Z)({
    p: "m",
    className: Card_Card_module.CardFooter
  }, props), children);
}
Card.displayName = 'Card';
Card.propTypes = {
  /* Depth of the card */
  depth: prop_types_default().oneOf([0, 1, 2]),

  /* Card is hoverable */
  hoverable: (prop_types_default()).bool,

  /* Card is hoverable with grey shadow */
  hoverableGrey: (prop_types_default()).bool,

  /* Additional class */
  className: (prop_types_default()).string,

  /* No border radius */
  noRadius: (prop_types_default()).bool,

  /* Ripple effect on click */
  ripple: (prop_types_default()).bool,

  /* Blue border around the box */
  selected: (prop_types_default()).bool,

  /* All box props work on this component */
  ...Box/* Box.propTypes */.x.propTypes
};
CardFooter.propTypes = {
  children: (prop_types_default()).node
};

/***/ }),

/***/ 1981:
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {


// EXPORTS
__webpack_require__.d(__webpack_exports__, {
  "K": () => (/* binding */ Stack)
});

// EXTERNAL MODULE: ./node_modules/@babel/runtime/helpers/esm/extends.js
var esm_extends = __webpack_require__(5058);
// EXTERNAL MODULE: ./node_modules/classnames/index.js
var classnames = __webpack_require__(1613);
var classnames_default = /*#__PURE__*/__webpack_require__.n(classnames);
// EXTERNAL MODULE: ./node_modules/prop-types/index.js
var prop_types = __webpack_require__(1527);
var prop_types_default = /*#__PURE__*/__webpack_require__.n(prop_types);
// EXTERNAL MODULE: ./node_modules/react/index.js
var react = __webpack_require__(5506);
// EXTERNAL MODULE: ./node_modules/@qwant/qwant-ponents/src/Box/Box.js + 1 modules
var Box = __webpack_require__(5845);
// EXTERNAL MODULE: ./node_modules/@qwant/qwant-ponents/src/PropTypes.js
var PropTypes = __webpack_require__(1289);
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
// EXTERNAL MODULE: ./node_modules/css-loader/dist/cjs.js??ruleSet[1].rules[6].use[1]!./node_modules/postcss-loader/dist/cjs.js!./node_modules/sass-loader/dist/cjs.js!./node_modules/@qwant/qwant-ponents/src/Stack/Stack.module.scss
var Stack_module = __webpack_require__(106);
;// CONCATENATED MODULE: ./node_modules/@qwant/qwant-ponents/src/Stack/Stack.module.scss

      
      
      
      
      
      
      
      
      

var options = {};

options.styleTagTransform = (styleTagTransform_default());
options.setAttributes = (setAttributesWithoutAttributes_default());

      options.insert = insertBySelector_default().bind(null, "head");
    
options.domAPI = (styleDomAPI_default());
options.insertStyleElement = (insertStyleElement_default());

var update = injectStylesIntoStyleTag_default()(Stack_module/* default */.Z, options);




       /* harmony default export */ const Stack_Stack_module = (Stack_module/* default */.Z && Stack_module/* default.locals */.Z.locals ? Stack_module/* default.locals */.Z.locals : undefined);

;// CONCATENATED MODULE: ./node_modules/@qwant/qwant-ponents/src/Stack/Stack.js







/**
 * Space children evenly horizontally or vertically
 *
 * ## Example
 *
 * ```
 * <Stack gap="xl2">
 *     <Button>Button 1</Button>
 *     <Button>Button 2</Button>
 *     <Button>Button 3</Button>
 * </Stack>
 * ```
 */

function Stack({
  children,
  className,
  horizontal,
  gap,
  nowrap,
  alignCenter,
  center,
  end,
  middle,
  baseline,
  ...props
}) {
  return /*#__PURE__*/react.createElement(Box/* Box */.x, (0,esm_extends/* default */.Z)({
    className: classnames_default()(horizontal ? Stack_Stack_module.HorizontalStack : Stack_Stack_module.VerticalStack, nowrap && Stack_Stack_module.noWrap, center && Stack_Stack_module.alignCenter, baseline && Stack_Stack_module.alignBaseline, middle && Stack_Stack_module.justifyCenter, end && Stack_Stack_module.end, gap && Stack_Stack_module[`Space${gap}`], className)
  }, props), children);
}
Stack.propTypes = {
  children: (prop_types_default()).node.isRequired,

  /** Align item horizontaly or verticaly **/
  horizontal: (prop_types_default()).bool,

  /** Space between every children **/
  gap: prop_types_default().oneOf(PropTypes/* spaces */.RZ),
  className: (prop_types_default()).string,

  /** Align elements vertically when the stack is horizontal */
  center: (prop_types_default()).bool,

  /** Align elements on the baseline when the stack is horizontal */
  baseline: (prop_types_default()).bool,

  /** Justify center when the stack is horizontal */
  middle: (prop_types_default()).bool,

  /** Align elements at the end of the element */
  end: (prop_types_default()).bool,
  alignCenter: (prop_types_default()).bool,
  ...Box/* Box.propTypes */.x.propTypes
};

/***/ }),

/***/ 3122:
/***/ ((module, __webpack_exports__, __webpack_require__) => {

/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "Z": () => (__WEBPACK_DEFAULT_EXPORT__)
/* harmony export */ });
/* harmony import */ var _css_loader_dist_runtime_sourceMaps_js__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(1091);
/* harmony import */ var _css_loader_dist_runtime_sourceMaps_js__WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(_css_loader_dist_runtime_sourceMaps_js__WEBPACK_IMPORTED_MODULE_0__);
/* harmony import */ var _css_loader_dist_runtime_api_js__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(4090);
/* harmony import */ var _css_loader_dist_runtime_api_js__WEBPACK_IMPORTED_MODULE_1___default = /*#__PURE__*/__webpack_require__.n(_css_loader_dist_runtime_api_js__WEBPACK_IMPORTED_MODULE_1__);
// Imports


var ___CSS_LOADER_EXPORT___ = _css_loader_dist_runtime_api_js__WEBPACK_IMPORTED_MODULE_1___default()((_css_loader_dist_runtime_sourceMaps_js__WEBPACK_IMPORTED_MODULE_0___default()));
// Module
___CSS_LOADER_EXPORT___.push([module.id, ":root{--cardRadius: 8px;--cardBorderColor: var(--grey-100);--cardShadowGrey: 4px 4px 0 0 var(--grey-900-alpha16);--cardShadowBlue: 4px 4px 0 0 var(--action-blue-200);--depth1: 0;--depth2: var(--cardShadowGrey)}[data-theme=dark]{--cardShadowGrey: 4px 4px 0 0 var(--grey-900-alpha16);--cardBorderColor: var(--grey-700);--depth1: 0;--depth2: var(--cardShadowGrey)}@media(color-index: 48){body:not([data-theme=light]){--cardShadowGrey: 4px 4px 0 0 var(--grey-900-alpha16);--cardBorderColor: var(--grey-700);--depth1: 0;--depth2: var(--cardShadowGrey)}}@media(color: 48842621){body:not([data-theme=light]){--cardShadowGrey: 4px 4px 0 0 var(--grey-900-alpha16);--cardBorderColor: var(--grey-700);--depth1: 0;--depth2: var(--cardShadowGrey)}}@media(prefers-color-scheme: dark){body:not([data-theme=light]){--cardShadowGrey: 4px 4px 0 0 var(--grey-900-alpha16);--cardBorderColor: var(--grey-700);--depth1: 0;--depth2: var(--cardShadowGrey)}}.Card-module__Card___mvB2p{border-radius:8px;border-radius:var(--cardRadius);border:2px solid var(--grey-100);border:2px solid var(--cardBorderColor);transition:box-shadow .3s,background .3s,border .3s}.Card-module__CardWithRipple___97PIF{overflow:hidden}.Card-module__CardNoRadius___xR1LG{border-radius:0}.Card-module__Depth1____aMrD{box-shadow:0;box-shadow:var(--depth1);background-color:var(--grey-000);border-color:var(--grey-100)}[data-theme=dark] .Card-module__Depth1____aMrD{background-color:var(--grey-800);border-color:var(--grey-700)}@media(color-index: 48){body:not([data-theme=light]) .Card-module__Depth1____aMrD{background-color:var(--grey-800);border-color:var(--grey-700)}}@media(color: 48842621){body:not([data-theme=light]) .Card-module__Depth1____aMrD{background-color:var(--grey-800);border-color:var(--grey-700)}}@media(prefers-color-scheme: dark){body:not([data-theme=light]) .Card-module__Depth1____aMrD{background-color:var(--grey-800);border-color:var(--grey-700)}}.Card-module__Depth2___ZFJFZ{box-shadow:4px 4px 0 0 var(--grey-900-alpha16);box-shadow:var(--depth2);background-color:var(--grey-000);border-color:var(--grey-600)}[data-theme=dark] .Card-module__Depth2___ZFJFZ{background-color:var(--grey-700);border-color:var(--grey-600)}@media(color-index: 48){body:not([data-theme=light]) .Card-module__Depth2___ZFJFZ{background-color:var(--grey-700);border-color:var(--grey-600)}}@media(color: 48842621){body:not([data-theme=light]) .Card-module__Depth2___ZFJFZ{background-color:var(--grey-700);border-color:var(--grey-600)}}@media(prefers-color-scheme: dark){body:not([data-theme=light]) .Card-module__Depth2___ZFJFZ{background-color:var(--grey-700);border-color:var(--grey-600)}}.Card-module__CardHoverable___NqiXk:hover{border-color:var(--grey-600);box-shadow:4px 4px 0 0 var(--action-blue-200);box-shadow:var(--cardShadowBlue)}[data-theme=dark] .Card-module__CardHoverable___NqiXk:hover{border-color:var(--grey-600);background-color:var(--grey-700)}@media(color-index: 48){body:not([data-theme=light]) .Card-module__CardHoverable___NqiXk:hover{border-color:var(--grey-600);background-color:var(--grey-700)}}@media(color: 48842621){body:not([data-theme=light]) .Card-module__CardHoverable___NqiXk:hover{border-color:var(--grey-600);background-color:var(--grey-700)}}@media(prefers-color-scheme: dark){body:not([data-theme=light]) .Card-module__CardHoverable___NqiXk:hover{border-color:var(--grey-600);background-color:var(--grey-700)}}.Card-module__CardHoverableGrey___llTlI:hover{border-color:var(--grey-600);box-shadow:4px 4px 0 0 var(--grey-900-alpha16);box-shadow:var(--cardShadowGrey)}[data-theme=dark] .Card-module__CardHoverableGrey___llTlI:hover{background-color:var(--grey-700)}@media(color-index: 48){body:not([data-theme=light]) .Card-module__CardHoverableGrey___llTlI:hover{background-color:var(--grey-700)}}@media(color: 48842621){body:not([data-theme=light]) .Card-module__CardHoverableGrey___llTlI:hover{background-color:var(--grey-700)}}@media(prefers-color-scheme: dark){body:not([data-theme=light]) .Card-module__CardHoverableGrey___llTlI:hover{background-color:var(--grey-700)}}.Card-module__CardSelected___ZtpvT{box-shadow:0 0 0 4px var(--action-blue-400-alpha20) !important}.Card-module__CardSelected___ZtpvT{border-color:var(--action-blue-400)}[data-theme=dark] .Card-module__CardSelected___ZtpvT{box-shadow:0 0 0 4px var(--action-blue-200-alpha20) !important}[data-theme=dark] .Card-module__CardSelected___ZtpvT{border-color:var(--action-blue-200)}@media(color-index: 48){body:not([data-theme=light]) .Card-module__CardSelected___ZtpvT{box-shadow:0 0 0 4px var(--action-blue-200-alpha20) !important}body:not([data-theme=light]) .Card-module__CardSelected___ZtpvT{border-color:var(--action-blue-200)}}@media(color: 48842621){body:not([data-theme=light]) .Card-module__CardSelected___ZtpvT{box-shadow:0 0 0 4px var(--action-blue-200-alpha20) !important}body:not([data-theme=light]) .Card-module__CardSelected___ZtpvT{border-color:var(--action-blue-200)}}@media(prefers-color-scheme: dark){body:not([data-theme=light]) .Card-module__CardSelected___ZtpvT{box-shadow:0 0 0 4px var(--action-blue-200-alpha20) !important}body:not([data-theme=light]) .Card-module__CardSelected___ZtpvT{border-color:var(--action-blue-200)}}.Card-module__CardFooter___oOP1W{border-top:2px solid var(--grey-200)}.Card-module__CardFooter___oOP1W a:hover{text-decoration:underline}[data-theme=dark] .Card-module__CardFooter___oOP1W{border-color:var(--grey-700)}@media(color-index: 48){body:not([data-theme=light]) .Card-module__CardFooter___oOP1W{border-color:var(--grey-700)}}@media(color: 48842621){body:not([data-theme=light]) .Card-module__CardFooter___oOP1W{border-color:var(--grey-700)}}@media(prefers-color-scheme: dark){body:not([data-theme=light]) .Card-module__CardFooter___oOP1W{border-color:var(--grey-700)}}", "",{"version":3,"sources":["webpack://./node_modules/@qwant/qwant-ponents/src/Card/Card.module.scss"],"names":[],"mappings":"AAAA,MAAM,iBAAiB,CAAC,kCAAkC,CAAC,qDAAqD,CAAC,oDAAoD,CAAC,WAAW,CAAC,+BAA+B,CAAC,kBAAkB,qDAAqD,CAAC,kCAAkC,CAAC,WAAW,CAAC,+BAA+B,CAAC,wBAAmC,6BAA6B,qDAAqD,CAAC,kCAAkC,CAAC,WAAW,CAAC,+BAA+B,CAAC,CAArM,wBAAmC,6BAA6B,qDAAqD,CAAC,kCAAkC,CAAC,WAAW,CAAC,+BAA+B,CAAC,CAArM,mCAAmC,6BAA6B,qDAAqD,CAAC,kCAAkC,CAAC,WAAW,CAAC,+BAA+B,CAAC,CAAC,2BAAM,iBAA+B,CAA/B,+BAA+B,CAAC,gCAAuC,CAAvC,uCAAuC,CAAC,mDAAmD,CAAC,qCAAgB,eAAe,CAAC,mCAAc,eAAe,CAAC,6BAAQ,YAAwB,CAAxB,wBAAwB,CAAC,gCAAgC,CAAC,4BAA4B,CAAC,+CAA0B,gCAAgC,CAAC,4BAA4B,CAAC,wBAAmC,0DAAqC,gCAAgC,CAAC,4BAA4B,CAAC,CAAtI,wBAAmC,0DAAqC,gCAAgC,CAAC,4BAA4B,CAAC,CAAtI,mCAAmC,0DAAqC,gCAAgC,CAAC,4BAA4B,CAAC,CAAC,6BAAQ,8CAAwB,CAAxB,wBAAwB,CAAC,gCAAgC,CAAC,4BAA4B,CAAC,+CAA0B,gCAAgC,CAAC,4BAA4B,CAAC,wBAAmC,0DAAqC,gCAAgC,CAAC,4BAA4B,CAAC,CAAtI,wBAAmC,0DAAqC,gCAAgC,CAAC,4BAA4B,CAAC,CAAtI,mCAAmC,0DAAqC,gCAAgC,CAAC,4BAA4B,CAAC,CAAC,0CAAqB,4BAA4B,CAAC,6CAA+B,CAA/B,gCAAgC,CAAC,4DAAuC,4BAA4B,CAAC,gCAAgC,CAAC,wBAAmC,uEAAkD,4BAA4B,CAAC,gCAAgC,CAAC,CAAnJ,wBAAmC,uEAAkD,4BAA4B,CAAC,gCAAgC,CAAC,CAAnJ,mCAAmC,uEAAkD,4BAA4B,CAAC,gCAAgC,CAAC,CAAC,8CAAyB,4BAA4B,CAAC,8CAA+B,CAA/B,gCAAgC,CAAC,gEAA2C,gCAAgC,CAAC,wBAAmC,2EAAsD,gCAAgC,CAAC,CAA1H,wBAAmC,2EAAsD,gCAAgC,CAAC,CAA1H,mCAAmC,2EAAsD,gCAAgC,CAAC,CAAC,mCAAkD,8DAA8D,CAAhH,mCAAc,mCAAkG,CAAC,qDAAoE,8DAA8D,CAAlI,qDAAgC,mCAAkG,CAAC,wBAAmC,gEAA+E,8DAA8D,CAA7I,gEAA2C,mCAAkG,CAAC,CAAjL,wBAAmC,gEAA+E,8DAA8D,CAA7I,gEAA2C,mCAAkG,CAAC,CAAjL,mCAAmC,gEAA+E,8DAA8D,CAA7I,gEAA2C,mCAAkG,CAAC,CAAC,iCAAY,oCAAoC,CAAC,yCAAoB,yBAAyB,CAAC,mDAA8B,4BAA4B,CAAC,wBAAmC,8DAAyC,4BAA4B,CAAC,CAAzG,wBAAmC,8DAAyC,4BAA4B,CAAC,CAAzG,mCAAmC,8DAAyC,4BAA4B,CAAC","sourcesContent":[":root{--cardRadius: 8px;--cardBorderColor: var(--grey-100);--cardShadowGrey: 4px 4px 0 0 var(--grey-900-alpha16);--cardShadowBlue: 4px 4px 0 0 var(--action-blue-200);--depth1: 0;--depth2: var(--cardShadowGrey)}[data-theme=dark]{--cardShadowGrey: 4px 4px 0 0 var(--grey-900-alpha16);--cardBorderColor: var(--grey-700);--depth1: 0;--depth2: var(--cardShadowGrey)}@media(prefers-color-scheme: dark){body:not([data-theme=light]){--cardShadowGrey: 4px 4px 0 0 var(--grey-900-alpha16);--cardBorderColor: var(--grey-700);--depth1: 0;--depth2: var(--cardShadowGrey)}}.Card{border-radius:var(--cardRadius);border:2px solid var(--cardBorderColor);transition:box-shadow .3s,background .3s,border .3s}.CardWithRipple{overflow:hidden}.CardNoRadius{border-radius:0}.Depth1{box-shadow:var(--depth1);background-color:var(--grey-000);border-color:var(--grey-100)}[data-theme=dark] .Depth1{background-color:var(--grey-800);border-color:var(--grey-700)}@media(prefers-color-scheme: dark){body:not([data-theme=light]) .Depth1{background-color:var(--grey-800);border-color:var(--grey-700)}}.Depth2{box-shadow:var(--depth2);background-color:var(--grey-000);border-color:var(--grey-600)}[data-theme=dark] .Depth2{background-color:var(--grey-700);border-color:var(--grey-600)}@media(prefers-color-scheme: dark){body:not([data-theme=light]) .Depth2{background-color:var(--grey-700);border-color:var(--grey-600)}}.CardHoverable:hover{border-color:var(--grey-600);box-shadow:var(--cardShadowBlue)}[data-theme=dark] .CardHoverable:hover{border-color:var(--grey-600);background-color:var(--grey-700)}@media(prefers-color-scheme: dark){body:not([data-theme=light]) .CardHoverable:hover{border-color:var(--grey-600);background-color:var(--grey-700)}}.CardHoverableGrey:hover{border-color:var(--grey-600);box-shadow:var(--cardShadowGrey)}[data-theme=dark] .CardHoverableGrey:hover{background-color:var(--grey-700)}@media(prefers-color-scheme: dark){body:not([data-theme=light]) .CardHoverableGrey:hover{background-color:var(--grey-700)}}.CardSelected{border-color:var(--action-blue-400);box-shadow:0 0 0 4px var(--action-blue-400-alpha20) !important}[data-theme=dark] .CardSelected{border-color:var(--action-blue-200);box-shadow:0 0 0 4px var(--action-blue-200-alpha20) !important}@media(prefers-color-scheme: dark){body:not([data-theme=light]) .CardSelected{border-color:var(--action-blue-200);box-shadow:0 0 0 4px var(--action-blue-200-alpha20) !important}}.CardFooter{border-top:2px solid var(--grey-200)}.CardFooter a:hover{text-decoration:underline}[data-theme=dark] .CardFooter{border-color:var(--grey-700)}@media(prefers-color-scheme: dark){body:not([data-theme=light]) .CardFooter{border-color:var(--grey-700)}}"],"sourceRoot":""}]);
// Exports
___CSS_LOADER_EXPORT___.locals = {
	"Card": "Card-module__Card___mvB2p",
	"CardWithRipple": "Card-module__CardWithRipple___97PIF",
	"CardNoRadius": "Card-module__CardNoRadius___xR1LG",
	"Depth1": "Card-module__Depth1____aMrD",
	"Depth2": "Card-module__Depth2___ZFJFZ",
	"CardHoverable": "Card-module__CardHoverable___NqiXk",
	"CardHoverableGrey": "Card-module__CardHoverableGrey___llTlI",
	"CardSelected": "Card-module__CardSelected___ZtpvT",
	"CardFooter": "Card-module__CardFooter___oOP1W"
};
/* harmony default export */ const __WEBPACK_DEFAULT_EXPORT__ = (___CSS_LOADER_EXPORT___);


/***/ }),

/***/ 9139:
/***/ ((module, __webpack_exports__, __webpack_require__) => {

/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "Z": () => (__WEBPACK_DEFAULT_EXPORT__)
/* harmony export */ });
/* harmony import */ var _css_loader_dist_runtime_sourceMaps_js__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(1091);
/* harmony import */ var _css_loader_dist_runtime_sourceMaps_js__WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(_css_loader_dist_runtime_sourceMaps_js__WEBPACK_IMPORTED_MODULE_0__);
/* harmony import */ var _css_loader_dist_runtime_api_js__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(4090);
/* harmony import */ var _css_loader_dist_runtime_api_js__WEBPACK_IMPORTED_MODULE_1___default = /*#__PURE__*/__webpack_require__.n(_css_loader_dist_runtime_api_js__WEBPACK_IMPORTED_MODULE_1__);
// Imports


var ___CSS_LOADER_EXPORT___ = _css_loader_dist_runtime_api_js__WEBPACK_IMPORTED_MODULE_1___default()((_css_loader_dist_runtime_sourceMaps_js__WEBPACK_IMPORTED_MODULE_0___default()));
// Module
___CSS_LOADER_EXPORT___.push([module.id, ".Ripple-module__RippleContainer___tE0EK{overflow:hidden;position:relative}.Ripple-module__Ripple___zrb_x{width:10px;height:10px;position:absolute;overflow:hidden;border-radius:50%;pointer-events:none;background-image:linear-gradient(301deg, var(--grey-100) 90%, var(--grey-300) 17%);-webkit-animation:Ripple-module__rippleEffect___jwwTt .5s ease-in-out both;animation:Ripple-module__rippleEffect___jwwTt .5s ease-in-out both}.Ripple-module__RippleContainerLight___VXWHC .Ripple-module__Ripple___zrb_x{background-image:linear-gradient(301deg, var(--grey-000-alpha30) 90%, var(--grey-300-alpha20) 17%)}@-webkit-keyframes Ripple-module__rippleEffect___jwwTt{0%{opacity:0;transform:scale(0.05)}1%{opacity:.6;transform:scale(0)}100%{opacity:0;transform:scale(1)}}@keyframes Ripple-module__rippleEffect___jwwTt{0%{opacity:0;transform:scale(0.05)}1%{opacity:.6;transform:scale(0)}100%{opacity:0;transform:scale(1)}}", "",{"version":3,"sources":["webpack://./node_modules/@qwant/qwant-ponents/src/Ripple/Ripple.module.scss"],"names":[],"mappings":"AAAA,wCAAiB,eAAe,CAAC,iBAAiB,CAAC,+BAAQ,UAAU,CAAC,WAAW,CAAC,iBAAiB,CAAC,eAAe,CAAC,iBAAiB,CAAC,mBAAmB,CAAC,kFAAkF,CAAC,0EAA0C,CAA1C,kEAA2C,CAAC,4EAA8B,kGAAkG,CAAC,uDAAwB,GAAG,SAAS,CAAC,qBAAqB,CAAC,GAAG,UAAU,CAAC,kBAAkB,CAAC,KAAK,SAAS,CAAC,kBAAkB,CAAC,CAA9H,+CAAwB,GAAG,SAAS,CAAC,qBAAqB,CAAC,GAAG,UAAU,CAAC,kBAAkB,CAAC,KAAK,SAAS,CAAC,kBAAkB,CAAC","sourcesContent":[".RippleContainer{overflow:hidden;position:relative}.Ripple{width:10px;height:10px;position:absolute;overflow:hidden;border-radius:50%;pointer-events:none;background-image:linear-gradient(301deg, var(--grey-100) 90%, var(--grey-300) 17%);animation:rippleEffect .5s ease-in-out both}.RippleContainerLight .Ripple{background-image:linear-gradient(301deg, var(--grey-000-alpha30) 90%, var(--grey-300-alpha20) 17%)}@keyframes rippleEffect{0%{opacity:0;transform:scale(0.05)}1%{opacity:.6;transform:scale(0)}100%{opacity:0;transform:scale(1)}}"],"sourceRoot":""}]);
// Exports
___CSS_LOADER_EXPORT___.locals = {
	"RippleContainer": "Ripple-module__RippleContainer___tE0EK",
	"Ripple": "Ripple-module__Ripple___zrb_x",
	"rippleEffect": "Ripple-module__rippleEffect___jwwTt",
	"RippleContainerLight": "Ripple-module__RippleContainerLight___VXWHC"
};
/* harmony default export */ const __WEBPACK_DEFAULT_EXPORT__ = (___CSS_LOADER_EXPORT___);


/***/ }),

/***/ 106:
/***/ ((module, __webpack_exports__, __webpack_require__) => {

/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "Z": () => (__WEBPACK_DEFAULT_EXPORT__)
/* harmony export */ });
/* harmony import */ var _css_loader_dist_runtime_sourceMaps_js__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(1091);
/* harmony import */ var _css_loader_dist_runtime_sourceMaps_js__WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(_css_loader_dist_runtime_sourceMaps_js__WEBPACK_IMPORTED_MODULE_0__);
/* harmony import */ var _css_loader_dist_runtime_api_js__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(4090);
/* harmony import */ var _css_loader_dist_runtime_api_js__WEBPACK_IMPORTED_MODULE_1___default = /*#__PURE__*/__webpack_require__.n(_css_loader_dist_runtime_api_js__WEBPACK_IMPORTED_MODULE_1__);
// Imports


var ___CSS_LOADER_EXPORT___ = _css_loader_dist_runtime_api_js__WEBPACK_IMPORTED_MODULE_1___default()((_css_loader_dist_runtime_sourceMaps_js__WEBPACK_IMPORTED_MODULE_0___default()));
// Module
___CSS_LOADER_EXPORT___.push([module.id, ".Stack-module__HorizontalStack___ENS2T{display:flex;flex-wrap:wrap;width:calc(100% + var(--space));margin-left:calc(var(--space)*-0.5);margin-right:calc(var(--space)*-0.5);--space: 0}.Stack-module__HorizontalStack___ENS2T>*{margin-right:calc(var(--space)*.5);margin-left:calc(var(--space)*.5)}.Stack-module__alignCenter___kqj3K{align-items:center}.Stack-module__alignBaseline___EOzu0{align-items:baseline}.Stack-module__justifyCenter___DO2Zi{justify-content:center}.Stack-module__noWrap___Uh_wx{flex-wrap:nowrap}.Stack-module__end___DzLFU{justify-content:flex-end}.Stack-module__VerticalStack___QD6xu{display:grid;grid-template-columns:1fr;align-content:flex-start;grid-gap:var(--space);--space: 0}.Stack-module__VerticalStack___QD6xu>*{min-width:0}.Stack-module__VerticalStack___QD6xu.Stack-module__alignCenter___kqj3K{align-content:center}.Stack-module__VerticalStack___QD6xu.Stack-module__alignBaseline___EOzu0{align-content:baseline}.Stack-module__VerticalStack___QD6xu.Stack-module__end___DzLFU{align-content:flex-end}.Stack-module__Spacexxxs___ZdIsk{--space: var(--spacing-xxxs)}.Stack-module__Spacexxs___eqIIB{--space: var(--spacing-xxs)}.Stack-module__Spacexs___iB3af{--space: var(--spacing-xs)}.Stack-module__Spaces___sRhPi{--space: var(--spacing-s)}.Stack-module__Spacem___veTGe{--space: var(--spacing-m)}.Stack-module__Spacel___VuRr5{--space: var(--spacing-l)}.Stack-module__Spacexl___F3n2Y{--space: var(--spacing-xl)}.Stack-module__Spacexl2___hgM2O{--space: var(--spacing-xl-2)}.Stack-module__Spacexxl3___vEAjZ{--space: var(--spacing-xxl-3)}.Stack-module__Spacexxl4___i4C5_{--space: var(--spacing-xxl-4)}.Stack-module__Spacexxl5___bFm7Z{--space: var(--spacing-xxl-5)}.Stack-module__Spacexxl6___g0g4D{--space: var(--spacing-xxl-6)}.Stack-module__Spacexxl7___TKjX1{--space: var(--spacing-xxl-7)}.Stack-module__Spacexxl8___eLRV7{--space: var(--spacing-xxl-8)}", "",{"version":3,"sources":["webpack://./node_modules/@qwant/qwant-ponents/src/Stack/Stack.module.scss"],"names":[],"mappings":"AAAA,uCAAiB,YAAY,CAAC,cAAc,CAAC,+BAA+B,CAAC,mCAAmC,CAAC,oCAAoC,CAAC,UAAU,CAAC,yCAAmB,kCAAkC,CAAC,iCAAiC,CAAC,mCAAa,kBAAkB,CAAC,qCAAe,oBAAoB,CAAC,qCAAe,sBAAsB,CAAC,8BAAQ,gBAAgB,CAAC,2BAAK,wBAAwB,CAAC,qCAAe,YAAY,CAAC,yBAAyB,CAAC,wBAAwB,CAAC,qBAAqB,CAAC,UAAU,CAAC,uCAAiB,WAAW,CAAC,uEAA2B,oBAAoB,CAAC,yEAA6B,sBAAsB,CAAC,+DAAmB,sBAAsB,CAAC,iCAAW,4BAA4B,CAAC,gCAAU,2BAA2B,CAAC,+BAAS,0BAA0B,CAAC,8BAAQ,yBAAyB,CAAC,8BAAQ,yBAAyB,CAAC,8BAAQ,yBAAyB,CAAC,+BAAS,0BAA0B,CAAC,gCAAU,4BAA4B,CAAC,iCAAW,6BAA6B,CAAC,iCAAW,6BAA6B,CAAC,iCAAW,6BAA6B,CAAC,iCAAW,6BAA6B,CAAC,iCAAW,6BAA6B,CAAC,iCAAW,6BAA6B","sourcesContent":[".HorizontalStack{display:flex;flex-wrap:wrap;width:calc(100% + var(--space));margin-left:calc(var(--space)*-0.5);margin-right:calc(var(--space)*-0.5);--space: 0}.HorizontalStack>*{margin-right:calc(var(--space)*.5);margin-left:calc(var(--space)*.5)}.alignCenter{align-items:center}.alignBaseline{align-items:baseline}.justifyCenter{justify-content:center}.noWrap{flex-wrap:nowrap}.end{justify-content:flex-end}.VerticalStack{display:grid;grid-template-columns:1fr;align-content:flex-start;grid-gap:var(--space);--space: 0}.VerticalStack>*{min-width:0}.VerticalStack.alignCenter{align-content:center}.VerticalStack.alignBaseline{align-content:baseline}.VerticalStack.end{align-content:flex-end}.Spacexxxs{--space: var(--spacing-xxxs)}.Spacexxs{--space: var(--spacing-xxs)}.Spacexs{--space: var(--spacing-xs)}.Spaces{--space: var(--spacing-s)}.Spacem{--space: var(--spacing-m)}.Spacel{--space: var(--spacing-l)}.Spacexl{--space: var(--spacing-xl)}.Spacexl2{--space: var(--spacing-xl-2)}.Spacexxl3{--space: var(--spacing-xxl-3)}.Spacexxl4{--space: var(--spacing-xxl-4)}.Spacexxl5{--space: var(--spacing-xxl-5)}.Spacexxl6{--space: var(--spacing-xxl-6)}.Spacexxl7{--space: var(--spacing-xxl-7)}.Spacexxl8{--space: var(--spacing-xxl-8)}"],"sourceRoot":""}]);
// Exports
___CSS_LOADER_EXPORT___.locals = {
	"HorizontalStack": "Stack-module__HorizontalStack___ENS2T",
	"alignCenter": "Stack-module__alignCenter___kqj3K",
	"alignBaseline": "Stack-module__alignBaseline___EOzu0",
	"justifyCenter": "Stack-module__justifyCenter___DO2Zi",
	"noWrap": "Stack-module__noWrap___Uh_wx",
	"end": "Stack-module__end___DzLFU",
	"VerticalStack": "Stack-module__VerticalStack___QD6xu",
	"Spacexxxs": "Stack-module__Spacexxxs___ZdIsk",
	"Spacexxs": "Stack-module__Spacexxs___eqIIB",
	"Spacexs": "Stack-module__Spacexs___iB3af",
	"Spaces": "Stack-module__Spaces___sRhPi",
	"Spacem": "Stack-module__Spacem___veTGe",
	"Spacel": "Stack-module__Spacel___VuRr5",
	"Spacexl": "Stack-module__Spacexl___F3n2Y",
	"Spacexl2": "Stack-module__Spacexl2___hgM2O",
	"Spacexxl3": "Stack-module__Spacexxl3___vEAjZ",
	"Spacexxl4": "Stack-module__Spacexxl4___i4C5_",
	"Spacexxl5": "Stack-module__Spacexxl5___bFm7Z",
	"Spacexxl6": "Stack-module__Spacexxl6___g0g4D",
	"Spacexxl7": "Stack-module__Spacexxl7___TKjX1",
	"Spacexxl8": "Stack-module__Spacexxl8___eLRV7"
};
/* harmony default export */ const __WEBPACK_DEFAULT_EXPORT__ = (___CSS_LOADER_EXPORT___);


/***/ })

}]);