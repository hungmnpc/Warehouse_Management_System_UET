import { HttpError, useOne, useResource, useUpdate } from '@refinedev/core';
import { Edit, RefreshButton, SaveButton, useAutocomplete } from '@refinedev/mui';
import { IProduct } from '../../utils/interfaces';
import {
    TextField,
    Autocomplete,
    FilterOptionsState,
    FormControlLabel,
    Checkbox,
    InputAdornment,
    IconButton,
} from '@mui/material';
import Grid2 from '@mui/material/Unstable_Grid2';
import { register } from 'module';
import { Controller } from 'react-hook-form';
import { DropDownOption } from '../../components/DropDown';
import { useForm } from '@refinedev/react-hook-form';
import React, { useEffect, useState } from 'react';
import EditIcon from '@mui/icons-material/Edit';
import CheckIcon from '@mui/icons-material/Check';

export const ProductEdit = () => {
    const { resources, id, resource } = useResource();

    const { data, isLoading, isError } = useOne<IProduct, HttpError>({
        resource: 'products',
        id,
    });

    const { mutate } = useUpdate();

    const [productData, setProductData] = useState<any>();
    useEffect(() => {
        if (data?.data) {
            setProductData({
                ...data.data,
                productCategory: {
                    key: data.data.productCategoryDTO.productCategoryId,
                    value: data.data.productCategoryDTO.categoryName,
                },
            });
        }
    }, [data]);

    const { autocompleteProps: categoryAutocompleteProps } = useAutocomplete({
        resource: 'categories/dropdown',
        dataProviderName: 'products',
    });

    const { autocompleteProps: unitAutocompleteProps } = useAutocomplete({
        resource: 'units/dropdown',
        dataProviderName: 'products',
    });

    const [editAble, setEditable] = useState<any>({
        productName: false,
        productCode: false,
    });

    console.log(editAble);

    const handleToggleEditable = (fieldname: string) => {
        setEditable({
            ...editAble,
            [fieldname]: !editAble[fieldname],
        });
    };

    // const isEditable = (fieldname: string) => {
    //     return editAble.includes(fieldname);
    // };

    const {
        saveButtonProps,
        refineCore: { formLoading },
        register,
        control,
        formState: { errors },
        getValues,
    } = useForm<any>({
        refineCoreProps: {
            resource: 'products',
        },
        defaultValues: {
            isPacked: false,
            refrigerated: false,
        },
    });

    const [open, toggleOpen] = React.useState(false);
    const [dialogValue, setDialogValue] = React.useState({
        categoryName: '',
        categoryDescription: '',
    });

    const handleClose = () => {
        setDialogValue({
            categoryName: '',
            categoryDescription: '',
        });
        toggleOpen(false);
    };

    const handleOpen = (initValue: string) => {
        setDialogValue({
            categoryName: initValue,
            categoryDescription: '',
        });
    };

    console.log(editAble);

    const hanldeSave = () => {
        console.log(productData);
        if (id) {
            mutate({
                values: {
                    ...productData,
                },
                id: id,
                resource: 'products',
            });
        }
    };

    console.log('unit', unitAutocompleteProps);

    return (
        <Edit
            footerButtons={
                <SaveButton onClick={hanldeSave} style={{ textTransform: 'none' }}>
                    Save
                </SaveButton>
            }
            isLoading={isLoading}
            headerButtons={<RefreshButton recordItemId={id} resource="products" />}
        >
            {productData && (
                <Grid2 xs={6} component="form" container spacing={2} autoComplete="off">
                    <Grid2 xs={8}>
                        <TextField
                            {...register('productName', {
                                required: 'This field is required',
                            })}
                            error={!!(errors as any)?.productName}
                            helperText={(errors as any)?.productName?.message}
                            margin="normal"
                            fullWidth
                            InputLabelProps={{ shrink: true }}
                            type="text"
                            label="Product Name"
                            name="productName"
                            value={productData.productName}
                            onChange={(event) => {
                                setProductData({
                                    ...productData,
                                    productName: event.target.value,
                                });
                            }}
                            required
                            disabled={!editAble['productName']}
                            inputProps={{
                                autoComplete: 'new-password',
                                form: {
                                    autocomplete: 'off',
                                },
                            }}
                            InputProps={{
                                endAdornment: (
                                    <InputAdornment position="end">
                                        <IconButton
                                            aria-label="toggle edit"
                                            onClick={() => handleToggleEditable('productName')}
                                        >
                                            {editAble['productName'] ? <CheckIcon /> : <EditIcon />}
                                        </IconButton>
                                    </InputAdornment>
                                ),
                            }}
                        />
                    </Grid2>
                    <Grid2 xs={4}>
                        <TextField
                            {...register('productCode', {
                                required: 'This field is required',
                            })}
                            required
                            error={!!(errors as any)?.productCode}
                            helperText={(errors as any)?.productCode?.message}
                            margin="normal"
                            fullWidth
                            InputLabelProps={{ shrink: true }}
                            disabled={!editAble['productCode']}
                            type="text"
                            label="Product Code"
                            name="productCode"
                            value={productData.productCode}
                            onChange={(event) => {
                                setProductData({
                                    ...productData,
                                    productCode: event.target.value,
                                });
                            }}
                            inputProps={{
                                autoComplete: 'new-password',
                                form: {
                                    autocomplete: 'off',
                                },
                            }}
                            InputProps={{
                                endAdornment: (
                                    <InputAdornment position="end">
                                        <IconButton
                                            aria-label="toggle edit"
                                            onClick={() => handleToggleEditable('productCode')}
                                        >
                                            {editAble['productCode'] ? <CheckIcon /> : <EditIcon />}
                                        </IconButton>
                                    </InputAdornment>
                                ),
                            }}
                        />
                    </Grid2>

                    <Grid2 xs={4}>
                        <Controller
                            control={control}
                            name="productCategory"
                            rules={{ required: 'This field is required' }}
                            // eslint-disable-next-line
                            render={({ field, fieldState }) => (
                                <Autocomplete
                                    {...categoryAutocompleteProps}
                                    {...field}
                                    freeSolo
                                    // disabled={!(editAble['productCategoty'] || false)}
                                    handleHomeEndKeys
                                    defaultValue={categoryAutocompleteProps.options.find(
                                        (option) => option.key === productData?.productCategory.key,
                                    )}
                                    filterOptions={(options: any[], state: FilterOptionsState<any>) => {
                                        if (state.inputValue) {
                                            let isExist = false;
                                            const filtered = options.filter((option) =>
                                                option.value.toLowerCase().includes(state.inputValue.toLowerCase()),
                                            );
                                            if (
                                                filtered.every(
                                                    (option) =>
                                                        option.value.toLowerCase() !== state.inputValue.toLowerCase(),
                                                )
                                            ) {
                                                filtered.push({
                                                    key: 'new',
                                                    value: `${state.inputValue}`,
                                                });
                                            }
                                            return filtered;
                                        } else {
                                            return options;
                                        }
                                    }}
                                    onChange={(_, value) => {
                                        field.onChange(value);
                                        setProductData({
                                            ...productData,
                                            productCategory: value,
                                        });
                                    }}
                                    getOptionLabel={(item) => {
                                        const value =
                                            categoryAutocompleteProps?.options?.find(
                                                (p) => p?.key?.toString() === item?.key?.toString(),
                                            )?.value ?? '';
                                        return value;
                                    }}
                                    renderOption={(props: any, option: any, state: object, ownerState: object) => {
                                        return (
                                            <DropDownOption
                                                key={option.key}
                                                hasDescription={true}
                                                resources="categories/description"
                                                providerName="products"
                                                id={option.key}
                                                props={props}
                                                option={option}
                                                isCreate={option.key === 'new'}
                                                handleOpenModal={option.key === 'new' ? handleOpen : props.onClick}
                                            >
                                                {option.key === 'new'
                                                    ? `Add new type: '${option.value}'`
                                                    : option.value}
                                            </DropDownOption>
                                        );
                                    }}
                                    isOptionEqualToValue={(option, value) =>
                                        value === undefined ||
                                        option?.key?.toString() === (value?.key ?? value)?.toString()
                                    }
                                    renderInput={(params) => {
                                        return (
                                            <TextField
                                                {...params}
                                                label="Category"
                                                margin="normal"
                                                variant="outlined"
                                                error={!!fieldState.error}
                                                helperText={fieldState.error?.message}
                                                required
                                            />
                                        );
                                    }}
                                />
                            )}
                        />
                    </Grid2>
                    <Grid2 xs={4}>
                        <Controller
                            control={control}
                            name="unit"
                            rules={{ required: 'This field is required' }}
                            // eslint-disable-next-line
                            defaultValue={null as any}
                            render={({ field, fieldState }) => (
                                <Autocomplete
                                    {...unitAutocompleteProps}
                                    {...field}
                                    freeSolo
                                    // disabled
                                    handleHomeEndKeys
                                    value={productData.unit}
                                    filterOptions={(options: any[], state: FilterOptionsState<any>) => {
                                        if (state.inputValue) {
                                            const filtered = options.filter((option) =>
                                                option.toLowerCase().includes(state.inputValue.toLowerCase()),
                                            );
                                            if (filtered.length == 0) {
                                                return [`${state.inputValue}`];
                                            } else {
                                                return filtered;
                                            }
                                        } else {
                                            return options;
                                        }
                                    }}
                                    onChange={(_, value) => {
                                        setProductData({
                                            ...productData,
                                            unit: value,
                                        });
                                        field.onChange(value);
                                    }}
                                    getOptionLabel={(item) => {
                                        const value = item;
                                        console.log(value);
                                        return value;
                                    }}
                                    renderOption={(props: any, option: any, state: object, ownerState: object) => {
                                        return (
                                            <DropDownOption
                                                key={option}
                                                hasDescription={false}
                                                // resources="types/description"
                                                // providerName="warehouses"
                                                id={option}
                                                props={props}
                                                option={option}
                                                isCreate={false}
                                                // handleOpenModal={option.key === 'new' ? handleOpen : props.onClick}
                                            >
                                                {option}
                                            </DropDownOption>
                                        );
                                    }}
                                    isOptionEqualToValue={(option, value) => {
                                        console.log(
                                            'is equal',
                                            value === undefined ||
                                                option?.key?.toString() === (value?.key ?? value)?.toString() ||
                                                option === value,
                                        );
                                        return (
                                            value === undefined ||
                                            option?.key?.toString() === (value?.key ?? value)?.toString() ||
                                            option === value
                                        );
                                    }}
                                    renderInput={(params) => {
                                        return (
                                            <TextField
                                                {...params}
                                                label="Unit"
                                                margin="normal"
                                                variant="outlined"
                                                error={!!fieldState.error}
                                                helperText={fieldState.error?.message}
                                                required
                                            />
                                        );
                                    }}
                                />
                            )}
                        />
                    </Grid2>
                    <Grid2 xs={2}>
                        <FormControlLabel
                            checked={productData.isPacked}
                            {...register('isPacked')}
                            name="isPacked"
                            labelPlacement="top"
                            control={<Checkbox name="isPacked" />}
                            label="Is Packed?"
                            onClick={(event) => {
                                setProductData({
                                    ...productData,
                                    isPacked: !productData.isPacked,
                                });
                                console.log(event);
                            }}
                            // onChange={(event) => {
                            //     setIsPacked(!isPacked);
                            // }}
                        />
                    </Grid2>
                    <Grid2 xs={2}>
                        <FormControlLabel
                            checked={productData.refrigerated}
                            {...register('refrigerated')}
                            name="refrigerated"
                            labelPlacement="top"
                            control={<Checkbox name="refrigerated" />}
                            label="Refrigerated?"
                            onClick={(event) => {
                                setProductData({
                                    ...productData,
                                    refrigerated: !productData.refrigerated,
                                });
                                console.log(event);
                            }}
                        />
                    </Grid2>
                    {productData.isPacked == true && (
                        <>
                            <Grid2 xs={4}>
                                <TextField
                                    {...register('packedHeight', {
                                        required: 'This field is required',
                                        pattern: {
                                            value: /^[1-9]\d*(\d+)?$/i,
                                            message: 'Please enter an number',
                                        },
                                    })}
                                    InputProps={{
                                        endAdornment: <InputAdornment position="end">mm</InputAdornment>,
                                    }}
                                    error={!!(errors as any)?.packedHeight}
                                    helperText={(errors as any)?.packedHeight?.message}
                                    margin="normal"
                                    fullWidth
                                    InputLabelProps={{ shrink: true }}
                                    type="text"
                                    label="Packed Height"
                                    name="packedHeight"
                                    required
                                    value={productData.packedHeight}
                                    onChange={(event) => {
                                        setProductData({
                                            ...productData,
                                            packedHeight: event.target.value,
                                        });
                                    }}
                                    inputProps={{
                                        autoComplete: 'new-password',
                                        form: {
                                            autocomplete: 'off',
                                        },
                                        style: { textAlign: 'right' },
                                    }}
                                />
                            </Grid2>
                            <Grid2 xs={4}>
                                <TextField
                                    {...register('packedWidth', {
                                        required: 'This field is required',
                                        pattern: {
                                            value: /^[1-9]\d*(\d+)?$/i,
                                            message: 'Please enter an number',
                                        },
                                    })}
                                    value={productData.packedWidth}
                                    onChange={(event) => {
                                        setProductData({
                                            ...productData,
                                            packedWidth: event.target.value,
                                        });
                                    }}
                                    InputProps={{
                                        endAdornment: <InputAdornment position="end">mm</InputAdornment>,
                                    }}
                                    error={!!(errors as any)?.packedWidth}
                                    helperText={(errors as any)?.packedWidth?.message}
                                    margin="normal"
                                    fullWidth
                                    InputLabelProps={{ shrink: true }}
                                    type="text"
                                    label="Packed Width"
                                    name="packedWidth"
                                    required
                                    inputProps={{
                                        autoComplete: 'new-password',
                                        form: {
                                            autocomplete: 'off',
                                        },
                                        style: { textAlign: 'right' },
                                    }}
                                />
                            </Grid2>
                            <Grid2 xs={4}>
                                <TextField
                                    {...register('packedDepth', {
                                        required: 'This field is required',
                                        pattern: {
                                            value: /^[1-9]\d*(\d+)?$/i,
                                            message: 'Please enter an number',
                                        },
                                    })}
                                    value={productData.packedDepth}
                                    onChange={(event) => {
                                        setProductData({
                                            ...productData,
                                            packedDepth: event.target.value,
                                        });
                                    }}
                                    InputProps={{
                                        endAdornment: <InputAdornment position="end">mm</InputAdornment>,
                                    }}
                                    error={!!(errors as any)?.packedDepth}
                                    helperText={(errors as any)?.packedDepth?.message}
                                    margin="normal"
                                    fullWidth
                                    InputLabelProps={{ shrink: true }}
                                    type="text"
                                    label="Packed Depth"
                                    name="packedDepth"
                                    required
                                    inputProps={{
                                        autoComplete: 'new-password',
                                        form: {
                                            autocomplete: 'off',
                                        },
                                        style: { textAlign: 'right' },
                                    }}
                                />
                            </Grid2>
                        </>
                    )}
                    <Grid2 xs={4}>
                        <TextField
                            {...register('sku')}
                            error={!!(errors as any)?.sku}
                            helperText={(errors as any)?.sku?.message}
                            margin="normal"
                            fullWidth
                            InputLabelProps={{ shrink: true }}
                            type="text"
                            label="SKU"
                            name="sku"
                            value={productData.sku}
                            onChange={(event) => {
                                setProductData({
                                    ...productData,
                                    sku: event.target.value,
                                });
                            }}
                            inputProps={{
                                autoComplete: 'new-password',
                                form: {
                                    autocomplete: 'off',
                                },
                            }}
                        />
                    </Grid2>

                    <Grid2 xs={4}>
                        <TextField
                            {...register('packedWeight', {
                                pattern: {
                                    value: /^[1-9]\d*(\d+)?$/i,
                                    message: 'Please enter an number',
                                },
                            })}
                            InputProps={{
                                endAdornment: <InputAdornment position="end">g</InputAdornment>,
                            }}
                            value={productData.packedWeight}
                            onChange={(event) => {
                                setProductData({
                                    ...productData,
                                    packedWeight: event.target.value,
                                });
                            }}
                            error={!!(errors as any)?.packedWeight}
                            helperText={(errors as any)?.packedWeight?.message}
                            margin="normal"
                            fullWidth
                            InputLabelProps={{ shrink: true }}
                            type="text"
                            label="Packed Weight"
                            name="packedWeight"
                            inputProps={{
                                autoComplete: 'new-password',
                                form: {
                                    autocomplete: 'off',
                                },
                                style: { textAlign: 'right' },
                            }}
                        />
                    </Grid2>
                    <Grid2 xs={4}>
                        <TextField
                            {...register('reorderQuantity', {
                                pattern: {
                                    value: /^[1-9]\d*(\d+)?$/i,
                                    message: 'Please enter an number',
                                },
                            })}
                            value={productData.reorderQuantity}
                            onChange={(event) => {
                                setProductData({
                                    ...productData,
                                    reorderQuantity: event.target.value,
                                });
                            }}
                            InputProps={{
                                endAdornment: (
                                    <InputAdornment position="end">
                                        {getValues('unit') ? getValues('unit') : ''}
                                    </InputAdornment>
                                ),
                            }}
                            error={!!(errors as any)?.reorderQuantity}
                            helperText={(errors as any)?.reorderQuantity?.message}
                            margin="normal"
                            fullWidth
                            InputLabelProps={{ shrink: true }}
                            type="text"
                            label="Reorder Quantity"
                            name="reorderQuantity"
                            inputProps={{
                                autoComplete: 'new-password',
                                form: {
                                    autocomplete: 'off',
                                },
                                style: { textAlign: 'right' },
                            }}
                        />
                    </Grid2>

                    <Grid2 xs={12}>
                        <TextField
                            {...register('productDescription')}
                            error={!!(errors as any)?.productDescription}
                            helperText={(errors as any)?.productDescription?.message}
                            margin="normal"
                            fullWidth
                            value={productData.productDescription}
                            onChange={(event) => {
                                setProductData({
                                    ...productData,
                                    productDescription: event.target.value,
                                });
                            }}
                            InputLabelProps={{ shrink: true }}
                            type="text"
                            label="Product Description"
                            name="productDescription"
                            minRows={10}
                            multiline={true}
                            inputProps={{
                                autoComplete: 'new-password',
                                form: {
                                    autocomplete: 'off',
                                },
                            }}
                        />
                    </Grid2>
                </Grid2>
            )}
        </Edit>
    );
};
