/**
 * @author Leon K
 */
(function () {
    'use strict';

    angular.module('UnityApp', [
        'ui.router'
    ])
        .config(config)
        .controller('appController', AppController);

    function config($urlRouterProvider, $stateProvider) {
        //$urlRouterProvider
        //.otherwise('/');
        $stateProvider
            .state('home', {
                url: 'home',
                templateUrl: 'views/partials/home.html',
                controller: 'HomeCtrl',
                controllerAs: 'homeCtrl'
            })
            .state('groups', {
                url: 'groups',
                templateUrl: 'views/partials/groups.html',
                controller: 'GroupsCtrl',
                controllerAs: 'groupsCtrl'
            }).state('subGroups', {
                url: 'subGroups',
                templateUrl: 'views/partials/subGroups.html',
                controller: 'SubGroupsCtrl',
                controllerAs: 'subGroupsCtrl'
            }).state('/', {
                url: '/',
                templateUrl: 'views/partials/home.html',
                controller: 'HomeCtrl',
                controllerAs: 'homeCtrl'
            })
    }

    /**
     *
     * App Controller
     */
    AppController.$inject = ['$scope', '$rootScope'];
    function AppController() {
        $rootScope.contextUrl = '/';

        $scope.$on('$stateChangeSuccess', onStateChangeSuccess);

        ////Helper methods////
        function onStateChangeSuccess(event, toState, toParams, fromState, fromParams) {
            var defaultAppName = 'UnityApp';

            if (!angular.isDefined(toState.data.pageTitle)) {
                $scope.pageTitle = defaultAppName;
            } else {
                $scope.pageTitle = defaultAppName + ' - ' + toState.data.pageTitle;
            }

            if (!angular.isDefined(toState.data.appName)) {
                $scope.appName = defaultAppName;
            }
        }
    }
})();