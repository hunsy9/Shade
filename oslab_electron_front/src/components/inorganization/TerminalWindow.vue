<template>
  <div :class="{fullbackground:fulll, background:!fulll }">
    <div id="terminal"></div>

    <img src="@/assets/zoomIn.png" class="zoomin" @click="toggleFullWindow"  v-if="!fulll"/>
    <img src="@/assets/zoomOut.png" class="zoomin" @click="toggleFullWindow"  v-else/>
  </div>
</template>


<script>
import { Terminal } from 'xterm';
import { WebLinksAddon } from 'xterm-addon-web-links';
import { FitAddon } from 'xterm-addon-fit';
import "xterm/css/xterm.css";

import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'

import { mapState, mapMutations } from "vuex";

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
    })
    this.connect()
  },
  mounted(){
    this.term.open(document.getElementById('terminal'));
    
    var curr_line = '';
    var entries = [];
    // var currPos = 0;
    // var pos = 0;


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

    this.term.attachCustomKeyEventHandler(event =>{
      if(event.type == 'keydown'){
        if(event.ctrlKey){
          this.term.write("ct")
        }
        if(event.code == "Escape")
        {
          this.term.write("^[")
        }
        if(event.code == "Enter"){
          const commandDto = {
            "key" : this.thkey,
            "command" : "ls"
          }
          fetch("http://localhost:8081/api/request/command",{
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(commandDto)
          })
          this.term.write("테스트성공 ㅋㅋㅋ")
          this.term.prompt()
        }
        if(event.code == "Backspace"){
          return
        }
        if(event.altKey){
          return
        }
        if(event.metaKey){
          return;
        }
        if(event.shiftKey){
          return;
        }
        else{
          this.term.write(event.key)
          entries.push(event.key)
        }
      }
    })
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