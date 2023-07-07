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
                
                },
                project_server: {
                
                }
            }
        ],
        org_user_privileges: [
            1
        ],
        mode: 0,
        full: false,
        selected_proj: "",
        selected_categ_l1: "",
        selected_categ_l2: "",
    },
    getters: {
        getCategory (state){
            const a = state.projects.find(project => project.project_name === state.selected_proj)
            if(a.category){
                return a.category
            }
        },
        getSelectedCatl1(state){
            if(state.selected_categ_l1){
                state.selected_categ_l2 = ""
                return " > " + state.selected_categ_l1
            }
        },
        getSelectedCatl2(state){
            if(state.selected_categ_l2){
                return " > " + state.selected_categ_l2
            }       
        },
        getServerList(state){
            const project = state.projects.find((project) => project.project_name == state.selected_proj)
            return project.project_server
        }
    },
    mutations: {
        resetSelect(state){
            state.mode = 0
            state.full = false
            state.selected_proj = ""
            state.selected_categ_l1 = ""
            state.selected_categ_l2 = ""
        },
        setOrg(state, org){
            state.mode = 0
            state.organName = org.org_name;
            state.organId = org.org_id;
        },
        setProj(state, data){
            state.org_user_privileges = data.org_user_privileges
            state.projects = data.projects
            state.projects.project_id = data.projects.project_id
            state.projects.project_name = data.projects.project_name
        },
        //mode 1 proj
        selectProj(state, selected_proj){
            state.mode = 1
            if(state.selected_proj != selected_proj){
                state.selected_categ_l1 = ""
                state.selected_categ_l2 = ""
            }
            state.selected_proj = selected_proj
        },
        selectCatl1(state, selected_categ_l1){
            state.selected_categ_l1 = selected_categ_l1
        },
        selectCatl2(state, selected_categ_l2){
            state.selected_categ_l2 = selected_categ_l2
        },
        //mode 2 contributors
        selectContributors(state){
            state.mode = 2
            state.selected_proj = ""
            state.selected_categ_l1 = ""
            state.selected_categ_l2 = ""
        },
        //mode 3 terminal
        selectTerminal(state){
            state.mode = 3
        },
        toggleFullWindow(state){
            state.full = !state.full
        }
    },
    actions: {
        async getProjects(context, org){
            const data = await api.getProj(org.org_id)
            if(data){
              context.commit("setOrg", org)
              context.commit("setProj", data)
              router.push('/in')
              ipcRenderer.send('resize-window')
            }
            else{
              console.log("getProj fail")
            }
        },
        async Contributors(context){
            const data = await api.getContributors(context.state.organId)
            if(data){
              context.commit("selectContributors")
              return data
            }
            else{
              console.log("Contritubors fail in store")
              return false
            }
        },
    },
    modules: {
    },
})