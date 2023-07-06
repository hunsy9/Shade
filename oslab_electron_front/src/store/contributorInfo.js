export default ({
  namespaced: true,

  state: {
    admin_email: {
      user_id: 0,
      contributor_email: ""
    },
    contributors: [
        {
            user_id: 0,
            contributor_email: ""
        },
    ]
  },
  getters: {
  },
  mutations: {
    setContributors(state, data){
      console.log(data.admin_email)
      console.log(data.contributors)
      state.admin_email = data.admin_email
      state.contributors = data.contributors
    },
  },
  actions: {
  },
  modules: {
  },
})
