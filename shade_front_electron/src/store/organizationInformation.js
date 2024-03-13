export default ({
    namespaced: true,
  
    state: {
        organizationInfos:[
            {
                org_id: "",
                org_name: ""
            },
        ]
    },
    getters: {
    },
    mutations: {
        setOrgInfo(state, info){
            state.organizationInfos = info
        }
    },
    actions: {
    },
    modules: {
    },
})