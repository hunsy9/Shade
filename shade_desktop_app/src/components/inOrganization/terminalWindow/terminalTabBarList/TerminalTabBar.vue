<template>
  <div class="terminalTabBar">
      <div class="terminalTabListWrapper" v-for="(item, index) in runningConnection" :key="index">
        <TerminalTab :server_name="item.server_name" :isMain="index===mainTerminalIndexState" @handleChangeTab="handleChangeTerminalTab(index)"/>
      </div>
    <div class="addTerminalTab">
      <div class="addTerminalTabWrapper">
        <div class="addTerminalBtn">
          <img src="@/assets/plus.png" width="20"/>
        </div>
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
      mainTerminalIndexState: 0
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
  methods:{
    handleChangeTerminalTab(idx){
      this.mainTerminalIndexState = idx
    }
  }
}
</script>

<style scoped>
.terminalTabBar{
  color: white;
  display: flex;
  margin-left: 0.2rem;
}

.terminalTabListWrapper{
  display: flex;
  flex-wrap: nowrap;
  overflow-x: scroll;
}

.addTerminalTab{
  display: flex;
  margin-left: 0.2rem;
  border-top-left-radius: 0.3rem;
  border-top-right-radius: 0.3rem;;
}

.addTerminalTabWrapper{
  width: 2rem;
  justify-content: center;
  display: flex;
  align-items: center;
}

.addTerminalBtn{
  border-radius: 0.2rem;
  display: flex;
  align-items: center;
  cursor: pointer;
}

.addTerminalBtn:hover{
  background-color: #1b1b1b;
}
</style>