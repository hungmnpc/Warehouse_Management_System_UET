import {
    Autocomplete,
    Button,
    Checkbox,
    Dialog,
    DialogActions,
    DialogContent,
    DialogContentText,
    DialogTitle,
    FilterOptionsState,
    FormControlLabel,
    InputAdornment,
    TextField,
} from '@mui/material';
import Grid2 from '@mui/material/Unstable_Grid2/Grid2';
import { Create, useAutocomplete } from '@refinedev/mui';
import { useForm } from '@refinedev/react-hook-form';
import { Controller } from 'react-hook-form';
import { DropDownOption } from '../../components/DropDown';
import { randomUUID } from 'crypto';
import React, { FormEvent } from 'react';
import { useCreate } from '@refinedev/core';

export const ProductCreate = () => {
    const [isPacked, setIsPacked] = React.useState(false);

    const [dialogValue, setDialogValue] = React.useState({
        categoryName: '',
        categoryDescription: '',
    });
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
        toggleOpen(true);
    };

    const { autocompleteProps: categoryAutocompleteProps } = useAutocomplete({
        resource: 'categories/dropdown',
        dataProviderName: 'products',
    });

    const { autocompleteProps: unitAutocompleteProps } = useAutocomplete({
        resource: 'units/dropdown',
        dataProviderName: 'products',
    });

    console.log(getValues('isPacked'));

    const { mutate } = useCreate();

    function handleSubmit(): void {
        const value = {
            categoryName: dialogValue.categoryName,
            categoryDescription: dialogValue.categoryDescription,
        };
        mutate(
            {
                resource: 'categories',
                values: value,
                dataProviderName: 'products',
                successNotification: (data, values, resource) => {
                    return {
                        message: `${value.categoryName} Successfully created.`,
                        description: 'Success with no errors',
                        type: 'success',
                    };
                },
                errorNotification: (data, values, resource) => {
                    return {
                        message: `Something went wrong when created category ${value.categoryName}`,
                        description: 'Error',
                        type: 'error',
                    };
                },
            },
            {
                onSuccess(data, variables, context) {
                    handleClose();
                },
            },
        );
    }

    return (
        <Create isLoading={true} saveButtonProps={saveButtonProps}>
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
                        required
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
                        {...register('productCode', {
                            required: 'This field is required',
                        })}
                        required
                        error={!!(errors as any)?.productCode}
                        helperText={(errors as any)?.productCode?.message}
                        margin="normal"
                        fullWidth
                        InputLabelProps={{ shrink: true }}
                        type="text"
                        label="Product Code"
                        name="productCode"
                        inputProps={{
                            autoComplete: 'new-password',
                            form: {
                                autocomplete: 'off',
                            },
                        }}
                    />
                </Grid2>

                <Grid2 xs={4}>
                    <Controller
                        control={control}
                        name="productCategory"
                        rules={{ required: 'This field is required' }}
                        // eslint-disable-next-line
                        defaultValue={null as any}
                        render={({ field, fieldState }) => (
                            <Autocomplete
                                {...categoryAutocompleteProps}
                                {...field}
                                freeSolo
                                handleHomeEndKeys
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
                                            {option.key === 'new' ? `Add new type: '${option.value}'` : option.value}
                                        </DropDownOption>
                                    );
                                }}
                                isOptionEqualToValue={(option, value) =>
                                    value === undefined || option?.key?.toString() === (value?.key ?? value)?.toString()
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
                                handleHomeEndKeys
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
                                isOptionEqualToValue={(option, value) =>
                                    value === undefined || option?.key?.toString() === (value?.key ?? value)?.toString()
                                }
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
                        checked={isPacked}
                        {...register('isPacked')}
                        name="isPacked"
                        labelPlacement="top"
                        control={<Checkbox name="isPacked" />}
                        label="Is Packed?"
                        onChange={(event) => {
                            setIsPacked(!isPacked);
                        }}
                    />
                </Grid2>
                <Grid2 xs={2}>
                    <FormControlLabel
                        {...register('refrigerated')}
                        name="refrigerated"
                        labelPlacement="top"
                        control={<Checkbox name="refrigerated" />}
                        label="Eefrigerated?"
                    />
                </Grid2>
                {isPacked == true && (
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
            <Dialog open={open} onClose={handleClose}>
                <form>
                    <DialogTitle>Add a new product category</DialogTitle>
                    <DialogContent>
                        <DialogContentText sx={{ mb: 3 }}>
                            Did you miss any product category in our list? Please, add it!
                        </DialogContentText>
                        <Grid2 xs={12} sx={{ mb: 2 }}>
                            <TextField
                                fullWidth
                                autoFocus
                                margin="dense"
                                id="name"
                                value={dialogValue.categoryName}
                                onChange={(event) =>
                                    setDialogValue({
                                        ...dialogValue,
                                        categoryName: event.target.value,
                                    })
                                }
                                label="Category Name"
                                type="text"
                                InputLabelProps={{ shrink: true }}
                            />
                        </Grid2>
                        <Grid2 xs={12}>
                            <TextField
                                fullWidth
                                multiline
                                margin="dense"
                                id="name"
                                value={dialogValue.categoryDescription}
                                onChange={(event) =>
                                    setDialogValue({
                                        ...dialogValue,
                                        categoryDescription: event.target.value,
                                    })
                                }
                                label="Description"
                                type="text"
                                InputLabelProps={{ shrink: true }}
                            />
                        </Grid2>
                    </DialogContent>
                    <DialogActions>
                        <Button onClick={handleClose}>Cancel</Button>
                        <Button onClick={handleSubmit}>Add</Button>
                    </DialogActions>
                </form>
            </Dialog>
        </Create>
    );
};
