export default {
    async login(username, password) {
        const response = await fetch('https://cdbe8898-93a7-4f31-97c0-b0c51738aa02.mock.pstmn.io/api/test', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ username, password }),
        });

        if (!response.ok) {
            throw new Error('로그인에 실패했습니다.');
        }

        const data = await response.json();
        return data;
    }
}