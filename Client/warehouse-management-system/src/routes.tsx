import { ErrorComponent } from "@refinedev/core";
import { BlogPostCreate, BlogPostEdit, BlogPostList, BlogPostShow } from "./pages/blog-posts";
import { CategoryCreate, CategoryEdit, CategoryList, CategoryShow } from "./pages/categories";

export const routers = [
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