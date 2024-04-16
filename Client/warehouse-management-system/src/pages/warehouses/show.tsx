import { Show } from '@refinedev/mui';
import classNames from 'classnames/bind';
import style from './WarehouseShow.module.scss';
import Grid2 from '@mui/material/Unstable_Grid2/Grid2';
import Timeline from '../../components/timeline/index';
import { Button, Card, CardActions, CardContent, IconButton, Typography } from '@mui/material';
import { HttpError, useList, useOne, useResource } from '@refinedev/core';
import { useEffect, useState } from 'react';
import { getHistory } from '../../utils/request';
import { IWarehouse } from '../../utils/interfaces';
import NorthEastIcon from '@mui/icons-material/NorthEast';
import { Link } from 'react-router-dom';

const events = [
    { ts: '2017-09-17T12:22:46.587Z', text: 'Logged in' },
    { ts: '2017-09-17T12:21:46.587Z', text: 'Clicked Home Page' },
    { ts: '2017-09-17T12:20:46.587Z', text: 'Edited Profile' },
    { ts: '2017-09-16T12:22:46.587Z', text: 'Registred' },
    { ts: '2017-09-16T12:21:46.587Z', text: 'Clicked Cart' },
    { ts: '2017-09-16T12:20:46.587Z', text: 'Clicked Checkout' },
];

const cx = classNames.bind(style);
export const WarehouseShow = () => {
    const { resources, resource, action, id } = useResource();
    const [histories, setHistories] = useState([]);

    const { data, isLoading, isError } = useOne<IWarehouse, HttpError>({
        resource: 'warehouses',
        id,
    });

    console.log('data', data);

    useEffect(() => {
        getHistory('warehouse', id)
            .then((data) => {
                console.log(data);
                setHistories(
                    data.map((data: any) => {
                        return {
                            ts: new Date(data.ts * 1000 + 0).toISOString(),
                            text: data.title,
                            description: data.description,
                        };
                    }),
                );
            })
            .catch((data) => {
                console.log(data);
            });
    }, []);

    console.log(histories);

    return (
        <Show canDelete={false} canEdit={false}>
            <div className={cx('wrapper')}>
                <Grid2 container columns={24}>
                    <Grid2 container spacing={4} className={cx('main-content')} xs={17}>
                        <Grid2 xs={7}>
                            <Card sx={{ minWidth: 275 }}>
                                <CardContent>
                                    <div className={cx('card-header')}>
                                        <span className={cx('size40')}>10</span>
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
                                    </div>
                                    <span>6 requests need to be handled</span>
                                    <br />
                                    <span className={cx('type')}>Goods received note</span>
                                </CardContent>
                            </Card>
                        </Grid2>
                        <Grid2 xs={7}>
                            <Card sx={{ minWidth: 275 }}>
                                <CardContent>
                                    <div className={cx('card-header')}>
                                        <span className={cx('size40')}>20</span>
                                        <Link to={'/404'}>
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
                                    <span>5 requests need to be handled</span>
                                    <br />
                                    <span className={cx('type')}>Goods delivery note</span>
                                </CardContent>
                            </Card>
                        </Grid2>
                    </Grid2>
                    <Grid2 xs={7}>
                        <h2>Warehouse timeline</h2>
                        <div className={cx('timeline')}>
                            <Timeline items={histories} />
                        </div>
                    </Grid2>
                </Grid2>
            </div>
        </Show>
    );
};
