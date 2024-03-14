<template>
  <div class="zidx" @click="this.$emit('closeProjectSelectionModal')" >
    <main>
      <div class="modalframe">
        <div class="curProj">
          <span>Current Project: {{proj}}</span>
        </div>
        <draggable :list="projlist">
          <transition-group>
            <div class="proj" v-for="(item, index) in projlist" :key="index">
              <div class="projectBlock" @click="clickProj(item.project_name, index)" v-on:mouseover="mouseOverElement = index">
                <img class="projIcon" :src="theme==0 ? require('@/assets/dotdot-light.png') : require('@/assets/dotdot.png') ">
                <span>{{ item.project_name }}</span>
                <div class="additionalOptionWrapper" v-if="mouseOverElement == index" @click="clickProjectOption(item, $event)">
                  <img src="@/assets/dot-horizontal.png" width="11"/>
                </div>
              </div>
            </div>
          </transition-group>
        </draggable>
        <div class="addNewProjectBlock" v-if="isAdmin == true" @click="$emit('openNewProjectModal')">
          <img src="@/assets/plus.png" width="18">
        </div>
      </div>
    </main>
  </div>
</template>

<script>
import {createNamespacedHelpers} from "vuex";
import { VueDraggableNext } from 'vue-draggable-next'
const { mapState, mapMutations,mapActions } = createNamespacedHelpers('inOrganization');

export default {
  name: 'ModalProjectSelect',
  props: {
    openProjectSelectionModal: Boolean,
    theme: Number,
    shiftState: Number
  },
  computed: {
    ...mapState({
      projlist: state => state.projects,
      proj: state => state.selected_proj,
      mode: state => state.mode,
      isAdmin: state => state.isAdmin
    }),
  },
  data(){
    return{
      mouseOverElement: -1
    }
  },
  methods: {
    ...mapMutations(['selectProj', 'setOrg','selectInitialProjectWindow']),
    ...mapActions(['getProjects']),
    clickProj(name, index){
      this.pickedProj = index
      this.selectProj(name)
    },
    clickProjectOption(item, e){
      this.$emit('openProjectOptionModal', item, e.clientX, e.clientY)
    }
  },
  components:{
    draggable: VueDraggableNext
  }
}
</script>

<style scoped>
.zidx {
  position: fixed;
  top: 0;
  left: 0;
  bottom: 0;
  right: 0;
  z-index: 10;
}

.zidx > main {
  z-index: 100;
  display: block;
  margin-top: 2rem;
  margin-left: 15rem;
  width: 220px;
  background-color: #1A1A1A;
  border-radius: 0.5rem;
  border: none;
  box-shadow:0 2px 3px 0 rgba(34,36,38,0.15);
  animation: fadeInUp 0.3s ease backwards;
}

.modalframe {
  position: relative;
  text-align: center;
}

.curProj{
  padding-left: 1rem;
  height: 3rem;
  font-size: small;
  display: flex;
  align-items: center;
  color: #9D9D9D;
}

.proj{
  cursor: pointer;
}
.projectBlock{
  height: 2.3rem;
  display: flex;
  align-items: center;
  padding-left: 1rem;
  color: #C9C9C9;
  cursor: pointer;
}

.projIcon{
  width: 10px;
  margin-right: 10px;
}

.projectBlock:hover{
  background-color: #242424;
  //opacity: 0.24;
}

.addNewProjectBlock{
  height: 3rem;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
}

.addNewProjectBlock:hover{
  background-color: #242424;
  opacity: 0.24;
}

.additionalOptionWrapper{
  position: absolute;
  right: 1rem;
  border-radius: 0.2rem;
  width: 15px;
  height: 15px;
  text-align: center;
}
.additionalOptionWrapper:hover{
  background-color: #1b1b1b;
}
@keyframes fadeInUp {
  0% {
    transform: translate(0px, 7px);
    opacity: 0;
  }
  100% {
    transform: translate(0px, 0);
    opacity: 1;
  }
}
</style>