import api from '@/api/login.js'
// import { ipcRenderer } from "electron"

export default ({
    namespaced: true,
    state: {
      mailKey: '',
    },
    mutations: {
      setMailKey(state, value) {
        state.mailKey = value
      }
    },
    actions: {
      async trySendMail(context, email) {
        const data = await api.sendMail(email)

        if (data) {
            context.commit("setMailKey", data)
            console.log(data)
            return data
        }
        else {
            console.log('fail SendMail')
            return false
        }
      },
      async tryVerify(context, info) {
        const data = await api.checkcode(info)
        console.log("tryveri: " + data)
        if (data) {
          console.log(data)
          return data
        }
        else {
          console.log('fail CodeVerify')
          return false
        }
      },
    },
    getters: {
      getMailKey(state) {
        return state.mailKey
      }
    },
    modules: {
    },
  })
  
  
  
  
  