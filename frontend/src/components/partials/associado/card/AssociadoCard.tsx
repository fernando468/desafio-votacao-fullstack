import { Card, CardContent, Grid, Typography } from "@mui/material";
import type { AssociadoResponse } from "../../../../types/associado.type";
import { formatCpf } from "../../../../utils/cpf.utils";

type AssociadoCardProps = {
  associados: AssociadoResponse[];
};

export default function AssociadoCard({ associados }: AssociadoCardProps) {
  return (
    <Grid
      container
      spacing={2}
      sx={{ display: "flex", flexDirection: "row", flwWrap: "wrap" }}
    >
      {associados?.map((associado) => (
        <Grid key={associado.id}>
          <Card
            key={associado.id}
            sx={{
              height: "180px",
              width: "200px",
              display: "flex",
              flexDirection: "column",
              justifyContent: "space-between",
            }}
          >
            <CardContent>
              <Typography variant="h6" component="div">
                {formatCpf(associado.cpf)}
              </Typography>
            </CardContent>
          </Card>
        </Grid>
      ))}
    </Grid>
  );
}
