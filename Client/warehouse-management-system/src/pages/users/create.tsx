import { useResource } from "@refinedev/core";

export const UserCreate = () => {

    const { resources, resource, action, id } = useResource();

    console.log("resources", resources);
    console.log("resource", resource);
    console.log("action", action);
    console.log("id", id);

    return <div>
        Create
    </div>
}