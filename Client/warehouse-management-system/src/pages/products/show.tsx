import { Show } from '@refinedev/mui';
import classNames from 'classnames/bind';
import style from './Product.module.scss';
import Grid2 from '@mui/material/Unstable_Grid2/Grid2';
import Timeline from '../../components/timeline/index';
import { Button } from '@mui/material';

const events = [
    { ts: '2017-09-17T12:22:46.587Z', text: 'Logged in' },
    { ts: '2017-09-17T12:21:46.587Z', text: 'Clicked Home Page' },
    { ts: '2017-09-17T12:20:46.587Z', text: 'Edited Profile' },
    { ts: '2017-09-16T12:22:46.587Z', text: 'Registred' },
    { ts: '2017-09-16T12:21:46.587Z', text: 'Clicked Cart' },
    { ts: '2017-09-16T12:20:46.587Z', text: 'Clicked Checkout' },
];

const cx = classNames.bind(style);
export const ProductShow = () => {
    return <Show canDelete={false} canEdit={false}></Show>;
};
