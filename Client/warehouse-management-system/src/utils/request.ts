import axios from 'axios';
import { UUID } from 'crypto';

const request = axios.create({
    baseURL: 'http://localhost:8222',
    headers: {
        'content-type': 'application/json',
    },
});

export const login = async (username: string, password: string) => {
    try {
        const response = await request.post(
            '/auth/login',
            JSON.stringify({
                username: username,
                password: password,
            }),
        );
        console.log(response);
        return response.data;
    } catch (exception: any) {
        console.log(exception.response.data);
        return exception.response.data;
    }
};

export const getHistory = async (agentType: string, agentId: UUID | any) => {
    try {
        const response = await request.get(
            `/histories?filterAnd=${encodeURIComponent(
                `agent_type|eq|${agentType}&agent_id|eq|${agentId}`,
            )}&orders=${encodeURIComponent('ts|desc')}`,
        );
        console.log(response);
        return response.data;
    } catch (exception: any) {
        console.log(exception.response.data);
        return exception.response.data;
    }
};

// export const login = async (username: string, password: string) => {
//     try {
//         const response = await request.post(
//             '/auth/login',
//             JSON.stringify({
//                 username: username,
//                 password: password,
//             }),
//         );
//         console.log(response);
//         return response.data;
//     } catch (exception: any) {
//         console.log(exception.response.data);
//         return exception.response.data;
//     }
// };
