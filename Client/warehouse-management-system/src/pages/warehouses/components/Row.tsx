import { TableRow, TableCell, IconButton, Collapse, Typography, Table, TableHead, TableBody } from '@mui/material';
import { Box } from '@mui/system';
import React from 'react';
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp';

export function Row(props: { row: any }) {
    const { row } = props;
    const [open, setOpen] = React.useState(false);

    const backgroundColor = row.quantityAvailable <= row.reorderPoint ? 'red' : '';

    return (
        <React.Fragment>
            <TableRow sx={{ '& > *': { borderBottom: 'unset' }, backgroundColor: backgroundColor }}>
                <TableCell width={50}>
                    <IconButton aria-label="expand row" size="small" onClick={() => setOpen(!open)}>
                        {open ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
                    </IconButton>
                </TableCell>
                <TableCell component="th" scope="row">
                    {row.productName}
                </TableCell>
                <TableCell width={200}>{row.quantityAvailable}</TableCell>
                <TableCell width={200}>{row.maximumStockLevel}</TableCell>
                <TableCell width={200}>{row.minimumStockLevel}</TableCell>
                <TableCell width={200}>{row.reorderPoint}</TableCell>
            </TableRow>
            <TableRow>
                <TableCell style={{ paddingBottom: 0, paddingTop: 0 }} colSpan={6}>
                    <Collapse in={open} timeout="auto" unmountOnExit>
                        <Box sx={{ margin: 1 }}>
                            <Typography variant="h6" gutterBottom component="div">
                                Detail Location
                            </Typography>
                            <Table size="medium" aria-label="purchases">
                                <TableHead>
                                    <TableRow>
                                        <TableCell>Bin</TableCell>
                                        <TableCell>Area</TableCell>
                                        <TableCell>Quantity</TableCell>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {row.detail.map((detail: any, index: any) => (
                                        <TableRow key={index}>
                                            <TableCell width={200} component="th" scope="row">
                                                {detail.binName}
                                            </TableCell>
                                            <TableCell>
                                                {detail.areaName} ({detail.binName.split('-')[0]})
                                            </TableCell>
                                            <TableCell width={200}>{detail.quantity}</TableCell>
                                            {/* <TableCell>{historyRow.customerId}</TableCell>
                        <TableCell align="right">{historyRow.amount}</TableCell>
                        <TableCell align="right">
                          {Math.round(historyRow.amount * row.price * 100) / 100}
                        </TableCell> */}
                                        </TableRow>
                                    ))}
                                </TableBody>
                            </Table>
                        </Box>
                    </Collapse>
                </TableCell>
            </TableRow>
        </React.Fragment>
    );
}
