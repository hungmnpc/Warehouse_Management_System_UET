import React, { useEffect, useRef, useState } from 'react';
import {
    Grid,
    Card,
    CardContent,
    Typography,
    CardHeader,
    Box,
    Tooltip,
    Button,
    IconButton,
    DialogActions,
    DialogTitle,
    DialogContent,
    Checkbox,
    FormControlLabel,
} from '@mui/material';
import { getBinLocationInAisle } from '../../../utils/request';
import EditIcon from '@mui/icons-material/Edit';
import { styled } from '@mui/material/styles';
import Dialog from '@mui/material/Dialog';
import classNames from 'classnames/bind';
import style from '../WarehouseShow.module.scss';
import ReactToPrint from 'react-to-print';

const cx = classNames.bind(style);

import { BinConfig } from './BinConfig';
import { BarcodeGenerator } from '../../../components/barcode/BarcodeGenerator';
import { BinBarcodePrint } from './BinBarCodesPrint';
import { ProductsInBin } from './ProductInBin';
import QRCode from 'qrcode.react';

interface RackGridProps {
    aisle: any;
}

const BootstrapDialog = styled(Dialog)(({ theme }) => ({
    '& .MuiDialogContent-root': {
        padding: theme.spacing(2),
    },
    '& .MuiDialogActions-root': {
        padding: theme.spacing(1),
    },
}));

const RackGrid = ({ aisle }: RackGridProps) => {
    const [data, setData] = useState<any[]>([]);

    const barcodesRef = useRef<any>();

    const getData = () => {
        console.log(aisle);
        getBinLocationInAisle(aisle.aisleId)
            .then((response) => {
                console.log('response bin', response);
                setData(response.data);
            })
            .catch((error) => {
                console.error(error);
            });
    };

    useEffect(() => {
        if (aisle) {
            getData();
        }
    }, [aisle]);

    const [open, setOpen] = useState<boolean>(false);
    const [barcodeSelected, setBarcodeSelected] = useState<any>(null);

    const handleClickEdit = (barcode: any) => {
        if (barcode) {
            setBarcodeSelected(barcode);
            setOpen(true);
        }
    };

    const handleCloseEdit = () => {
        setBarcodeSelected(null);
        setOpen(false);
    };

    const [isGenerateBarcode, setIsGenerateBarcode] = useState<boolean>(false);

    const [generateBarcodeValues, setGenerateBarcodeValues] = useState<any[]>([]);

    return (
        <Box
            sx={{
                overflowX: 'auto', // Cho phép cuộn ngang
                whiteSpace: 'nowrap', // Ngăn xuống hàng
                padding: '16px', // Thêm một chút padding để dễ cuộn hơn
                display: 'flex',
                flexDirection: 'column',
            }}
        >
            <FormControlLabel
                control={<Checkbox checked={isGenerateBarcode} onClick={() => setIsGenerateBarcode((prev) => !prev)} />}
                label="Generate QrCode"
            />
            <div>
                {data.map((rack) => (
                    <Card
                        key={rack.rackId}
                        sx={{
                            display: 'inline-block', // Để card nằm trong cùng một dòng
                            width: 'fit-content', // Cho phép card chỉ rộng vừa đủ với nội dung
                            // Khoảng cách giữa các card
                        }}
                    >
                        <CardHeader title={`${rack.rackName}`} />
                        <CardContent>
                            {rack.levelRackLocations.toReversed().map((level: any) => (
                                <Box key={level.levelRackId} mb={2}>
                                    <Grid container spacing={1}>
                                        {level.binLocations.map((bin: any) => (
                                            <Grid item key={bin.binId}>
                                                <Tooltip
                                                    title={
                                                        <Box
                                                            sx={{
                                                                padding: '8px',
                                                                display: 'flex',
                                                                flexDirection: 'column',
                                                                alignItems: 'center',
                                                                alignContent: 'center',
                                                            }}
                                                        >
                                                            {/* <QRCode size={100} value={bin.barcode} renderAs="canvas" /> */}
                                                            {/* <BarcodeGenerator
                                                                width={0.5}
                                                                displayValue={true}
                                                                value={bin.barcode}
                                                            /> */}
                                                            <Typography>{bin.barcode}</Typography>
                                                            <ProductsInBin binId={bin.binId} />
                                                        </Box>
                                                    }
                                                    placement="top"
                                                >
                                                    <Box
                                                        sx={{
                                                            cursor: bin.disable ? 'default' : 'pointer',
                                                            width: '50px', // Kích thước của hình chữ nhật
                                                            height: '50px',
                                                            borderRadius: '10px',
                                                            backgroundColor: bin.disable
                                                                ? '#EEEEEE'
                                                                : bin.occupied
                                                                ? '#ffb550'
                                                                : '#ffe8ca', // Màu sắc để chỉ trạng thái
                                                            display: 'flex',
                                                            alignItems: 'center',
                                                            justifyContent: 'center',
                                                            '&:hover': {
                                                                '& .bin_edit_btn': {
                                                                    visibility: 'visible', // Hiển thị nút chỉnh sửa khi hover vào Box
                                                                },
                                                            },
                                                        }}
                                                    >
                                                        {isGenerateBarcode ? (
                                                            <Checkbox
                                                                onClick={() => {
                                                                    if (generateBarcodeValues.includes(bin.barcode)) {
                                                                        setGenerateBarcodeValues((prev) =>
                                                                            prev.filter(
                                                                                (value) => value !== bin.barcode,
                                                                            ),
                                                                        );
                                                                    } else {
                                                                        setGenerateBarcodeValues((prev) => [
                                                                            ...prev,
                                                                            bin.barcode,
                                                                        ]);
                                                                    }
                                                                }}
                                                                checked={generateBarcodeValues.includes(bin.barcode)}
                                                                sx={{ color: 'black' }}
                                                            />
                                                        ) : (
                                                            <IconButton
                                                                className="bin_edit_btn"
                                                                sx={{
                                                                    visibility: 'hidden',
                                                                    color: 'black',
                                                                }}
                                                                onClick={() => handleClickEdit(bin.barcode)}
                                                            >
                                                                <EditIcon />
                                                            </IconButton>
                                                        )}
                                                    </Box>
                                                </Tooltip>
                                            </Grid>
                                        ))}
                                    </Grid>
                                </Box>
                            ))}
                        </CardContent>
                    </Card>
                ))}
            </div>
            {isGenerateBarcode && (
                <div style={{ display: 'none' }}>
                    <BinBarcodePrint
                        title="Bin Location Barcodes"
                        ref={barcodesRef}
                        values={generateBarcodeValues}
                        displayValue={true}
                    />
                </div>
            )}
            {isGenerateBarcode && (
                <ReactToPrint
                    trigger={() => (
                        <Button sx={{ width: 100 }} variant="contained">
                            Print
                        </Button>
                    )}
                    content={() => barcodesRef.current}
                />
            )}
            <BinConfig open={open} handleClose={handleCloseEdit} binBarCode={barcodeSelected} />
        </Box>
    );
};

export default RackGrid;
