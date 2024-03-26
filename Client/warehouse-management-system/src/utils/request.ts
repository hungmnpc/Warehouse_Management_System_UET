import axios from "axios";

const request = axios.create({
    baseURL: 'http://localhost:8222',
    headers: {
        'content-type': 'application/json',
    },
});

export const login = async (username:string, password:string) => {
    const response = await request.post('/auth/login',JSON.stringify(
    {
        username: username,
        password: password,
    }));
    
    return response.data;
};