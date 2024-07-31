import { useState } from "react";
import PincodeInput from "./PincodeInput";
import { useDispatch, useSelector } from "react-redux";
import { setPincode } from "../../features/product/productSlice";

const PinCode = () => {
  const dispatch = useDispatch();
  const { pincode } = useSelector((state) => state.productState);
  const [isPinAvailable, setIsPinAvailable] = useState(true);
  const [isPinChecked, setIsPinChecked] = useState(false);
  const inputs = {
    name: "pincode",
    type: "text",
    errorMessage: "Pincode is required",
    placeholder: "please enter pincode",
    required: true,
  };

  const handlePinCheck = () => {
    setIsPinChecked(true);
    const pincodes = [560059, 560070, 560057, 560060];
    const pin = pincodes.find((pin) => pin == pincode);
    pin ? setIsPinAvailable(true) : setIsPinAvailable(false);
  };

  return (
    <div className="flex items-center gap-2 p-3 pt-1 sm:p-0 flex-wrap text-sm">
      <div>
        <span>PIN CODE</span>
      </div>
      <PincodeInput
        {...inputs}
        value={pincode}
        onChange={(e) => dispatch(setPincode(e.target.value))}
        isPinAvailable={isPinAvailable}
        isPinChecked={isPinChecked}
      />
      <div>
        <button
          type="button"
          onClick={handlePinCheck}
          className="bg-secondary text-black  font-medium py-1 px-2 shadow-lg uppercase transition duration-200 ease-in-out transform hover:bg-secondary_dark hover:scale-105 active:bg-secondary_light active:scale-95"
        >
          Check
        </button>
      </div>
    </div>
  );
};
export default PinCode;
