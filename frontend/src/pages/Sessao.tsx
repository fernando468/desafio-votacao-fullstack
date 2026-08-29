import { Grid, Pagination } from "@mui/material";
import CustomButton from "../components/custom-button/CustomButton";
import Modal from "../components/modal/Modal";
import SessaoCard from "../components/partials/sessao/card/SessaoCard";
import SessaoForm from "../components/partials/sessao/form/SessaoForm";
import useSessao from "../hooks/useSessao";

export default function Sessao() {
  const {
    abrirModal,
    pagina,
    totalPaginas,
    isLoading,
    sessoes,
    control,
    handleCriarOuEditarSubmit,
    toggleModal,
    setPagina,
    associados,
    pautas,
  } = useSessao();

  return (
    <Grid
      container
      spacing={2}
      sx={{ minHeight: "100vh", flexDirection: "column" }}
    >
      <Grid sx={{ width: "100%" }}>
        <CustomButton variant="contained" color="primary" onClick={toggleModal}>
          Criar Sessão
        </CustomButton>
      </Grid>
      <Grid sx={{ width: "100%" }}>
        <SessaoCard sessoes={sessoes} associados={associados} />
      </Grid>
      <Grid
        sx={{
          display: "flex",
          justifyContent: "center",
          width: "100%",
        }}
      >
        <Pagination
          count={totalPaginas}
          page={pagina + 1}
          onChange={(_, page) => setPagina(page - 1)}
          color="primary"
        />
      </Grid>
      <Modal
        title="Criar Sessão"
        content={
          <SessaoForm
            control={control}
            onSubmit={handleCriarOuEditarSubmit}
            pautas={pautas}
          />
        }
        openDialog={abrirModal}
        textConfirm="Confirmar"
        textCancel="Cancelar"
        actionButtonConfirm={() => {}}
        actionButtonCancel={toggleModal}
        colorButton="primary"
        formId="form-criar-sessao"
        isLoading={isLoading}
      />
    </Grid>
  );
}
