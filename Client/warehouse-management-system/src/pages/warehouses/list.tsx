import { useDelete, useList, usePermissions, useResource } from '@refinedev/core';
import { List } from '@refinedev/mui';
import { IWarehouse } from '../../utils/interfaces';
import React from 'react';
import {
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
import { StyledTableCell } from '../users';
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
import { UUID } from 'crypto';

export const WarehouseList = () => {
    const { list, create, edit, show, clone, push, replace, goBack, listUrl, createUrl, editUrl, showUrl, cloneUrl } =
        useNavigation();
    const { resources, resource, action, id } = useResource();
    console.log(resource);
    const [page, setPage] = React.useState(0);
    const { data: permissionsData } = usePermissions<string[]>();

    const [rowsPerPage, setRowsPerPage] = React.useState(5);

    const { data, isLoading } = useList<IWarehouse>({
        resource: 'warehouses',
        pagination: {
            current: page,
            pageSize: rowsPerPage,
        },
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
                            <StyledTableCell>Warehouse Name</StyledTableCell>
                            <StyledTableCell>Warehouse Name Acronym</StyledTableCell>
                            <StyledTableCell>Location</StyledTableCell>
                            <StyledTableCell>Type</StyledTableCell>
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
                                <TableRow
                                    onDoubleClick={() => {
                                        show('warehouses', row.id);
                                    }}
                                    key={row.id}
                                >
                                    <TableCell width={200} component="th" scope="row">
                                        {row.warehouseName}
                                    </TableCell>
                                    <TableCell width={200} component="th" scope="row">
                                        {row.warehouseNameAcronym}
                                    </TableCell>
                                    <TableCell
                                        width={300}
                                        style={{
                                            textWrap: 'nowrap',
                                        }}
                                        component="th"
                                        scope="row"
                                    >
                                        {row.location}, {row.wardName}, {row.districtName}, {row.provinceName}
                                    </TableCell>
                                    <TableCell component="th" scope="row">
                                        {row.type}
                                    </TableCell>
                                    <TableCell component="th" scope="row">
                                        {row.createdDate.replace('T', ' ')}
                                    </TableCell>
                                    <TableCell component="th" scope="row">
                                        {row.createdBy}
                                    </TableCell>
                                    <TableCell align="right">
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
                                            <Tooltip title="Edit">
                                                <IconButton aria-label="fingerprint" color="primary">
                                                    <EditIcon />
                                                </IconButton>
                                            </Tooltip>
                                        </Link>
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
