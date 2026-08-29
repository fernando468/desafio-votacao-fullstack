import {
  Box,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
} from "@mui/material";
import CustomButton from "../custom-button/CustomButton";

type ModalProps = {
  title: string;
  content: React.ReactNode;
  openDialog: boolean;
  textConfirm: string;
  textCancel: string;
  actionButtonConfirm?: () => void;
  actionButtonCancel: () => void;
  colorButton?: "primary" | "secondary" | "error" | "info" | "success";
  formId?: string;
  isLoading?: boolean;
};

export default function Modal({
  title,
  content,
  openDialog,
  textConfirm,
  textCancel,
  actionButtonConfirm,
  actionButtonCancel,
  colorButton,
  formId,
  isLoading = false,
}: ModalProps) {
  const isFormSubmit = Boolean(formId);

  return (
    <Box>
      <Dialog
        open={openDialog}
        onClose={actionButtonCancel}
        maxWidth="sm"
        fullWidth
      >
        <DialogTitle>{title}</DialogTitle>
        <DialogContent>
          {content && typeof content === "string" ? (
            <DialogContentText id="alert-dialog-description">
              {content}
            </DialogContentText>
          ) : (
            <Box sx={{ paddingTop: 1 }}>{content}</Box>
          )}
        </DialogContent>
        <DialogActions>
          <CustomButton
            color={colorButton}
            type={isFormSubmit ? "submit" : "button"}
            form={formId}
            variant="contained"
            loading={isLoading}
            onClick={isFormSubmit ? undefined : actionButtonConfirm}
          >
            {textConfirm}
          </CustomButton>
          <CustomButton
            onClick={actionButtonCancel}
            variant="outlined"
            loading={isLoading}
          >
            {textCancel}
          </CustomButton>
        </DialogActions>
      </Dialog>
    </Box>
  );
}
