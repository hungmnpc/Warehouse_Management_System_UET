import { useDelete, useList, useParsed, usePermissions, useResource } from '@refinedev/core';
import { List } from '@refinedev/mui';
import { IProduct, ISupplier, IUser, IWarehouse } from '../../utils/interfaces';
import React, { useRef, useState } from 'react';
import HelpIcon from '@mui/icons-material/Help';
import QrCodeIcon from '@mui/icons-material/QrCode';
import {
    Box,
    Button,
    Dialog,
    DialogTitle,
    FormControl,
    IconButton,
    InputLabel,
    MenuItem,
    Paper,
    Select,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableFooter,
    TableHead,
    TableRow,
    TextField,
    Theme,
    Tooltip,
    createStyles,
    tableCellClasses,
} from '@mui/material';
import { StickyStyledTableCell, StyledTableCell } from '../users';
import { TableRowsLoader } from '../../components/skeleton';
import { DeleteButtonIcon } from '../../components/buttons/button';
import { Link } from 'react-router-dom';
import { CustomTablePagination } from '../../components/table';
import TablePaginationActions from '@mui/material/TablePagination/TablePaginationActions';
import FirstPageRoundedIcon from '@mui/icons-material/FirstPageRounded';
import LastPageRoundedIcon from '@mui/icons-material/LastPageRounded';
import ChevronLeftRoundedIcon from '@mui/icons-material/ChevronLeftRounded';
import ChevronRightRoundedIcon from '@mui/icons-material/ChevronRightRounded';
import InfoIcon from '@mui/icons-material/Info';
import Info from '@mui/icons-material/Info';
import EditIcon from '@mui/icons-material/Edit';
import VisibilityIcon from '@mui/icons-material/Visibility';
import { useNavigation } from '@refinedev/core';
import styled from '@emotion/styled';
import { productHeaderTableKey, purchaseOrderHeaderKey, roles, supplierHeaderTableKey } from '../../constant';
import { UUID } from 'crypto';
import SettingsIcon from '@mui/icons-material/Settings';
import QRCode from 'qrcode.react';
import { IPurchaseOrder } from '../../utils/interfaces';
import { StatusPurchaseOrder } from './components/StatusRender';

const rolePermission = [roles.superAdmin, roles.admin, roles.warehouseManager];

type MyParams = {
    warehouseId: UUID | any;
};

type SearchPo = {
    poCode: string | null;
    referenceNumber: string | null;
    status?: any | null;
};

const statusValue = [
    {
        value: 0,
        label: 'None',
    },
    {
        value: 1,
        label: 'Draft',
    },
    {
        value: 2,
        label: 'Incoming',
    },
    {
        value: 3,
        label: 'Received & Requires Warehousing',
    },
    {
        value: 4,
        label: 'Warehousing',
    },
    {
        value: 5,
        label: 'Stocked',
    },
];

