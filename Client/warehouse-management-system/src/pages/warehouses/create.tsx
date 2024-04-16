import {
    Autocomplete,
    Button,
    Dialog,
    DialogActions,
    DialogContent,
    DialogContentText,
    DialogTitle,
    FilterOptionsState,
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

export const WarehouseCreate = () => {
    const [dialogValue, setDialogValue] = React.useState({
        typeName: '',
        description: '',
    });

    const { mutate } = useCreate();

    const [open, toggleOpen] = React.useState(false);

    const handleClose = () => {
        setDialogValue({
            typeName: '',
            description: '',
        });
        toggleOpen(false);
    };

    const handleOpen = (initValue: string) => {
        setDialogValue({
            typeName: initValue,
            description: '',
        });
        toggleOpen(true);
    };
    const {
        saveButtonProps,
        refineCore: { formLoading },
        register,
        control,
        formState: { errors },
        getValues,
    } = useForm<any>({
        refineCoreProps: {
            resource: '',
            dataProviderName: 'warehouses',
        },
    });

    const { autocompleteProps: typeAutocompleteProps } = useAutocomplete({
        resource: 'dropdown/types',
        dataProviderName: 'warehouses',
    });

    const { autocompleteProps: provinceAutoCompleteProps } = useAutocomplete({
        resource: 'province',
        dataProviderName: 'locations',
    });

    const { autocompleteProps: districtAutoCompleteProps } = useAutocomplete({
        resource: `province/district/${getValues('province') ? getValues('province').province_id : '01'}`,
        dataProviderName: 'locations',
    });

    const { autocompleteProps: wardAutoCompleteProps } = useAutocomplete({
        resource: `province/ward/${getValues('district') ? getValues('district').district_id : '018'}`,
        dataProviderName: 'locations',
    });

    function handleSubmit(): void {
        const value = {
            warehouseTypeName: dialogValue.typeName,
            description: dialogValue.description,
        };
        mutate(
            {
                resource: 'types',
                values: value,
                dataProviderName: 'warehouses',
                successNotification: (data, values, resource) => {
                    return {
                        message: `${value.warehouseTypeName} Successfully created.`,
                        description: 'Success with no errors',
                        type: 'success',
                    };
                },
                errorNotification: (data, values, resource) => {
                    return {
                        message: `Something went wrong when created type ${value.warehouseTypeName}`,
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
        <Create isLoading={formLoading} saveButtonProps={saveButtonProps}>
            <Grid2 xs={6} component="form" container spacing={2} autoComplete="off">
                <Grid2 xs={6}>
                    <TextField
                        {...register('warehouseName', {
                            required: 'This field is required',
                            minLength: {
                                value: 3,
                                message: 'Warehouse name must be at least 3 characters',
                            },
                        })}
                        error={!!(errors as any)?.warehouseName}
                        helperText={(errors as any)?.warehouseName?.message}
                        margin="normal"
                        fullWidth
                        InputLabelProps={{ shrink: true }}
                        type="text"
                        label="Warehouse Name"
                        name="warehouseName"
                        required
                        inputProps={{
                            autoComplete: 'new-password',
                            form: {
                                autocomplete: 'off',
                            },
                        }}
                    />
                </Grid2>
                <Grid2 xs={6}>
                    <TextField
                        {...register('warehouseNameAcronym', {
                            required: 'This field is required',
                        })}
                        required
                        error={!!(errors as any)?.warehouseNameAcronym}
                        helperText={(errors as any)?.warehouseNameAcronym?.message}
                        margin="normal"
                        fullWidth
                        InputLabelProps={{ shrink: true }}
                        type="text"
                        label="Acronym Warehouse Name"
                        name="warehouseNameAcronym"
                        inputProps={{
                            autoComplete: 'new-password',
                            form: {
                                autocomplete: 'off',
                            },
                        }}
                    />
                </Grid2>
                <Grid2 xs={12}>
                    <TextField
                        {...register('location', {
                            required: 'This field is required',
                        })}
                        required
                        error={!!(errors as any)?.location}
                        helperText={(errors as any)?.location?.message}
                        margin="normal"
                        fullWidth
                        InputLabelProps={{ shrink: true }}
                        type="text"
                        label="Address"
                        name="location"
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
                        name="province"
                        rules={{ required: 'This field is required' }}
                        // eslint-disable-next-line
                        defaultValue={null as any}
                        render={({ field, fieldState }) => (
                            <Autocomplete
                                {...provinceAutoCompleteProps}
                                {...field}
                                disablePortal
                                filterOptions={(options: any[], state: FilterOptionsState<any>) => {
                                    const filtered = options.filter((option) =>
                                        option.province_name.toLowerCase().includes(state.inputValue.toLowerCase()),
                                    );
                                    return filtered;
                                }}
                                onChange={(_, value) => {
                                    field.onChange(value);
                                }}
                                getOptionLabel={(item) => {
                                    return item.province_name;
                                }}
                                renderOption={(props: any, option: any, state: object, ownerState: object) => {
                                    return (
                                        <DropDownOption
                                            key={option.province_id}
                                            hasDescription={false}
                                            id={option.province_id}
                                            props={props}
                                            option={option}
                                            isCreate={false}
                                        >
                                            {option.province_name}
                                        </DropDownOption>
                                    );
                                }}
                                isOptionEqualToValue={(option, value) => {
                                    if (
                                        value === undefined ||
                                        option?.province_id?.toString() === (value?.province_id ?? value)?.toString()
                                    ) {
                                    }
                                    return (
                                        value === undefined ||
                                        option?.province_id?.toString() === (value?.province_id ?? value)?.toString()
                                    );
                                }}
                                renderInput={(params) => {
                                    return (
                                        <TextField
                                            {...params}
                                            label="Province"
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
                        disabled={!!!getValues('province')}
                        control={control}
                        name="district"
                        rules={{ required: 'This field is required' }}
                        // eslint-disable-next-line
                        defaultValue={null as any}
                        render={({ field, fieldState }) => (
                            <Autocomplete
                                disabled={!!!getValues('province')}
                                {...districtAutoCompleteProps}
                                {...field}
                                disablePortal
                                filterOptions={(options: any[], state: FilterOptionsState<any>) => {
                                    const filtered = options.filter((option) =>
                                        option.district_name.toLowerCase().includes(state.inputValue.toLowerCase()),
                                    );
                                    return filtered;
                                }}
                                onChange={(_, value) => {
                                    field.onChange(value);
                                }}
                                getOptionLabel={(item) => {
                                    return item.district_name;
                                }}
                                renderOption={(props: any, option: any, state: object, ownerState: object) => {
                                    return (
                                        <DropDownOption
                                            key={option.district_id}
                                            hasDescription={false}
                                            id={option.district_id}
                                            props={props}
                                            option={option}
                                            isCreate={false}
                                        >
                                            {option.district_name}
                                        </DropDownOption>
                                    );
                                }}
                                isOptionEqualToValue={(option, value) => {
                                    if (
                                        value === undefined ||
                                        option?.district_id?.toString() === (value?.district_id ?? value)?.toString()
                                    ) {
                                    }
                                    return (
                                        value === undefined ||
                                        option?.district_id?.toString() === (value?.district_id ?? value)?.toString()
                                    );
                                }}
                                renderInput={(params) => {
                                    return (
                                        <TextField
                                            {...params}
                                            label="District"
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
                        disabled={!!!getValues('district')}
                        control={control}
                        name="ward"
                        rules={{ required: 'This field is required' }}
                        // eslint-disable-next-line
                        defaultValue={null as any}
                        render={({ field, fieldState }) => (
                            <Autocomplete
                                disabled={!!!getValues('district')}
                                {...wardAutoCompleteProps}
                                {...field}
                                disablePortal
                                filterOptions={(options: any[], state: FilterOptionsState<any>) => {
                                    const filtered = options.filter((option) =>
                                        option.ward_name.toLowerCase().includes(state.inputValue.toLowerCase()),
                                    );
                                    return filtered;
                                }}
                                onChange={(_, value) => {
                                    field.onChange(value);
                                }}
                                getOptionLabel={(item) => {
                                    return item.ward_name;
                                }}
                                renderOption={(props: any, option: any, state: object, ownerState: object) => {
                                    return (
                                        <DropDownOption
                                            key={option.ward_id}
                                            hasDescription={false}
                                            id={option.ward_id}
                                            props={props}
                                            option={option}
                                            isCreate={false}
                                        >
                                            {option.ward_name}
                                        </DropDownOption>
                                    );
                                }}
                                isOptionEqualToValue={(option, value) => {
                                    if (
                                        value === undefined ||
                                        option?.ward_id?.toString() === (value?.ward_id ?? value)?.toString()
                                    ) {
                                    }
                                    return (
                                        value === undefined ||
                                        option?.ward_id?.toString() === (value?.ward_id ?? value)?.toString()
                                    );
                                }}
                                renderInput={(params) => {
                                    return (
                                        <TextField
                                            {...params}
                                            label="Ward"
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
                        name="warehouseType"
                        rules={{ required: 'This field is required' }}
                        // eslint-disable-next-line
                        defaultValue={null as any}
                        render={({ field, fieldState }) => (
                            <Autocomplete
                                {...typeAutocompleteProps}
                                {...field}
                                freeSolo
                                handleHomeEndKeys
                                filterOptions={(options: any[], state: FilterOptionsState<any>) => {
                                    if (state.inputValue) {
                                        const filtered = options.filter((option) =>
                                            option.value.toLowerCase().includes(state.inputValue.toLowerCase()),
                                        );
                                        if (filtered.length == 0) {
                                            return [
                                                {
                                                    key: 'new',
                                                    value: `${state.inputValue}`,
                                                },
                                            ];
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
                                    const value =
                                        typeAutocompleteProps?.options?.find(
                                            (p) => p?.key?.toString() === item?.key?.toString(),
                                        )?.value ?? '';
                                    return value;
                                }}
                                renderOption={(props: any, option: any, state: object, ownerState: object) => {
                                    return (
                                        <DropDownOption
                                            key={option.key}
                                            hasDescription={option.key !== 'new'}
                                            resources="types/description"
                                            providerName="warehouses"
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
                                            label="Warehouse Type"
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
            </Grid2>
            <Dialog open={open} onClose={handleClose}>
                <form>
                    <DialogTitle>Add a new type</DialogTitle>
                    <DialogContent>
                        <DialogContentText sx={{ mb: 3 }}>
                            Did you miss any warehouse's type in our list? Please, add it!
                        </DialogContentText>
                        <Grid2 xs={12} sx={{ mb: 2 }}>
                            <TextField
                                fullWidth
                                autoFocus
                                margin="dense"
                                id="name"
                                value={dialogValue.typeName}
                                onChange={(event) =>
                                    setDialogValue({
                                        ...dialogValue,
                                        typeName: event.target.value,
                                    })
                                }
                                label="Warehouse Type Name"
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
                                value={dialogValue.description}
                                onChange={(event) =>
                                    setDialogValue({
                                        ...dialogValue,
                                        description: event.target.value,
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
