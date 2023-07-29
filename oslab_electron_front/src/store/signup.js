import api from '@/api/login.js'
// import { ipcRenderer } from "electron"

export default ({
    namespaced: true,
    state: {
      
    },
    mutations: {
      
    },
    actions: {
      async trySignup(context, info) {

        const data = await api.signUp(info)
        return data
        
      },
    },
    getters: {
    },
    modules: {
    },
  })
  
  
  
  
  