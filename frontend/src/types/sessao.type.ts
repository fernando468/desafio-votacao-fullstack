import type { PautaResponse } from "./pauta.type";

export type SessaoRequest = {
  pautaId: number;
  dataInicio: string;
  dataFim: string;
};

export type SessaoResponse = {
  id: number;
  associadoId: number;
  sessaoId: number;
  dataCriacao: string;
  dataVotacao: string;
  pauta: PautaResponse;
};
