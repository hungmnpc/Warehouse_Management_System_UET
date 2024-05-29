import { ErrorComponent } from '@refinedev/core';
import { BlogPostCreate, BlogPostEdit, BlogPostList, BlogPostShow } from './pages/blog-posts';
import { CategoryCreate, CategoryEdit, CategoryList, CategoryShow } from './pages/categories';
import { UserList } from './pages/users';
import { UserCreate } from './pages/users/create';
import { UserEdit } from './pages/users/edit';
import { WarehouseList } from './pages/warehouses/list';
import { WarehouseCreate } from './pages/warehouses/create';
import { NotFoundPage } from './pages/common/notFoundPage';
import { WarehouseEdit } from './pages/warehouses';
import { WarehouseShow } from './pages/warehouses/show';
import { ProductCreate, ProductEdit, ProductList } from './pages/products';
import { roles } from './constant';
import { ProductShow } from './pages/products/show';
import { ImportStockList } from './pages/import-pages';
import { SupplierList } from './pages/supplier';
import { SupplierCreate } from './pages/supplier/create';
import { EditSupplier } from './pages/supplier/edit';
import { PurchaseOrderList } from './pages/purchase-order/list';
import { PurchaseOrderShow } from './pages/purchase-order/show';
import { PurchaseOrderCreate } from './pages/purchase-order/create';

export const routers = [
    {
        path: '/users',
        element: <UserList />,
        permission: ['ROLE_ADMIN', 'ROLE_SUPER_ADMIN', 'ROLE_WAREHOUSE_MANAGER'],
        inner: [
            {
                path: 'create',
                element: <UserCreate />,
            },
            {
                path: 'edit/:id',
                element: <UserEdit />,
            },
            {
                path: 'show/:id',
                element: <BlogPostShow />,
            },
        ],
    },
    {
        path: '/warehouses',
        element: <WarehouseList />,
        permission: ['ROLE_ADMIN', 'ROLE_SUPER_ADMIN', roles.warehouseManager],
        inner: [
            {
                path: 'create',
                element: <WarehouseCreate />,
            },
            {
                path: 'edit/:id',
                element: <WarehouseEdit />,
            },
            {
                path: 'show/:id',
                element: <WarehouseShow />,
            },
        ],
    },
    {
        path: '/products',
        element: <ProductList />,
        permission: [roles.admin, roles.superAdmin, roles.warehouseManager],
        inner: [
            {
                path: 'create',
                element: <ProductCreate />,
            },
            {
                path: 'edit/:id',
                element: <ProductEdit />,
            },
            {
                path: 'show/:id',
                element: <ProductShow />,
            },
        ],
    },
    {
        path: '/blog-posts',
        element: <BlogPostList />,
        inner: [
            {
                path: 'create',
                element: <BlogPostCreate />,
            },
            {
                path: 'edit/:id',
                element: <BlogPostEdit />,
            },
            {
                path: 'show/:id',
                element: <BlogPostShow />,
            },
        ],
    },
    {
        path: '/products/categories',
        element: <CategoryList />,
        permission: [],
        inner: [
            {
                path: 'create',
                element: <CategoryCreate />,
            },
            {
                path: 'edit/:id',
                element: <CategoryEdit />,
            },
            {
                path: 'show/:id',
                element: <CategoryShow />,
            },
        ],
    },
    {
        path: '/warehouses/:id/goods-received',
        element: <ImportStockList />,
        permission: [],
        inner: [
            {
                path: 'create',
                element: <CategoryCreate />,
            },
            {
                path: 'edit/:id',
                element: <CategoryEdit />,
            },
            {
                path: 'show/:id',
                element: <CategoryShow />,
            },
        ],
    },
    {
        path: '/supplier',
        element: <SupplierList />,
        permission: [],
        inner: [
            {
                path: 'create',
                element: <SupplierCreate />,
            },
            {
                path: 'edit/:id',
                element: <EditSupplier />,
            },
            {
                path: 'show/:id',
                element: <CategoryShow />,
            },
        ],
    },
    {
        path: '/purchase_orders',
        element: <PurchaseOrderList />,
        permission: [],
        inner: [
            {
                path: 'create',
                element: <PurchaseOrderCreate />,
            },
            {
                path: 'edit/:id',
                element: <CategoryEdit />,
            },
            {
                path: 'show/:id',
                element: <PurchaseOrderShow />,
            },
        ],
    },
    {
        path: '*',
        permission: [],
        element: <NotFoundPage />,
    },
];
