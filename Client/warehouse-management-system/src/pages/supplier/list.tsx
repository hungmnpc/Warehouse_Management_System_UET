import { useDelete, useList, usePermissions, useResource } from '@refinedev/core';
import { List } from '@refinedev/mui';
import { IProduct, ISupplier, IUser, IWarehouse } from '../../utils/interfaces';
import React, { useRef } from 'react';
import HelpIcon from '@mui/icons-material/Help';
import QrCodeIcon from '@mui/icons-material/QrCode';
import {
    Dialog,
    DialogTitle,
    IconButton,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableFooter,
    TableHead,
    TableRow,
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
import { productHeaderTableKey, roles, supplierHeaderTableKey } from '../../constant';
import { UUID } from 'crypto';
import SettingsIcon from '@mui/icons-material/Settings';
import QRCode from 'qrcode.react';

const rolePermission = [roles.superAdmin, roles.admin, roles.warehouseManager];

export const SupplierList = () => {
    const { resources, resource, action, id } = useResource();

    const { mutate } = useDelete();

    const deleteItem = (id: UUID) => {
        mutate({
            id: id,
            resource: 'suppliers',
            mutationMode: 'undoable',
        });
    };

    const { data: permissionsData } = usePermissions<string[]>();
    const [page, setPage] = React.useState(0);

    const [rowsPerPage, setRowsPerPage] = React.useState(5);
    const { data, isLoading } = useList<ISupplier>({
        resource: 'suppliers',
        pagination: {
            current: page,
            pageSize: rowsPerPage,
        },
        queryOptions: {
            cacheTime: 0,
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
            <TableContainer style={{ maxHeight: 'calc(53px * 10)' }} component={Paper}>
                <Table stickyHeader sx={{ minWidth: 500 }} aria-label="user table">
                    <TableHead>
                        <TableRow>
                            {supplierHeaderTableKey.map((header, index) => (
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
                                        {row.supplierName}
                                    </TableCell>
                                    <TableCell width={200} component="th" scope="row">
                                        {row.supplierNumber}
                                    </TableCell>
                                    <TableCell component="th" scope="row">
                                        {row.supplierAddress1 || ''}
                                    </TableCell>
                                    <TableCell component="th" scope="row">
                                        {row.supplierAddress2 || ''}
                                    </TableCell>
                                    <TableCell component="th" scope="row">
                                        {row.createdDate.replace('T', ' ')}
                                    </TableCell>
                                    <TableCell component="th" scope="row">
                                        {row.lastModifiedDate.replace('T', ' ')}
                                    </TableCell>
                                    <TableCell component="th" scope="row">
                                        {row.lastModifiedBy}
                                    </TableCell>
                                    <StickyStyledTableCell align="right">
                                        <DeleteButtonIcon
                                            onDelete={() => {
                                                deleteItem(row.id);
                                            }}
                                        />
                                        <Link
                                            to={
                                                resource?.edit
                                                    ? resource?.edit?.toString().replace(':id', row.id)
                                                    : '/404'
                                            }
                                        >
                                            <Tooltip title="Detail">
                                                <IconButton aria-label="fingerprint" color="primary">
                                                    <EditIcon />
                                                </IconButton>
                                            </Tooltip>
                                        </Link>
                                        {/* <Link
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
                                        </Link> */}
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
