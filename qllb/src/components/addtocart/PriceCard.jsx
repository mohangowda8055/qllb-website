import { FaIndianRupeeSign } from "react-icons/fa6";
import TotalSaving from "./TotalSaving";

const PriceCard = ({ product }) => {
  const discount = product.mrp * (product.discountPercentage / 100);
  const price = product.mrp - discount;
  return (
    <div className="p-2 flex flex-col gap-3 ">
      <div className="bg-yellow-100 p-3 border border-gray-300 flex flex-col gap-2">
        <div className="flex justify-between items-center">
          <div className="flex flex-col">
            <span className="font-semibold tracking-wide">Base Price</span>
            <span className="text-xs">(inclusive of GST)</span>
          </div>
          <div className="font-semibold">
            <span className="flex justify-center items-center">
              <FaIndianRupeeSign size={13} className="inline" />
              <span>{product.mrp.toFixed(2)}</span>
            </span>
          </div>
        </div>
        <div className="flex justify-between font-semibold">
          <div>
            <span>Special Discount</span>
          </div>
          <div>
            <span className="flex justify-center items-center">
             - <FaIndianRupeeSign size={13} className="inline" />
              <span>{discount.toFixed(2)}</span>
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
              <span>{price.toFixed(2)}</span>
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
              <span>0</span>
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
              <span>{price.toFixed(2)}</span>
            </span>
          </div>
        </div>
        {
          product.productType !== "INVERTER" && (
<div className=" bg-white border border-green-500 border-dashed">
          <div className="p-2 text-sm">
            <p>
              Note: Additionally, rebate{" "}
              <span className="font-semibold pr-1">
                upto{" "}
                <span>
                  <FaIndianRupeeSign size={13} className="inline" />
                  <span>{product.rebate.toFixed(2)}</span>
                </span>
              </span>
              per unit on return of similar old battery.
            </p>
          </div>
        </div>
          )
        }
      </div>
      <TotalSaving discount={discount} rebate={product.rebate} productType={product.productType} />
    </div>
  );
};
export default PriceCard;
