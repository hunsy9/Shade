const server = "http://localhost:8081/"

export default {
    async login(info) {
        // console.log("입력된 id, pw" + id + pw)

        const response = await fetch(`${server}api/user/signin`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
            },
            body: JSON.stringify(info)
        })

        if (!response.ok) {
            throw new Error('로그인에 실패했습니다.')
        }

        console.log("123")
        const data = response.json()
        console.log(data)
        return data
    },
    async getOrg(user_id) {

        const response = await fetch(`${server}api/user/getUserInfo/${user_id}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        })

        if (!response.ok) {
            throw new Error('Organization을 가져오는데 실패했습니다.')
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
            body: serverInfo
        })
        console.log(response)
        if (!response.ok) {
            throw new Error()
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
    async isDup(email) {
        console.log("입력된 email " + email)

        const response = await fetch(`${server}api/user/duplicateEmail?email=${email}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        })

        if (!response.ok) {
            throw new Error('중복체크에 실패했습니다.')
        }

        const data = await response.json()
        return data
    },
    async signUp(info) {
        console.log("입력된 info " + info)

        const response = await fetch(`${server}api/user/signup`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(info)
        })

        if (!response.ok) {
            throw new Error('회원가입에 실패했습니다.')
        }

        console.log("pass")
        const data = await response.json()
        return data
    },
    async addOrg(info) {
        console.log("입력된 info " + info)

        const response = await fetch(`${server}api/org/addOrganization`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(info)
        })

        if (!response.ok) {
            throw new Error('회원가입에 실패했습니다.')
        }

        console.log("pass")
        const data = await response.json()
        return data
    },
}