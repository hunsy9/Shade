<template>
  <div class="cont" @click="open">
    <img class="img1" src="@/assets/dotdot.png">
    <span>PROJECT</span>
    <img class="img2" src="@/assets/openproj.png">
  </div>
  <template v-if="isOpen == true">
    <div class="proj" v-for="(item, index) in projlist" :key="index">
      <div :class="{click:isClick && mode == 1 && index == pickedProj }" @click="clickProj(item.project_name, index)">
        <img class="img1" src="@/assets/inproj.png">
        <span>{{ item.project_name }}</span>
      </div>
    </div>
  </template>
</template>
  
<script>
import { createNamespacedHelpers } from 'vuex'
const { mapState, mapMutations } = createNamespacedHelpers('inOrganization')

export default {
  name: 'LeftExpandingMenu',  
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
      mode: state => state.mode
    }),
  },
  methods: {
    ...mapMutations(['selectProj']),
    open(){
      this.isOpen = !this.isOpen
    },
    clickProj(name, index){
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
}
.click{
  background-color: #313131;
}
.img1{
  margin-top: 0.7rem;
  margin-left: 1.2rem;
  width: 1.2rem;
}
span{
  margin-left: 1.1rem;
  margin-bottom: 0.7rem;
  color: white;
}
.img2{
  margin-top: 0.7rem;
  margin-left: 1rem;
  width: 0.5rem;
}
.proj{
  cursor:pointer;
  margin-left: 2.5rem;
}
</style>