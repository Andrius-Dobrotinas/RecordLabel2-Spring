"use strict";

(function () {

    angular.module("RecordLabel").controller("ReleaseViewCtrl",
        ["$scope", "$routeParams", "$sce", "releasesService", "resourceErrorHandler", "stateSvc",
        function ReleaseViewCtrl($scope, $routeParams, $sce, releasesService, resourceErrorHandler, stateSvc) {

            stateSvc.setState(true);

            var takeCareOfResponse = function (data) {
                var model = data.release
                model.youtubeReferences = data.youtubeReferences;

                // TODO: probably convert to a service?
                // Fix youtube links for use with iFrame.
                if (model.youtubeReferences) {
                    model.youtubeReferences.forEach(function (reference) {
                        reference.target = $sce.trustAsResourceUrl(reference.target)
                    })
                }

                $scope.model = model;
            }

            resourceErrorHandler(releasesService.get($routeParams)).$promise.then(takeCareOfResponse)
            .finally(function () {
                stateSvc.setState(false);
            });
        }]);

})();