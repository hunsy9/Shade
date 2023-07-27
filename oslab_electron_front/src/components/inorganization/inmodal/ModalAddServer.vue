<template>
  <div class="modal">
    <div class="overlay"></div>
    <div class="modal-card">
      <div class="header">Add New Server</div>
      <div class="serverInfo">
        <label class="server"> Custom Server Name </label>
        <input type="text" v-model="server.server_name"/>
      </div>
      <div class="serverInfo">
        <label class="server"> Custom Server Desc </label>
        <input type="text" v-model="server.server_desc"/>
      </div>
      <div class="serverInfo">
        <label class="server"> User Name </label>
        <input type="text" v-model="server.username"/>
      </div>
      <div class="serverInfo">
        <label class="server"> Host </label>
        <input type="text" v-model="server.host"/>
      </div>
      <div class="serverInfo">
        <label class="server"> Port </label>
        <input type="number" v-model="server.port"/>
      </div>
      <div class="serverInfo">
        <label class="server"> Password </label>
        <input type="password" v-model="server.password"/>
      </div>
      <div class="fileInfo">
        <label class="server" id="fileLab"> Public Key </label>
        <label for="file" class="fileLabel">
          <div class="fileUpload">{{keyFileName}}</div>
        </label>
        <input type="file" name="file" id="file" @change="handleFileChange"/>
      </div>
      <div class="conTestLoading" v-if="isTesting">
        <span class="loadingLabel" v-if="isTesting" :class="{'fade-in-down':this.successStatus, bounce:this.failStatus}"> {{ connectingStatus }} </span>
        <LoadingSpinner v-if="isLoading" width="10px" height="10px"></LoadingSpinner>
        <img src="@/assets/testSuccess.png" :class="{'fade-in-down':this.successStatus}" width="15" height="15" v-if="successStatus === true">
        <img src="@/assets/testFail.png" :class="{bounce:this.failStatus}" width="15" height="15" v-if="failStatus === true">
      </div>
      <div class="btnWrapper">
        <button @click="$emit('closeModalAddServer')" class="cancel">Cancel</button>
        <button @click="testConnection" class="testCon" >Connection Test</button>
        <button @click="addServer(server)" class="saveBtn" :class="{testCompleted:testCompleted}">Save</button>
      </div>
    </div>
  </div>
</template>

<script>
import {ipcRenderer} from 'electron'
import {createNamespacedHelpers} from 'vuex'
import LoadingSpinner from "@/components/common/LoadingSpinner";

const {mapActions} = createNamespacedHelpers('inOrganization')

export default {
  name: "ModalAddServer",
  components: {
    LoadingSpinner
  },
  data() {
    return {
      server: {
        server_name: "",
        server_desc: "",
        username: "",
        host: "",
        port: 0,
        password: "",
        publicKey: ""
      },
      publicKey: "",
      keyFileName: "Upload",
      testCompleted: false,
      isLoading: false,
      isTesting: false,
      connectingStatus: "",
      successStatus: false,
      failStatus: false,
    };
  },
  methods: {
    ...mapActions(['addNewServer', 'refetchNewServer']),
    handleFileChange(e) {
      let file = e.target.files[0];
      this.publicKey = file;
      this.keyFileName = file.name
    },
    testConnectionInit(){
      this.connectingStatus = "connecting..."
      this.testCompleted = false
      this.failStatus = false
      this.successStatus = false
      this.isLoading = true
      this.isTesting = true
    },
    testSuccess(){
      this.testCompleted = true
      this.connectingStatus = "Success"
      this.successStatus = true
    },
    testFailed(){
      this.connectingStatus = "Failed"
      this.failStatus = true
    },
    async testConnection() {
      this.testConnectionInit()
      var formData = new FormData();

      if (this.publicKey === "") {
        formData.append("keyfile", new File([], "nFile"))
      } else {
        formData.append("keyfile", this.publicKey)
      }

      const testDto = {
        "username": this.server.username,
        "host": this.server.host,
        "port": this.server.port,
        "password": this.server.password
      }
      formData.append("server", new Blob([JSON.stringify(testDto)], {type: "application/json"}))
      let res = await fetch("http://localhost:8081/api/request/test", {
        method: "POST",
        body: formData,
      })
      if (res.status == 200) {
        this.testSuccess()
      } else {
        this.testFailed()
      }
      this.isLoading = false
    },
    async addServer(server) {
      if (this.testCompleted) {
        this.server.publicKey = this.publicKey
        const res = await this.addNewServer(server)
        if (res) {
          await this.refetchNewServer()
        }
        ipcRenderer.send('close-add-server-modal')
        server.server_name = ""
        server.server_desc = ""
        server.username = ""
        server.host = ""
        server.port = null
        server.password = ""
      } else {
        alert("you should test server before save!")
      }
    },
  },
}
</script>

