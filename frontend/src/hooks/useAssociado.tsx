import { zodResolver } from "@hookform/resolvers/zod";
import { useCallback, useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import { toast } from "sonner";
import {
  schemaAssociadoFormData,
  type AssociadoFormDataInput,
  type AssociadoFormDataOutput,
} from "../components/partials/associado/associado.schema";
import AssociadoService from "../services/associado.service";
import type {
  AssociadoRequest,
  AssociadoResponse,
} from "../types/associado.type";

const associadoService = new AssociadoService();

export default function useAssociado(tamanhoPagina: number = 10) {
  const tamanho = tamanhoPagina;
  const [abrirModal, setAbrirModal] = useState(false);
  const [pagina, setPagina] = useState(0);
  const [isLoading, setIsLoading] = useState(false);
  const [totalPaginas, setTotalPaginas] = useState(0);
  const [associados, setAssociados] = useState<AssociadoResponse[]>([]);
  const { control, reset } = useForm<
    AssociadoFormDataInput,
    any,
    AssociadoFormDataOutput
  >({
    defaultValues: {
      cpf: "",
    },
    resolver: zodResolver(schemaAssociadoFormData),
  });

  const toggleModal = () => {
    setAbrirModal(!abrirModal);
    reset();
  };

  const handleCriarOuEditarSubmit = async ({
    data,
  }: {
    data: AssociadoRequest;
  }) => {
    setIsLoading(true);

    await associadoService
      .post(data)
      .then(() => {
        toast.success("Associado criado com sucesso!");
        toggleModal();
        getAssociadoPage();
        setIsLoading(false);
      })
      .catch((error) => {
        const isErroCpf = error.response.data.details[0].includes("CPF");
        console.log(error.response.data.details);
        const mensagem = isErroCpf
          ? "CPF Inválido"
          : error.response.data.message;
        toast.error(mensagem);
        setIsLoading(false);
      });
  };

  const getAssociadoPage = useCallback(async () => {
    const response = await associadoService.getPage({
      pagina: pagina,
      tamanho: tamanho,
    });
    if (response) {
      setAssociados(response.content);
      setTotalPaginas(response.totalPages);
    }
  }, [pagina, tamanho]);

  useEffect(() => {
    const carregarAssociado = async () => {
      await getAssociadoPage();
    };

    carregarAssociado();
  }, [pagina, getAssociadoPage]);

  return {
    abrirModal,
    pagina,
    tamanho,
    totalPaginas,
    isLoading,
    associados,
    control,
    handleCriarOuEditarSubmit,
    toggleModal,
    setPagina,
    setAbrirModal,
    reset,
    getAssociadoPage,
  };
}
