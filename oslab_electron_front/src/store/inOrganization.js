import api from '@/api/login.js'
import { ipcRenderer } from "electron"
import router from '@/router/index.js'

export default ({
    namespaced: true,
  
    state: {
        organId: "",
        organName: "",
        projects: [
            {
                project_id: "",
                project_name: "",
                category: {
                    develop: [
                        "",
                        ""
                    ],
                    operation: [
                        "",
                        "",
                        ""
                    ]
                },
                project_server: {
                    "2:operation:REDIS": [
                        {
                            server_id: "",
                            server_name: "",
                            server_desc: ""
                        }
                    ]
                }
            }
        ],
        org_user_privileges: [
            1
        ]
    },
    
    getters: {
    },
    mutations: {
        setOrg(state, org){
            state.organName = org.org_name;
            state.organId = org.org_id;
        },
        setProj(state, projects){
            state.projects = projects
        }
    },
    actions: {
        async getProjects(context, org){
            const data = await api.getProj()
            if(data){
              context.commit("setOrg", org)
              context.commit("setProj", data)
              router.push('/in')
              ipcRenderer.send('resize-window')
            }
            else{
              console.log("getProj fail")
            }
        }
    },
    modules: {
    },
})