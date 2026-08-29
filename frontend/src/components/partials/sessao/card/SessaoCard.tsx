import { zodResolver } from "@hookform/resolvers/zod";
import {
  Box,
  Card,
  CardActions,
  CardContent,
  Chip,
  Grid,
  Typography,
} from "@mui/material";
import { useState } from "react";
import { useForm } from "react-hook-form";
import { toast } from "sonner";
import VotoService from "../../../../services/voto.service";
import type { AssociadoResponse } from "../../../../types/associado.type";
import type { SessaoResponse } from "../../../../types/sessao.type";
import { TipoVoto } from "../../../../types/tipoVoto.type";
import type { VotoRequest } from "../../../../types/voto.type";
import { formatCpf } from "../../../../utils/cpf.utils";
import { formatDate } from "../../../../utils/date.utils";
import CustomAutocomplete from "../../../custom-autocomplete/CustomAutocomplete";
import CustomButton from "../../../custom-button/CustomButton";
import Modal from "../../../modal/Modal";
import { schemaVotoFormData } from "../sessao.schema";

type SessaoCardProps = {
  sessoes: SessaoResponse[];
  associados: AssociadoResponse[];
};

const opcoesVoto = [{ id: TipoVoto.SIM }, { id: TipoVoto.NAO }];

export default function SessaoCard({ sessoes, associados }: SessaoCardProps) {
  const [carregando, setCarregando] = useState(false);
  const [abrirModal, setAbrirModal] = useState(false);
  const votoService = new VotoService();
  const { control, setValue, reset, handleSubmit } = useForm<VotoRequest>({
    defaultValues: {
      associadoId: 0,
      sessaoId: 0,
      tipoVoto: TipoVoto.SIM,
    },
    resolver: zodResolver(schemaVotoFormData),
  });

  const abrirModalVoto = (sessao: SessaoResponse, tipoVoto: TipoVoto) => {
    setValue("sessaoId", sessao.id);
    setValue("tipoVoto", tipoVoto);

    setAbrirModal(true);
  };

  const fecharModal = () => {
    setAbrirModal(false);
    reset();
  };

  const realizarVoto = async (votoRequest: VotoRequest) => {
    setCarregando(true);

    await votoService
      .post(votoRequest)
      .then(() => {
        toast.success("Voto realizado com sucesso!");
        fecharModal();
      })
      .catch((error) => {
        toast.error(error.response.data.message);
      })
      .finally(() => {
        setCarregando(false);
      });
  };

  return (
    <Grid
      container
      spacing={2}
      sx={{ display: "flex", flexDirection: "row", flwWrap: "wrap" }}
    >
      {sessoes?.map((sessao) => (
        <Grid key={sessao.id}>
          <Card
            key={sessao.id}
            sx={{
              height: "180px",
              width: "200px",
              display: "flex",
              flexDirection: "column",
              justifyContent: "space-between",
            }}
          >
            <CardContent>
              <Typography variant="h5" component="div">
                {sessao.pauta.titulo}
              </Typography>
              <Box>
                <Chip
                  label={
                    sessao.isAberta
                      ? "Aberta para Votação"
                      : "Fechada para Votação"
                  }
                  size="small"
                  color={sessao.isAberta ? "success" : "error"}
                />
              </Box>
              <Typography variant="body2" color="text.secondary">
                {formatDate(sessao.dataInicio)} - {formatDate(sessao.dataFim)}
              </Typography>
            </CardContent>
            <CardActions>
              <CustomButton
                variant="contained"
                disabled={!sessao.isAberta}
                color="primary"
                onClick={() => abrirModalVoto(sessao, TipoVoto.SIM)}
                loading={carregando}
              >
                SIM
              </CustomButton>
              <CustomButton
                variant="contained"
                disabled={!sessao.isAberta}
                color="error"
                onClick={() => abrirModalVoto(sessao, TipoVoto.NAO)}
                loading={carregando}
              >
                NAO
              </CustomButton>
            </CardActions>
          </Card>
        </Grid>
      ))}
      <Modal
        title="Votar"
        content={
          <Grid container spacing={2}>
            <Grid size={{ xs: 12 }}>
              <CustomAutocomplete
                id="id-associado"
                name="associadoId"
                label="Associado"
                options={associados}
                control={control}
                getOptionLabel={(option) => formatCpf(option.cpf)}
              />
            </Grid>

            <Grid size={{ xs: 12 }}>
              <CustomAutocomplete
                id="tipo-voto"
                name="tipoVoto"
                label="Voto"
                options={opcoesVoto}
                control={control}
                getOptionLabel={(option) => option.id}
              />
            </Grid>
          </Grid>
        }
        openDialog={abrirModal}
        textConfirm="Confirmar"
        textCancel="Cancelar"
        actionButtonConfirm={handleSubmit(realizarVoto)}
        actionButtonCancel={fecharModal}
        colorButton="primary"
      />
    </Grid>
  );
}
