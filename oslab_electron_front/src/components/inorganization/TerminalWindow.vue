<template>
  <div :class="{ fullbackground: fulll, background: !fulll }">
    <div class="bar" v-if="fulll" />
    <div id="terminal"></div>

    <img src="@/assets/zoomIn.png" class="zoomin" @click="toggle" v-if="!fulll" />
    <img src="@/assets/zoomOut.png" class="zoomin" @click="toggle" v-else />
    <LoadingSpinner v-if="isLoading"></LoadingSpinner>
    <button class="exitShell" @click="exitShell">x</button>
  </div>
</template>

<script>
import Stomp from "webstomp-client";
import SockJS from "sockjs-client";

import { mapState, mapMutations } from "vuex";

import { Terminal } from "xterm";
import { WebLinksAddon } from "xterm-addon-web-links";
import { FitAddon } from "xterm-addon-fit";
import "xterm/css/xterm.css";
import LoadingSpinner from "@/components/common/LoadingSpinner";
import { terminalState } from '../../store/inOrganization';

export default {
  name: "TerminalWindow",
  components:{
    LoadingSpinner
  },
  data() {
    return {
      term: undefined,
      fit_addon: new FitAddon(),
      shell_head: "",
      flag: false,
      flag2: false,
      isLoading: false
    };
  },
  computed: {
    ...mapState("inOrganization", {
      fulll: (state) => state.full,
    }),
    ...mapState("terminal", {
      wskey: (state) => state.webSocketKey,
      thkey: (state) => state.threadKey,
    }),
  },
  methods: {
    ...mapMutations("inOrganization", ["toggleFullWindow","setExitShellstatus"]),
    async exitShell(){
      var returnValue = confirm("Are you sure you want to shut down the terminal?");
      if(returnValue){
        const keyBundle = {
          wsKey: this.wskey,
          thKey: this.thkey
        }
        console.log(keyBundle.thKey)
        let response = await fetch("http://152.67.213.248:8081/api/request/exitShell", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(keyBundle),
        });
        if(response.body){
          this.setExitShellstatus(terminalState.TERMINATED)
        }
      }
    },
    toggle() {
      if (this.fulll) {
        this.term.resize(40, 80);
        this.fit_addon.fit();
      } else {
        this.term.resize(40, 60);
        this.fit_addon.fit();
      }
      this.toggleFullWindow();
    },
    connect() {
      const serverURL = "http://144.24.78.122:8082/ws";
      let socket = new SockJS(serverURL);
      this.stompClient = Stomp.over(socket);
      console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`);
      this.stompClient.connect(
        {},
        (frame) => {
          // 소켓 연결 성공
          this.connected = true;
          console.log("소켓 연결 성공", frame);
          // 서버의 메시지 전송 endpoint를 구독합니다.
          // 이런형태를 pub sub 구조라고 합니다.
          const subUrl = this.wskey.split(":").join("/");
          this.stompClient.subscribe("/sub/ws/" + subUrl, (res) => {
            if (this.flag) {
              this.flag = !this.flag;
            } else {
              if (this.flag2) {
                this.term.write(res.body);
                this.shell_head = res.body;
                this.flag2 = !this.flag2;
              } else {
                this.term.write("\n" + res.body);
                this.shell_head = res.body;
              }
            }
          });

        },
        (error) => {
          // 소켓 연결 실패
          console.log("소켓 연결 실패", error);
          this.connected = false;
        }
      );
    },
  },

  created() {
    this.term = new Terminal({
      cursorBlink: true,
      cursorStyle: "bar",
      disableStdin: false,
      convertEol: true,
      fontSize: 13,
      fontWeight: "normal",
      rows: 40,
      cols: 60,
    });
    this.connect();
  },
  mounted() {
    this.term.open(document.getElementById("terminal"));

    var curr_line = "";
    var curr_line_pos = 0;
    var entries = [];
    let currEntryPos = 0;

    window.addEventListener("keydown", (e) => {
      if (e.isComposing) return;
    });
    this.term.loadAddon(new WebLinksAddon());
    this.term.loadAddon(this.fit_addon);
    this.fit_addon.fit();

    this.setExitShellstatus(terminalState.OPENED)

    this.term.prompt = () => {
      this.term.write("\n\x1b[32mseunghun>" + curr_line + "\n");
    };

    this.term.write("\x1b[32mWelcome to SSH Desktop!\n\n\x1b[0m");

    this.term.attachCustomKeyEventHandler((event) => {
      if (event.type == "keydown") {
        if (event.ctrlKey) {
          return;
        } else if (event.altKey) {
          return;
        } else if (event.metaKey) {
          return;
        } else if (event.shiftKey) {
          return;
        } else if (event.code == "Escape") {
          return;
        } else if (event.code == "Enter") {
          if (curr_line.replace(/^\\s+|\\s+$/g, "").length != 0) {
            entries.push(curr_line);
            currEntryPos = entries.length - 1;
            const commandDto = {
              key: this.thkey,
              command: curr_line,
            };
            this.flag = true;
            this.flag2 = true;
            fetch("http://152.67.213.248:8081/api/request/command", {
              method: "POST",
              headers: {
                "Content-Type": "application/json",
              },
              body: JSON.stringify(commandDto),
            });
          }
          this.term.write(`\x1b[2K\r${this.shell_head}` + curr_line + "\n");
          curr_line = "";
          curr_line_pos = 0;
        } else if (event.code == "Backspace") {
          if (curr_line_pos > 0) {
            curr_line_pos -= 1;
          }
          let buff_line = curr_line.slice(0, -1);
          curr_line = buff_line;
          this.term.write(`\x1b[2K\r${this.shell_head}` + curr_line);
        } else if (event.code == "Tab") {
          curr_line_pos += 4;
          curr_line += "    ";
          this.term.write("    ");
          return;
        } else if (event.code == "ArrowLeft") {
          if (curr_line_pos == 0) return;
          curr_line_pos--;
          this.term.write("\x1b[D");
        } else if (event.code == "ArrowRight") {
          if (curr_line_pos == curr_line.length) return;
          curr_line_pos++;
          this.term.write("\x1b[C");
        } else if (event.code == "ArrowUp") {
          if (entries.length > 0) {
            if (currEntryPos > 0) {
              currEntryPos -= 1;
            }
            curr_line = entries[currEntryPos];
            curr_line_pos = curr_line.length;
            this.term.write(`\x1b[2K\r${this.shell_head}` + curr_line);
          }
        } else if (event.code == "ArrowDown") {
          curr_line_pos = curr_line.length;
          currEntryPos += 1;
          if (currEntryPos === entries.length || entries.length === 0) {
            currEntryPos -= 1;
            curr_line = "";
            this.term.write(`\x1b[2K\r${this.shell_head}`);
          } else {
            curr_line = entries[currEntryPos];
            this.term.write(`\x1b[2K\r${this.shell_head}` + curr_line);
          }
        } else {
          if (curr_line.length != curr_line_pos) {
            let a = curr_line.slice(0, curr_line_pos);
            let b = curr_line.slice(curr_line_pos);
            curr_line = a + event.key + b;
            console.log(a + " " + b + curr_line + "cur" + "" + curr_line_pos);
            this.term.write(`\x1b[2K\r${this.shell_head}` + curr_line);
            this.term.write(
              "\x1b["
                .concat((curr_line.length - curr_line_pos - 1).toString())
                .concat("D")
            );
          } else {
            curr_line += event.key;
            this.term.write(`\x1b[2K\r${this.shell_head}` + curr_line);
          }
          curr_line_pos += 1;
        }
      }
    });

    // term.onData('key', (key, ev) => {
    //   const printable = !ev.altKey && !ev.altGraphKey && !ev.ctrlKey && !ev.metaKey &&
    //       !(ev.keyCode === 37 && term.buffer.cursorX < 6);
    //
    //   if (ev.keyCode === 13) { // Enter key
    //     if (curr_line.replace(/^\s+|\s+$/g, '').length != 0) { // Check if string is all whitespace
    //       entries.push(curr_line);
    //       currPos = entries.length - 1;
    //       term.prompt();
    //     } else {
    //       term.write('\\n\\33[2K\r\u001b[32mscm> \u001b[37m');
    //     }
    //     curr_line = '';
    //   } else if (ev.keyCode === 8) { // Backspace
    //     if (term.buffer.cursorX > 5) {
    //       curr_line = curr_line.slice(0, term.buffer.cursorX - 6) + curr_line.slice(term.buffer.cursorX - 5);
    //       pos = curr_line.length - term.buffer.cursorX + 6;
    //       term.write('\\33[2K\r\u001b[32mscm> \u001b[37m' + curr_line);
    //       term.write('\\033['.concat(pos.toString()).concat('D')); //term.write('\033[<N>D');
    //       if (term.buffer.cursorX == 5 || term.buffer.cursorX == curr_line.length + 6) {
    //         term.write('\\033[1C')
    //       }
    //     }
    //   } else if (ev.keyCode === 38) { // Up arrow
    //     if (entries.length > 0) {
    //       if (currPos > 0) {
    //         currPos -= 1;
    //       }
    //       curr_line = entries[currPos];
    //       term.write('\\33[2K\r\u001b[32mscm> \u001b[37m' + curr_line);
    //     }
    //   } else if (ev.keyCode === 40) { // Down arrow
    //     currPos += 1;
    //     if (currPos === entries.length || entries.length === 0) {
    //       currPos -= 1;
    //       curr_line = '';
    //       term.write('\\33[2K\r\u001b[32mscm> \u001b[37m');
    //     } else {
    //       curr_line = entries[currPos];
    //       term.write('\\33[2K\r\u001b[32mscm> \u001b[37m' + curr_line);
    //
    //     }
    //   } else if (printable && !(ev.keyCode === 39 && term.buffer.cursorX > curr_line.length + 4)) {
    //     if (ev.keyCode != 37 && ev.keyCode != 39) {
    //       var input = ev.key;
    //       if (ev.keyCode == 9) { // Tab
    //         input = "    ";
    //       }
    //       pos = curr_line.length - term.buffer.cursorX + 4;
    //       curr_line = [curr_line.slice(0, term.buffer.cursorX - 5), input, curr_line.slice(term.buffer.cursorX - 5)].join('');
    //       term.write('\\33[2K\r\u001b[32mscm> \u001b[37m' + curr_line);
    //       term.write('\\033['.concat(pos.toString()).concat('D')); //term.write('\033[<N>D');
    //     } else {
    //       term.write(key);
    //     }
    //   }
    // });
  },
};
</script>

<style scoped>
.fullbackground {
  background-color: black;
  width: 100%;
  height: calc(100vh - 2rem);
}
.background {
  background-color: black;
  width: calc(100vw - 16rem);
  height: calc(100vh - 7rem);
}
.bar {
  height: 2rem;
  background-color: #2b2b2b;
}
#terminal {
  width: 100%;
  height: 100%;
}
.zoomin {
  position: absolute;
  width: 2rem;
  height: 2rem;
  right: 4.5rem;
  bottom: 2rem;
  z-index: 1;
  cursor: pointer;
}
.exitShell{
  background-color: #D9D9D9;
  border-radius: 5px;
  color: #F96464;
  position: absolute;
  width: 2rem;
  height: 2rem;
  right: 2rem;
  bottom: 2rem;
  z-index: 1;
}
</style>
