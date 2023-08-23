const express = require("express");
let router = express.Router();
const {createServer} = require("http");
const {Server: PtySocketService} = require("socket.io");

const httpServer = createServer((req, res) => {
    res.header("Access-Control-Allow-Origin", "*");
    res.write("terminal server started");
    res.end();
});

const whitelist = [
    "*",
];

const corsOptions = {
    origin: (origin, callback) => {
        if (whitelist.indexOf(origin) !== -1) {
            callback(null, true);
        } else {
            callback(new Error("NOT allowed"));
        }
    },
    credentials: true,
};

const port = 3001;

httpServer.listen(port, () => {
    console.log("server listening on port", port);

    const io = new PtySocketService(httpServer,{
        cors: corsOptions
    });

    global.io = io

    console.log("Socket IO started, waiting for connection");
});

module.exports = router;