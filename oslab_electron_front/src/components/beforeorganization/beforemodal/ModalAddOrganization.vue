<template>
  <div class="modalframe">
    <div class="header">Add Your Organization</div>
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
      <button class="cancelbtn" @click="closeNewOrganModal"> Cancel</button>
      <button class="authbtn" @click="auth" v-if="is_auth==false"> Authentication</button>
      <button class="save" @click="save" v-if="is_auth==true"> Save to your Desktop</button>
    </div>
    <div class="zidx">
      <main>
        <div class="modalframe">
          <div class="header">Add Your Organization</div>
          <div class="emailbar">
            <span class="text1">Organization Email</span>
            <input class="inpmail" type="text">
            <button class="sendbtn" @click="codeSend()">Code Send</button>
          </div>
          <div class="codebar" v-if="show">
            <span class="text2">Enter the code</span>
            <input class="inpcode" type="text">
            <button class="authbtn" @click="save()">Authentication</button>
          </div>
          <div class="timeveri">

          </div>

        </div>
      </main>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ModalAddOrganization',
  data() {
    return {
      organizationEmail: "",
      verificationCode: "",
      is_auth: false,
      show: false
    }
  },
  methods: {
    save() {
      this.is_auth = false
    },
    auth() {
      this.is_auth = true
    },
    closeNewOrganModal() {
      this.is_auth = false
      this.$emit('closeModalAddOrganization')
    },
  },
  async codeSend() {
    this.show = !this.show
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
}

.zidx > main {
  display: block;
  margin: 0 auto;
  width: 625px;
  height: 375px;
  background-color: #242424;
  border-radius: 0.5rem;
  border: none;
  box-shadow: 0.2px 0.2px 4px 4px #0000002f;
}

.modalframe {
  width: 100%;
  height: 100%;
}

.header {
  margin-top: 2rem;
  margin-left: auto;
  margin-right: auto;
  color: white;
  text-align: center;
}

.emailbar {
  margin-top: 5rem;
  margin-left: auto;
  margin-right: auto;
  width: 100%;
}

.text1 {
  padding-left: 5rem;
  /* margin-left: calc(10rem); */
  color: white;
}

.inpmail {
  color: white;
  position: relative;
  left: 0.5rem;
  text-align: center;
  background-color: #565656;
  width: 45%;
  height: 1.4rem;
  border: 1px;
}

.sendbtn {
  color: white;
  background-color: #989898cf;
  position: relative;
  left: 0.7rem;
  width: 6rem;
  height: 1.7rem;
  border-radius: 0.2rem;
  border: none;
  box-shadow: 0 0.2px 1px 1px #0000002f;
  cursor: pointer;
}

.codebar {
  margin-top: 0.3rem;
  margin-left: auto;
  margin-right: auto;
}

.text2 {
  margin-left: 6.96rem;
  color: white;
}

.inpcode {
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
  cursor: pointer;
}

.authbtn {
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
  cursor: pointer;
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
  cursor: pointer;
}

.timeveri {
  margin-top: 0.3rem;
}
</style>
