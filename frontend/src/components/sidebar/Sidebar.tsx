import type { JSX } from "@emotion/react/jsx-runtime";
import { Assignment, Home, Menu, PlayCircle } from "@mui/icons-material";
import {
  AppBar,
  Box,
  Drawer,
  Grid,
  IconButton,
  List,
  ListItem,
  ListItemButton,
  ListItemIcon,
  ListItemText,
  Paper,
  Toolbar,
  Typography,
  useMediaQuery,
  useTheme,
} from "@mui/material";
import { useEffect, useState } from "react";
import { Outlet, useLocation, useNavigate } from "react-router-dom";

type MenuItem = {
  name: string;
  path: string;
  icon: JSX.Element;
};

const drawerWidth = 250;

const menuItems: MenuItem[] = [
  { name: "Associado", path: "/", icon: <Home /> },
  { name: "Pauta", path: "/pauta", icon: <Assignment /> },
  { name: "Sessão", path: "/sessao", icon: <PlayCircle /> },
];

export default function Sidebar() {
  const theme = useTheme();
  const navigate = useNavigate();
  const location = useLocation();

  const isMobile = useMediaQuery(theme.breakpoints.down("md"));

  const [mobileOpen, setMobileOpen] = useState(false);

  const navegarPagina = (path: string) => {
    navigate(path);

    if (isMobile) {
      setMobileOpen(false);
    }
  };

  useEffect(() => {
    const menuItem = menuItems.find(
      (menuItem) => menuItem.path === location.pathname,
    );
    document.title = `${menuItem?.name} | Votação Associados`;
  }, [location]);

  const drawerContent = (
    <List>
      {menuItems.map((item, index) => {
        const isActive = location.pathname === item.path;

        return (
          <ListItem key={`${item.name}-${index}`} disablePadding>
            <ListItemButton
              onClick={() => navegarPagina(item.path)}
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
            >
              <ListItemIcon
                sx={{
                  color: theme.palette.primary.contrastText,
                  minWidth: 45,
                }}
              >
                {item.icon}
              </ListItemIcon>

              <ListItemText primary={item.name} />
            </ListItemButton>
          </ListItem>
        );
      })}
    </List>
  );

  return (
    <Box sx={{ display: "flex", minHeight: "100vh" }}>
      {isMobile && (
        <AppBar
          position="fixed"
          sx={{
            backgroundColor: "#007efd",
          }}
        >
          <Toolbar>
            <IconButton
              color="inherit"
              edge="start"
              onClick={() => setMobileOpen(true)}
              sx={{ mr: 2 }}
            >
              <Menu />
            </IconButton>

            <Typography variant="h6">
              {menuItems.find((item) => item.path === location.pathname)?.name}
            </Typography>
          </Toolbar>
        </AppBar>
      )}

      {!isMobile && (
        <Drawer
          variant="permanent"
          sx={{
            width: drawerWidth,
            flexShrink: 0,
            "& .MuiDrawer-paper": {
              width: drawerWidth,
              boxSizing: "border-box",
              backgroundColor: "#007efd",
              color: "#fff",
              boxShadow: "0px 0px 10px rgba(0, 0, 0, 0.5)",
            },
          }}
        >
          {drawerContent}
        </Drawer>
      )}

      {isMobile && (
        <Drawer
          variant="temporary"
          open={mobileOpen}
          onClose={() => setMobileOpen(false)}
          ModalProps={{
            keepMounted: true,
          }}
          sx={{
            "& .MuiDrawer-paper": {
              width: drawerWidth,
              boxSizing: "border-box",
              backgroundColor: "#007efd",
              color: "#fff",
            },
          }}
        >
          {drawerContent}
        </Drawer>
      )}

      <Box
        component="main"
        sx={{
          flexGrow: 1,
          minWidth: 0,
          minHeight: "100vh",
          p: { xs: 1, sm: 2 },
          pt: { xs: 10, md: 2 },
        }}
      >
        <Grid container spacing={2}>
          <Grid size={12}>
            <Paper
              sx={{
                p: { xs: 1.5, sm: 2 },
                borderRadius: 2,
                width: "100%",
              }}
            >
              <Typography
                variant="h4"
                sx={{
                  fontSize: {
                    xs: "1.5rem",
                    sm: "2rem",
                    md: "2.125rem",
                  },
                }}
              >
                {
                  menuItems.find((item) => item.path === location.pathname)
                    ?.name
                }
              </Typography>
            </Paper>
          </Grid>

          <Grid size={12}>
            <Outlet />
          </Grid>
        </Grid>
      </Box>
    </Box>
  );
}
