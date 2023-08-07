<template>
  <div class="zidx" @click="this.$emit('closeModalLogOut')">
    <main>
      <div class="modalframe">
        <div class="content" @click="toMain">
          To main
        </div>
        <div class="content" @click="closeModalLogOut">
          Logout        
        </div>
      </div>
    </main>
  </div>
</template>
    
<script>
import { mapMutations } from "vuex";
import router from "@/router";
import { ipcRenderer } from 'electron';

export default {
  name: 'ModalLogOut',
  props: {
    openModalLogOut: Boolean
  },
  methods: {
    toMain(){
      router.push('/')
      ipcRenderer.send('reset-window')
      this.$emit('closeModalLogOut')
    },
    closeModalLogOut() {
      this.$emit('closeModalLogOut')
      this.setLogin(false)
      localStorage.removeItem('vuex')
      router.push('/')
      ipcRenderer.send('reset-window')
    },
    ...mapMutations('login', ['setLogin'])
  },
}
</script>
  
<style scoped>
.zidx{
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
  margin-top: 5rem;
  margin-left: auto;
  margin-right: 2rem;
  width: 150px;
  background-color: #D9D9D9;
  border-radius: 0.5rem;
  border: none;
  box-shadow: 0.2px 0.2px 4px 4px #0000002f;
  animation: fadeInUp 1s ease backwards;

}
.modalframe {
  position: relative;
}
.content{
  line-height: 300%;
  text-align: center;
  height: 3rem;
  cursor: pointer;
}
.content:hover{
  background-color: #727272aa;
}
@keyframes fadeInUp{
  0%{transform:translate(0px, 7px); opacity: 0;}
  100%{transform:translate(0px, 0); opacity: 1;}
}
</style>