export const PurchaseOrderList = () => {
    const { resources, resource, action, id } = useResource();

    const {
        pathname,
        params: {
            ...restParams // TParams - Any other parameters are also parsed and available in `params`
        },
    } = useParsed<MyParams>();

    console.log('restParams', restParams);

    const { mutate } = useDelete();

    const deleteItem = (id: UUID) => {
        mutate({
            id: id,
            resource: 'purchase_orders',
            mutationMode: 'undoable',
        });
    };

    const { data: permissionsData } = usePermissions<string[]>();
    const [page, setPage] = React.useState(0);

    const [rowsPerPage, setRowsPerPage] = React.useState(5);

    const [search, setSearch] = useState<SearchPo>({
        poCode: '',
        referenceNumber: '',
    });

    const [doRefetch, setDoRefetch] = useState<boolean>(true);

    const { data, isLoading } = useList<IPurchaseOrder>({
        resource: 'purchase_orders',
        pagination: {
            current: page,
            pageSize: rowsPerPage,
        },
        filters: [
            { field: 'warehouseId', operator: 'eq', value: restParams.warehouseId },
            {
                field: 'poCode',
                operator: 'eq',
                value: search.poCode,
            },
            {
                field: 'referenceNumber',
                operator: 'eq',
                value: search.referenceNumber,
            },
            {
                field: 'status',
                operator: 'eq',
                value: search.status,
            },
        ],
        queryOptions: {
            cacheTime: 200,
            enabled: doRefetch,
        },
    });

    // Avoid a layout jump when reaching the last page with empty rows.
    const handleChangeRowsPerPage = (event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        setRowsPerPage(parseInt(event.target.value, 10));
        setPage(0);
    };
    const handleChangePage = (event: React.MouseEvent<HTMLButtonElement> | null, newPage: number) => {
        setPage(newPage);
    };
    let emptyRows = 0;

    if (data?.data) {
        emptyRows = Math.max(0, rowsPerPage - data?.data.length);
    }

    return (
        <List canCreate={rolePermission.some((role) => permissionsData?.includes(role))}>
            <Box maxWidth={900} mb={2} display="flex" gap={1}>
                <TextField
                    label="Purchase Order Code"
                    fullWidth
                    variant="outlined"
                    value={search.poCode}
                    type="search"
                    InputLabelProps={{ shrink: true }}
                    onChange={(e) => {
                        // console.log('message zz', messages);
                        if (e.target.value == '') {
                            setDoRefetch(false);
                        }
                        setSearch((prev) => ({ ...prev, poCode: e.target.value }));
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
                <TextField
                    label="Reference Number"
                    fullWidth
                    type="search"
                    variant="outlined"
                    value={search.referenceNumber}
                    InputLabelProps={{ shrink: true }}
                    onChange={(e) => {
                        // console.log('message zz', messages);
                        if (e.target.value == '') {
                            setDoRefetch(false);
                        }
                        setSearch((prev) => ({ ...prev, referenceNumber: e.target.value }));
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
                <TextField
                    multiline
                    id="outlined-select-currency"
                    onChange={(e) => {
                        console.log(e.target.value);
                        setDoRefetch(false);
                        setSearch((prev) => ({
                            ...prev,
                            status: e.target.value === '0' ? null : e.target.value,
                        }));
                    }}
                    select
                    fullWidth
                    label="Status"
                    defaultValue={0}
                >
                    {statusValue.map((option) => (
                        <MenuItem key={option.value} value={option.value}>
                            {option.label}
                        </MenuItem>
                    ))}
                </TextField>
                <Button
                    size="medium"
                    variant="contained"
                    color="primary"
                    onClick={() => {
                        setDoRefetch(true);
                    }}
                    style={{ marginLeft: '8px' }}
                >
                    Search
                </Button>
            </Box>
            <TableContainer style={{ maxHeight: 'calc(53px * 10)' }} component={Paper}>
                <Table stickyHeader sx={{ minWidth: 500 }} aria-label="user table">
                    <TableHead>
                        <TableRow>
                            {purchaseOrderHeaderKey.map((header, index) => (
                                <StyledTableCell key={index} style={{ minWidth: header.minWidth }}>
                                    {header.title}&nbsp;
                                    {header.description !== '' && (
                                        <Tooltip title={header.description}>
                                            <HelpIcon sx={{ fontSize: 13 }} />
                                        </Tooltip>
                                    )}
                                </StyledTableCell>
                            ))}
                            <StickyStyledTableCell style={{ minWidth: 120 }} align="right">
                                Actions
                            </StickyStyledTableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {isLoading ? (
                            <TableRowsLoader rowsNum={rowsPerPage} fieldsNum={7} />
                        ) : (
                            data?.data.map((row) => (
                                <TableRow key={row.id}>
                                    <TableCell width={200} component="th" scope="row">
                                        {row.poCode || ''}
                                    </TableCell>
                                    <TableCell width={200} component="th" scope="row">
                                        {row.referenceNumber || ''}
                                    </TableCell>
                                    <TableCell component="th" scope="row">
                                        {row.supplierName || ''}
                                    </TableCell>
                                    <TableCell component="th" scope="row">
                                        {row.warehouseName || ''}
                                    </TableCell>
                                    <TableCell component="th" scope="row">
                                        {/* {row.status || ''} */}
                                        <StatusPurchaseOrder value={row.status} />
                                    </TableCell>
                                    <TableCell component="th" scope="row">
                                        {row.inboundDate || ''}
                                    </TableCell>
                                    <TableCell component="th" scope="row">
                                        {row.arrivalDate || ''}
                                    </TableCell>
                                    <TableCell component="th" scope="row">
                                        {row.createdDate.replace('T', ' ')}
                                    </TableCell>
                                    <TableCell component="th" scope="row">
                                        {row.createdBy}
                                    </TableCell>
                                    <TableCell component="th" scope="row">
                                        {row.lastModifiedDate.replace('T', ' ')}
                                    </TableCell>
                                    <TableCell component="th" scope="row">
                                        {row.lastModifiedBy}
                                    </TableCell>
                                    <StickyStyledTableCell align="right">
                                        <DeleteButtonIcon
                                            disabled={row.status !== 'Draft'}
                                            onDelete={() => {
                                                deleteItem(row.id);
                                            }}
                                        />

                                        <Link
                                            to={
                                                resource?.show
                                                    ? resource?.show?.toString().replace(':id', row.id)
                                                    : '/404'
                                            }
                                        >
                                            <Tooltip title="Show">
                                                <IconButton aria-label="fingerprint" color="primary">
                                                    <VisibilityIcon />
                                                </IconButton>
                                            </Tooltip>
                                        </Link>
                                    </StickyStyledTableCell>
                                </TableRow>
                            ))
                        )}
                        {emptyRows > 0 && (
                            <TableRow style={{ height: 53 * emptyRows }}>
                                <TableCell colSpan={12} />
                            </TableRow>
                        )}
                    </TableBody>
                </Table>
            </TableContainer>
            <TableContainer>
                <Table>
                    <TableFooter>
                        <TableRow>
                            <CustomTablePagination
                                rowsPerPageOptions={[5, 10, 25, { label: 'All', value: -1 }]}
                                colSpan={supplierHeaderTableKey.length}
                                count={data ? data.total : 0}
                                rowsPerPage={rowsPerPage}
                                page={page}
                                slotProps={{
                                    select: {
                                        'aria-label': 'rows per page',
                                    },
                                    actions: {
                                        showFirstButton: true,
                                        showLastButton: true,
                                        slots: {
                                            firstPageIcon: FirstPageRoundedIcon,
                                            lastPageIcon: LastPageRoundedIcon,
                                            nextPageIcon: ChevronRightRoundedIcon,
                                            backPageIcon: ChevronLeftRoundedIcon,
                                        },
                                    },
                                }}
                                onPageChange={handleChangePage}
                                onRowsPerPageChange={handleChangeRowsPerPage}
                                actionscomponent={TablePaginationActions}
                            />
                        </TableRow>
                    </TableFooter>
                </Table>
            </TableContainer>
        </List>
    );
};
