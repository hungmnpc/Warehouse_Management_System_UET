import { Autocomplete, Box, FilterOptionsState, TextField } from '@mui/material';
import Grid2 from '@mui/material/Unstable_Grid2/Grid2';
import { Create, DateField, useAutocomplete } from '@refinedev/mui';
import { useForm } from '@refinedev/react-hook-form';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider/LocalizationProvider';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { Controller } from 'react-hook-form';
import { DropDownOption } from '../../components/DropDown';
import { useEffect } from 'react';
import { useGo, useResource } from '@refinedev/core';

export const PurchaseOrderCreate = () => {
    const go = useGo();
    const { resources, resource, action, id } = useResource();
    const {
        saveButtonProps,
        handleSubmit,
        refineCore: { formLoading, onFinish },
        register,
        control,
        formState: { errors },
        getValues,
    } = useForm<any>({
        refineCoreProps: {
            resource: 'purchase_orders',
            dataProviderName: 'purchaseOrder',
            redirect: false,
            onMutationSuccess: (response: any) => {
                console.log(response);
                if (response.data.success) {
                    go({ to: resource?.show?.toString().replace(':id', response.data.data) });
                }
            },
        },
    });

    const { autocompleteProps: supplierDropDown } = useAutocomplete({
        resource: 'suppliers/dropdown',
        pagination: {
            current: 0,
            pageSize: 9999999,
        },
    });

    const { autocompleteProps: warehouseAutocompleteProps } = useAutocomplete({
        resource: 'dropdown',
        dataProviderName: 'warehouses',
    });

    useEffect(() => {
        console.log('supplier Drop down', supplierDropDown);
    }, [supplierDropDown]);

    return (
        <Create saveButtonProps={saveButtonProps}>
            <Grid2 xs={12} component="form" container spacing={2} autoComplete="off">
                <Grid2 container spacing={2} xs={6}>
                    <Grid2 xs={6}>
                        <TextField
                            {...register('poCode')}
                            error={!!(errors as any)?.poCode}
                            helperText={(errors as any)?.poCode?.message}
                            margin="normal"
                            fullWidth
                            InputLabelProps={{ shrink: true }}
                            type="text"
                            label="Purchase Order Code"
                            name="poCode"
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
                            {...register('referenceNumber')}
                            error={!!(errors as any)?.referenceNumber}
                            helperText={(errors as any)?.referenceNumber?.message}
                            margin="normal"
                            fullWidth
                            InputLabelProps={{ shrink: true }}
                            type="text"
                            label="Reference Number"
                            name="referenceNumber"
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
                            {...register('inboundDate')}
                            error={!!(errors as any)?.inboundDate}
                            helperText={(errors as any)?.inboundDate?.message}
                            margin="normal"
                            fullWidth
                            InputLabelProps={{ shrink: true }}
                            type="date"
                            label="Inbound Date"
                            name="inboundDate"
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
                            {...register('arrivalDate')}
                            error={!!(errors as any)?.arrivalDate}
                            helperText={(errors as any)?.arrivalDate?.message}
                            margin="normal"
                            fullWidth
                            InputLabelProps={{ shrink: true }}
                            type="date"
                            label="Arrival Date"
                            name="arrivalDate"
                            inputProps={{
                                autoComplete: 'new-password',
                                form: {
                                    autocomplete: 'off',
                                },
                            }}
                        />
                    </Grid2>
                    <Grid2 xs={6}>
                        <Controller
                            control={control}
                            name="supplier"
                            rules={{ required: 'This field is required' }}
                            // eslint-disable-next-line
                            defaultValue={null as any}
                            render={({ field, fieldState }) => (
                                <Autocomplete
                                    {...supplierDropDown}
                                    {...field}
                                    freeSolo
                                    handleHomeEndKeys
                                    filterOptions={(options: any[], state: FilterOptionsState<any>) => {
                                        if (state.inputValue) {
                                            let isExist = false;
                                            const filtered = options.filter((option) =>
                                                option.value.toLowerCase().includes(state.inputValue.toLowerCase()),
                                            );
                                            console.log('filterd', filtered);
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
                                            supplierDropDown?.options?.find(
                                                (p) => p?.key?.toString() === item?.key?.toString(),
                                            )?.value ?? '';
                                        return value;
                                    }}
                                    renderOption={(props: any, option: any, state: object, ownerState: object) => {
                                        return (
                                            <DropDownOption
                                                key={option.key}
                                                hasDescription={false}
                                                id={option.key}
                                                props={props}
                                                option={option}
                                            >
                                                {option.value}
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
                                                label="Supplier"
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
                    <Grid2 xs={6}>
                        <Controller
                            control={control}
                            name="warehouse"
                            rules={{ required: 'This field is required' }}
                            // eslint-disable-next-line
                            defaultValue={null as any}
                            render={({ field }) => (
                                <Autocomplete
                                    {...warehouseAutocompleteProps}
                                    {...field}
                                    onChange={(_, value) => {
                                        field.onChange(value);
                                    }}
                                    getOptionLabel={(item) => {
                                        return (
                                            warehouseAutocompleteProps?.options?.find(
                                                (p) => p?.key?.toString() === item?.key?.toString(),
                                            )?.value ?? ''
                                        );
                                    }}
                                    isOptionEqualToValue={(option, value) =>
                                        value === undefined ||
                                        option?.key?.toString() === (value?.key ?? value)?.toString()
                                    }
                                    renderOption={(props: any, option: any, state: object, ownerState: object) => {
                                        return (
                                            <DropDownOption
                                                key={option.key}
                                                hasDescription={false}
                                                id={option.key}
                                                props={props}
                                                option={option}
                                            >
                                                {option.value}
                                            </DropDownOption>
                                        );
                                    }}
                                    renderInput={(params) => {
                                        return (
                                            <TextField
                                                {...params}
                                                label="Warehouse"
                                                margin="normal"
                                                variant="outlined"
                                                error={!!(errors as any)?.warehouse?.key}
                                                helperText={(errors as any)?.warehouse?.key?.message}
                                                required
                                            />
                                        );
                                    }}
                                />
                            )}
                        />
                    </Grid2>
                    <Grid2 xs={12}>
                        <TextField
                            {...register('comment')}
                            error={!!(errors as any)?.comment}
                            helperText={(errors as any)?.comment?.message}
                            margin="normal"
                            fullWidth
                            InputLabelProps={{ shrink: true }}
                            type="text"
                            label="Comment"
                            name="comment"
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
                <Grid2 container xs={6}></Grid2>
            </Grid2>
        </Create>
    );
};
