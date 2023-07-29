const server = "http://localhost:8081/";

import store from '@/store/index';

export default {

    async deleteProject(deleteDto){
        console.log(deleteDto)
    },
    async deleteContributor(deleteDto){
        console.log(deleteDto)
    },
    async deleteCategoryL1(deleteDto) {
        console.log(deleteDto)
    },
    async deleteCategoryL2(deleteDto){
        console.log(deleteDto)
    },
    async deleteServer(deleteDto) {
        let res = await fetch(`${server}api/server/delete?server_id=${deleteDto.server_id}`, {
            method: 'POST',
        })
        if(await res.json()){
            await store.dispatch('inOrganization/refetchNewServer')
        }
    },

}