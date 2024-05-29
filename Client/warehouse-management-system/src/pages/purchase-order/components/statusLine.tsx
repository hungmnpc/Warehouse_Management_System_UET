import { Tooltip } from '@mui/material';
import { useTheme } from '@mui/system';
import { ItemDirective, ItemsDirective, TimelineComponent } from '@syncfusion/ej2-react-layouts';

interface Props {
    currentStatus: string | any;
    isAssigned?: boolean;
}

export const statuses = ['Draft', 'Incoming', 'Received and Requires Warehousing', 'Warehousing', 'Stocked'];

export const StatusLine = ({ currentStatus }: Props) => {
    const theme = useTheme();
    console.log('statuses', statuses);
    const getStatus = (status: string) => {
        if (status === 'Received and Requires Warehousing') {
            return 'R & RW';
        }
        return status;
    };

    let indexOfCurrent = statuses.findIndex((value) => value === currentStatus);
    return (
        <div className="status-line">
            <TimelineComponent orientation="Horizontal">
                <ItemsDirective>
                    {statuses.map((status, index) => {
                        let dotCss =
                            indexOfCurrent === index ? 'state-progress' : index < indexOfCurrent ? 'state-success' : '';
                        let cssClass =
                            indexOfCurrent <= index ? 'intermediate' : index < indexOfCurrent ? 'completed' : '';
                        if (theme.palette.mode == 'light') {
                            cssClass += ' light';
                        } else {
                            cssClass += ' dark';
                        }

                        return (
                            <ItemDirective
                                dotCss={dotCss}
                                cssClass={cssClass}
                                content={getStatus(status)}
                                key={index}
                            ></ItemDirective>
                        );
                    })}
                </ItemsDirective>
            </TimelineComponent>
        </div>
    );
};
