"use strict";

(function () {

    angular.module("RecordLabel").factory("resourceErrorHandler", ["$rootScope", function ($rootScope) {
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

})();