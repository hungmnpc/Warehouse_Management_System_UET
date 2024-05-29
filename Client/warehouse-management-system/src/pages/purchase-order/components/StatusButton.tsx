import { Button } from '@mui/material';
import { PurchaseOrderStatus } from '../status';
import React from 'react';

interface StatusButtonProp {
    currentStatus: string | any;
    onClick: (...prop: any) => void;
}

export const StatusButton = ({ currentStatus, onClick }: StatusButtonProp) => {
    const statuses = Object.entries(PurchaseOrderStatus);

    const [nextStatus, setNextStatus] = React.useState<any | null>({});

    const handleClick = (status: String) => {
        onClick(status);
    };

    const renderButton = () => {
        console.log('currentStatus', currentStatus);
        switch (currentStatus) {
            case 'Draft':
                return (
                    <Button
                        style={{ textTransform: 'none' }}
                        disabled={false}
                        onClick={() => handleClick('INCOMING')}
                        variant="contained"
                    >
                        Submit
                    </Button>
                );
            case 'Incoming':
                return (
                    <Button
                        style={{ textTransform: 'none' }}
                        disabled={false}
                        onClick={() => handleClick('RECEIVED_AND_REQUIRES_WAREHOUSING')}
                        variant="contained"
                    >
                        Received
                    </Button>
                );
            default:
                break;
        }
    };

    return renderButton();
};
