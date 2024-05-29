import {
    Box,
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
    Tooltip,
} from '@mui/material';
import { styled } from '@mui/material/styles';
import TablePaginationActions from '@mui/material/TablePagination/TablePaginationActions';
import { productHeaderTableDialogKey, productHeaderTableKey } from '../../constant';
import { StyledTableCell, StickyStyledTableCell } from '../../pages/users';
import { DeleteButtonIcon } from '../buttons/button';
import { TableRowsLoader } from '../skeleton';
import { CustomTablePagination } from '../table';
import HelpIcon from '@mui/icons-material/Help';
import QrCodeIcon from '@mui/icons-material/QrCode';
import { usePermissions, useList, useResource, useNavigation } from '@refinedev/core';
import React from 'react';
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
];

interface ProductTableProps {
    ignoreIds: UUID[] | any;
    setIdSelected: (value: any) => void;
}

export const ProductTable = ({ ignoreIds, setIdSelected }: ProductTableProps) => {
    const navigate = useNavigate();
    const [page, setPage] = React.useState(0);
    const { create } = useNavigation();

    const [rowsPerPage, setRowsPerPage] = React.useState(9999999);
    const { data, isLoading } = useList<IProduct>({
        resource: 'products',
        pagination: {
            current: page,
            pageSize: rowsPerPage,
        },
        filters: [
            {
                field: 'idNotIn',
                operator: 'eq',
                value: ignoreIds,
            },
        ],
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

    if (data?.data) {
        emptyRows = Math.max(0, rowsPerPage - data?.data.length);
    }

    return (
        <>
            {data && (
                <DataGrid
                    rows={data.data}
                    columns={columns}
                    getRowId={(row) => {
                        return row.productId;
                    }}
                    initialState={{
                        pagination: {
                            paginationModel: { page: 0, pageSize: 5 },
                        },
                    }}
                    onRowSelectionModelChange={(newRowSelectionModel: any) => {
                        console.log('newRowSelectionModel', newRowSelectionModel);
                        setIdSelected(newRowSelectionModel);
                    }}
                    pageSizeOptions={[5, 10]}
                    checkboxSelection
                    slots={{
                        toolbar: GridToolbar,
                        noRowsOverlay: CustomNoRowsOverlay,
                    }}
                />
            )}
        </>
    );
};

const StyledGridOverlay = styled('div')(({ theme }) => ({
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    justifyContent: 'center',
    height: '100%',
    '& .ant-empty-img-1': {
        fill: theme.palette.mode === 'light' ? '#aeb8c2' : '#262626',
    },
    '& .ant-empty-img-2': {
        fill: theme.palette.mode === 'light' ? '#f5f5f7' : '#595959',
    },
    '& .ant-empty-img-3': {
        fill: theme.palette.mode === 'light' ? '#dce0e6' : '#434343',
    },
    '& .ant-empty-img-4': {
        fill: theme.palette.mode === 'light' ? '#fff' : '#1c1c1c',
    },
    '& .ant-empty-img-5': {
        fillOpacity: theme.palette.mode === 'light' ? '0.8' : '0.08',
        fill: theme.palette.mode === 'light' ? '#f5f5f5' : '#fff',
    },
}));

function CustomNoRowsOverlay() {
    return (
        <StyledGridOverlay>
            <svg width="120" height="100" viewBox="0 0 184 152" aria-hidden focusable="false">
                <g fill="none" fillRule="evenodd">
                    <g transform="translate(24 31.67)">
                        <ellipse className="ant-empty-img-5" cx="67.797" cy="106.89" rx="67.797" ry="12.668" />
                        <path
                            className="ant-empty-img-1"
                            d="M122.034 69.674L98.109 40.229c-1.148-1.386-2.826-2.225-4.593-2.225h-51.44c-1.766 0-3.444.839-4.592 2.225L13.56 69.674v15.383h108.475V69.674z"
                        />
                        <path
                            className="ant-empty-img-2"
                            d="M33.83 0h67.933a4 4 0 0 1 4 4v93.344a4 4 0 0 1-4 4H33.83a4 4 0 0 1-4-4V4a4 4 0 0 1 4-4z"
                        />
                        <path
                            className="ant-empty-img-3"
                            d="M42.678 9.953h50.237a2 2 0 0 1 2 2V36.91a2 2 0 0 1-2 2H42.678a2 2 0 0 1-2-2V11.953a2 2 0 0 1 2-2zM42.94 49.767h49.713a2.262 2.262 0 1 1 0 4.524H42.94a2.262 2.262 0 0 1 0-4.524zM42.94 61.53h49.713a2.262 2.262 0 1 1 0 4.525H42.94a2.262 2.262 0 0 1 0-4.525zM121.813 105.032c-.775 3.071-3.497 5.36-6.735 5.36H20.515c-3.238 0-5.96-2.29-6.734-5.36a7.309 7.309 0 0 1-.222-1.79V69.675h26.318c2.907 0 5.25 2.448 5.25 5.42v.04c0 2.971 2.37 5.37 5.277 5.37h34.785c2.907 0 5.277-2.421 5.277-5.393V75.1c0-2.972 2.343-5.426 5.25-5.426h26.318v33.569c0 .617-.077 1.216-.221 1.789z"
                        />
                    </g>
                    <path
                        className="ant-empty-img-3"
                        d="M149.121 33.292l-6.83 2.65a1 1 0 0 1-1.317-1.23l1.937-6.207c-2.589-2.944-4.109-6.534-4.109-10.408C138.802 8.102 148.92 0 161.402 0 173.881 0 184 8.102 184 18.097c0 9.995-10.118 18.097-22.599 18.097-4.528 0-8.744-1.066-12.28-2.902z"
                    />
                    <g className="ant-empty-img-4" transform="translate(149.65 15.383)">
                        <ellipse cx="20.654" cy="3.167" rx="2.849" ry="2.815" />
                        <path d="M5.698 5.63H0L2.898.704zM9.259.704h4.985V5.63H9.259z" />
                    </g>
                </g>
            </svg>
            <Box sx={{ mt: 1 }}>No Product to show</Box>
        </StyledGridOverlay>
    );
}
