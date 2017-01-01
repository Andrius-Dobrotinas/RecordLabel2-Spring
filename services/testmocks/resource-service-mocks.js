"use strict";

/**
 *  @description Injects promise capability into an object's function. The specified function
 *  gets set up to return a promise that is resolved by calling "resolve" on the object that
 *  is returned by this function.
 *  A handy way of emulating AngularJS resource-based services in order to test if a method
 *  has been called, but doesn't return
 *  @param {object} angularQ AngularJS $q service
 *  @param {object} resourceService Any object whose function is to be set up to return a promise
 *  @param {string} methodName Name of the resourceService's function to be set up
 */
function injectPromiseIntoServiceMock(qService, resourceService, methodName) {
    var promiseObj = {
        serviceDeferredPromise: null,
        promise: null,
        resolve: function(data) {
            if (!this.serviceDeferredPromise || !this.promise)
                throw "Cannot resolve because the promise has not been created yet";

            this.serviceDeferredPromise.resolve(data);
            this.promise.$resolved = true;
        },

    };
    resourceService[methodName] = function(params) {
        promiseObj.serviceDeferredPromise = qService.defer();
        promiseObj.promise = {
            $promise: promiseObj.serviceDeferredPromise.promise,
            $resolved: false
        };
        return promiseObj.promise;
    };

    return promiseObj;
}