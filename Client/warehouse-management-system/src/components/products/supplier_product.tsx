import {
    Button,
    IconButton,
    Link,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableFooter,
    TableHead,
    TableRow,
    TextField,
    Tooltip,
} from '@mui/material';
import TablePaginationActions from '@mui/material/TablePagination/TablePaginationActions';
import { productHeaderTableDialogKey, productHeaderTableKey, supplierProductHeaderTableKey } from '../../constant';
import { StickyStyledTableCellLeft, StickyStyledTableCell, StyledTableCell } from '../../pages/users';
import { DeleteButtonIcon } from '../buttons/button';
import { TableRowsLoader } from '../skeleton';
import { CustomTablePagination } from '../table';
import HelpIcon from '@mui/icons-material/Help';
import QrCodeIcon from '@mui/icons-material/QrCode';
import { usePermissions, useList, useResource, useNavigation } from '@refinedev/core';
import React, { useEffect } from 'react';
import { IProduct } from '../../utils/interfaces';
import VisibilityIcon from '@mui/icons-material/Visibility';
import FirstPageRoundedIcon from '@mui/icons-material/FirstPageRounded';
import LastPageRoundedIcon from '@mui/icons-material/LastPageRounded';
import ChevronLeftRoundedIcon from '@mui/icons-material/ChevronLeftRounded';
import ChevronRightRoundedIcon from '@mui/icons-material/ChevronRightRounded';
import { List } from '@refinedev/mui';
import { useNavigate } from 'react-router-dom';
import { CheckBox } from '@mui/icons-material';
import { DataGrid, GridColDef, GridToolbar } from '@mui/x-data-grid';
import CheckCircleIcon from '@mui/icons-material/CheckCircle';
import HighlightOffIcon from '@mui/icons-material/HighlightOff';
import { UUID } from 'crypto';
import SaveIcon from '@mui/icons-material/Save';
import { count } from 'console';
import { postProductsToPurchaseOrder } from '../../utils/request';

const columns: GridColDef[] = [
    { field: 'productName', headerName: 'Product Name', width: 300 },
    { field: 'productCode', headerName: 'Product Code', width: 150 },
    {
        field: 'categoryName',
        headerName: 'Product Category',
        width: 130,
        valueGetter(value: any) {
            if (value) {
                return value.row.productCategoryDTO.categoryName;
            }
        },
    },
    { field: 'unit', headerName: 'Unit', width: 130 },
    {
        field: 'isPacked',
        headerName: 'Is Packed ?',
        width: 130,
        valueGetter(value: any) {
            const row = value.row;
            if (row.isPacked) {
                return true;
            } else {
                return false;
            }
        },
        renderCell(params) {
            const row = params.row;
            if (params.value) {
                return (
                    <Tooltip title={`${row.packedHeight}(H) * ${row.packedWidth}(W) * ${row.packedDepth}(D)`}>
                        <CheckCircleIcon />
                    </Tooltip>
                );
            } else {
                return <HighlightOffIcon />;
            }
        },
    },
    {
        field: 'count',
        headerName: 'Count',
        width: 130,
        renderCell(params) {
            return <TextField />;
        },
    },
];

interface ProductTableProps {
    supplierId: UUID | any;
    setIdSelected: (value: any) => void;
    onSaveData: (data: any | AddProduct[], callBackSuccessFull: () => any, callBackError: () => any) => void;
}

export interface AddProduct {
    productId: UUID | any;
    quantity: number | any;
}

