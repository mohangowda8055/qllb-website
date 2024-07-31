import { FaShoppingCart } from "react-icons/fa";
import { useSelector } from "react-redux";
import { Link } from "react-router-dom";

const CartButton = () => {
  const { numItemsInCart } = useSelector((state) => state.cartState);
  return (
    <div className="relative sm:block">
      <Link to="/cart" className=" inline-block">
        <span className=" text-neutral">
          <FaShoppingCart size={22} />
          <span className="absolute top-0 right-0 inline-flex items-center justify-center px-2 py-1 text-xs font-bold leading-none text-black bg-secondary rounded-full transform translate-x-1/2 -translate-y-1/2">
            {numItemsInCart}
          </span>
        </span>
      </Link>
    </div>
  );
};
export default CartButton;
