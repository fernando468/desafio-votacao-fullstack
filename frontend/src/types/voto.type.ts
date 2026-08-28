import type { AssociadoResponse } from "./associado.type";
import type { SessaoResponse } from "./sessao.type";
import { TipoVoto } from "./tipoVoto.type";

type VotoRequest = {
  associadoId: number;
  sessaoId: number;
  tipoVoto: TipoVoto;
};

type VotoResponse = {
  id: number;
  tipoVoto: TipoVoto;
  associado: AssociadoResponse;
  sessao: SessaoResponse;
};

export type { VotoRequest, VotoResponse };
