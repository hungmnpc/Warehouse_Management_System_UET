import {
    TextField,
    FormControl,
    FormLabel,
    FormGroup,
    FormControlLabel,
    Checkbox,
    Button,
    Grid,
    DialogActions,
    DialogContent,
    DialogTitle,
    IconButton,
    styled,
    Dialog,
    InputAdornment,
    Autocomplete,
    CircularProgress,
    Switch,
} from '@mui/material';
import { Container } from '@mui/system';
import { useContext, useEffect, useLayoutEffect, useMemo, useRef, useState } from 'react';
import CloseIcon from '@mui/icons-material/Close';
import { getBinDetail, getProductList } from '../../../utils/request';
import { useCreate, useList } from '@refinedev/core';
import { useAutocomplete } from '@refinedev/mui';
import { useDebounce } from '@uidotdev/usehooks';
import { WarehouseContext } from '../show';

interface BinConfigProps {
    binBarCode: string | any;
    open: boolean;
    handleClose: (...rest: any) => any;
}

const BootstrapDialog = styled(Dialog)(({ theme }) => ({
    '& .MuiDialogContent-root': {
        padding: theme.spacing(2),
    },
    '& .MuiDialogActions-root': {
        padding: theme.spacing(1),
    },
}));

export const BinConfig = ({ binBarCode, open, handleClose }: BinConfigProps) => {
    const [formData, setFormData] = useState<any>({
        singleProduct: false,
        binDescription: '',
        maxStorage: undefined,
        unitStorage: '',
        onlyProductId: '',
        onlyProductName: '',
    });

    const { warehouseId, warehouseName } = useContext(WarehouseContext);

    console.log('warehouseId', warehouseId);

    const [triggerRender, setStriggerRender] = useState<boolean>(true);
    const [loading, setLoading] = useState(false);

    const handleCloseDialog = () => {
        setFormData({
            singleProduct: false,
            binDescription: '',
            maxStorage: undefined,
            unitStorage: '',
            onlyProductId: '',
            onlyProductName: '',
        });
        handleClose();
    };
    const [binId, setBinId] = useState<any>(null);
    const [searchPro, setSearchPro] = useState('');

    const { mutate } = useCreate();

    const [pagePro, setPagePro] = useState(0);

    const [sizePro, setSizePro] = useState(99999);

    const [isLast, setIsLast] = useState(false);

    const [firstOptionAdded, setFirstOptionAdded] = useState<any>(null);

    console.log(isLast);

    const [products, setProducts] = useState<any[]>([]);

    const debouncedSearchTerm = useDebounce(searchPro, 300);

    const getDataProduct = (productName: any) => {
        setLoading(true);
        getProductList(productName, pagePro, sizePro)
            .then((response) => {
                console.log('product', response);
                if (response.success) {
                    if (response.data.pageNumber === 0) {
                        setProducts(response.data.data);
                    } else {
                        setProducts((prevProducts: any[]) => [...prevProducts, ...response.data.data]);
                    }
                    if (response.data.data.length > 0) {
                        setFirstOptionAdded(response.data.data[0].productName);
                    }
                    if (response.data.dataCount < sizePro * (pagePro + 1)) {
                        setIsLast(true);
                    } else {
                        setIsLast(false);
                    }
                    setLoading(false);
                }
            })
            .catch((error) => console.error(error));
    };

    const [selectedProduct, setSelectedProduct] = useState<any>(null);

    const getDataBin = () => {
        if (warehouseId) {
            getBinDetail(binBarCode, warehouseId)
                .then((response) => {
                    if (response.success) {
                        setFormData({
                            ...response.data.binConfigurationDTO,
                            singleProduct:
                                response.data.binLocation.isMultipleProduct == null
                                    ? false
                                    : !response.data.binLocation.isMultipleProduct,
                        });
                        setBinId(response.data.binLocation.binId);
                    }
                })
                .catch((error) => {
                    console.error(error);
                });
        }
    };

    useEffect(() => {
        if (binBarCode) {
            getDataBin();
        }
    }, [binBarCode]);

    useEffect(() => {
        console.log('loadmore');
        getDataProduct(searchPro);
    }, [pagePro, debouncedSearchTerm]);

    useEffect(() => {
        setStriggerRender(!triggerRender);
    }, [binId]);

    const handleSaveConfig = () => {
        let value = {
            ...formData,
            maxStorage: parseInt(formData.maxStorage),
        };
        if (!value.singleProduct) {
            value.maxStorage = null;
            value.unitStorage = null;
            value.onlyProductId = null;
            value.onlyProductName = null;
        } else if (selectedProduct) {
            value.onlyProductId = selectedProduct.productId;
            value.onlyProductName = selectedProduct.productName;
        }
        mutate(
            {
                values: value,
                resource: `warehouses/bins/${binId}/config`,
                successNotification(data, values, resource) {
                    return {
                        message: `Config Bin successfully`,
                        description: 'Success with no errors',
                        type: 'success',
                    };
                },
            },
            {
                onSuccess(data, variables, context) {
                    handleClose();
                },
            },
        );
    };

    const handleChange = (event: any) => {
        const { name, value, type, checked } = event.target;
        setFormData((prevState: any) => ({
            ...prevState,
            [name]: type === 'checkbox' ? checked : value,
        }));
    };

    const { autocompleteProps: unitAutocompleteProps } = useAutocomplete({
        resource: 'units/dropdown',
        dataProviderName: 'products',
    });

    console.log(
        'defaul prod',
        products.find((product) => product.productId === formData.onlyProductId),
    );

    return (
        <BootstrapDialog onClose={handleCloseDialog} aria-labelledby="customized-dialog-title" open={open}>
            <DialogTitle sx={{ m: 0, p: 2 }} id="customized-dialog-title">
                Bin: {binBarCode}
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
                {/* <BinConfig binBarCode={barcodeSelected} /> */}
                <Container maxWidth="md">
                    <form>
                        <Grid container spacing={2}>
                            <Grid item xs={12}>
                                <TextField
                                    InputLabelProps={{ shrink: true }}
                                    name="binDescription"
                                    label="Bin Description"
                                    fullWidth
                                    value={formData.binDescription}
                                    onChange={handleChange}
                                />
                            </Grid>

                            <Grid item xs={12}>
                                <FormControl component="fieldset">
                                    <FormLabel component="legend">Only Product for Storage:</FormLabel>
                                    <FormGroup>
                                        <FormControlLabel
                                            control={
                                                <Switch
                                                    checked={formData.singleProduct}
                                                    onChange={handleChange}
                                                    name="singleProduct"
                                                />
                                            }
                                            label="Single product storage"
                                        />
                                    </FormGroup>
                                </FormControl>
                            </Grid>
                            {formData.singleProduct && (
                                <>
                                    <Grid item xs={6}>
                                        <TextField
                                            name="maxStorage"
                                            label="Max Storage"
                                            fullWidth
                                            value={formData.maxStorage}
                                            InputLabelProps={{ shrink: true }}
                                            onChange={handleChange}
                                            InputProps={{
                                                endAdornment: (
                                                    <InputAdornment position="end">
                                                        {formData.unitStorage}
                                                    </InputAdornment>
                                                ),
                                            }}
                                            inputProps={{
                                                autoComplete: 'new-password',
                                                form: {
                                                    autocomplete: 'off',
                                                },
                                                style: { textAlign: 'right' },
                                            }}
                                        />
                                    </Grid>
                                    <Grid item xs={6}>
                                        {/* <TextField
                                    name="unitStorage"
                                    label="Unit of Storage"
                                    fullWidth
                                    value={formData.unitStorage}
                                    InputLabelProps={{ shrink: true }}
                                    onChange={handleChange}
                                /> */}
                                        <Autocomplete
                                            fullWidth
                                            value={formData.unitStorage}
                                            onChange={(event, newValue) => {
                                                setFormData((prevState: any) => ({
                                                    ...prevState,
                                                    unitStorage: newValue,
                                                }));
                                            }}
                                            options={unitAutocompleteProps.options}
                                            renderInput={(params) => (
                                                <TextField
                                                    {...params}
                                                    label="Unit of Storage"
                                                    InputLabelProps={{ shrink: true }}
                                                />
                                            )}
                                        />
                                    </Grid>
                                    <Grid item xs={12}>
                                        <Autocomplete
                                            disablePortal
                                            fullWidth
                                            loading={loading}
                                            options={products}
                                            getOptionLabel={(option) => option.productName}
                                            defaultValue={products.find(
                                                (product) => product.productId === formData.onlyProductId,
                                            )}
                                            onChange={(_, value) => {
                                                console.log(value);
                                                setSelectedProduct(value);
                                                if (value) {
                                                    setFormData((prevState: any) => ({
                                                        ...prevState,
                                                        unitStorage: value.unit,
                                                    }));
                                                }
                                            }}
                                            onInputChange={(event, value) => {
                                                console.log(value);
                                                setSearchPro(value);
                                                setPagePro(0);
                                            }}
                                            renderOption={(props, option) => {
                                                return (
                                                    <li {...props} data-name={option.productName}>
                                                        {option.productName}
                                                    </li>
                                                );
                                            }}
                                            renderInput={(params) => (
                                                <TextField
                                                    {...params}
                                                    label="Product"
                                                    InputProps={{
                                                        ...params.InputProps,
                                                        endAdornment: (
                                                            <>
                                                                {loading ? (
                                                                    <CircularProgress color="inherit" size={20} />
                                                                ) : null}
                                                                {params.InputProps.endAdornment}
                                                            </>
                                                        ),
                                                    }}
                                                />
                                            )}
                                            ListboxProps={{
                                                style: {
                                                    maxHeight: 200,
                                                },

                                                onScroll: (event: React.SyntheticEvent) => {
                                                    console.log('scrolling');
                                                    const listboxNode = event.currentTarget;
                                                    if (
                                                        listboxNode.scrollTop + listboxNode.clientHeight ===
                                                        listboxNode.scrollHeight
                                                    ) {
                                                        if (!isLast) {
                                                            setPagePro((prev) => prev + 1);
                                                        }
                                                    }
                                                },
                                            }}
                                        />
                                    </Grid>
                                </>
                            )}
                        </Grid>
                    </form>
                </Container>
            </DialogContent>
            <DialogActions>
                <Button autoFocus onClick={handleSaveConfig}>
                    Save changes
                </Button>
            </DialogActions>
        </BootstrapDialog>
    );
};
