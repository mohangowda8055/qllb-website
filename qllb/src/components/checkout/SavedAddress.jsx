import { useDispatch } from "react-redux";
import { setShippingAddress } from "../../features/order/orderSlice";

const SavedAddress = ({ text, width, address, nextStep }) => {
  const dispatch = useDispatch();
  const { addressLine1, addressLine2, city, state, postalCode, phoneNumber } =
    address;
  return (
    <div className={`sm:w-${width}`}>
      <div>
        <div className="py-1">
          <h1 className=" text-lg font-semibold">{text}</h1>
        </div>
        <div>{addressLine1}</div>
        <div>{addressLine2}</div>
        <div>
          {city}, {state} - {postalCode}
        </div>
        <div className="pt-1">{phoneNumber}</div>
        <div className={`pt-4 ${text === "Shipping Address" && "hidden"}`}>
          <button
            type="button"
            className=" bg-secondary text-black font-medium py-1 px-2 shadow-lg uppercase transition duration-200 ease-in-out transform hover:bg-secondary_dark hover:scale-105 active:bg-secondary_light active:scale-95"
            onClick={() => {
              dispatch(setShippingAddress(address));
              nextStep();
            }}
          >
            Deliver Here
          </button>
        </div>
      </div>
    </div>
  );
};
export default SavedAddress;
