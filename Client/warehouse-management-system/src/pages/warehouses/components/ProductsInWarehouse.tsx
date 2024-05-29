import {
    Dialog,
    DialogTitle,
    IconButton,
    DialogContent,
    Grid,
    TextField,
    Autocomplete,
    FormControlLabel,
    Checkbox,
    Button,
    DialogActions,
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
import { Box, Container } from '@mui/system';
import CloseIcon from '@mui/icons-material/Close';
import { DeleteButtonIcon } from '../../../components/buttons/button';
import { TableRowsLoader } from '../../../components/skeleton';
import { CustomTablePagination } from '../../../components/table';
import { StyledTableCell } from '../../users';
import { List } from '@refinedev/mui';
import { useList } from '@refinedev/core';
import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import TablePaginationActions from '@mui/material/TablePagination/TablePaginationActions';
import FirstPageRoundedIcon from '@mui/icons-material/FirstPageRounded';
import LastPageRoundedIcon from '@mui/icons-material/LastPageRounded';
import ChevronLeftRoundedIcon from '@mui/icons-material/ChevronLeftRounded';
import ChevronRightRoundedIcon from '@mui/icons-material/ChevronRightRounded';
import VisibilityIcon from '@mui/icons-material/Visibility';
import { Row } from './Row';
import { useDebounce } from '@uidotdev/usehooks';

interface ProductsInWarehouseProps {
    warehouseId?: any;
}
export const ProductsInWarehouse = ({ warehouseId }: ProductsInWarehouseProps) => {
    const [page, setPage] = React.useState(0);

    const [rowsPerPage, setRowsPerPage] = React.useState(5);

    const handleChangeRowsPerPage = (event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        setRowsPerPage(parseInt(event.target.value, 10));
        setPage(0);
    };
    const handleChangePage = (event: React.MouseEvent<HTMLButtonElement> | null, newPage: number) => {
        setPage(newPage);
    };

    const [productNameSearch, setProductNameSearch] = useState<string>('');
    const [doRefetch, setDoRefetch] = useState(true);

    const { data, isLoading, refetch } = useList<any>({
        resource: 'inventories/products/search',
        pagination: {
            current: page,
            pageSize: rowsPerPage,
        },
        filters: [
            {
                field: 'warehouseId',
                operator: 'eq',
                value: warehouseId,
            },
            {
                field: 'productName',
                operator: 'eq',
                value: productNameSearch,
            },
        ],
        queryOptions: {
            cacheTime: 200,
            enabled: doRefetch,
        },
    });

    let emptyRows = 0;

    if (data?.data) {
        emptyRows = Math.max(0, rowsPerPage - data?.data.length);
    }

    return (
        <List canCreate={false} title="Products" breadcrumb={null}>
            <Box maxWidth={400} mb={2} display="flex">
                <TextField
                    fullWidth
                    variant="outlined"
                    placeholder="Product name..."
                    value={productNameSearch}
                    onChange={(e) => {
                        // console.log('message zz', messages);
                        if (e.target.value == '') {
                            setDoRefetch(false);
                        }
                        setProductNameSearch(e.target.value);
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
                <Button
                    variant="contained"
                    color="primary"
                    onClick={() => setDoRefetch(true)}
                    style={{ marginLeft: '8px' }}
                >
                    Search
                </Button>
            </Box>
            <TableContainer style={{ maxHeight: 'calc(53px * 10)' }} component={Paper}>
                <Table stickyHeader sx={{ minWidth: 500 }} aria-label="user table">
                    <TableHead>
                        <TableRow>
                            <StyledTableCell />
                            <StyledTableCell width={200}>Product Name</StyledTableCell>
                            <StyledTableCell width={200}>Quantity Available</StyledTableCell>
                            <StyledTableCell width={200}>Maximum Stock Level</StyledTableCell>
                            <StyledTableCell width={200}>Minimum Stock Level</StyledTableCell>
                            <StyledTableCell width={200}>Reorder Point</StyledTableCell>
                            <StyledTableCell align="right">Actions</StyledTableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {isLoading ? (
                            <TableRowsLoader rowsNum={rowsPerPage} fieldsNum={7} />
                        ) : (
                            data?.data.map((row, index) => <Row key={index} row={row} />)
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
