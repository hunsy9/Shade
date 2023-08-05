import api from '@/api/login.js'
// import { ipcRenderer } from "electron"

export default ({
    namespaced: true,
    state: {
      
    },
    mutations: {
      
    },
    actions: {
      async tryAddMaster(context, info) {
        const data = await api.addOrgMaster(info)
        return data
      },
      async tryAddOrgUser(context, info) {
        const data = await api.addOrgUser(info)
        return data
      },
      async tryAddOrgCont(context, info) {
        const data = await api.addOrgCont(info)
        return data
      },
    },
    getters: {
    },
    modules: {
    },
  })