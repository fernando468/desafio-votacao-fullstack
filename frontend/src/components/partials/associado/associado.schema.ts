import { z } from "zod";

const schemaAssociadoFormData = z.object({
  cpf: z.coerce.string().regex(/^\d{11}$/, "CPF tem que ter 11 dígitos"),
});

type AssociadoFormData = z.infer<typeof schemaAssociadoFormData>;
type AssociadoFormDataInput = z.input<typeof schemaAssociadoFormData>;
type AssociadoFormDataOutput = z.output<typeof schemaAssociadoFormData>;

export { schemaAssociadoFormData };
export type {
  AssociadoFormData,
  AssociadoFormDataInput,
  AssociadoFormDataOutput,
};
