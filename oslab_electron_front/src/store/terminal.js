import api from '@/api/login.js'

export default ({
    namespaced: true,
  
    state: {
        userId: 1,
        runningServerId: 0,
        roomId: "",
    },
    getters: {
    },
    mutations: {
        setRommId(state, roomId, serverId){
            state.roomId = roomId
            state.runningServerId = serverId
        }
    },
    actions: {
        async connectTerminal(context, serverId){
            const data = await api.getRoomId(serverId)
            if(data){
                context.commit('setRommId', data)
                context.commit('inOrganization/selectTerminal', data, { root: true })
            }
            else{
                console.log("Contritubors fail in store")
            }
        }
    },
    modules: {
    },
})