import { Skeleton, TableCell, TableRow } from '@mui/material';

interface TableRowsLoaderProps {
    rowsNum?: number;
    fieldsNum?: number;
}

export const TableRowsLoader = ({ rowsNum, fieldsNum }: TableRowsLoaderProps) => {
    return [...Array(rowsNum)].map((row, index) => (
        <TableRow key={index}>
            {[...Array(fieldsNum)].map((field, index) => (
                <TableCell key={index} component="th" scope="row">
                    <Skeleton animation="wave" variant="text" />
                </TableCell>
            ))}
        </TableRow>
    ));
};
