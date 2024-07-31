import { useSelector } from "react-redux";
import PayButton from "./PayButton";
import PriceCard from "./PriceCard";
import SavedAddress from "./SavedAddress";

const OrderSummary = () => {
  const { shippingAddress } = useSelector((state) => state.orderState);
  return (
    <div className="flex flex-col sm:justify-center p-2 sm:p-4 sm:m-auto sm:flex-row gap-4">
      <SavedAddress
        text={"Shipping Address"}
        width={"3/5"}
        address={shippingAddress}
      />
      <div className=" flex flex-col sm:flex-row sm:w-2/5 gap-2 sm:gap-6">
        <PriceCard />
        <PayButton />
      </div>
    </div>
  );
};
export default OrderSummary;
