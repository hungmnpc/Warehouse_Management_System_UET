import PermContactCalendarIcon from '@mui/icons-material/PermContactCalendar';
import WarehouseIcon from '@mui/icons-material/Warehouse';
import CategoryIcon from '@mui/icons-material/Category';
import Inventory2Icon from '@mui/icons-material/Inventory2';
import ListAltIcon from '@mui/icons-material/ListAlt';
import TripOriginIcon from '@mui/icons-material/TripOrigin';

export const resources = [
    {
        name: 'users',
        list: '/users',
        create: '/users/create',
        edit: '/users/edit/:id',
        show: '/users/show/:id',
        meta: {
            canDelete: true,
            icon: <PermContactCalendarIcon />,
        },
    },
    {
        name: 'warehouses',
        list: '/warehouses',
        create: '/warehouses/create',
        edit: '/warehouses/edit/:id',
        show: '/warehouses/show/:id',
        meta: {
            canDelete: true,
            icon: <WarehouseIcon />,
        },
        parentName: 'Warehouse Management',
    },
    {
        name: 'Warehouse Types',
        list: '/warehouse_types',
        create: '/warehouse_types/create',
        edit: '/warehouse_types/edit/:id',
        show: '/warehouse_types/show/:id',
        meta: {
            canDelete: true,
            icon: <CategoryIcon />,
        },
        parentName: 'Warehouse Management',
    },

    {
        name: 'Product',
        list: '/products',
        create: '/products/create',
        edit: '/products/edit/:id',
        show: '/products/show/:id',
        meta: {
            canDelete: true,
            icon: <Inventory2Icon />,
        },
        parentName: 'Products Management',
    },
    {
        name: 'Category',
        list: '/products/categories',
        create: '/products/categories/create',
        edit: '/products/categories/edit/:id',
        show: '/products/categories/show/:id',
        meta: {
            canDelete: true,
            icon: <CategoryIcon />,
        },
        parentName: 'Products Management',
    },
    {
        name: 'Products Management',
        meta: {
            canDelete: true,
            icon: <CategoryIcon />,
        },
    },
    {
        name: 'Purchase Order',
        list: '/purchase_orders',
        create: '/purchase_orders/create',
        edit: '/purchase_orders/edit/:id',
        show: '/purchase_orders/show/:id',
        meta: {
            canDelete: true,
            icon: <ListAltIcon />,
        },
    },
    {
        name: 'Supplier',
        list: '/supplier',
        create: '/supplier/create',
        edit: '/supplier/edit/:id',
        show: '/supplier/show/:id',
        meta: {
            canDelete: true,
            icon: <TripOriginIcon />,
        },
    },
];

export const resourcesWHM = [
    {
        name: 'users',
        list: '/users',
        create: '/users/create',
        edit: '/users/edit/:id',
        show: '/users/show/:id',
        meta: {
            canDelete: true,
            icon: <PermContactCalendarIcon />,
        },
    },
    {
        name: 'warehouses',
        list: '/warehouses',
        create: '/warehouses/create',
        edit: '/warehouses/edit/:id',
        show: '/warehouses/show/:id',
        meta: {
            canDelete: true,
            icon: <WarehouseIcon />,
        },
        parentName: 'Warehouse Management',
    },
    {
        name: 'Product',
        list: '/products',
        create: '/products/create',
        edit: '/products/edit/:id',
        show: '/products/show/:id',
        meta: {
            canDelete: true,
            icon: <Inventory2Icon />,
        },
        parentName: 'Products Management',
    },
    {
        name: 'Category',
        list: '/products/categories',
        create: '/products/categories/create',
        edit: '/products/categories/edit/:id',
        show: '/products/categories/show/:id',
        meta: {
            canDelete: true,
            icon: <CategoryIcon />,
        },
        parentName: 'Products Management',
    },
    {
        name: 'Products Management',
        meta: {
            canDelete: true,
            icon: <CategoryIcon />,
        },
    },
    {
        name: 'Purchase Order',
        list: '/purchase_orders',
        create: '/purchase_orders/create',
        edit: '/purchase_orders/edit/:id',
        show: '/purchase_orders/show/:id',
        meta: {
            canDelete: true,
            icon: <ListAltIcon />,
        },
    },
    {
        name: 'Supplier',
        list: '/supplier',
        create: '/supplier/create',
        edit: '/supplier/edit/:id',
        show: '/supplier/show/:id',
        meta: {
            canDelete: true,
            icon: <TripOriginIcon />,
        },
    },
];
