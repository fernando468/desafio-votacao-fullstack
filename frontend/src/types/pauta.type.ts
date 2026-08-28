type PautaRequest = {
  descricao: string;
  titulo: string;
};

type PautaResponse = {
  id: number;
  descricao: string;
  titulo: string;
};

export type { PautaRequest, PautaResponse };
