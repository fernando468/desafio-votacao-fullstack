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
  currency?: boolean;
  fullWidth?: boolean;
  required?: boolean;
  min?: number;
  max?: number;
  minLength?: number;
  maxLength?: number;
  endAdornment?: JSX.Element;
  startAdornment?: JSX.Element;
  size?: "small" | "medium";
};

export default function CustomFormTextField({
  name,
  label,
  type,
  placeholder,
  control,
  currency,
  min,
  max,
  minLength,
  maxLength,
  fullWidth = true,
  required = false,
  endAdornment,
  startAdornment,
  size = "small",
}: FormTextFieldProps) {
  return (
    <Controller
      control={control}
      name={name}
      render={({ field: { onChange, value, ...fieldProps }, fieldState }) => {
        const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
          const rawValue = event.target.value;

          if (currency) {
            // 1. Remove tudo que não for dígito
            const digitsOnly = rawValue.replace(/\D/g, "");

            // 2. Se tiver vazio, define como 0, se não, converte para número
            // Salvamos como centavos ou float. Aqui vou salvar como float (ex: 1200.00)
            const numericValue = digitsOnly ? parseFloat(digitsOnly) / 100 : 0;

            // 3. Atualiza o React Hook Form com o NÚMERO limpo
            onChange(numericValue);
          } else {
            onChange(rawValue);
          }
        };

        // Formatação visual para o usuário
        const displayedValue =
          currency && value !== undefined && value !== null
            ? new Intl.NumberFormat("pt-BR", {
                minimumFractionDigits: 2,
                maximumFractionDigits: 2,
              }).format(Number(value))
            : (value ?? "");

        return (
          <TextField
            {...fieldProps}
            value={displayedValue}
            onChange={handleChange}
            type={currency ? "text" : type}
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
                inputMode: currency ? "numeric" : "text",
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
