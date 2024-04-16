import React from 'react';
import { BaseRecord, DataProvider } from '@refinedev/core';
import axios from 'axios';
import { ACCESS_TOKEN } from './authProvider';
import { DTO, IResponseData, IResponsePagination, IUser } from './utils/interfaces';

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
        const request = axios.create({
            baseURL: `${url}`,
            headers: {
                'Content-Type': 'application/json',
                Authorization: 'Bearer ' + localStorage.getItem(ACCESS_TOKEN),
            },
        });
        const response = await request.post(url.endsWith('/') ? resource : `/${resource}`, JSON.stringify(variables));
        const data = await response.data;
        return {
            data: data,
        };
    },
    update: async () => {
        throw new Error('Not implemented');
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
        if (!pagination) {
            pagination = {
                current: 0,
                pageSize: 999999,
            };
        }
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
            },
        });
        const data: IResponsePagination<any> = await response.data;

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
