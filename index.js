
var app = require('express')();
var http = require('http').Server(app);
var io = require('socket.io')(http);

// var message = [];

app.get('/', function (req, res) {
    res.sendFile(__dirname + '/index.html');
});

io.on('connection', function (socket) {
    socket.emit("send id", { id: socket.id });

    socket.on('chat message', function (msg) {
        console.log('message: ' + msg);

        // message.push(msg);

        io.emit('message', { data: msg, id: socket.id });
    
    });

    socket.on('disconnect', () => {
        console.log('disconnected');
    })
});

http.listen(process.env.PORT || 3000, function () {
    console.log('listening on *:3000');
});