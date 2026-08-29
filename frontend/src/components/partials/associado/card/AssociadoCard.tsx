import type { AssociadoResponse } from "../../../../types/associado.type";
import { formatCpf } from "../../../../utils/cpf.utils";
import CustomCard from "../../../custom-card/CustomCard";

type AssociadoCardProps = {
  associados: AssociadoResponse[];
};

export default function AssociadoCard({ associados }: AssociadoCardProps) {
  const associadosFormatados = associados.map((associado) => ({
    id: associado.id,
    titulo: formatCpf(associado.cpf),
  }));
  return (
    <CustomCard data={associadosFormatados} />

    // <Grid
    //   container
    //   spacing={2}
    //   sx={{ display: "flex", flexDirection: "row", flwWrap: "wrap" }}
    // >
    //   {associados?.map((associado) => (
    //     <Grid key={associado.id}>
    //       <Card
    //         key={associado.id}
    //         sx={{
    //           height: "180px",
    //           width: "200px",
    //           display: "flex",
    //           flexDirection: "column",
    //           justifyContent: "space-between",
    //         }}
    //       >
    //         <CardContent>
    //           <Typography variant="h6" component="div">
    //             {formatCpf(associado.cpf)}
    //           </Typography>
    //         </CardContent>
    //       </Card>
    //     </Grid>
    //   ))}
    // </Grid>
  );
}
