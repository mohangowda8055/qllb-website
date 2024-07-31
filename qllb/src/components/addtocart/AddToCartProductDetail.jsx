import { FaIndianRupeeSign } from "react-icons/fa6";
import Quantity from "./Quantity";
import PinCode from "./PinCode";
import OldBattery from "./OldBattery";
import AddToCartButton from "./AddToCartButton";
import Services from "./Services";

const AddToCartProductDetail = ({
  product,
  quantity,
  handleIncreaseQty,
  handleDecreaseQty,
  handleAddToCart,
  isRebate,
  setIsRebate,
}) => {
  const discount = product.mrp * (product.discountPercentage / 100);
  const price = product.mrp - discount;
  return (
    <div className="p-3 sm:shadow-lg">
      <div>
        <div>
          <h1 className="capitalize text-lg font-semibold">
            {product.productName}
          </h1>
        </div>
        <div className="flex justify-start items-center pt-2">
          <div className="flex justify-center items-center">
            <FaIndianRupeeSign size={15} className="text-green-600 inline" />
            <span className="text-green-600 font-semibold">
              {price.toFixed(2)}{" "}
            </span>
          </div>
          <div className="flex justify-center items-center text-sm text-gray-400 pl-1">
            <FaIndianRupeeSign size={12} className="inline" />
            <span className="line-through">{product.mrp.toFixed(2)}</span>
            <span>(Per unit)</span>
          </div>
        </div>{
          product.productType !== "INVERTER" && (
          <div className="flex pt-2 flex-wrap">
          <span>
            Additional, rebate <span className="font-bold">upto</span>
          </span>
          <span className="flex justify-center items-center">
            <FaIndianRupeeSign size={13} className="inline" />
            <span className="font-bold">
              {product.rebate.toFixed(2)} per unit
            </span>
          </span>
          <span> on return of similar old battery.</span>
        </div>
        )
        }
      </div>
      <div className="hidden sm:flex flex-col gap-4">
        <Quantity
          quantity={quantity}
          handleIncreaseQty={handleIncreaseQty}
          handleDecreaseQty={handleDecreaseQty}
          product={product}
        />
        <PinCode />
        <OldBattery isRebate={isRebate} setIsRebate={setIsRebate} />
        <AddToCartButton product={product} handleAddToCart={handleAddToCart} />
        <Services totalWarranty={product.warranty + product.guarantee} />
      </div>
    </div>
  );
};
export default AddToCartProductDetail;
