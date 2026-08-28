import { Button } from "@mui/material";

type CustomButtonProps = React.ComponentProps<typeof Button> & {
  fullWidth?: boolean;
  variant?: "contained" | "outlined" | "text";
  color?: "primary" | "secondary" | "success" | "error" | "info" | "warning";
  size?: "small" | "medium" | "large";
};

export default function CustomButton(props: CustomButtonProps) {
  return <Button {...props} size={props.size || "small"} />;
}
