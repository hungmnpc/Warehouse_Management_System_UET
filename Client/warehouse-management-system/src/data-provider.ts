import React from "react";
import { DataProvider } from "@refinedev/core";

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
  getList: async () => {
      throw new Error("Not implemented");
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