import { zodResolver } from "@hookform/resolvers/zod";
import { useCallback, useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import { toast } from "sonner";
import { schemaPautaFormData } from "../components/partials/pauta/pauta.schema";
import PautaService from "../services/pauta.service";
import type { PautaRequest, PautaResponse } from "../types/pauta.type";

const pautaService = new PautaService();

export default function usePauta(tamanhoPagina: number = 10) {
  const tamanho = tamanhoPagina;
  const [abrirModal, setAbrirModal] = useState(false);
  const [pagina, setPagina] = useState(0);
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

  const getPautasPage = useCallback(async () => {
    const response = await pautaService.getPage({
      pagina: pagina,
      tamanho: tamanho,
    });
    if (response) {
      setPautas(response.content);
      setTotalPaginas(response.totalPages);
    }
  }, [pagina, tamanho]);

  useEffect(() => {
    const carregarPautas = async () => {
      await getPautasPage();
    };

    carregarPautas();
  }, [pagina, getPautasPage]);

  return {
    abrirModal,
    pagina,
    tamanho,
    totalPaginas,
    isLoading,
    pautas,
    control,
    handleCriarOuEditarSubmit,
    toggleModal,
    setPagina,
    getPautasPage,
  };
}
