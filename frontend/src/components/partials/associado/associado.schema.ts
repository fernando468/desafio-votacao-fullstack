import { z } from "zod";

const schemaAssociadoFormData = z.object({
  cpf: z
    .string()
    .min(11, "CPF tem que ter 11 dígitos")
    .max(11, "CPF tem que ter 11 dígitos"),
});

type AssociadoFormData = z.infer<typeof schemaAssociadoFormData>;

export { schemaAssociadoFormData };
export type { AssociadoFormData };
