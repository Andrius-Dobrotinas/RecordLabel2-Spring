"use strict";

describe("ReleaseViewCtrl Tests", function() {
	var controllerConstructor, scope, rootScope, q, referenceUrlTrustService;
	var routeParams, mockModel;

	var resourceErrorHandlerMock = function(promise) {
		return promise;
	};
	
	beforeEach(module("RecordLabel"));

	// Mock $sce service that referenceUrlTrustService is dependant on
	beforeEach(module(function($provide) {
		$provide.service("$sce", function() { this.trustAsResourceUrl = function() {} });
	}));
	
	beforeEach(inject(function($controller, $rootScope, $q, _referenceUrlTrustService_) {
		controllerConstructor = $controller;
		scope = $rootScope.$new();
		rootScope = $rootScope;
		q = $q;
		referenceUrlTrustService = _referenceUrlTrustService_;

		routeParams = "param";
		mockModel = sinon.stub({
			release: null,
			youtubeReferences: [ { target: "https://youtube.com/asd"} ]
		});
		
		/*$httpBackend.whenGET("/api/metadata/get").respond(function(method, url) {
			return null;
		});*/
		
		/*angular.mock.inject(function ($injector) {
			referenceUrlTrustService = $injector.get("referenceUrlTrustService");
		});*/
	}));

	it("must call releasesService.get", function() {

		var releasesServiceDeferred;
		var releasesService = {
			get: function(params) {
				releasesServiceDeferred = q.defer();
				return { $promise: releasesServiceDeferred.promise } ;
			}
		}

		var releasesServiceSpy = sinon.spy(releasesService, "get");

		var ctrl = controllerConstructor("ReleaseViewCtrl", {
		"$scope": scope, "$routeParams": routeParams,
			"releasesService": releasesService,
			"resourceErrorHandler": resourceErrorHandlerMock,
			"referenceUrlTrustService": referenceUrlTrustService
		});

		expect(releasesServiceSpy.calledWith(routeParams)).toBe(true);
	});

	it("must use resourceErrorHandler", function() {
		var resourceErrorHandlerMock = function(promise) {
			return promise;
		};
		var resourceErrorHandlerSpy = sinon.spy(resourceErrorHandlerMock);

		var releasesServicePromiseDeferred;
		var _promise;
		var releasesService = {
			get: function(params) {
				releasesServicePromiseDeferred = q.defer();
				_promise = {
					$promise: releasesServicePromiseDeferred.promise,
					$resolved: false
				};
				return _promise ;
			}
		}

		var ctrl = controllerConstructor("ReleaseViewCtrl", {
			"$scope": scope, "$routeParams": routeParams,
			"releasesService": releasesService,
			"resourceErrorHandler": resourceErrorHandlerSpy,
			"referenceUrlTrustService": referenceUrlTrustService
		});

		expect(resourceErrorHandlerSpy.calledWith(_promise)).toBe(true);
	});

	it("must assign model from the backend to scope.model", function() {

		var releasesServiceDeferred;
		var releasesService = {
			get: function(params) {
				releasesServiceDeferred = q.defer();
				return { $promise: releasesServiceDeferred.promise } ;
			}
		}

		var ctrl = controllerConstructor("ReleaseViewCtrl", {
			"$scope": scope, "$routeParams": routeParams,
			"releasesService": releasesService,
			"resourceErrorHandler": resourceErrorHandlerMock,
			"referenceUrlTrustService": referenceUrlTrustService
		});

		releasesServiceDeferred.resolve(mockModel);
		rootScope.$apply();

		expect(scope.model).toBe(mockModel);
	});

	it("must use referenceUrlTrustService for model.youtubeReferences when the property is trueish", function() {

		var releasesServiceDeferred;
		var releasesService = {
			get: function(params) {
				releasesServiceDeferred = q.defer();
				return { $promise: releasesServiceDeferred.promise } ;
			}
		}

		var trustUrlsStub = sinon.stub(referenceUrlTrustService, "trustUrls");

		var ctrl = controllerConstructor("ReleaseViewCtrl", {
			"$scope": scope, "$routeParams": routeParams,
			"releasesService": releasesService,
			"resourceErrorHandler": resourceErrorHandlerMock,
			"referenceUrlTrustService": referenceUrlTrustService
		});

		releasesServiceDeferred.resolve(mockModel);
		rootScope.$apply();

		expect(trustUrlsStub.calledWith(mockModel.youtubeReferences)).toBe(true);
	});

	it("must not use referenceUrlTrustService for model.youtubeReferences when the property is falsish", function() {

		var releasesServiceDeferred;
		var releasesService = {
			get: function(params) {
				releasesServiceDeferred = q.defer();
				return { $promise: releasesServiceDeferred.promise } ;
			}
		}

		var trustUrlsStub = sinon.stub(referenceUrlTrustService, "trustUrls");

		var ctrl = controllerConstructor("ReleaseViewCtrl", {
			"$scope": scope, "$routeParams": routeParams,
			"releasesService": releasesService,
			"resourceErrorHandler": resourceErrorHandlerMock,
			"referenceUrlTrustService": referenceUrlTrustService
		});

		mockModel.youtubeReferences = null;

		releasesServiceDeferred.resolve(mockModel);
		rootScope.$apply();

		expect(trustUrlsStub.called).toBe(false);
	});

	describe("IsLoading tests", function() {
		it("must must report that it's loading", function () {
			var releasesServicePromiseDeferred;
			var _promise;
			var releasesService = {
				get: function (params) {
					releasesServicePromiseDeferred = q.defer();
					_promise = {
						$promise: releasesServicePromiseDeferred.promise,
						$resolved: false
					};
					return _promise;
				}
			}

			var ctrl = controllerConstructor("ReleaseViewCtrl", {
				"$scope": scope, "$routeParams": routeParams,
				"releasesService": releasesService,
				"resourceErrorHandler": resourceErrorHandlerMock,
				"referenceUrlTrustService": referenceUrlTrustService
			});

			expect(ctrl.isLoading()).toBe(true);
		});

		it("must must report that it's not loading", function () {
			var releasesServicePromiseDeferred;
			var _promise;
			var releasesService = {
				get: function (params) {
					releasesServicePromiseDeferred = q.defer();
					_promise = {
						$promise: releasesServicePromiseDeferred.promise,
						$resolved: false
					};
					return _promise;
				}
			}

			var ctrl = controllerConstructor("ReleaseViewCtrl", {
				"$scope": scope, "$routeParams": routeParams,
				"releasesService": releasesService,
				"resourceErrorHandler": resourceErrorHandlerMock,
				"referenceUrlTrustService": referenceUrlTrustService
			});

			_promise.$resolved = true;

			expect(ctrl.isLoading()).toBe(false);
		});
	});
	/*function getResourceServiceMock() {
		return {
			releasesServicePromiseDeferred: null,
			_promise: null,
			releasesService: {
				get: function (params) {
					this.releasesServicePromiseDeferred = q.defer();
					this._promise = {
						$promise: releasesServicePromiseDeferred.promise,
						$resolved: false
					};
					return _promise;
				}
			},
			resolve: function() {
				releasesServicePromiseDeferred.resolve();
				_promise.$resolved = true;
			}
		}
	}*/
});