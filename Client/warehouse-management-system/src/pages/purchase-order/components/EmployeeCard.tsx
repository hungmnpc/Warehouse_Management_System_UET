import { Card, CardContent, Grid, Typography, Checkbox } from '@mui/material';

interface EmployeeCardProps {
    employee: any;
    onSelect: (id: string, isSelected: boolean) => void;
    selected: boolean;
}

export const EmployeeCard: React.FC<EmployeeCardProps> = ({ employee, onSelect, selected = false }) => {
    const handleCheckboxChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        onSelect(employee.id, event.target.checked);
    };

    console.log('employee', employee);

    return (
        <Card variant="outlined" sx={{ marginBottom: 2, borderRadius: 5, width: 300 }}>
            <CardContent>
                <Grid container alignItems="center">
                    <Grid item xs={8}>
                        <Typography color="textSecondary">{employee.userName}</Typography>
                        <Typography fontSize={12} color="textSecondary">
                            {employee.roleName}
                        </Typography>
                    </Grid>
                    <Grid item xs={4}>
                        <Checkbox checked={selected} onChange={handleCheckboxChange} />
                    </Grid>
                </Grid>
            </CardContent>
        </Card>
    );
};
