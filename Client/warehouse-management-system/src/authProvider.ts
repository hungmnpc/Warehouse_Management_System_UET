import { AuthBindings } from "@refinedev/core";
import { login } from "./utils/request";
import { JwtPayload, jwtDecode } from "jwt-decode";

export const TOKEN_KEY = "refine-auth";
export const ACCESS_TOKEN = "access-token"

export interface JWTPayloadCustom extends JwtPayload {
  roles?:string;
  fullName?: string;
}

export const authProvider: AuthBindings = {
  login: async ({ username, email, password }) => {
    if ((username || email) && password) {
      const response = await login(username, password);
      console.log(response);
      if (response.result.responseCode === '00') {
        localStorage.setItem(TOKEN_KEY, username);
        localStorage.setItem(ACCESS_TOKEN, response.data.access_token);
        getUserInfoByAccessToken(response.data.access_token);
        return {
                success: true,
                redirectTo: "/",
              };
      } else {
        return {
          success: false,
          error: {
            name: "",
            message: response.result.message,
          },
        };
      }
      // Handle Login ở đây
      // localStorage.setItem(TOKEN_KEY, username);
      // return {
      //   success: true,
      //   redirectTo: "/",
      // };
    }

    return {
      success: false,
      error: {
        name: "LoginError",
        message: "Invalid username or password",
      },
    };
  },
  logout: async () => {
    localStorage.removeItem(ACCESS_TOKEN);
    localStorage.removeItem(TOKEN_KEY);
    return {
      success: true,
      redirectTo: "/login",
    };
  },
  check: async () => {
    const token = localStorage.getItem(ACCESS_TOKEN);
    if (token) {
      const decode = jwtDecode(token);
      if (tokenValid(decode.exp)) {
        return {
          authenticated: true,
        };
      }
    }
    return {
      authenticated: false,
      redirectTo: "/login",
    };
  },
  getPermissions: async () => null,
  getIdentity: async () => {
    const token = localStorage.getItem(TOKEN_KEY);
    const accessToken = localStorage.getItem(ACCESS_TOKEN);
    if (accessToken) {
      const decode = jwtDecode<JWTPayloadCustom>(accessToken);
      return {
        id: 1,
        name: decode.fullName,
        avatar: "https://i.pravatar.cc/300",
      };
    }
    if (token) {
      return {
        id: 1,
        name: "John Doe",
        avatar: "https://i.pravatar.cc/300",
      };
    }
    return null;
  },
  onError: async (error) => {
    console.error(error);
    return { error };
  },
};

const checkTokenExp = (token:string) => {
  
}

const getUserInfoByAccessToken = (accessToken:string) => {
  if (accessToken) {
      const decode = jwtDecode<JWTPayloadCustom>(accessToken);
      console.log(decode);
      if (tokenValid(decode.exp)) {
          return {
              name: decode.sub,
              roles: decode.roles,
          };
      } else {
          localStorage.removeItem('access_token');
      }
  }

  return null;
};

const tokenValid = (expiry:any) => {
  const currentDate = Date.now() / 1000;
  return currentDate < expiry;
};
