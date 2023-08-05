let express = require('express');
const os = require("os");
const pty = require("node-pty");
let router = express.Router();

router.get('/', (req, res, next) => {
  res.render('index', { title: 'Express' });
});

router.post('/startSocketRoom', (req,res)=>{
  const namespace = req.body.nameSpace //nameSpace String
  console.log(namespace)
  const ssh = io.of(namespace)

  const initCommand = `sshpass -p ${req.body.password} ssh ${req.body.username}@${req.body.host} -p ${req.body.port}\n`

  ssh.on("connection", (socket) => {

    socket.on("startChannel", async (identifier) => {
      console.log(identifier)
      console.log("Start ptyChannel ", socket.id);
      const shell = os.platform() === "win32" ? "powershell.exe" : "bash";

      const ptyProcess = pty.spawn(shell, [], {
        name: "xterm-color",
        cols: 100,
        rows: 24,
        cwd: process.env.HOME,
        env: process.env,
      });

      ptyProcess.write(initCommand)

      ptyProcess.onData((e) => {
        console.log("data: " + e)
        socket.emit("output", e);
      });

      socket.on("input", (input) => {
        ptyProcess.write(input);
      });
    })

    socket.on("disconnect", () => {
      console.log("Disconnected Socket: ", socket.id);
      io.of(namespace).local.disconnectSockets()
      io._nsps.delete(namespace)
    });
  });

  res.send("success")
})

module.exports = router;
