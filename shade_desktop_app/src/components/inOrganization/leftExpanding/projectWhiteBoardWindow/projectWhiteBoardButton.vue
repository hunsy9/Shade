<template>
  <div class="cont" :class="{click:isClick && mode == 2, 'cont-light': theme==0}">
    <img :src="theme==0 ? require('@/assets/palette-light.png') : require('@/assets/palette.png')">
    <span :class="{'lightSpan': theme==0}">Project WhiteBoard</span>
  </div>
</template>

<script>
import { mapState } from "vuex";

export default {
  name: 'ProjectWhiteBoardButton',
  props:{
    theme: Number
  },
  data() {
    return{
      isClick: false,
    }
  },
  computed: {
    ...mapState('inOrganization',{
      mode: state => state.mode,
      exitStatus : state => state.exitShellState
    })
  },
}
</script>

<style scoped>
.lightSpan{
  color: black;
}
.cont{
  cursor:pointer;
  display: flex;
  align-items: center;
  height: 3rem;
  animation: fade-in 1.2s linear;
}
.cont-light:hover{
  background-color: #efefef;
}
.cont:hover{
  background-color: var(--ModeLeftExpandingHover);
}

img{
  margin-left: 1rem;
  width: 1.5rem;
}
span{
  margin-left: 1rem;
  color: white;
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