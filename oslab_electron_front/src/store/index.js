import { createStore } from 'vuex'
import createPersistedState from 'vuex-persistedstate';
import login from '@/store/login.js'
import token from '@/store/token.js'
import contributorInfo from '@/store/contributorInfo.js'
import organizationInfo from '@/store/organizationinfo.js'
import inOrganization from '@/store/inOrganization.js'



export default createStore({
  state: {
  },
  getters: {
  },
  mutations: {
  },
  actions: {
  },
  modules: {
    login,
    token,
    contributorInfo,
    organizationInfo,
    inOrganization
  },
  plugins: [
    createPersistedState({
        paths: ["token", "inOrganization"],
      },
    ),
  ],
})
