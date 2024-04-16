import { useResource } from '@refinedev/core';
import { Create, useAutocomplete } from '@refinedev/mui';
import { useForm } from '@refinedev/react-hook-form';
import { ICreateUser, IDropDown, IUser } from '../../utils/interfaces';
import { Autocomplete, Box, IconButton, InputAdornment, TextField } from '@mui/material';
import { Controller } from 'react-hook-form';
import Grid2 from '@mui/material/Unstable_Grid2/Grid2';
import { emailRegex } from '../../constant';
import VisibilityIcon from '@mui/icons-material/Visibility';
import VisibilityOffIcon from '@mui/icons-material/VisibilityOff';
import React from 'react';
export const UserCreate = () => {
    const [showPassword, setShowPassword] = React.useState(false);
    const handleClickShowPassword = () => setShowPassword(!showPassword);
    const handleMouseDownPassword = () => setShowPassword(!showPassword);
    const { resources, resource, action, id } = useResource();
    const {
        saveButtonProps,
        refineCore: { formLoading },
        register,
        control,
        formState: { errors },
        getValues,
    } = useForm<ICreateUser>({
        refineCoreProps: {
            resource: 'users',
            dataProviderName: 'users',
        },
    });

    const { autocompleteProps: roleAutocompleteProps } = useAutocomplete({
        resource: 'dropdown/roles',
        dataProviderName: 'users',
    });

    const { autocompleteProps: warehouseAutocompleteProps } = useAutocomplete({
        resource: 'dropdown',
        dataProviderName: 'warehouses',
    });

    console.log(warehouseAutocompleteProps);

    return (
        <Create isLoading={formLoading} saveButtonProps={saveButtonProps}>
            <Grid2 xs={6} component="form" container spacing={2} autoComplete="off">
                <Grid2 xs={6}>
                    <TextField
                        {...register('firstName', {
                            required: 'This field is required',
                        })}
                        required
                        error={!!(errors as any)?.firstName}
                        helperText={(errors as any)?.firstName?.message}
                        margin="normal"
                        fullWidth
                        InputLabelProps={{ shrink: true }}
                        type="text"
                        label="First Name"
                        name="firstName"
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
                        {...register('lastName', {
                            required: 'This field is required',
                        })}
                        required
                        error={!!(errors as any)?.lastName}
                        helperText={(errors as any)?.lastName?.message}
                        margin="normal"
                        fullWidth
                        InputLabelProps={{ shrink: true }}
                        type="text"
                        label="Last Name"
                        name="lastName"
                        inputProps={{
                            autoComplete: 'new-password',
                            form: {
                                autocomplete: 'off',
                            },
                        }}
                    />
                </Grid2>
                <Grid2 xs={8}>
                    <TextField
                        {...register('userName', {
                            required: 'This field is required',
                        })}
                        required
                        error={!!(errors as any)?.userName}
                        helperText={(errors as any)?.userName?.message}
                        margin="normal"
                        fullWidth
                        InputLabelProps={{ shrink: true }}
                        type="text"
                        label="User Name"
                        name="userName"
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
                        name="role"
                        rules={{ required: 'This field is required' }}
                        // eslint-disable-next-line
                        defaultValue={null as any}
                        render={({ field }) => (
                            <Autocomplete
                                {...roleAutocompleteProps}
                                {...field}
                                onChange={(_, value) => {
                                    field.onChange(value);
                                }}
                                getOptionLabel={(item) => {
                                    return (
                                        roleAutocompleteProps?.options?.find(
                                            (p) => p?.key?.toString() === item?.key?.toString(),
                                        )?.value ?? ''
                                    );
                                }}
                                isOptionEqualToValue={(option, value) =>
                                    value === undefined || option?.key?.toString() === (value?.key ?? value)?.toString()
                                }
                                renderInput={(params) => {
                                    return (
                                        <TextField
                                            {...params}
                                            label="Role"
                                            margin="normal"
                                            variant="outlined"
                                            error={!!(errors as any)?.role?.key}
                                            helperText={(errors as any)?.role?.key?.message}
                                            required
                                        />
                                    );
                                }}
                            />
                        )}
                    />
                </Grid2>
                {getValues('role') &&
                    (getValues('role').value === 'Warehouse Manager' || getValues('role').value === 'Employee') && (
                        <Grid2 xs={12}>
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
                    )}
                <Grid2 xs={12}>
                    <TextField
                        {...register('email', {
                            pattern: {
                                value: emailRegex,
                                message: 'Invalid email',
                            },
                        })}
                        error={!!(errors as any)?.email}
                        helperText={(errors as any)?.email?.message}
                        margin="normal"
                        fullWidth
                        InputLabelProps={{ shrink: true }}
                        type="text"
                        label="Email"
                        name="email"
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
                        {...register('password', {
                            minLength: {
                                value: 8,
                                message: 'Your password must be at least 8 characters',
                            },
                            required: 'This field is required',
                        })}
                        required
                        error={!!(errors as any)?.password}
                        helperText={(errors as any)?.password?.message}
                        margin="normal"
                        fullWidth
                        InputLabelProps={{ shrink: true }}
                        type={showPassword ? 'text' : 'password'}
                        label="Password"
                        name="password"
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
                                        aria-label="toggle password visibility"
                                        onClick={handleClickShowPassword}
                                        onMouseDown={handleMouseDownPassword}
                                    >
                                        {showPassword ? <VisibilityOffIcon /> : <VisibilityIcon />}
                                    </IconButton>
                                </InputAdornment>
                            ),
                        }}
                    />
                </Grid2>
            </Grid2>
        </Create>
    );
};

const dropDowns: IDropDown[] = [
    {
        key: 'role1',
        value: 'Role 1',
    },
    {
        key: 'role2',
        value: 'Role 2',
    },
];
