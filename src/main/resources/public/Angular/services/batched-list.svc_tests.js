"use strict";

describe("batchedListServiceFactory Tests", function() {
    var q;
    var itemsPerPage;
    var resourceErrorHandlerMock, releasesServiceMock;
    var functionSpy, promiseObj, svc;

    beforeEach(module("RecordLabel"));

    beforeEach(function() {
        itemsPerPage = 5;
        resourceErrorHandlerMock = function(promise) {
            return promise;
        };
        releasesServiceMock = {
            queryBatch: function() {}
        };

        module(function($provide) {
            $provide.factory("resourceErrorHandler", function() { return resourceErrorHandlerMock });
        });
    });

    beforeEach(inject(function($q) {
        q = $q;
    }));

    // Prepare spies and instantiate the service
    beforeEach(inject(function(batchedListServiceFactory) {
        functionSpy = sinon.spy(releasesServiceMock, "queryBatch");
        promiseObj = TestUtilities.injectPromiseIntoServiceMock(q, releasesServiceMock, "queryBatch");
        svc = batchedListServiceFactory(releasesServiceMock, itemsPerPage);
    }));

    describe("initial state before requests", function(){

        it("must indicate that no more items are available", function() {
            expect(svc.areMoreItemsAvailable()).toBe(false);
        });

        it("must indicate that current batch number is 0", function() {
            expect(svc.getCurrentBatchNumber()).toBe(0);
        });

        it("must indicate that no batches left", function() {
            expect(svc.getNumberOfBatchesLeft()).toBe(0);
        });
    });

    describe("loadMore: Arguments passed to ResourceService Tests", function(){

        it("must invoke resourceService.queryBatch once", inject(function(batchedListServiceFactory) {
            releasesServiceMock = {
                queryBatch: function() {
                    console.log("executing queryBatch")
                }
            };
            var functionSpy = sinon.spy(releasesServiceMock, "queryBatch");
            TestUtilities.injectPromiseIntoServiceMock(q, releasesServiceMock, "queryBatch");
            var svc = batchedListServiceFactory(releasesServiceMock, itemsPerPage);

            svc.loadMore();

            expect(functionSpy.calledOnce).toBe(true);
        }));

        it("must invoke resourceService.queryBatch with the right object containing 'number' and 'size' properties", function() {
            svc.loadMore();

            expect(functionSpy.getCall(0).args.length).toBe(1, "Must invoke queryBatch with exactly 1 argument");
            var arg = functionSpy.getCall(0).args[0];
            expect(arg.hasOwnProperty("number")).toBe(true, "Argument must contain 'number' property");
            expect(arg.hasOwnProperty("size")).toBe(true, "Argument must contain 'size' property");
        });

        it("'size' property of arguments object passed to queryBatch must have the same value as the one" +
            " passed to the service constructor", function() {
            svc.loadMore();
            expect(functionSpy.getCall(0).args[0].size).toBe(itemsPerPage);
        });

        it("'number' property of arguments object passed to queryBatch must be equal to 1 on first call", function() {
            var args = {
                number: 1,
                size: itemsPerPage
            };
            svc.loadMore();

            expect(functionSpy.getCall(0).args[0].number).toBe(args.number);
        });

        it("'number' property of arguments object passed to queryBatch must be equal to 1 on first call", function() {
            svc.loadMore();
            expect(functionSpy.getCall(0).args[0].number).toBe(1);
        });

        it("'number' property of arguments object passed to queryBatch must be equal to 2 on second call", function() {
            svc.loadMore();
            functionSpy.reset();
            svc.loadMore();

            expect(functionSpy.getCall(0).args[0].number).toBe(2);
        });

    });

    describe("isLoading tests", function() {
        var scope;

        beforeEach(inject(function($rootScope) {
            scope = $rootScope.$new();
        }));

        it("isLoading must indicate FALSE initially before any requests", function() {
            expect(svc.isLoading()).toBe(false);
        });

        it("isLoading must indicate TRUE after loadMore until promise is resolved", function() {
            svc.loadMore();
            expect(svc.isLoading()).toBe(true);
        });

        it("isLoading must indicate FALSE after loadMore when promise is resolved", function() {
            svc.loadMore();

            var response = {
                entries: [ "entry1", "entry2"],
                batchCount: 1
            };
            promiseObj.resolve(response);
            scope.$apply();

            expect(svc.isLoading()).toBe(false);
        });
    });

    describe("loadMore when Response Received Tests", function(){
        var scope;

        beforeEach(inject(function($rootScope) {
            scope = $rootScope.$new();
        }));

        it("must add new batch of entries to the main entries collection when it's empty", function() {
            svc.loadMore();

            var response = {
                entries: [ "entry1", "entry2"],
                batchCount: 1
            };
            promiseObj.resolve(response);
            scope.$apply();

            expect(svc.getEntries().length).toBe(2);
            expect(svc.getEntries()[0]).toBe(response.entries[0]);
            expect(svc.getEntries()[1]).toBe(response.entries[1]);
        });

        it("subsequent loadMore calls must add new batch of entries to the main entries collection", function() {
            svc.loadMore();
            var response = {
                entries: [ "entry1", "entry2"],
                batchCount: 1
            };
            promiseObj.resolve(response);
            scope.$apply();

            // Second request
            svc.loadMore();
            var response2 = {
                entries: [ "entry3", "entry4"],
                batchCount: 1
            };
            promiseObj.resolve(response2);
            scope.$apply();

            expect(svc.getEntries().length).toBe(4);
            expect(svc.getEntries()[2]).toBe(response2.entries[0]);
            expect(svc.getEntries()[3]).toBe(response2.entries[1]);
        });

        it("must calculate the number of batches left: must return 0 when response.batchCount is 1", function() {
            svc.loadMore();

            var response = {
                entries: [ "entry1", "entry2"],
                batchCount: 1
            };
            promiseObj.resolve(response);
            scope.$apply();

            expect(svc.getNumberOfBatchesLeft()).toBe(0);
        });

        it("must calculate the number of batches left: must return 1 when response.batchCount is 2", function() {
            svc.loadMore();

            var response = {
                entries: [ "entry1", "entry2"],
                batchCount: 2
            };
            promiseObj.resolve(response);
            scope.$apply();

            expect(svc.getNumberOfBatchesLeft()).toBe(1);
        });

        it("must indicate that no more items are available when number of batches left is 0", function() {
            svc.loadMore();

            var response = {
                entries: [ "entry1", "entry2"],
                batchCount: 1
            };
            promiseObj.resolve(response);
            scope.$apply();

            expect(svc.getNumberOfBatchesLeft()).toBe(0);
            expect(svc.areMoreItemsAvailable()).toBe(false);
        });

        it("must indicate that more items are available when number of batches left is more than 0", function() {
            svc.loadMore();

            var response = {
                entries: [ "entry1", "entry2"],
                batchCount: 2
            };
            promiseObj.resolve(response);
            scope.$apply();

            expect(svc.getNumberOfBatchesLeft()).toBe(1);
            expect(svc.areMoreItemsAvailable()).toBe(true);
        });
    });
});