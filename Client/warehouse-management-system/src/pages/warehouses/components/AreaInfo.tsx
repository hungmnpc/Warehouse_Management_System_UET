import { useEffect, useState } from 'react';
import { getAllAisleInArea } from '../../../utils/request';
import { Aisle } from './Aisle';
import { Button, Card, CardContent, Typography } from '@mui/material';
import { Box, useTheme } from '@mui/system';
import { CreataeAisleDialog } from './CreateAisleDialog';
import { useCreate } from '@refinedev/core';

interface AreaInfoProps {
    areaData: any;
}

export const AreaInfo = ({ areaData }: AreaInfoProps) => {
    const theme = useTheme();
    const [aisles, setAisles] = useState<any[]>([]);

    const getAllAisle = () => {
        getAllAisleInArea(areaData.id)
            .then((response) => {
                console.log('aisle', response);
                setAisles(response.data);
            })
            .catch((error) => {
                console.error(error);
            });
    };

    useEffect(() => {
        if (areaData.id) {
            getAllAisle();
        }
    }, [areaData.id]);

    const [openCreatAisle, setOpenCreatAisle] = useState<boolean>(false);

    const handleOpenCreateAisle = () => {
        setOpenCreatAisle(true);
    };

    const handleCloseCreateAisle = () => {
        setOpenCreatAisle(false);
    };

    const { mutate } = useCreate();

    const handleSubmitNewAisle = (value: any) => {
        console.log(areaData);
        console.log(value);
        if (value) {
            mutate(
                {
                    values: value,
                    resource: `warehouses/areas/${areaData.id}/aisles`,
                    successNotification(data, values, resource) {
                        return {
                            message: `Successfully created new aisle.`,
                            description: 'Success with no errors',
                            type: 'success',
                        };
                    },
                    errorNotification(error: any, values, resource) {
                        console.log('erorr', error);
                        return {
                            message: `Unsuccessfully created new aisle`,
                            description: error.response.data.result.message,
                            type: 'error',
                        };
                    },
                },
                {
                    onSuccess(data, variables, context) {
                        setOpenCreatAisle(false);
                        getAllAisle();
                    },
                },
            );
        }
    };

    return (
        <Box key={areaData.areaName} mb={3}>
            <Button onClick={handleOpenCreateAisle} variant="contained">
                Create Aisle
            </Button>
            {aisles.map((aisle, index) => (
                <Aisle aisleData={aisle} key={index} />
            ))}
            {aisles.length == 0 && <p>No Aisle to display</p>}
            <CreataeAisleDialog
                open={openCreatAisle}
                handleClose={handleCloseCreateAisle}
                handleSubmitDialog={handleSubmitNewAisle}
            />
        </Box>
    );
};
