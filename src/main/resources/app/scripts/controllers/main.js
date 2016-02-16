angular.module('MyApp')
    .controller('MainCtrl', ['$scope','RandomNumSocketData', function($scope, RandomNumSocketData) {
        $scope.headingTitle = 'Akka-Http Angular Example - Home';
        $scope.RandomNumSocketData = RandomNumSocketData;
    }]);