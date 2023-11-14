<template>
  <div class="terminalWrapper">
    <div id="terminal"></div>
  </div>
</template>

<script>
import { Terminal } from "xterm";
import {FitAddon} from "xterm-addon-fit";
import "xterm/css/xterm.css";
import { io } from "socket.io-client";
import {mapMutations, mapState} from "vuex";
import {terminalState} from "@/store/inOrganization";

export default {
  name: 'TerminalWindow',
  data(){
    return{
      startFlag : false,
      socket: null,
      term: null,
      ExitStatus: false
    }
  },
  computed:{
    ...mapState('terminal',{
      serverAddress: state => state.serverAddress
    }),
  },
  methods:{
    ...mapMutations('inOrganization', [
        "setExitShellstatus"
    ]),
    exitShell(){
      this.socket.disconnect()
      this.setExitShellstatus(terminalState.TERMINATED)
    }
  },
  mounted() {
    const serverAddress = this.serverAddress; // your server address

    this.setExitShellstatus(terminalState.OPENED)

    const createTerminal = (term) => {
      term.open(document.getElementById("terminal"));
      term.options = {
        fontSize: 12,
        fontWeight: "normal"
      };
      this.term = term
    };

    const connectToSocket = (serverAddress) => {
      return new Promise((res) => {
        const socket = io(serverAddress);
        this.socket = socket
        res(socket);
      });
    };

    connectToSocket(serverAddress)
        .then((socket) => {
          socket.emit("startChannel",this.serverAddress)
          console.log("The socket is getting first");
          socket.on("connect", () => {
            console.log("Id", socket.id);
            console.log("Now the terminal is instantiated");
            const term = new Terminal({
              rows: 49,
              cols: 100, //any value
            });
            const fitAddon = new FitAddon();
            term.loadAddon(fitAddon);
            fitAddon.fit();
            console.log("Creating a new terminal now");
            function onSize() {
              fitAddon.fit();
            }
            onSize();
            window.addEventListener('resize', onSize, false);
            createTerminal(term);

            socket.on("output", (data) => {
              console.log("Now I am getting data from pty", data);
              // When there is data from PTY on server, print that on Terminal.
              term.write(data);
            });

            socket.on("unexpectedExit", ()=>{
              this.setExitShellstatus(terminalState.TERMINATED)
            })

            term.onData((data) => {
              console.log("Now data is being emitted", data);
              socket.emit("input", data);
            });
          });
        })
        .catch((err) => console.log("Error occured while connecting to socket" + err));
  }
}
</script>

<style scoped>
.terminalWrapper{
  background-color: black;
  height: 100%;
}
#terminal{
  padding: 10px;
  animation: fade-in 0.7s linear;
}

@keyframes fade-in {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}
</style>