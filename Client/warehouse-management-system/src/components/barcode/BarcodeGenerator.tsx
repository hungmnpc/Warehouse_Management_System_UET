import { useRef } from 'react';
import classNames from 'classnames/bind';
import styles from './BarcodeGenerator.module.scss';
import Barcode from 'react-barcode';

const cx = classNames.bind(styles);

interface BarcodeGeneratorProps {
    value: string | any;
    displayValue?: boolean;
    width?: number;
    height?: number;
}

export const BarcodeGenerator = ({ width = 1, height = 30, value, displayValue = true }: BarcodeGeneratorProps) => {
    return <Barcode displayValue={displayValue} width={width} height={height} value={value} />;
};
