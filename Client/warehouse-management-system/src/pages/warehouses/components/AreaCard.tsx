import { useEffect, useState } from 'react';
import { getAllAisleInArea } from '../../../utils/request';
import { Aisle } from './Aisle';
import { Card, CardContent, Typography } from '@mui/material';
import { Box, useTheme } from '@mui/system';

interface AreaComponentProps {
    areaData: any;
    onClick: ({ ...rest }) => any;
}

export const AreaCard = ({ areaData, onClick }: AreaComponentProps) => {
    return (
        <Card
            onClick={onClick}
            sx={{
                height: 200,
                cursor: 'pointer',
                boxShadow:
                    'rgba(50, 50, 93, 0.25) 0px 50px 100px -20px, rgba(0, 0, 0, 0.3) 0px 30px 60px -30px, rgba(10, 37, 64, 0.35) 0px -2px 6px 0px inset',
                transition: 'box-shadow 0.3s ease-in-out', // Hiệu ứng chuyển tiếp mượt mà
                '&:hover': {
                    boxShadow: '0 8px 16px 0 rgba(255, 0, 0, 0.2)', // Shadow màu đỏ khi hover
                },
            }}
        >
            <CardContent>
                <Typography variant="h6">
                    {areaData.areaName} ({areaData.areaPrefix})
                </Typography>
            </CardContent>
        </Card>
    );
};
{
    /* <Box key={areaData.areaName} mb={3}>
    {aisles.map((aisle, index) => (
        <Aisle aisleData={aisle} key={index} />
    ))}
</Box> */
}
