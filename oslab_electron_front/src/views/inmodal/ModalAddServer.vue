<template>
  <div class="background">
    <div class="top">Add New Server</div>
    <div>
      <div class="servername">
        <span> Custom Server Name </span>
        <input type="text" v-model="server.server_name"/>
      </div>
      <div class="servername">
        <span> Custom Server Desc </span>
        <input type="text" v-model="server.server_desc"/>
      </div>
      <div class="servername">
        <span> User Name </span>
        <input type="text" v-model="server.username"/>
      </div>
      <div class="servername">
        <span> Host </span>
        <input type="text" v-model="server.host"/>
      </div>
      <div class="servername">
        <span> Port </span>
        <input type="number" v-model="server.port"/>
      </div>
      <div class="servername">
        <span> Password </span>
        <input type="password" v-model="server.password"/>
      </div>
    </div>
    <button @click="closeAddServer" class="cancel">Cancel</button>
    <button @click="addServer(server)">Save</button>
  </div>
</template>

<script>
import { ipcRenderer } from 'electron'
import { createNamespacedHelpers } from 'vuex'
const { mapActions } = createNamespacedHelpers('inOrganization')

export default {
  name: "ModalAddServer",
  data() {
    return {
      server: {
        server_name: "",
        server_desc: "",
        username: "",
        host: "",
        port: 0,
        password: "",
      },
    };
  },
  methods: {
    ...mapActions(['addNewServer', 'refetchNewServer']),
    async addServer(server){
      const res = await this.addNewServer(server)
      if(res){
        await this.refetchNewServer()
      }
      ipcRenderer.send('close-add-server-modal')
      server.server_name = ""
      server.server_desc = ""
      server.username = ""
      server.host = ""
      server.port = null
      server.password = ""
    },
    closeAddServer(){
      ipcRenderer.send('close-add-server-modal')
    }
  },
}
</script>

<style scoped>
.background {
  width: 100vw;
  height: 100vh;
  background-color: #242424;
}
.top {
  color: white;
  text-align: center;
  padding-top: 2rem;
  padding-bottom: 2rem;
}
.servername {
  float: right;
  margin-top: 0.4rem;
  margin-left: auto;
  margin-right: 4rem;
}
span {
  font-size: 0.8rem;
  padding-left: auto;
  padding-right: 1rem;
  color: white;
}
input {
  font-size: 0.9rem;
  color: white;
  padding: 0.2rem 0;
  text-align: center;
  background-color: #565656;
  width: 15rem;
  border: 1px;
}
button{
  background-color: #989898;
  border-radius: 0.2rem;
  border: none;
  box-shadow: 0 0.2px 1px 1px #0000002f;
  position: absolute;
  top: 19rem;
  left: 25rem;
  cursor: pointer;
}
.cancel{
  left: 4rem;
}
</style>
