import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import Sidebar from "./components/sidebar/Sidebar.tsx";
import "./index.css";
import Inicio from "./pages/Inicio.tsx";
import Pauta from "./pages/Pauta.tsx";
import Sessao from "./pages/Sessao.tsx";
import Votacao from "./pages/Votacao.tsx";

const routes = createBrowserRouter([
  {
    path: "/",
    element: <Sidebar />,
    children: [
      {
        index: true,
        element: <Inicio />,
      },
      {
        path: "sessao",
        element: <Sessao />,
      },
      {
        path: "votacao",
        element: <Votacao />,
      },
      {
        path: "pauta",
        element: <Pauta />,
      },
    ],
  },
]);

createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <RouterProvider router={routes} />
  </StrictMode>,
);
