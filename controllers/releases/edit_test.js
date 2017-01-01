"use strict";

describe("ReleaseEditCtrl Tests", function() {
    var controllerConstructor, scope, rootScope, q;
    var routeParamsWithId, routeParamsEmpty, mockModel;

    var resourceErrorHandlerMock = function(promise) {
        return promise;
    };

    var resourcePostSvcMock = function(promise) {
        return promise;
    };

    var constantsServiceMock = {
        get: function() {}
    };

    var releasesServiceMock = {
        getForEdit: function() {},
        getTemplate: function() {}
    };

    var artistsServiceMock = {
        getList: function() {}
    };

    var mediaTypesServiceMock = {
        query: function() {}
    };

    beforeEach(module("RecordLabel"));

    beforeEach(inject(function($controller, $rootScope, $q) {
        controllerConstructor = $controller;
        scope = $rootScope.$new();
        rootScope = $rootScope;
        q = $q;

        routeParamsWithId = { id: 1 };
        routeParamsEmpty = {};
    }));

    it("must call constantsService.get once", function() {
        var serviceFunctionSpy = sinon.spy(constantsServiceMock, "get");

        var ctrl = controllerConstructor("ReleaseEditCtrl", {
            "$scope": scope, "$routeParams": routeParamsWithId,
            "releasesService": releasesServiceMock,
            "artistsService": artistsServiceMock,
            "mediaTypesService": mediaTypesServiceMock,
            "constantsService": constantsServiceMock,
            "resourceErrorHandler": resourceErrorHandlerMock,
            "resourcePostSvc": resourcePostSvcMock
        });

        expect(serviceFunctionSpy.calledOnce).toBe(true);
    });

    it("must use resourceErrorHandler for constantsService.get", function() {
        var resourceErrorHandlerMockSpy = sinon.spy(resourceErrorHandlerMock);

        var promiseObj = injectPromiseIntoServiceMock(q, constantsServiceMock, "get");

        var ctrl = controllerConstructor("ReleaseEditCtrl", {
            "$scope": scope, "$routeParams": routeParamsWithId,
            "releasesService": releasesServiceMock,
            "artistsService": artistsServiceMock,
            "mediaTypesService": mediaTypesServiceMock,
            "constantsService": constantsServiceMock,
            "resourceErrorHandler": resourceErrorHandlerMockSpy,
            "resourcePostSvc": resourcePostSvcMock
        });

        expect(resourceErrorHandlerMockSpy.calledWith(promiseObj.promise)).toBe(true);
    });

    it("must call mediaTypesService.query once", function() {
        var serviceFunctionSpy = sinon.spy(mediaTypesServiceMock, "query");

        var ctrl = controllerConstructor("ReleaseEditCtrl", {
            "$scope": scope, "$routeParams": routeParamsWithId,
            "releasesService": releasesServiceMock,
            "artistsService": artistsServiceMock,
            "mediaTypesService": mediaTypesServiceMock,
            "constantsService": constantsServiceMock,
            "resourceErrorHandler": resourceErrorHandlerMock,
            "resourcePostSvc": resourcePostSvcMock
        });

        expect(serviceFunctionSpy.calledOnce).toBe(true);
    });

    it("must use resourceErrorHandler for mediaTypesService.query", function() {
        var resourceErrorHandlerMockSpy = sinon.spy(resourceErrorHandlerMock);

        var promiseObj = injectPromiseIntoServiceMock(q, mediaTypesServiceMock, "query");

        var ctrl = controllerConstructor("ReleaseEditCtrl", {
            "$scope": scope, "$routeParams": routeParamsWithId,
            "releasesService": releasesServiceMock,
            "artistsService": artistsServiceMock,
            "mediaTypesService": mediaTypesServiceMock,
            "constantsService": constantsServiceMock,
            "resourceErrorHandler": resourceErrorHandlerMockSpy,
            "resourcePostSvc": resourcePostSvcMock
        });

        expect(resourceErrorHandlerMockSpy.calledWith(promiseObj.promise)).toBe(true);
    });

    it("must call artistsService.getList once", function() {
        var serviceFunctionSpy = sinon.spy(artistsServiceMock, "getList");

        var ctrl = controllerConstructor("ReleaseEditCtrl", {
            "$scope": scope, "$routeParams": routeParamsWithId,
            "releasesService": releasesServiceMock,
            "artistsService": artistsServiceMock,
            "mediaTypesService": mediaTypesServiceMock,
            "constantsService": constantsServiceMock,
            "resourceErrorHandler": resourceErrorHandlerMock,
            "resourcePostSvc": resourcePostSvcMock
        });

        expect(serviceFunctionSpy.calledOnce).toBe(true);
    });

    it("must use resourceErrorHandler for artistsService.getList", function() {
        var resourceErrorHandlerMockSpy = sinon.spy(resourceErrorHandlerMock);

        var promiseObj = injectPromiseIntoServiceMock(q, artistsServiceMock, "getList");

        var ctrl = controllerConstructor("ReleaseEditCtrl", {
            "$scope": scope, "$routeParams": routeParamsWithId,
            "releasesService": releasesServiceMock,
            "artistsService": artistsServiceMock,
            "mediaTypesService": mediaTypesServiceMock,
            "constantsService": constantsServiceMock,
            "resourceErrorHandler": resourceErrorHandlerMockSpy,
            "resourcePostSvc": resourcePostSvcMock
        });

        expect(resourceErrorHandlerMockSpy.calledWith(promiseObj.promise)).toBe(true);
    });

    it("must indicate NOT New when route params contain Id property with value", function() {
        injectPromiseIntoServiceMock(q, releasesServiceMock, "getTemplate");

        var ctrl = controllerConstructor("ReleaseEditCtrl", {
            "$scope": scope, "$routeParams": routeParamsWithId,
            "releasesService": releasesServiceMock,
            "artistsService": artistsServiceMock,
            "mediaTypesService": mediaTypesServiceMock,
            "constantsService": constantsServiceMock,
            "resourceErrorHandler": resourceErrorHandlerMock,
            "resourcePostSvc": resourcePostSvcMock
        });

        expect(ctrl.isNew).toBe(false);
    });

    it("must indicate New when route params don't contain Id property with value", function() {
        injectPromiseIntoServiceMock(q, releasesServiceMock, "getTemplate");

        var ctrl = controllerConstructor("ReleaseEditCtrl", {
            "$scope": scope, "$routeParams": routeParamsEmpty,
            "releasesService": releasesServiceMock,
            "artistsService": artistsServiceMock,
            "mediaTypesService": mediaTypesServiceMock,
            "constantsService": constantsServiceMock,
            "resourceErrorHandler": resourceErrorHandlerMock,
            "resourcePostSvc": resourcePostSvcMock
        });

        expect(ctrl.isNew).toBe(true);
    });

    it("when creating New, must call releasesService.getTemplate once", function() {
        injectPromiseIntoServiceMock(q, releasesServiceMock, "getTemplate");
        var serviceFunctionSpy = sinon.spy(releasesServiceMock, "getTemplate");

        var ctrl = controllerConstructor("ReleaseEditCtrl", {
            "$scope": scope, "$routeParams": routeParamsEmpty,
            "releasesService": releasesServiceMock,
            "artistsService": artistsServiceMock,
            "mediaTypesService": mediaTypesServiceMock,
            "constantsService": constantsServiceMock,
            "resourceErrorHandler": resourceErrorHandlerMock,
            "resourcePostSvc": resourcePostSvcMock
        });

        expect(serviceFunctionSpy.calledOnce).toBe(true);
    });

    it("when editing existing, must call releasesService.getForEdit with object containing Id from route params", function() {
        var params = { id: routeParamsWithId.id };

        injectPromiseIntoServiceMock(q, releasesServiceMock, "getForEdit");
        var serviceFunctionSpy = sinon.spy(releasesServiceMock, "getForEdit");

        var ctrl = controllerConstructor("ReleaseEditCtrl", {
            "$scope": scope, "$routeParams": routeParamsWithId,
            "releasesService": releasesServiceMock,
            "artistsService": artistsServiceMock,
            "mediaTypesService": mediaTypesServiceMock,
            "constantsService": constantsServiceMock,
            "resourceErrorHandler": resourceErrorHandlerMock,
            "resourcePostSvc": resourcePostSvcMock
        });

        expect(serviceFunctionSpy.calledOnce).toBe(true);
        expect(serviceFunctionSpy.getCall(0).args[0]).toEqual(params);
    });

    /**
     *  @description Injects promise capability into an object's function. The specified function
     *  gets set up to return a promise that is resolved by calling "resolve" on the object that
     *  is returned by this function.
     *  A handy way of emulating AngularJS resource-based services in order to test if a method
     *  has been called, but doesn't return
     *  @param {object} angularQ AngularJS $q service
     *  @param {object} resourceService Any object whose function is to be set up to return a promise
     *  @param {string} methodName Name of the resourceService's function to be set up
     */
    function injectPromiseIntoServiceMock(qService, resourceService, methodName) {
        var promiseObj = {
            serviceDeferredPromise: null,
            promise: null,
            resolve: function(data) {
                if (!this.serviceDeferredPromise || !this.promise)
                    throw "Cannot resolve because the promise has not been created yet";

                this.serviceDeferredPromise.resolve(data);
                this.promise.$resolved = true;
            },

        };
        resourceService[methodName] = function(params) {
            promiseObj.serviceDeferredPromise = qService.defer();
            promiseObj.promise = {
                $promise: promiseObj.serviceDeferredPromise.promise,
                $resolved: false
            };
            return promiseObj.promise;
        };

        return promiseObj;
    }
});