"use strict";

(function () {

    var application = angular.module("RecordLabel");

    application.factory("metadataService", ["$resource", function ($resource) {
        return $resource("/api/metadata/get");
    }]);

    application.factory("releasesService", ["$resource", function ($resource) {
        return $resource("/api/releases/:act/:id", { id: "@id", size: "@size", number: "@number" }, {
            get: { method: "GET", params: { act: "get" } },
            queryBatch: { method: "GET", params: { act: "getBatch" } },
            getForEdit: { method: "GET", params: { act: "getForEdit" } },
            getTemplate: { method: "GET", params: { act: "getTemplate" }, cache: true },
            save: { method: "POST", params: { act: "Post" } }
        });
    }]);

    application.factory("constantsService", ["$resource", function ($resource) {
        return $resource("/api/constants/:act", { id: "@id" }, {
            get: { method: "GET", params: { act: "get" }, cache: true },
        });
    }]);

    application.factory("artistsService", ["$resource", function ($resource) {
        return $resource("api/artists/:act", { id: "@id" }, {
            // Returns a list of artists with essential data only
            getList: { method: "GET", params: { act: "getList" }, isArray: true }
        });
    }]);

    application.factory("mediaTypesService", ["$resource", function ($resource) {
        return $resource("api/mediatypes/:act");
    }]);

})();