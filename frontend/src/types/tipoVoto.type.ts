export const TipoVoto = {
  SIM: "SIM",
  NAO: "NAO",
} as const;

export type TipoVoto = (typeof TipoVoto)[keyof typeof TipoVoto];
