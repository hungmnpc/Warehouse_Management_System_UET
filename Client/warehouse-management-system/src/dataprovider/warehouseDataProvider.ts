import React from 'react';
import { BaseRecord, CreateResponse, DataProvider } from '@refinedev/core';
import axios from 'axios';
import { ACCESS_TOKEN } from '../authProvider';
import { IResponseData, IResponsePagination } from '../utils/interfaces';

interface CreateParams<TVariables> {
    resource: string;
    variables: TVariables;
    meta?: any;
}

export const warehouseProvider = (url: string): DataProvider => ({
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

    create: async <TData extends BaseRecord = BaseRecord, TVariables = {}>(
        params: CreateParams<any>,
    ): Promise<CreateResponse<TData>> => {
        const { resource, variables, meta } = params;
        console.log('resource', resource);
        console.log('variable', variables);
        console.log('meta', meta);
        let resource_ = resource == 'warehouses' ? '' : resource;

        let variables_;

        let pathUrl = resource_ == '' ? '' : url.endsWith('/') ? resource_ : `/${resource_}`;
        if (resource_ == '') {
            let { province, ward, district, ...rest } = variables;
            variables_ = {
                ...rest,
                provinceName: province.province_name,
                provinceId: province.province_id,
                districtName: district.district_name,
                districtId: district.district_id,
                wardName: ward.ward_name,
                wardId: ward.ward_id,
            };
        } else {
            variables_ = variables;
        }
        const request = axios.create({
            baseURL: `${url}`,
            headers: {
                'Content-Type': 'application/json',
                Authorization: 'Bearer ' + localStorage.getItem(ACCESS_TOKEN),
            },
        });
        const response = await request.post(pathUrl, JSON.stringify(variables_));
        const data = await response.data;
        return {
            data: data,
        };
    },
    update: async () => {
        throw new Error('Not implemented');
    },
    deleteOne: async ({ resource, id, variables, meta }) => {
        throw new Error('Not implemented');
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
