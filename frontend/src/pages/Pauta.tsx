import { zodResolver } from "@hookform/resolvers/zod";
import { Grid, Pagination } from "@mui/material";
import { useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import { toast } from "sonner";
import CustomButton from "../components/custom-button/CustomButton";
import Modal from "../components/modal/Modal";
import PautaCard from "../components/partials/pauta/card/PautaCard";
import PautaForm from "../components/partials/pauta/form/PautaForm";
import { schemaPautaFormData } from "../components/partials/pauta/pauta.schema";
import PautaService from "../services/pauta.service";
import type { PautaRequest, PautaResponse } from "../types/pauta.type";

const pautaService = new PautaService();

export default function Pauta() {
  const [abrirModal, setAbrirModal] = useState(false);
  const [pagina, setPagina] = useState(0);
  const tamanho = 10;
  const [totalPaginas, setTotalPaginas] = useState(0);
  const [isLoading, setIsLoading] = useState(false);
  const [pautas, setPautas] = useState<PautaResponse[]>([]);
  const { control, reset } = useForm<PautaRequest>({
    defaultValues: {
      descricao: "",
      titulo: "",
    },
    resolver: zodResolver(schemaPautaFormData),
  });
  const toggleModal = () => {
    setAbrirModal(!abrirModal);
    reset();
  };

  const handleCriarOuEditarSubmit = async ({
    data,
  }: {
    data: PautaRequest;
  }) => {
    setIsLoading(true);

    await pautaService
      .post(data)
      .then(() => {
        toggleModal();
        getPautasPage();
        setIsLoading(false);
      })
      .catch((error) => {
        const isErroTitulo = error.response.data.details[0].includes("Titulo");
        const mensagem = isErroTitulo
          ? "Titulo Inválido"
          : error.response.data.message;
        toast.error(mensagem);
        setIsLoading(false);
      });
  };

  const getPautasPage = async () => {
    const response = await pautaService.getPage({
      pagina: pagina,
      tamanho: tamanho,
    });
    if (response) {
      setPautas(response.content);
      setTotalPaginas(response.totalPages);
    }
  };

  useEffect(() => {
    const carregarPautas = async () => {
      await getPautasPage();
    };

    carregarPautas();
  }, [pagina]);

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
