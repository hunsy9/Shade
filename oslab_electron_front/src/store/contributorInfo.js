export default ({
  namespaced: true,

  state: {
    admin_email: {
      user_id: null,
      contributor_email: null
    },
    contributors: [
        {
            user_id: null,
            contributor_email: null
        },
    ]
  },
  getters: {
  },
  mutations: {
    setContributors(state, data){
      if(!data) {
        console.log(data)
        state.admin_email.contributor_email = null
        for(var i in state.contributors){
          i.contributor_email = null
        }
      } else {
        state.admin_email = data.admin_email
        state.contributors = data.contributors
      }
    },
  },
  actions: {
  },
  modules: {
  },
})
