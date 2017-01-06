"use strict";

(function () {

    // This service takes care of loading items in batches and tracking availability of more items
    angular.module("RecordLabel").factory("batchedListServiceFactory", ["resourceErrorHandler", function (resourceErrorHandler) {
        return function (resourceSvc, itemsPerPage) {
            var BatchedListService = function(resourceService, batchSize) {
                var entries = [];
                var currentBatchNumber = 0;
                var moreItemsAvailable = false;
                var promise = undefined;
                var numberOfBatchesLeft = 0;

                this.getEntries = function() {
                    return entries;
                };
                this.getNumberOfBatchesLeft = function() {
                    return numberOfBatchesLeft;
                };
                this.getCurrentBatchNumber = function() {
                    return currentBatchNumber;
                };
                this.isLoading = function() {
                    return (promise && !promise.$resolved) == true;
                };
                this.areMoreItemsAvailable = function() {
                    return moreItemsAvailable;
                };
                this.loadMore = function () {
                    promise = resourceErrorHandler(resourceService.queryBatch(
                        {
                            number: ++currentBatchNumber,
                            size: batchSize
                        }));

                    promise.$promise.then(function (data) {
                        // Add a new batch of entries to the current collection
                        entries = entries.concat(data.entries);
                        numberOfBatchesLeft = data.batchCount - currentBatchNumber;
                        moreItemsAvailable = numberOfBatchesLeft > 0;
                    });
                };
            };
            return new BatchedListService(resourceSvc, itemsPerPage);
        };
    }]);

})();