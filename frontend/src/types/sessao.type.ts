import type { PautaResponse } from "./pauta.type";

export type SessaoRequest = {
  pautaId: number;
  dataInicio: string;
  tempoEmMinuto: number;
};

export type SessaoResponse = {
  id: number;
  dataInicio: string;
  dataFim: string;
  isAberta: boolean;
  pauta: PautaResponse;
};
