<template>
  <div class="zidx">
    <main>
      <img src="@/assets/xbutton.png" class="xbtn" @click="$emit('closeAppLoginModal')">
      <div class="modalframe">
        <img
          class="sshdesktopicon"
          src="@/assets/right-arrow-angle-and-horizontal-down-line-code-signs.png"
        >
        <div class="text1">SSH Desktop</div>
        <div class="text2">Welcome</div>
        <div class="text3">Log in to SSH Desktop to continue</div>
      </div>
      <div class="inpframe">
        <div class="id_bar">
          <!-- <img src="@/assets/id.png" class="bar_img" /> -->
          <input type="email" class="inp" placeholder="Email" v-model="loginId"/>
          <span class="email_span" v-if="no_email==true">Please Enter the Email !</span>
        </div>
        <div class="pass_bar">
          <!-- <img src="@/assets/pw.png" class="bar_img" /> -->
          <input type="password" class="inp" placeholder="Password" v-model="loginPassword"/>
          <span class="pwd_span" v-if="no_pwd==true">Please Enter the Password !</span>
        </div>
      </div>
      <button class="continuebtn" @click="login(loginId, loginPassword)">Continue</button>
      <a class="signup" @click="signUp">Sign up</a>
    </main>
  </div>

</template>

<script>

import { createNamespacedHelpers } from 'vuex'
const { mapActions, mapState } = createNamespacedHelpers('login')
const { mapMutations } = createNamespacedHelpers('organizationInfo')

export default {
  name: "ModalLogin",
  data() {
    return {
      loginId: "",
      loginPassword: "",

      no_email: false,
      no_pwd: false,
    };
  },
  computed: {
    ...mapState({
      isLogin: state => state.isLogin,
    }),
  },
  methods: {
    chk_null(cmp) { // 비어있는지 체크하는 함수
      if (cmp.trim().length == 0) { return true }
      return false
    },
    setInit() {
      this.no_email = false
      this.no_pwd = false
    },
    async login(id, pw){
      this.setInit()
      if ((this.no_email = this.chk_null(this.loginId))) { return }      // email 비어있으면 종료
      else { this.no_email = false }
      if ((this.no_pwd = this.chk_null(this.loginPassword))) { return }          // pwd 비어있으면 종료
      else { this.no_pwd = false }

      const data = await this.tryLogin(id, pw)
      if(data){
        this.setOrgInfo(data)
      }
      this.loginId = null
      this.loginPassword = null
      this.$emit("closeAppLoginModal")
    },
    signUp(){
      this.$emit("closeAppLoginModal")
      this.$emit("openAppSignUpModal")
    },
    ...mapActions(['tryLogin']),
    ...mapMutations(['setOrgInfo'])
  },
};
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
  width: 400px;
  height: 500px;
  background-color: white;
  border-radius: 0.5rem;
  border: none;
  box-shadow: 0.2px 0.2px 4px 4px #0000002f;
}
.modalframe {
  margin-top: 2rem;
  padding-top: 1.7rem;
  position: relative;
}
.xbtn {
  position: relative;
  top: 0.7rem;
  left: 1rem;
  width: 0.7rem;
  height: 0.7rem;
}
.sshdesktopicon {
  width: 4rem;
  height: 4rem;
  background-color: #d9d9d9;
  border-radius: 0.5rem;
  display: block;
  margin-left: auto;
  margin-right: auto;
}
.text1 {
  text-align: center;
  color: black;
  font-weight: 500;
  display: block;
  margin-top: 1rem;
  margin-left: auto;
  margin-right: auto;
}
.text2 {
  text-align: center;
  color: black;
  font-weight: 500;
  font-size: 1.5rem;
  display: block;
  margin-top: 2rem;
  margin-left: auto;
  margin-right: auto;
}
.text3 {
  font-size: 0.7rem;
  text-align: center;
  color: #747474;
  display: block;
  margin-top: 0.3rem;
  margin-left: auto;
  margin-right: auto;
}
.inpframe{
  display: block;
  margin-top: 1.5rem;
  margin-left: auto;
  margin-right: auto;
}
.id_bar{
  width: 20rem;
  height: 2.74rem;
  margin-top: 0.5rem;
  margin-left: auto;
  margin-right: auto;
  border: solid 0.2rem #b8a5c3;
  border-radius: 0.4rem;
}
.pass_bar{
  width: 20rem;
  height: 2.74rem;
  margin-top: 0.5rem;
  margin-left: auto;
  margin-right: auto;
  border: solid 0.2rem #b8a5c3;
  border-radius: 0.4rem;
}
.bar_img{
  margin: 0.3rem 0.5rem;
  width: 1.4rem;
  height: 1.4rem;
}
.inp{
  margin-top: 0.3rem;
  margin-bottom: 0.1rem;
  margin-left: 1.5rem;
  position: absolute;
  width: 16rem;
  height: 2rem;
  border: none;
  outline: none;
}
.continuebtn {
  font-size: 1rem;
  position: relative;
  left: 50%;
  transform: translateX(-50%); 
  color: black;
  margin-top: 0.7rem;
  margin-left: auto;
  margin-right: auto;
  background-color: #b8a5c3;
  padding: 1rem 8.3rem;
  border-radius: 0.2rem;
  border: none;
  cursor: pointer;
}
.signup{
  font-size: 0.7rem;
  position: relative;
  left: 0.1.5rem;
  display: block;
  margin-top: 1.3rem;
  margin-left: auto;
  margin-right: auto;
  text-align: center;
  color: #747474;
  text-decoration: underline;
}
a{
  cursor: pointer;
}
.email_span {
  position: inherit;
  display: block;
  margin-top: 2.9rem;
  margin-left: 0.25rem;
  font-size: 9px;
  color: tomato;
  font-weight: 600;
}
.pwd_span {
  position: inherit;
  display: block;
  margin-top: 2.9rem;
  margin-left: 0.25rem;
  font-size: 9px;
  color: tomato;
  font-weight: 600;
}
</style>
