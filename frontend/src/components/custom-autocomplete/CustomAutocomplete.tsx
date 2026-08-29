import { Autocomplete, TextField } from "@mui/material";
import { Controller } from "react-hook-form";

type FormAutocompleteProps = {
  id: string;
  name: string;
  label: string;
  options: any[];
  control: any;
  getOptionLabel?: (option: any) => string;
  fullWidth?: boolean;
};

export default function CustomAutocomplete({
  id,
  name,
  label,
  options,
  control,
  getOptionLabel,
  fullWidth = true,
}: FormAutocompleteProps) {
  return (
    <Controller
      control={control}
      name={name}
      render={({ field, fieldState }) => (
        <Autocomplete
          {...field}
          value={
            field.value !== undefined && field.value !== null
              ? options.find((option) => option.id === field.value) || null
              : null
          }
          id={id}
          fullWidth={fullWidth}
          options={options}
          getOptionLabel={getOptionLabel}
          isOptionEqualToValue={(option, value) => option.id === value.id}
          onChange={(event, newValue) => {
            event.preventDefault();
            field.onChange(newValue ? newValue.id : null);
          }}
          renderInput={(params) => (
            <TextField
              {...params}
              label={label}
              error={fieldState.invalid}
              helperText={fieldState.error?.message}
              variant="outlined"
              size="small"
              fullWidth={fullWidth}
            />
          )}
          sx={{ width: fullWidth ? "100%" : 300 }}
        />
      )}
    />
  );
}
