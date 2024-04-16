import { HttpError, useOne, useResource } from '@refinedev/core';
import { Breadcrumb, Edit, SaveButton, Show } from '@refinedev/mui';
import { DeleteButton, DeleteButtonIcon } from '../../components/buttons/button';
import { IUser } from '../../utils/interfaces';

export const UserEdit = () => {
    const { resources, resource, action, id } = useResource();
    const { data, isLoading, isError } = useOne<IUser, HttpError>({
        resource: 'users',
        id,
        dataProviderName: 'users',
    });

    console.log('dadasda', data?.data);
    if (isLoading) {
        return <div>Loading......</div>;
    }
    return (
        <Edit
            breadcrumb={<Breadcrumb />}
            footerButtons={
                <>
                    <DeleteButton
                        onDelete={() => {
                            console.log(id);
                        }}
                    />
                    <SaveButton
                        style={{
                            fontWeight: 700,
                            textTransform: 'none',
                        }}
                    />
                </>
            }
        >
            Edit {id}
        </Edit>
    );
};
