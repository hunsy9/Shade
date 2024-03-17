import api from '@/api/login.js'
// import {makeLocalPty} from "@/localTerm";

export default ({
    namespaced: true,
    state: {
        server_id: 0,
        server_name: "",
        room_id: "",
        serverAddress:"",
        runningConnection: []
    },
    mutations: {
        setServerAddress(state,serverAddress){
          state.serverAddress = serverAddress
        },
        setServerName(state,server_name){
            state.server_name = server_name
        },
        setServerId(state, server_id){
            state.server_id = server_id
        },
        setRunningConnection(state){
            state.runningConnection.push({
                server_id: state.server_id,
                room_id: state.room_id,
                server_name: state.server_name
            })
            state.runningConnection.push({
                server_id: 325,
                room_id: state.room_id,
                server_name: "TestTab"
            })
        },
        resetRunningConnection(state){
            state.runningConnection = []
        }
    },
    actions: {
        async connectLocalTerminal(){
            // await makeLocalPty()
        },
        async connectTerminal(context, data){
            console.log(data)

            context.commit('setServerId', data.server_id)
            context.commit('setServerName', data.server_name)
            if(context.state.server_id === data.server_id){
                const user_id = context.rootState.login.userID;
                const server_id = data.server_id;
                const room_id = await api.getRoomId(data.server_id, user_id)
                const nameSpace = `/${user_id}/${server_id}/${room_id}`
                const serverAddress = `http://localhost:3001${nameSpace}`

                console.log(nameSpace)
                context.commit("setServerAddress", serverAddress)

                const detail = {
                    user_id: user_id,
                    server_id: server_id,
                    nameSpace : nameSpace
                }

                await fetch('http://localhost:8081/api/request/startPty',{
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(detail)
                })

                context.commit('inOrganization/selectTerminal', null, { root: true })
                context.commit('setRunningConnection')
            }
        }
    },
})