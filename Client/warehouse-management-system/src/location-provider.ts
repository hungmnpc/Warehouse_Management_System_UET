import React from 'react';
import { BaseRecord, DataProvider } from '@refinedev/core';
import axios from 'axios';
import { ACCESS_TOKEN } from './authProvider';
import { DTO, IResponseData, IResponsePagination, IUser } from './utils/interfaces';

export const locationProvider = (url: string): DataProvider => ({
    getOne: async ({ id, resource }) => {
        throw new Error('Not implemented');
    },

    create: async ({ resource, variables, meta }) => {
        throw new Error('Not implemented');
    },
    update: async () => {
        throw new Error('Not implemented');
    },
    deleteOne: async ({ resource, id, variables, meta }) => {
        throw new Error('Not implemented');
    },

    getList: async ({ resource, pagination, filters, sorters, meta }) => {
        const request = axios.create({
            baseURL: `${url}`,
            headers: {
                'Content-Type': 'application/json',
            },
        });
        const response = await request.get(url.endsWith('/') ? resource : `/${resource}`);
        console.log(response);
        return {
            data: response.data.results,
            total: response.data.results.lenght,
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
