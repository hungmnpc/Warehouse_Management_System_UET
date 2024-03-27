import React from "react";
import { BaseRecord, DataProvider } from "@refinedev/core";
import axios from "axios";
import { ACCESS_TOKEN } from "./authProvider";
import { IResponsePagination, IUser } from "./utils/interfaces";

export const dataProvider = (url: string): DataProvider => ({

    

  getOne: async ({ id, resource }) => {
    const response = await fetch(`${url}/${resource}/${id}`);
      const data = await response.json();

      return {
          data,
      };
  },


  create: async () => {
      throw new Error("Not implemented");
  },
  update: async () => {
      throw new Error("Not implemented");
  },
  deleteOne: async () => {
      throw new Error("Not implemented");
  },

  getList: async ({ resource, pagination, filters, sorters, meta }) => {
    console.log("call 1")
    const request = axios.create({
        baseURL: `${url}`,
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN)
        },
    });
    const response = await request.get(url.endsWith('/') ? resource : `/${resource}`);
    const data:IResponsePagination<any> = await response.data;
    console.log("datasd", data)
    return {
        data: data.data.data,
        total: data.data.dataCount
    };
  },
  getApiUrl: () => url,

  custom: async({url,
    method,
    filters,
    sort,
    payload,
    query,
    headers,
    metaData,}) => {
    const response = await fetch(`${url}`);
      const data = await response.json();

      return {
          data,
      };
  },
});