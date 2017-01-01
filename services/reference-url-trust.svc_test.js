"use strict";

describe("referenceUrlTrustService Tests", function() {

    var trustAsResourceUrlSpy;

    beforeEach(module("RecordLabel")) ;

    beforeEach(function() {
        trustAsResourceUrlSpy = sinon.spy(function() {});
        module(function($provide) {
            $provide.service("$sce", function() { this.trustAsResourceUrl = trustAsResourceUrlSpy });
        })
    });

    it("must call asTrustedUrl for each entry in the array", inject(function(referenceUrlTrustService) {
        var references = [
            { reference: "https://youtube.com"},
            { reference: "https://google.com"}
        ]
        referenceUrlTrustService.trustUrls(references);

        expect(trustAsResourceUrlSpy.called).toBe(true);
    }));
});