import api from '@/api/login.js'
import { ipcRenderer } from "electron"

export default ({
  namespaced: true,
  state: {
    userName:'',
    isLogin: false,
    isAdmin: true,
    userID: 0,
    userEmail: '',
  },
  mutations: {
    setLogin(state, value) {
      state.isLogin = value
    },
    setUserID(state, value) {
      state.userID = value
    },
    setUserEmail(state, value) {
      state.userEmail = value
    },
    setUserName(state, value) {
      state.userName = value
    }
  },
  actions: {
    async tryLogin(context, info) {
      const data = await api.login(info)
      if(data){
        
        context.commit("setLogin", true)
        context.commit("setUserName", data.user_name)
        context.commit("setUserID", data.user_id)
        context.commit("setUserEmail", data.user_email)
        ipcRenderer.send("close-app-login-modal")
        return data
      }
      else{
        console.log("login fail")
        return false
      }
    },
    async tryGetOrg(context, user_id) {
      console.log("ok")
      console.log(user_id)
      const data = await api.getOrg(user_id)
      if (data) {
        return data
      }
      else {
        console.log("getOrg fail")
        return false
      }
    }
  },
  getters: {
    isLogin1 : state => state.isLogin,
    getUserID(state) {
      console.log(state.userID)
      return state.userID
    },
    getUserEmail(state) {
      console.log(state.userID)
      return state.userEmail
    },
  },
  modules: {
  },
})
