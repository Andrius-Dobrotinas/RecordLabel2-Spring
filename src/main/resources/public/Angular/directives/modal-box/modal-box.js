"use strict";

(function () {

    angular.module("RecordLabel").directive("modalBox", function () {
        return {
            restrict: "A",
            replace: true,
            transclude: {
                body: "modalBoxBody",
                buttons: "?modalBoxButtons"
            },
            templateUrl: "Angular/directives/modal-box/modal-box.html",
            scope: {
                id: "@modalId",
                title: "@modalTitle",
                closeButtonTitle: "@modalCloseBtnTitle",
                closeButtonDisabled: "&modalCloseBtnDisabled",
                xButton: "&modalNoTopCloseBtn"
            }
        }
    });

})();