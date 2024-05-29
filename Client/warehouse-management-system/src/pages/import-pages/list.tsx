import {
    Breadcrumbs,
    IconButton,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableFooter,
    TableHead,
    TableRow,
    Tooltip,
} from '@mui/material';
import { useDelete, useList, useNavigation, useParsed, usePermissions } from '@refinedev/core';
import { Breadcrumb, List } from '@refinedev/mui';
import { UUID } from 'crypto';
import { StickyStyledTableCell, StyledTableCell } from '../users';
import { TableRowsLoader } from '../../components/skeleton';
import { DeleteButtonIcon } from '../../components/buttons/button';
import { Link } from 'react-router-dom';
import { CustomTablePagination } from '../../components/table';
import React from 'react';
import FirstPageRoundedIcon from '@mui/icons-material/FirstPageRounded';
import LastPageRoundedIcon from '@mui/icons-material/LastPageRounded';
import ChevronLeftRoundedIcon from '@mui/icons-material/ChevronLeftRounded';
import ChevronRightRoundedIcon from '@mui/icons-material/ChevronRightRounded';
import EditIcon from '@mui/icons-material/Edit';
import VisibilityIcon from '@mui/icons-material/Visibility';
import TablePaginationActions from '@mui/material/TablePagination/TablePaginationActions';
import { PurchaseOrderStatusRaw, goodsReceivedHeader } from '../../constant';
import HelpIcon from '@mui/icons-material/Help';
import { IGoodsReceived, IPurchaseOrder } from '../../utils/interfaces';
import { StatusComponent } from '../../components/status';
import { StatusPurchaseOrder } from '../purchase-order/components/StatusRender';

type MyParams = {
    id: UUID | any;
    warehouseId: UUID | any;
};

const renderStatus = (input: string) => {
    console.log(typeof input);
    switch (input) {
        case '1':
            return <StatusComponent txtColor="black" value={'DRAFT'} bgcolor={'#FFEC9E'} />;
            break;
        default:
            return <StatusComponent txtColor="black" value={'NULL'} bgcolor={'white'} />;
            break;
    }
};

export const ImportStockList = () => {
    const {
        resource,
        action,
        id,
        pathname,
        params: {
            ...restParams // TParams - Any other parameters are also parsed and available in `params`
        },
    } = useParsed<MyParams>();

    const { list, create, edit, show, clone, push, replace, goBack, listUrl, createUrl, editUrl, showUrl, cloneUrl } =
        useNavigation();
    console.log(resource);
    const [page, setPage] = React.useState(0);
    const { data: permissionsData } = usePermissions<string[]>();

    const [rowsPerPage, setRowsPerPage] = React.useState(5);

    console.log(restParams);

    const { data, isLoading } = useList<IPurchaseOrder>({
        resource: 'purchase_orders',
        pagination: {
            current: page,
            pageSize: rowsPerPage,
        },
        filters: [
            { field: 'warehouseId', operator: 'eq', value: restParams.id },
            {
                field: 'status',
                operator: 'eq',
                value: [
                    PurchaseOrderStatusRaw.RECEIVED_AND_REQUIRES_WAREHOUSING,
                    PurchaseOrderStatusRaw.WAREHOUSING,
                    PurchaseOrderStatusRaw.STOCKED,
                ],
            },
        ],
        queryOptions: {
            cacheTime: 0,
        },
    });

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

    const { mutate } = useDelete();

    const deleteItem = (id: UUID) => {
        mutate({
            id: id,
            resource: 'warehouses',
            mutationMode: 'undoable',
        });
    };
    console.log(data);

    return (
        <List canCreate={true}>
            <TableContainer style={{ maxHeight: 'calc(53px * 10)' }} component={Paper}>
                <Table stickyHeader sx={{ minWidth: 500 }} aria-label="user table">
                    <TableHead>
                        <TableRow>
                            {goodsReceivedHeader.map((header, index) => (
                                <StyledTableCell key={index} style={{ minWidth: header.minWidth }}>
                                    {header.title}&nbsp;
                                    {header.description !== '' && (
                                        <Tooltip title={header.description}>
                                            <HelpIcon sx={{ fontSize: 13 }} />
                                        </Tooltip>
                                    )}
                                </StyledTableCell>
                            ))}
                            <StickyStyledTableCell style={{ minWidth: 200 }} align="right">
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
                                        {row.poCode}
                                    </TableCell>
                                    <TableCell
                                        width={300}
                                        style={{
                                            textWrap: 'nowrap',
                                        }}
                                        component="th"
                                        scope="row"
                                    >
                                        {row.warehouseName}
                                    </TableCell>
                                    <TableCell component="th" scope="row">
                                        <StatusPurchaseOrder value={row.status} />
                                    </TableCell>
                                    <TableCell component="th" scope="row">
                                        {row.employeeName}
                                    </TableCell>
                                    <TableCell component="th" scope="row">
                                        {row.arrivalDate}
                                    </TableCell>

                                    <TableCell component="th" scope="row">
                                        {row.deadLineToStock}
                                    </TableCell>
                                    <TableCell align="right">
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
                                    </TableCell>
                                </TableRow>
                            ))
                        )}
                        {emptyRows > 0 && (
                            <TableRow style={{ height: 53 * emptyRows }}>
                                <TableCell colSpan={7} />
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
                                colSpan={7}
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
