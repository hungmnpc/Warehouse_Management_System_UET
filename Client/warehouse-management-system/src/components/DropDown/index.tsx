import {
    Button,
    Dialog,
    DialogActions,
    DialogContent,
    DialogContentText,
    DialogTitle,
    Icon,
    TextField,
    Tooltip,
    Typography,
} from '@mui/material';
import React, { FormEvent, ReactNode } from 'react';
import InfoIcon from '@mui/icons-material/Info';
import './DropDown.scss';
import { HttpError, useOne } from '@refinedev/core';

interface IDropDownOptionProps {
    option: any;
    props: any;
    children?: any;
    hasDescription?: false | boolean;
    resources?: string;
    id?: any;
    providerName?: string | any;
    isCreate?: false | boolean;
    handleOpenModal?: (initValue: string) => void;
}

export const DropDownOption = ({
    option,
    props,
    children,
    hasDescription,
    resources,
    id,
    providerName,
    isCreate,
    handleOpenModal,
}: IDropDownOptionProps) => {
    let optionNode: ReactNode;
    if (isCreate && handleOpenModal) {
        props.onClick = () => {
            handleOpenModal(option.value);
        };
    }

    if (hasDescription) {
        const { data, isLoading, isError } = useOne<any, HttpError>({
            resource: resources,
            id,
            dataProviderName: providerName,
        });

        console.log(data);
        optionNode = (
            <div {...props}>
                <div className="dropdown_option">
                    <div className="value">{children}</div>
                    <Tooltip
                        title={<div style={{ fontSize: '16px' }}>{isLoading ? 'Loading...' : data?.data}</div>}
                        placement="right-start"
                        arrow
                        className="detail"
                        // title="lorem"
                    >
                        <Icon>
                            <InfoIcon fontSize="small" />
                        </Icon>
                    </Tooltip>
                </div>
            </div>
        );
    } else {
        optionNode = (
            <div {...props}>
                <div className="dropdown_option">
                    <div className="value">{children}</div>
                </div>
            </div>
        );
    }

    function handleSubmit(event: FormEvent<HTMLFormElement>): void {
        console.log(event);
        throw new Error('Function not implemented.');
    }

    return <>{optionNode}</>;
};
