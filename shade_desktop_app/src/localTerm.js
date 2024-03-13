const pty = require("node-pty")
const os = require("os");

export const makeLocalPty = () =>{
    const shell = os.platform() === "win32" ? "powershell.exe" : "bash";
    return new Promise((resolve)=>{
        const ptyTerm = pty.spawn(shell, [], {
            name: "xterm-color",
            cols: 100,
            rows: 24,
            cwd: process.env.HOME,
            env: process.env,
        });
        resolve(ptyTerm)
    })
}