<template>
  <div class="zidx">
    <main>
      <div class="modalframe">
        <div class="header">Add Your Organization </div>
        <div class="emailbar">
          <span class="text1">Organization Email</span>
          <input class="inpmail" type="text">
        </div>
        <div class="codebar" v-if="is_auth==true">
          <span class="text2" v-if="is_verify==false">Enter the code</span>
          <input class="inpcode" v-if="is_verify==false" type="text">
          <span class="loding_text" v-if="is_verify == false"> veryfing ... </span>
          <img src="@/assets/loading.gif" class="loding_img" v-if="is_verify == false">
          <button class="verybtn" v-if="is_verify==false" @click="very"> Verify </button>

          <span class="success_text" v-if="is_verify == true"> email verifed </span>
          <img src="@/assets/email_verified.png" class="success_img" v-if="is_verify == true">
          <input class="dumyinp2" v-if="is_verify == true" type="text">
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
    </main>
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
      is_verify: false,
    }
  },
  methods: {
    save(){
      this.is_auth = false
      //this.$emit("closeModalAddOrganization")
    },
    auth() {
      // need to async, api
      this.is_auth = true
    },
    closeNewOrganModal() {
      this.is_auth = false
      this.$emit("closeModalAddOrganization")
    },
    very() {
      this.is_verify = true
    }
  }
}
</script>

<style scoped>

.zidx{
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
.modalframe{
  width: 100%;
  height: 100%;
}
.header{
  margin-top: 2rem;
  margin-left: auto;
  margin-right: auto;
  padding-top: 2rem;
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
  margin-top: 0.15rem;
  margin-left: auto;
  margin-right: auto;
}
.dumyinp {
  position: relative;
  left: 0.5rem;
  width: 0%;
  height: 1.73rem;
  border: 1px;
  visibility: hidden;
}
.dumyinp2 {
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
  top: 9rem;
  color: white;
  background-color: #989898cf;
  width: 6rem;
  height: 1.4rem;
  border-radius: 0.4rem;
  border: none;
  box-shadow: 0 0.2px 1px 1px #0000002f;
  cursor:pointer;
}
.authbtn{
  position: relative;
  left: 25.3rem;
  top: 9rem;
  color: white;
  padding: 0px;
  background-color: #989898cf;
  width: 7rem;
  height: 1.4rem;
  border-radius: 0.4rem;
  border: none;
  box-shadow: 0 0.2px 1px 1px #0000002f;
  cursor:pointer;
}
.save {
  position: relative;
  left: 20.3rem;
  top: 9rem;
  color: white;
  padding: 0px;
  background-color: #989898cf;
  width: 12rem;
  height: 1.4rem;
  border-radius: 0.4rem;
  border: none;
  box-shadow: 0 0.2px 1px 1px #0000002f;
  cursor:pointer;
}
.loding_img {
  position: relative;
  left: 2rem;
  top: 0rem;
  bottom: 0rem;
  padding: 0px;
  margin: 0px;
  width: 1rem;
  height: 1rem;
}
.success_img {
  position: relative;
  left: 22.8rem;
  top: 0.1rem;
  bottom: 0rem;
  padding: 0px;
  margin: 0.2rem 0rem 0rem 0rem;
  width: 1rem;
  height: 1rem;
}
.verybtn{
  position: relative;
  left: 3rem;
  top: 0rem;
  color: white;
  padding: 0rem;
  margin: 0rem;
  background-color: #989898cf;
  width: 6rem;
  height: 1.7rem;
  border-radius: 0.2rem;
  border: none;
  box-shadow: 0 0.2px 1px 1px #0000002f;
  cursor:pointer;
}
.loding_text {
  position: relative;
  left: 4rem;
  color: white;
  font-size: 1rem;
  padding: 0rem 2rem 0rem 0rem;
  margin: 0rem;
}
.success_text {
  position: relative;
  left: 24.7rem;
  color: #3DF22D;
  font-size: 1rem;
  padding: 0rem 2rem 0rem 0rem;
  margin:02rem 0rem;
}

.timeveri{
  margin-top: 0.3rem;
}
</style>