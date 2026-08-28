import { z } from "zod";
import { TipoVoto } from "../../../types/tipoVoto.type";

const schemaSessaoFormData = z.object({
  dataFim: z.string().min(1, "Data fim é obrigatória"),
  dataInicio: z.string().min(1, "Data inicio é obrigatória"),
  pautaId: z
    .number({ error: "Pauta é obrigatória" })
    .min(1, "Pauta é obrigatória"),
});

type SessaoFormData = z.infer<typeof schemaSessaoFormData>;

const schemaVotoFormData = z.object({
  associadoId: z.number().min(1, "Associado é obrigatória"),
  sessaoId: z.number().min(1, "Sessão é obrigatória"),
  tipoVoto: z.enum([TipoVoto.SIM, TipoVoto.NAO]),
});

type VotoFormData = z.infer<typeof schemaVotoFormData>;

export { schemaSessaoFormData, schemaVotoFormData };
export type { SessaoFormData, VotoFormData };
