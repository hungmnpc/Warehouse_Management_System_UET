import { Authenticated, GitHubBanner, Refine, usePermissions } from '@refinedev/core';
import { DevtoolsPanel, DevtoolsProvider } from '@refinedev/devtools';
import { RefineKbar, RefineKbarProvider } from '@refinedev/kbar';

import {
    ErrorComponent,
    notificationProvider,
    useNotificationProvider,
    RefineSnackbarProvider,
    ThemedLayoutV2,
    ThemedTitleV2,
} from '@refinedev/mui';

import CssBaseline from '@mui/material/CssBaseline';
import GlobalStyles from '@mui/material/GlobalStyles';
import routerBindings, {
    CatchAllNavigate,
    DocumentTitleHandler,
    NavigateToResource,
    UnsavedChangesNotifier,
} from '@refinedev/react-router-v6';
import { BrowserRouter, Outlet, Route, Routes } from 'react-router-dom';
import { ACCESS_TOKEN, authProvider, getResource } from './authProvider';
import { AppIcon } from './components/app-icon';
import { Header } from './components/header';
import { ColorModeContextProvider } from './contexts/color-mode';
import { BlogPostCreate, BlogPostEdit, BlogPostList, BlogPostShow } from './pages/blog-posts';
import { CategoryCreate, CategoryEdit, CategoryList, CategoryShow } from './pages/categories';
import { ForgotPassword } from './pages/forgotPassword';
import { Login } from './pages/login';
import { Register } from './pages/register';
import { routers } from './routes';
import { dataProvider } from './data-provider';
import { resources } from './resources';
import PrivateRoute from './privateRoute';
import { NotPermissionPage } from './pages/common/permissions';
import { locationProvider } from './location-provider';
import { warehouseProvider } from './dataprovider/warehouseDataProvider';
import { purchaseDataProvider } from './dataprovider/purchaseDataProvider';
import { useEffect, useState } from 'react';

const customTitleHandler = ({ resource, action, params }: any) => {
    let title = 'WMS-UET'; // Default title

    if (resource && action) {
        title = `${action.toUpperCase()} ${resource.name.toUpperCase()}  ${params.id ? params.id : ''}`;
    }

    return title;
};

function App() {
    const renderRoute = (data: any, index: number) => {
        if (data.inner) {
            return (
                <Route key={index} path={data.path}>
                    <Route
                        index
                        element={
                            <PrivateRoute authenNames={data.permission} elementFallBack={<NotPermissionPage />}>
                                {data.element}
                            </PrivateRoute>
                        }
                    />
                    {data.inner.map((inner: any, index: number) => {
                        return (
                            <Route
                                key={index}
                                path={inner.path}
                                element={
                                    <PrivateRoute authenNames={data.permission} elementFallBack={<NotPermissionPage />}>
                                        {inner.element}
                                    </PrivateRoute>
                                }
                            />
                        );
                    })}
                </Route>
            );
        } else {
            return (
                <Route
                    key={index}
                    path={data.path}
                    element={
                        <PrivateRoute authenNames={data.permission} elementFallBack={<NotPermissionPage />}>
                            {data.element}
                        </PrivateRoute>
                    }
                />
            );
        }
    };

    // let resource = permissionData?.includes("")

    const [resouces, setResources] = useState<any[]>([]);

    useEffect(() => {
        console.log('vào đây ');
        setResources(getResource());
    }, [localStorage.getItem(ACCESS_TOKEN)]);
    console.log(getResource());

    return (
        <BrowserRouter>
            <RefineKbarProvider>
                <ColorModeContextProvider>
                    <CssBaseline />
                    <GlobalStyles styles={{ html: { WebkitFontSmoothing: 'auto' } }} />
                    <RefineSnackbarProvider>
                        <Refine
                            dataProvider={{
                                default: dataProvider('http://localhost:8222'),
                                users: dataProvider('http://localhost:8222/auth'),
                                warehouses: warehouseProvider('http://localhost:8222/warehouses'),
                                products: dataProvider('http://localhost:8222/products'),
                                histories: dataProvider('http://localhost:8222/histories'),
                                inventories: dataProvider('http://localhost:8222/inventories'),
                                purchaseOrder: purchaseDataProvider('http://localhost:8222'),
                                locations: locationProvider('https://vapi.vnappmob.com/api'),
                            }}
                            notificationProvider={notificationProvider}
                            authProvider={authProvider}
                            routerProvider={routerBindings}
                            resources={resouces}
                            options={{
                                syncWithLocation: true,
                                warnWhenUnsavedChanges: true,
                                useNewQueryKeys: true,
                                projectId: 'Ig9Gm3-5fvyy4-dUoiN0',
                            }}
                        >
                            <Routes>
                                <Route
                                    element={
                                        <Authenticated
                                            key="authenticated-inner"
                                            fallback={<CatchAllNavigate to="/login" />}
                                        >
                                            <ThemedLayoutV2
                                                Header={() => <Header sticky />}
                                                Title={({ collapsed }) => (
                                                    <ThemedTitleV2
                                                        collapsed={collapsed}
                                                        text="Warehouse management system"
                                                        icon={<AppIcon />}
                                                    />
                                                )}
                                            >
                                                <Outlet />
                                            </ThemedLayoutV2>
                                        </Authenticated>
                                    }
                                >
                                    <Route index element={<NavigateToResource resource="blog_posts" />} />
                                    <Route>{routers.map((data: any, index: number) => renderRoute(data, index))}</Route>
                                </Route>
                                <Route
                                    element={
                                        <Authenticated key="authenticated-outer" fallback={<Outlet />}>
                                            <NavigateToResource />
                                        </Authenticated>
                                    }
                                >
                                    <Route path="/login" element={<Login />} />
                                    <Route path="/register" element={<Register />} />
                                    <Route path="/forgot-password" element={<ForgotPassword />} />
                                </Route>
                            </Routes>
                            <RefineKbar />
                            <UnsavedChangesNotifier />
                            <DocumentTitleHandler handler={customTitleHandler} />;
                        </Refine>
                    </RefineSnackbarProvider>
                </ColorModeContextProvider>
            </RefineKbarProvider>
        </BrowserRouter>
    );
}

export default App;
