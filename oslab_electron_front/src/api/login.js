const server = "http://localhost:8081/"

export default {
    async login(id, pw) {
        console.log("입력된 id, pw" + id + pw)

        const response = await fetch(`${server}api/user/getUserInfo/1`, {
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
        const response = await fetch(`${server}api/org/getOrgInfo/1/${org_id}`, {
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
    },
    async getContributors(org_id) {
        const response = await fetch(`${server}api/org/getOrgMemberInfo/${org_id}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        })

        if (!response.ok) {
            throw new Error(org_id + '의 contributors를 가져오는데 실패 했습니다.')
        }

        const data = await response.json()

        return data
    },
    async getRoomId(server_id, user_id) {
        const response = await fetch(`${server}api/request/getRoomId/${user_id}/${server_id}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        })

        if (!response.ok) {
            throw new Error(user_id + "가" + server_id + '의 RoomId를 가져오는데 실패 했습니다.')
        }

        const data = await response.text()

        return data
    },
    async postSSH(postData) {
        const response = await fetch(`${server}api/request/generateKey`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(postData)
        })
        console.log(response)
        if (!response.ok) {
            throw new Error(postData.user_id + "가" + postData.server_id + '의 RoomKey를 가져오는데 실패 했습니다.')
        }

        const data = await response.text()

        return data
    },
    async postNewServer(serverInfo) {
        const response = await fetch(`${server}api/server/add`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(serverInfo)
        })
        console.log(response)
        if (!response.ok) {
            throw new Error(serverInfo.server_name + "가" + serverInfo.username + '를 가져오는데 실패 했습니다.')
        }

        const data = await response.text()

        return data
    },
    async refetchServer(obj) {
        const response = await fetch(`${server}api/server/reFetchServer/${obj.org_id}/${obj.category_id}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        })
        console.log(response)
        if (!response.ok) {
            throw new Error(obj.org_id + "가" + obj.category_id + '를 가져오는데 실패 했습니다.')
        }

        const data = await response.text()

        return data
    },
}