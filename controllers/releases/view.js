"use strict";

(function () {

    angular.module("RecordLabel").controller("ReleaseViewCtrl",
        ["$scope", "$routeParams", "$sce", "releasesService", "resourceErrorHandler", "stateSvc",
            function ReleaseViewCtrl($scope, $routeParams, $sce, releasesService, resourceErrorHandler) {
                var ctrl = this;
                $scope.model;

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

                var promise = resourceErrorHandler(releasesService.get($routeParams));;
                promise.$promise.then(takeCareOfResponse);

                ctrl.isLoading = function () {
                    return !promise.$resolved;
                }
            }]);
})();