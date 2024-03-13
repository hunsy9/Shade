<template>
  <div class="terminalTab">
    <span class="serverNameSpan">{{server_name}}</span>
    <div class="exitServerBtnWrapper">
      <img src="@/assets/x.png" width="8"/>
    </div>
  </div>
</template>

<script>

export default {
  name: 'TerminalTab',
  props:{
    server_name: String
  },
  data() {
    return{

    }
  },
  computed: {
  },
}
</script>

<style scoped>
.terminalTab{
  display: flex;
  cursor: pointer;
  align-items: center;
  justify-content: center;
}
.serverNameSpan{
  padding: 1rem;
}
.exitServerBtnWrapper{
  width: 16px;
  height: 16px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 0.2rem;
  cursor: pointer;
}
.exitServerBtnWrapper:hover{
  background-color: #1b1b1b;
}
</style>