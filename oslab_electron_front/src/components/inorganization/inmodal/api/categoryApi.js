const server = "http://localhost:8081/";

export default {
    async addCategoryL1(categoryDto) {
        const response = await fetch(`${server}api/category/addL1`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(categoryDto)
        })
        return response.json()
    },
    async addCategoryL2(categoryDto){
        const response = await fetch(`${server}api/category/addL2`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(categoryDto)
        })
        return response.json()
    },
    async editCategory(dto){
        const response = await fetch(`${server}api/category/edit`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(dto)
        })
        return response
    },
    async reFetchCategory(dto){
        let org_id = dto.org_id
        let project_id = dto.project_id

        const response = await fetch(`${server}api/category/reFetchCategory?org_id=${org_id}&project_id=${project_id}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        })
        return response.json()
    }

}