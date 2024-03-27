import { ErrorComponent } from "@refinedev/core";
import { BlogPostCreate, BlogPostEdit, BlogPostList, BlogPostShow } from "./pages/blog-posts";
import { CategoryCreate, CategoryEdit, CategoryList, CategoryShow } from "./pages/categories";
import { UserList } from "./pages/users";
import { UserCreate } from "./pages/users/create";
import { UserEdit } from "./pages/users/edit";

export const routers = [
    {
        path: "/users",
        element: <UserList />,
        inner: [
            {
                path: 'create',
                element: <UserCreate />
            },
            {
                path: 'edit/:id',
                element: <UserEdit />
            },
            {
                path: 'show/:id',
                element: <BlogPostShow />
            }, 
        ]
    },
    {
        path: "/blog-posts",
        element: <BlogPostList />,
        inner: [
            {
                path: 'create',
                element: <BlogPostCreate />
            },
            {
                path: 'edit/:id',
                element: <BlogPostEdit />
            },
            {
                path: 'show/:id',
                element: <BlogPostShow />
            }, 
        ]
    },
    {
        path: "/categories",
        element: <CategoryList />,
        inner: [
            {
                path: 'create',
                element: <CategoryCreate />
            },
            {
                path: 'edit/:id',
                element: <CategoryEdit />
            },
            {
                path: 'show/:id',
                element: <CategoryShow />
            }, 
        ]
    },
    {
        path: "*",
        element: <ErrorComponent />,
    }
]