import { z } from "zod";

const schemaPautaFormData = z.object({
  descricao: z
    .string()
    .min(3, "Mínimo 3 caracteres")
    .max(150, "Máximo 150 caracteres"),
  titulo: z
    .string()
    .min(3, "Mínimo 3 caracteres")
    .max(30, "Máximo 30 caracteres"),
});

type PautaFormData = z.infer<typeof schemaPautaFormData>;

export { schemaPautaFormData };
export type { PautaFormData };
