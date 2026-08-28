import { Grid } from "@mui/material";
import { Form, type Control, type FormSubmitHandler } from "react-hook-form";
import type { SessaoRequest } from "../../../../types/sessao.type";
import CustomFormTextField from "../../../custom-text-field/CustomTextField";

type SessaoFormProps = {
  onSubmit?: FormSubmitHandler<SessaoRequest>;
  control: Control<SessaoRequest, any, SessaoRequest>;
};

export default function SessaoForm({ control, onSubmit }: SessaoFormProps) {
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
              <CustomFormTextField
                name="nome"
                label="Nome da Sessão"
                type="text"
                placeholder="Nome da Sessão"
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
          </Grid>
        </Form>
      </Grid>
    </Grid>
  );
}
