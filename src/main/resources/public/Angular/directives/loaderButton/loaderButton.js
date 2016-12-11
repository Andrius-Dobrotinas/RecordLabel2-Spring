"use strict";

(function () {

    angular.module("RecordLabel").directive("loaderButton", function () {
        return {
            restrict: "A",
            replace: true,
            templateUrl: "Angular/directives/loaderButton/loaderButton.html",
            scope: {
                text: "@lbtnText",
                busy: "&lbtnIsBusy",
                action: "&lbtnClick"
            }
        }
    });

})();