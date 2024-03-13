<template>
  <div class="exitTerminalButtonWrapper">
    <div class="exitTerminalButton" @click="backToProjectWindow">Exit Terminal Mode</div>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";

export default {
  name: 'ExitTerminalButton',
  props:{
    theme: Number
  },
  computed: {
    ...mapState('inOrganization',{
      mode: state => state.mode,
      exitStatus : state => state.exitShellState
    })
  },
  methods: {
    ...mapMutations('inOrganization',['selectProj', 'setOrg','selectInitialProjectWindow']),
    ...mapMutations('terminal', ['resetRunningConnection']),
    backToProjectWindow(){
      this.selectInitialProjectWindow()
      this.resetRunningConnection()
    },
  }
}
</script>

<style scoped>
.exitTerminalButtonWrapper{
  position: fixed;
  height: 3rem;
  bottom: 0rem;
}
.exitTerminalButton{
  display: flex;
  cursor: pointer;
  line-height: 1rem;
  justify-content: center;
  font-size: small;
  border-radius: 0.2rem;
  height: 1rem;
  width: 80%;
  background-color: #2E2E2E;
  color: #9D9D9D;
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