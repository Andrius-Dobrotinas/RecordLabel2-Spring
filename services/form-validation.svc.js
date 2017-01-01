"use strict";

(function () {

    angular.module("RecordLabel").service("formValidationService", function ($rootScope) {
        return {
            // Checks if a field has an invalid NON-empty value
            isInvalidForRequired: function (formField) {
                return formField.$dirty && !formField.$error.required && formField.$invalid;
            },

            // Checks if a field is empty
            isEmptyForRequired: function (formField) {
                return formField.$dirty && formField.$error && formField.$error.required;
            }
        }
    });

})();