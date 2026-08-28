import { z } from "zod";

const schemaSessaoFormData = z.object({
  dataFim: z.string().min(1, "Data fim é obrigatória"),
  dataInicio: z.string().min(1, "Data inicio é obrigatória"),
  pautaId: z.number(),
});

type SessaoFormData = z.infer<typeof schemaSessaoFormData>;

export { schemaSessaoFormData };
export type { SessaoFormData };
