import { useList, usePermissions, useResource } from "@refinedev/core";
import { Link } from "react-router-dom";
import { dataProvider } from "../../data-provider";
import { IconButton, Paper, Table, TableBody, TableCell, TableContainer, TableFooter, TableHead, TablePagination, TableRow, Tooltip, styled, tableCellClasses } from "@mui/material";
import { List } from "@refinedev/mui";
import React from "react";
import { IResponsePagination, IUser } from "../../utils/interfaces";
import TablePaginationActions from "@mui/material/TablePagination/TablePaginationActions";
import { Fingerprint, ManageAccounts } from "@mui/icons-material";
import DeleteIcon from '@mui/icons-material/Delete';
import { DeleteButtonIcon } from "../../components/buttons/button";

const StyledTableCell = styled(TableCell)(({ theme }) => ({
    [`&.${tableCellClasses.head}`]: {
        backgroundColor: theme.palette.common.black,
        color: theme.palette.common.white,
    },
    [`&.${tableCellClasses.body}`]: {
        fontSize: 14,
    },
}));

export const UserList = () => {

    const { resources, resource, action, id } = useResource();

    const secondDataProvider = dataProvider("users");

    const { data, isLoading } = useList<IUser>({
        dataProviderName: "users",
        resource: "users",

    })
    const [page, setPage] = React.useState(0);
    const { data: permissionsData } = usePermissions<string[]>();

    console.log(data);

    const [rowsPerPage, setRowsPerPage] = React.useState(5);

    // Avoid a layout jump when reaching the last page with empty rows.
    const handleChangeRowsPerPage = (
        event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>,
    ) => {
        setRowsPerPage(parseInt(event.target.value, 10));
        setPage(0);
    };
    const handleChangePage = (
        event: React.MouseEvent<HTMLButtonElement> | null,
        newPage: number,
    ) => {
        setPage(newPage);
    };



    if (isLoading) {
        return <div>Loading......</div>
    }
    console.log(data)

    if (data?.data) {
        const emptyRows = Math.max(0, rowsPerPage - data?.data.length);

        return <div>
            <List
                canCreate={permissionsData?.includes("ROLE_SUPER_ADMIN")}
            >
                <TableContainer component={Paper}>
                    <Table sx={{ minWidth: 500 }} aria-label="user table">
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
                            {data?.data.map((row) => (
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
                                        {row.createdDate.replace("T", " ")}
                                    </TableCell>
                                    <TableCell component="th" scope="row">
                                        {row.createdBy}
                                    </TableCell>
                                    <TableCell align="right">
                                        <DeleteButtonIcon onDelete = {() => {
                                            console.log("Delete")
                                        }} />
                                        <Link to={resource?.edit ? resource?.edit?.toString().replace(":id", row.id) : '/404'}>
                                            <Tooltip title="Detail">
                                                <IconButton aria-label="fingerprint" color="primary">
                                                    <ManageAccounts />
                                                </IconButton>
                                            </Tooltip>
                                        </Link>
                                    </TableCell>
                                </TableRow>
                            ))}
                            {emptyRows > 0 && (
                                <TableRow style={{ height: 53 * emptyRows }}>
                                    <TableCell colSpan={7} />
                                </TableRow>
                            )}
                        </TableBody>
                        <TableFooter>
                            <TableRow>
                                <TablePagination
                                    rowsPerPageOptions={[5, 10, 25, { label: 'All', value: -1 }]}
                                    colSpan={7}
                                    count={data.total}
                                    rowsPerPage={rowsPerPage}
                                    page={page}
                                    slotProps={{
                                        select: {
                                            inputProps: {
                                                'aria-label': 'rows per page',
                                            },
                                            native: true,
                                        },
                                    }}
                                    onPageChange={handleChangePage}
                                    onRowsPerPageChange={handleChangeRowsPerPage}
                                    ActionsComponent={TablePaginationActions}
                                />
                            </TableRow>
                        </TableFooter>
                    </Table>
                </TableContainer>
            </List>
        </div>
    }
}