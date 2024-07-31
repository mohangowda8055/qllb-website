import { FaIndianRupeeSign } from "react-icons/fa6";
import TotalSaving from "./TotalSaving";
import { useNavigate } from "react-router-dom";
import { useUpdateCart } from "../../reactquery/cart/cartCustomHooks";
import { useSelector } from "react-redux";

const PriceCard = ({
  basePrice,
  discount,
  totalPrice,
  shippingCharge,
  grandTotal,
  totalRebate,
}) => {
  const navigate = useNavigate();
  const { updateCart, isPending } = useUpdateCart();
  const cart = useSelector((state) => state.cartState);
  const data = {
    cartId: cart.cartId,
    total: cart.cartTotal,
    deliveryCost: cart.shipping,
    cartItems: cart.cartItems,
  };
  const user = useSelector((state) => state.userState.user);

  const handleGoBack = () => {
    let isModified = false;
    cart.storedCartItems.map((storedItem) =>{
    const item = cart.cartItems.find( (item) => item.product.productId === storedItem.product.productId );
    if(item.quantity !== storedItem.quantity || item.isRebate !== storedItem.isRebate ){
    isModified = true;
    }
    })
    if(isModified){
      updateCart({ data, user });
    }
    navigate(-1);
  }
  return (
    <div className="p-0 md:p-2 flex flex-col gap-3 ">
      <div className="bg-yellow-100 p-3 border-b md:border border-gray-300 flex flex-col gap-2">
        <div className="flex justify-between items-center">
          <div className="flex flex-col">
            <span className="font-semibold tracking-wide">Base Price</span>
            <span className="text-xs">(inclusive of GST)</span>
          </div>
          <div className="font-semibold">
            <span className="flex justify-center items-center">
              <FaIndianRupeeSign size={13} className="inline" />
              <span>{basePrice.toFixed(2)}</span>
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
              <span>{totalPrice.toFixed(2)}</span>
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
              <span>{shippingCharge}</span>
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
      <TotalSaving discount={discount} rebate={totalRebate} />
      <div className="flex justify-center items-center gap-2">
        <div className="py-1 md:py-2">
          <button
            className="bg-white text-black border border-gray-300 text-sm font-medium py-1 px-2 shadow-sm uppercase transition duration-200 ease-in-out transform hover:bg-secondary_dark hover:scale-105 active:bg-secondary_light active:scale-95"
            onClick={() => handleGoBack()}
          >
            Go Back
          </button>
        </div>
        <div className="py-1 md:py-2">
          <button
            disabled={isPending}
            className="bg-white text-black border border-gray-300 text-sm font-medium py-1 px-2 shadow-sm uppercase transition duration-200 ease-in-out transform hover:bg-secondary_dark hover:scale-105 active:bg-secondary_light active:scale-95"
            onClick={() => updateCart({ data, user })}
          >
            {isPending ? "Submitting" : "Save for Later"}
          </button>
        </div>
        <div className="py-1 md:py-2">
          <button
            type="button"
            className="bg-secondary text-sm md:text-base text-black border font-medium py-1 px-2 shadow-lg uppercase transition duration-200 ease-in-out transform hover:bg-secondary_dark hover:scale-105 active:bg-secondary_light active:scale-95"
            onClick={() => {
              updateCart({ data, user });
              navigate("/checkout");
            }}
          >
            Proceed To Order
          </button>
        </div>
      </div>
    </div>
  );
};
export default PriceCard;
