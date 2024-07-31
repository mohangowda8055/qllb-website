import { useSelector } from "react-redux";
import { useAddress } from "../../reactquery/order/orderQueryCustomHooks";
import AddressForm from "./AddressForm";
import SavedAddress from "./SavedAddress";

const Address = ({ nextStep }) => {
  const { user } = useSelector((state) => state.userState);
  const { pincode } = useSelector((state) => state.cartState);
  const { data } = useAddress(user);
  return (
    <div className="flex flex-col p-2 sm:px-4 sm:py-8 sm:flex-row gap-4">
      {data === null 
        ? "No Address Saved"
        : data?.map(
            (address) =>
              address.addressType == "DELIVERY" && address.postalCode == pincode && (
                <SavedAddress
                  text={"Saved Address"}
                  width={"1/4"}
                  address={address}
                  nextStep={nextStep}
                  key={address.addressType}
                />
              )
          )}

      <div className=" sm:hidden flex justify-center items-center pt-6">
        <div className="border-b-2 border-black border-solid w-40 content-none"></div>
        <span className="px-2">Or</span>
        <div className="border-b-2 border-black border-solid w-40 content-none"></div>
      </div>
      <AddressForm nextStep={nextStep} />
    </div>
  );
};
export default Address;
