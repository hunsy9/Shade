<template>
  <div :class="{fullbackground:fulll, background:!fulll }">


    <img src="@/assets/zoomIn.png" class="zoomin" @click="toggleFullWindow"  v-if="!fulll"/>
    <img src="@/assets/zoomOut.png" class="zoomin" @click="toggleFullWindow"  v-else/>
  </div>

</template>
  
<script>

import { createNamespacedHelpers } from 'vuex'
const { mapState, mapMutations } = createNamespacedHelpers('inOrganization')

export default {
  name: 'TerminalWindow', 
  computed: {
    ...mapState({
      fulll: state => state.full
    })
  },
  methods: {
    ...mapMutations(['toggleFullWindow']),
  },
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