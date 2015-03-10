'use strict';

angular.module('todoApp', ['todoService']).
        config(['$routeProvider', function ($routeProvider) {
    $routeProvider.
            when('/formation/contributeur/listJs', {templateUrl:'views/arbreformation/contributeurJs.jsp', controller:TodoListController}).

            otherwise({redirectTo:'/formation/contributeur/listJs'});
}]);
