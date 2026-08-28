import type { JSX } from "@emotion/react/jsx-runtime";
import { TextField } from "@mui/material";
import { Controller } from "react-hook-form";

type Type = "text" | "password" | "email" | "number" | "date";

type FormTextFieldProps = {
  name: string;
  label: string;
  type: Type;
  placeholder: string;
  control: any;
  fullWidth?: boolean;
  required?: boolean;
  min?: number;
  max?: number;
  minLength?: number;
  maxLength?: number;
  endAdornment?: JSX.Element;
  startAdornment?: JSX.Element;
  size?: "small" | "medium";
  number?: boolean;
};

export default function CustomFormTextField({
  name,
  label,
  type,
  placeholder,
  control,
  min,
  max,
  minLength,
  maxLength,
  fullWidth = true,
  required = false,
  endAdornment,
  startAdornment,
  size = "small",
  number,
}: FormTextFieldProps) {
  return (
    <Controller
      control={control}
      name={name}
      render={({ field: { onChange, value, ...fieldProps }, fieldState }) => {
        const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
          const rawValue = event.target.value;
          const valueWithoutNumbers =
            type === "number" || number
              ? rawValue.replace(/\D/g, "")
              : type === "date"
                ? rawValue
                : rawValue.replace(/\d/g, "");

          onChange(
            maxLength
              ? valueWithoutNumbers.slice(0, maxLength)
              : valueWithoutNumbers,
          );
        };

        return (
          <TextField
            {...fieldProps}
            value={value ?? ""}
            onChange={handleChange}
            type={type}
            placeholder={placeholder}
            className="input"
            aria-label={label}
            aria-invalid={fieldState.invalid}
            error={fieldState.invalid}
            helperText={fieldState.error?.message}
            fullWidth={fullWidth}
            required={required}
            size={size}
            slotProps={{
              htmlInput: {
                min: min,
                max: max,
                minLength: minLength,
                maxLength: maxLength,
                inputMode: type === "number" || number ? "numeric" : undefined,
              },
              input: {
                endAdornment: endAdornment,
                startAdornment: startAdornment,
              },
            }}
          />
        );
      }}
    />
  );
}
