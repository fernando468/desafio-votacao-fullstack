import { zodResolver } from "@hookform/resolvers/zod";
import { Grid, Pagination } from "@mui/material";
import { useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import { toast } from "sonner";
import CustomButton from "../components/custom-button/CustomButton";
import Modal from "../components/modal/Modal";
import SessaoCard from "../components/partials/sessao/card/SessaoCard";
import SessaoForm from "../components/partials/sessao/form/SessaoForm";
import { schemaSessaoFormData } from "../components/partials/sessao/sessao.schema";
import PautaService from "../services/pauta.service";
import AssociadoService from "../services/pauta.service copy";
import SessaoService from "../services/sessao.service";
import type { AssociadoResponse } from "../types/associado.type";
import type { PautaResponse } from "../types/pauta.type";
import type { SessaoRequest, SessaoResponse } from "../types/sessao.type";

export default function Sessao() {
  const [abrirModal, setAbrirModal] = useState(false);
  const [pautas, setPautas] = useState<PautaResponse[]>([]);
  const { control, reset } = useForm<SessaoRequest>({
    defaultValues: {
      dataFim: "",
      dataInicio: "",
      pautaId: 1,
    },
    resolver: zodResolver(schemaSessaoFormData),
  });
  const [pagina, setPagina] = useState(0);
  const tamanho = 10;
  const [totalPaginas, setTotalPaginas] = useState(0);
  const [sessoes, setSessoes] = useState<SessaoResponse[]>([]);
  const [associados, setAssociados] = useState<AssociadoResponse[]>([]);
  const sessaoService = new SessaoService();
  const pautaService = new PautaService();
  const associadoService = new AssociadoService();

  const toggleModal = () => {
    setAbrirModal(!abrirModal);
    reset();
  };

  const handleCriarOuEditarSubmit = async ({
    data,
  }: {
    data: SessaoRequest;
  }) => {
    await sessaoService
      .post(data)
      .then(() => {
        toast.success("Sessão criada com sucesso!");
        toggleModal();
        getSessoesPage();
      })
      .catch((error) => {
        toast.error(error.response.data.message);
      });
  };

  const getSessoesPage = async () => {
    const response = await sessaoService.getPage({
      pagina: pagina,
      tamanho: tamanho,
    });
    console.log(response);
    if (response) {
      setSessoes(response.content);
      setTotalPaginas(response.totalPages);
    }
  };

  const getPautasPage = async () => {
    const response = await pautaService.getPage({
      pagina: pagina,
      tamanho: tamanho,
    });
    if (response) {
      setPautas(response.content);
    }
  };

  const getAssociadosPage = async () => {
    const response = await associadoService.getPage({
      pagina: pagina,
      tamanho: tamanho,
    });
    if (response) {
      setAssociados(response.content);
    }
  };

  useEffect(() => {
    const carregarAssociados = async () => {
      await getAssociadosPage();
    };

    carregarAssociados();
  }, []);

  useEffect(() => {
    const carregarPautas = async () => {
      await getPautasPage();
    };

    carregarPautas();
  }, []);

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
        <SessaoCard sessoes={sessoes} associados={associados} />
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
      />
    </Grid>
  );
}
