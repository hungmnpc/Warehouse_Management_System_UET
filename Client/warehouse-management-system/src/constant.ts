import { PurchaseOrderStatus } from './pages/purchase-order/status';
import { HeaderKey } from './utils/interfaces';

export const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

export const roles = {
    warehouseManager: 'ROLE_WAREHOUSE_MANAGER',
    admin: 'ROLE_ADMIN',
    superAdmin: 'ROLE_SUPER_ADMIN',
    employee: 'ROLE_EMPLOYEE',
};

export const PurchaseOrderStatusRaw = Object.freeze({
    DRAFT: 1,
    INCOMING: 2,
    RECEIVED_AND_REQUIRES_WAREHOUSING: 3,
    WAREHOUSING: 4,
    STOCKED: 5,
});

export const supplierHeaderTableKey: HeaderKey[] = [
    {
        title: 'Supplier Name',
        description: '',
        minWidth: 300,
    },
    {
        title: 'Supplier Number',
        description: '',
        minWidth: 300,
    },
    {
        title: 'Supplier Address 1',
        description: '',
        minWidth: 300,
    },
    {
        title: 'Supplier Address 2',
        description: '',
        minWidth: 300,
    },
    {
        title: 'Created At',
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

export const supplierProductHeaderTableKey: HeaderKey[] = [
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
];

export const productHeaderTableDialogKey: HeaderKey[] = [
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
        title: 'Unit',
        description: '',
        minWidth: 150,
    },
    {
        title: 'Is Packed?',
        description: '',
        minWidth: 120,
    },
];

export const PurchaseOrderDetailKeyHeader: HeaderKey[] = [
    {
        title: 'Product Name',
        description: '',
        minWidth: 200,
    },
    {
        title: 'Product Code',
        description: '',
        minWidth: 130,
    },
    {
        title: 'Product Category',
        description: '',
        minWidth: 150,
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
        minWidth: 100,
    },
    {
        title: 'Quantity',
        description: '',
        minWidth: 100,
    },
    {
        title: 'Stocked',
        description: '',
        minWidth: 100,
    },
];

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

export const purchaseOrderHeaderKey: HeaderKey[] = [
    {
        title: 'Purchase Order Code',
        description: '',
        minWidth: 200,
    },
    {
        title: 'Reference Number',
        description: '',
        minWidth: 150,
    },
    {
        title: 'Supplier',
        description: '',
        minWidth: 200,
    },
    {
        title: 'To Warehouse',
        description: '',
        minWidth: 200,
    },
    {
        title: 'Status',
        description: '',
        minWidth: 200,
    },
    {
        title: 'Inbound Date',
        description: '',
        minWidth: 200,
    },
    {
        title: 'Arrival Date',
        description: '',
        minWidth: 200,
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

export const goodsReceivedHeader: HeaderKey[] = [
    {
        title: 'PO Code',
        description: '',
        minWidth: 150,
    },
    {
        title: 'Warehouse Name',
        description: '',
        minWidth: 200,
    },
    {
        title: 'Status',
        description: '',
        minWidth: 200,
    },
    {
        title: 'Employee Assigned',
        description: '',
        minWidth: 200,
    },
    {
        title: 'Received Date',
        description: '',
        minWidth: 200,
    },
    {
        title: 'Deadline to stocked',
        description: '',
        minWidth: 200,
    },
];
