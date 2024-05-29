import {
    Grid,
    TextField,
    Autocomplete,
    FormControlLabel,
    Checkbox,
    Button,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle,
    IconButton,
} from '@mui/material';
import { Container } from '@mui/system';
import { useState } from 'react';
import CloseIcon from '@mui/icons-material/Close';

interface CreateAisleDialogProps {
    handleClose: () => any;
    open?: boolean;

    handleSubmitDialog: (...rest: any) => any;
}

export const CreataeAisleDialog = ({ handleClose, open = false, handleSubmitDialog }: CreateAisleDialogProps) => {
    const [formData, setFormData] = useState<any>({
        aisleName: '',
        locationType: '',
    });

    const [createRackType, setCreateRackType] = useState({
        numberOfRack: null,
        rackPrefix: '',
        numberOfLevelEachRack: null,
        multipleProduct: false,
        numberOfBinEachLevel: null,
    });

    const [createFloorType, setCreateFloorType] = useState({
        numberOfFloorStorage: null,
        floorStoragePrefix: '',
        numberOfBin: null,
        multipleProduct: false,
    });

    const handleChange = (e: any) => {
        const { name, value } = e.target;
        setFormData((prevState: any) => ({
            ...prevState,
            [name]: value,
        }));
    };

    const handleRackChange = (e: any) => {
        const { name, value, type } = e.target;
        console.log(type);
        setCreateRackType((prevState: any) => ({
            ...prevState,
            [name]: type === 'number' ? parseInt(value) : value,
        }));
    };

    const handleFloorChange = (e: any) => {
        const { name, value, type } = e.target;
        setCreateFloorType((prevState: any) => ({
            ...prevState,
            [name]: type === 'number' ? parseInt(value) : value,
        }));
    };

    const handleAutocompleteChange = (event: any, value: any) => {
        setFormData((prevState: any) => ({
            ...prevState,
            locationType: value,
        }));
    };

    const handleCheckboxChange = (e: any) => {
        const { name, checked } = e.target;
        setFormData((prevState: any) => ({
            ...prevState,
            [name]: checked,
        }));
    };

    const handleRackCheckboxChange = (e: any) => {
        const { name, checked } = e.target;
        setCreateRackType((prevState: any) => ({
            ...prevState,
            [name]: checked,
        }));
    };

    const handleFloorCheckboxChange = (e: any) => {
        const { name, checked } = e.target;
        setCreateFloorType((prevState: any) => ({
            ...prevState,
            [name]: checked,
        }));
    };

    const handleSubmit = (e: any) => {
        e.preventDefault();
        // Xử lý dữ liệu ở đây (ví dụ: gửi đến backend)

        let value = { ...formData };
        if (formData.locationType === 'FLOOR') {
            value = {
                ...value,
                createFloorStorage: {
                    ...createRackType,
                },
            };
        } else if (formData.locationType === 'RACK') {
            value = {
                ...value,
                createRackStorage: createRackType,
            };
        }
        console.log(value);
        handleSubmitDialog(value);
    };
    return (
        <Dialog sx={{ minHeight: 900 }} onClose={handleClose} aria-labelledby="customized-dialog-title" open={open}>
            <DialogTitle sx={{ m: 0, p: 2 }} id="customized-dialog-title">
                Create Aisle
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
                            <Grid item xs={12}>
                                <TextField
                                    name="aisleName"
                                    label="Aisle Name"
                                    variant="outlined"
                                    fullWidth
                                    value={formData.aisleName}
                                    onChange={handleChange}
                                />
                            </Grid>
                            <Grid item xs={12}>
                                <Autocomplete
                                    options={['RACK', 'FLOOR']}
                                    renderInput={(params) => (
                                        <TextField {...params} label="Location Type" variant="outlined" />
                                    )}
                                    value={formData.locationType}
                                    onChange={handleAutocompleteChange}
                                />
                            </Grid>
                            {formData.locationType === 'FLOOR' && (
                                <Grid item xs={12}>
                                    <TextField
                                        name="numberOfFloorStorage"
                                        label="Number of Floor Storage"
                                        type="number"
                                        variant="outlined"
                                        fullWidth
                                        value={createFloorType.numberOfFloorStorage}
                                        onChange={handleFloorChange}
                                    />
                                    <TextField
                                        name="floorStoragePrefix"
                                        label="Floor Storage Prefix"
                                        variant="outlined"
                                        fullWidth
                                        value={createFloorType.floorStoragePrefix}
                                        onChange={handleFloorChange}
                                    />
                                    <TextField
                                        name="numberOfBin"
                                        label="Number of Bin"
                                        type="number"
                                        variant="outlined"
                                        fullWidth
                                        value={createFloorType.numberOfBin}
                                        onChange={handleFloorChange}
                                    />
                                    <FormControlLabel
                                        control={<Checkbox name="multipleProduct" color="primary" />}
                                        label="Multiple Product"
                                        checked={createFloorType.multipleProduct}
                                        onChange={handleFloorCheckboxChange}
                                    />
                                </Grid>
                            )}
                            {formData.locationType === 'RACK' && (
                                <>
                                    <Grid item xs={6}>
                                        <TextField
                                            name="numberOfRack"
                                            label="Number of Rack"
                                            type="number"
                                            variant="outlined"
                                            fullWidth
                                            value={createRackType.numberOfRack}
                                            onChange={handleRackChange}
                                        />
                                    </Grid>
                                    <Grid item xs={6}>
                                        <TextField
                                            name="rackPrefix"
                                            label="Rack Prefix"
                                            variant="outlined"
                                            fullWidth
                                            value={createRackType.rackPrefix}
                                            onChange={handleRackChange}
                                        />
                                    </Grid>
                                    <Grid item xs={6}>
                                        <TextField
                                            name="numberOfLevelEachRack"
                                            label="Number of Level Each Rack"
                                            type="number"
                                            variant="outlined"
                                            fullWidth
                                            value={createRackType.numberOfLevelEachRack}
                                            onChange={handleRackChange}
                                        />
                                    </Grid>
                                    <Grid item xs={6}>
                                        <TextField
                                            name="numberOfBinEachLevel"
                                            label="Number of Bin Each Level"
                                            type="number"
                                            variant="outlined"
                                            fullWidth
                                            value={createRackType.numberOfBinEachLevel}
                                            onChange={handleRackChange}
                                        />
                                    </Grid>
                                    <Grid item xs={6}>
                                        <FormControlLabel
                                            control={<Checkbox name="multipleProduct" color="primary" />}
                                            label="Multiple Product"
                                            checked={createRackType.multipleProduct}
                                            onChange={handleRackCheckboxChange}
                                        />
                                    </Grid>
                                </>
                            )}
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
