import { useDelete, useList, usePermissions, useResource } from '@refinedev/core';
import { List } from '@refinedev/mui';
import { IProduct, IUser, IWarehouse } from '../../utils/interfaces';
import React, { useRef } from 'react';
import HelpIcon from '@mui/icons-material/Help';
import QrCodeIcon from '@mui/icons-material/QrCode';
import {
    Button,
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
import { productHeaderTableKey, roles } from '../../constant';
import { UUID } from 'crypto';
import SettingsIcon from '@mui/icons-material/Settings';
import QRCode from 'qrcode.react';
import { BarcodeGenerator } from '../../components/barcode/BarcodeGenerator';
import ReactToPrint from 'react-to-print';

const rolePermission = [roles.superAdmin, roles.admin, roles.warehouseManager];

export const ProductList = () => {
    const { resources, resource, action, id } = useResource();

    const barcodeRef = useRef<HTMLDivElement>(null);

    const [open, toggleOpen] = React.useState(false);
    const [productChoosed, setProductChoosed] = React.useState<IProduct>();

    const canvas = useRef<any>();

    const handleClose = () => {
        toggleOpen(false);
    };

    const handleOpen = (product: IProduct) => {
        setProductChoosed(product);
        toggleOpen(true);
    };

    const { mutate } = useDelete();

    const deleteItem = (id: UUID) => {
        mutate({
            id: id,
            resource: 'products',
            mutationMode: 'undoable',
        });
    };

    const [page, setPage] = React.useState(0);
    const { data: permissionsData } = usePermissions<string[]>();

    const [rowsPerPage, setRowsPerPage] = React.useState(5);
    const { data, isLoading } = useList<IProduct>({
        resource: 'products',
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
                            {productHeaderTableKey.map((header) => (
                                <StyledTableCell style={{ minWidth: header.minWidth }}>
                                    {header.title}&nbsp;
                                    {header.description !== '' && (
                                        <Tooltip title={header.description}>
                                            <HelpIcon sx={{ fontSize: 13 }} />
                                        </Tooltip>
                                    )}
                                </StyledTableCell>
                            ))}
                            <StickyStyledTableCell style={{ minWidth: 180 }} align="right">
                                Actions
                            </StickyStyledTableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {isLoading ? (
                            <TableRowsLoader rowsNum={rowsPerPage} fieldsNum={7} />
                        ) : (
                            data?.data.map((row) => (
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
                                            onDelete={() => {
                                                deleteItem(row.productId);
                                            }}
                                        />
                                        <Link
                                            to={
                                                resource?.edit
                                                    ? resource?.edit?.toString().replace(':id', row.productId)
                                                    : '/404'
                                            }
                                        >
                                            <Tooltip title="Detail">
                                                <IconButton aria-label="fingerprint" color="primary">
                                                    <EditIcon />
                                                </IconButton>
                                            </Tooltip>
                                        </Link>
                                        <Tooltip title="Barcode">
                                            <IconButton
                                                onClick={() => {
                                                    handleOpen(row);
                                                }}
                                                aria-label="fingerprint"
                                                color="primary"
                                            >
                                                <QrCodeIcon />
                                            </IconButton>
                                        </Tooltip>
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

            <Dialog open={open} onClose={handleClose}>
                <DialogTitle>Product's Barcode</DialogTitle>

                {open && productChoosed && (
                    <>
                        <div
                            ref={barcodeRef}
                            style={{
                                marginTop: 10,
                                padding: 10,
                                width: 500,
                                height: 300,
                                display: 'flex',
                                flexDirection: 'column',
                                justifyContent: 'center',
                                alignItems: 'center',
                            }}
                        >
                            {/* <BarcodeGenerator height={100} value={productChoosed?.barcode} /> */}
                            <QRCode size={250} value={productChoosed.barcode} renderAs="canvas" />
                            <p>{productChoosed.barcode}</p>
                        </div>
                        <ReactToPrint
                            content={() => barcodeRef.current}
                            trigger={() => <Button variant="contained">Print</Button>}
                        />
                    </>
                )}
            </Dialog>
        </List>
    );
};
