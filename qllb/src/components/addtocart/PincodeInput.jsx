import { useState } from "react";

const PincodeInput = (props) => {
  const [focused, setFocused] = useState(false);
  const {
    errorMessage,
    onChange,
    isPinAvailable,
    isPinChecked,
    ...inputProps
  } = props;
  const handleFocus = (e) => {
    setFocused(true);
  };
  return (
    <div>
      <input
        {...inputProps}
        onChange={onChange}
        placeholder="560070"
        onBlur={handleFocus}
        focused={focused.toString()}
        className="w-36 p-2 border border-gray-400 rounded-lg"
      />
      {!isPinAvailable ? (
        <span className=" text-red-500 pl-1">Not Available</span>
      ) : (
        isPinChecked && <span className=" text-green-500 pl-1">Available</span>
      )}
      <span className="validation">{errorMessage}</span>
    </div>
  );
};
export default PincodeInput;
