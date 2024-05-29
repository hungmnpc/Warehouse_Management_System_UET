import { Paper } from '@mui/material';
import Grid2 from '@mui/material/Unstable_Grid2/Grid2';
import Barcode from 'react-barcode';
import { BarcodeGenerator } from '../../../components/barcode/BarcodeGenerator';
import React from 'react';
import QRCode from 'qrcode.react';

interface BinBarcodePrintProps extends React.ComponentPropsWithoutRef<'div'> {
    values: any[];
    displayValue?: boolean;
    title?: string;
}

export const BinBarcodePrint = React.forwardRef<HTMLDivElement, BinBarcodePrintProps>(
    ({ values, displayValue = true, title = 'Barcode' }: BinBarcodePrintProps, ref) => {
        return (
            <div style={{ padding: 30, textAlign: 'center' }} ref={ref}>
                <h3 style={{ color: 'black' }}>{title}</h3>
                <Grid2 container spacing={2}>
                    {values.map((value, index) => (
                        <Grid2 xs={4}>
                            {/* <Paper style={{ height: 100 }}> */}
                            {/* <BarcodeGenerator height={100} value={value} displayValue={displayValue} /> */}
                            <div
                                style={{
                                    marginTop: 10,
                                    padding: 10,
                                    display: 'flex',
                                    flexDirection: 'column',
                                    justifyContent: 'center',
                                    alignItems: 'center',
                                }}
                            >
                                <QRCode size={200} value={value} renderAs="canvas" />
                                <p style={{ color: 'black' }}>{value}</p>
                            </div>
                            {/* </Paper> */}
                        </Grid2>
                    ))}
                </Grid2>
            </div>
        );
    },
);
