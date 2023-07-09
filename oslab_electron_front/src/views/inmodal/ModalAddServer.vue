<template>
  <div class="background">
    <div class="top">
      Add New Server
    </div>
    <div class="servername">
      <span>
        Custom Server Name
      </span>
      <input type="text">
    </div>
    <div class="servername">
      <span>
        Public IP
      </span>
      <input type="text">
    </div>
    <div class="servername">
      <span>
        Private IP
      </span>
      <input type="text">
    </div>
    <div class="servername">
      <span>
        SSH Key
      </span>
      <input type="file">
    </div>
    <div class="servername">
      <span>
        VPN Host
      </span>
      <input type="text">
    </div>
    <div class="servername">
      <span>
        VPN Key
      </span>
      <input type="file">
    </div>
  </div>
</template>

<script>
export default {
  name: 'ModalAddServer',
  data() {
    return {
      addServerInfo: {
        customServerName: '',
        publicIp: '',
        privateIp: '',
        sshKey: '파일?',
        vpnHost: '',
        vpnKey: '',
      }
    }
  }
}
</script>

<style scoped>
.background{
  width: 100vw;
  height: 100vh;
  background-color: #242424;
}
.top{
  color: white;
  text-align : center;
  padding-top: 2rem;
  padding-bottom: 2rem;
}
.servername{
  margin-top: 0.2rem;
}
span{
  padding-right: 2rem;
  color: white;
}
input{
  color: white;
  padding: 0.1rem 0;
  text-align: center;
  background-color: #565656;
  width: 45%;
  height: 1.4rem;
  border: 1px;
}
</style>