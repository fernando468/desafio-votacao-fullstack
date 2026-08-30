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
      {data?.map((data, index) => (
        <Grid key={`${data.id}-${index}`} size={custoStyleCard.size}>
          <Card sx={{ ...custoStyleCard.card, overflow: "auto" }}>
            <CardContent>
              <Typography
                variant="h5"
                sx={{
                  overflowWrap: "anywhere",
                }}
              >
                {data.titulo}
              </Typography>
              <Typography
                variant="body2"
                sx={{
                  overflowWrap: "anywhere",
                }}
              >
                {data.descricao}
              </Typography>
            </CardContent>
          </Card>
        </Grid>
      ))}
    </Grid>
  );
}
