import { zodResolver } from "@hookform/resolvers/zod";
import { Grid, Pagination } from "@mui/material";
import { useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import { toast } from "sonner";
import CustomButton from "../components/custom-button/CustomButton";
import Modal from "../components/modal/Modal";
import { schemaAssociadoFormData } from "../components/partials/associado/associado.schema";
import AssociadoCard from "../components/partials/associado/card/AssociadoCard";
import AssociadoForm from "../components/partials/associado/form/AssociadoForm";
import AssociadoService from "../services/associado.service";
import type {
  AssociadoRequest,
  AssociadoResponse,
} from "../types/associado.type";

export default function Associado() {
  const [abrirModal, setAbrirModal] = useState(false);
  const [pagina, setPagina] = useState(0);
  const tamanho = 10;
  const [totalPaginas, setTotalPaginas] = useState(0);
  const [associados, setAssociados] = useState<AssociadoResponse[]>([]);
  const { control, reset } = useForm<AssociadoRequest>({
    defaultValues: {
      cpf: "",
    },
    resolver: zodResolver(schemaAssociadoFormData),
  });
  const associadoService = new AssociadoService();

  const toggleModal = () => {
    setAbrirModal(!abrirModal);
    reset();
  };

  const handleCriarOuEditarSubmit = async ({
    data,
  }: {
    data: AssociadoRequest;
  }) => {
    await associadoService
      .post(data)
      .then(() => {
        toast.success("Associado criado com sucesso!");
        toggleModal();
        getPautasPage();
      })
      .catch((error) => {
        const isErroCpf = error.response.data.details[0].includes("CPF");
        console.log(error.response.data.details);
        const mensagem = isErroCpf
          ? "CPF Inválido"
          : error.response.data.message;
        toast.error(mensagem);
      });
  };

  const getPautasPage = async () => {
    const response = await associadoService.getPage({
      pagina: pagina,
      tamanho: tamanho,
    });
    if (response) {
      setAssociados(response.content);
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
      sx={{ minHeight: "calc(100vh - 32px)", flexDirection: "column" }}
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
          marginTop: "auto",
          paddingTop: 2,
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
      />
    </Grid>
  );
}
