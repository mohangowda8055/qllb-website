import { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { setShippingAddress } from "../../features/order/orderSlice";
import { useCreateAddress } from "../../reactquery/order/orderQueryCustomHooks";
import { toast } from "react-toastify";
import { useNavigate } from "react-router-dom";

const AddressForm = ({ nextStep }) => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { user } = useSelector((state) => state.userState);
  const { pincode:cartPincode } = useSelector((state) => state.cartState);
  const { createAddress, isError, isPending } = useCreateAddress();
  const [focusedValues, setFocusedValues] = useState({
    phoneNumber: false,
    postalCode: false,
    state: false,
    city: false,
    addressLine1: false,
    addressLine2: false,
  });
  const [values, setValues] = useState({
    phoneNumber: "",
    postalCode: cartPincode,
    state: "Karnataka",
    city: "Bengaluru",
    addressLine1: "",
    addressLine2: "",
  });

  const handleSubmit = (e) => {
    e.preventDefault();
    createAddress(
      {
        address: {
          addressLine1: values.addressLine1,
          addressLine2: values.addressLine2,
          city: values.city,
          state: values.state,
          postalCode: values.postalCode,
          phoneNumber: values.phoneNumber,
          addressType: "DELIVERY",
        },
        user,
      },
      {
        onSuccess: () => {
          dispatch(setShippingAddress(values));
          nextStep();
        },
      }
    );
  };

  isError && toast.error("Something went wrong try again!!!");

  const handleFocus = (e) => {
    setFocusedValues({ ...focusedValues, [e.target.name]: true });
  };

  const handleChange = (e) => {
    setValues({ ...values, [e.target.name]: e.target.value });
  };

  return (
    <div className=" flex flex-col gap-2 sm:w-full">
      <div>
        <h1 className=" pb-2 text-lg font-semibold">Add Delivery Address</h1>
      </div>
      <form onSubmit={handleSubmit} className=" flex flex-col gap-2">
        <div className="sm:flex sm:gap-2">
          <div className="sm:w-1/2">
            <label htmlFor="">Phone Number</label>
            <input
              type="number"
              name="phoneNumber"
              value={values.phoneNumber}
              onBlur={handleFocus}
              onChange={handleChange}
              focused={focusedValues.phoneNumber.toString()}
              placeholder="Phone number (Required)*"
              pattern="^[0-9]{10}$"
              required
              className="w-full p-2 border border-gray-400 rounded-lg"
            />
            <span className="validation">Phone number should be 10 digits</span>
          </div>
          <div className="sm:w-1/2">
            <label htmlFor="">Pincode</label>
            <input
              type="number"
              name="postalCode"
              value={values.postalCode}
              onBlur={handleFocus}
              onChange={handleChange}
              focused={focusedValues.postalCode.toString()}
              placeholder="Pincode (Required)*"
              pattern="^[0-9]{6}$"
              required
              disabled
              className="w-full p-2 border border-gray-400 rounded-lg"
            />
            <span className="validation">
              Pincode number should be 6 digits
            </span>
          </div>
        </div>
        <div>
          <label htmlFor="">State</label>
          <input
            type="text"
            name="state"
            value={values.state}
            onBlur={handleFocus}
            onChange={handleChange}
            focused={focusedValues.state.toString()}
            placeholder="State (Required)*"
            required
            disabled
            className="w-full p-2 border border-gray-400 rounded-lg"
          />
          <span className="validation">State is required</span>
        </div>
        <div>
          <label htmlFor="">City</label>
          <input
            type="text"
            name="city"
            value={values.city}
            onBlur={handleFocus}
            onChange={handleChange}
            focused={focusedValues.city.toString()}
            placeholder="City (Required)*"
            required
            disabled
            className="w-full p-2 border border-gray-400 rounded-lg"
          />
          <span className="validation">City is required</span>
        </div>
        <div>
          <label htmlFor="">Address Line 1</label>
          <input
            type="text"
            name="addressLine1"
            value={values.addressLine1}
            onBlur={handleFocus}
            onChange={handleChange}
            focused={focusedValues.addressLine1.toString()}
            placeholder="House No., Road Name (Required)*"
            required
            className="w-full p-2 border border-gray-400 rounded-lg"
          />
          <span className="validation">Addres Line 1 is required</span>
        </div>
        <div>
          <label htmlFor="">Address Line 2</label>
          <input
            type="text"
            name="addressLine2"
            value={values.addressLine2}
            onBlur={handleFocus}
            onChange={handleChange}
            focused={focusedValues.addressLine2.toString()}
            placeholder="Area, Landmark (Required)*"
            required
            className="w-full p-2 border border-gray-400 rounded-lg"
          />
          <span className="validation">Address Line 2 is required</span>
        </div>
        <div className="flex pt-4 gap-4">
          <button
            type="submit"
            disabled={isPending}
            className=" bg-secondary text-black font-medium py-1 px-2 shadow-lg uppercase transition duration-200 ease-in-out transform hover:bg-secondary_dark hover:scale-105 active:bg-secondary_light active:scale-95"
          >
            {isPending ? "Submitting" : "Deliver Here"}
          </button>
        </div>
      </form>
    </div>
  );
};
export default AddressForm;
