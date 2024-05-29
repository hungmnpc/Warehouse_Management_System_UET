import Grid2 from '@mui/material/Unstable_Grid2/Grid2';
import { HttpError, useDelete, useList, useNotification, useOne, useResource } from '@refinedev/core';
import { RefreshButton, Show } from '@refinedev/mui';
import style from './PurchaseOrder.module.scss';
import classNames from 'classnames/bind';
import React, { useEffect, useState } from 'react';
import EditIcon from '@mui/icons-material/Edit';
import SaveIcon from '@mui/icons-material/Save';
import {
    assignEmployeeToPo,
    checkPoWasAssigned,
    getHistory,
    postProductsToPurchaseOrder,
    updateDeadlinePurchaseOrder,
    updateStatusPurchaseOrder,
} from '../../utils/request';
import Timeline from '../../components/timeline';
import { Box } from '@mui/system';
import CloseIcon from '@mui/icons-material/Close';
import { TimelineComponent, ItemDirective, ItemsDirective } from '@syncfusion/ej2-react-layouts';
import {
    AppBar,
    Button,
    Dialog,
    IconButton,
    InputAdornment,
    Link,
    Paper,
    Skeleton,
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
    useTheme,
} from '@mui/material';
import { IPurchaseOrder } from '../../utils/interfaces';
import TablePaginationActions from '@mui/material/TablePagination/TablePaginationActions';
import { DeleteButtonIcon } from '../../components/buttons/button';
import { TableRowsLoader } from '../../components/skeleton';
import { CustomTablePagination } from '../../components/table';
import { PurchaseOrderDetailKeyHeader, productHeaderTableKey } from '../../constant';
import { StyledTableCell, StickyStyledTableCell } from '../users';
import HelpIcon from '@mui/icons-material/Help';
import FirstPageRoundedIcon from '@mui/icons-material/FirstPageRounded';
import LastPageRoundedIcon from '@mui/icons-material/LastPageRounded';
import ChevronLeftRoundedIcon from '@mui/icons-material/ChevronLeftRounded';
import ChevronRightRoundedIcon from '@mui/icons-material/ChevronRightRounded';
import { ProductTable } from '../../components/products/table';
import { AddProduct, SupplierProductTable } from '../../components/products/supplier_product';
import { UUID } from 'crypto';
import { StatusButton } from './components/StatusButton';
import ChatUI from '../../components/communication/chatui';
import { StatusLine } from './components/statusLine';
import { AssignPO } from './components/AssignPO';
import { error } from 'console';
import { NotFoundPage } from '../common/notFoundPage';

const cx = classNames.bind(style);

