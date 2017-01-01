"use strict";

(function() {
    angular.module("RecordLabel").service("referenceUrlTrustService", ["$sce", function($sce) {
        return {
            trustUrls: function(urlsArray) {
                urlsArray.forEach(function (reference) {
                    reference.target = $sce.trustAsResourceUrl(reference.target)
                })
            }
        };
    }]);
})();