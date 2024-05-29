import { List } from '@refinedev/mui';
import axios from 'axios';
import { UUID } from 'crypto';
import { ACCESS_TOKEN } from '../authProvider';

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
            `/histories/page?filterAnd=${encodeURIComponent(
                `agent_type|eq|${agentType}&agent_id|eq|${agentId}`,
            )}&orders=${encodeURIComponent('ts|asc')}`,
        );
        console.log(response);
        return response.data.data;
    } catch (exception: any) {
        console.log(exception.response.data);
        return exception.response.data;
    }
};

export const postProductsToSupplier = async (productIds: UUID[], supplierId: UUID | any) => {
    try {
        request.interceptors.request.use(function (config) {
            const token = localStorage.getItem(ACCESS_TOKEN);
            config.headers.Authorization = token ? `Bearer ${token}` : '';
            return config;
        });
        const response = await request.post(`/suppliers/${supplierId}/products/batch`, JSON.stringify(productIds));
        console.log(response);
        return response.data;
    } catch (exception: any) {
        console.log(exception.response.data);
        return exception.response.data;
    }
};

export const postProductsToPurchaseOrder = async (purchaseOrderId: UUID | any, data: any) => {
    try {
        request.interceptors.request.use(function (config) {
            const token = localStorage.getItem(ACCESS_TOKEN);
            config.headers.Authorization = token ? `Bearer ${token}` : '';
            return config;
        });
        const response = await request.post(`/purchase_orders/${purchaseOrderId}/products`, JSON.stringify(data));
        console.log(response);
        return response.data;
    } catch (exception: any) {
        console.log(exception.response.data);
        return exception.response.data;
    }
};

export const updateStatusPurchaseOrder = async (purchaseOrderId: UUID | any, status: any) => {
    try {
        request.interceptors.request.use(function (config) {
            const token = localStorage.getItem(ACCESS_TOKEN);
            config.headers.Authorization = token ? `Bearer ${token}` : '';
            return config;
        });
        const response = await request.put(`/purchase_orders/${purchaseOrderId}/status`, JSON.stringify(status));
        console.log(response);
        return response.data;
    } catch (exception: any) {
        console.log(exception.response.data);
        return exception.response.data;
    }
};

export const updateDeadlinePurchaseOrder = async (purchaseOrderId: UUID | any, date: any) => {
    try {
        request.interceptors.request.use(function (config) {
            const token = localStorage.getItem(ACCESS_TOKEN);
            config.headers.Authorization = token ? `Bearer ${token}` : '';
            return config;
        });
        const response = await request.put(`/purchase_orders/${purchaseOrderId}/deadline`, JSON.stringify(date));
        console.log(response);
        return response.data;
    } catch (exception: any) {
        console.log(exception.response.data);
        return exception.response.data;
    }
};

export const assignEmployeeToPo = async (purchaseOrderId: UUID | any, employeeIds: any) => {
    try {
        request.interceptors.request.use(function (config) {
            const token = localStorage.getItem(ACCESS_TOKEN);
            config.headers.Authorization = token ? `Bearer ${token}` : '';
            return config;
        });
        const response = await request.post(
            `/purchase_orders/${purchaseOrderId}/employees`,
            JSON.stringify(employeeIds),
        );
        console.log(response);
        return response.data;
    } catch (exception: any) {
        console.log(exception.response.data);
        return exception.response.data;
    }
};

export const checkPoWasAssigned = async (purchaseOrderId: UUID | any) => {
    try {
        request.interceptors.request.use(function (config) {
            const token = localStorage.getItem(ACCESS_TOKEN);
            config.headers.Authorization = token ? `Bearer ${token}` : '';
            return config;
        });
        const response = await request.get(`/purchase_orders/${purchaseOrderId}/employees/check`);
        console.log(response);
        return response.data;
    } catch (exception: any) {
        console.log(exception.response.data);
        return exception.response.data;
    }
};

export const getCountPOWarehouse = async (warehouseId: UUID | any) => {
    try {
        request.interceptors.request.use(function (config) {
            const token = localStorage.getItem(ACCESS_TOKEN);
            config.headers.Authorization = token ? `Bearer ${token}` : '';
            return config;
        });
        const response = await request.get(`/warehouses/${warehouseId}/purchase_orders/count`);
        console.log(response);
        return response.data;
    } catch (exception: any) {
        console.log(exception.response.data);
        return exception.response.data;
    }
};

