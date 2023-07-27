<template>
  <div class="modalframe">
    <img
      class="sshdesktopicon"
      src="@/assets/right-arrow-angle-and-horizontal-down-line-code-signs.png"
    />
    <div class="text1">SSH Desktop</div>
  </div>
  <div class="modalframe">
    <div class="inpbar">
      <span>
        Your Name
      </span>
      <input class="nameinp">
    </div>
    <div class="inpbar">
      <input type="email" class="emailinp" placeholder="      Enter Your Email">
      <button class="verifybtn">Verify</button>
    </div>
    <div class="inpbar">
      <input type="password" class="passinp" placeholder="      Enter Your Password">
    </div>
    <div class="inpbar">
      <input type="password" class="passinp" placeholder="      Check Your Password ">
    </div>
  </div>
  <button class="signupbtn">Sign Up!</button>
</template>

<script>
export default {
  name: "ModalSignUp",
};
</script>

<style scoped>
input{
  border: 2px solid #B8A5C3;
  border-radius: 4px;
  outline: none;
}
.modalframe {
  position: relative;
}
.sshdesktopicon {
  width: 4rem;
  height: 4rem;
  background-color: #d9d9d9;
  border-radius: 0.5rem;
  display: block;
  margin-top: 2rem;
  margin-left: auto;
  margin-right: auto;
}
.text1 {
  text-align: center;
  color: black;
  font-weight: 500;
  display: block;
  margin-top: 1rem;
  margin-bottom: 3rem;
  margin-left: auto;
  margin-right: auto;
}
.inpbar{
  width: 80%;
  display: block;
  margin-left: auto;
  margin-right: auto;
  margin-top: 0.5rem;
}
span{
  position: relative;
  top: -0.3rem;
}
.nameinp{
  position: relative;
  left: 14%;
  width: 60%;
  height: 2.3rem;
  font-size: 0.7rem;
}
.emailinp{ 
  position: relative;
  width: 70%;
  height: 2.3rem;
  font-size: 0.7rem;
}
.verifybtn{
  position: absolute;
  color: #454545;
  width: 19%;
  font-size: 0.7rem;
  margin-left: 1rem;
  padding-top: 0.9rem;
  padding-bottom: 0.9rem;
  background-color: #b8a5c3;
  border-radius: 0.2rem;
  border: none;
  cursor: pointer;
}
.passinp{
  position: relative;
  width: 98%;
  height: 2.3rem;
  font-size: 0.7rem;
}
.signupbtn{
  height: 8%;
  width: 80%;
  font-size: 1rem;
  color: black;
  margin-top: 2rem;
  display: block;
  margin-left: auto;
  margin-right: auto;
  background-color: #b8a5c3;
  border-radius: 0.2rem;
  border: none;
  cursor: pointer;
}
</style>