export const SupplierProductTable = ({ supplierId, setIdSelected, onSaveData }: ProductTableProps) => {
    const navigate = useNavigate();
    const [page, setPage] = React.useState(0);
    const { create } = useNavigation();

    const [productIds, setProductIds] = React.useState<any[]>([]);

    const [productAdd, setProductAdd] = React.useState<AddProduct[]>([]);

    const isContainProductId = (id: any) => {
        console.log('check');
        return productAdd.some((product: AddProduct) => product.productId === id);
    };
    const [rowsPerPage, setRowsPerPage] = React.useState(5);
    const {
        data: productsData,
        isLoading: productLoading,
        refetch,
    } = useList<IProduct>({
        resource: `suppliers/${supplierId}/products`,
        pagination: {
            current: page,
            pageSize: rowsPerPage,
        },
        queryOptions: {
            cacheTime: 0,
        },
    });

    const { resources, resource, action, id } = useResource();

    const handleChangeRowsPerPage = (event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        setRowsPerPage(parseInt(event.target.value, 10));
        setPage(0);
    };
    const handleChangePage = (event: React.MouseEvent<HTMLButtonElement> | null, newPage: number) => {
        setPage(newPage);
    };
    let emptyRows = 0;

    if (productsData?.data) {
        emptyRows = Math.max(0, rowsPerPage - productsData?.data.length);
    }

    const changeCountAddProduct = (productId: any, count: any) => {
        let copy = productAdd.filter((item: AddProduct) => item.quantity > 0);

        const index = copy.findIndex((productAdd: AddProduct) => productAdd.productId === productId);

        console.log(index);
        if (index != -1) {
            copy[index] = {
                productId,
                quantity: count,
            };
        } else {
            copy.push({
                productId,
                quantity: count,
            });
        }

        copy = copy.filter((item: AddProduct) => item.quantity > 0);

        setProductAdd(copy);
    };

    useEffect(() => {
        let a = productAdd.map((item: AddProduct) => item.productId);
        setProductIds(a);
    }, [productAdd]);
    console.log(productAdd);

    const onSave = (productId: UUID | any) => {
        let data = productAdd.find((item: AddProduct) => item.productId === productId);
        if (data) {
            onSaveData(
                data,
                () => {
                    const copy = productAdd.filter((item: AddProduct) => item.productId !== productId);
                    setProductAdd(copy);
                    console.log('success');
                },
                () => {
                    console.log('error');
                },
            );
        }
    };

    const getValue = (productId: UUID | any) => {
        const item = productAdd.find((item: AddProduct) => item.productId === productId);
        if (item) {
            return item.quantity;
        } else {
            return '';
        }
    };

    return (
        <>
            <h3>List Supplier's Items</h3>
            <TableContainer style={{ maxHeight: 'calc(53px * 10)' }} component={Paper}>
                <Table stickyHeader sx={{ minWidth: 500 }} aria-label="user table">
                    <TableHead>
                        <TableRow>
                            <StickyStyledTableCellLeft style={{ minWidth: 150 }} align="left">
                                Count
                            </StickyStyledTableCellLeft>
                            {supplierProductHeaderTableKey.map((header, index) => (
                                <StyledTableCell key={index} style={{ minWidth: header.minWidth }}>
                                    {header.title}&nbsp;
                                    {header.description !== '' && (
                                        <Tooltip title={header.description}>
                                            <HelpIcon sx={{ fontSize: 13 }} />
                                        </Tooltip>
                                    )}
                                </StyledTableCell>
                            ))}
                            <StickyStyledTableCell style={{ minWidth: 100 }} align="right">
                                Actions
                            </StickyStyledTableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {productLoading ? (
                            <TableRowsLoader rowsNum={rowsPerPage} fieldsNum={7} />
                        ) : (
                            productsData?.data.map((row) => (
                                <TableRow key={row.productId}>
                                    <StickyStyledTableCellLeft align="left">
                                        <TextField
                                            onChange={(event: any) => {
                                                changeCountAddProduct(row.productId, parseInt(event.target.value));
                                            }}
                                            sx={{
                                                padding: 0,
                                            }}
                                            type="number"
                                            value={getValue(row.productId)}
                                        />
                                    </StickyStyledTableCellLeft>
                                    <TableCell width={200} component="th" scope="row">
                                        {row.productName}
                                    </TableCell>
                                    <TableCell width={200} component="th" scope="row">
                                        {row.productCode}
                                    </TableCell>
                                    <TableCell component="th" scope="row">
                                        {row.productCategoryDTO.categoryName}
                                    </TableCell>
                                    <TableCell component="th" scope="row">
                                        {row.reorderQuantity}
                                    </TableCell>
                                    <TableCell component="th" scope="row">
                                        {row.unit}
                                    </TableCell>
                                    <TableCell component="th" scope="row">
                                        {row.isPacked ? (
                                            <Tooltip
                                                title={`${row.packedHeight}(H) * ${row.packedWidth}(W) * ${row.packedDepth}(D)`}
                                            >
                                                <p>true</p>
                                            </Tooltip>
                                        ) : (
                                            'false'
                                        )}
                                    </TableCell>
                                    <TableCell component="th" scope="row">
                                        {row.sku}
                                    </TableCell>
                                    <StickyStyledTableCell align="right">
                                        <IconButton
                                            onClick={() => {
                                                // console.log('productAdd', productAdd);
                                                // console.log('productIds', productIds);
                                                onSave(row.productId);
                                            }}
                                            disabled={!productIds.includes(row.productId)}
                                        >
                                            <SaveIcon />
                                        </IconButton>
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
                                colSpan={7}
                                count={productsData ? productsData.total : 0}
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
        </>
    );
};