export const getCountGrWarehouse = async (warehouseId: UUID | any) => {
    try {
        request.interceptors.request.use(function (config) {
            const token = localStorage.getItem(ACCESS_TOKEN);
            config.headers.Authorization = token ? `Bearer ${token}` : '';
            return config;
        });
        const response = await request.get(`/warehouses/${warehouseId}/goods_received/count`);
        console.log(response);
        return response.data;
    } catch (exception: any) {
        console.log(exception.response.data);
        return exception.response.data;
    }
};

export const getAllAreaWarehouse = async (warehouseId: UUID | any) => {
    try {
        request.interceptors.request.use(function (config) {
            const token = localStorage.getItem(ACCESS_TOKEN);
            config.headers.Authorization = token ? `Bearer ${token}` : '';
            return config;
        });
        const response = await request.get(`/warehouses/${warehouseId}/areas`);
        console.log(response);
        return response.data;
    } catch (exception: any) {
        console.log(exception.response.data);
        return exception.response.data;
    }
};

export const getAllAisleInArea = async (areaId: UUID | any) => {
    try {
        request.interceptors.request.use(function (config) {
            const token = localStorage.getItem(ACCESS_TOKEN);
            config.headers.Authorization = token ? `Bearer ${token}` : '';
            return config;
        });
        const response = await request.get(`/warehouses/areas/${areaId}/aisles`);
        console.log(response);
        return response.data;
    } catch (exception: any) {
        console.log(exception.response.data);
        return exception.response.data;
    }
};

export const getBinLocationInAisle = async (aisleId: UUID | any) => {
    try {
        request.interceptors.request.use(function (config) {
            const token = localStorage.getItem(ACCESS_TOKEN);
            config.headers.Authorization = token ? `Bearer ${token}` : '';
            return config;
        });
        const response = await request.get(`/warehouses/areas/aisles/${aisleId}/bins`);
        console.log(response);
        return response.data;
    } catch (exception: any) {
        console.log(exception.response.data);
        return exception.response.data;
    }
};

export const getBinDetail = async (barcode: any, warehouseId: any) => {
    try {
        request.interceptors.request.use(function (config) {
            const token = localStorage.getItem(ACCESS_TOKEN);
            config.headers.Authorization = token ? `Bearer ${token}` : '';
            return config;
        });
        const response = await request.get(`/warehouses/bins/barcode/${barcode}`, {
            params: {
                warehouseId: warehouseId,
            },
        });
        console.log(response);
        return response.data;
    } catch (exception: any) {
        console.log(exception.response.data);
        return exception.response.data;
    }
};

export const getProductList = async (productName: any, page: any, size: any) => {
    try {
        request.interceptors.request.use(function (config) {
            const token = localStorage.getItem(ACCESS_TOKEN);
            config.headers.Authorization = token ? `Bearer ${token}` : '';
            return config;
        });
        const response = await request.get(`products`, {
            params: {
                page: page ? page : 0,
                size: size ? size : 99999,
                productName: productName ? productName : '',
            },
        });
        console.log(response);
        return response.data;
    } catch (exception: any) {
        console.log(exception.response.data);
        return exception.response.data;
    }
};

export const getAreaGroups = async () => {
    try {
        request.interceptors.request.use(function (config) {
            const token = localStorage.getItem(ACCESS_TOKEN);
            config.headers.Authorization = token ? `Bearer ${token}` : '';
            return config;
        });
        const response = await request.get(`warehouses/areas/groups`);
        console.log(response);
        return response.data;
    } catch (exception: any) {
        console.log(exception.response.data);
        return exception.response.data;
    }
};

export const getProductsInBin = async (binId: any) => {
    try {
        request.interceptors.request.use(function (config) {
            const token = localStorage.getItem(ACCESS_TOKEN);
            config.headers.Authorization = token ? `Bearer ${token}` : '';
            return config;
        });
        const response = await request.get(`inventories/products/bins/${binId}/products`);
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
