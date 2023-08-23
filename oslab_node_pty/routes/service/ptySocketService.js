const express = require("express");
let router = express.Router();
const {createServer} = require("http");
const {Server: PtySocketService} = require("socket.io");

const httpServer = createServer((req, res) => {
    res.write("terminal server started");
    res.end();
});

const port = 3001;

httpServer.listen(port, () => {
    console.log("server listening on port", port);

    const io = new PtySocketService(httpServer, {
        cors:{
            origin:true,
            credentials:true
        }
    });

    global.io = io

    console.log("Socket IO started, waiting for connection");
});

module.exports = router;