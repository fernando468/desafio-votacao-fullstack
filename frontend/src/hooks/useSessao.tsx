import { zodResolver } from "@hookform/resolvers/zod";
import { format, set } from "date-fns";
import { useCallback, useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import { toast } from "sonner";
import { schemaSessaoFormData } from "../components/partials/sessao/sessao.schema";
import SessaoService from "../services/sessao.service";
import type { SessaoRequest, SessaoResponse } from "../types/sessao.type";
import useAssociado from "./useAssociado";
import usePauta from "./usePauta";

const sessaoService = new SessaoService();

export default function useSessao() {
  const tamanho = 10;
  const [abrirModal, setAbrirModal] = useState(false);
  const { control, reset } = useForm<SessaoRequest>({
    defaultValues: {
      tempoEmMinuto: 1,
      dataInicio: "",
      pautaId: 0,
    },
    resolver: zodResolver(schemaSessaoFormData),
  });
  const [pagina, setPagina] = useState(0);
  const [totalPaginas, setTotalPaginas] = useState(0);
  const [sessoes, setSessoes] = useState<SessaoResponse[]>([]);
  const [isLoading, setIsLoading] = useState(false);

  const { getPautasPage, pautas } = usePauta(999);
  const { getAssociadoPage, associados } = useAssociado(999);

  const toggleModal = () => {
    setAbrirModal(!abrirModal);
    reset();
  };

  const handleCriarOuEditarSubmit = async ({
    data,
  }: {
    data: SessaoRequest;
  }) => {
    setIsLoading(true);

    const dataInicio = new Date(data.dataInicio);
    const agora = new Date();
    const resultado = format(
      set(dataInicio, {
        hours: agora.getHours(),
        minutes: agora.getMinutes(),
        seconds: agora.getSeconds(),
        milliseconds: agora.getMilliseconds(),
      }),
      "yyyy-MM-dd'T'HH:mm:ss.SSS",
    );

    await sessaoService
      .post({
        ...data,
        dataInicio: resultado,
      })
      .then(() => {
        toast.success("Sessão criada com sucesso!");
        toggleModal();
        getSessoesPage();
        setIsLoading(false);
      })
      .catch((error) => {
        toast.error(error.response.data.message);
        setIsLoading(false);
      });
  };

  const getSessoesPage = useCallback(async () => {
    const response = await sessaoService.getPage({
      pagina: pagina,
      tamanho: tamanho,
    });

    if (response) {
      setSessoes(response.content);
      setTotalPaginas(response.totalPages);
    }
  }, [pagina, tamanho]);

  useEffect(() => {
    const carregarSessoes = async () => {
      await getSessoesPage();
    };

    carregarSessoes();
  }, [pagina, getSessoesPage]);

  return {
    abrirModal,
    pagina,
    tamanho,
    totalPaginas,
    isLoading,
    sessoes,
    associados,
    control,
    handleCriarOuEditarSubmit,
    toggleModal,
    setPagina,
    setAbrirModal,
    reset,
    getSessoesPage,
    getAssociadoPage,
    getPautasPage,
    pautas,
  };
}
