import api from '@/api/login.js'
import { ipcRenderer } from "electron";

export default ({
  namespaced: true,

  state: {
    userName:'seunghun',
    isLogin: true,
    isAdmin: true,
  },
  mutations: {
    setLogin(state, value) {
      state.isLogin = value;
    }
  },
  actions: {
    async tryLogin(context, id, pw) {
      const data = await api.login(id, pw)
      if(data.isLogin){
        console.log(context.state.isLogin)
        context.commit('setLogin', data.isLogin)
        console.log(context.state.isLogin)
        ipcRenderer.send("close-app-login-modal")
      }
      else{
        console.log("login fail")
      }
      // else{
      // 계정이 틀리면
      // }
    },
    look(context) {
      context.commit('setLogin', '119')
      console.log(context.state.isLogin)
    }
  },
  getters: {
  },
})
