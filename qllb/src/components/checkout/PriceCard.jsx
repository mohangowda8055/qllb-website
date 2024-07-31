import { FaIndianRupeeSign } from "react-icons/fa6";
import { useSelector } from "react-redux";

const PriceCard = () => {
  const { totalMrp, totalDiscount, cartTotal, shipping, grandTotal } =
    useSelector((state) => state.cartState);

  return (
    <div>
      <div className="bg-yellow-100 p-3 border-b md:border border-gray-300 flex flex-col gap-2">
        <div className="flex justify-between items-center">
          <div className="flex flex-col">
            <span className="font-semibold tracking-wide">Base Price</span>
            <span className="text-xs">(inclusive of GST)</span>
          </div>
          <div className="font-semibold">
            <span className="flex justify-center items-center">
              <FaIndianRupeeSign size={13} className="inline" />
              <span>{totalMrp.toFixed(2)}</span>
            </span>
          </div>
        </div>
        <div className="flex justify-between font-semibold">
          <div>
            <span>Special Discount</span>
          </div>
          <div>
            <span className="flex justify-center items-center">
              <FaIndianRupeeSign size={13} className="inline" />
              <span>{totalDiscount.toFixed(2)}</span>
            </span>
          </div>
        </div>
        <div className="flex justify-between">
          <div className="flex flex-col">
            <span className="font-semibold">Total Price</span>
            <span className="text-xs">(Inclusive of GST)</span>
          </div>
          <div>
            <span className="flex justify-center items-center font-semibold">
              <FaIndianRupeeSign size={13} className="inline" />
              <span>{cartTotal.toFixed(2)}</span>
            </span>
          </div>
        </div>
        <div className="flex justify-between font-semibold">
          <div>
            <span>Shipping Charge</span>
          </div>
          <div>
            <span className="flex justify-center items-center">
              <FaIndianRupeeSign size={13} className="inline" />
              <span>{shipping}</span>
            </span>
          </div>
        </div>
        <div className="flex justify-between font-semibold text-green-600">
          <div>
            <span>Total Amount to be paid</span>
          </div>
          <div>
            <span className="flex justify-center items-center">
              <FaIndianRupeeSign size={13} className="inline" />
              <span>{grandTotal.toFixed(2)}</span>
            </span>
          </div>
        </div>
      </div>
    </div>
  );
};
export default PriceCard;
