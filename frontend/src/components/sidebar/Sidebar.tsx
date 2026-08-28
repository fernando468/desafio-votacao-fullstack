import type { JSX } from "@emotion/react/jsx-runtime";
import { Assignment, Home, PlayCircle } from "@mui/icons-material";
import {
  Box,
  Drawer,
  Grid,
  List,
  ListItem,
  ListItemButton,
  ListItemIcon,
  ListItemText,
  Paper,
  Typography,
  useTheme,
} from "@mui/material";
import { Outlet, useNavigate } from "react-router-dom";

type MenuItem = {
  name: string;
  path: string;
  icon: JSX.Element;
};

const menuItems: MenuItem[] = [
  { name: "Associado", path: "/", icon: <Home /> },
  { name: "Pauta", path: "/pauta", icon: <Assignment /> },
  { name: "Sessão", path: "/sessao", icon: <PlayCircle /> },
];

export default function Sidebar() {
  const theme = useTheme();
  const navigate = useNavigate();

  const navegarPagina = (path: string) => {
    navigate(path);
  };

  return (
    <Box sx={{ display: "flex" }}>
      <Drawer
        variant="permanent"
        sx={{ display: { xs: "none", md: "block", width: 250 } }}
        slotProps={{
          paper: {
            sx: {
              backgroundColor: "#007efd",
              color: "#fff",
              width: 250,
              boxShadow: "0px 0px 10px rgba(0, 0, 0, 0.5)",
            },
          },
        }}
      >
        <List>
          {menuItems.map((item, index) => {
            const isActive = location.pathname === item.path;
            return (
              <ListItem key={`${item.name}-${index}`} disablePadding>
                <ListItemButton
                  sx={{
                    color: theme.palette.primary.contrastText,
                    backgroundColor: isActive
                      ? theme.palette.primary.dark
                      : "transparent",
                    "&:hover": {
                      backgroundColor: isActive
                        ? theme.palette.primary.dark
                        : "rgba(255, 255, 255, 0.1)",
                    },
                  }}
                  onClick={() => navegarPagina(item.path)}
                >
                  <ListItemIcon
                    sx={{ color: theme.palette.primary.contrastText }}
                  >
                    {item.icon}
                  </ListItemIcon>
                  <ListItemText primary={item.name} />
                </ListItemButton>
              </ListItem>
            );
          })}
        </List>
      </Drawer>
      <Box
        component="main"
        sx={{
          flexGrow: 1,
          p: 2,
          minWidth: 0,
          height: "100vh",
        }}
      >
        <Grid container spacing={2}>
          <Grid sx={{ width: "100%" }}>
            <Paper sx={{ p: 1.5, borderRadius: 2, width: "100%" }}>
              <Typography variant="h4">
                {
                  menuItems.find((item) => item.path === location.pathname)
                    ?.name
                }
              </Typography>
            </Paper>
          </Grid>
          <Outlet />
        </Grid>
      </Box>
    </Box>
  );
}
