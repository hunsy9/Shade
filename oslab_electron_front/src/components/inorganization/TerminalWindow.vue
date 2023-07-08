<template>
  <div :class="{fullbackground:fulll, background:!fulll }">
    <div id="terminal"></div>

    <img src="@/assets/zoomIn.png" class="zoomin" @click="toggleFullWindow"  v-if="!fulll"/>
    <img src="@/assets/zoomOut.png" class="zoomin" @click="toggleFullWindow"  v-else/>
  </div>

</template>


<script>

import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'

import { mapState, mapMutations } from "vuex";

import {Terminal} from 'xterm';
import {WebLinksAddon} from 'xterm-addon-web-links';
import {FitAddon} from 'xterm-addon-fit';
import "xterm/css/xterm.css";

export default {
  name: 'TerminalWindow',
  data(){
    return{
      term : undefined
    }
  },
  computed: {
    ...mapState('inOrganization',{
      fulll: state => state.full
    }),
    ...mapState('terminal',{
      wskey: state => state.webSocketKey,
      thkey: state => state.threadKey
    })
  },
  methods: {
    ...mapMutations('inOrganization',['toggleFullWindow']),
    connect(){
      const serverURL = "http://localhost:8082/ws"
      let socket = new SockJS(serverURL);
      this.stompClient = Stomp.over(socket);
      console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`)
      this.stompClient.connect(
        {},
        frame => {
          // 소켓 연결 성공
          this.connected = true;
          console.log('소켓 연결 성공', frame);
          // 서버의 메시지 전송 endpoint를 구독합니다.
          // 이런형태를 pub sub 구조라고 합니다.
          const subUrl = this.wskey.split(":").join("/")
          this.stompClient.subscribe("/sub/ws/" + subUrl, res => {
            console.log('구독으로 받은 메시지 입니다.', res.body);
            this.term.write(res.body+"\n")

          });
        },
        error => {
          // 소켓 연결 실패
          console.log('소켓 연결 실패', error);
          this.connected = false;
        }
      )
    }
  },
  created(){
    this.term = new Terminal({
      cursorBlink: "block",
      convertEol: true,
      fontSize: 13,
      fontWeight: "normal"
    })
    this.connect()
  },
  mounted(){
    this.term.open(document.getElementById('terminal'));

    var curr_line = '';
    // var curr_line_pos = 0;
    var entries = [];
    let currEntryPos = 0;


    window.addEventListener('keydown',(e)=>{
      if(e.isComposing) return
    })
    this.term.loadAddon( new WebLinksAddon())
    const fitAddon = new FitAddon()
    this.term.loadAddon(fitAddon)

    fitAddon.fit();

    this.term.prompt = () => {
      this.term.write('\n\r' + curr_line + '\r\n\u001b[32mseunghun> \u001b[37m');
    };

    this.term.write('Welcome to SSH Desktop!');
    this.term.prompt()



    this.term.attachCustomKeyEventHandler(event => {
      if (event.type == 'keydown') {
        if (event.ctrlKey) {
          this.term.write("ctrl")
        } else if (event.altKey) {
          return
        } else if (event.metaKey) {
          return;
        } else if (event.shiftKey) {
          return;
        } else if (event.code == "Escape") {
          this.term.write("^[")
        } else if (event.code == "Enter") {
          if (curr_line.replace(/^\\s+|\\s+$/g, '').length != 0) {
            entries.push(curr_line)
            currEntryPos = entries.length - 1;
            const commandDto = {
              "key" : this.thkey,
              "command" : curr_line
            }
            fetch("http://localhost:8081/api/request/command",{
              method: 'POST',
              headers: {
                'Content-Type': 'application/json',
              },
              body: JSON.stringify(commandDto)
            })
            this.term.prompt()
          }
          curr_line = ''
        } else if (event.code == "Backspace") {
          let buff_line = curr_line.slice(0,-2)
          curr_line = buff_line
          this.term.write('\\33[2K\r\u001b[32mseunghun> \u001b[37m' + curr_line);
        } else if (event.code == "Tab") {
          curr_line += "    "
          this.term.write("    ")
          return;
        } else if(event.code == "ArrowLeft"){
          this.term.write('\x1b[D')
        } else if(event.code == "ArrowRight"){
          this.term.write('\x1b[C')
        } else if (event.code == "ArrowUp") {
          if (entries.length > 0) {
            if (currEntryPos > 0) {
              currEntryPos -= 1;
            }
            // if(currEntryPos == 0){
            //   return;
            // }
            curr_line = entries[currEntryPos];
            this.term.write('\\33[2K\r\u001b[32mseunghun> \u001b[37m' + curr_line);
          }
        } else if (event.code == "ArrowDown") {
          currEntryPos += 1;
          if (currEntryPos === entries.length || entries.length === 0) {
            currEntryPos -= 1;
            curr_line = '';
            this.term.write('\\33[2K\r\u001b[32mseunghun> \u001b[37m');
          } else {
            curr_line = entries[currEntryPos];
            this.term.write('\\33[2K\r\u001b[32mseunghun> \u001b[37m' + curr_line);
          }
        }
        else {
          this.term.write(event.key)
          curr_line += event.key
        }
      }
    })


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

  }
}
</script>

<style scoped>
.fullbackground{
  background-color: black;
  width: 100%;
  height: 100vh;
}
.background{
  background-color: black;
  width: calc(100vw - 16rem);
  height: calc(100vh - 7rem);
}
.zoomin{
  position: absolute;
  width: 2rem;
  height: 2rem;
  right: 2rem;
  bottom: 2rem;
  z-index: 1;
  cursor:pointer;
}
</style>