import { Grid } from "@mui/material";
import { Form, type Control, type FormSubmitHandler } from "react-hook-form";
import type { PautaRequest } from "../../../../types/pauta.type";
import CustomFormTextField from "../../../custom-text-field/CustomTextField";

type PautaFormProps = {
  onSubmit?: FormSubmitHandler<PautaRequest>;
  control: Control<PautaRequest>;
};

export default function PautaForm({ control, onSubmit }: PautaFormProps) {
  return (
    <Grid container spacing={2}>
      <Grid size={{ xs: 12 }}>
        <Form control={control} id="form-criar-pauta" onSubmit={onSubmit}>
          <Grid
            container
            spacing={2}
            sx={{ display: "flex", flexDirection: "column" }}
          >
            <Grid>
              <CustomFormTextField
                name="titulo"
                label="Título"
                type="text"
                minLength={3}
                maxLength={30}
                placeholder="Título"
                control={control}
                required
              />
            </Grid>
            <Grid>
              <CustomFormTextField
                name="descricao"
                label="Descrição"
                type="text"
                minLength={3}
                maxLength={150}
                placeholder="Descrição"
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
