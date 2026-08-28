import { Card, CardContent, Grid, Typography } from "@mui/material";
import type { SessaoResponse } from "../../../../types/sessao.type";

type SessaoCardProps = {
  sessoes: SessaoResponse[];
};

export default function SessaoCard({ sessoes }: SessaoCardProps) {
  return (
    <Grid
      container
      spacing={2}
      sx={{ display: "flex", flexDirection: "row", flwWrap: "wrap" }}
    >
      {sessoes?.map((sessao) => (
        <Grid key={sessao.id}>
          <Card key={sessao.id} sx={{ height: "200px", width: "200px" }}>
            <CardContent>
              <Typography variant="h5" component="div">
                {sessao.pauta.titulo}
              </Typography>
              <Typography variant="body2" color="text.secondary">
                {sessao.dataCriacao}
              </Typography>
            </CardContent>
          </Card>
        </Grid>
      ))}
    </Grid>
  );
}
