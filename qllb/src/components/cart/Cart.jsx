import { useSelector } from "react-redux";
import CartItem from "./CartItem";
import PriceCard from "./PriceCard";

const Cart = () => {
  const {
    cartItems,
    cartTotal,
    totalDiscount,
    totalRebate,
    totalMrp,
    shipping,
    grandTotal,
  } = useSelector((state) => state.cartState);
  return (
    <div className="md:flex gap-2">
      <div className="md:w-3/5">
        {cartItems?.map((cartItem) => {
          return <CartItem cartItem={cartItem} key={cartItem.id.productId} />;
        })}
      </div>
      <div>
        <PriceCard
          basePrice={totalMrp}
          discount={totalDiscount}
          totalPrice={cartTotal}
          shippingCharge={shipping}
          grandTotal={grandTotal}
          totalRebate={totalRebate}
        />
      </div>
    </div>
  );
};
export default Cart;
