export const resources = [
    {
      name: "users",
      list: "/users",
      create: "/users/create",
      edit: "/users/edit/:id",
      show: "/users/show/:id",
      meta: {
        canDelete: true,
      },
    },
  ]