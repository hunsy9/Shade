<template>
  <div class="terminalTab" :class="isMain ? 'terminalTab' : 'terminalTab-main'" @click="this.$emit('handleChangeTab')">
    <span class="serverNameSpan">{{server_name}}</span>
    <div class="exitServerBtnWrapper">
      <img src="@/assets/x.png" width="8" height="8"/>
    </div>
  </div>
</template>

<script>

export default {
  name: 'TerminalTab',
  props:{
    server_name: String,
    isMain: Boolean
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
  width: 130px;
  height: 39px;
  justify-content: space-around;
  margin-left: 0.3rem;
  background-color: #000000;
  border-top-left-radius: 0.7rem;
  border-top-right-radius: 0.7rem;
}

.terminalTab-main{
  border-bottom: 3px solid #4D4DB8;
}

.serverNameSpan{
  padding: 1rem;
}
.exitServerBtnWrapper{
  width: 22px;
  height: 22px;
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