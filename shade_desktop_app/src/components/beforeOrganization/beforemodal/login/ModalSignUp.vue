<template>
  <div class="zidx">
    <main>
      <img src="@/assets/xbutton.png" class="xbtn" @click="$emit('closeAppModalSignUp')">
      <button class="signin_btn" @click="back">Sign in</button>
      <div class="modalframe">
        <img
          class="sshdesktopicon"
          src="@/assets/shadeIcon.png"
        >
        <div class="text1">Shade</div>
      </div>
      <div class="modalframe">
        <div class="inpbar_m">
          <input class="nameinp" placeholder="Enter Your Name" v-model="name_">
          <span class="name_span" v-if="no_name==true">Please Enter the Name !</span>
        </div>
        <div class="inpbar_m" v-if="click_verify==false">
          <input type="email" class="emailinp" placeholder="Enter Your Email" v-model="email_">
          <button class="verifybtn" @click="verifing">Verify</button>
          <span class="email_span" v-if="no_email==true">Plese Enter the E-mail !</span>
          <span class="email_span" v-if="dup_email==true">This is a duplicate email</span>
        </div>
        <div class="inpbar_s" v-if="click_verify==true&&pass_code==false">
          <input type="text" class="codeinp" placeholder="Enter the Code" v-model="code_" >
          <input type="text" class="timer" v-model="restime" disabled v-if="pass_code==false">
          <button class="okbtn" @click="code_chk" v-if="pass_code==false">OK !</button>
          <button class="resendbtn" @click="resend" v-if="pass_code==false">Resend</button>
          <span class="email_span" v-if="no_veri_code==true">Plese Enter the Code !</span>
          <span class="email_span" v-if="invalid_code==true">Invalid Code !</span>
        </div>
        <div class="inpbar_m" v-if="pass_code==true">
          <input type="email" class="emailinp" v-model="email_" disabled>
        </div>
        <div class="inpbar_l">
          <input type="password" class="passinp" placeholder="Enter Your Password" v-model="pwd_">
          <span class="pwd_span" v-if="no_pwd==true">Please Enter the Password !</span>
        </div>
        <div class="inpbar_l">
          <input type="password" class="passinp" placeholder="Check Your Password " v-model="pwd_chk_">
          <span class="pwd_chk_span" v-if="no_pwd_chk==true">Please Enter the Password !</span>
          <span class="pwd_chk_span" v-if="no_same_pwd==true">Passwords are Different !</span>
        </div>
      </div>
      <button class="signupbtn" @click="signUp">Sign Up!</button>
      <span class="signup_span" v-if="signup_msg==true">Please Verify the E-mail !</span>
    </main>
  </div>
</template>

<script>

import { mapActions } from "vuex";
import { mapGetters } from "vuex";

