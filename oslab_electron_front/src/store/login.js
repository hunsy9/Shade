import api from '@/api/login.js'
import { ipcRenderer } from "electron";

export default ({
  namespaced: true,

  state: {
    isLogin: false,
    isAdmin: true,
  },
  getters: {
  },
  mutations: {
  },
  actions: {
    async tryLogin(context, id, pw) {
      const data = await api.login(id, pw)
      context.state.isLogin = data.isLogin
      if(context.state.isLogin){
        ipcRenderer.send("close-app-login-modal");
      }
      // else{
      // 계정이 틀리면
      // }
    }
  },
  modules: {
  },
})
