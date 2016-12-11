"use strict";

(function () {

    angular.module("RecordLabel", ["ngRoute", "ngResource"])
        .config(function ($routeProvider) {
            $routeProvider.caseInsensitiveMatch = true;
            $routeProvider.when("/Releases", {
                templateUrl: "Angular/templates/ReleaseList.html",
                controller: "ReleaseListCtrl",
                controllerAs: "ctrl"
            })
            .when("/Releases/New", {
                templateUrl: "Angular/templates/ReleaseEdit.html",
                controller: "ReleaseEditCtrl"
            })
            .when("/Releases/:id", {
                templateUrl: "Angular/templates/ReleaseView.html",
                controller: "ReleaseViewCtrl"
            })
            .when("/Releases/Edit/:id", {
                templateUrl: "Angular/templates/ReleaseEdit.html",
                controller: "ReleaseEditCtrl"
            })
            .otherwise({
                redirectTo: "/Releases"
            }) 
        })
        .run(["$rootScope", "metadataService", "infoMsgService", function ($rootScope, metadataService, infoMsgService) {
            $rootScope.errors = [];
            $rootScope.infoMessage;
            $rootScope.isLoading = false;

            // Populate global metadata list because we'll need it a lot
            $rootScope.metadataList = metadataService.query();

            // Default settings, in case ajax request for settings doesn't come through
            $rootScope.settings = {
                itemsPerPage: 10
            };

            // Synchronous call to get settings. Settings must be retrieved before any other requests are be made
            // because they contain ItemsPerPage param.
            $.ajax({
                url: "api/settings/get",
                async: false
            }).done(function (data) {
                $rootScope.settings.itemsPerPage = data.itemsPerPage;
            });

            $rootScope.$on('$locationChangeStart', function (event) {
                $rootScope.errors.length = 0;
                infoMsgService.changeLocation();
            });

            // TODO: temporary until I implement the whole thing
            $rootScope.isAdminMode = true;
        }]);

})();