import { HeaderKey } from './utils/interfaces';

export const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

export const roles = {
    warehouseManager: 'ROLE_WAREHOUSE_MANAGER',
    admin: 'ROLE_ADMIN',
    superAdmin: 'ROLE_SUPER_ADMIN',
    employee: 'ROLE_EMPLOYEE',
};

export const productHeaderTableKey: HeaderKey[] = [
    {
        title: 'Product Name',
        description: '',
        minWidth: 300,
    },
    {
        title: 'Product Code',
        description: '',
        minWidth: 200,
    },
    {
        title: 'Product Category',
        description: '',
        minWidth: 200,
    },
    {
        title: 'Reoder Quantity',
        description:
            'The total number of product units you request from a manufacturer or supplier on an inventory replenishment purchase order.',
        minWidth: 200,
    },
    {
        title: 'Unit',
        description: '',
        minWidth: 150,
    },
    {
        title: 'Is Packed?',
        description: '',
        minWidth: 120,
    },
    {
        title: 'SKU',
        description:
            'In inventory management, a stock keeping unit (abbreviated as SKU) is the unit of measure in which the stocks of a material are managed',
        minWidth: 120,
    },
    {
        title: 'Created At',
        description: '',
        minWidth: 200,
    },
    {
        title: 'Created By',
        description: '',
        minWidth: 200,
    },
    {
        title: 'Last Modified At',
        description: '',
        minWidth: 200,
    },
    {
        title: 'Last Modified By',
        description: '',
        minWidth: 200,
    },
];
