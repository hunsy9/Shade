import { createStore } from 'vuex'
import createPersistedState from 'vuex-persistedstate';
import login from '@/store/login.js'
import token from '@/store/token.js'
import contributorInfo from '@/store/contributorInfo.js'
import organizationInfo from '@/store/organizationInformation.js'
import inOrganization from '@/store/inOrganization.js'
import terminal from '@/store/terminal.js'

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
    inOrganization,
    terminal
  },
  plugins: [
    createPersistedState({
        paths: ["token", "inOrganization", "login", "organizationInfo","contributorInfo","terminal"],
      },
    ),
  ],
})
