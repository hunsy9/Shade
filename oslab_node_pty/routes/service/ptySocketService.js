const express = require("express");
let router = express.Router();
const {createServer} = require("http");
const {Server: PtySocketService} = require("socket.io");

const httpServer = createServer((req, res) => {
    res.setHeader("Access-Control-Allow-Origin", "*");
    res.write("terminal server started");
    // res.end();
});

const port = 3001;

httpServer.listen(port, () => {
    console.log("server listening on port", port);

    const io = new PtySocketService(httpServer,{
        cors: {
            credentials: true,
        },
        allowEIO3: true,
    });

    global.io = io

    console.log("Socket IO started, waiting for connection");
});

module.exports = router;