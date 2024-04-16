import { useDelete, useList, usePermissions, useResource } from '@refinedev/core';
import { Link } from 'react-router-dom';
import { dataProvider } from '../../data-provider';
import {
    IconButton,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableFooter,
    TableHead,
    TablePagination,
    TableRow,
    Tooltip,
    styled,
    tableCellClasses,
} from '@mui/material';
import { List } from '@refinedev/mui';
import React from 'react';
import { IResponsePagination, IUser } from '../../utils/interfaces';
import TablePaginationActions from '@mui/material/TablePagination/TablePaginationActions';
import { Fingerprint, ManageAccounts } from '@mui/icons-material';
import DeleteIcon from '@mui/icons-material/Delete';
import { DeleteButtonIcon } from '../../components/buttons/button';
import { TableRowsLoader } from '../../components/skeleton';
import { CustomTablePagination } from '../../components/table';
import FirstPageRoundedIcon from '@mui/icons-material/FirstPageRounded';
import LastPageRoundedIcon from '@mui/icons-material/LastPageRounded';
import ChevronLeftRoundedIcon from '@mui/icons-material/ChevronLeftRounded';
import ChevronRightRoundedIcon from '@mui/icons-material/ChevronRightRounded';
import { UUID } from 'crypto';

export const StyledTableCell = styled(TableCell)(({ theme }) => ({
    [`&.${tableCellClasses.head}`]: {
        backgroundColor: theme.palette.mode == 'light' ? '#F1EEDC' : '#0C0C0C',
        color: theme.palette.mode == 'dark' ? '#FFF' : '#000',
    },
    [`&.${tableCellClasses.body}`]: {
        fontSize: 14,
    },
}));

export const StickyStyledTableCell = styled(TableCell)(({ theme }) => {
    console.log('theme', theme);
    return {
        [`&.${tableCellClasses.head}`]: {
            backgroundColor: theme.palette.mode == 'light' ? '#F1EEDC' : '#0C0C0C',
            color: theme.palette.mode == 'dark' ? '#FFF' : '#000',
            position: 'sticky',
            right: 0,
            zIndex: theme.zIndex.appBar + 1,
        },
        [`&.${tableCellClasses.body}`]: {
            backgroundColor: theme.palette.mode == 'light' ? '#F1EEDC' : '#0C0C0C',
            color: theme.palette.mode == 'dark' ? '#FFF' : '#000',
            fontSize: 14,
            position: 'sticky',
            right: 0,
            zIndex: theme.zIndex.appBar + 1,
        },
    };
});

const rolePermission = ['ROLE_SUPER_ADMIN', 'ROLE_ADMIN', 'ROLE_WAREHOUSE_MANAGER'];

export const UserList = () => {
    const { resources, resource, action, id } = useResource();

    const { mutate } = useDelete();

    const deleteItem = (id: UUID) => {
        mutate({
            id: id,
            resource: 'users',
            dataProviderName: 'users',
            mutationMode: 'undoable',
        });
    };

    const secondDataProvider = dataProvider('users');

    const [page, setPage] = React.useState(0);
    const { data: permissionsData } = usePermissions<string[]>();

    const [rowsPerPage, setRowsPerPage] = React.useState(5);
    const { data, isLoading } = useList<IUser>({
        dataProviderName: 'users',
        resource: 'users',
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
        <div>
            <List canCreate={rolePermission.some((role) => permissionsData?.includes(role))}>
                <TableContainer style={{ maxHeight: 'calc(53px * 10)' }} component={Paper}>
                    <Table stickyHeader sx={{ minWidth: 500 }} aria-label="user table">
                        <TableHead>
                            <TableRow>
                                <StyledTableCell>First Name</StyledTableCell>
                                <StyledTableCell>Last Name</StyledTableCell>
                                <StyledTableCell>User Name</StyledTableCell>
                                <StyledTableCell>Role Name</StyledTableCell>
                                <StyledTableCell>Created At</StyledTableCell>
                                <StyledTableCell>Created By</StyledTableCell>
                                <StyledTableCell align="right">Actions</StyledTableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {isLoading ? (
                                <TableRowsLoader rowsNum={rowsPerPage} fieldsNum={7} />
                            ) : (
                                data?.data.map((row) => (
                                    <TableRow key={row.id}>
                                        <TableCell width={200} component="th" scope="row">
                                            {row.firstName}
                                        </TableCell>
                                        <TableCell width={200} component="th" scope="row">
                                            {row.lastName}
                                        </TableCell>
                                        <TableCell component="th" scope="row">
                                            {row.userName}
                                        </TableCell>
                                        <TableCell component="th" scope="row">
                                            {row.roleName}
                                        </TableCell>
                                        <TableCell component="th" scope="row">
                                            {row.createdDate.replace('T', ' ')}
                                        </TableCell>
                                        <TableCell component="th" scope="row">
                                            {row.createdBy}
                                        </TableCell>
                                        <TableCell align="right">
                                            <DeleteButtonIcon
                                                disabled={row.roleName === 'ROLE_SUPER_ADMIN'}
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
                                                        <ManageAccounts />
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
        </div>
    );
};
