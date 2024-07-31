import { RiDeleteBinLine } from "react-icons/ri";
import { removeCartItem } from "../../features/cart/cartSlice";
import { useDispatch, useSelector } from "react-redux";
import { useDeleteCartItem } from "../../reactquery/cart/cartCustomHooks";

const RemoveCartItemButton = ({ cartItem }) => {
  const { id } = cartItem;
  const { cartId, productId } = id;
  const dispatch = useDispatch();
  const { user } = useSelector((state) => state.userState);
  const { deleteCartItem } = useDeleteCartItem();
  const handleRemoveCartitem = () => {
    deleteCartItem({ cartId, productId, user });
    dispatch(removeCartItem(productId));
  };
  return (
    <div className="py-1 md:py-2">
      <button
        className="bg-white text-black border border-gray-300 text-sm font-medium py-1 px-2 shadow-sm uppercase hover:cursor-pointer transition duration-200 ease-in-out transform hover:scale-105 active:bg-secondary_light active:scale-95"
        onClick={handleRemoveCartitem}
      >
        <span className="flex justify-center items-center gap-1">
          <RiDeleteBinLine className="inline" />
          <span>Remove</span>
        </span>
      </button>
    </div>
  );
};
export default RemoveCartItemButton;
