﻿"use strict";

(function () {

    angular.module("RecordLabel")
        .constant("settingsUrl", "api/settings/get")
        .constant("itemsPerPageDefault", 10)
        .constant("authenticationUrl", "/api/authentication/authenticate")
        .constant("endSessionUrl", "/api/authentication/endsession")
        .constant("checkAuthStateUrl", "/api/authentication/isauthenticated")
        .constant("imagesUploadUrl", "api/images/upload/")
        .constant("setCoverUrl", "api/thumbnails/create")
        .constant("defaultErrorMsg",
            "An error has been encountered while making a request to server");

})();