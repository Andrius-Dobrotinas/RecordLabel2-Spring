"use strict";

(function () {

    angular.module("RecordLabel").factory("infoMsgSvc", ["$rootScope", function ($rootScope) {
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