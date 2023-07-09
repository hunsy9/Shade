<template>
  <button class="button" @click="openNewServerModal">
    Add New Server
  </button>
</template>
    
<script>
import { ipcRenderer } from 'electron'

export default {
  name: 'NewServerButton',
  methods: {
    openNewServerModal(){
      ipcRenderer.send('open-add-server-modal')
    }
  } 
}
</script>

<style scoped>
.button{
  position: absolute;
  top: 7%;
  right: 7%;
  color: white;
  display: block;
  margin: 0 auto;
  background-color: #989898;
  padding-left: 1.8rem;
  padding-right: 1.8rem;
  border-radius: 0.2rem;
  border: none;
  box-shadow: 0 1px 1px 0.5px #0000002f;
  cursor:pointer;
}
</style>