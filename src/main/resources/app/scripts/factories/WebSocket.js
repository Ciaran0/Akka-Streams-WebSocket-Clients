angular.module('MyApp')
    .factory('RandomNumSocketData', function($websocket) {
        // Open a WebSocket connection
        var dataStream = $websocket('ws://localhost:8080/randomNums');

        var collection = [];

        dataStream.onMessage(function(message) {
            //collection.push(JSON.parse(message.data));
            collection.push(message.data);
        });

        var methods = {
            collection: collection,
            get: function() {
                dataStream.send(JSON.stringify({ action: 'get' }));
            }
        };

        return methods;
    });
