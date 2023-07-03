import api from '@/api/login.js'
import { ipcRenderer } from "electron"

export default ({
  namespaced: true,
  state: {
    userName:'seunghun',
    isLogin: false,
    isAdmin: true,
  },
  mutations: {
    setLogin(state, value) {
      state.isLogin = value
    }
  },
  actions: {
    async tryLogin(context) {
      const data = await api.login()
      if(data){
        context.commit("setLogin", true)
        ipcRenderer.send("close-app-login-modal")
        return data
      }
      else{
        console.log("login fail")
        return false
      }
    },
  },
  getters: {
    isLogin1 : state => state.isLogin
  },
  modules: {
  },
})
