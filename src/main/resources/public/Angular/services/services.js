﻿"use strict";

(function () {

    var application = angular.module("RecordLabel");

    application.factory("resourceErrorHandler", ["$rootScope", function ($rootScope) {
        return function (promise) {
            promise.$promise.catch(function (e) {
                if (!e.statusText) {
                    e.statusText = "An error has been encountered while making a request to server";
                }
                $rootScope.errors.push(e);
            });
            return promise;
        };
    }]);

    application.factory("modelPostResourceService", ["$location", "$rootScope", "infoMsgService", function ($location, $rootScope, infoMsgService) {
        return function (promise, redirectTo, errorArray) {
            promise.$promise.then(function () {
                infoMsgService.setMessage("Successfully saved!");
                $location.url(redirectTo);
            })
            .catch(function (e) {
                if (e.data && e.data.modelState) {
                    for (let field in e.data.modelState) {
                        e.data.modelState[field].forEach(function (error) {
                            errorArray.push({ field: field, error: error });
                            console.log(field, error);
                        });
                    }
                } else {
                    if (!e.statusText) {
                        e.statusText = "An error has been encountered while making a request to server";
                    }
                    $rootScope.errors.push(e);
                }
            });

            return promise;
        }
    }]);

    application.factory("infoMsgService", ["$rootScope", function ($rootScope) {
        return {
            setMessage: function (msg) {
                $rootScope.infoMessage = msg;
                $rootScope.isInfoMsgFresh = true;
            },
            changeLocation: function () {
                if (!$rootScope.isInfoMsgFresh) {
                    $rootScope.infoMessage = undefined;
                }
                $rootScope.isInfoMsgFresh = false;
            }
        }
    }]);

})();