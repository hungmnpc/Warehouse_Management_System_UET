import { usePermissions } from '@refinedev/core';
import { ReactNode } from 'react';
import { Route } from 'react-router-dom';

interface IPrivateRouteProps {
    authenNames: string[];

    elementFallBack: ReactNode;
    children?: ReactNode;
}

const PrivateRoute = ({ authenNames, elementFallBack, children }: IPrivateRouteProps) => {
    const { data: permissionsData } = usePermissions<string[]>();
    if (authenNames.some((role) => permissionsData?.includes(role)) || authenNames.length == 0) {
        return children;
    } else {
        return elementFallBack;
    }
};

export default PrivateRoute;
