import React from 'react';
import { BaseRecord, DataProvider, LogicalFilter } from '@refinedev/core';
import axios from 'axios';
import { ACCESS_TOKEN } from './authProvider';
import { DTO, IResponseData, IResponsePagination, IUser } from './utils/interfaces';

const customSerializer = (params: any) => {
    // Build query string manually to avoid square brackets
    const queryString = Object.entries(params)
        .map(([key, value]) => {
            if (Array.isArray(value)) {
                return value.map((item) => `${key}=${item}`).join('&');
            }
            return `${key}=${value}`;
        })
        .join('&');

    return queryString;
};

export const dataProvider = (url: string): DataProvider => ({
    getOne: async ({ id, resource }) => {
        const request = axios.create({
            baseURL: `${url}`,
            headers: {
                'Content-Type': 'application/json',
                Authorization: 'Bearer ' + localStorage.getItem(ACCESS_TOKEN),
            },
        });
        const response = await request.get(`${url.endsWith('/') ? resource : `/${resource}`}/${id}`);
        const responseBody: IResponseData<any> = response.data;

        console.log(responseBody);
        if (responseBody.success) {
            return {
                data: responseBody.data,
            };
        } else {
            return {
                data: responseBody.result.message,
            };
        }
    },

    create: async ({ resource, variables, meta }) => {
        console.log('resource', resource);
        console.log('variable', variables);
        console.log('meta', meta);
        // throw new Error('Not implemented');
        const request = axios.create({
            baseURL: `${url}`,
            headers: {
                'Content-Type': 'application/json',
                Authorization: 'Bearer ' + localStorage.getItem(ACCESS_TOKEN),
            },
        });
        try {
            const response = await request.post(
                url.endsWith('/') ? resource : `/${resource}`,
                JSON.stringify(variables),
            );
            const data = await response.data;
            return {
                data: data,
            };
        } catch (error) {
            throw error;
        }
    },
    update: async ({ resource, id, variables, meta }) => {
        resource = `${resource}/${id}`;
        const request = axios.create({
            baseURL: `${url}`,
            headers: {
                'Content-Type': 'application/json',
                Authorization: 'Bearer ' + localStorage.getItem(ACCESS_TOKEN),
            },
        });
        const response = await request.put(url.endsWith('/') ? resource : `/${resource}`, JSON.stringify(variables));
        const data: IResponseData<any> = await response.data;
        console.log(data);
        return {
            data: data.data,
        };
    },
    deleteOne: async ({ resource, id, variables, meta }) => {
        resource = `${resource}/${id}`;
        const request = axios.create({
            baseURL: `${url}`,
            headers: {
                'Content-Type': 'application/json',
                Authorization: 'Bearer ' + localStorage.getItem(ACCESS_TOKEN),
            },
        });
        const response = await request.delete(url.endsWith('/') ? resource : `/${resource}`);
        const data: IResponseData<any> = await response.data;
        console.log(data);
        return {
            data: data.data,
        };
    },

    getList: async ({ resource, pagination, filters, sorters, meta }) => {
        console.log('pagination', filters);
        if (!pagination) {
            pagination = {
                current: 0,
                pageSize: 999999,
            };
        }

        let filterParam: any = {};
        if (filters) {
            console.log('In');
            filters.forEach((filter: any) => {
                console.log('Alo');
                if (filter.value) {
                    filterParam[filter.field] = encodeURIComponent(filter.value);
                } else {
                    return;
                }
            });
        }
        console.log(filterParam);
        const request = axios.create({
            baseURL: `${url}`,
            headers: {
                'Content-Type': 'application/json',
                Authorization: 'Bearer ' + localStorage.getItem(ACCESS_TOKEN),
            },
        });
        const response = await request.get(url.endsWith('/') ? resource : `/${resource}`, {
            params: {
                page: pagination?.current,
                size: pagination?.pageSize,
                ...filterParam,
            },
            paramsSerializer: customSerializer,
        });
        const data: IResponsePagination<any> = await response.data;

        console.log('data', data);
        return {
            data: data.data.data,
            total: data.data.dataCount,
        };
    },
    getApiUrl: () => url,

    custom: async ({ url, method, filters, sort, payload, query, headers, metaData }) => {
        const response = await fetch(`${url}`);
        const data = await response.json();

        return {
            data,
        };
    },
});

function sleep(ms: number) {
    return new Promise((resolve) => setTimeout(resolve, ms));
}
