"use strict";

describe("formValidationService Tests", function() {
    var formField;

    beforeEach(module("RecordLabel")) ;

    beforeEach(function() {
        formField = {
            $dirty: false,
            $error: {
                required: undefined
            },
            $invalid: false
        };
    });

    it("isInvalidForRequired must indicate FALSE when form field is NOT Dirty",
        inject(function(formValidationService) {

        formField.$dirty = false;
        formField.$error.required = false;
        formField.$invalid = true;

        expect(formValidationService.isInvalidForRequired(formField)).toBe(false);
    }));

    it("isInvalidForRequired must indicate TRUE when form field is Dirty and has an invalid NON-EMPTY value but no Required error",
        inject(function(formValidationService) {

        formField.$dirty = true;
        formField.$error.required = false;
        formField.$invalid = true;

        expect(formValidationService.isInvalidForRequired(formField)).toBe(true);
    }));

    it("isInvalidForRequired must indicate FALSE when form field is Dirty and has an invalid value due to simply being EMPTY (Required error)",
        inject(function(formValidationService) {

        formField.$dirty = true;
        formField.$error.required = true;
        formField.$invalid = true;

        expect(formValidationService.isInvalidForRequired(formField)).toBe(false);
    }));

    it("isValidForRequired must indicate FALSE when form field is Dirty and has a valid NON-empty value",
        inject(function(formValidationService) {

        formField.$dirty = true;
        formField.$error.required = false;
        formField.$invalid = false;

        expect(formValidationService.isInvalidForRequired(formField)).toBe(false);
    }));

    it("isEmptyForRequired must indicate FALSE when form field is NOT Dirty",
        inject(function(formValidationService) {

        formField.$dirty = false;
        formField.$error.required = true;

        expect(formValidationService.isEmptyForRequired(formField)).toBe(false);
    }));

    it("isEmptyForRequired must indicate TRUE when form field is Dirty and empty",
        inject(function(formValidationService) {

        formField.$dirty = true;
        formField.$error.required = true;

        expect(formValidationService.isEmptyForRequired(formField)).toBe(true);
    }));
});