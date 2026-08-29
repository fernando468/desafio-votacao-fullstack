import { Grid } from "@mui/material";
import { Form, type Control, type FormSubmitHandler } from "react-hook-form";
import type { AssociadoRequest } from "../../../../types/associado.type";
import CustomFormTextField from "../../../custom-text-field/CustomTextField";
import type {
  AssociadoFormDataInput,
  AssociadoFormDataOutput,
} from "../associado.schema";

type AssociadoFormProps = {
  onSubmit?: FormSubmitHandler<AssociadoRequest>;
  control: Control<AssociadoFormDataInput, any, AssociadoFormDataOutput>;
};

export default function AssociadoForm({
  control,
  onSubmit,
}: AssociadoFormProps) {
  return (
    <Grid container spacing={2}>
      <Grid size={{ xs: 12 }}>
        <Form control={control} id="form-criar-associado" onSubmit={onSubmit}>
          <Grid
            container
            spacing={2}
            sx={{ display: "flex", flexDirection: "column" }}
          >
            <Grid>
              <CustomFormTextField
                name="cpf"
                label="CPF"
                type="numeric"
                placeholder="CPF"
                maxLength={11}
                minLength={11}
                control={control}
                required
              />
            </Grid>
          </Grid>
        </Form>
      </Grid>
    </Grid>
  );
}
