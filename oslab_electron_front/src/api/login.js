export default {
    async login() {
        const response = await fetch('http://localhost:8080/api/user/getUserInfo/1', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
            
        });

        if (!response.ok) {
            throw new Error('로그인에 실패했습니다.');
        }

        const data = await response.json();
        return data;
    }
}