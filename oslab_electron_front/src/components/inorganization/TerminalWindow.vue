<template>
  <div id="terminal"></div>
  <button class="exitShell" @click="exitShell">x</button>
</template>

<script>
import { Terminal } from "xterm";
import {FitAddon} from "xterm-addon-fit";
import "xterm/css/xterm.css";
import { io } from "socket.io-client";
import { mapState } from "vuex";

export default {
  name: 'TerminalWindow',
  data(){
    return{
      startFlag : false,
      socket: null,
      term: null
    }
  },
  computed:{
    ...mapState('terminal',{
      serverAddress: state => state.serverAddress
    }),
  },
  methods:{
    exitShell(){
      this.socket.disconnect()
      this.term.write("\nBroken Pipe")
    }
  },
  mounted() {
    const serverAddress = this.serverAddress; // your server address


    const createTerminal = (term) => {
      term.open(document.getElementById("terminal"));
      term.options = {
        fontSize: 12,
        fontWeight: "normal"
      };
      this.term = term
    };

    const connectToSocket = (serverAddress) => {
      console.log(serverAddress)
      return new Promise((res) => {
        const socket = io(serverAddress);
        this.socket = socket
        res(socket);
      });
    };

    connectToSocket(serverAddress)
        .then((socket) => {
          console.log("The socket is getting first");
          socket.on("connect", () => {
            console.log("Id", socket.id);
            console.log("Now the terminal is instantiated");
            // const x = ""
            const term = new Terminal({
              rows: 44,
              cols: 100, //any value
            });
            const fitAddon = new FitAddon();
            term.loadAddon(fitAddon);
            fitAddon.fit();
            console.log("Creating a new terminal now");
            function onSize() {
              fitAddon.fit(); // note: fitAddon.proposeDimensions() only will produce the correct result
            }
            onSize();
            window.addEventListener('resize', onSize, false);
            createTerminal(term);

            socket.emit("startChannel",this.serverAddress)

            socket.on("output", (data) => {
              console.log("Now I am getting data from pty", data);
              // When there is data from PTY on server, print that on Terminal.
              term.write(data);
            });

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
.exitShell {
  background-color: #d9d9d9;
  border-radius: 5px;
  color: #f96464;
  position: absolute;
  width: 2rem;
  height: 2rem;
  right: 2rem;
  bottom: 2rem;
  z-index: 1;
}
</style>