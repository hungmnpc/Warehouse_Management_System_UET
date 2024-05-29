import classNames from 'classnames/bind';
import styles from './status.module.scss';

const cx = classNames.bind(styles);

interface StatusComponentProps {
    value: any;
    bgcolor: string | any;
    txtColor: string;
}

export const StatusComponent = ({ value, bgcolor, txtColor }: StatusComponentProps) => {
    return (
        <div className={cx('wrapper')} style={{ backgroundColor: bgcolor, color: txtColor, border: '2px solid black' }}>
            {value}
        </div>
    );
};
