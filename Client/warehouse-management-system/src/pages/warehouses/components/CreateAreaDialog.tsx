import {
    Autocomplete,
    Button,
    CircularProgress,
    Container,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle,
    FormControl,
    FormControlLabel,
    FormGroup,
    FormLabel,
    Grid,
    IconButton,
    InputAdornment,
    Switch,
    TextField,
} from '@mui/material';
import CloseIcon from '@mui/icons-material/Close';
import { useEffect, useState } from 'react';
import { AnyARecord } from 'dns';
import { getAreaGroups } from '../../../utils/request';
import { useCreate } from '@refinedev/core';

interface CreateAreaDialogProps {
    handleClose: () => any;
    open?: boolean;

    handleSubmitDialog: (...rest: any) => any;
}

export const CreateAreaDialog = ({ handleClose, open = false, handleSubmitDialog }: CreateAreaDialogProps) => {
    const [areaInfo, setAreaInfo] = useState({
        areaName: '',
        areaPrefix: '',
        areaGroupId: '',
        areaGroupName: '',
    });

    const [areaGroups, setAreaGroups] = useState<any[]>([]);

    const getValueAreaGroups = () => {
        getAreaGroups()
            .then((response) => {
                console.log(response);
                if (response.success) {
                    setAreaGroups(response.data);
                }
            })
            .catch((error) => console.error(error));
    };

    useEffect(() => {
        getValueAreaGroups();
    }, []);

    const handleChange = (e: any) => {
        const { name, value } = e.target;
        setAreaInfo((prevState) => ({
            ...prevState,
            [name]: value,
        }));
    };

    const handleSubmit = (e: any) => {
        e.preventDefault();
        // Xử lý dữ liệu ở đây (ví dụ: gửi đến backend)
        handleSubmitDialog(areaInfo);
        console.log(areaInfo);
    };

    return (
        <Dialog sx={{ minHeight: 900 }} onClose={handleClose} aria-labelledby="customized-dialog-title" open={open}>
            <DialogTitle sx={{ m: 0, p: 2 }} id="customized-dialog-title">
                Create Area
            </DialogTitle>
            <IconButton
                aria-label="close"
                onClick={handleClose}
                sx={{
                    position: 'absolute',
                    right: 8,
                    top: 8,
                    color: (theme) => theme.palette.grey[500],
                }}
            >
                <CloseIcon />
            </IconButton>
            <DialogContent dividers>
                <Container>
                    <form onSubmit={handleSubmit}>
                        <Grid container spacing={2}>
                            <Grid item xs={6}>
                                <TextField
                                    name="areaName"
                                    label="Area Name"
                                    variant="outlined"
                                    fullWidth
                                    value={areaInfo.areaName}
                                    onChange={handleChange}
                                />
                            </Grid>
                            <Grid item xs={6}>
                                <TextField
                                    name="areaPrefix"
                                    label="Area Prefix"
                                    variant="outlined"
                                    fullWidth
                                    value={areaInfo.areaPrefix}
                                    onChange={handleChange}
                                />
                            </Grid>
                            <Grid item xs={12}>
                                <Autocomplete
                                    disablePortal
                                    fullWidth
                                    value={areaGroups.find((option) => option.key === areaInfo.areaGroupId)}
                                    options={areaGroups}
                                    getOptionLabel={(option) => option.value}
                                    onChange={(_, value) => {
                                        console.log(value);
                                        if (value) {
                                            setAreaInfo((prevState: any) => ({
                                                ...prevState,
                                                areaGroupId: value.key,
                                                areaGroupName: value.value,
                                            }));
                                        }
                                    }}
                                    onInputChange={(event, value) => {
                                        console.log(value);
                                    }}
                                    renderOption={(props, option) => {
                                        return (
                                            <li {...props} data-name={option.key}>
                                                {option.value}
                                            </li>
                                        );
                                    }}
                                    renderInput={(params) => {
                                        console.log('params', params);
                                        return (
                                            <TextField
                                                {...params}
                                                label="Area group"
                                                InputProps={{
                                                    ...params.InputProps,
                                                    endAdornment: <>{params.InputProps.endAdornment}</>,
                                                }}
                                            />
                                        );
                                    }}
                                    ListboxProps={{
                                        style: {
                                            maxHeight: 200,
                                        },
                                    }}
                                />
                            </Grid>
                        </Grid>
                    </form>
                </Container>
            </DialogContent>
            <DialogActions>
                <Button variant="contained" autoFocus onClick={handleSubmit}>
                    Save
                </Button>
            </DialogActions>
        </Dialog>
    );
};
