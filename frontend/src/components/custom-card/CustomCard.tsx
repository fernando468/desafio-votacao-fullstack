import { Card, CardContent, Grid, Typography } from "@mui/material";
import custoStyleCard from "./customStyleCard";

export type TipoCard = {
  id: number;
  titulo: string;
  descricao?: string;
};

type CustomCardProps = {
  data: TipoCard[];
};

export default function CustomCard({ data }: CustomCardProps) {
  return (
    <Grid container spacing={2}>
      {data?.map((data) => (
        <Grid key={data.id} size={custoStyleCard.size}>
          <Card sx={custoStyleCard.card}>
            <CardContent>
              <Typography variant="h5" component="div">
                {data.titulo}
              </Typography>

              <Typography variant="body2" color="text.secondary">
                {data.descricao}
              </Typography>
            </CardContent>
          </Card>
        </Grid>
      ))}
    </Grid>
  );
}
