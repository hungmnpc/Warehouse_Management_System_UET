import { TextField } from '@mui/material';
import Grid2 from '@mui/material/Unstable_Grid2/Grid2';
import { useCreate, useGo, useResource } from '@refinedev/core';
import { Create } from '@refinedev/mui';
import { useForm } from '@refinedev/react-hook-form';
import { redirect } from 'react-router-dom';

export const SupplierCreate = () => {
    const { mutate } = useCreate();
    const go = useGo();

    const { resources, resource, action, id } = useResource();

    console.log(resource);

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
            resource: 'suppliers',
            redirect: false,
            onMutationSuccess: (response: any) => {
                console.log(response);
                if (response.data.success) {
                    go({ to: resource?.edit?.toString().replace(':id', response.data.data) });
                }
            },
        },
    });

    return (
        <Create isLoading={formLoading} saveButtonProps={saveButtonProps}>
            <Grid2 xs={6} component="form" onSubmit={handleSubmit(onFinish)} container spacing={2} autoComplete="off">
                <Grid2 xs={6}>
                    <TextField
                        {...register('supplierName', {
                            required: 'This field is required',
                        })}
                        error={!!(errors as any)?.supplierName}
                        helperText={(errors as any)?.supplierName?.message}
                        margin="normal"
                        fullWidth
                        InputLabelProps={{ shrink: true }}
                        type="text"
                        label="Supplier Name"
                        name="supplierName"
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
                        {...register('supplierNumber')}
                        error={!!(errors as any)?.supplierNumber}
                        helperText={(errors as any)?.supplierNumber?.message}
                        margin="normal"
                        fullWidth
                        InputLabelProps={{ shrink: true }}
                        type="text"
                        label="Supplier Number"
                        name="supplierNumber"
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
                        {...register('supplierAddress1')}
                        error={!!(errors as any)?.supplierAddress1}
                        helperText={(errors as any)?.supplierAddress1?.message}
                        margin="normal"
                        fullWidth
                        InputLabelProps={{ shrink: true }}
                        type="text"
                        label="Supplier Address 1"
                        name="supplierAddress1"
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
                        {...register('supplierAddress2')}
                        error={!!(errors as any)?.supplierAddress2}
                        helperText={(errors as any)?.supplierAddress2?.message}
                        margin="normal"
                        fullWidth
                        InputLabelProps={{ shrink: true }}
                        type="text"
                        label="Supplier Address 2"
                        name="supplierAddress2"
                        inputProps={{
                            autoComplete: 'new-password',
                            form: {
                                autocomplete: 'off',
                            },
                        }}
                    />
                </Grid2>
            </Grid2>
        </Create>
    );
};
