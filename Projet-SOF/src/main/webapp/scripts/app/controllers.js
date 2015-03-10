'use strict';

function TodoListController($scope, $location, data) {
    $scope.persons = data.query();
    $scope.gotoTodoNewPage = function () {
        $location.path("/formation/contributeur/listJs")
    };
}



