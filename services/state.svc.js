"use strict";

(function () {

    angular.module("RecordLabel").factory("stateSvc", ["$rootScope", function ($rootScope) {
        return {
            setState: function (loading) {
                $rootScope.isLoading = loading;
            }
        }
    }]);

})();