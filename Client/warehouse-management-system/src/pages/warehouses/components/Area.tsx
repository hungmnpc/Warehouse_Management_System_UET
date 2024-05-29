import { useEffect, useState } from 'react';
import { getAllAreaWarehouse } from '../../../utils/request';
import { AreaCard } from './AreaCard';
import Grid2 from '@mui/material/Unstable_Grid2/Grid2';
import { Dialog, DialogTitle, DialogContent, Typography, IconButton, Slide, Box, Tab } from '@mui/material';
import CloseIcon from '@mui/icons-material/Close';
import classNames from 'classnames/bind';
import styles from '../WarehouseShow.module.scss';
import { AreaInfo } from './AreaInfo';
import React from 'react';
import { TransitionProps } from '@mui/material/transitions';

interface AreaWarehouseProps {
    warehouseId: any;
    triggerRefetch: boolean;
}

const cx = classNames.bind(styles);

const Transition = React.forwardRef(function Transition(
    props: TransitionProps & {
        children: React.ReactElement<any, any>;
    },
    ref: React.Ref<unknown>,
) {
    return <Slide direction="up" ref={ref} {...props} />;
});

export const AreaWarehouse = ({ warehouseId, triggerRefetch }: AreaWarehouseProps) => {
    const [areas, setAreas] = useState<any[]>([]);

    const getArea = () => {
        getAllAreaWarehouse(warehouseId)
            .then((response) => {
                console.log('area', response);
                if (response.success) {
                    setAreas(response.data);
                }
            })
            .catch((error) => console.error(error));
    };

    useEffect(() => {
        if (warehouseId) {
            getArea();
        }
    }, [warehouseId, triggerRefetch]);

    const [selectedArea, setSelectedArea] = useState<any>(null);

    const [open, setOpen] = useState<boolean>(false);

    const handleCardClick = (area: any) => {
        setSelectedArea(area);
        setOpen(true);
    };

    const handleModalClose = () => {
        setSelectedArea(null);
        setOpen(false);
    };

    return (
        <div>
            <Grid2 spacing={4} container>
                {areas.map((area, index) => (
                    <Grid2 xs={6} key={index}>
                        <AreaCard onClick={() => handleCardClick(area)} areaData={area} />
                    </Grid2>
                ))}
            </Grid2>

            <Dialog TransitionComponent={Transition} fullScreen open={open} onClose={handleModalClose}>
                {selectedArea && (
                    <>
                        <DialogTitle className={cx('dialog_title')}>
                            {selectedArea.areaName} ({selectedArea.areaPrefix})
                            <IconButton onClick={handleModalClose}>
                                <CloseIcon />
                            </IconButton>
                        </DialogTitle>
                        <DialogContent>
                            <AreaInfo areaData={selectedArea} />
                        </DialogContent>
                    </>
                )}
            </Dialog>
        </div>
    );
};
