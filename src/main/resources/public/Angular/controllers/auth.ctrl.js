﻿"use strict";

(function () {

    angular.module("RecordLabel").controller("AuthCtrl",
        ["$scope", "authService",
        function ReleaseEditCtrl($scope, authService) {
            var ctrl = this;

            ctrl.authInitiated = false;

            $scope.model = {
                username : undefined,
                password : undefined
            };

            ctrl.isAuthInitiated = function() {
                return ctrl.authInitiated;
            };

            ctrl.getAuthError = function() {
                return authService.getAuthError();
            };

            ctrl.isGodMode = function() {
                return authService.isGodMode();
            };

            ctrl.initAuthentication = function() {
                ctrl.authInitiated = true;
            };

            ctrl.authenticate = function() {
                var creds = {
                    username: $scope.model.username,
                    password: $scope.model.password
                };
                authService.authenticate(creds);
                $scope.model.password = undefined;
            };

            ctrl.logout = function() {
                // TODO
                alert("TODO!");
            };

    }])
})();