import { AuthPage, DarkTheme, LightTheme, ThemedTitleV2 } from "@refinedev/mui";
import { AppIcon } from "../../components/app-icon";
import { Avatar, Box, Button, Checkbox, Container, CssBaseline, FormControl, FormControlLabel, Grid, Link, TextField, ThemeProvider, Typography, createTheme } from "@mui/material";
import { Copyright } from "@mui/icons-material";
import { useLogin } from "@refinedev/core";

const defaultTheme = createTheme();
export interface ILoginForm {
  username: string;
  password: string;
  remember: boolean;
}
export const Login = () => {

  const theme = localStorage.getItem("colorMode") ? LightTheme : localStorage.getItem("colorMode") === 'dark' ? DarkTheme : LightTheme;
  const { mutate: login } = useLogin<ILoginForm>();
  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    console.log(data.get('remember'));
    console.log({
      email: data.get('username')?.toString(),
      password: data.get('password')?.toString(),
      remember: data.get('remember') != null
    });
    let username = data.get('username')?.toString();
    let password = data.get('password')?.toString();
    let remember = data.get('remember') != null;
    if (username && password) {
      const loginValue = {
        username: username,
        password: password,
        remember: remember
      }
      login(loginValue);
    }
  };



  return (
    <Container component="main" maxWidth="xs">
      <CssBaseline />
      <Box
        sx={{
          marginTop: 8,
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center',
        }}
      >
        <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
          {/* <LockOutlinedIcon /> */}
        </Avatar>
        <Typography component="h1" variant="h5">
          Sign in
        </Typography>
        <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
          <TextField
            margin="normal"
            required
            fullWidth
            id="username"
            label="Username"
            name="username"
            autoComplete="username"
            autoFocus
          />
          <TextField
            margin="normal"
            required
            fullWidth
            name="password"
            label="Password"
            type="password"
            id="password"
            autoComplete="current-password"
          />
          <FormControlLabel
            control={<Checkbox name="remember" value="remember" color="primary" />}
            label="Remember me"
          />
          <Button
            type="submit"
            fullWidth
            variant="contained"
            sx={{ mt: 3, mb: 2 }}
          >
            Sign In
          </Button>
          <Grid container>
            <Grid item xs>
              <Link href="#" variant="body2">
                Forgot password?
              </Link>
            </Grid>
            <Grid item>
              {/* <Link href="#" variant="body2">
                  {"Don't have an account? Sign Up"}
                </Link> */}
            </Grid>
          </Grid>
        </Box>
      </Box>
    </Container>
  );
};
