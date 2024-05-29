import { Show } from '@refinedev/mui';
import classNames from 'classnames/bind';
import style from './WarehouseShow.module.scss';
import Grid2 from '@mui/material/Unstable_Grid2/Grid2';
import Timeline from '../../components/timeline/index';
import { Button, Card, CardActions, CardContent, IconButton, List, Typography } from '@mui/material';
import { HttpError, useCreate, useList, useOne, useResource } from '@refinedev/core';
import { createContext, useEffect, useState } from 'react';
import { getCountGrWarehouse, getCountPOWarehouse, getHistory } from '../../utils/request';
import { IWarehouse } from '../../utils/interfaces';
import NorthEastIcon from '@mui/icons-material/NorthEast';
import { Link } from 'react-router-dom';
import ChatUI from '../../components/communication/chatui';
import { Box, positions } from '@mui/system';
import { AreaWarehouse } from './components/Area';
import { CreateAreaDialog } from './components/CreateAreaDialog';
import { ProductsInWarehouse } from './components/ProductsInWarehouse';
import { randomUUID } from 'crypto';
import { NotFoundPage } from '../common/notFoundPage';

const cx = classNames.bind(style);

export type WarehouseContent = {
    warehouseId: any;
    warehouseName: string;
};

export const WarehouseContext = createContext<WarehouseContent>({
    warehouseId: null,
    warehouseName: 'no name',
});
export const WarehouseShow = () => {
    const { resources, resource, action, id } = useResource();
    const [trigger, setTrigger] = useState<boolean>(false);
    const [histories, setHistories] = useState([]);

    const { data, isLoading, isError } = useOne<IWarehouse, HttpError>({
        resource: 'warehouses',
        id,
    });

    const [poCount, setpoCount] = useState<any>({
        countAllPurchaseOrder: 0,
        countDraftPurchaseOrder: 0,
    });

    const [grCount, setGrCount] = useState<any>({
        all: 0,
        handle: 0,
    });

    const getCountPoWarehouse = () => {
        getCountPOWarehouse(id)
            .then((response) => {
                console.log('response', response);
                setpoCount({
                    countAllPurchaseOrder: response.data.countAllPurchaseOrder,
                    countDraftPurchaseOrder: response.data.countDraftPurchaseOrder,
                });
            })
            .catch((error) => console.error(error));
    };

    const getCountGRWarehouse = () => {
        getCountGrWarehouse(id)
            .then((response) => {
                console.log('response', response);
                setGrCount({
                    all: response.data.total,
                    handle: response.data.needHandle,
                });
            })
            .catch((error) => console.error(error));
    };

    useEffect(() => {
        getCountPoWarehouse();
        getCountGRWarehouse();
    }, []);

    useEffect(() => {
        getHistory('warehouse', id)
            .then((data) => {
                console.log('data his', data);
                setHistories(data);
            })
            .catch((data) => {
                console.log(data);
            });
    }, []);

    console.log(histories);

    const [openCreateArea, setOpenCreateArea] = useState<boolean>(false);

    const handleCloseCreateArea = () => {
        setOpenCreateArea(false);
    };

    const handleOpenCreateArea = () => {
        setOpenCreateArea(true);
    };

    const { mutate } = useCreate();

    const handleCreateNewArea = (value: any) => {
        if (value) {
            mutate(
                {
                    values: value,
                    resource: `warehouses/${id}/areas`,
                    successNotification(data, values, resource) {
                        return {
                            message: `Successfully created new area`,
                            description: 'Success with no errors',
                            type: 'success',
                        };
                    },
                    errorNotification(error, values, resource) {
                        console.log(error);
                        return {
                            message: `Error when created area`,
                            description: 'Some thing wrong',
                            type: 'error',
                        };
                    },
                },
                {
                    onSuccess(data, variables, context) {
                        setTrigger((prev) => !prev);
                        setOpenCreateArea(false);
                    },
                },
            );
        }
    };

    return (
        <Show
            isLoading={isLoading}
            canDelete={false}
            canEdit={false}
            headerButtons={({ defaultButtons }) => (
                <>
                    {defaultButtons}
                    <Button onClick={handleOpenCreateArea} variant="contained">
                        New Area
                    </Button>
                    {/* <Button onClick={handleOpenCreateArea} variant="contained">
                        Products In Warehouse
                    </Button> */}
                </>
            )}
        >
            {!data?.data ? (
                <NotFoundPage />
            ) : (
                <WarehouseContext.Provider
                    value={{ warehouseId: data.data.id, warehouseName: data.data.warehouseName }}
                >
                    <div className={cx('wrapper')}>
                        <Grid2 gap={0} container columns={24}>
                            <Grid2 container spacing={1} columns={12} className={cx('main-content')} xs={16}>
                                {/* <Grid2 direction="row" xs={21} columns={12}> */}
                                <Grid2 height={'auto'} xs={6}>
                                    <Card sx={{ minWidth: 275 }}>
                                        <CardContent>
                                            <div className={cx('card-header')}>
                                                <span className={cx('size40')}>{grCount.all}</span>
                                                <Link to={`/warehouses/${id}/goods-received`}>
                                                    <IconButton
                                                        style={{
                                                            width: 50,
                                                            height: 50,
                                                            borderRadius: '50%',
                                                            boxShadow: '0 24px 48px rgba(134, 144, 162, 0.4)',
                                                        }}
                                                        size="large"
                                                    >
                                                        <NorthEastIcon />
                                                    </IconButton>
                                                </Link>
                                            </div>
                                            <span>{grCount.handle} requests need to be handled</span>
                                            <br />
                                            <span className={cx('type')}>Goods received note</span>
                                        </CardContent>
                                    </Card>
                                </Grid2>
                                {/* <Grid2 xs={4}>
                                        <Card sx={{ minWidth: 275 }}>
                                            <CardContent>
                                                <div className={cx('card-header')}>
                                                    <span className={cx('size40')}>{grCount.all}</span>
                                                    <Link to={`/warehouses/${id}/goods-received`}>
                                                        <IconButton
                                                            style={{
                                                                width: 50,
                                                                height: 50,
                                                                borderRadius: '50%',
                                                                boxShadow: '0 24px 48px rgba(134, 144, 162, 0.4)',
                                                            }}
                                                            size="large"
                                                        >
                                                            <NorthEastIcon />
                                                        </IconButton>
                                                    </Link>
                                                </div>
                                                <span>{grCount.handle} requests need to be handled</span>
                                                <br />
                                                <span className={cx('type')}>Goods delivery note</span>
                                            </CardContent>
                                        </Card>
                                    </Grid2> */}
                                <Grid2 height={'auto'} xs={6}>
                                    <Card sx={{ minWidth: 275 }}>
                                        <CardContent>
                                            <div className={cx('card-header')}>
                                                <span className={cx('size40')}>{poCount.countAllPurchaseOrder}</span>
                                                <Link to={`/purchase_orders?warehouseId=${id}`}>
                                                    <IconButton
                                                        style={{
                                                            width: 50,
                                                            height: 50,
                                                            borderRadius: '50%',
                                                            boxShadow: '0 24px 48px rgba(134, 144, 162, 0.4)',
                                                        }}
                                                        size="large"
                                                    >
                                                        <NorthEastIcon />
                                                    </IconButton>
                                                </Link>
                                            </div>
                                            <span>
                                                {poCount.countDraftPurchaseOrder}{' '}
                                                {poCount.countDraftPurchaseOrder > 1 ? 'POs' : 'PO'} need to be handled
                                            </span>
                                            <br />
                                            <span className={cx('type')}>Purchase Order</span>
                                        </CardContent>
                                    </Card>
                                </Grid2>
                                {/* </Grid2> */}
                                <Grid2 xs={12} mt={3}>
                                    <Typography variant="h5">Area</Typography>
                                    <Box sx={{ width: 1 }}>
                                        <AreaWarehouse triggerRefetch={trigger} warehouseId={id} />
                                    </Box>
                                </Grid2>
                            </Grid2>
                            <Grid2 xs={8}>
                                <ChatUI
                                    refetch={() => getHistory('warehouse', id)}
                                    agentId={id}
                                    agentType="warehouse"
                                    data={histories}
                                    title="Warehouse"
                                />
                            </Grid2>
                        </Grid2>
                    </div>

                    <ProductsInWarehouse warehouseId={id} />

                    <CreateAreaDialog
                        handleSubmitDialog={handleCreateNewArea}
                        open={openCreateArea}
                        handleClose={handleCloseCreateArea}
                    />
                </WarehouseContext.Provider>
            )}
        </Show>
    );
};
