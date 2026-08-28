import { zodResolver } from "@hookform/resolvers/zod";
import { Grid, Pagination } from "@mui/material";
import { useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import CustomButton from "../components/custom-button/CustomButton";
import Modal from "../components/modal/Modal";
import SessaoCard from "../components/partials/sessao/card/SessaoCard";
import SessaoForm from "../components/partials/sessao/form/SessaoForm";
import { schemaSessaoFormData } from "../components/partials/sessao/sessao.schema";
import SessaoService from "../services/sessao.service";
import type { SessaoRequest, SessaoResponse } from "../types/sessao.type";

export default function Sessao() {
  const [abrirModal, setAbrirModal] = useState(false);
  const { control, reset } = useForm<SessaoRequest>({
    defaultValues: {
      dataFim: "",
      dataInicio: "",
      pautaId: 1,
    },
    resolver: zodResolver(schemaSessaoFormData),
  });
  const sessaoService = new SessaoService();
  const [pagina, setPagina] = useState(0);
  const tamanho = 10;
  const [totalPaginas, setTotalPaginas] = useState(0);
  const [sessoes, setSessoes] = useState<SessaoResponse[]>([]);

  const toggleModal = () => {
    setAbrirModal(!abrirModal);
  };

  const handleCriarOuEditarSubmit = async ({
    data,
  }: {
    data: SessaoRequest;
  }) => {
    const response = await sessaoService.post(data);
    if (response) {
      toggleModal();
      reset();
    }
  };

  const getSessoesPage = async () => {
    const response = await sessaoService.getPage({
      pagina: pagina,
      tamanho: tamanho,
    });
    if (response) {
      setSessoes(response.content);
      setTotalPaginas(response.totalPages);
    }
  };

  useEffect(() => {
    const carregarSessoes = async () => {
      await getSessoesPage();
    };

    carregarSessoes();
  }, [pagina]);

  return (
    <Grid
      container
      spacing={2}
      sx={{ minHeight: "calc(100vh - 32px)", flexDirection: "column" }}
    >
      <Grid sx={{ width: "100%" }}>
        <CustomButton variant="contained" color="primary" onClick={toggleModal}>
          Criar Sessão
        </CustomButton>
      </Grid>
      <Grid sx={{ width: "100%" }}>
        <SessaoCard sessoes={sessoes} />
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
        title="Criar Sessão"
        content={
          <SessaoForm control={control} onSubmit={handleCriarOuEditarSubmit} />
        }
        openDialog={abrirModal}
        textConfirm="Confirmar"
        textCancel="Cancelar"
        actionButtonConfirm={() => {}}
        actionButtonCancel={toggleModal}
        colorButton="primary"
        formId="form-criar-sessao"
      />
    </Grid>
  );
}
