import { useResource } from "@refinedev/core";
import { Breadcrumb, Edit, SaveButton, Show } from "@refinedev/mui";
import { DeleteButton, DeleteButtonIcon } from "../../components/buttons/button";

export const UserEdit = () => {
    const { resources, resource, action, id } = useResource();

    console.log("resources", resources);
    console.log("resource", resource);
    console.log("action", action);
    console.log("id", id);

    return (
        <Edit
            breadcrumb={<Breadcrumb />}
            footerButtons={<><DeleteButton onDelete={() => {
                console.log(id)
            }} /><SaveButton style={{
                textTransform: 'none'
            }} /></>}
        >
            Edit {id}
        </Edit>
    )

}