<style scoped>

.server{
  font-size: 0.8rem;
  padding-right: 2rem;
  color: white;
}

#fileLab{
  margin-top: 0.25rem;
  margin-right: 0.25rem;
}

.fileLabel{
  font-size: 0.8rem;
  color: white;
}

.loadingLabel {
  padding-left: 100px;
  padding-right: 0.1rem;
  color: #DDDDDD;
  font-size: 0.8rem;
}

input {
  font-size: 0.9rem;
  color: white;
  padding: 0.2rem 0;
  text-align: center;
  background-color: #565656;
  width: 15rem;
  border: none;
  outline: none;
}

input::-webkit-inner-spin-button {
  appearance: none;
  -moz-appearance: none;
  -webkit-appearance: none;
}

button {
  background-color: #989898;
  border-radius: 0.2rem;
  border: none;
  box-shadow: 0 0.2px 1px 1px #0000002f;
  cursor: pointer;
  display: inline-block;
  font-size: 0.8rem;
  color: white;
}

.saveBtn {
  margin-right: 30px;
  background-color: #646464;
}

.testCon {
  margin-left: 200px;
  margin-right: 20px;
}

.cancel {
  margin-left: 20px;
}

.modal,
.overlay {
  width: 100%;
  height: 100%;
  position: fixed;
  left: 0;
  top: 0;
}

.modal-card {
  position: relative;
  max-width: 475px;
  min-height: 350px;
  margin: auto;
  margin-top: 150px;
  padding: 20px;
  background-color: white;
  z-index: 10;
  background-color: #242424;
  border-radius: 0.5rem;
  border: none;
  box-shadow: 0.2px 0.2px 4px 4px #0000002f;
  animation: fade-in-down 0.5s;
}

.header {
  text-align: center;
  color: white;
  margin-bottom: 30px;
  margin-top: 15px;
}

.serverInfo {
  text-align: center;
  float: right;
  margin-top: 3px;
  margin-bottom: 3px;
  margin-right: 40px;
}

.fileInfo{
  text-align: center;
  float: right;
  display: flex;
  margin-top: 3px;
  margin-bottom: 3px;
  margin-right: 40px;
}

.conTestLoading {
  text-align: right;
  float: right;
  display: flex;
  margin-top: 3px;
  margin-bottom: 3px;
  margin-right: 40px;
}

.btnWrapper {
  top: 30px;
  width: 100%;
  display: flex;
  text-align: center;
  position: relative;
}

.testCompleted {
  animation-name: testCompletedAnimation;
  animation-iteration-count: infinite;
  animation-duration: 2s;
}

@keyframes testCompletedAnimation {
  from{background-color: #8E8E8E}
  to{background-color: #2C2C2C}
}

.fade-in-down {
  animation: fade-in-down 2s ease;
  animation-fill-mode: forwards;
}
@keyframes fade-in-down {
  0% {
    opacity: 0;
    transform: translateY(-20px);
  }
  100% {
    opacity: 1;
    transform: translateY(0);
  }
}

.bounce {
  animation: bounce 2s ease infinite;
}
@keyframes bounce {
  70% { transform:translateY(0%); }
  80% { transform:translateY(-15%); }
  90% { transform:translateY(0%); }
  95% { transform:translateY(-7%); }
  97% { transform:translateY(0%); }
  99% { transform:translateY(-3%); }
  100% { transform:translateY(0); }
}

.fileUpload{
  background-color: #989898;
  border-radius: 0.2rem;
  border: none;
  box-shadow: 0 0.2px 1px 1px #0000002f;
  width: 15rem;
  margin-top: 0.1rem;
  padding-top: 0.1rem;
  padding-bottom: 0.1rem;
}
#file{
  display: none;
}
</style>
