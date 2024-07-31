import { useState } from "react";

const FormInput = (props) => {
  const [focused, setFocused] = useState(false);
  const { label, errorMessage, onChange, id, ...inputProps } = props;

  const handleFocus = () => {
    setFocused(true);
  };

  return (
    <div className="flex flex-col gap-1">
      <label htmlFor="" className="px-2 text-gray-400">
        {label}
      </label>
      <input
        {...inputProps}
        onChange={onChange}
        onBlur={handleFocus}
        onFocus={() =>
          inputProps.name === "confirmPassword" && setFocused(true)
        }
        focused={focused.toString()}
        className="w-full p-2 border border-gray-400 rounded-lg"
      />
      <span className="validation">{errorMessage}</span>
    </div>
  );
};
export default FormInput;
