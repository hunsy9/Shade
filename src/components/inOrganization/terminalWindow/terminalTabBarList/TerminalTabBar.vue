<template>
  <div class="terminalTabBar">
<!--    <draggable :list="runningConnection">-->
      <div class="terminalTabListWrapper" v-for="(item, index) in runningConnection" :key="index">
        <TerminalTab :server_name="item.server_name"/>
      </div>
<!--    </draggable>-->
    <div class="addTerminalTabWrapper">
      <div class="addTerminalBtn">
        <img src="@/assets/plus.png" width="20"/>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
import TerminalTab from "@/components/inOrganization/terminalWindow/terminalTabBarList/terminalTab/TerminalTab.vue";
// import { VueDraggableNext } from 'vue-draggable-next'

export default {
  name: 'TerminalTabBar',
  components: {
    TerminalTab,
    // draggable: VueDraggableNext
  },
  props:{
    theme: Number
  },
  data() {
    return{

    }
  },
  computed: {
    ...mapState('inOrganization',{
      mode: state => state.mode,
      exitStatus : state => state.exitShellState
    }),
    ...mapState('terminal',{
      runningConnection: state => state.runningConnection
    })
  },
}
</script>

<style scoped>
.terminalTabBar{
  color: white;
  display: flex;
}

.terminalTabListWrapper{
  display: flex;
  flex-wrap: nowrap;
  overflow-x: scroll;
}

.addTerminalTabWrapper{
  width: 5rem;
  justify-content: center;
  display: flex;
  align-items: center;
}

.addTerminalBtn{
  width: 22px;
  height: 22px;
  border-radius: 0.2rem;
  display: flex;
  align-items: center;
  cursor: pointer;
}

.addTerminalBtn:hover{
  background-color: #1b1b1b;
}
</style>