import api from '@/api/login.js'
import inOrganization from "@/store/inOrganization";

export default ({
    namespaced: true,
  
    state: {
        user_id: 1,
        server_id: 0,
        room_id: "",
        threadKey: "",
        webSocketKey: "",
    },
    getters: {
    },
    mutations: {
        setRommId(state, room_id){
            state.room_id = room_id
        },
        setServerId(state, server_id){
            state.server_id = server_id
        },
        setThreadKey(state, threadKey){
            state.threadKey = threadKey
        },
        setWSKey(state, webSocketKey){
            state.webSocketKey = webSocketKey
        },

    },
    actions: {
        async connectTerminal(context, server_id){
            context.commit('setServerId', server_id)

            if(context.state.server_id == server_id){
                const room_id = await api.getRoomId(server_id, context.state.user_id)
                if(room_id){
                    context.commit('setRommId', room_id)

                    if(context.state.room_id == room_id){
                        console.log(room_id)
                        const postData = {
                            user_id: context.state.user_id,
                            server_id: context.state.server_id,
                            room_id: context.state.room_id,
                        }
                        const d = await api.postSSH(postData)
                        const key = JSON.parse(d)
                        context.commit('setThreadKey', key.threadKey)
                        context.commit('setWSKey', key.webSocketKey)
                        if(key){
                            const keyBundle = {
                                "threadKey": key.threadKey,
                                "webSocketKey": key.webSocketKey,
                                "org_id" : inOrganization.state.organId,
                                "server_id" : server_id,
                                "keyExistence" : key.keyExistence
                            }
                            const server = "http://152.67.213.248:8081/"
                            fetch(`${server}api/request/connect`, {
                                method: 'POST',
                                headers: {
                                    'Content-Type': 'application/json',
                                },
                                body: JSON.stringify(keyBundle)
                            })
                            // 터미널 모드로 설정
                            context.commit('inOrganization/selectTerminal', null, { root: true })
                        }else{
                            console.log("키 가져오기 실패")
                        }
                    }
                }
                else{
                    console.log("Contritubors fail in store")
                }
            }
        }
    },
    modules: {
    },
})