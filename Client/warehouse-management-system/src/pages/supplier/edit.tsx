import Grid2 from '@mui/material/Unstable_Grid2/Grid2';
import { HttpError, useList, useNotification, useOne, useResource } from '@refinedev/core';
import { Edit, RefreshButton } from '@refinedev/mui';
import { IProduct, ISupplier } from '../../utils/interfaces';
import {
    AppBar,
    Button,
    Dialog,
    Grid,
    IconButton,
    InputAdornment,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableFooter,
    TableHead,
    TableRow,
    TextField,
    Toolbar,
    Tooltip,
    Typography,
} from '@mui/material';
import React, { useEffect, useState } from 'react';
import { Container } from '@mui/system';
import styles from './supplier.module.scss';
import classNames from 'classnames/bind';
import EditIcon from '@mui/icons-material/Edit';
import SaveIcon from '@mui/icons-material/Save';
import CloseIcon from '@mui/icons-material/Close';
import { productHeaderTableKey, supplierProductHeaderTableKey } from '../../constant';
import { StickyStyledTableCell, StyledTableCell } from '../users';
import HelpIcon from '@mui/icons-material/Help';
import QrCodeIcon from '@mui/icons-material/QrCode';
import { TableRowsLoader } from '../../components/skeleton';
import { DeleteButtonIcon } from '../../components/buttons/button';
import { CustomTablePagination } from '../../components/table';
import TablePaginationActions from '@mui/material/TablePagination/TablePaginationActions';
import FirstPageRoundedIcon from '@mui/icons-material/FirstPageRounded';
import LastPageRoundedIcon from '@mui/icons-material/LastPageRounded';
import ChevronLeftRoundedIcon from '@mui/icons-material/ChevronLeftRounded';
import ChevronRightRoundedIcon from '@mui/icons-material/ChevronRightRounded';
import { ProductTable } from '../../components/products/table';
import { UUID } from 'crypto';
import { postProductsToSupplier } from '../../utils/request';

const cx = classNames.bind(styles);

interface EditField {
    isEditSupplierName: boolean;
    isEditSupplierNumber: boolean;
    isEditSupplierAddress1: boolean;
    isEditSupplierAddress2: boolean;
}

const initEditAble: EditField = {
    isEditSupplierAddress1: false,
    isEditSupplierAddress2: false,
    isEditSupplierName: false,
    isEditSupplierNumber: false,
};

