import { Grid, Pagination } from "@mui/material";
import CustomButton from "../components/custom-button/CustomButton";
import Modal from "../components/modal/Modal";
import PautaCard from "../components/partials/pauta/card/PautaCard";
import PautaForm from "../components/partials/pauta/form/PautaForm";
import usePauta from "../hooks/usePauta";

export default function Pauta() {
  const {
    abrirModal,
    pagina,
    totalPaginas,
    isLoading,
    pautas,
    control,
    handleCriarOuEditarSubmit,
    toggleModal,
    setPagina,
  } = usePauta();

  return (
    <Grid
      container
      spacing={2}
      sx={{ minHeight: "100vh", flexDirection: "column" }}
    >
      <Grid sx={{ width: "100%" }}>
        <CustomButton variant="contained" color="primary" onClick={toggleModal}>
          Criar Pauta
        </CustomButton>
      </Grid>
      <Grid sx={{ width: "100%" }}>
        <PautaCard pautas={pautas} />
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
        title="Criar Pauta"
        content={
          <PautaForm control={control} onSubmit={handleCriarOuEditarSubmit} />
        }
        openDialog={abrirModal}
        textConfirm="Confirmar"
        textCancel="Cancelar"
        actionButtonConfirm={() => {}}
        actionButtonCancel={toggleModal}
        colorButton="primary"
        formId="form-criar-pauta"
        isLoading={isLoading}
      />
    </Grid>
  );
}