export const PurchaseOrderShow = () => {
    const theme = useTheme();
    const { resources, resource, id } = useResource();
    const [histories, setHistories] = useState([]);
    const [refetchKey, setRefetchKey] = useState(0);
    const {
        data,
        isLoading,
        isError,
        refetch: RefetchPurchaseOrder,
    } = useOne<IPurchaseOrder, HttpError>({
        resource: 'purchase_orders',
        id,
    });

    const [page, setPage] = React.useState(0);

    const [rowsPerPage, setRowsPerPage] = React.useState(5);
    const {
        data: items,
        isLoading: itemsLoading,
        refetch,
    } = useList<any>({
        resource: `purchase_orders/${id}/products`,
        pagination: {
            current: page,
            pageSize: rowsPerPage,
        },
        queryOptions: {
            cacheTime: 0,
        },
    });
    const [isAssigned, setIsAssigned] = useState<boolean>(false);

    const checkAssigned = () => {
        checkPoWasAssigned(id)
            .then((response: any) => {
                console.log('assigned', response);
                if (response.success) {
                    setIsAssigned(response.data);
                }
            })
            .catch((error) => console.log(error));
    };

    // Avoid a layout jump when reaching the last page with empty rows.
    const handleChangeRowsPerPage = (event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        setRowsPerPage(parseInt(event.target.value, 10));
        setPage(0);
    };
    const handleChangePage = (event: React.MouseEvent<HTMLButtonElement> | null, newPage: number) => {
        setPage(newPage);
    };

    const [deadline, setDeadline] = useState<any>(null);

    const [idSelected, setIdSeleted] = React.useState<UUID[]>([]);
    console.log(idSelected);

    const getHistoryPurchaseOrder = () => {
        getHistory('purchase order', id)
            .then((data) => {
                console.log('data his', data);
                setHistories(data);
            })
            .catch((data) => {
                console.log(data);
            });
    };

    useEffect(() => {
        checkAssigned();
        getHistoryPurchaseOrder();
    }, []);

    useEffect(() => {
        if (data?.data) {
            setDeadline(data.data.deadLineToStock);
        }
    }, [data?.data]);
    const { open: toastOpen, close } = useNotification();

    let emptyRows = 0;

    if (items?.data) {
        emptyRows = Math.max(0, rowsPerPage - items?.data.length);
    }

    const [open, setOpen] = React.useState(false);

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    const handleAddProductToPurchaseOrder = (
        productData: AddProduct | any,
        callBackSuccessFull: () => any,
        callBackError: () => any,
    ) => {
        postProductsToPurchaseOrder(id, productData)
            .then((response) => {
                toastOpen?.({
                    key: response.data[0],
                    type: 'success',
                    message: 'Successfully add product',
                    description: '',
                });
                refetch();
                getHistoryPurchaseOrder();
                callBackSuccessFull();
            })
            .catch((error) => {
                toastOpen?.({
                    key: 'error',
                    type: 'error',
                    message: 'Add product fail',
                    description: '',
                });
                getHistoryPurchaseOrder();
                callBackError();
            });
    };

    const refetchRef = React.useRef(null);

    const clickChangeStatus = (status: any) => {
        updateStatusPurchaseOrder(id, status)
            .then((response) => {
                console.log('success', response);
                if (response.success) {
                    toastOpen?.({
                        key: response,
                        type: 'success',
                        message: 'Change status successfully',
                        description: '',
                    });
                } else {
                    toastOpen?.({
                        key: response,
                        type: 'error',
                        message: response.result.message,
                        description: '',
                    });
                }
                setTimeout(() => {
                    getHistoryPurchaseOrder();
                }, 2000);
                document.getElementById('refreshBtn')?.click();
            })
            .catch((error) => {
                console.log('error');
                toastOpen?.({
                    key: 'error',
                    type: 'error',
                    message: 'Some thing wrong',
                    description: '',
                });
            });
    };

    const { mutate } = useDelete();

    const deleteItem = (id: UUID) => {
        mutate(
            {
                id: id,
                resource: 'purchase_orders/details',
                mutationMode: 'undoable',
            },
            {
                onSuccess(data, variables, context) {
                    refetch();
                },
            },
        );
    };

    console.log('dât', data);

    const [isChangingDeadline, setIsChangingDeadline] = useState<boolean>(false);

    const onClickDeadlineChangeBtn = () => {};

    function handleSubmit(poId: any, selectedEmployees: any, callBack: (...params: any) => any): void {
        assignEmployeeToPo(poId, selectedEmployees)
            .then((response: any) => {
                // setIsAssigned(false);
                document.getElementById('refreshBtn')?.click();
                console.log(response);
                callBack();
            })
            .catch((error) => console.error(error));
    }

    return (
        <Show
            isLoading={isLoading}
            canDelete={false}
            canEdit={false}
            headerButtons={<RefreshButton id="refreshBtn" recordItemId={id} resource="purchase_orders" />}
        >
            {data?.data ? (
                <div className={cx('wrapper')}>
                    <Grid2 container columns={24} spacing={4}>
                        <Grid2 className={cx('main-content')} xs={16}>
                            {isLoading ? (
                                <Skeleton variant="rectangular" width={'100%'} height={'100%'} />
                            ) : (
                                <Box
                                    sx={{
                                        width: '100%',
                                        height: '100%',
                                        borderRadius: 1,
                                        // backgroundColor: theme.palette.mode === 'dark' ? '#31363F' : '#EEEEEE',
                                        paddingX: 2,
                                        paddingTop: 2,
                                    }}
                                >
                                    <div className={cx('po_info')}>
                                        <h2>Purchase Order Info</h2>
                                        <div className={cx('po_info_field')}>
                                            <p className={cx('title')}>Purchase Order Code</p>
                                            <p>: {data?.data.poCode}</p>
                                        </div>
                                        <div className={cx('po_info_field')}>
                                            <p className={cx('title')}>Reference Number</p>
                                            <p>: {data?.data.referenceNumber}</p>
                                        </div>
                                        <div className={cx('po_info_field')}>
                                            <p className={cx('title')}>Supplier</p>
                                            <p>: {data?.data.supplierName}</p>
                                        </div>
                                        <div className={cx('po_info_field')}>
                                            <p className={cx('title')}>Warehouse</p>
                                            <p>: {data?.data.warehouseName}</p>
                                        </div>
                                        <div className={cx('po_info_field')}>
                                            <p className={cx('title')}>Inbound Date</p>
                                            <p>: {data?.data.inboundDate}</p>
                                        </div>
                                        <div className={cx('po_info_field')}>
                                            <p className={cx('title')}>Arrival Date</p>
                                            <p>: {data?.data.arrivalDate}</p>
                                        </div>
                                        <div className={cx('po_info_field')}>
                                            <p className={cx('title')}>Employee Assigned</p>

                                            <p>
                                                :{' '}
                                                <Tooltip title={data?.data.employeeFullName}>
                                                    {data?.data.employeeName}
                                                </Tooltip>
                                            </p>
                                        </div>
                                        <div className={cx('po_info_field')}>
                                            <p className={cx('title')}>Deadline to stock</p>

                                            {/* <Grid2 xs={6}> */}
                                            <TextField
                                                margin="normal"
                                                disabled={!isChangingDeadline}
                                                // fullWidth
                                                sx={{ width: 300 }}
                                                InputLabelProps={{ shrink: true }}
                                                type={isChangingDeadline ? 'date' : 'text'}
                                                name="arrivalDate"
                                                value={deadline}
                                                onChange={(event) => {
                                                    console.log(event.target.value);
                                                    setDeadline(event.target.value);
                                                }}
                                                inputProps={{
                                                    autoComplete: 'new-password',
                                                    form: {
                                                        autocomplete: 'off',
                                                    },
                                                }}
                                                InputProps={{
                                                    endAdornment: (
                                                        <InputAdornment position="end">
                                                            <IconButton
                                                                aria-label="toggle password visibility"
                                                                onClick={() => {
                                                                    if (
                                                                        isChangingDeadline &&
                                                                        deadline &&
                                                                        deadline !== data?.data.deadLineToStock
                                                                    ) {
                                                                        updateDeadlinePurchaseOrder(id, deadline)
                                                                            .then((response) => {
                                                                                if (response.success) {
                                                                                    toastOpen?.({
                                                                                        key: response,
                                                                                        type: 'success',
                                                                                        message:
                                                                                            'Change deadline to stock successfully',
                                                                                        description: '',
                                                                                    });
                                                                                } else {
                                                                                    toastOpen?.({
                                                                                        key: response,
                                                                                        type: 'error',
                                                                                        message:
                                                                                            response.result.message,
                                                                                        description: '',
                                                                                    });
                                                                                }
                                                                                setTimeout(() => {
                                                                                    getHistoryPurchaseOrder();
                                                                                }, 2000);
                                                                            })
                                                                            .catch((error) => {
                                                                                console.log('error');
                                                                                toastOpen?.({
                                                                                    key: 'error',
                                                                                    type: 'error',
                                                                                    message: 'Some thing wrong',
                                                                                    description: '',
                                                                                });
                                                                            });
                                                                    }
                                                                    setIsChangingDeadline((prev) => !prev);
                                                                }}
                                                            >
                                                                {isChangingDeadline ? <SaveIcon /> : <EditIcon />}
                                                            </IconButton>
                                                        </InputAdornment>
                                                    ),
                                                }}
                                            />
                                            {/* </Grid2> */}
                                        </div>
                                        <StatusLine currentStatus={data?.data.status} />
                                    </div>

                                    <div className={cx('product_list')}>
                                        <div className={cx('add-div')}>
                                            {data?.data.status === 'Draft' && (
                                                <Button
                                                    onClick={!open ? handleClickOpen : handleClose}
                                                    variant="contained"
                                                >
                                                    {!open ? "Open list supplier's item" : "Close list supplier's item"}
                                                </Button>
                                            )}
                                        </div>
                                        <div>
                                            {open && (
                                                <SupplierProductTable
                                                    supplierId={data?.data.supplierId}
                                                    setIdSelected={setIdSeleted}
                                                    onSaveData={handleAddProductToPurchaseOrder}
                                                />
                                            )}
                                        </div>
                                        <div>
                                            <div style={{ display: 'flex', gap: 10 }}>
                                                <StatusButton
                                                    onClick={clickChangeStatus}
                                                    currentStatus={data?.data.status}
                                                />
                                                <AssignPO
                                                    handleSublit={handleSubmit}
                                                    isAssigned={isAssigned}
                                                    poId={id}
                                                    currentStatus={data?.data.status}
                                                />
                                            </div>
                                            <h3>Order Detail</h3>
                                            <TableContainer style={{ maxHeight: 'calc(53px * 10)' }} component={Paper}>
                                                <Table stickyHeader sx={{ minWidth: 500 }} aria-label="user table">
                                                    <TableHead>
                                                        <TableRow>
                                                            {PurchaseOrderDetailKeyHeader.map((header) => (
                                                                <StyledTableCell style={{ minWidth: header.minWidth }}>
                                                                    {header.title}&nbsp;
                                                                    {header.description !== '' && (
                                                                        <Tooltip title={header.description}>
                                                                            <HelpIcon sx={{ fontSize: 13 }} />
                                                                        </Tooltip>
                                                                    )}
                                                                </StyledTableCell>
                                                            ))}
                                                            {data?.data.status === 'Draft' && (
                                                                <StickyStyledTableCell
                                                                    style={{ minWidth: 100 }}
                                                                    align="right"
                                                                >
                                                                    Actions
                                                                </StickyStyledTableCell>
                                                            )}
                                                        </TableRow>
                                                    </TableHead>
                                                    <TableBody>
                                                        {itemsLoading ? (
                                                            <TableRowsLoader
                                                                rowsNum={rowsPerPage}
                                                                fieldsNum={PurchaseOrderDetailKeyHeader.length}
                                                            />
                                                        ) : (
                                                            items?.data.map((row) => (
                                                                <TableRow key={row.productId}>
                                                                    <TableCell width={200} component="th" scope="row">
                                                                        {row.product.productName}
                                                                    </TableCell>
                                                                    <TableCell width={100} component="th" scope="row">
                                                                        {row.product.productCode}
                                                                    </TableCell>
                                                                    <TableCell width={150} component="th" scope="row">
                                                                        {row.product.productCategoryDTO.categoryName}
                                                                    </TableCell>
                                                                    <TableCell width={200} component="th" scope="row">
                                                                        {row.product.reorderQuantity}
                                                                    </TableCell>
                                                                    <TableCell width={200} component="th" scope="row">
                                                                        {row.product.unit}
                                                                    </TableCell>
                                                                    <TableCell width={200} component="th" scope="row">
                                                                        {row.quantity}
                                                                    </TableCell>
                                                                    <TableCell width={200} component="th" scope="row">
                                                                        {row.stockedQuantity || 0}
                                                                    </TableCell>
                                                                    {data?.data.status === 'Draft' && (
                                                                        <StickyStyledTableCell align="right">
                                                                            <DeleteButtonIcon
                                                                                onDelete={() => {
                                                                                    deleteItem(
                                                                                        row.purchaseOrderDetailId,
                                                                                    );
                                                                                }}
                                                                            />
                                                                        </StickyStyledTableCell>
                                                                    )}
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
                                                                rowsPerPageOptions={[
                                                                    5,
                                                                    10,
                                                                    25,
                                                                    { label: 'All', value: -1 },
                                                                ]}
                                                                colSpan={7}
                                                                count={items ? items.total : 0}
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
                                    </div>
                                </Box>
                            )}
                        </Grid2>
                        <Grid2 xs={8}>
                            <Box
                                sx={{
                                    width: '100%',
                                    borderRadius: 1,
                                    paddingX: 2,
                                    paddingTop: 2,
                                }}
                            >
                                <ChatUI
                                    refetch={getHistoryPurchaseOrder}
                                    agentId={id}
                                    agentType="purchase order"
                                    data={histories}
                                    title="Purchase Order"
                                />
                            </Box>
                        </Grid2>
                    </Grid2>
                </div>
            ) : (
                <NotFoundPage />
            )}
        </Show>
    );
};
