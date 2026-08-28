import { Card, CardContent, Grid, Typography } from "@mui/material";
import type { PautaResponse } from "../../../../types/pauta.type";

type PautaCardProps = {
  pautas: PautaResponse[];
};

export default function PautaCard({ pautas }: PautaCardProps) {
  return (
    <Grid
      container
      spacing={2}
      sx={{ display: "flex", flexDirection: "row", flwWrap: "wrap" }}
    >
      {pautas?.map((pauta) => (
        <Grid key={pauta.id}>
          <Card key={pauta.id} sx={{ height: "200px", width: "200px" }}>
            <CardContent>
              <Typography variant="h5" component="div">
                {pauta.titulo}
              </Typography>
              <Typography variant="body2" color="text.secondary">
                {pauta.descricao}
              </Typography>
            </CardContent>
          </Card>
        </Grid>
      ))}
    </Grid>
  );
}
