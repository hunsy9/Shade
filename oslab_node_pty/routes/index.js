let express = require('express');
const os = require("os");
const pty = require("node-pty");
let router = express.Router();

let flag = false

const makePty = (shell, setEscape, initCommand) =>{
  return new Promise((resolve, reject)=>{
    const ptyTerm =pty.spawn(shell, [], {
      name: "xterm-color",
      cols: 100,
      rows: 24,
      cwd: process.env.HOME,
      env: process.env,
    });
    ptyTerm.write(setEscape+'\n')
    ptyTerm.write(initCommand)
    flag = true
    resolve(ptyTerm)
  })
}

router.get('/', (req, res, next) => {
  res.render('index', { title: 'Express' });
});

router.post('/startSocketRoom', (req,res)=>{
  const namespace = req.body.nameSpace //nameSpace String
  const escapeUUID = namespace.split("/")[3]
  console.log(namespace)
  const ssh = io.of(namespace)

  const setEscape = `export PS1=${escapeUUID}`

  const initCommand = `sshpass -p ${req.body.password} ssh ${req.body.username}@${req.body.host} -p ${req.body.port}\n`

  ssh.on("connection", (socket) => {
    console.log("connection Success!")
    socket.on("startChannel", async () => {
      console.log("Start ptyChannel ", socket.id);
      const shell = os.platform() === "win32" ? "powershell.exe" : "bash";

      // const ptyProcess = pty.spawn(shell, [], {
      //   name: "xterm-color",
      //   cols: 100,
      //   rows: 24,
      //   cwd: process.env.HOME,
      //   env: process.env,
      // });
      //
      // ptyProcess.write(setEscape+'\n')
      // ptyProcess.write(initCommand)

      await makePty(shell,setEscape,initCommand)
          .then((ptyProcess)=>{
            setTimeout(()=>{
              ptyProcess.onData((e) => {
                if(e.trim() === escapeUUID){
                  socket.emit("output", "Connection Closed!");
                  socket.emit("unexpectedExit")
                  socket.disconnect()
                }
                console.log("data: " + e)
                socket.emit("output", e);
              });

              socket.on("input", (input) => {
                ptyProcess.write(input);
              });
            },300)
          })
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
