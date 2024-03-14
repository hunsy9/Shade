const server = "http://localhost:8081/";

export default {
    async deleteProject(deleteDto){
        console.log(deleteDto)
    },
    async deleteContributor(deleteDto){
        const response = await fetch(`${server}api/contributor/delete`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(deleteDto)
        })
        return response
    },
    async delCategory(deleteDto) {
        const response = await fetch(`${server}api/category/delCategory`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(deleteDto)
        })
        return response
    },
    async deleteServer(deleteDto) {
        let res = await fetch(`${server}api/server/delete?server_id=${deleteDto.server_id}`, {
            method: 'POST',
        })
        return res
    },
}