export default {
  name: "ModalSignUp",
  data() {
    return {
      name_: "",
      email_: "",
      pwd_: "",
      pwd_chk_: "",
      code_: "",

      no_name: false,
      no_email: false,
      no_pwd: false,
      no_pwd_chk: false,
      no_same_pwd: false,
      no_veri_code: false,
      invalid_code: false,
      pass_code: false,
      
      chk_email: true,
      dup_email: false,
      veri_email: false,
      click_verify: false,
      signup_msg: false,

      timer: null,
      counter: 180,
      restime: '3:00',
    };
  },
  methods: {
    chk_null(cmp) { // 비어있는지 체크하는 함수
      if (cmp.trim().length == 0) { return true }
      return false
    },
    setInit() {
      this.no_name = false
      this.no_email = false
      this.no_pwd = false
      this.no_pwd_chk = false
    },
    async signUp() { // sign up 버튼
      this.setInit()
      if ((this.no_name = this.chk_null(this.name_))) { return }        // name 비어있으면 종료
      else { this.no_name = false }
      if ((this.no_email = this.chk_null(this.email_))) { return }      // email 비어있으면 종료
      else { this.no_email = false }
      if ((this.no_pwd = this.chk_null(this.pwd_))) { return }          // pwd 비어있으면 종료
      else { this.no_pwd = false }
      if ((this.no_pwd_chk = this.chk_null(this.pwd_chk_))) { return }  // pwd check 비어있으면 종료
      else { this.no_pwd_chk = false }

      this.name_ = this.name_.trim()
      this.email_ = this.email_.trim()
      this.pwd_ = this.pwd_.trim()
      this.pwd_chk_ = this.pwd_chk_.trim()

      if (this.pwd_ != this.pwd_chk_) {                                 // pwd 같은지 체크
        this.no_same_pwd = true
        return false
      }
      else { this.no_same_pwd = false }

      if (this.pass_code == false) {   // email verify, code check 안했으면 종료
        this.signup_msg = true
        return
      }
      else { this.signup_msg = false }

      
      const param = {
        user_email: this.email_,
        user_password: this.pwd_,
        user_name: this.name_
      }
      const data = await this.trySignup(param)

      if (data == false) { return }
      this.$emit("closeAppModalSignUp")
    },
    back() { // 뒤로가기 sign in 버튼
      this.$emit("closeAppModalSignUp")
      this.$emit("openModalLogin")
    },
    async verifing() {
      if ((this.no_email = this.chk_null(this.email_))) { return }      // email 비어있으면 종료
      else {
        this.no_name = false
        this.no_email = false
        this.no_pwd = false
        this.no_pwd_chk = false
      }

      this.email_ = this.email_.trim()
      const dup_data = await this.tryDup(this.email_)
      if (dup_data) { // 중복이면 종료
        this.dup_email = true
        return
      } 

      
      this.click_verify = true
      const mail_data = await this.trySendMail(this.email_)
      if (!mail_data) {
        //메일 전송 실패
        return
      }
      this.mounted()
      
      this.signup_msg = false
    },
    async code_chk() {
      if ((this.no_veri_code = this.chk_null(this.code_))) { return }
      else {
        this.no_name = false
        this.no_pwd = false
        this.no_pwd_chk = false
        this.no_veri_code = false
      }
      // 코드 맞는지 체크
      // 맞으면 this.invalid_code = false, this.pass_code = true, this.signup_msg = false,
      // 틀리면 this.invalid_code = true,
      this.email_ = this.email_.trim()
      const param = {
        mailKey: this.getMailKey(),
        code: this.code_,
      }
      // console.log(param) ok
      const code_data = await this.tryVerify(param)
      if (!code_data) {
        //코드 다름
        this.invalid_code = true
        return
      }

      this.timerStop()

      this.signup_msg = false
      this.invalid_code = false
      this.pass_code = true
    },
    async resend() { // code resend
      const mail_data = await this.trySendMail(this.email_)
      if (!mail_data) {
        //메일 전송 실패
        return
      }
      this.mounted()
    },
    mounted() {
      if(this.timer != null){
        this.timerStop(this.timer)
        this.timer = null
      }
      this.timer = this.timerStart()
    },
    timerStart() {
      this.restime = '3:00'
      this.counter = 180;
      var interval = setInterval(() => {
        this.counter--; //1초씩 감소
        this.restime = this.prettyTime();
        if (this.counter <= 0) this.timerStop(interval);
      }, 1000);
      return interval;
    },
    timerStop(id) {
      clearInterval(id);
      this.counter = 0;
    },
    prettyTime() {
      // 시간 형식으로 변환 리턴
      let time = this.counter / 60;
      let minutes = parseInt(time);
      let secondes = Math.round((time - minutes) * 60);
      return (
        minutes.toString() +
        ":" +
        secondes.toString().padStart(2, "0")
      );
    },
    ...mapActions('duplicate', ['tryDup']),
    ...mapActions('signup' , ['trySignup']),
    ...mapActions('emailverify' , ['trySendMail', 'tryVerify']),
    ...mapGetters('emailverify' , ['getMailKey']),
  }
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
  margin-top: 2.5rem;
  box-shadow: 0.2px 0.2px 4px 4px #0000002f;
  animation: fade-in-down 0.5s;
}
.modalframe {
  margin-top: 2rem;
  padding-top: 0rem;
  position: relative;
  animation: fade-in-down 0.5s;
}
.xbtn {
  position: relative;
  top: 0.68rem;
  left: 1rem;
  width: 0.7rem;
  height: 0.7rem;
}
.signin_btn {
  position: relative;
  top: 0.7rem;
  left: 20rem;
  background-color: white;
  border-radius: 1.5rem;
  border-color: #454545;
  cursor: pointer;
}
.inpbar_s {
  width: 9.6rem;
  height: 2.35rem;
  margin-top: 0.5rem;
  margin-left: 2.3rem;
  margin-right: auto;
  border: solid 0.15rem #000000;
  border-radius: 0.4rem;
}
.inpbar_m {
  width: 14.82rem;
  height: 2.35rem;
  margin-top: 0.5rem;
  margin-left: 2.3rem;
  margin-right: auto;
  border: solid 0.15rem #000000;
  border-radius: 0.4rem;
}
.inpbar_l {
  width: 20rem;
  height: 2.35rem;
  margin-top: 0.5rem;
  margin-left: auto;
  margin-right: auto;
  border: solid 0.15rem #000000;
  border-radius: 0.4rem;
}
input {
  border: none;
  height: auto;
  width: auto;
  outline: none;
}
.sshdesktopicon {
  width: 4rem;
  height: 4rem;
  /*background-color: #d9d9d9;*/
  border-radius: 0.5rem;
  display: block;
  margin-top: 0rem;
  margin-left: auto;
  margin-right: auto;
}
.text1 {
  text-align: center;
  color: black;
  font-weight: 500;
  display: block;
  margin-top: 1rem;
  margin-bottom: 2rem;
  margin-left: auto;
  margin-right: auto;
 }