export const EditSupplier = () => {
    const { open: openNotification } = useNotification();
    const { resource, id } = useResource();

    const { data, isLoading, isError } = useOne<ISupplier, HttpError>({
        resource: 'suppliers',
        id,
    });

    const [supplierState, setSupplierState] = useState<ISupplier | null>(null);

    const [editField, setIsEditField] = useState<EditField>(initEditAble);

    const handleToggle = (field: string, value: boolean) => {
        setIsEditField({
            ...editField,
            [field]: value,
        });
    };

    useEffect(() => {
        if (data) {
            setSupplierState(data.data);
        }
    }, [data]);

    const [page, setPage] = React.useState(0);

    const [rowsPerPage, setRowsPerPage] = React.useState(5);

    const {
        data: productsData,
        isLoading: productLoading,
        refetch,
    } = useList<IProduct>({
        resource: `suppliers/${id}/products`,
        pagination: {
            current: page,
            pageSize: rowsPerPage,
        },
        queryOptions: {
            cacheTime: 0,
        },
    });

    console.log(productsData);

    let emptyRows = 0;

    if (productsData?.data) {
        emptyRows = Math.max(0, rowsPerPage - productsData?.data.length);
    }

    // Avoid a layout jump when reaching the last page with empty rows.
    const handleChangeRowsPerPage = (event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        setRowsPerPage(parseInt(event.target.value, 10));
        setPage(0);
    };
    const handleChangePage = (event: React.MouseEvent<HTMLButtonElement> | null, newPage: number) => {
        setPage(newPage);
    };

    const [open, setOpen] = React.useState(false);

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setIdSeleted([]);
        setOpen(false);
    };

    const handleSubmitAddProduct = () => {
        postProductsToSupplier(idSelected, id)
            .then((response) => {
                console.log(response);
                if (openNotification) {
                    openNotification({
                        message: 'Add product successfully',
                        description: `${idSelected.length} ${
                            idSelected.length == 1 ? 'product was' : 'products were'
                        } added.`,
                        type: 'success',
                    });
                    handleClose();
                    refetch();
                }
            })
            .catch((error) => {
                console.error(error);
            });
    };

    const [idSelected, setIdSeleted] = React.useState<UUID[]>([]);
    console.log(idSelected);

    return (
        <Edit>
            {supplierState !== null && (
                <>
                    <Grid2 xs={12} justifyContent="center" alignItems="center" container spacing={2}>
                        <Grid2 xs={5}>
                            <TextField
                                margin="normal"
                                fullWidth
                                InputLabelProps={{ shrink: true }}
                                type="text"
                                label="Supplier Name"
                                name="supplierName"
                                inputProps={{
                                    autoComplete: 'new-password',
                                    form: {
                                        autocomplete: 'off',
                                    },
                                }}
                                disabled={!editField.isEditSupplierName}
                                onChange={(e) => {
                                    console.log(e.target.value);
                                    setSupplierState({
                                        ...supplierState,
                                        supplierName: e.target.value,
                                    });
                                }}
                                InputProps={{
                                    endAdornment: (
                                        <InputAdornment position="end">
                                            <IconButton
                                                aria-label="toggle password visibility"
                                                onClick={() => {
                                                    handleToggle('isEditSupplierName', !editField.isEditSupplierName);
                                                }}
                                            >
                                                {editField.isEditSupplierName ? <SaveIcon /> : <EditIcon />}
                                            </IconButton>
                                        </InputAdornment>
                                    ),
                                }}
                                defaultValue={supplierState.supplierName}
                            />
                        </Grid2>
                        <Grid2 xs={7}>
                            <TextField
                                margin="normal"
                                fullWidth
                                InputLabelProps={{ shrink: true }}
                                type="text"
                                label="Supplier Address 1"
                                name="supplierAddress1"
                                inputProps={{
                                    autoComplete: 'new-password',
                                    form: {
                                        autocomplete: 'off',
                                    },
                                }}
                                disabled={!editField.isEditSupplierAddress1}
                                defaultValue={supplierState.supplierAddress1}
                                InputProps={{
                                    endAdornment: (
                                        <InputAdornment position="end">
                                            <IconButton
                                                aria-label="toggle password visibility"
                                                onClick={() => {
                                                    handleToggle(
                                                        'isEditSupplierAddress1',
                                                        !editField.isEditSupplierAddress1,
                                                    );
                                                }}
                                            >
                                                {editField.isEditSupplierAddress1 ? <SaveIcon /> : <EditIcon />}
                                            </IconButton>
                                        </InputAdornment>
                                    ),
                                }}
                            />
                        </Grid2>

                        <Grid2 xs={5}>
                            <TextField
                                margin="normal"
                                fullWidth
                                InputLabelProps={{ shrink: true }}
                                type="text"
                                label="Supplier Number"
                                name="supplierNumber"
                                inputProps={{
                                    autoComplete: 'new-password',
                                    form: {
                                        autocomplete: 'off',
                                    },
                                }}
                                disabled={!editField.isEditSupplierNumber}
                                defaultValue={supplierState.supplierNumber}
                                InputProps={{
                                    endAdornment: (
                                        <InputAdornment position="end">
                                            <IconButton
                                                aria-label="toggle password visibility"
                                                onClick={() => {
                                                    handleToggle(
                                                        'isEditSupplierNumber',
                                                        !editField.isEditSupplierNumber,
                                                    );
                                                }}
                                            >
                                                {editField.isEditSupplierNumber ? <SaveIcon /> : <EditIcon />}
                                            </IconButton>
                                        </InputAdornment>
                                    ),
                                }}
                            />
                        </Grid2>
                        <Grid2 xs={7}>
                            <TextField
                                margin="normal"
                                fullWidth
                                InputLabelProps={{ shrink: true }}
                                type="text"
                                label="Supplier Address 2"
                                name="supplierAddress2"
                                inputProps={{
                                    autoComplete: 'new-password',
                                    form: {
                                        autocomplete: 'off',
                                    },
                                }}
                                disabled={!editField.isEditSupplierAddress2}
                                defaultValue={supplierState.supplierAddress2}
                                InputProps={{
                                    endAdornment: (
                                        <InputAdornment position="end">
                                            <IconButton
                                                aria-label="toggle password visibility"
                                                onClick={() => {
                                                    handleToggle(
                                                        'isEditSupplierAddress2',
                                                        !editField.isEditSupplierAddress2,
                                                    );
                                                }}
                                            >
                                                {editField.isEditSupplierAddress2 ? <SaveIcon /> : <EditIcon />}
                                            </IconButton>
                                        </InputAdornment>
                                    ),
                                }}
                            />
                        </Grid2>
                    </Grid2>

                    <div className={cx('supplier-product-container')}>
                        <div className={cx('add-div')}>
                            <Button onClick={handleClickOpen} variant="contained">
                                Add Product
                            </Button>
                        </div>
                        <TableContainer style={{ maxHeight: 'calc(53px * 10)' }} component={Paper}>
                            <Table stickyHeader sx={{ minWidth: 500 }} aria-label="user table">
                                <TableHead>
                                    <TableRow>
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
                                                    <DeleteButtonIcon onDelete={() => {}} />
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
                    </div>
                </>
            )}

            <Dialog fullWidth maxWidth="lg" open={open}>
                <AppBar sx={{ position: 'relative' }}>
                    <Toolbar>
                        <IconButton edge="start" color="inherit" onClick={handleClose} aria-label="close">
                            <CloseIcon />
                        </IconButton>
                        <Typography sx={{ ml: 2, flex: 1 }} variant="h6" component="div">
                            Choose Products
                        </Typography>
                        <Button
                            disabled={idSelected.length == 0}
                            variant="contained"
                            autoFocus
                            onClick={handleSubmitAddProduct}
                        >
                            save
                        </Button>
                    </Toolbar>
                </AppBar>
                <div className={cx('dialog-product')}>
                    <ProductTable
                        setIdSelected={setIdSeleted}
                        ignoreIds={productsData?.data.map((productData) => productData.productId)}
                    />
                </div>
            </Dialog>
        </Edit>
    );
};
