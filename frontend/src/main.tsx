import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import {
  createBrowserRouter,
  Navigate,
  RouterProvider,
} from "react-router-dom";
import { Toaster } from "sonner";
import Sidebar from "./components/sidebar/Sidebar.tsx";
import "./index.css";
import Associado from "./pages/Associado.tsx";
import Pauta from "./pages/Pauta.tsx";
import Sessao from "./pages/Sessao.tsx";

const routes = createBrowserRouter([
  {
    path: "/",
    element: <Sidebar />,
    children: [
      {
        index: true,
        element: <Associado />,
      },
      {
        path: "sessao",
        element: <Sessao />,
      },

      {
        path: "pauta",
        element: <Pauta />,
      },
      {
        path: "*",
        element: <Navigate to="/" replace />,
      },
    ],
  },
]);

createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <Toaster position="top-center" duration={3000} closeButton richColors />
    <RouterProvider router={routes} />
  </StrictMode>,
);