span{
  position: relative;
  top: -0.3rem;
}
.nameinp{
  position: relative;
  margin-top: 0.12rem;
  margin-left: 2rem;
  width: 85%;
  height: 2rem;
  font-size: 0.7rem;
}
.emailinp{ 
  position: relative;
  margin-top: 0.12rem;
  margin-left: 2rem;
  width: 84%;
  height: 2rem;
  font-size: 0.7rem;
}
.codeinp{ 
  position: relative;
  margin-top: 0.12rem;
  margin-left: 2rem;
  width: 50%;
  height: 2rem;
  font-size: 0.7rem;
}
.verifybtn {
  position: absolute;
  color: #ffffff;
  top: 3.33rem;
  width: 19%;
  font-size: 0.7rem;
  margin-left: 0.73rem;
  padding-top: 0.9rem;
  padding-bottom: 0.9rem;
  background-color: #000000;
  border-radius: 0.2rem;
  border: none;
  cursor: pointer;
}
.timer {
  position:relative;
  background-color: white;
  width: 1.6rem;
  height: 1rem;
  left: 0.5rem;
  top: 0rem;
  font-size: 0.7rem;
  color:gray;
}
.okbtn {
  position: absolute;
  color: #454545;
  top: 3.33rem;
  left: 12rem;
  width: 19%;
  font-size: 0.7rem;
  margin-left: 0.73rem;
  padding-top: 0.9rem;
  padding-bottom: 0.9rem;
  background-color: #000000;
  border-radius: 0.2rem;
  border: none;
  cursor: pointer;
}
.resendbtn {
  position: absolute;
  color: #454545;
  top: 3.33rem;
  left: 11.91rem;
  width: 19%;
  font-size: 0.7rem;
  margin-left: 6rem;
  padding-top: 0.9rem;
  padding-bottom: 0.9rem;
  background-color: #000000;
  border-radius: 0.2rem;
  border: none;
  cursor: pointer;
}
.passinp{
  position: relative;
  margin-top: 0.12rem;
  margin-left: 2rem;
  width: 88%;
  height: 2rem;
  font-size: 0.7rem;
}
.signupbtn{
  height: 8%;
  width: 81.5%;
  font-size: 1rem;
  color: white;
  margin-top: 2rem;
  display: block;
  margin-left: auto;
  margin-right: auto;
  background-color: #000000;
  border-radius: 0.5rem;
  border: none;
  cursor: pointer;
}
.name_span {
  position: inherit;
  display: block;
  margin-top: 0.25rem;
  margin-left: 0.25rem;
  font-size: 9px;
  color: tomato;
  font-weight: 600;
}
.email_span {
  position: inherit;
  display: block;
  margin-top: 0.25rem;
  margin-left: 0.25rem;
  font-size: 9px;
  color: tomato;
  font-weight: 600;
}
.pwd_span {
  position: inherit;
  display: block;
  margin-top: 0.25rem;
  margin-left: 0.25rem;
  font-size: 9px;
  color: tomato;
  font-weight: 600;
}
.pwd_chk_span {
  position: inherit;
  display: block;
  margin-top: 0.25rem;
  margin-left: 0.25rem;
  font-size: 9px;
  color: tomato;
  font-weight: 600;
}
.signup_span {
  position: inherit;
  display: block;
  margin-left: 9rem;
  font-size: 9px;
  color: tomato;
  font-weight: 600;
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
</style>
