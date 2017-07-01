(function webpackUniversalModuleDefinition(root, factory) {
	if(typeof exports === 'object' && typeof module === 'object')
		module.exports = factory();
	else if(typeof define === 'function' && define.amd)
		define([], factory);
	else if(typeof exports === 'object')
		exports["echarts"] = factory();
	else
		root["echarts"] = factory();
})(this, function() {
return /******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};

/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {

/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId])
/******/ 			return installedModules[moduleId].exports;

/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			exports: {},
/******/ 			id: moduleId,
/******/ 			loaded: false
/******/ 		};

/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);

/******/ 		// Flag the module as loaded
/******/ 		module.loaded = true;

/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}


/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;

/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;

/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "";

/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(0);
/******/ })
/************************************************************************/
/******/ ([
/* 0 */
/***/ function(module, exports, __webpack_require__) {

	/**
	 * Export echarts as CommonJS module
	 */
	module.exports = __webpack_require__(1);

	// Import all charts and components
	__webpack_require__(99);
	__webpack_require__(133);
	__webpack_require__(138);
	__webpack_require__(147);
	__webpack_require__(151);

	__webpack_require__(161);
	__webpack_require__(182);
	__webpack_require__(194);
	__webpack_require__(215);
	__webpack_require__(219);
	__webpack_require__(223);
	__webpack_require__(238);
	__webpack_require__(244);
	__webpack_require__(251);
	__webpack_require__(257);
	__webpack_require__(261);
	__webpack_require__(269);

	__webpack_require__(112);
	__webpack_require__(273);
	__webpack_require__(279);
	__webpack_require__(283);
	__webpack_require__(294);
	__webpack_require__(224);
	__webpack_require__(297);
	__webpack_require__(303);

	__webpack_require__(315);

	__webpack_require__(316);
	__webpack_require__(330);

	__webpack_require__(345);
	__webpack_require__(351);
	__webpack_require__(354);

	__webpack_require__(357);
	__webpack_require__(366);

	__webpack_require__(379);


/***/ },
/* 1 */
/***/ function(module, exports, __webpack_require__) {

	// Enable DEV mode when using source code without build. which has no __DEV__ variable
	// In build process 'typeof __DEV__' will be replace with 'boolean'
	// So this code will be removed or disabled anyway after built.
	if (false) {
	    // In browser
	    if (typeof window !== 'undefined') {
	        window.__DEV__ = true;
	    }
	    // In node
	    else if (typeof global !== 'undefined') {
	        global.__DEV__ = true;
	    }
	}

	/*!
	 * ECharts, a javascript interactive chart library.
	 *
	 * Copyright (c) 2015, Baidu Inc.
	 * All rights reserved.
	 *
	 * LICENSE
	 * https://github.com/ecomfe/echarts/blob/master/LICENSE.txt
	 */

	/**
	 * @module echarts
	 */


	    var env = __webpack_require__(2);

	    var GlobalModel = __webpack_require__(3);
	    var ExtensionAPI = __webpack_require__(25);
	    var CoordinateSystemManager = __webpack_require__(26);
	    var OptionManager = __webpack_require__(27);

	    var ComponentModel = __webpack_require__(19);
	    var SeriesModel = __webpack_require__(28);

	    var ComponentView = __webpack_require__(29);
	    var ChartView = __webpack_require__(42);
	    var graphic = __webpack_require__(43);

	    var zrender = __webpack_require__(81);
	    var zrUtil = __webpack_require__(4);
	    var colorTool = __webpack_require__(39);
	    var Eventful = __webpack_require__(33);
	    var timsort = __webpack_require__(85);

	    var each = zrUtil.each;

	    var PRIORITY_PROCESSOR_FILTER = 1000;
	    var PRIORITY_PROCESSOR_STATISTIC = 5000;


	    var PRIORITY_VISUAL_LAYOUT = 1000;
	    var PRIORITY_VISUAL_GLOBAL = 2000;
	    var PRIORITY_VISUAL_CHART = 3000;
	    var PRIORITY_VISUAL_COMPONENT = 4000;
	    var PRIORITY_VISUAL_BRUSH = 5000;

	    // Main process have three entries: `setOption`, `dispatchAction` and `resize`,
	    // where they must not be invoked nestedly, except the only case: invoke
	    // dispatchAction with updateMethod "none" in main process.
	    // This flag is used to carry out this rule.
	    // All events will be triggered out side main process (i.e. when !this[IN_MAIN_PROCESS]).
	    var IN_MAIN_PROCESS = '__flag_in_main_process';
	    var HAS_GRADIENT_OR_PATTERN_BG = '_hasGradientOrPatternBg';


	    var OPTION_UPDATED = '_optionUpdated';

	    function createRegisterEventWithLowercaseName(method) {
	        return function (eventName, handler, context) {
	            // Event name is all lowercase
	            eventName = eventName && eventName.toLowerCase();
	            Eventful.prototype[method].call(this, eventName, handler, context);
	        };
	    }
	    /**
	     * @module echarts~MessageCenter
	     */
	    function MessageCenter() {
	        Eventful.call(this);
	    }
	    MessageCenter.prototype.on = createRegisterEventWithLowercaseName('on');
	    MessageCenter.prototype.off = createRegisterEventWithLowercaseName('off');
	    MessageCenter.prototype.one = createRegisterEventWithLowercaseName('one');
	    zrUtil.mixin(MessageCenter, Eventful);
	    /**
	     * @module echarts~ECharts
	     */
	    function ECharts (dom, theme, opts) {
	        opts = opts || {};

	        // Get theme by name
	        if (typeof theme === 'string') {
	            theme = themeStorage[theme];
	        }

	        /**
	         * @type {string}
	         */
	        this.id;
	        /**
	         * Group id
	         * @type {string}
	         */
	        this.group;
	        /**
	         * @type {HTMLDomElement}
	         * @private
	         */
	        this._dom = dom;
	        /**
	         * @type {module:zrender/ZRender}
	         * @private
	         */
	        this._zr = zrender.init(dom, {
	            renderer: opts.renderer || 'canvas',
	            devicePixelRatio: opts.devicePixelRatio
	        });

	        /**
	         * @type {Object}
	         * @private
	         */
	        this._theme = zrUtil.clone(theme);

	        /**
	         * @type {Array.<module:echarts/view/Chart>}
	         * @private
	         */
	        this._chartsViews = [];

	        /**
	         * @type {Object.<string, module:echarts/view/Chart>}
	         * @private
	         */
	        this._chartsMap = {};

	        /**
	         * @type {Array.<module:echarts/view/Component>}
	         * @private
	         */
	        this._componentsViews = [];

	        /**
	         * @type {Object.<string, module:echarts/view/Component>}
	         * @private
	         */
	        this._componentsMap = {};

	        /**
	         * @type {module:echarts/ExtensionAPI}
	         * @private
	         */
	        this._api = new ExtensionAPI(this);

	        /**
	         * @type {module:echarts/CoordinateSystem}
	         * @private
	         */
	        this._coordSysMgr = new CoordinateSystemManager();

	        Eventful.call(this);

	        /**
	         * @type {module:echarts~MessageCenter}
	         * @private
	         */
	        this._messageCenter = new MessageCenter();

	        // Init mouse events
	        this._initEvents();

	        // In case some people write `window.onresize = chart.resize`
	        this.resize = zrUtil.bind(this.resize, this);

	        // Can't dispatch action during rendering procedure
	        this._pendingActions = [];
	        // Sort on demand
	        function prioritySortFunc(a, b) {
	            return a.prio - b.prio;
	        }
	        timsort(visualFuncs, prioritySortFunc);
	        timsort(dataProcessorFuncs, prioritySortFunc);

	        this._zr.animation.on('frame', this._onframe, this);
	    }

	    var echartsProto = ECharts.prototype;

	    echartsProto._onframe = function () {
	        // Lazy update
	        if (this[OPTION_UPDATED]) {

	            this[IN_MAIN_PROCESS] = true;

	            updateMethods.prepareAndUpdate.call(this);

	            this[IN_MAIN_PROCESS] = false;

	            this[OPTION_UPDATED] = false;
	        }
	    };
	    /**
	     * @return {HTMLDomElement}
	     */
	    echartsProto.getDom = function () {
	        return this._dom;
	    };

	    /**
	     * @return {module:zrender~ZRender}
	     */
	    echartsProto.getZr = function () {
	        return this._zr;
	    };

	    /**
	     * @param {Object} option
	     * @param {boolean} notMerge
	     * @param {boolean} [lazyUpdate=false] Useful when setOption frequently.
	     */
	    echartsProto.setOption = function (option, notMerge, lazyUpdate) {
	        if (true) {
	            zrUtil.assert(!this[IN_MAIN_PROCESS], '`setOption` should not be called during main process.');
	        }

	        this[IN_MAIN_PROCESS] = true;

	        if (!this._model || notMerge) {
	            var optionManager = new OptionManager(this._api);
	            var theme = this._theme;
	            var ecModel = this._model = new GlobalModel(null, null, theme, optionManager);
	            ecModel.init(null, null, theme, optionManager);
	        }

	        this._model.setOption(option, optionPreprocessorFuncs);

	        if (lazyUpdate) {
	            this[OPTION_UPDATED] = true;
	        }
	        else {
	            updateMethods.prepareAndUpdate.call(this);
	            this._zr.refreshImmediately();
	            this[OPTION_UPDATED] = false;
	        }

	        this[IN_MAIN_PROCESS] = false;

	        this._flushPendingActions();
	    };

	    /**
	     * @DEPRECATED
	     */
	    echartsProto.setTheme = function () {
	        console.log('ECharts#setTheme() is DEPRECATED in ECharts 3.0');
	    };

	    /**
	     * @return {module:echarts/model/Global}
	     */
	    echartsProto.getModel = function () {
	        return this._model;
	    };

	    /**
	     * @return {Object}
	     */
	    echartsProto.getOption = function () {
	        return this._model && this._model.getOption();
	    };

	    /**
	     * @return {number}
	     */
	    echartsProto.getWidth = function () {
	        return this._zr.getWidth();
	    };

	    /**
	     * @return {number}
	     */
	    echartsProto.getHeight = function () {
	        return this._zr.getHeight();
	    };

	    /**
	     * Get canvas which has all thing rendered
	     * @param {Object} opts
	     * @param {string} [opts.backgroundColor]
	     */
	    echartsProto.getRenderedCanvas = function (opts) {
	        if (!env.canvasSupported) {
	            return;
	        }
	        opts = opts || {};
	        opts.pixelRatio = opts.pixelRatio || 1;
	        opts.backgroundColor = opts.backgroundColor
	            || this._model.get('backgroundColor');
	        var zr = this._zr;
	        var list = zr.storage.getDisplayList();
	        // Stop animations
	        zrUtil.each(list, function (el) {
	            el.stopAnimation(true);
	        });
	        return zr.painter.getRenderedCanvas(opts);
	    };
	    /**
	     * @return {string}
	     * @param {Object} opts
	     * @param {string} [opts.type='png']
	     * @param {string} [opts.pixelRatio=1]
	     * @param {string} [opts.backgroundColor]
	     */
	    echartsProto.getDataURL = function (opts) {
	        opts = opts || {};
	        var excludeComponents = opts.excludeComponents;
	        var ecModel = this._model;
	        var excludesComponentViews = [];
	        var self = this;

	        each(excludeComponents, function (componentType) {
	            ecModel.eachComponent({
	                mainType: componentType
	            }, function (component) {
	                var view = self._componentsMap[component.__viewId];
	                if (!view.group.ignore) {
	                    excludesComponentViews.push(view);
	                    view.group.ignore = true;
	                }
	            });
	        });

	        var url = this.getRenderedCanvas(opts).toDataURL(
	            'image/' + (opts && opts.type || 'png')
	        );

	        each(excludesComponentViews, function (view) {
	            view.group.ignore = false;
	        });
	        return url;
	    };


	    /**
	     * @return {string}
	     * @param {Object} opts
	     * @param {string} [opts.type='png']
	     * @param {string} [opts.pixelRatio=1]
	     * @param {string} [opts.backgroundColor]
	     */
	    echartsProto.getConnectedDataURL = function (opts) {
	        if (!env.canvasSupported) {
	            return;
	        }
	        var groupId = this.group;
	        var mathMin = Math.min;
	        var mathMax = Math.max;
	        var MAX_NUMBER = Infinity;
	        if (connectedGroups[groupId]) {
	            var left = MAX_NUMBER;
	            var top = MAX_NUMBER;
	            var right = -MAX_NUMBER;
	            var bottom = -MAX_NUMBER;
	            var canvasList = [];
	            var dpr = (opts && opts.pixelRatio) || 1;
	            for (var id in instances) {
	                var chart = instances[id];
	                if (chart.group === groupId) {
	                    var canvas = chart.getRenderedCanvas(
	                        zrUtil.clone(opts)
	                    );
	                    var boundingRect = chart.getDom().getBoundingClientRect();
	                    left = mathMin(boundingRect.left, left);
	                    top = mathMin(boundingRect.top, top);
	                    right = mathMax(boundingRect.right, right);
	                    bottom = mathMax(boundingRect.bottom, bottom);
	                    canvasList.push({
	                        dom: canvas,
	                        left: boundingRect.left,
	                        top: boundingRect.top
	                    });
	                }
	            }

	            left *= dpr;
	            top *= dpr;
	            right *= dpr;
	            bottom *= dpr;
	            var width = right - left;
	            var height = bottom - top;
	            var targetCanvas = zrUtil.createCanvas();
	            targetCanvas.width = width;
	            targetCanvas.height = height;
	            var zr = zrender.init(targetCanvas);

	            each(canvasList, function (item) {
	                var img = new graphic.Image({
	                    style: {
	                        x: item.left * dpr - left,
	                        y: item.top * dpr - top,
	                        image: item.dom
	                    }
	                });
	                zr.add(img);
	            });
	            zr.refreshImmediately();

	            return targetCanvas.toDataURL('image/' + (opts && opts.type || 'png'));
	        }
	        else {
	            return this.getDataURL(opts);
	        }
	    };

	    var updateMethods = {


	        /**
	         * @param {Object} payload
	         * @private
	         */
	        update: function (payload) {
	            // console.time && console.time('update');

	            var ecModel = this._model;
	            var api = this._api;
	            var coordSysMgr = this._coordSysMgr;
	            var zr = this._zr;
	            // update before setOption
	            if (!ecModel) {
	                return;
	            }

	            // Fixme First time update ?
	            ecModel.restoreData();

	            // TODO
	            // Save total ecModel here for undo/redo (after restoring data and before processing data).
	            // Undo (restoration of total ecModel) can be carried out in 'action' or outside API call.

	            // Create new coordinate system each update
	            // In LineView may save the old coordinate system and use it to get the orignal point
	            coordSysMgr.create(this._model, this._api);

	            processData.call(this, ecModel, api);

	            stackSeriesData.call(this, ecModel);

	            coordSysMgr.update(ecModel, api);

	            doVisualEncoding.call(this, ecModel, payload);

	            doRender.call(this, ecModel, payload);

	            // Set background
	            var backgroundColor = ecModel.get('backgroundColor') || 'transparent';

	            var painter = zr.painter;
	            // TODO all use clearColor ?
	            if (painter.isSingleCanvas && painter.isSingleCanvas()) {
	                zr.configLayer(0, {
	                    clearColor: backgroundColor
	                });
	            }
	            else {
	                // In IE8
	                if (!env.canvasSupported) {
	                    var colorArr = colorTool.parse(backgroundColor);
	                    backgroundColor = colorTool.stringify(colorArr, 'rgb');
	                    if (colorArr[3] === 0) {
	                        backgroundColor = 'transparent';
	                    }
	                }
	                if (backgroundColor.colorStops || backgroundColor.image) {
	                    // Gradient background
	                    // FIXME Fixed layer？
	                    zr.configLayer(0, {
	                        clearColor: backgroundColor
	                    });
	                    this[HAS_GRADIENT_OR_PATTERN_BG] = true;

	                    this._dom.style.background = 'transparent';
	                }
	                else {
	                    if (this[HAS_GRADIENT_OR_PATTERN_BG]) {
	                        zr.configLayer(0, {
	                            clearColor: null
	                        });
	                    }
	                    this[HAS_GRADIENT_OR_PATTERN_BG] = false;

	                    this._dom.style.background = backgroundColor;
	                }
	            }

	            // console.time && console.timeEnd('update');
	        },

	        // PENDING
	        /**
	         * @param {Object} payload
	         * @private
	         */
	        updateView: function (payload) {
	            var ecModel = this._model;

	            // update before setOption
	            if (!ecModel) {
	                return;
	            }

	            ecModel.eachSeries(function (seriesModel) {
	                seriesModel.getData().clearAllVisual();
	            });

	            doVisualEncoding.call(this, ecModel, payload);

	            invokeUpdateMethod.call(this, 'updateView', ecModel, payload);
	        },

	        /**
	         * @param {Object} payload
	         * @private
	         */
	        updateVisual: function (payload) {
	            var ecModel = this._model;

	            // update before setOption
	            if (!ecModel) {
	                return;
	            }

	            ecModel.eachSeries(function (seriesModel) {
	                seriesModel.getData().clearAllVisual();
	            });

	            doVisualEncoding.call(this, ecModel, payload);

	            invokeUpdateMethod.call(this, 'updateVisual', ecModel, payload);
	        },

	        /**
	         * @param {Object} payload
	         * @private
	         */
	        updateLayout: function (payload) {
	            var ecModel = this._model;

	            // update before setOption
	            if (!ecModel) {
	                return;
	            }

	            doLayout.call(this, ecModel, payload);

	            invokeUpdateMethod.call(this, 'updateLayout', ecModel, payload);
	        },

	        /**
	         * @param {Object} payload
	         * @private
	         */
	        highlight: function (payload) {
	            toggleHighlight.call(this, 'highlight', payload);
	        },

	        /**
	         * @param {Object} payload
	         * @private
	         */
	        downplay: function (payload) {
	            toggleHighlight.call(this, 'downplay', payload);
	        },

	        /**
	         * @param {Object} payload
	         * @private
	         */
	        prepareAndUpdate: function (payload) {
	            var ecModel = this._model;

	            prepareView.call(this, 'component', ecModel);

	            prepareView.call(this, 'chart', ecModel);

	            updateMethods.update.call(this, payload);
	        }
	    };

	    /**
	     * @param {Object} payload
	     * @private
	     */
	    function toggleHighlight(method, payload) {
	        var ecModel = this._model;

	        // dispatchAction before setOption
	        if (!ecModel) {
	            return;
	        }

	        ecModel.eachComponent(
	            {mainType: 'series', query: payload},
	            function (seriesModel, index) {
	                var chartView = this._chartsMap[seriesModel.__viewId];
	                if (chartView && chartView.__alive) {
	                    chartView[method](
	                        seriesModel, ecModel, this._api, payload
	                    );
	                }
	            },
	            this
	        );
	    }

	    /**
	     * Resize the chart
	     */
	    echartsProto.resize = function () {
	        if (true) {
	            zrUtil.assert(!this[IN_MAIN_PROCESS], '`resize` should not be called during main process.');
	        }

	        this[IN_MAIN_PROCESS] = true;

	        this._zr.resize();

	        var optionChanged = this._model && this._model.resetOption('media');
	        updateMethods[optionChanged ? 'prepareAndUpdate' : 'update'].call(this);

	        // Resize loading effect
	        this._loadingFX && this._loadingFX.resize();

	        this[IN_MAIN_PROCESS] = false;

	        this._flushPendingActions();
	    };

	    /**
	     * Show loading effect
	     * @param  {string} [name='default']
	     * @param  {Object} [cfg]
	     */
	    echartsProto.showLoading = function (name, cfg) {
	        if (zrUtil.isObject(name)) {
	            cfg = name;
	            name = '';
	        }
	        name = name || 'default';

	        this.hideLoading();
	        if (!loadingEffects[name]) {
	            if (true) {
	                console.warn('Loading effects ' + name + ' not exists.');
	            }
	            return;
	        }
	        var el = loadingEffects[name](this._api, cfg);
	        var zr = this._zr;
	        this._loadingFX = el;

	        zr.add(el);
	    };

	    /**
	     * Hide loading effect
	     */
	    echartsProto.hideLoading = function () {
	        this._loadingFX && this._zr.remove(this._loadingFX);
	        this._loadingFX = null;
	    };

	    /**
	     * @param {Object} eventObj
	     * @return {Object}
	     */
	    echartsProto.makeActionFromEvent = function (eventObj) {
	        var payload = zrUtil.extend({}, eventObj);
	        payload.type = eventActionMap[eventObj.type];
	        return payload;
	    };

	    /**
	     * @pubilc
	     * @param {Object} payload
	     * @param {string} [payload.type] Action type
	     * @param {boolean} [silent=false] Whether trigger event.
	     */
	    echartsProto.dispatchAction = function (payload, silent) {
	        var actionWrap = actions[payload.type];
	        if (!actionWrap) {
	            return;
	        }

	        var actionInfo = actionWrap.actionInfo;
	        var updateMethod = actionInfo.update || 'update';

	        // if (__DEV__) {
	        //     zrUtil.assert(
	        //         !this[IN_MAIN_PROCESS],
	        //         '`dispatchAction` should not be called during main process.'
	        //         + 'unless updateMathod is "none".'
	        //     );
	        // }

	        // May dispatchAction in rendering procedure
	        if (this[IN_MAIN_PROCESS]) {
	            this._pendingActions.push(payload);
	            return;
	        }

	        this[IN_MAIN_PROCESS] = true;

	        var payloads = [payload];
	        var batched = false;
	        // Batch action
	        if (payload.batch) {
	            batched = true;
	            payloads = zrUtil.map(payload.batch, function (item) {
	                item = zrUtil.defaults(zrUtil.extend({}, item), payload);
	                item.batch = null;
	                return item;
	            });
	        }

	        var eventObjBatch = [];
	        var eventObj;
	        var isHighlightOrDownplay = payload.type === 'highlight' || payload.type === 'downplay';
	        for (var i = 0; i < payloads.length; i++) {
	            var batchItem = payloads[i];
	            // Action can specify the event by return it.
	            eventObj = actionWrap.action(batchItem, this._model);
	            // Emit event outside
	            eventObj = eventObj || zrUtil.extend({}, batchItem);
	            // Convert type to eventType
	            eventObj.type = actionInfo.event || eventObj.type;
	            eventObjBatch.push(eventObj);

	            // Highlight and downplay are special.
	            isHighlightOrDownplay && updateMethods[updateMethod].call(this, batchItem);
	        }

	        if (updateMethod !== 'none' && !isHighlightOrDownplay) {
	            // Still dirty
	            if (this[OPTION_UPDATED]) {
	                // FIXME Pass payload ?
	                updateMethods.prepareAndUpdate.call(this, payload);
	                this[OPTION_UPDATED] = false;
	            }
	            else {
	                updateMethods[updateMethod].call(this, payload);
	            }
	        }

	        // Follow the rule of action batch
	        if (batched) {
	            eventObj = {
	                type: actionInfo.event || payload.type,
	                batch: eventObjBatch
	            };
	        }
	        else {
	            eventObj = eventObjBatch[0];
	        }

	        this[IN_MAIN_PROCESS] = false;

	        !silent && this._messageCenter.trigger(eventObj.type, eventObj);

	        this._flushPendingActions();

	    };

	    echartsProto._flushPendingActions = function () {
	        var pendingActions = this._pendingActions;
	        while (pendingActions.length) {
	            var payload = pendingActions.shift();
	            this.dispatchAction(payload);
	        }
	    };

	    /**
	     * Register event
	     * @method
	     */
	    echartsProto.on = createRegisterEventWithLowercaseName('on');
	    echartsProto.off = createRegisterEventWithLowercaseName('off');
	    echartsProto.one = createRegisterEventWithLowercaseName('one');

	    /**
	     * @param {string} methodName
	     * @private
	     */
	    function invokeUpdateMethod(methodName, ecModel, payload) {
	        var api = this._api;

	        // Update all components
	        each(this._componentsViews, function (component) {
	            var componentModel = component.__model;
	            component[methodName](componentModel, ecModel, api, payload);

	            updateZ(componentModel, component);
	        }, this);

	        // Upate all charts
	        ecModel.eachSeries(function (seriesModel, idx) {
	            var chart = this._chartsMap[seriesModel.__viewId];
	            chart[methodName](seriesModel, ecModel, api, payload);

	            updateZ(seriesModel, chart);

	            updateProgressiveAndBlend(seriesModel, chart);
	        }, this);

	        // If use hover layer
	        updateHoverLayerStatus(this._zr, ecModel);
	    }

	    /**
	     * Prepare view instances of charts and components
	     * @param  {module:echarts/model/Global} ecModel
	     * @private
	     */
	    function prepareView(type, ecModel) {
	        var isComponent = type === 'component';
	        var viewList = isComponent ? this._componentsViews : this._chartsViews;
	        var viewMap = isComponent ? this._componentsMap : this._chartsMap;
	        var zr = this._zr;

	        for (var i = 0; i < viewList.length; i++) {
	            viewList[i].__alive = false;
	        }

	        ecModel[isComponent ? 'eachComponent' : 'eachSeries'](function (componentType, model) {
	            if (isComponent) {
	                if (componentType === 'series') {
	                    return;
	                }
	            }
	            else {
	                model = componentType;
	            }

	            // Consider: id same and type changed.
	            var viewId = model.id + '_' + model.type;
	            var view = viewMap[viewId];
	            if (!view) {
	                var classType = ComponentModel.parseClassType(model.type);
	                var Clazz = isComponent
	                    ? ComponentView.getClass(classType.main, classType.sub)
	                    : ChartView.getClass(classType.sub);
	                if (Clazz) {
	                    view = new Clazz();
	                    view.init(ecModel, this._api);
	                    viewMap[viewId] = view;
	                    viewList.push(view);
	                    zr.add(view.group);
	                }
	                else {
	                    // Error
	                    return;
	                }
	            }

	            model.__viewId = viewId;
	            view.__alive = true;
	            view.__id = viewId;
	            view.__model = model;
	        }, this);

	        for (var i = 0; i < viewList.length;) {
	            var view = viewList[i];
	            if (!view.__alive) {
	                zr.remove(view.group);
	                view.dispose(ecModel, this._api);
	                viewList.splice(i, 1);
	                delete viewMap[view.__id];
	            }
	            else {
	                i++;
	            }
	        }
	    }

	    /**
	     * Processor data in each series
	     *
	     * @param {module:echarts/model/Global} ecModel
	     * @private
	     */
	    function processData(ecModel, api) {
	        each(dataProcessorFuncs, function (process) {
	            process.func(ecModel, api);
	        });
	    }

	    /**
	     * @private
	     */
	    function stackSeriesData(ecModel) {
	        var stackedDataMap = {};
	        ecModel.eachSeries(function (series) {
	            var stack = series.get('stack');
	            var data = series.getData();
	            if (stack && data.type === 'list') {
	                var previousStack = stackedDataMap[stack];
	                if (previousStack) {
	                    data.stackedOn = previousStack;
	                }
	                stackedDataMap[stack] = data;
	            }
	        });
	    }

	    /**
	     * Layout before each chart render there series, special visual encoding stage
	     *
	     * @param {module:echarts/model/Global} ecModel
	     * @private
	     */
	    function doLayout(ecModel, payload) {
	        var api = this._api;
	        each(visualFuncs, function (visual) {
	            if (visual.isLayout) {
	                visual.func(ecModel, api, payload);
	            }
	        });
	    }

	    /**
	     * Encode visual infomation from data after data processing
	     *
	     * @param {module:echarts/model/Global} ecModel
	     * @private
	     */
	    function doVisualEncoding(ecModel, payload) {
	        var api = this._api;
	        ecModel.clearColorPalette();
	        ecModel.eachSeries(function (seriesModel) {
	            seriesModel.clearColorPalette();
	        });
	        each(visualFuncs, function (visual) {
	            visual.func(ecModel, api, payload);
	        });
	    }

	    /**
	     * Render each chart and component
	     * @private
	     */
	    function doRender(ecModel, payload) {
	        var api = this._api;
	        // Render all components
	        each(this._componentsViews, function (componentView) {
	            var componentModel = componentView.__model;
	            componentView.render(componentModel, ecModel, api, payload);

	            updateZ(componentModel, componentView);
	        }, this);

	        each(this._chartsViews, function (chart) {
	            chart.__alive = false;
	        }, this);

	        // Render all charts
	        ecModel.eachSeries(function (seriesModel, idx) {
	            var chartView = this._chartsMap[seriesModel.__viewId];
	            chartView.__alive = true;
	            chartView.render(seriesModel, ecModel, api, payload);

	            chartView.group.silent = !!seriesModel.get('silent');

	            updateZ(seriesModel, chartView);

	            updateProgressiveAndBlend(seriesModel, chartView);

	        }, this);

	        // If use hover layer
	        updateHoverLayerStatus(this._zr, ecModel);

	        // Remove groups of unrendered charts
	        each(this._chartsViews, function (chart) {
	            if (!chart.__alive) {
	                chart.remove(ecModel, api);
	            }
	        }, this);
	    }

	    var MOUSE_EVENT_NAMES = [
	        'click', 'dblclick', 'mouseover', 'mouseout', 'mousemove', 'mousedown', 'mouseup', 'globalout'
	    ];
	    /**
	     * @private
	     */
	    echartsProto._initEvents = function () {
	        each(MOUSE_EVENT_NAMES, function (eveName) {
	            this._zr.on(eveName, function (e) {
	                var ecModel = this.getModel();
	                var el = e.target;
	                if (el && el.dataIndex != null) {
	                    var dataModel = el.dataModel || ecModel.getSeriesByIndex(el.seriesIndex);
	                    var params = dataModel && dataModel.getDataParams(el.dataIndex, el.dataType) || {};
	                    params.event = e;
	                    params.type = eveName;
	                    this.trigger(eveName, params);
	                }
	                // If element has custom eventData of components
	                else if (el && el.eventData) {
	                    this.trigger(eveName, el.eventData);
	                }
	            }, this);
	        }, this);

	        each(eventActionMap, function (actionType, eventType) {
	            this._messageCenter.on(eventType, function (event) {
	                this.trigger(eventType, event);
	            }, this);
	        }, this);
	    };

	    /**
	     * @return {boolean}
	     */
	    echartsProto.isDisposed = function () {
	        return this._disposed;
	    };

	    /**
	     * Clear
	     */
	    echartsProto.clear = function () {
	        this.setOption({ series: [] }, true);
	    };
	    /**
	     * Dispose instance
	     */
	    echartsProto.dispose = function () {
	        if (this._disposed) {
	            if (true) {
	                console.warn('Instance ' + this.id + ' has been disposed');
	            }
	            return;
	        }
	        this._disposed = true;

	        var api = this._api;
	        var ecModel = this._model;

	        each(this._componentsViews, function (component) {
	            component.dispose(ecModel, api);
	        });
	        each(this._chartsViews, function (chart) {
	            chart.dispose(ecModel, api);
	        });

	        // Dispose after all views disposed
	        this._zr.dispose();

	        delete instances[this.id];
	    };

	    zrUtil.mixin(ECharts, Eventful);

	    function updateHoverLayerStatus(zr, ecModel) {
	        var storage = zr.storage;
	        var elCount = 0;
	        storage.traverse(function (el) {
	            if (!el.isGroup) {
	                elCount++;
	            }
	        });
	        if (elCount > ecModel.get('hoverLayerThreshold') && !env.node) {
	            storage.traverse(function (el) {
	                if (!el.isGroup) {
	                    el.useHoverLayer = true;
	                }
	            });
	        }
	    }
	    /**
	     * Update chart progressive and blend.
	     * @param {module:echarts/model/Series|module:echarts/model/Component} model
	     * @param {module:echarts/view/Component|module:echarts/view/Chart} view
	     */
	    function updateProgressiveAndBlend(seriesModel, chartView) {
	        // Progressive configuration
	        var elCount = 0;
	        chartView.group.traverse(function (el) {
	            if (el.type !== 'group' && !el.ignore) {
	                elCount++;
	            }
	        });
	        var frameDrawNum = +seriesModel.get('progressive');
	        var needProgressive = elCount > seriesModel.get('progressiveThreshold') && frameDrawNum && !env.node;
	        if (needProgressive) {
	            chartView.group.traverse(function (el) {
	                // FIXME marker and other components
	                if (!el.isGroup) {
	                    el.progressive = needProgressive ?
	                        Math.floor(elCount++ / frameDrawNum) : -1;
	                    if (needProgressive) {
	                        el.stopAnimation(true);
	                    }
	                }
	            });
	        }

	        // Blend configration
	        var blendMode = seriesModel.get('blendMode') || null;
	        if (true) {
	            if (!env.canvasSupported && blendMode && blendMode !== 'source-over') {
	                console.warn('Only canvas support blendMode');
	            }
	        }
	        chartView.group.traverse(function (el) {
	            // FIXME marker and other components
	            if (!el.isGroup) {
	                el.setStyle('blend', blendMode);
	            }
	        });
	    }
	    /**
	     * @param {module:echarts/model/Series|module:echarts/model/Component} model
	     * @param {module:echarts/view/Component|module:echarts/view/Chart} view
	     */
	    function updateZ(model, view) {
	        var z = model.get('z');
	        var zlevel = model.get('zlevel');
	        // Set z and zlevel
	        view.group.traverse(function (el) {
	            if (el.type !== 'group') {
	                z != null && (el.z = z);
	                zlevel != null && (el.zlevel = zlevel);
	            }
	        });
	    }
	    /**
	     * @type {Array.<Function>}
	     * @inner
	     */
	    var actions = [];

	    /**
	     * Map eventType to actionType
	     * @type {Object}
	     */
	    var eventActionMap = {};

	    /**
	     * Data processor functions of each stage
	     * @type {Array.<Object.<string, Function>>}
	     * @inner
	     */
	    var dataProcessorFuncs = [];

	    /**
	     * @type {Array.<Function>}
	     * @inner
	     */
	    var optionPreprocessorFuncs = [];

	    /**
	     * Visual encoding functions of each stage
	     * @type {Array.<Object.<string, Function>>}
	     * @inner
	     */
	    var visualFuncs = [];
	    /**
	     * Theme storage
	     * @type {Object.<key, Object>}
	     */
	    var themeStorage = {};
	    /**
	     * Loading effects
	     */
	    var loadingEffects = {};


	    var instances = {};
	    var connectedGroups = {};

	    var idBase = new Date() - 0;
	    var groupIdBase = new Date() - 0;
	    var DOM_ATTRIBUTE_KEY = '_echarts_instance_';
	    /**
	     * @alias module:echarts
	     */
	    var echarts = {
	        /**
	         * @type {number}
	         */
	        version: '3.2.3',
	        dependencies: {
	            zrender: '3.1.3'
	        }
	    };

	    function enableConnect(chart) {

	        var STATUS_PENDING = 0;
	        var STATUS_UPDATING = 1;
	        var STATUS_UPDATED = 2;
	        var STATUS_KEY = '__connectUpdateStatus';
	        function updateConnectedChartsStatus(charts, status) {
	            for (var i = 0; i < charts.length; i++) {
	                var otherChart = charts[i];
	                otherChart[STATUS_KEY] = status;
	            }
	        }
	        zrUtil.each(eventActionMap, function (actionType, eventType) {
	            chart._messageCenter.on(eventType, function (event) {
	                if (connectedGroups[chart.group] && chart[STATUS_KEY] !== STATUS_PENDING) {
	                    var action = chart.makeActionFromEvent(event);
	                    var otherCharts = [];
	                    for (var id in instances) {
	                        var otherChart = instances[id];
	                        if (otherChart !== chart && otherChart.group === chart.group) {
	                            otherCharts.push(otherChart);
	                        }
	                    }
	                    updateConnectedChartsStatus(otherCharts, STATUS_PENDING);
	                    each(otherCharts, function (otherChart) {
	                        if (otherChart[STATUS_KEY] !== STATUS_UPDATING) {
	                            otherChart.dispatchAction(action);
	                        }
	                    });
	                    updateConnectedChartsStatus(otherCharts, STATUS_UPDATED);
	                }
	            });
	        });

	    }
	    /**
	     * @param {HTMLDomElement} dom
	     * @param {Object} [theme]
	     * @param {Object} opts
	     */
	    echarts.init = function (dom, theme, opts) {
	        if (true) {
	            // Check version
	            if ((zrender.version.replace('.', '') - 0) < (echarts.dependencies.zrender.replace('.', '') - 0)) {
	                throw new Error(
	                    'ZRender ' + zrender.version
	                    + ' is too old for ECharts ' + echarts.version
	                    + '. Current version need ZRender '
	                    + echarts.dependencies.zrender + '+'
	                );
	            }
	            if (!dom) {
	                throw new Error('Initialize failed: invalid dom.');
	            }
	            if (zrUtil.isDom(dom) && dom.nodeName.toUpperCase() !== 'CANVAS' && (!dom.clientWidth || !dom.clientHeight)) {
	                console.warn('Can\'t get dom width or height');
	            }
	        }

	        var chart = new ECharts(dom, theme, opts);
	        chart.id = 'ec_' + idBase++;
	        instances[chart.id] = chart;

	        dom.setAttribute &&
	            dom.setAttribute(DOM_ATTRIBUTE_KEY, chart.id);

	        enableConnect(chart);

	        return chart;
	    };

	    /**
	     * @return {string|Array.<module:echarts~ECharts>} groupId
	     */
	    echarts.connect = function (groupId) {
	        // Is array of charts
	        if (zrUtil.isArray(groupId)) {
	            var charts = groupId;
	            groupId = null;
	            // If any chart has group
	            zrUtil.each(charts, function (chart) {
	                if (chart.group != null) {
	                    groupId = chart.group;
	                }
	            });
	            groupId = groupId || ('g_' + groupIdBase++);
	            zrUtil.each(charts, function (chart) {
	                chart.group = groupId;
	            });
	        }
	        connectedGroups[groupId] = true;
	        return groupId;
	    };

	    /**
	     * @return {string} groupId
	     */
	    echarts.disConnect = function (groupId) {
	        connectedGroups[groupId] = false;
	    };

	    /**
	     * Dispose a chart instance
	     * @param  {module:echarts~ECharts|HTMLDomElement|string} chart
	     */
	    echarts.dispose = function (chart) {
	        if (zrUtil.isDom(chart)) {
	            chart = echarts.getInstanceByDom(chart);
	        }
	        else if (typeof chart === 'string') {
	            chart = instances[chart];
	        }
	        if ((chart instanceof ECharts) && !chart.isDisposed()) {
	            chart.dispose();
	        }
	    };

	    /**
	     * @param  {HTMLDomElement} dom
	     * @return {echarts~ECharts}
	     */
	    echarts.getInstanceByDom = function (dom) {
	        var key = dom.getAttribute(DOM_ATTRIBUTE_KEY);
	        return instances[key];
	    };
	    /**
	     * @param {string} key
	     * @return {echarts~ECharts}
	     */
	    echarts.getInstanceById = function (key) {
	        return instances[key];
	    };

	    /**
	     * Register theme
	     */
	    echarts.registerTheme = function (name, theme) {
	        themeStorage[name] = theme;
	    };

	    /**
	     * Register option preprocessor
	     * @param {Function} preprocessorFunc
	     */
	    echarts.registerPreprocessor = function (preprocessorFunc) {
	        optionPreprocessorFuncs.push(preprocessorFunc);
	    };

	    /**
	     * @param {number} [priority=1000]
	     * @param {Function} processorFunc
	     */
	    echarts.registerProcessor = function (priority, processorFunc) {
	        if (typeof priority === 'function') {
	            processorFunc = priority;
	            priority = PRIORITY_PROCESSOR_FILTER;
	        }
	        if (true) {
	            if (isNaN(priority)) {
	                throw new Error('Unkown processor priority');
	            }
	        }
	        dataProcessorFuncs.push({
	            prio: priority,
	            func: processorFunc
	        });
	    };

	    /**
	     * Usage:
	     * registerAction('someAction', 'someEvent', function () { ... });
	     * registerAction('someAction', function () { ... });
	     * registerAction(
	     *     {type: 'someAction', event: 'someEvent', update: 'updateView'},
	     *     function () { ... }
	     * );
	     *
	     * @param {(string|Object)} actionInfo
	     * @param {string} actionInfo.type
	     * @param {string} [actionInfo.event]
	     * @param {string} [actionInfo.update]
	     * @param {string} [eventName]
	     * @param {Function} action
	     */
	    echarts.registerAction = function (actionInfo, eventName, action) {
	        if (typeof eventName === 'function') {
	            action = eventName;
	            eventName = '';
	        }
	        var actionType = zrUtil.isObject(actionInfo)
	            ? actionInfo.type
	            : ([actionInfo, actionInfo = {
	                event: eventName
	            }][0]);

	        // Event name is all lowercase
	        actionInfo.event = (actionInfo.event || actionType).toLowerCase();
	        eventName = actionInfo.event;

	        if (!actions[actionType]) {
	            actions[actionType] = {action: action, actionInfo: actionInfo};
	        }
	        eventActionMap[eventName] = actionType;
	    };

	    /**
	     * @param {string} type
	     * @param {*} CoordinateSystem
	     */
	    echarts.registerCoordinateSystem = function (type, CoordinateSystem) {
	        CoordinateSystemManager.register(type, CoordinateSystem);
	    };

	    /**
	     * Layout is a special stage of visual encoding
	     * Most visual encoding like color are common for different chart
	     * But each chart has it's own layout algorithm
	     *
	     * @param {number} [priority=1000]
	     * @param {Function} layoutFunc
	     */
	    echarts.registerLayout = function (priority, layoutFunc) {
	        if (typeof priority === 'function') {
	            layoutFunc = priority;
	            priority = PRIORITY_VISUAL_LAYOUT;
	        }
	        if (true) {
	            if (isNaN(priority)) {
	                throw new Error('Unkown layout priority');
	            }
	        }
	        visualFuncs.push({
	            prio: priority,
	            func: layoutFunc,
	            isLayout: true
	        });
	    };

	    /**
	     * @param {number} [priority=3000]
	     * @param {Function} visualFunc
	     */
	    echarts.registerVisual = function (priority, visualFunc) {
	        if (typeof priority === 'function') {
	            visualFunc = priority;
	            priority = PRIORITY_VISUAL_CHART;
	        }
	        if (true) {
	            if (isNaN(priority)) {
	                throw new Error('Unkown visual priority');
	            }
	        }
	        visualFuncs.push({
	            prio: priority,
	            func: visualFunc
	        });
	    };

	    /**
	     * @param {string} name
	     */
	    echarts.registerLoading = function (name, loadingFx) {
	        loadingEffects[name] = loadingFx;
	    };


	    var parseClassType = ComponentModel.parseClassType;
	    /**
	     * @param {Object} opts
	     * @param {string} [superClass]
	     */
	    echarts.extendComponentModel = function (opts, superClass) {
	        var Clazz = ComponentModel;
	        if (superClass) {
	            var classType = parseClassType(superClass);
	            Clazz = ComponentModel.getClass(classType.main, classType.sub, true);
	        }
	        return Clazz.extend(opts);
	    };

	    /**
	     * @param {Object} opts
	     * @param {string} [superClass]
	     */
	    echarts.extendComponentView = function (opts, superClass) {
	        var Clazz = ComponentView;
	        if (superClass) {
	            var classType = parseClassType(superClass);
	            Clazz = ComponentView.getClass(classType.main, classType.sub, true);
	        }
	        return Clazz.extend(opts);
	    };

	    /**
	     * @param {Object} opts
	     * @param {string} [superClass]
	     */
	    echarts.extendSeriesModel = function (opts, superClass) {
	        var Clazz = SeriesModel;
	        if (superClass) {
	            superClass = 'series.' + superClass.replace('series.', '');
	            var classType = parseClassType(superClass);
	            Clazz = SeriesModel.getClass(classType.main, classType.sub, true);
	        }
	        return Clazz.extend(opts);
	    };

	    /**
	     * @param {Object} opts
	     * @param {string} [superClass]
	     */
	    echarts.extendChartView = function (opts, superClass) {
	        var Clazz = ChartView;
	        if (superClass) {
	            superClass.replace('series.', '');
	            var classType = parseClassType(superClass);
	            Clazz = ChartView.getClass(classType.main, true);
	        }
	        return Clazz.extend(opts);
	    };

	    /**
	     * ZRender need a canvas context to do measureText.
	     * But in node environment canvas may be created by node-canvas.
	     * So we need to specify how to create a canvas instead of using document.createElement('canvas')
	     *
	     * Be careful of using it in the browser.
	     *
	     * @param {Function} creator
	     * @example
	     *     var Canvas = require('canvas');
	     *     var echarts = require('echarts');
	     *     echarts.setCanvasCreator(function () {
	     *         // Small size is enough.
	     *         return new Canvas(32, 32);
	     *     });
	     */
	    echarts.setCanvasCreator = function (creator) {
	        zrUtil.createCanvas = creator;
	    };

	    echarts.registerVisual(PRIORITY_VISUAL_GLOBAL, __webpack_require__(93));
	    echarts.registerPreprocessor(__webpack_require__(94));
	    echarts.registerLoading('default', __webpack_require__(96));

	    // Default action
	    echarts.registerAction({
	        type: 'highlight',
	        event: 'highlight',
	        update: 'highlight'
	    }, zrUtil.noop);
	    echarts.registerAction({
	        type: 'downplay',
	        event: 'downplay',
	        update: 'downplay'
	    }, zrUtil.noop);


	    // --------
	    // Exports
	    // --------
	    //
	    echarts.List = __webpack_require__(97);
	    echarts.Model = __webpack_require__(12);

	    echarts.graphic = __webpack_require__(43);
	    echarts.number = __webpack_require__(7);
	    echarts.format = __webpack_require__(6);
	    echarts.matrix = __webpack_require__(11);
	    echarts.vector = __webpack_require__(10);
	    echarts.color = __webpack_require__(39);

	    echarts.util = {};
	    each([
	            'map', 'each', 'filter', 'indexOf', 'inherits',
	            'reduce', 'filter', 'bind', 'curry', 'isArray',
	            'isString', 'isObject', 'isFunction', 'extend', 'defaults'
	        ],
	        function (name) {
	            echarts.util[name] = zrUtil[name];
	        }
	    );

	    // PRIORITY
	    echarts.PRIORITY = {
	        PROCESSOR: {
	            FILTER: PRIORITY_PROCESSOR_FILTER,
	            STATISTIC: PRIORITY_PROCESSOR_STATISTIC
	        },
	        VISUAL: {
	            LAYOUT: PRIORITY_VISUAL_LAYOUT,
	            GLOBAL: PRIORITY_VISUAL_GLOBAL,
	            CHART: PRIORITY_VISUAL_CHART,
	            COMPONENT: PRIORITY_VISUAL_COMPONENT,
	            BRUSH: PRIORITY_VISUAL_BRUSH
	        }
	    };

	    module.exports = echarts;


/***/ },
/* 2 */
/***/ function(module, exports) {

	/**
	 * echarts设备环境识别
	 *
	 * @desc echarts基于Canvas，纯Javascript图表库，提供直观，生动，可交互，可个性化定制的数据统计图表。
	 * @author firede[firede@firede.us]
	 * @desc thanks zepto.
	 */

	    var env = {};
	    if (typeof navigator === 'undefined') {
	        // In node
	        env = {
	            browser: {},
	            os: {},
	            node: true,
	            // Assume canvas is supported
	            canvasSupported: true
	        };
	    }
	    else {
	        env = detect(navigator.userAgent);
	    }

	    module.exports = env;

	    // Zepto.js
	    // (c) 2010-2013 Thomas Fuchs
	    // Zepto.js may be freely distributed under the MIT license.

	    function detect(ua) {
	        var os = {};
	        var browser = {};
	        // var webkit = ua.match(/Web[kK]it[\/]{0,1}([\d.]+)/);
	        // var android = ua.match(/(Android);?[\s\/]+([\d.]+)?/);
	        // var ipad = ua.match(/(iPad).*OS\s([\d_]+)/);
	        // var ipod = ua.match(/(iPod)(.*OS\s([\d_]+))?/);
	        // var iphone = !ipad && ua.match(/(iPhone\sOS)\s([\d_]+)/);
	        // var webos = ua.match(/(webOS|hpwOS)[\s\/]([\d.]+)/);
	        // var touchpad = webos && ua.match(/TouchPad/);
	        // var kindle = ua.match(/Kindle\/([\d.]+)/);
	        // var silk = ua.match(/Silk\/([\d._]+)/);
	        // var blackberry = ua.match(/(BlackBerry).*Version\/([\d.]+)/);
	        // var bb10 = ua.match(/(BB10).*Version\/([\d.]+)/);
	        // var rimtabletos = ua.match(/(RIM\sTablet\sOS)\s([\d.]+)/);
	        // var playbook = ua.match(/PlayBook/);
	        // var chrome = ua.match(/Chrome\/([\d.]+)/) || ua.match(/CriOS\/([\d.]+)/);
	        var firefox = ua.match(/Firefox\/([\d.]+)/);
	        // var safari = webkit && ua.match(/Mobile\//) && !chrome;
	        // var webview = ua.match(/(iPhone|iPod|iPad).*AppleWebKit(?!.*Safari)/) && !chrome;
	        var ie = ua.match(/MSIE\s([\d.]+)/)
	            // IE 11 Trident/7.0; rv:11.0
	            || ua.match(/Trident\/.+?rv:(([\d.]+))/);
	        var edge = ua.match(/Edge\/([\d.]+)/); // IE 12 and 12+

	        // Todo: clean this up with a better OS/browser seperation:
	        // - discern (more) between multiple browsers on android
	        // - decide if kindle fire in silk mode is android or not
	        // - Firefox on Android doesn't specify the Android version
	        // - possibly devide in os, device and browser hashes

	        // if (browser.webkit = !!webkit) browser.version = webkit[1];

	        // if (android) os.android = true, os.version = android[2];
	        // if (iphone && !ipod) os.ios = os.iphone = true, os.version = iphone[2].replace(/_/g, '.');
	        // if (ipad) os.ios = os.ipad = true, os.version = ipad[2].replace(/_/g, '.');
	        // if (ipod) os.ios = os.ipod = true, os.version = ipod[3] ? ipod[3].replace(/_/g, '.') : null;
	        // if (webos) os.webos = true, os.version = webos[2];
	        // if (touchpad) os.touchpad = true;
	        // if (blackberry) os.blackberry = true, os.version = blackberry[2];
	        // if (bb10) os.bb10 = true, os.version = bb10[2];
	        // if (rimtabletos) os.rimtabletos = true, os.version = rimtabletos[2];
	        // if (playbook) browser.playbook = true;
	        // if (kindle) os.kindle = true, os.version = kindle[1];
	        // if (silk) browser.silk = true, browser.version = silk[1];
	        // if (!silk && os.android && ua.match(/Kindle Fire/)) browser.silk = true;
	        // if (chrome) browser.chrome = true, browser.version = chrome[1];
	        if (firefox) browser.firefox = true, browser.version = firefox[1];
	        // if (safari && (ua.match(/Safari/) || !!os.ios)) browser.safari = true;
	        // if (webview) browser.webview = true;
	        if (ie) {
	            browser.ie = true; browser.version = ie[1];
	        }
	        if (ie) {
	            browser.ie = true;
	            browser.version = ie[1];
	        }
	        if (edge) {
	            browser.edge = true;
	            browser.version = edge[1];
	        }

	        // os.tablet = !!(ipad || playbook || (android && !ua.match(/Mobile/)) ||
	        //     (firefox && ua.match(/Tablet/)) || (ie && !ua.match(/Phone/) && ua.match(/Touch/)));
	        // os.phone  = !!(!os.tablet && !os.ipod && (android || iphone || webos ||
	        //     (chrome && ua.match(/Android/)) || (chrome && ua.match(/CriOS\/([\d.]+)/)) ||
	        //     (firefox && ua.match(/Mobile/)) || (ie && ua.match(/Touch/))));

	        return {
	            browser: browser,
	            os: os,
	            node: false,
	            // 原生canvas支持，改极端点了
	            // canvasSupported : !(browser.ie && parseFloat(browser.version) < 9)
	            canvasSupported : document.createElement('canvas').getContext ? true : false,
	            // @see <http://stackoverflow.com/questions/4817029/whats-the-best-way-to-detect-a-touch-screen-device-using-javascript>
	            // works on most browsers
	            // IE10/11 does not support touch event, and MS Edge supports them but not by
	            // default, so we dont check navigator.maxTouchPoints for them here.
	            touchEventsSupported: 'ontouchstart' in window && !browser.ie && !browser.edge,
	            // <http://caniuse.com/#search=pointer%20event>.
	            pointerEventsSupported: 'onpointerdown' in window
	                // Firefox supports pointer but not by default,
	                // only MS browsers are reliable on pointer events currently.
	                && (browser.edge || (browser.ie && browser.version >= 10))
	        };
	    }


/***/ },
/* 3 */
/***/ function(module, exports, __webpack_require__) {

	/**
	 * ECharts global model
	 *
	 * @module {echarts/model/Global}
	 *
	 */



	    var zrUtil = __webpack_require__(4);
	    var modelUtil = __webpack_require__(5);
	    var Model = __webpack_require__(12);
	    var each = zrUtil.each;
	    var filter = zrUtil.filter;
	    var map = zrUtil.map;
	    var isArray = zrUtil.isArray;
	    var indexOf = zrUtil.indexOf;
	    var isObject = zrUtil.isObject;

	    var ComponentModel = __webpack_require__(19);

	    var globalDefault = __webpack_require__(23);

	    var OPTION_INNER_KEY = '\0_ec_inner';

	    /**
	     * @alias module:echarts/model/Global
	     *
	     * @param {Object} option
	     * @param {module:echarts/model/Model} parentModel
	     * @param {Object} theme
	     */
	    var GlobalModel = Model.extend({

	        constructor: GlobalModel,

	        init: function (option, parentModel, theme, optionManager) {
	            theme = theme || {};

	            this.option = null; // Mark as not initialized.

	            /**
	             * @type {module:echarts/model/Model}
	             * @private
	             */
	            this._theme = new Model(theme);

	            /**
	             * @type {module:echarts/model/OptionManager}
	             */
	            this._optionManager = optionManager;
	        },

	        setOption: function (option, optionPreprocessorFuncs) {
	            zrUtil.assert(
	                !(OPTION_INNER_KEY in option),
	                'please use chart.getOption()'
	            );

	            this._optionManager.setOption(option, optionPreprocessorFuncs);

	            this.resetOption();
	        },

	        /**
	         * @param {string} type null/undefined: reset all.
	         *                      'recreate': force recreate all.
	         *                      'timeline': only reset timeline option
	         *                      'media': only reset media query option
	         * @return {boolean} Whether option changed.
	         */
	        resetOption: function (type) {
	            var optionChanged = false;
	            var optionManager = this._optionManager;

	            if (!type || type === 'recreate') {
	                var baseOption = optionManager.mountOption(type === 'recreate');

	                if (!this.option || type === 'recreate') {
	                    initBase.call(this, baseOption);
	                }
	                else {
	                    this.restoreData();
	                    this.mergeOption(baseOption);
	                }
	                optionChanged = true;
	            }

	            if (type === 'timeline' || type === 'media') {
	                this.restoreData();
	            }

	            if (!type || type === 'recreate' || type === 'timeline') {
	                var timelineOption = optionManager.getTimelineOption(this);
	                timelineOption && (this.mergeOption(timelineOption), optionChanged = true);
	            }

	            if (!type || type === 'recreate' || type === 'media') {
	                var mediaOptions = optionManager.getMediaOption(this, this._api);
	                if (mediaOptions.length) {
	                    each(mediaOptions, function (mediaOption) {
	                        this.mergeOption(mediaOption, optionChanged = true);
	                    }, this);
	                }
	            }

	            return optionChanged;
	        },

	        /**
	         * @protected
	         */
	        mergeOption: function (newOption) {
	            var option = this.option;
	            var componentsMap = this._componentsMap;
	            var newCptTypes = [];

	            // 如果不存在对应的 component model 则直接 merge
	            each(newOption, function (componentOption, mainType) {
	                if (componentOption == null) {
	                    return;
	                }

	                if (!ComponentModel.hasClass(mainType)) {
	                    option[mainType] = option[mainType] == null
	                        ? zrUtil.clone(componentOption)
	                        : zrUtil.merge(option[mainType], componentOption, true);
	                }
	                else {
	                    newCptTypes.push(mainType);
	                }
	            });

	            // FIXME OPTION 同步是否要改回原来的
	            ComponentModel.topologicalTravel(
	                newCptTypes, ComponentModel.getAllClassMainTypes(), visitComponent, this
	            );

	            this._seriesIndices = this._seriesIndices || [];

	            function visitComponent(mainType, dependencies) {
	                var newCptOptionList = modelUtil.normalizeToArray(newOption[mainType]);

	                var mapResult = modelUtil.mappingToExists(
	                    componentsMap[mainType], newCptOptionList
	                );

	                makeKeyInfo(mainType, mapResult);

	                var dependentModels = getComponentsByTypes(
	                    componentsMap, dependencies
	                );

	                option[mainType] = [];
	                componentsMap[mainType] = [];

	                each(mapResult, function (resultItem, index) {
	                    var componentModel = resultItem.exist;
	                    var newCptOption = resultItem.option;

	                    zrUtil.assert(
	                        isObject(newCptOption) || componentModel,
	                        'Empty component definition'
	                    );

	                    // Consider where is no new option and should be merged using {},
	                    // see removeEdgeAndAdd in topologicalTravel and
	                    // ComponentModel.getAllClassMainTypes.
	                    if (!newCptOption) {
	                        componentModel.mergeOption({}, this);
	                        componentModel.optionUpdated({}, false);
	                    }
	                    else {
	                        var ComponentModelClass = ComponentModel.getClass(
	                            mainType, resultItem.keyInfo.subType, true
	                        );

	                        if (componentModel && componentModel instanceof ComponentModelClass) {
	                            componentModel.mergeOption(newCptOption, this);
	                            componentModel.optionUpdated(newCptOption, false);
	                        }
	                        else {
	                            // PENDING Global as parent ?
	                            var extraOpt = zrUtil.extend(
	                                {
	                                    dependentModels: dependentModels,
	                                    componentIndex: index
	                                },
	                                resultItem.keyInfo
	                            );
	                            componentModel = new ComponentModelClass(
	                                newCptOption, this, this, extraOpt
	                            );
	                            componentModel.init(newCptOption, this, this, extraOpt);
	                            // Call optionUpdated after init.
	                            // newCptOption has been used as componentModel.option
	                            // and may be merged with theme and default, so pass null
	                            // to avoid confusion.
	                            componentModel.optionUpdated(null, true);
	                        }
	                    }

	                    componentsMap[mainType][index] = componentModel;
	                    option[mainType][index] = componentModel.option;
	                }, this);

	                // Backup series for filtering.
	                if (mainType === 'series') {
	                    this._seriesIndices = createSeriesIndices(componentsMap.series);
	                }
	            }
	        },

	        /**
	         * Get option for output (cloned option and inner info removed)
	         * @public
	         * @return {Object}
	         */
	        getOption: function () {
	            var option = zrUtil.clone(this.option);

	            each(option, function (opts, mainType) {
	                if (ComponentModel.hasClass(mainType)) {
	                    var opts = modelUtil.normalizeToArray(opts);
	                    for (var i = opts.length - 1; i >= 0; i--) {
	                        // Remove options with inner id.
	                        if (modelUtil.isIdInner(opts[i])) {
	                            opts.splice(i, 1);
	                        }
	                    }
	                    option[mainType] = opts;
	                }
	            });

	            delete option[OPTION_INNER_KEY];

	            return option;
	        },

	        /**
	         * @return {module:echarts/model/Model}
	         */
	        getTheme: function () {
	            return this._theme;
	        },

	        /**
	         * @param {string} mainType
	         * @param {number} [idx=0]
	         * @return {module:echarts/model/Component}
	         */
	        getComponent: function (mainType, idx) {
	            var list = this._componentsMap[mainType];
	            if (list) {
	                return list[idx || 0];
	            }
	        },

	        /**
	         * @param {Object} condition
	         * @param {string} condition.mainType
	         * @param {string} [condition.subType] If ignore, only query by mainType
	         * @param {number} [condition.index] Either input index or id or name.
	         * @param {string} [condition.id] Either input index or id or name.
	         * @param {string} [condition.name] Either input index or id or name.
	         * @return {Array.<module:echarts/model/Component>}
	         */
	        queryComponents: function (condition) {
	            var mainType = condition.mainType;
	            if (!mainType) {
	                return [];
	            }

	            var index = condition.index;
	            var id = condition.id;
	            var name = condition.name;

	            var cpts = this._componentsMap[mainType];

	            if (!cpts || !cpts.length) {
	                return [];
	            }

	            var result;

	            if (index != null) {
	                if (!isArray(index)) {
	                    index = [index];
	                }
	                result = filter(map(index, function (idx) {
	                    return cpts[idx];
	                }), function (val) {
	                    return !!val;
	                });
	            }
	            else if (id != null) {
	                var isIdArray = isArray(id);
	                result = filter(cpts, function (cpt) {
	                    return (isIdArray && indexOf(id, cpt.id) >= 0)
	                        || (!isIdArray && cpt.id === id);
	                });
	            }
	            else if (name != null) {
	                var isNameArray = isArray(name);
	                result = filter(cpts, function (cpt) {
	                    return (isNameArray && indexOf(name, cpt.name) >= 0)
	                        || (!isNameArray && cpt.name === name);
	                });
	            }
	            else {
	                // Return all components with mainType
	                result = cpts;
	            }

	            return filterBySubType(result, condition);
	        },

	        /**
	         * The interface is different from queryComponents,
	         * which is convenient for inner usage.
	         *
	         * @usage
	         * var result = findComponents(
	         *     {mainType: 'dataZoom', query: {dataZoomId: 'abc'}}
	         * );
	         * var result = findComponents(
	         *     {mainType: 'series', subType: 'pie', query: {seriesName: 'uio'}}
	         * );
	         * var result = findComponents(
	         *     {mainType: 'series'},
	         *     function (model, index) {...}
	         * );
	         * // result like [component0, componnet1, ...]
	         *
	         * @param {Object} condition
	         * @param {string} condition.mainType Mandatory.
	         * @param {string} [condition.subType] Optional.
	         * @param {Object} [condition.query] like {xxxIndex, xxxId, xxxName},
	         *        where xxx is mainType.
	         *        If query attribute is null/undefined or has no index/id/name,
	         *        do not filtering by query conditions, which is convenient for
	         *        no-payload situations or when target of action is global.
	         * @param {Function} [condition.filter] parameter: component, return boolean.
	         * @return {Array.<module:echarts/model/Component>}
	         */
	        findComponents: function (condition) {
	            var query = condition.query;
	            var mainType = condition.mainType;

	            var queryCond = getQueryCond(query);
	            var result = queryCond
	                ? this.queryComponents(queryCond)
	                : this._componentsMap[mainType];

	            return doFilter(filterBySubType(result, condition));

	            function getQueryCond(q) {
	                var indexAttr = mainType + 'Index';
	                var idAttr = mainType + 'Id';
	                var nameAttr = mainType + 'Name';
	                return q && (
	                        q.hasOwnProperty(indexAttr)
	                        || q.hasOwnProperty(idAttr)
	                        || q.hasOwnProperty(nameAttr)
	                    )
	                    ? {
	                        mainType: mainType,
	                        // subType will be filtered finally.
	                        index: q[indexAttr],
	                        id: q[idAttr],
	                        name: q[nameAttr]
	                    }
	                    : null;
	            }

	            function doFilter(res) {
	                return condition.filter
	                     ? filter(res, condition.filter)
	                     : res;
	            }
	        },

	        /**
	         * @usage
	         * eachComponent('legend', function (legendModel, index) {
	         *     ...
	         * });
	         * eachComponent(function (componentType, model, index) {
	         *     // componentType does not include subType
	         *     // (componentType is 'xxx' but not 'xxx.aa')
	         * });
	         * eachComponent(
	         *     {mainType: 'dataZoom', query: {dataZoomId: 'abc'}},
	         *     function (model, index) {...}
	         * );
	         * eachComponent(
	         *     {mainType: 'series', subType: 'pie', query: {seriesName: 'uio'}},
	         *     function (model, index) {...}
	         * );
	         *
	         * @param {string|Object=} mainType When mainType is object, the definition
	         *                                  is the same as the method 'findComponents'.
	         * @param {Function} cb
	         * @param {*} context
	         */
	        eachComponent: function (mainType, cb, context) {
	            var componentsMap = this._componentsMap;

	            if (typeof mainType === 'function') {
	                context = cb;
	                cb = mainType;
	                each(componentsMap, function (components, componentType) {
	                    each(components, function (component, index) {
	                        cb.call(context, componentType, component, index);
	                    });
	                });
	            }
	            else if (zrUtil.isString(mainType)) {
	                each(componentsMap[mainType], cb, context);
	            }
	            else if (isObject(mainType)) {
	                var queryResult = this.findComponents(mainType);
	                each(queryResult, cb, context);
	            }
	        },

	        /**
	         * @param {string} name
	         * @return {Array.<module:echarts/model/Series>}
	         */
	        getSeriesByName: function (name) {
	            var series = this._componentsMap.series;
	            return filter(series, function (oneSeries) {
	                return oneSeries.name === name;
	            });
	        },

	        /**
	         * @param {number} seriesIndex
	         * @return {module:echarts/model/Series}
	         */
	        getSeriesByIndex: function (seriesIndex) {
	            return this._componentsMap.series[seriesIndex];
	        },

	        /**
	         * @param {string} subType
	         * @return {Array.<module:echarts/model/Series>}
	         */
	        getSeriesByType: function (subType) {
	            var series = this._componentsMap.series;
	            return filter(series, function (oneSeries) {
	                return oneSeries.subType === subType;
	            });
	        },

	        /**
	         * @return {Array.<module:echarts/model/Series>}
	         */
	        getSeries: function () {
	            return this._componentsMap.series.slice();
	        },

	        /**
	         * After filtering, series may be different
	         * frome raw series.
	         *
	         * @param {Function} cb
	         * @param {*} context
	         */
	        eachSeries: function (cb, context) {
	            assertSeriesInitialized(this);
	            each(this._seriesIndices, function (rawSeriesIndex) {
	                var series = this._componentsMap.series[rawSeriesIndex];
	                cb.call(context, series, rawSeriesIndex);
	            }, this);
	        },

	        /**
	         * Iterate raw series before filtered.
	         *
	         * @param {Function} cb
	         * @param {*} context
	         */
	        eachRawSeries: function (cb, context) {
	            each(this._componentsMap.series, cb, context);
	        },

	        /**
	         * After filtering, series may be different.
	         * frome raw series.
	         *
	         * @parma {string} subType
	         * @param {Function} cb
	         * @param {*} context
	         */
	        eachSeriesByType: function (subType, cb, context) {
	            assertSeriesInitialized(this);
	            each(this._seriesIndices, function (rawSeriesIndex) {
	                var series = this._componentsMap.series[rawSeriesIndex];
	                if (series.subType === subType) {
	                    cb.call(context, series, rawSeriesIndex);
	                }
	            }, this);
	        },

	        /**
	         * Iterate raw series before filtered of given type.
	         *
	         * @parma {string} subType
	         * @param {Function} cb
	         * @param {*} context
	         */
	        eachRawSeriesByType: function (subType, cb, context) {
	            return each(this.getSeriesByType(subType), cb, context);
	        },

	        /**
	         * @param {module:echarts/model/Series} seriesModel
	         */
	        isSeriesFiltered: function (seriesModel) {
	            assertSeriesInitialized(this);
	            return zrUtil.indexOf(this._seriesIndices, seriesModel.componentIndex) < 0;
	        },

	        /**
	         * @param {Function} cb
	         * @param {*} context
	         */
	        filterSeries: function (cb, context) {
	            assertSeriesInitialized(this);
	            var filteredSeries = filter(
	                this._componentsMap.series, cb, context
	            );
	            this._seriesIndices = createSeriesIndices(filteredSeries);
	        },

	        restoreData: function () {
	            var componentsMap = this._componentsMap;

	            this._seriesIndices = createSeriesIndices(componentsMap.series);

	            var componentTypes = [];
	            each(componentsMap, function (components, componentType) {
	                componentTypes.push(componentType);
	            });

	            ComponentModel.topologicalTravel(
	                componentTypes,
	                ComponentModel.getAllClassMainTypes(),
	                function (componentType, dependencies) {
	                    each(componentsMap[componentType], function (component) {
	                        component.restoreData();
	                    });
	                }
	            );
	        }

	    });

	    /**
	     * @inner
	     */
	    function mergeTheme(option, theme) {
	        for (var name in theme) {
	            // 如果有 component model 则把具体的 merge 逻辑交给该 model 处理
	            if (!ComponentModel.hasClass(name)) {
	                if (typeof theme[name] === 'object') {
	                    option[name] = !option[name]
	                        ? zrUtil.clone(theme[name])
	                        : zrUtil.merge(option[name], theme[name], false);
	                }
	                else {
	                    if (option[name] == null) {
	                        option[name] = theme[name];
	                    }
	                }
	            }
	        }
	    }

	    function initBase(baseOption) {
	        baseOption = baseOption;

	        // Using OPTION_INNER_KEY to mark that this option can not be used outside,
	        // i.e. `chart.setOption(chart.getModel().option);` is forbiden.
	        this.option = {};
	        this.option[OPTION_INNER_KEY] = 1;

	        /**
	         * @type {Object.<string, Array.<module:echarts/model/Model>>}
	         * @private
	         */
	        this._componentsMap = {};

	        /**
	         * Mapping between filtered series list and raw series list.
	         * key: filtered series indices, value: raw series indices.
	         * @type {Array.<nubmer>}
	         * @private
	         */
	        this._seriesIndices = null;

	        mergeTheme(baseOption, this._theme.option);

	        // TODO Needs clone when merging to the unexisted property
	        zrUtil.merge(baseOption, globalDefault, false);

	        this.mergeOption(baseOption);
	    }

	    /**
	     * @inner
	     * @param {Array.<string>|string} types model types
	     * @return {Object} key: {string} type, value: {Array.<Object>} models
	     */
	    function getComponentsByTypes(componentsMap, types) {
	        if (!zrUtil.isArray(types)) {
	            types = types ? [types] : [];
	        }

	        var ret = {};
	        each(types, function (type) {
	            ret[type] = (componentsMap[type] || []).slice();
	        });

	        return ret;
	    }

	    /**
	     * @inner
	     */
	    function makeKeyInfo(mainType, mapResult) {
	        // We use this id to hash component models and view instances
	        // in echarts. id can be specified by user, or auto generated.

	        // The id generation rule ensures new view instance are able
	        // to mapped to old instance when setOption are called in
	        // no-merge mode. So we generate model id by name and plus
	        // type in view id.

	        // name can be duplicated among components, which is convenient
	        // to specify multi components (like series) by one name.

	        // Ensure that each id is distinct.
	        var idMap = {};

	        each(mapResult, function (item, index) {
	            var existCpt = item.exist;
	            existCpt && (idMap[existCpt.id] = item);
	        });

	        each(mapResult, function (item, index) {
	            var opt = item.option;

	            zrUtil.assert(
	                !opt || opt.id == null || !idMap[opt.id] || idMap[opt.id] === item,
	                'id duplicates: ' + (opt && opt.id)
	            );

	            opt && opt.id != null && (idMap[opt.id] = item);

	            // Complete subType
	            if (isObject(opt)) {
	                var subType = determineSubType(mainType, opt, item.exist);
	                item.keyInfo = {mainType: mainType, subType: subType};
	            }
	        });

	        // Make name and id.
	        each(mapResult, function (item, index) {
	            var existCpt = item.exist;
	            var opt = item.option;
	            var keyInfo = item.keyInfo;

	            if (!isObject(opt)) {
	                return;
	            }

	            // name can be overwitten. Consider case: axis.name = '20km'.
	            // But id generated by name will not be changed, which affect
	            // only in that case: setOption with 'not merge mode' and view
	            // instance will be recreated, which can be accepted.
	            keyInfo.name = opt.name != null
	                ? opt.name + ''
	                : existCpt
	                ? existCpt.name
	                : '\0-';

	            if (existCpt) {
	                keyInfo.id = existCpt.id;
	            }
	            else if (opt.id != null) {
	                keyInfo.id = opt.id + '';
	            }
	            else {
	                // Consider this situatoin:
	                //  optionA: [{name: 'a'}, {name: 'a'}, {..}]
	                //  optionB [{..}, {name: 'a'}, {name: 'a'}]
	                // Series with the same name between optionA and optionB
	                // should be mapped.
	                var idNum = 0;
	                do {
	                    keyInfo.id = '\0' + keyInfo.name + '\0' + idNum++;
	                }
	                while (idMap[keyInfo.id]);
	            }

	            idMap[keyInfo.id] = item;
	        });
	    }

	    /**
	     * @inner
	     */
	    function determineSubType(mainType, newCptOption, existComponent) {
	        var subType = newCptOption.type
	            ? newCptOption.type
	            : existComponent
	            ? existComponent.subType
	            // Use determineSubType only when there is no existComponent.
	            : ComponentModel.determineSubType(mainType, newCptOption);

	        // tooltip, markline, markpoint may always has no subType
	        return subType;
	    }

	    /**
	     * @inner
	     */
	    function createSeriesIndices(seriesModels) {
	        return map(seriesModels, function (series) {
	            return series.componentIndex;
	        }) || [];
	    }

	    /**
	     * @inner
	     */
	    function filterBySubType(components, condition) {
	        // Using hasOwnProperty for restrict. Consider
	        // subType is undefined in user payload.
	        return condition.hasOwnProperty('subType')
	            ? filter(components, function (cpt) {
	                return cpt.subType === condition.subType;
	            })
	            : components;
	    }

	    /**
	     * @inner
	     */
	    function assertSeriesInitialized(ecModel) {
	        // Components that use _seriesIndices should depends on series component,
	        // which make sure that their initialization is after series.
	        if (true) {
	            if (!ecModel._seriesIndices) {
	                throw new Error('Series has not been initialized yet.');
	            }
	        }
	    }

	    zrUtil.mixin(GlobalModel, __webpack_require__(24));

	    module.exports = GlobalModel;


/***/ },
/* 4 */
/***/ function(module, exports) {

	/**
	 * @module zrender/core/util
	 */


	    // 用于处理merge时无法遍历Date等对象的问题
	    var BUILTIN_OBJECT = {
	        '[object Function]': 1,
	        '[object RegExp]': 1,
	        '[object Date]': 1,
	        '[object Error]': 1,
	        '[object CanvasGradient]': 1,
	        '[object CanvasPattern]': 1,
	        // In node-canvas Image can be Canvas.Image
	        '[object Image]': 1
	    };

	    var objToString = Object.prototype.toString;

	    var arrayProto = Array.prototype;
	    var nativeForEach = arrayProto.forEach;
	    var nativeFilter = arrayProto.filter;
	    var nativeSlice = arrayProto.slice;
	    var nativeMap = arrayProto.map;
	    var nativeReduce = arrayProto.reduce;

	    /**
	     * @param {*} source
	     * @return {*} 拷贝后的新对象
	     */
	    function clone(source) {
	        if (typeof source == 'object' && source !== null) {
	            var result = source;
	            if (source instanceof Array) {
	                result = [];
	                for (var i = 0, len = source.length; i < len; i++) {
	                    result[i] = clone(source[i]);
	                }
	            }
	            else if (
	                !isBuildInObject(source)
	                // 是否为 dom 对象
	                && !isDom(source)
	            ) {
	                result = {};
	                for (var key in source) {
	                    if (source.hasOwnProperty(key)) {
	                        result[key] = clone(source[key]);
	                    }
	                }
	            }

	            return result;
	        }

	        return source;
	    }

	    /**
	     * @memberOf module:zrender/core/util
	     * @param {*} target
	     * @param {*} source
	     * @param {boolean} [overwrite=false]
	     */
	    function merge(target, source, overwrite) {
	        // We should escapse that source is string
	        // and enter for ... in ...
	        if (!isObject(source) || !isObject(target)) {
	            return overwrite ? clone(source) : target;
	        }

	        for (var key in source) {
	            if (source.hasOwnProperty(key)) {
	                var targetProp = target[key];
	                var sourceProp = source[key];

	                if (isObject(sourceProp)
	                    && isObject(targetProp)
	                    && !isArray(sourceProp)
	                    && !isArray(targetProp)
	                    && !isDom(sourceProp)
	                    && !isDom(targetProp)
	                    && !isBuildInObject(sourceProp)
	                    && !isBuildInObject(targetProp)
	                ) {
	                    // 如果需要递归覆盖，就递归调用merge
	                    merge(targetProp, sourceProp, overwrite);
	                }
	                else if (overwrite || !(key in target)) {
	                    // 否则只处理overwrite为true，或者在目标对象中没有此属性的情况
	                    // NOTE，在 target[key] 不存在的时候也是直接覆盖
	                    target[key] = clone(source[key], true);
	                }
	            }
	        }

	        return target;
	    }

	    /**
	     * @param {Array} targetAndSources The first item is target, and the rests are source.
	     * @param {boolean} [overwrite=false]
	     * @return {*} target
	     */
	    function mergeAll(targetAndSources, overwrite) {
	        var result = targetAndSources[0];
	        for (var i = 1, len = targetAndSources.length; i < len; i++) {
	            result = merge(result, targetAndSources[i], overwrite);
	        }
	        return result;
	    }

	    /**
	     * @param {*} target
	     * @param {*} source
	     * @memberOf module:zrender/core/util
	     */
	    function extend(target, source) {
	        for (var key in source) {
	            if (source.hasOwnProperty(key)) {
	                target[key] = source[key];
	            }
	        }
	        return target;
	    }

	    /**
	     * @param {*} target
	     * @param {*} source
	     * @param {boolen} [overlay=false]
	     * @memberOf module:zrender/core/util
	     */
	    function defaults(target, source, overlay) {
	        for (var key in source) {
	            if (source.hasOwnProperty(key)
	                && (overlay ? source[key] != null : target[key] == null)
	            ) {
	                target[key] = source[key];
	            }
	        }
	        return target;
	    }

	    function createCanvas() {
	        return document.createElement('canvas');
	    }
	    // FIXME
	    var _ctx;
	    function getContext() {
	        if (!_ctx) {
	            // Use util.createCanvas instead of createCanvas
	            // because createCanvas may be overwritten in different environment
	            _ctx = util.createCanvas().getContext('2d');
	        }
	        return _ctx;
	    }

	    /**
	     * 查询数组中元素的index
	     * @memberOf module:zrender/core/util
	     */
	    function indexOf(array, value) {
	        if (array) {
	            if (array.indexOf) {
	                return array.indexOf(value);
	            }
	            for (var i = 0, len = array.length; i < len; i++) {
	                if (array[i] === value) {
	                    return i;
	                }
	            }
	        }
	        return -1;
	    }

	    /**
	     * 构造类继承关系
	     *
	     * @memberOf module:zrender/core/util
	     * @param {Function} clazz 源类
	     * @param {Function} baseClazz 基类
	     */
	    function inherits(clazz, baseClazz) {
	        var clazzPrototype = clazz.prototype;
	        function F() {}
	        F.prototype = baseClazz.prototype;
	        clazz.prototype = new F();

	        for (var prop in clazzPrototype) {
	            clazz.prototype[prop] = clazzPrototype[prop];
	        }
	        clazz.prototype.constructor = clazz;
	        clazz.superClass = baseClazz;
	    }

	    /**
	     * @memberOf module:zrender/core/util
	     * @param {Object|Function} target
	     * @param {Object|Function} sorce
	     * @param {boolean} overlay
	     */
	    function mixin(target, source, overlay) {
	        target = 'prototype' in target ? target.prototype : target;
	        source = 'prototype' in source ? source.prototype : source;

	        defaults(target, source, overlay);
	    }

	    /**
	     * @param {Array|TypedArray} data
	     */
	    function isArrayLike(data) {
	        if (! data) {
	            return;
	        }
	        if (typeof data == 'string') {
	            return false;
	        }
	        return typeof data.length == 'number';
	    }

	    /**
	     * 数组或对象遍历
	     * @memberOf module:zrender/core/util
	     * @param {Object|Array} obj
	     * @param {Function} cb
	     * @param {*} [context]
	     */
	    function each(obj, cb, context) {
	        if (!(obj && cb)) {
	            return;
	        }
	        if (obj.forEach && obj.forEach === nativeForEach) {
	            obj.forEach(cb, context);
	        }
	        else if (obj.length === +obj.length) {
	            for (var i = 0, len = obj.length; i < len; i++) {
	                cb.call(context, obj[i], i, obj);
	            }
	        }
	        else {
	            for (var key in obj) {
	                if (obj.hasOwnProperty(key)) {
	                    cb.call(context, obj[key], key, obj);
	                }
	            }
	        }
	    }

	    /**
	     * 数组映射
	     * @memberOf module:zrender/core/util
	     * @param {Array} obj
	     * @param {Function} cb
	     * @param {*} [context]
	     * @return {Array}
	     */
	    function map(obj, cb, context) {
	        if (!(obj && cb)) {
	            return;
	        }
	        if (obj.map && obj.map === nativeMap) {
	            return obj.map(cb, context);
	        }
	        else {
	            var result = [];
	            for (var i = 0, len = obj.length; i < len; i++) {
	                result.push(cb.call(context, obj[i], i, obj));
	            }
	            return result;
	        }
	    }

	    /**
	     * @memberOf module:zrender/core/util
	     * @param {Array} obj
	     * @param {Function} cb
	     * @param {Object} [memo]
	     * @param {*} [context]
	     * @return {Array}
	     */
	    function reduce(obj, cb, memo, context) {
	        if (!(obj && cb)) {
	            return;
	        }
	        if (obj.reduce && obj.reduce === nativeReduce) {
	            return obj.reduce(cb, memo, context);
	        }
	        else {
	            for (var i = 0, len = obj.length; i < len; i++) {
	                memo = cb.call(context, memo, obj[i], i, obj);
	            }
	            return memo;
	        }
	    }

	    /**
	     * 数组过滤
	     * @memberOf module:zrender/core/util
	     * @param {Array} obj
	     * @param {Function} cb
	     * @param {*} [context]
	     * @return {Array}
	     */
	    function filter(obj, cb, context) {
	        if (!(obj && cb)) {
	            return;
	        }
	        if (obj.filter && obj.filter === nativeFilter) {
	            return obj.filter(cb, context);
	        }
	        else {
	            var result = [];
	            for (var i = 0, len = obj.length; i < len; i++) {
	                if (cb.call(context, obj[i], i, obj)) {
	                    result.push(obj[i]);
	                }
	            }
	            return result;
	        }
	    }

	    /**
	     * 数组项查找
	     * @memberOf module:zrender/core/util
	     * @param {Array} obj
	     * @param {Function} cb
	     * @param {*} [context]
	     * @return {Array}
	     */
	    function find(obj, cb, context) {
	        if (!(obj && cb)) {
	            return;
	        }
	        for (var i = 0, len = obj.length; i < len; i++) {
	            if (cb.call(context, obj[i], i, obj)) {
	                return obj[i];
	            }
	        }
	    }

	    /**
	     * @memberOf module:zrender/core/util
	     * @param {Function} func
	     * @param {*} context
	     * @return {Function}
	     */
	    function bind(func, context) {
	        var args = nativeSlice.call(arguments, 2);
	        return function () {
	            return func.apply(context, args.concat(nativeSlice.call(arguments)));
	        };
	    }

	    /**
	     * @memberOf module:zrender/core/util
	     * @param {Function} func
	     * @return {Function}
	     */
	    function curry(func) {
	        var args = nativeSlice.call(arguments, 1);
	        return function () {
	            return func.apply(this, args.concat(nativeSlice.call(arguments)));
	        };
	    }

	    /**
	     * @memberOf module:zrender/core/util
	     * @param {*} value
	     * @return {boolean}
	     */
	    function isArray(value) {
	        return objToString.call(value) === '[object Array]';
	    }

	    /**
	     * @memberOf module:zrender/core/util
	     * @param {*} value
	     * @return {boolean}
	     */
	    function isFunction(value) {
	        return typeof value === 'function';
	    }

	    /**
	     * @memberOf module:zrender/core/util
	     * @param {*} value
	     * @return {boolean}
	     */
	    function isString(value) {
	        return objToString.call(value) === '[object String]';
	    }

	    /**
	     * @memberOf module:zrender/core/util
	     * @param {*} value
	     * @return {boolean}
	     */
	    function isObject(value) {
	        // Avoid a V8 JIT bug in Chrome 19-20.
	        // See https://code.google.com/p/v8/issues/detail?id=2291 for more details.
	        var type = typeof value;
	        return type === 'function' || (!!value && type == 'object');
	    }

	    /**
	     * @memberOf module:zrender/core/util
	     * @param {*} value
	     * @return {boolean}
	     */
	    function isBuildInObject(value) {
	        return !!BUILTIN_OBJECT[objToString.call(value)];
	    }

	    /**
	     * @memberOf module:zrender/core/util
	     * @param {*} value
	     * @return {boolean}
	     */
	    function isDom(value) {
	        return value && value.nodeType === 1
	               && typeof(value.nodeName) == 'string';
	    }

	    /**
	     * If value1 is not null, then return value1, otherwise judget rest of values.
	     * @memberOf module:zrender/core/util
	     * @return {*} Final value
	     */
	    function retrieve(values) {
	        for (var i = 0, len = arguments.length; i < len; i++) {
	            if (arguments[i] != null) {
	                return arguments[i];
	            }
	        }
	    }

	    /**
	     * @memberOf module:zrender/core/util
	     * @param {Array} arr
	     * @param {number} startIndex
	     * @param {number} endIndex
	     * @return {Array}
	     */
	    function slice() {
	        return Function.call.apply(nativeSlice, arguments);
	    }

	    /**
	     * @memberOf module:zrender/core/util
	     * @param {boolean} condition
	     * @param {string} message
	     */
	    function assert(condition, message) {
	        if (!condition) {
	            throw new Error(message);
	        }
	    }

	    var util = {
	        inherits: inherits,
	        mixin: mixin,
	        clone: clone,
	        merge: merge,
	        mergeAll: mergeAll,
	        extend: extend,
	        defaults: defaults,
	        getContext: getContext,
	        createCanvas: createCanvas,
	        indexOf: indexOf,
	        slice: slice,
	        find: find,
	        isArrayLike: isArrayLike,
	        each: each,
	        map: map,
	        reduce: reduce,
	        filter: filter,
	        bind: bind,
	        curry: curry,
	        isArray: isArray,
	        isString: isString,
	        isObject: isObject,
	        isFunction: isFunction,
	        isBuildInObject: isBuildInObject,
	        isDom: isDom,
	        retrieve: retrieve,
	        assert: assert,
	        noop: function () {}
	    };
	    module.exports = util;



/***/ },
/* 5 */
/***/ function(module, exports, __webpack_require__) {

	

	    var formatUtil = __webpack_require__(6);
	    var nubmerUtil = __webpack_require__(7);
	    var Model = __webpack_require__(12);
	    var zrUtil = __webpack_require__(4);

	    var modelUtil = {};

	    /**
	     * If value is not array, then translate it to array.
	     * @param  {*} value
	     * @return {Array} [value] or value
	     */
	    modelUtil.normalizeToArray = function (value) {
	        return value instanceof Array
	            ? value
	            : value == null
	            ? []
	            : [value];
	    };

	    /**
	     * Sync default option between normal and emphasis like `position` and `show`
	     * In case some one will write code like
	     *     label: {
	     *         normal: {
	     *             show: false,
	     *             position: 'outside',
	     *             textStyle: {
	     *                 fontSize: 18
	     *             }
	     *         },
	     *         emphasis: {
	     *             show: true
	     *         }
	     *     }
	     * @param {Object} opt
	     * @param {Array.<string>} subOpts
	     */
	     modelUtil.defaultEmphasis = function (opt, subOpts) {
	        if (opt) {
	            var emphasisOpt = opt.emphasis = opt.emphasis || {};
	            var normalOpt = opt.normal = opt.normal || {};

	            // Default emphasis option from normal
	            zrUtil.each(subOpts, function (subOptName) {
	                var val = zrUtil.retrieve(emphasisOpt[subOptName], normalOpt[subOptName]);
	                if (val != null) {
	                    emphasisOpt[subOptName] = val;
	                }
	            });
	        }
	    };

	    modelUtil.LABEL_OPTIONS = ['position', 'show', 'textStyle', 'distance', 'formatter'];

	    /**
	     * data could be [12, 2323, {value: 223}, [1221, 23], {value: [2, 23]}]
	     * This helper method retieves value from data.
	     * @param {string|number|Date|Array|Object} dataItem
	     * @return {number|string|Date|Array.<number|string|Date>}
	     */
	    modelUtil.getDataItemValue = function (dataItem) {
	        // Performance sensitive.
	        return dataItem && (dataItem.value == null ? dataItem : dataItem.value);
	    };

	    /**
	     * data could be [12, 2323, {value: 223}, [1221, 23], {value: [2, 23]}]
	     * This helper method determine if dataItem has extra option besides value
	     * @param {string|number|Date|Array|Object} dataItem
	     */
	    modelUtil.isDataItemOption = function (dataItem) {
	        return zrUtil.isObject(dataItem)
	            && !(dataItem instanceof Array);
	            // // markLine data can be array
	            // && !(dataItem[0] && zrUtil.isObject(dataItem[0]) && !(dataItem[0] instanceof Array));
	    };

	    /**
	     * This helper method convert value in data.
	     * @param {string|number|Date} value
	     * @param {Object|string} [dimInfo] If string (like 'x'), dimType defaults 'number'.
	     */
	    modelUtil.converDataValue = function (value, dimInfo) {
	        // Performance sensitive.
	        var dimType = dimInfo && dimInfo.type;
	        if (dimType === 'ordinal') {
	            return value;
	        }

	        if (dimType === 'time' && !isFinite(value) && value != null && value !== '-') {
	            value = +nubmerUtil.parseDate(value);
	        }

	        // dimType defaults 'number'.
	        // If dimType is not ordinal and value is null or undefined or NaN or '-',
	        // parse to NaN.
	        return (value == null || value === '')
	            ? NaN : +value; // If string (like '-'), using '+' parse to NaN
	    };

	    /**
	     * Create a model proxy to be used in tooltip for edge data, markLine data, markPoint data.
	     * @param {module:echarts/data/List} data
	     * @param {Object} opt
	     * @param {string} [opt.seriesIndex]
	     * @param {Object} [opt.name]
	     * @param {Object} [opt.mainType]
	     * @param {Object} [opt.subType]
	     */
	    modelUtil.createDataFormatModel = function (data, opt) {
	        var model = new Model();
	        zrUtil.mixin(model, modelUtil.dataFormatMixin);
	        model.seriesIndex = opt.seriesIndex;
	        model.name = opt.name || '';
	        model.mainType = opt.mainType;
	        model.subType = opt.subType;

	        model.getData = function () {
	            return data;
	        };
	        return model;
	    };

	    // PENDING A little ugly
	    modelUtil.dataFormatMixin = {
	        /**
	         * Get params for formatter
	         * @param {number} dataIndex
	         * @param {string} [dataType]
	         * @return {Object}
	         */
	        getDataParams: function (dataIndex, dataType) {
	            var data = this.getData(dataType);

	            var seriesIndex = this.seriesIndex;
	            var seriesName = this.name;

	            var rawValue = this.getRawValue(dataIndex, dataType);
	            var rawDataIndex = data.getRawIndex(dataIndex);
	            var name = data.getName(dataIndex, true);
	            var itemOpt = data.getRawDataItem(dataIndex);

	            return {
	                componentType: this.mainType,
	                componentSubType: this.subType,
	                seriesType: this.mainType === 'series' ? this.subType : null,
	                seriesIndex: seriesIndex,
	                seriesName: seriesName,
	                name: name,
	                dataIndex: rawDataIndex,
	                data: itemOpt,
	                dataType: dataType,
	                value: rawValue,
	                color: data.getItemVisual(dataIndex, 'color'),

	                // Param name list for mapping `a`, `b`, `c`, `d`, `e`
	                $vars: ['seriesName', 'name', 'value']
	            };
	        },

	        /**
	         * Format label
	         * @param {number} dataIndex
	         * @param {string} [status='normal'] 'normal' or 'emphasis'
	         * @param {string} [dataType]
	         * @param {number} [dimIndex]
	         * @return {string}
	         */
	        getFormattedLabel: function (dataIndex, status, dataType, dimIndex) {
	            status = status || 'normal';
	            var data = this.getData(dataType);
	            var itemModel = data.getItemModel(dataIndex);

	            var params = this.getDataParams(dataIndex, dataType);
	            if (dimIndex != null && (params.value instanceof Array)) {
	                params.value = params.value[dimIndex];
	            }

	            var formatter = itemModel.get(['label', status, 'formatter']);

	            if (typeof formatter === 'function') {
	                params.status = status;
	                return formatter(params);
	            }
	            else if (typeof formatter === 'string') {
	                return formatUtil.formatTpl(formatter, params);
	            }
	        },

	        /**
	         * Get raw value in option
	         * @param {number} idx
	         * @param {string} [dataType]
	         * @return {Object}
	         */
	        getRawValue: function (idx, dataType) {
	            var data = this.getData(dataType);
	            var dataItem = data.getRawDataItem(idx);
	            if (dataItem != null) {
	                return (zrUtil.isObject(dataItem) && !(dataItem instanceof Array))
	                    ? dataItem.value : dataItem;
	            }
	        },

	        /**
	         * Should be implemented.
	         * @param {number} dataIndex
	         * @param {boolean} [multipleSeries=false]
	         * @param {number} [dataType]
	         * @return {string} tooltip string
	         */
	        formatTooltip: zrUtil.noop
	    };

	    /**
	     * Mapping to exists for merge.
	     *
	     * @public
	     * @param {Array.<Object>|Array.<module:echarts/model/Component>} exists
	     * @param {Object|Array.<Object>} newCptOptions
	     * @return {Array.<Object>} Result, like [{exist: ..., option: ...}, {}],
	     *                          which order is the same as exists.
	     */
	    modelUtil.mappingToExists = function (exists, newCptOptions) {
	        // Mapping by the order by original option (but not order of
	        // new option) in merge mode. Because we should ensure
	        // some specified index (like xAxisIndex) is consistent with
	        // original option, which is easy to understand, espatially in
	        // media query. And in most case, merge option is used to
	        // update partial option but not be expected to change order.
	        newCptOptions = (newCptOptions || []).slice();

	        var result = zrUtil.map(exists || [], function (obj, index) {
	            return {exist: obj};
	        });

	        // Mapping by id or name if specified.
	        zrUtil.each(newCptOptions, function (cptOption, index) {
	            if (!zrUtil.isObject(cptOption)) {
	                return;
	            }

	            // id has highest priority.
	            for (var i = 0; i < result.length; i++) {
	                if (!result[i].option // Consider name: two map to one.
	                    && cptOption.id != null
	                    && result[i].exist.id === cptOption.id + ''
	                ) {
	                    result[i].option = cptOption;
	                    newCptOptions[index] = null;
	                    return;
	                }
	            }

	            for (var i = 0; i < result.length; i++) {
	                var exist = result[i].exist;
	                if (!result[i].option // Consider name: two map to one.
	                    // Can not match when both ids exist but different.
	                    && (exist.id == null || cptOption.id == null)
	                    && cptOption.name != null
	                    && !modelUtil.isIdInner(cptOption)
	                    && !modelUtil.isIdInner(exist)
	                    && exist.name === cptOption.name + ''
	                ) {
	                    result[i].option = cptOption;
	                    newCptOptions[index] = null;
	                    return;
	                }
	            }
	        });

	        // Otherwise mapping by index.
	        zrUtil.each(newCptOptions, function (cptOption, index) {
	            if (!zrUtil.isObject(cptOption)) {
	                return;
	            }

	            var i = 0;
	            for (; i < result.length; i++) {
	                var exist = result[i].exist;
	                if (!result[i].option
	                    && !modelUtil.isIdInner(exist)
	                    // Caution:
	                    // Do not overwrite id. But name can be overwritten,
	                    // because axis use name as 'show label text'.
	                    // 'exist' always has id and name and we dont
	                    // need to check it.
	                    && cptOption.id == null
	                ) {
	                    result[i].option = cptOption;
	                    break;
	                }
	            }

	            if (i >= result.length) {
	                result.push({option: cptOption});
	            }
	        });

	        return result;
	    };

	    /**
	     * @public
	     * @param {Object} cptOption
	     * @return {boolean}
	     */
	    modelUtil.isIdInner = function (cptOption) {
	        return zrUtil.isObject(cptOption)
	            && cptOption.id
	            && (cptOption.id + '').indexOf('\0_ec_\0') === 0;
	    };

	    /**
	     * A helper for removing duplicate items between batchA and batchB,
	     * and in themselves, and categorize by series.
	     *
	     * @param {Array.<Object>} batchA Like: [{seriesId: 2, dataIndex: [32, 4, 5]}, ...]
	     * @param {Array.<Object>} batchB Like: [{seriesId: 2, dataIndex: [32, 4, 5]}, ...]
	     * @return {Array.<Array.<Object>, Array.<Object>>} result: [resultBatchA, resultBatchB]
	     */
	    modelUtil.compressBatches = function (batchA, batchB) {
	        var mapA = {};
	        var mapB = {};

	        makeMap(batchA || [], mapA);
	        makeMap(batchB || [], mapB, mapA);

	        return [mapToArray(mapA), mapToArray(mapB)];

	        function makeMap(sourceBatch, map, otherMap) {
	            for (var i = 0, len = sourceBatch.length; i < len; i++) {
	                var seriesId = sourceBatch[i].seriesId;
	                var dataIndices = modelUtil.normalizeToArray(sourceBatch[i].dataIndex);
	                var otherDataIndices = otherMap && otherMap[seriesId];

	                for (var j = 0, lenj = dataIndices.length; j < lenj; j++) {
	                    var dataIndex = dataIndices[j];

	                    if (otherDataIndices && otherDataIndices[dataIndex]) {
	                        otherDataIndices[dataIndex] = null;
	                    }
	                    else {
	                        (map[seriesId] || (map[seriesId] = {}))[dataIndex] = 1;
	                    }
	                }
	            }
	        }

	        function mapToArray(map, isData) {
	            var result = [];
	            for (var i in map) {
	                if (map.hasOwnProperty(i) && map[i] != null) {
	                    if (isData) {
	                        result.push(+i);
	                    }
	                    else {
	                        var dataIndices = mapToArray(map[i], true);
	                        dataIndices.length && result.push({seriesId: i, dataIndex: dataIndices});
	                    }
	                }
	            }
	            return result;
	        }
	    };

	    module.exports = modelUtil;


/***/ },
/* 6 */
/***/ function(module, exports, __webpack_require__) {

	

	    var zrUtil = __webpack_require__(4);
	    var numberUtil = __webpack_require__(7);
	    var textContain = __webpack_require__(8);

	    var formatUtil = {};
	    /**
	     * 每三位默认加,格式化
	     * @type {string|number} x
	     */
	    formatUtil.addCommas = function (x) {
	        if (isNaN(x)) {
	            return '-';
	        }
	        x = (x + '').split('.');
	        return x[0].replace(/(\d{1,3})(?=(?:\d{3})+(?!\d))/g,'$1,')
	               + (x.length > 1 ? ('.' + x[1]) : '');
	    };

	    /**
	     * @param {string} str
	     * @return {string} str
	     */
	    formatUtil.toCamelCase = function (str) {
	        return str.toLowerCase().replace(/-(.)/g, function(match, group1) {
	            return group1.toUpperCase();
	        });
	    };

	    /**
	     * Normalize css liked array configuration
	     * e.g.
	     *  3 => [3, 3, 3, 3]
	     *  [4, 2] => [4, 2, 4, 2]
	     *  [4, 3, 2] => [4, 3, 2, 3]
	     * @param {number|Array.<number>} val
	     */
	    formatUtil.normalizeCssArray = function (val) {
	        var len = val.length;
	        if (typeof (val) === 'number') {
	            return [val, val, val, val];
	        }
	        else if (len === 2) {
	            // vertical | horizontal
	            return [val[0], val[1], val[0], val[1]];
	        }
	        else if (len === 3) {
	            // top | horizontal | bottom
	            return [val[0], val[1], val[2], val[1]];
	        }
	        return val;
	    };

	    formatUtil.encodeHTML = function (source) {
	        return String(source)
	            .replace(/&/g, '&amp;')
	            .replace(/</g, '&lt;')
	            .replace(/>/g, '&gt;')
	            .replace(/"/g, '&quot;')
	            .replace(/'/g, '&#39;');
	    };

	    var TPL_VAR_ALIAS = ['a', 'b', 'c', 'd', 'e', 'f', 'g'];

	    var wrapVar = function (varName, seriesIdx) {
	        return '{' + varName + (seriesIdx == null ? '' : seriesIdx) + '}';
	    };

	    /**
	     * Template formatter
	     * @param  {string} tpl
	     * @param  {Array.<Object>|Object} paramsList
	     * @return {string}
	     */
	    formatUtil.formatTpl = function (tpl, paramsList) {
	        if (!zrUtil.isArray(paramsList)) {
	            paramsList = [paramsList];
	        }
	        var seriesLen = paramsList.length;
	        if (!seriesLen) {
	            return '';
	        }

	        var $vars = paramsList[0].$vars || [];
	        for (var i = 0; i < $vars.length; i++) {
	            var alias = TPL_VAR_ALIAS[i];
	            tpl = tpl.replace(wrapVar(alias),  wrapVar(alias, 0));
	        }
	        for (var seriesIdx = 0; seriesIdx < seriesLen; seriesIdx++) {
	            for (var k = 0; k < $vars.length; k++) {
	                tpl = tpl.replace(
	                    wrapVar(TPL_VAR_ALIAS[k], seriesIdx),
	                    paramsList[seriesIdx][$vars[k]]
	                );
	            }
	        }

	        return tpl;
	    };


	    /**
	     * @param {string} str
	     * @return {string}
	     * @inner
	     */
	    var s2d = function (str) {
	        return str < 10 ? ('0' + str) : str;
	    };

	    /**
	     * ISO Date format
	     * @param {string} tpl
	     * @param {number} value
	     * @inner
	     */
	    formatUtil.formatTime = function (tpl, value) {
	        if (tpl === 'week'
	            || tpl === 'month'
	            || tpl === 'quarter'
	            || tpl === 'half-year'
	            || tpl === 'year'
	        ) {
	            tpl = 'MM-dd\nyyyy';
	        }

	        var date = numberUtil.parseDate(value);
	        var y = date.getFullYear();
	        var M = date.getMonth() + 1;
	        var d = date.getDate();
	        var h = date.getHours();
	        var m = date.getMinutes();
	        var s = date.getSeconds();

	        tpl = tpl.replace('MM', s2d(M))
	            .toLowerCase()
	            .replace('yyyy', y)
	            .replace('yy', y % 100)
	            .replace('dd', s2d(d))
	            .replace('d', d)
	            .replace('hh', s2d(h))
	            .replace('h', h)
	            .replace('mm', s2d(m))
	            .replace('m', m)
	            .replace('ss', s2d(s))
	            .replace('s', s);

	        return tpl;
	    };

	    /**
	     * Capital first
	     * @param {string} str
	     * @return {string}
	     */
	    formatUtil.capitalFirst = function (str) {
	        return str ? str.charAt(0).toUpperCase() + str.substr(1) : str;
	    };

	    formatUtil.truncateText = textContain.truncateText;

	    module.exports = formatUtil;


/***/ },
/* 7 */
/***/ function(module, exports) {

	/**
	 * 数值处理模块
	 * @module echarts/util/number
	 */



	    var number = {};

	    var RADIAN_EPSILON = 1e-4;

	    function _trim(str) {
	        return str.replace(/^\s+/, '').replace(/\s+$/, '');
	    }

	    /**
	     * Linear mapping a value from domain to range
	     * @memberOf module:echarts/util/number
	     * @param  {(number|Array.<number>)} val
	     * @param  {Array.<number>} domain Domain extent domain[0] can be bigger than domain[1]
	     * @param  {Array.<number>} range  Range extent range[0] can be bigger than range[1]
	     * @param  {boolean} clamp
	     * @return {(number|Array.<number>}
	     */
	    number.linearMap = function (val, domain, range, clamp) {
	        var subDomain = domain[1] - domain[0];
	        var subRange = range[1] - range[0];

	        if (subDomain === 0) {
	            return subRange === 0
	                ? range[0]
	                : (range[0] + range[1]) / 2;
	        }

	        // Avoid accuracy problem in edge, such as
	        // 146.39 - 62.83 === 83.55999999999999.
	        // See echarts/test/ut/spec/util/number.js#linearMap#accuracyError
	        // It is a little verbose for efficiency considering this method
	        // is a hotspot.
	        if (clamp) {
	            if (subDomain > 0) {
	                if (val <= domain[0]) {
	                    return range[0];
	                }
	                else if (val >= domain[1]) {
	                    return range[1];
	                }
	            }
	            else {
	                if (val >= domain[0]) {
	                    return range[0];
	                }
	                else if (val <= domain[1]) {
	                    return range[1];
	                }
	            }
	        }
	        else {
	            if (val === domain[0]) {
	                return range[0];
	            }
	            if (val === domain[1]) {
	                return range[1];
	            }
	        }

	        return (val - domain[0]) / subDomain * subRange + range[0];
	    };

	    /**
	     * Convert a percent string to absolute number.
	     * Returns NaN if percent is not a valid string or number
	     * @memberOf module:echarts/util/number
	     * @param {string|number} percent
	     * @param {number} all
	     * @return {number}
	     */
	    number.parsePercent = function(percent, all) {
	        switch (percent) {
	            case 'center':
	            case 'middle':
	                percent = '50%';
	                break;
	            case 'left':
	            case 'top':
	                percent = '0%';
	                break;
	            case 'right':
	            case 'bottom':
	                percent = '100%';
	                break;
	        }
	        if (typeof percent === 'string') {
	            if (_trim(percent).match(/%$/)) {
	                return parseFloat(percent) / 100 * all;
	            }

	            return parseFloat(percent);
	        }

	        return percent == null ? NaN : +percent;
	    };

	    /**
	     * Fix rounding error of float numbers
	     * @param {number} x
	     * @return {number}
	     */
	    number.round = function (x, precision) {
	        if (precision == null) {
	            precision = 10;
	        }
	        // PENDING
	        return +(+x).toFixed(precision);
	    };

	    number.asc = function (arr) {
	        arr.sort(function (a, b) {
	            return a - b;
	        });
	        return arr;
	    };

	    /**
	     * Get precision
	     * @param {number} val
	     */
	    number.getPrecision = function (val) {
	        val = +val;
	        if (isNaN(val)) {
	            return 0;
	        }
	        // It is much faster than methods converting number to string as follows
	        //      var tmp = val.toString();
	        //      return tmp.length - 1 - tmp.indexOf('.');
	        // especially when precision is low
	        var e = 1;
	        var count = 0;
	        while (Math.round(val * e) / e !== val) {
	            e *= 10;
	            count++;
	        }
	        return count;
	    };

	    number.getPrecisionSafe = function (val) {
	        var str = val.toString();
	        var dotIndex = str.indexOf('.');
	        if (dotIndex < 0) {
	            return 0;
	        }
	        return str.length - 1 - dotIndex;
	    };

	    /**
	     * @param {Array.<number>} dataExtent
	     * @param {Array.<number>} pixelExtent
	     * @return {number}  precision
	     */
	    number.getPixelPrecision = function (dataExtent, pixelExtent) {
	        var log = Math.log;
	        var LN10 = Math.LN10;
	        var dataQuantity = Math.floor(log(dataExtent[1] - dataExtent[0]) / LN10);
	        var sizeQuantity = Math.round(log(Math.abs(pixelExtent[1] - pixelExtent[0])) / LN10);
	        return Math.max(
	            -dataQuantity + sizeQuantity,
	            0
	        );
	    };

	    // Number.MAX_SAFE_INTEGER, ie do not support.
	    number.MAX_SAFE_INTEGER = 9007199254740991;

	    /**
	     * To 0 - 2 * PI, considering negative radian.
	     * @param {number} radian
	     * @return {number}
	     */
	    number.remRadian = function (radian) {
	        var pi2 = Math.PI * 2;
	        return (radian % pi2 + pi2) % pi2;
	    };

	    /**
	     * @param {type} radian
	     * @return {boolean}
	     */
	    number.isRadianAroundZero = function (val) {
	        return val > -RADIAN_EPSILON && val < RADIAN_EPSILON;
	    };

	    /**
	     * @param {string|Date|number} value
	     * @return {Date} date
	     */
	    number.parseDate = function (value) {
	        if (value instanceof Date) {
	            return value;
	        }
	        else if (typeof value === 'string') {
	            // Treat as ISO format. See issue #3623
	            var ret = new Date(value);
	            if (isNaN(+ret)) {
	                // FIXME new Date('1970-01-01') is UTC, new Date('1970/01/01') is local
	                ret = new Date(new Date(value.replace(/-/g, '/')) - new Date('1970/01/01'));
	            }
	            return ret;
	        }

	        return new Date(Math.round(value));
	    };

	    /**
	     * Quantity of a number. e.g. 0.1, 1, 10, 100
	     * @param  {number} val
	     * @return {number}
	     */
	    number.quantity = function (val) {
	        return Math.pow(10, Math.floor(Math.log(val) / Math.LN10));
	    };

	    // "Nice Numbers for Graph Labels" of Graphic Gems
	    /**
	     * find a “nice” number approximately equal to x. Round the number if round = true, take ceiling if round = false
	     * The primary observation is that the “nicest” numbers in decimal are 1, 2, and 5, and all power-of-ten multiples of these numbers.
	     * @param  {number} val
	     * @param  {boolean} round
	     * @return {number}
	     */
	    number.nice = function (val, round) {
	        var exp10 = number.quantity(val);
	        var f = val / exp10; // between 1 and 10
	        var nf;
	        if (round) {
	            if (f < 1.5) { nf = 1; }
	            else if (f < 2.5) { nf = 2; }
	            else if (f < 4) { nf = 3; }
	            else if (f < 7) { nf = 5; }
	            else { nf = 10; }
	        }
	        else {
	            if (f < 1) { nf = 1; }
	            else if (f < 2) { nf = 2; }
	            else if (f < 3) { nf = 3; }
	            else if (f < 5) { nf = 5; }
	            else { nf = 10; }
	        }
	        return nf * exp10;
	    };

	    module.exports = number;


/***/ },
/* 8 */
/***/ function(module, exports, __webpack_require__) {

	

	    var textWidthCache = {};
	    var textWidthCacheCounter = 0;
	    var TEXT_CACHE_MAX = 5000;

	    var util = __webpack_require__(4);
	    var BoundingRect = __webpack_require__(9);
	    var retrieve = util.retrieve;

	    function getTextWidth(text, textFont) {
	        var key = text + ':' + textFont;
	        if (textWidthCache[key]) {
	            return textWidthCache[key];
	        }

	        var textLines = (text + '').split('\n');
	        var width = 0;

	        for (var i = 0, l = textLines.length; i < l; i++) {
	            // measureText 可以被覆盖以兼容不支持 Canvas 的环境
	            width = Math.max(textContain.measureText(textLines[i], textFont).width, width);
	        }

	        if (textWidthCacheCounter > TEXT_CACHE_MAX) {
	            textWidthCacheCounter = 0;
	            textWidthCache = {};
	        }
	        textWidthCacheCounter++;
	        textWidthCache[key] = width;

	        return width;
	    }

	    function getTextRect(text, textFont, textAlign, textBaseline) {
	        var textLineLen = ((text || '') + '').split('\n').length;

	        var width = getTextWidth(text, textFont);
	        // FIXME 高度计算比较粗暴
	        var lineHeight = getTextWidth('国', textFont);
	        var height = textLineLen * lineHeight;

	        var rect = new BoundingRect(0, 0, width, height);
	        // Text has a special line height property
	        rect.lineHeight = lineHeight;

	        switch (textBaseline) {
	            case 'bottom':
	            case 'alphabetic':
	                rect.y -= lineHeight;
	                break;
	            case 'middle':
	                rect.y -= lineHeight / 2;
	                break;
	            // case 'hanging':
	            // case 'top':
	        }

	        // FIXME Right to left language
	        switch (textAlign) {
	            case 'end':
	            case 'right':
	                rect.x -= rect.width;
	                break;
	            case 'center':
	                rect.x -= rect.width / 2;
	                break;
	            // case 'start':
	            // case 'left':
	        }

	        return rect;
	    }

	    function adjustTextPositionOnRect(textPosition, rect, textRect, distance) {

	        var x = rect.x;
	        var y = rect.y;

	        var height = rect.height;
	        var width = rect.width;

	        var textHeight = textRect.height;

	        var halfHeight = height / 2 - textHeight / 2;

	        var textAlign = 'left';

	        switch (textPosition) {
	            case 'left':
	                x -= distance;
	                y += halfHeight;
	                textAlign = 'right';
	                break;
	            case 'right':
	                x += distance + width;
	                y += halfHeight;
	                textAlign = 'left';
	                break;
	            case 'top':
	                x += width / 2;
	                y -= distance + textHeight;
	                textAlign = 'center';
	                break;
	            case 'bottom':
	                x += width / 2;
	                y += height + distance;
	                textAlign = 'center';
	                break;
	            case 'inside':
	                x += width / 2;
	                y += halfHeight;
	                textAlign = 'center';
	                break;
	            case 'insideLeft':
	                x += distance;
	                y += halfHeight;
	                textAlign = 'left';
	                break;
	            case 'insideRight':
	                x += width - distance;
	                y += halfHeight;
	                textAlign = 'right';
	                break;
	            case 'insideTop':
	                x += width / 2;
	                y += distance;
	                textAlign = 'center';
	                break;
	            case 'insideBottom':
	                x += width / 2;
	                y += height - textHeight - distance;
	                textAlign = 'center';
	                break;
	            case 'insideTopLeft':
	                x += distance;
	                y += distance;
	                textAlign = 'left';
	                break;
	            case 'insideTopRight':
	                x += width - distance;
	                y += distance;
	                textAlign = 'right';
	                break;
	            case 'insideBottomLeft':
	                x += distance;
	                y += height - textHeight - distance;
	                break;
	            case 'insideBottomRight':
	                x += width - distance;
	                y += height - textHeight - distance;
	                textAlign = 'right';
	                break;
	        }

	        return {
	            x: x,
	            y: y,
	            textAlign: textAlign,
	            textBaseline: 'top'
	        };
	    }

	    /**
	     * Show ellipsis if overflow.
	     *
	     * @param  {string} text
	     * @param  {string} containerWidth
	     * @param  {string} textFont
	     * @param  {number} [ellipsis='...']
	     * @param  {Object} [options]
	     * @param  {number} [options.maxIterations=3]
	     * @param  {number} [options.minChar=0] If truncate result are less
	     *                  then minChar, ellipsis will not show, which is
	     *                  better for user hint in some cases.
	     * @param  {number} [options.placeholder=''] When all truncated, use the placeholder.
	     * @return {string}
	     */
	    function truncateText(text, containerWidth, textFont, ellipsis, options) {
	        if (!containerWidth) {
	            return '';
	        }

	        options = options || {};

	        ellipsis = retrieve(ellipsis, '...');
	        var maxIterations = retrieve(options.maxIterations, 2);
	        var minChar = retrieve(options.minChar, 0);
	        // FIXME
	        // Other languages?
	        var cnCharWidth = getTextWidth('国', textFont);
	        // FIXME
	        // Consider proportional font?
	        var ascCharWidth = getTextWidth('a', textFont);
	        var placeholder = retrieve(options.placeholder, '');

	        // Example 1: minChar: 3, text: 'asdfzxcv', truncate result: 'asdf', but not: 'a...'.
	        // Example 2: minChar: 3, text: '维度', truncate result: '维', but not: '...'.
	        var contentWidth = containerWidth = Math.max(0, containerWidth - 1); // Reserve some gap.
	        for (var i = 0; i < minChar && contentWidth >= ascCharWidth; i++) {
	            contentWidth -= ascCharWidth;
	        }

	        var ellipsisWidth = getTextWidth(ellipsis);
	        if (ellipsisWidth > contentWidth) {
	            ellipsis = '';
	            ellipsisWidth = 0;
	        }

	        contentWidth = containerWidth - ellipsisWidth;

	        var textLines = (text + '').split('\n');

	        for (var i = 0, len = textLines.length; i < len; i++) {
	            var textLine = textLines[i];
	            var lineWidth = getTextWidth(textLine, textFont);

	            if (lineWidth <= containerWidth) {
	                continue;
	            }

	            for (var j = 0;; j++) {
	                if (lineWidth <= contentWidth || j >= maxIterations) {
	                    textLine += ellipsis;
	                    break;
	                }

	                var subLength = j === 0
	                    ? estimateLength(textLine, contentWidth, ascCharWidth, cnCharWidth)
	                    : lineWidth > 0
	                    ? Math.floor(textLine.length * contentWidth / lineWidth)
	                    : 0;

	                textLine = textLine.substr(0, subLength);
	                lineWidth = getTextWidth(textLine, textFont);
	            }

	            if (textLine === '') {
	                textLine = placeholder;
	            }

	            textLines[i] = textLine;
	        }

	        return textLines.join('\n');
	    }

	    function estimateLength(text, contentWidth, ascCharWidth, cnCharWidth) {
	        var width = 0;
	        var i = 0;
	        for (var len = text.length; i < len && width < contentWidth; i++) {
	            var charCode = text.charCodeAt(i);
	            width += (0 <= charCode && charCode <= 127) ? ascCharWidth : cnCharWidth;
	        }
	        return i;
	    }

	    var textContain = {

	        getWidth: getTextWidth,

	        getBoundingRect: getTextRect,

	        adjustTextPositionOnRect: adjustTextPositionOnRect,

	        truncateText: truncateText,

	        measureText: function (text, textFont) {
	            var ctx = util.getContext();
	            ctx.font = textFont || '12px sans-serif';
	            return ctx.measureText(text);
	        }
	    };

	    module.exports = textContain;


/***/ },
/* 9 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	/**
	 * @module echarts/core/BoundingRect
	 */


	    var vec2 = __webpack_require__(10);
	    var matrix = __webpack_require__(11);

	    var v2ApplyTransform = vec2.applyTransform;
	    var mathMin = Math.min;
	    var mathAbs = Math.abs;
	    var mathMax = Math.max;
	    /**
	     * @alias module:echarts/core/BoundingRect
	     */
	    function BoundingRect(x, y, width, height) {
	        /**
	         * @type {number}
	         */
	        this.x = x;
	        /**
	         * @type {number}
	         */
	        this.y = y;
	        /**
	         * @type {number}
	         */
	        this.width = width;
	        /**
	         * @type {number}
	         */
	        this.height = height;
	    }

	    BoundingRect.prototype = {

	        constructor: BoundingRect,

	        /**
	         * @param {module:echarts/core/BoundingRect} other
	         */
	        union: function (other) {
	            var x = mathMin(other.x, this.x);
	            var y = mathMin(other.y, this.y);

	            this.width = mathMax(
	                    other.x + other.width,
	                    this.x + this.width
	                ) - x;
	            this.height = mathMax(
	                    other.y + other.height,
	                    this.y + this.height
	                ) - y;
	            this.x = x;
	            this.y = y;
	        },

	        /**
	         * @param {Array.<number>} m
	         * @methods
	         */
	        applyTransform: (function () {
	            var min = [];
	            var max = [];
	            return function (m) {
	                // In case usage like this
	                // el.getBoundingRect().applyTransform(el.transform)
	                // And element has no transform
	                if (!m) {
	                    return;
	                }
	                min[0] = this.x;
	                min[1] = this.y;
	                max[0] = this.x + this.width;
	                max[1] = this.y + this.height;

	                v2ApplyTransform(min, min, m);
	                v2ApplyTransform(max, max, m);

	                this.x = mathMin(min[0], max[0]);
	                this.y = mathMin(min[1], max[1]);
	                this.width = mathAbs(max[0] - min[0]);
	                this.height = mathAbs(max[1] - min[1]);
	            };
	        })(),

	        /**
	         * Calculate matrix of transforming from self to target rect
	         * @param  {module:zrender/core/BoundingRect} b
	         * @return {Array.<number>}
	         */
	        calculateTransform: function (b) {
	            var a = this;
	            var sx = b.width / a.width;
	            var sy = b.height / a.height;

	            var m = matrix.create();

	            // 矩阵右乘
	            matrix.translate(m, m, [-a.x, -a.y]);
	            matrix.scale(m, m, [sx, sy]);
	            matrix.translate(m, m, [b.x, b.y]);

	            return m;
	        },

	        /**
	         * @param {(module:echarts/core/BoundingRect|Object)} b
	         * @return {boolean}
	         */
	        intersect: function (b) {
	            var a = this;
	            var ax0 = a.x;
	            var ax1 = a.x + a.width;
	            var ay0 = a.y;
	            var ay1 = a.y + a.height;

	            var bx0 = b.x;
	            var bx1 = b.x + b.width;
	            var by0 = b.y;
	            var by1 = b.y + b.height;

	            return ! (ax1 < bx0 || bx1 < ax0 || ay1 < by0 || by1 < ay0);
	        },

	        contain: function (x, y) {
	            var rect = this;
	            return x >= rect.x
	                && x <= (rect.x + rect.width)
	                && y >= rect.y
	                && y <= (rect.y + rect.height);
	        },

	        /**
	         * @return {module:echarts/core/BoundingRect}
	         */
	        clone: function () {
	            return new BoundingRect(this.x, this.y, this.width, this.height);
	        },

	        /**
	         * Copy from another rect
	         */
	        copy: function (other) {
	            this.x = other.x;
	            this.y = other.y;
	            this.width = other.width;
	            this.height = other.height;
	        }
	    };

	    module.exports = BoundingRect;


/***/ },
/* 10 */
/***/ function(module, exports) {

	
	    var ArrayCtor = typeof Float32Array === 'undefined'
	        ? Array
	        : Float32Array;

	    /**
	     * @typedef {Float32Array|Array.<number>} Vector2
	     */
	    /**
	     * 二维向量类
	     * @exports zrender/tool/vector
	     */
	    var vector = {
	        /**
	         * 创建一个向量
	         * @param {number} [x=0]
	         * @param {number} [y=0]
	         * @return {Vector2}
	         */
	        create: function (x, y) {
	            var out = new ArrayCtor(2);
	            if (x == null) {
	                x = 0;
	            }
	            if (y == null) {
	                y = 0;
	            }
	            out[0] = x;
	            out[1] = y;
	            return out;
	        },

	        /**
	         * 复制向量数据
	         * @param {Vector2} out
	         * @param {Vector2} v
	         * @return {Vector2}
	         */
	        copy: function (out, v) {
	            out[0] = v[0];
	            out[1] = v[1];
	            return out;
	        },

	        /**
	         * 克隆一个向量
	         * @param {Vector2} v
	         * @return {Vector2}
	         */
	        clone: function (v) {
	            var out = new ArrayCtor(2);
	            out[0] = v[0];
	            out[1] = v[1];
	            return out;
	        },

	        /**
	         * 设置向量的两个项
	         * @param {Vector2} out
	         * @param {