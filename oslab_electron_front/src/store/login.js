import api from '@/api/login.js'
import { ipcRenderer } from "electron";

export default ({
  namespaced: true,

  state: {
    userName:'seunghun',
    isLogin: true,
    isAdmin: true,
  },
  getters: {
  },
  mutations: {
    setLogin(state, isLogin){
      state.isLogin = isLogin;
    }
  },
  actions: {
    async tryLogin(context, id, pw) {
      const data = await api.login(id, pw);
      context.commit('setLogin', data.isLogin);
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
