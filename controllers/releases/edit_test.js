"use strict";

describe("ReleaseEditCtrl Tests", function() {
    var controllerConstructor, scope, rootScope, q;
    var routeParamsWithId, routeParamsEmpty, mockModel;
    var resourceErrorHandlerMock, resourcePostSvcMock, constantsServiceMock,
        releasesServiceMock, artistsServiceMock, mediaTypesServiceMock, releaseTemplate;

    beforeEach(module("RecordLabel"));

    beforeEach(inject(function($controller, $rootScope, $q) {
        controllerConstructor = $controller;
        scope = $rootScope.$new();
        rootScope = $rootScope;
        q = $q;

        resourceErrorHandlerMock = function(promise) {
            return promise;
        };

        resourcePostSvcMock = function(promise) {
            return promise;
        };

        constantsServiceMock = {
            get: function() {}
        };

        releasesServiceMock = {
            getForEdit: function() {},
            getTemplate: function() {}
        };

        artistsServiceMock = {
            getList: function() {}
        };

        mediaTypesServiceMock = {
            query: function() {}
        };

        releaseTemplate = { artistId: 1, mediaId: 2 };

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
        expect(scope.constants).toBe(promiseObj.promise, "constants variable must be assigned a value returned from the service");
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
        expect(scope.mediaTypes).toBe(promiseObj.promise, "mediaTypes variable must be assigned a value returned from the service");
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
        expect(scope.artists).toBe(promiseObj.promise, "artists variable must be assigned a value returned from the service");
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

        var promiseObj = injectPromiseIntoServiceMock(q, releasesServiceMock, "getForEdit");
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
        expect(scope.model).toBe(promiseObj.promise, "model variable must be assigned a value returned from the service");
    });


    /** TODO: This test seems terribly wrong because it depends on the implementation of the ReleasesService...
     * Maybe I should create a separate service that assigns artistId and mediaId null values and verify
     * that it was called? Seems a bit overkill to write a SERVICE just for THAT...
     */
    it("when creating New, must assign nulls to retrieved model's artistId and mediaId in order for " +
        "Artist and MediaType Select element validations to work", inject(function($httpBackend, releasesService) {

        var ctrl = controllerConstructor("ReleaseEditCtrl", {
            "$scope": scope, "$routeParams": routeParamsEmpty,
            "releasesService": releasesService,
            "artistsService": artistsServiceMock,
            "mediaTypesService": mediaTypesServiceMock,
            "constantsService": constantsServiceMock,
            "resourceErrorHandler": resourceErrorHandlerMock,
            "resourcePostSvc": resourcePostSvcMock
        });

        $httpBackend.when('GET', '/api/releases/getTemplate').respond(releaseTemplate);
        $httpBackend.flush();

        expect(scope.model.artistId).toBe(null);
        expect(scope.model.mediaId).toBe(null);
    }));

    it("isLoading must indicate FALSE when all promises are resolved in case of EDIT", function() {
        var constantsPromise = injectPromiseIntoServiceMock(q, constantsServiceMock, "get");
        var artistsPromise = injectPromiseIntoServiceMock(q, artistsServiceMock, "getList");
        var mediaTypesPromise = injectPromiseIntoServiceMock(q, mediaTypesServiceMock, "query");
        var releasesPromise = injectPromiseIntoServiceMock(q, releasesServiceMock, "getForEdit");

        var ctrl = controllerConstructor("ReleaseEditCtrl", {
            "$scope": scope, "$routeParams": routeParamsWithId,
            "releasesService": releasesServiceMock,
            "artistsService": artistsServiceMock,
            "mediaTypesService": mediaTypesServiceMock,
            "constantsService": constantsServiceMock,
            "resourceErrorHandler": resourceErrorHandlerMock,
            "resourcePostSvc": resourcePostSvcMock
        });

        constantsPromise.resolve();
        artistsPromise.resolve();
        mediaTypesPromise.resolve();
        releasesPromise.resolve();

        scope.$apply();

        expect(ctrl.isLoading()).toBe(false);
    });

    it("isLoading must indicate FALSE when all promises are resolved in case of NEW", function() {
        var constantsPromise = injectPromiseIntoServiceMock(q, constantsServiceMock, "get");
        var artistsPromise = injectPromiseIntoServiceMock(q, artistsServiceMock, "getList");
        var mediaTypesPromise = injectPromiseIntoServiceMock(q, mediaTypesServiceMock, "query");
        var releasesPromise = injectPromiseIntoServiceMock(q, releasesServiceMock, "getTemplate");

        var ctrl = controllerConstructor("ReleaseEditCtrl", {
            "$scope": scope, "$routeParams": routeParamsEmpty,
            "releasesService": releasesServiceMock,
            "artistsService": artistsServiceMock,
            "mediaTypesService": mediaTypesServiceMock,
            "constantsService": constantsServiceMock,
            "resourceErrorHandler": resourceErrorHandlerMock,
            "resourcePostSvc": resourcePostSvcMock
        });

        constantsPromise.resolve();
        artistsPromise.resolve();
        mediaTypesPromise.resolve();
        releasesPromise.resolve(releaseTemplate);

        scope.$apply();

        expect(ctrl.isLoading()).toBe(false);
    });

    it("isLoading must indicate TRUE when at least one promise is not resolved (releases)", function() {
        var constantsPromise = injectPromiseIntoServiceMock(q, constantsServiceMock, "get");
        var artistsPromise = injectPromiseIntoServiceMock(q, artistsServiceMock, "getList");
        var mediaTypesPromise = injectPromiseIntoServiceMock(q, mediaTypesServiceMock, "query");
        var releasesPromise = injectPromiseIntoServiceMock(q, releasesServiceMock, "getForEdit");

        var ctrl = controllerConstructor("ReleaseEditCtrl", {
            "$scope": scope, "$routeParams": routeParamsWithId,
            "releasesService": releasesServiceMock,
            "artistsService": artistsServiceMock,
            "mediaTypesService": mediaTypesServiceMock,
            "constantsService": constantsServiceMock,
            "resourceErrorHandler": resourceErrorHandlerMock,
            "resourcePostSvc": resourcePostSvcMock
        });

        constantsPromise.resolve();
        artistsPromise.resolve();
        mediaTypesPromise.resolve();

        scope.$apply();

        expect(ctrl.isLoading()).toBe(true);
    });

    it("isLoading must indicate TRUE when at least one promise is not resolved (mediaTypes)", function() {
        var constantsPromise = injectPromiseIntoServiceMock(q, constantsServiceMock, "get");
        var artistsPromise = injectPromiseIntoServiceMock(q, artistsServiceMock, "getList");
        var mediaTypesPromise = injectPromiseIntoServiceMock(q, mediaTypesServiceMock, "query");
        var releasesPromise = injectPromiseIntoServiceMock(q, releasesServiceMock, "getForEdit");

        var ctrl = controllerConstructor("ReleaseEditCtrl", {
            "$scope": scope, "$routeParams": routeParamsWithId,
            "releasesService": releasesServiceMock,
            "artistsService": artistsServiceMock,
            "mediaTypesService": mediaTypesServiceMock,
            "constantsService": constantsServiceMock,
            "resourceErrorHandler": resourceErrorHandlerMock,
            "resourcePostSvc": resourcePostSvcMock
        });

        constantsPromise.resolve();
        artistsPromise.resolve();
        releasesPromise.resolve();

        scope.$apply();

        expect(ctrl.isLoading()).toBe(true);
    });

    it("isLoading must indicate TRUE when at least one promise is not resolved (artists)", function() {
        var constantsPromise = injectPromiseIntoServiceMock(q, constantsServiceMock, "get");
        var artistsPromise = injectPromiseIntoServiceMock(q, artistsServiceMock, "getList");
        var mediaTypesPromise = injectPromiseIntoServiceMock(q, mediaTypesServiceMock, "query");
        var releasesPromise = injectPromiseIntoServiceMock(q, releasesServiceMock, "getForEdit");

        var ctrl = controllerConstructor("ReleaseEditCtrl", {
            "$scope": scope, "$routeParams": routeParamsWithId,
            "releasesService": releasesServiceMock,
            "artistsService": artistsServiceMock,
            "mediaTypesService": mediaTypesServiceMock,
            "constantsService": constantsServiceMock,
            "resourceErrorHandler": resourceErrorHandlerMock,
            "resourcePostSvc": resourcePostSvcMock
        });

        constantsPromise.resolve();
        mediaTypesPromise.resolve();
        releasesPromise.resolve();

        scope.$apply();

        expect(ctrl.isLoading()).toBe(true);
    });

    it("isLoading must indicate TRUE when at least one promise is not resolved (constantsPromise)", function() {
        var constantsPromise = injectPromiseIntoServiceMock(q, constantsServiceMock, "get");
        var artistsPromise = injectPromiseIntoServiceMock(q, artistsServiceMock, "getList");
        var mediaTypesPromise = injectPromiseIntoServiceMock(q, mediaTypesServiceMock, "query");
        var releasesPromise = injectPromiseIntoServiceMock(q, releasesServiceMock, "getForEdit");

        var ctrl = controllerConstructor("ReleaseEditCtrl", {
            "$scope": scope, "$routeParams": routeParamsWithId,
            "releasesService": releasesServiceMock,
            "artistsService": artistsServiceMock,
            "mediaTypesService": mediaTypesServiceMock,
            "constantsService": constantsServiceMock,
            "resourceErrorHandler": resourceErrorHandlerMock,
            "resourcePostSvc": resourcePostSvcMock
        });

        artistsPromise.resolve();
        mediaTypesPromise.resolve();
        releasesPromise.resolve();

        scope.$apply();

        expect(ctrl.isLoading()).toBe(true);
    });

    it("isLoading must indicate TRUE when no promises are resolved", function() {
        var constantsPromise = injectPromiseIntoServiceMock(q, constantsServiceMock, "get");
        var artistsPromise = injectPromiseIntoServiceMock(q, artistsServiceMock, "getList");
        var mediaTypesPromise = injectPromiseIntoServiceMock(q, mediaTypesServiceMock, "query");
        var releasesPromise = injectPromiseIntoServiceMock(q, releasesServiceMock, "getForEdit");

        var ctrl = controllerConstructor("ReleaseEditCtrl", {
            "$scope": scope, "$routeParams": routeParamsWithId,
            "releasesService": releasesServiceMock,
            "artistsService": artistsServiceMock,
            "mediaTypesService": mediaTypesServiceMock,
            "constantsService": constantsServiceMock,
            "resourceErrorHandler": resourceErrorHandlerMock,
            "resourcePostSvc": resourcePostSvcMock
        });

        expect(ctrl.isLoading()).toBe(true);
    });


    describe("ReleaseEditCtrl FORM tests", function() {

        var formValidationService;

        beforeEach(function() {
            formValidationService = {
                isInvalidForRequired: function() {},
                isEmptyForRequired: function() {}
            };
        });

        it("isInvalidForRequired must invoke formValidationService.isInvalidForRequired with the passed in argument", function() {
            var isInvalidForRequiredSpy = sinon.spy(formValidationService, "isInvalidForRequired");

            var ctrl = controllerConstructor("ReleaseEditCtrl", {
                "$scope": scope, "$routeParams": routeParamsWithId,
                "releasesService": releasesServiceMock,
                "artistsService": artistsServiceMock,
                "mediaTypesService": mediaTypesServiceMock,
                "constantsService": constantsServiceMock,
                "resourceErrorHandler": resourceErrorHandlerMock,
                "resourcePostSvc": resourcePostSvcMock,
                "formValidationService": formValidationService
            });

            var formField = { value: "Slayer"};
            ctrl.isInvalidForRequired(formField);

            expect(isInvalidForRequiredSpy.calledOnce).toBe(true);
            expect(isInvalidForRequiredSpy.getCall(0).args[0]).toBe(formField);
        });

        it("isEmptyForRequired must invoke formValidationService.isEmptyForRequired with the passed in argument", function() {
            var isEmptyForRequiredSpy = sinon.spy(formValidationService, "isEmptyForRequired");

            var ctrl = controllerConstructor("ReleaseEditCtrl", {
                "$scope": scope, "$routeParams": routeParamsWithId,
                "releasesService": releasesServiceMock,
                "artistsService": artistsServiceMock,
                "mediaTypesService": mediaTypesServiceMock,
                "constantsService": constantsServiceMock,
                "resourceErrorHandler": resourceErrorHandlerMock,
                "resourcePostSvc": resourcePostSvcMock,
                "formValidationService": formValidationService
            });

            var formField = { value: "Slayer"};
            ctrl.isEmptyForRequired(formField);

            expect(isEmptyForRequiredSpy.calledOnce).toBe(true);
            expect(isEmptyForRequiredSpy.getCall(0).args[0]).toBe(formField);
        });
    });
});