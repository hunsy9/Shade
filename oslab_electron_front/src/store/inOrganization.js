import api from '@/api/login.js'
import { ipcRenderer } from "electron"
import router from '@/router/index.js'
import categoryApi from "@/components/inorganization/inmodal/api/categoryApi";
import deleteApi from "@/components/common/api/deleteApi";
// import store from "@/store/index"

export const terminalState = {
    INIT: 0,
    OPENED: 1,
    TERMINATED: 2,
}

export const DeleteState = {
    DELETEPROJECT: 0,
    DELETECONTRIBUTOR: 1,
    DELETECATEGORYL1: 2,
    DELETECATEGORYL2: 3,
    DELETESERVER: 4
}

export const ActionCategoryState = {
    ADDL1:0,
    ADDL2:1,
    EDIT: 2,
}

export const RefetchState = {
    BEFOREORG: 0,
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
        selected_proj_id: 0,
        selected_categ_l1: "",
        selected_categ_l2: "",
        selected_categid: "",
        exitShellState: 0,
        isAdmin: false,
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
        },
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
        reFetchServer(state, dto){
            let key = dto.key
            let newData = JSON.parse(dto.newData)
            state.projects.find(projects => projects.project_name == state.selected_proj).project_server[key] = newData
        },
        reFetchProject(state,dto){
            state.projects.push(dto)
        },
        reFetchCategoryL1(state, dto){
            let newPrev = dto.prev
            state.projects.find(projects=>projects.project_id === state.selected_proj_id).category[newPrev] = null
            let newCategoryKey = dto.new_category_id + ":" + newPrev + ":null"
            state.projects.find(projects=>projects.project_id === state.selected_proj_id).project_server[newCategoryKey] = []
        },
        reFetchCategory(state,dto){
            state.projects.find(project => project.project_id === state.selected_proj_id).category = dto.category
            state.projects.find(project => project.project_id === state.selected_proj_id).project_server = dto.project_server
        },
        reFetchSelectedCategory(state,dto) {
            if(dto.prev != ""){
                state.selected_categ_l1 = dto.prev
            }
            if(dto.next != ""){
                state.selected_categ_l2 = dto.next
            }
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
            state.selected_proj_id = state.projects.find(project=> project.project_name === selected_proj).project_id
        },
        selectCatl1(state, selected_categ_l1){
            state.selected_categ_l1 = selected_categ_l1
        },
        selectCatl2(state, selected_categ_l2){
            state.selected_categ_l2 = selected_categ_l2
            const a = Object.keys(state.projects.find(project => project.project_name === state.selected_proj).project_server)
            for(var i = 0; i < a.length; i++){
                const b = a[i].split(":")
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
        },
        setOrgAdmin(state, isadmin) {
            state.isAdmin = isadmin
        }
    },
    actions: {
        async getProjects(context, org){
            const data = await api.getProj(org.org_id)
            if(data) {
                context.commit("setOrg", org)
                context.commit("setProj", data)
                router.push('/in')
                ipcRenderer.send('resize-window')
            }
            else{
                console.log("getProj fail")
            }

            const user_id = context.rootState.login.userID

            const admin_id = await api.getAdmin(org.org_id)
            if (admin_id) {
                context.commit("setOrgAdmin", (user_id == admin_id))
            }
            else {
                console.log("getAdminId fail")
            }
        },
        async Contributors(context){
            const data = await api.getContributors(context.state.organId)
            if(data){
                //   context.commit("selectContributors")
                return data
            }
            else{
                console.log("Contritubors fail in store")
                return false
            }
        },
        async addNewServer(context, server){
            let publicKey = server.publicKey
            console.log(publicKey)
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

            let formData = new FormData();

            if (publicKey == "") {
                formData.append("keyfile", new File([], "nFile"))
            } else {
                formData.append("keyfile", publicKey)
            }

            formData.append("serverDetail", new Blob([JSON.stringify(obj)], {type: "application/json"}))
            console.log(obj)
            const data = await api.postNewServer(formData)
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
            const dto = {
                newData: data,
                key: findKey
            }
            context.commit("reFetchServer", dto)
            if(data){
                console.log("success in fetchNewServer")
            }
            else{
                console.log("fail in fetchNewServer")
            }
        },
        async addNewProject(context, data){
            const res = await api.postProject(data)
            console.log("newData: " + res)
            const dto = {
                "project_id": res,
                "project_name": data.project_name,
                "category": null,
                "project_server": null
            }
            context.commit("reFetchProject", dto)
            if(res){
                console.log("success in addNewProject")
            }
            else{
                console.log("fail in addNewProject")
            }
        },
        async addCategoryL1(context, data){
            await categoryApi.addCategoryL1(data)
            // let res = await categoryApi.addCategoryL1(data)
            // const dto = {
            //     "new_category_id": res,
            //     ...data
            // }
            // if(res > 0){
            //     context.commit("reFetchCategoryL1", dto)
            // }
        },
        async addCategoryL2(context,data){
            await categoryApi.addCategoryL2(data)
        },
        async reFetchCategory(context){
            const dto = {
                org_id : context.state.organId,
                project_id: context.state.selected_proj_id
            }
            let res = await categoryApi.reFetchCategory(dto)
            context.commit("reFetchCategory", res)
        },
        async reFetchContributor(context){
            const data = await context.dispatch('Contributors')
            if(data.admin_contributor.user_id){
                await context.commit('contributorInfo/setContributors', data,{root:true})
            }
        },
        async reFetchSelectedCategory(context,data){
            context.commit('reFetchSelectedCategory', data)
        },
        async deleteServer(context,dto){
            await deleteApi.deleteServer(dto)
        },
        async deleteCategory(context, dto){
            let res = await deleteApi.delCategory(dto)
            console.log(res)
        },
        async editCategory(context, dto){
            await categoryApi.editCategory(dto)
        },
        async deleteContributor(context,dto){
            await deleteApi.deleteContributor(dto)
        }

    },
    modules: {
    },
})