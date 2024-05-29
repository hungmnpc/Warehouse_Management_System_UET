import { PurchaseOrderStatus } from '../status';
import classNames from 'classnames/bind';
import styles from '../PurchaseOrder.module.scss';
import { useTheme } from '@mui/system';
import { PurchaseOrderStatusRaw } from '../../../constant';
import { statuses } from './statusLine';

const cx = classNames.bind(styles);

function snakeCaseToTitleCase(str: string) {
    // Chia chuỗi bằng dấu gạch dưới
    const words = str.split('_');

    // Chuyển đổi mỗi từ sang Title Case
    const titleCaseWords = words.map((word, index) => {
        // Đổi từ đầu tiên thành chữ hoa, còn lại thành chữ thường
        return word.charAt(0).toUpperCase() + word.slice(1).toLowerCase();
    });

    // Nối các từ lại với khoảng trắng
    return titleCaseWords.join(' ');
}

export const StatusPurchaseOrder = ({ value }: { value: string }) => {
    // const statuses = Object.entries(PurchaseOrderStatusRaw).sort((a, b) => a[1] - b[1]);
    const statusValue = statuses.findIndex((statuses) => statuses === value);
    console.log(statusValue);

    const theme = useTheme();
    let color;
    switch (statusValue) {
        case -1:
            color = '#F3CA52';
            break;
        case 0:
            color = '#808080';
            break;
        case 1:
            color = '#FFA500';
            break;
        case 2:
            color = '#FFFF00';
            break;
        case 3:
            color = '#0000FF';
            break;
        case 4:
            color = '#008000';
            break;

        default:
            break;
    }

    const style = theme.palette.mode === 'light' ? { backgroundColor: color } : { borderColor: color };
    return (
        <div className={cx('status-cpn')} style={style}>
            {snakeCaseToTitleCase(value)}
        </div>
    );
};
