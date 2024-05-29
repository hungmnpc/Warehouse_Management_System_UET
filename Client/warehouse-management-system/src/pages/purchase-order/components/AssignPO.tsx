import { Button, Dialog, DialogActions, DialogTitle, IconButton, TextField } from '@mui/material';
import { EmployeeCard } from './EmployeeCard';
import { useEffect, useState } from 'react';
import { useList } from '@refinedev/core';
import { IUser } from '../../../utils/interfaces';
import { Box, display } from '@mui/system';
import CloseIcon from '@mui/icons-material/Close';
import { assignEmployeeToPo } from '../../../utils/request';

interface StatusButtonProp {
    currentStatus: string | any;
    poId: any;
    isAssigned?: boolean;
    handleSublit: (...params: any) => any;
}

export const AssignPO = ({ currentStatus, poId, isAssigned = false, handleSublit }: StatusButtonProp) => {
    const [selectedEmployees, setSelectedEmployees] = useState<string | null>(null);

    const [open, setOpen] = useState<boolean>(false);
    const [usernameSearch, setUserNameSearch] = useState<string>('');
    const [doRefetch, setDoRefetch] = useState(true);

    const handleClose = () => {
        setOpen(false);
    };

    const { data, isLoading } = useList<IUser>({
        dataProviderName: 'users',
        resource: 'users',
        pagination: {
            current: 0,
            pageSize: 9999999,
        },
        filters: [
            {
                field: 'username',
                value: usernameSearch,
                operator: 'eq',
            },
        ],
        queryOptions: {
            cacheTime: 0,
            enabled: doRefetch,
        },
    });

    const handleSelectEmployee = (id: string, isSelected: boolean) => {
        setSelectedEmployees((prevSelected) => {
            if (isSelected) {
                return id;
            } else {
                return null;
            }
        });
    };
    const renderButton = () => {
        console.log(currentStatus);
        switch (currentStatus) {
            case 'Draft':
            case 'Incoming':
            case 'Received and Requires Warehousing':
                return (
                    <Button
                        onClick={() => {
                            setOpen(true);
                        }}
                        style={{ textTransform: 'none' }}
                        disabled={false}
                        variant="contained"
                    >
                        {isAssigned ? 'Change assigned to' : 'Assigned to'}
                    </Button>
                );
            case 'Warehousing':
            case 'Stocked':
                return (
                    <Button style={{ textTransform: 'none', display: 'none' }} disabled={false} variant="contained">
                        Received
                    </Button>
                );
            default:
                break;
        }
    };

    function handleSubmitEmployee(event: any): void {
        console.log(selectedEmployees);
        console.log(poId);
        handleSublit(poId, selectedEmployees, () => {
            setOpen(false);
        });
        // assignEmployeeToPo(poId, selectedEmployees)
        //     .then((response: any) => {
        //         console.log(response);
        //     })
        //     .catch((error) => console.error(error));
    }

    return (
        <>
            {renderButton()}
            <Dialog fullWidth onClose={handleClose} open={open}>
                <DialogTitle>Assign to employee</DialogTitle>
                <IconButton
                    aria-label="close"
                    onClick={handleClose}
                    sx={{
                        position: 'absolute',
                        right: 8,
                        top: 8,
                        color: (theme) => theme.palette.grey[500],
                    }}
                >
                    <CloseIcon />
                </IconButton>

                <div
                    style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', flexDirection: 'column' }}
                >
                    <Box maxWidth={400} mb={2} display="flex">
                        <TextField
                            fullWidth
                            variant="outlined"
                            placeholder="Product name..."
                            value={usernameSearch}
                            onChange={(e) => {
                                // console.log('message zz', messages);
                                if (e.target.value == '') {
                                    setDoRefetch(false);
                                }
                                setUserNameSearch(e.target.value);
                            }}
                            onKeyPress={(e) => {
                                console.log(e);
                                setDoRefetch(false);
                                if (e.key === 'Enter') {
                                    // sendNote();
                                    // refetch();
                                    setDoRefetch(true);
                                }
                            }}
                        />
                        <Button
                            variant="contained"
                            color="primary"
                            onClick={() => setDoRefetch(true)}
                            style={{ marginLeft: '8px' }}
                        >
                            Search
                        </Button>
                    </Box>
                    {data &&
                        data.data.map((employee: any, index: any) => (
                            <EmployeeCard
                                selected={employee.id === selectedEmployees}
                                key={employee.id}
                                employee={employee}
                                onSelect={handleSelectEmployee}
                            />
                        ))}
                </div>
                <DialogActions>
                    <Button
                        disabled={selectedEmployees == null}
                        variant="contained"
                        autoFocus
                        onClick={handleSubmitEmployee}
                    >
                        Save
                    </Button>
                </DialogActions>
            </Dialog>
        </>
    );
};
