<template>
  <div class="modalframe">
    <div class="header">Add Your Organization </div>
    <div class="emailbar">
      <span class="text1">Organization Email</span>
      <input class="inpmail" type="text">
    </div>
    <div class="codebar" v-if="is_auth==true">
      <span class="text2">Enter the code</span>
      <input class="inpcode" type="text">
    </div>
    <div class="dumydiv" v-if="is_auth==false">
      <input class="dumyinp" type="text">
    </div>
    <div class="timeveri">

    </div>
    <div class="btn">
      <button class="cancelbtn" @click="closeNewOrganModal"> Cancel </button>
      <button class="authbtn" @click="auth" v-if="is_auth==false"> Authentication </button>
      <button class="save" @click="save" v-if="is_auth==true"> Save to your Desktop </button>
    </div>
  </div>
</template>

<script>
import { ipcRenderer } from "electron";

export default {  
  name: 'ModalAddOrganization',
  data() {
    return {
      organizationEmail: "",
      verificationCode: "",
      is_auth: false,
    }
  },
  methods: {
    save(){
      this.is_auth = false
      ipcRenderer.send('close-add-organ-modal')
    },
    auth() {
      this.is_auth = true
    },
    closeNewOrganModal() {
      this.is_auth = false
      ipcRenderer.send('close-add-organ-modal')
    },
  }
}
</script>

<style scoped>
.modalframe{
  position: absolute;
  width: 100%;
  height: 100%;
  background-color: #242424;
}
.header{
  margin-top: 2rem;
  margin-left: auto;
  margin-right: auto;
  color: white;
  text-align: center;
}
.emailbar{
  margin-top: 5rem;
  margin-left: auto;
  margin-right: auto;
  width: 100%;
}
.text1{
  padding-left: 5rem;
  /* margin-left: calc(10rem); */
  color: white;
}
.inpmail{
  color: white;
  position: relative;
  left: 0.5rem;
  text-align: center;
  background-color: #565656;
  width: 45%;
  height: 1.4rem;
  border: 1px;
}
.sendbtn{
  color: white;
  background-color: #989898cf;
  position: relative;
  left: 0.7rem;
  width: 6rem;
  height: 1.7rem;
  border-radius: 0.2rem;
  border: none;
  box-shadow: 0 0.2px 1px 1px #0000002f;
  cursor:pointer;
}
.codebar{
  margin-top: 0.3rem;
  margin-left: auto;
  margin-right: auto;
}
.text2{
  margin-left: 6.96rem;
  color: white;
}
.inpcode{
  color: white;
  position: relative;
  left: 0.5rem;
  text-align: center;
  background-color: #565656;
  width: 19.5%;
  height: 1.4rem;
  border: 1px;
}
.dumydiv {
  margin-top: 0.3rem;
  margin-left: auto;
  margin-right: auto;
}
.dumyinp {
  position: relative;
  left: 0.5rem;
  width: 0%;
  height: 1.4rem;
  border: 1px;
  visibility: hidden;
}
.cancelbtn {
  position: relative;
  left: 1rem;
  top: 9.3rem;
  color: white;
  background-color: #989898cf;
  width: 6rem;
  height: 1.7rem;
  border-radius: 0.2rem;
  border: none;
  box-shadow: 0 0.2px 1px 1px #0000002f;
  cursor:pointer;
}
.authbtn{
  position: relative;
  left: 26.3rem;
  top: 9.3rem;
  color: white;
  background-color: #989898cf;
  width: 6rem;
  height: 1.7rem;
  border-radius: 0.2rem;
  border: none;
  box-shadow: 0 0.2px 1px 1px #0000002f;
  cursor:pointer;
}
.save {
  position: relative;
  left: 20.3rem;
  top: 9.3rem;
  color: white;
  background-color: #989898cf;
  width: 12rem;
  height: 1.7rem;
  border-radius: 0.2rem;
  border: none;
  box-shadow: 0 0.2px 1px 1px #0000002f;
  cursor:pointer;
}
.timeveri{
  margin-top: 0.3rem;
}
</style>
