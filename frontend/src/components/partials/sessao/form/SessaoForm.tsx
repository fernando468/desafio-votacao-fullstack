import { Grid } from "@mui/material";
import { Form, type Control, type FormSubmitHandler } from "react-hook-form";
import type { PautaResponse } from "../../../../types/pauta.type";
import type { SessaoRequest } from "../../../../types/sessao.type";
import CustomAutocomplete from "../../../custom-autocomplete/CustomAutocomplete";
import CustomFormTextField from "../../../custom-text-field/CustomTextField";

type SessaoFormProps = {
  pautas: PautaResponse[];
  onSubmit?: FormSubmitHandler<SessaoRequest>;
  control: Control<SessaoRequest>;
};

export default function SessaoForm({
  control,
  onSubmit,
  pautas,
}: SessaoFormProps) {
  return (
    <Grid container spacing={2}>
      <Grid>
        <Form control={control} id="form-criar-sessao" onSubmit={onSubmit}>
          <Grid
            container
            spacing={2}
            sx={{ display: "flex", flexDirection: "column" }}
          >
            <Grid>
              <CustomAutocomplete
                id="id-pauta"
                name="pautaId"
                label="Pauta"
                options={pautas}
                control={control}
                getOptionLabel={(option) => option.titulo}
              />
            </Grid>
            <Grid>
              <CustomFormTextField
                name="dataInicio"
                label="Data de Início"
                type="date"
                placeholder="Data de Início"
                control={control}
                required
              />
            </Grid>
            <Grid>
              <CustomFormTextField
                name="dataFim"
                label="Data de Término"
                type="date"
                placeholder="Data de Término"
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
