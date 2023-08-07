import api from '@/api/login.js'

export default ({
    namespaced: true,
  
    state: {
        server_id: 0,
        room_id: "",
        serverAddress:""
    },
    getters: {
    },
    mutations: {
        setServerAddress(state,serverAddress){
          state.serverAddress = serverAddress
        },
        setServerId(state, server_id){
            state.server_id = server_id
        },
    },
    actions: {
        async connectTerminal(context, data){
            console.log(data)

            context.commit('setServerId', data.server_id)

            if(context.state.server_id == data.server_id){
                const user_id = context.rootState.login.userID;
                const server_id = data.server_id;
                const room_id = await api.getRoomId(data.server_id, user_id)
                const nameSpace = `/${user_id}/${server_id}/${room_id}`
                const serverAddress = `http://localhost:3001${nameSpace}`

                context.commit("setServerAddress", serverAddress)

                console.log("Create RoomID: " + room_id)
                console.log("nameSpace: " + nameSpace)
                console.log("serverAddress: " + serverAddress)

                const detail = {
                    user_id: user_id,
                    server_id: server_id,
                    nameSpace : nameSpace
                }

                await fetch('http://localhost:8082/api/sshService/startPty',{
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(detail)
                })

                context.commit('inOrganization/selectTerminal', null, { root: true })
            }
        }
    },
})