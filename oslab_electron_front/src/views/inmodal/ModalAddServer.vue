<template>
  <div>
  
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