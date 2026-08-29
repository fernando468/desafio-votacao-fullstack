import type { PautaResponse } from "../../../../types/pauta.type";
import CustomCard from "../../../custom-card/CustomCard";

type PautaCardProps = {
  pautas: PautaResponse[];
};

export default function PautaCard({ pautas }: PautaCardProps) {
  return (
    <CustomCard data={pautas} />
    // <Grid container spacing={2}>
    //   {pautas?.map((pauta) => (
    //     <Grid
    //       key={pauta.id}
    //       size={{
    //         xs: 12,
    //         sm: 6,
    //         md: 4,
    //         lg: 3,
    //       }}
    //     >
    //       <Card sx={{ height: 200, width: "100%" }}>
    //         <CardContent>
    //           <Typography variant="h5" component="div">
    //             {pauta.titulo}
    //           </Typography>

    //           <Typography variant="body2" color="text.secondary">
    //             {pauta.descricao}
    //           </Typography>
    //         </CardContent>
    //       </Card>
    //     </Grid>
    //   ))}
    // </Grid>
  );
}
