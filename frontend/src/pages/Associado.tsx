import { Grid, Pagination } from "@mui/material";
import CustomButton from "../components/custom-button/CustomButton";
import Modal from "../components/modal/Modal";
import AssociadoCard from "../components/partials/associado/card/AssociadoCard";
import AssociadoForm from "../components/partials/associado/form/AssociadoForm";
import useAssociado from "../hooks/useAssociado";

export default function Associado() {
  const {
    abrirModal,
    pagina,
    totalPaginas,
    isLoading,
    associados,
    control,
    handleCriarOuEditarSubmit,
    toggleModal,
    setPagina,
  } = useAssociado();

  return (
    <Grid
      container
      spacing={2}
      sx={{ minHeight: "100vh", flexDirection: "column" }}
    >
      <Grid sx={{ width: "100%" }}>
        <CustomButton variant="contained" color="primary" onClick={toggleModal}>
          Criar Associado
        </CustomButton>
      </Grid>
      <Grid sx={{ width: "100%" }}>
        <AssociadoCard associados={associados} />
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
        title="Criar Associado"
        content={
          <AssociadoForm
            control={control}
            onSubmit={handleCriarOuEditarSubmit}
          />
        }
        openDialog={abrirModal}
        textConfirm="Confirmar"
        textCancel="Cancelar"
        actionButtonConfirm={() => {}}
        actionButtonCancel={toggleModal}
        colorButton="primary"
        formId="form-criar-associado"
        isLoading={isLoading}
      />
    </Grid>
  );
}
