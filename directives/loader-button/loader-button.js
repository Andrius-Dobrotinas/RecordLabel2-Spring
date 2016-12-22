"use strict";

(function () {

    angular.module("RecordLabel").directive("loaderButton", function () {
        return {
            restrict: "A",
            replace: true,
            templateUrl: "Angular/directives/loader-button/loader-button.html",
            scope: {
                text: "@lbtnText",
                busy: "&lbtnIsBusy",
                action: "&lbtnClick"
            }
        }
    });

})();