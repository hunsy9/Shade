<template>
  <div class="cont" @click="open">
    <img class="dotdot" :src="theme==0 ? require('@/assets/dotdot-light.png') : require('@/assets/dotdot.png') ">
    <span>Project</span>
    <img class="img2" src="@/assets/openproj.png">
  </div>
  <template v-if="isOpen == true">
    <div class="proj" v-for="(item, index) in projlist" :key="index">
      <div class="projectBlock" :class="{click:isClick && mode == 1 && index == pickedProj }" @click="clickProj(item.project_name, index)">
        <img class="img1" :src="theme==0 ? require('@/assets/inproj-light.png') : require('@/assets/inproj.png')">
        <span>{{ item.project_name }}</span>
      </div>
    </div>
  </template>
</template>

<script>
import {createNamespacedHelpers} from 'vuex'
// import { terminalState } from '../../store/inOrganization';
const { mapState, mapMutations,mapActions } = createNamespacedHelpers('inOrganization')

export default {
  name: 'LeftExpandingMenu',
  props:{
    theme: Number
  },
  data() {
    return{
        isOpen: false,
        isClick: false,
        pickedProj: 0
    }
  },
  computed: {
    ...mapState({
      projlist: state => state.projects,
      mode: state => state.mode,
      exitStatus: state => state.exitShellState
    }),
    ...mapState({
      org_id: state => state.organId,
      org_name: state => state.organName
    })
  },
  methods: {
    ...mapMutations(['selectProj', 'setOrg','selectInitialProjectWindow']),
    ...mapActions(['getProjects']),
    open(){
      this.isOpen = !this.isOpen
      if(this.mode!=3){
        this.selectInitialProjectWindow()
      }
    },
    clickProj(name, index){
      // if(this.exitStatus == terminalState.OPENED){
      //   alert("The terminal did not shut down\n\n" +
      //       "Shut down the terminal first!")
      //   return
      // }
      this.isClick = true
      this.pickedProj = index
      this.selectProj(name)
    }
  }
}
</script>

<style scoped>

.cont{
  cursor:pointer;
  padding-bottom: 0.7rem;
  animation: fade-in 1.2s linear;
}

.cont span{
  position: relative;
  top: -0.6rem;
  margin-left: 1.1rem;
  color: var(--ModeTextColor);
}

.click{
  /*background-color: #313131;*/
}
.img1{
  margin-top: 0.7rem;
  margin-bottom: 0.5rem;
  margin-left: 3rem;
  width: 1.2rem;
}
.proj span{
  position: relative;
  top: -0.7rem;
  margin-left: 1.1rem;
  color: var(--ModeTextColor);
  font-size: 0.9rem;
}

.dotdot{
  margin-top: 0.7rem;
  margin-bottom: 0.5rem;
  margin-left: 1.2rem;
  width: 1.2rem;
}
.img2{
  position: relative;
  top: -0.75rem;
  margin-left: 4.5rem;
  width: 0.5rem;
}
.projectBlock:hover{
  background-color: var(--ModeLeftExpandingHover);
}
.proj{
  cursor:pointer;
  /*margin-left: 0.5rem;*/
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