﻿"use strict";

(function () {

    angular.module("RecordLabel").controller("ReleaseEditCtrl",
        ["$scope", "$routeParams", "releasesService", "artistsService", "mediaTypesService", "constantsService", "resourceErrorHandler", "modelPostResourceService",
        function ReleaseEditCtrl($scope, $routeParams, releasesService, artistsService, mediaTypesService, constantsService, resourceErrorHandler, modelPostResourceService) {

            $scope.validationErrors = [];
            $scope.constants = resourceErrorHandler(constantsService.get());
            $scope.artists = resourceErrorHandler(artistsService.getList());
            $scope.mediaTypes = resourceErrorHandler(mediaTypesService.query());

            // Either get an existing entry for editing or, if not Id supplied, get a template for a new item
            $scope.model;
            if ($routeParams.id) {
                $scope.model = resourceErrorHandler(releasesService.getForEdit($routeParams));
            } else {
                $scope.model = resourceErrorHandler(releasesService.getTemplate());

                // Set Release template Artist and MediaType Ids to null in order for Select element validations to work
                $scope.model.$promise.then(function (data) {
                    data.artistId = null;
                    data.mediaId = null;
                })
            }

            $scope.update = function (form) {
                $scope.errors.length = 0;

                if (form.$valid) {
                    $scope.validationErrors.length = 0;
                    modelPostResourceService(releasesService.save($scope.model), "/Releases", $scope.validationErrors);
                } else {
                    $scope.errors.push({ statusText: "Data cannot be submitted because there are validation errors!" });

                    // Mark fields as dirty so that the UI displayed messages for each
                    for (let field in form) {
                        if (field.charAt(0) !== "$") {
                            form[field].$setDirty();
                        }
                    }
                }
            }

            $scope.addReference = function () {
                $scope.model.references.push(angular.copy($scope.constants.defaultReference));
            }

            $scope.deleteReference = function (index) {
                $scope.model.references.splice(index, 1);
            }

            $scope.addTrack = function () {
                $scope.model.tracks.push(angular.copy($scope.constants.defaultTrack));
            }

            $scope.deleteTrack = function (index) {
                $scope.model.tracks.splice(index, 1);
            }

            // Checks if a field has invalid value
            $scope.isValid = function (formField) {
                return formField.$dirty && formField.$invalid;
            }

            // Checks if a field has an invalid NON-empty value
            $scope.isValidForRequired = function (formField) {
                return formField.$dirty && !formField.$error.required && formField.$invalid;
            }

            // Checks if a field is empty
            $scope.isEmptyForRequired = function (formField) {
                return formField.$dirty && formField.$error && formField.$error.required;
            }
        }]);

})();