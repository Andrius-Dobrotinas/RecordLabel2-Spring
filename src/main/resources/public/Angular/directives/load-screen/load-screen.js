"use strict";

(function () {

    angular.module("RecordLabel").directive("loadScreen", function () {
        return {
            restrict: "A",
            replace: true,
            transclude: true,
            templateUrl: "Angular/directives/load-screen/load-screen.html",
            scope: {
                show: "&lsShow",
                class: "@lsClass"
            }
        }
    });

})();