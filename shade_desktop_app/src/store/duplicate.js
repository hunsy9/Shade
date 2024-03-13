import api from '@/api/login.js'
// import { ipcRenderer } from "electron"

export default ({
    namespaced: true,
    state: {
    },
    mutations: {
    },
    actions: {
      async tryDup(context, email) {

        const data = await api.isDup(email)
        return data
      },
    },
    getters: {
    },
    modules: {
    },
  })
  
  
  
  
  