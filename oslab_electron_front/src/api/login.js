export default {
    async login(id, pw) {
        console.log("입력된 id, pw" + id + pw)

        const response = await fetch('http://localhost:8080/api/user/getUserInfo/1', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
            
        })

        if (!response.ok) {
            throw new Error('로그인에 실패했습니다.')
        }

        const data = await response.json()
        return data
    },
    async getProj(org_id) {
        const response = await fetch(`http://localhost:8080/api/org/getOrgInfo/1/${org_id}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
            
        })

        if (!response.ok) {
            throw new Error(org_id + '를 getProj하는 것에 실패 했습니다.')
        }

        const data = await response.json()
        return data
    }
}