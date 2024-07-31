import { BiDownArrowAlt } from "react-icons/bi";
import CartQuantity from "./CartQuantity";
import OldBattery from "./OldBattery";
import { AiOutlinePercentage } from "react-icons/ai";
import { FaIndianRupeeSign } from "react-icons/fa6";
import RemoveCartItemButton from "./RemoveCartItemButton";

const CartItem = ({ cartItem }) => {
  const { quantity, isRebate, product } = cartItem;
  const discount = (product.mrp * (product.discountPercentage / 100)).toFixed(
    4
  );
  const price = (
    product.mrp -
    discount -
    (isRebate ? product.rebate : 0)
  ).toFixed(2);
  return (
    <div className="p-0 md:p-2">
      <div className="bg-white border-b md:border border-gray-300">
        <div className="p-2">
          <div className="flex items-center gap-2">
            <div>
              <img
                src={product.imageMainUrl}
                alt="image"
                className="max-w-20 h-auto"
              />
            </div>
            <div className="text-sm font-semibold">
              <div className="tracking-wide">
                {cartItem.product.productName}
              </div>
              <div className="flex items-center py-2">
                <BiDownArrowAlt size={18} className="inline text-green-600" />
                <span className="text-green-600">
                  {product.discountPercentage}
                </span>
                <AiOutlinePercentage className="inline text-green-600" />
                <FaIndianRupeeSign className=" inline pl-1" />
                <span>{price}</span>
                <span className=" text-gray-500 text-sm pl-1 line-through">
                  {product.mrp}
                </span>
              </div>
            </div>
          </div>
          <div className="flex items-center gap-2">
            <CartQuantity quantity={quantity} product={product} />
            {
              product.productType !== "INVERTER" && (
              <div className=" bg-white border border-green-500 border-dashed">
              <div className=" text-xs p-1 sm:text-sm">
                <p>
                  Note: Additionally, rebate{" "}
                  <span className="font-semibold pr-1">
                    upto{" "}
                    <span>
                      <FaIndianRupeeSign size={13} className="inline" />
                      <span>{product.rebate}</span>
                    </span>
                  </span>
                  per unit on return of similar old battery.
                </p>
              </div>
            </div>
              )
            }
          </div>
          {product.productType !== "INVERTER" && <OldBattery isRebate={isRebate} productId={product.productId} /> }  
          <RemoveCartItemButton cartItem={cartItem} />
        </div>
      </div>
    </div>
  );
};
export default CartItem;
