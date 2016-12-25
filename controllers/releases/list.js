"use strict";

(function () {

    angular.module("RecordLabel").controller("ReleaseListCtrl",
        ["$scope", "releasesService", "batchedListSvc", "stateSvc",
        function ReleaseListCtrl($scope, releasesService, batchedListSvc) {
            var ctrl = this;

            var svc = batchedListSvc(releasesService, $scope.settings.itemsPerPage);

            ctrl.entries = function () {
                return svc.entries;
            }

            ctrl.moreItemsAvailable = function () {
                return svc.moreItemsAvailable;
            }

            ctrl.isLoading = function () {
                return !svc.promise.$resolved;
            }

            ctrl.loadMore = function () {
                svc.loadMore();
            }
        }]);

})();