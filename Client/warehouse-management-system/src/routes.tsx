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
        permission: ['ROLE_ADMIN', 'ROLE_SUPER_ADMIN'],
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
        path: '/categories',
        element: <CategoryList />,
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
        path: '*',
        permission: [],
        element: <NotFoundPage />,
    },
];
