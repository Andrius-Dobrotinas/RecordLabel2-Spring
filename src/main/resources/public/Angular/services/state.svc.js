"use strict";

(function () {

    /* TODO: currently, unused. In the future, this should provide a way to register callbacks
    that are used (by the service or rootScope) to determine the state of the controller.
    It would be cool in case I need a global centralized way to control the loading screen
     */
    angular.module("RecordLabel").factory("stateSvc", ["$rootScope", function ($rootScope) {
        return {
            setState: function (loading) {
                $rootScope.isLoading = loading;
            }
        }
    }]);

})();