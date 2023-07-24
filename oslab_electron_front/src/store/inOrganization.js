import api from '@/api/login.js'
import { ipcRenderer } from "electron"
import router from '@/router/index.js'

export const terminalState = {
    INIT: 0,
    OPENED: 1,
    TERMINATED: 2,
}

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
        selected_categid: "",
        exitShellState: 0
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
                state.selected_categid = ""
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
            state.selected_categid = ""
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
        setNewServer(state, data){
            let key = data.k
            alert(key)
            alert(state.projects.project_server)
            state.projects.project_server[key] = data.d
        },
        //mode 1 proj
        selectProj(state, selected_proj){
            state.mode = 1
            if(state.selected_proj != selected_proj){
                state.selected_categ_l1 = ""
                state.selected_categ_l2 = ""
                state.selected_categid = ""
            }
            state.selected_proj = selected_proj
        },
        selectCatl1(state, selected_categ_l1){
            state.selected_categ_l1 = selected_categ_l1
        },
        selectCatl2(state, selected_categ_l2){
            state.selected_categ_l2 = selected_categ_l2
            const a = Object.keys(state.projects.find(project => project.project_name === state.selected_proj).project_server)
            for(var i = 0; i < a.length; i++){
                const b = a[i].split(":")
                console.log(b)
                if(b[1] == state.selected_categ_l1 && b[2] == state.selected_categ_l2){
                    state.selected_categid = b[0]
                }
            }
        },
        //mode 2 contributors
        selectContributors(state){
            state.mode = 2
            state.selected_proj = ""
            state.selected_categ_l1 = ""
            state.selected_categ_l2 = ""
            state.selected_categid = ""
        },
        //mode 3 terminal
        selectTerminal(state){
            state.mode = 3
        },
        toggleFullWindow(state){
            state.full = !state.full
        },
        setExitShellstatus(state, terminalState){
            state.exitShellState = terminalState;
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
        async addNewServer(context, server){
            const obj = {
                "org_id": parseInt(context.state.organId),
                "category_id": parseInt(context.state.selected_categid),
                "server_name": server.server_name,
                "server_desc": server.server_desc,
                "username": server.username,
                "host" : server.host,
                "port" : parseInt(server.port),
                "password" : server.password,
                "server_id" : null
            }
            console.log(obj)
            const data = await api.postNewServer(obj)
            if(data){
              return true
            }
            else{
              console.log("fail in addNewServer")
              return false
            }
        },
        async refetchNewServer(context){
            const obj = {
                "org_id": parseInt(context.state.organId),
                "category_id": parseInt(context.state.selected_categid),
            }
            const data = await api.refetchServer(obj)
            const keys = Object.keys(context.state.projects.find(project => project.project_name === context.state.selected_proj).project_server)
            let findKey = keys.filter(key => key.split(":")[0] == obj.category_id)
            const oo = {
                d: data,
                k: findKey
            }
            context.commit("setNewServer", oo)
            if(data){
                console.log("success in fetchNewServer")
            }
            else{
                console.log("fail in fetchNewServer")
            }
        },
    },
    modules: {
    },
})