export default ({
  namespaced: true,

  state: {
    admin_contributor: {
      user_id: null,
      contributor_email: null
    },
    contributors: [
        {
            user_id: null,
            contributor_email: null
        },
    ],
    pending_contributors: [
      {
        user_id: null,
        contributor_email: null
      },
    ],
  },
  getters: {
  },
  mutations: {
    setContributors(state, data){
      if(!data) {
        console.log(data)
        state.admin_contributor.contributor_email = null
        for(var i in state.contributors){
          i.contributor_email = null
        }
      } else {
        state.admin_contributor = data.admin_contributor
        state.contributors = data.contributors
        state.pending_contributors = data.pending_contributors
      }
    },
  },
  actions: {
  },
  modules: {
  },
})
