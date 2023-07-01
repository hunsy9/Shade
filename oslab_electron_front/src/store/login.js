import api from '@/api/login.js'
import { ipcRenderer } from "electron"
import organizationInfo from './organizationinfo'

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
        context.commit('organizationInfo/setOrgInfo', data)
        context.commit('setLogin', true)
        console.log(data)
        ipcRenderer.send("close-app-login-modal")
      }
      else{
        console.log("login fail")
      }
    },
  },
  getters: {
    isLogin1 : state => state.isLogin
  },
  modules: {
    organizationInfo
  },
})
