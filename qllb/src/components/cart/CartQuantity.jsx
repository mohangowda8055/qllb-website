import { useDispatch } from "react-redux";
import { editCartItemQty } from "../../features/cart/cartSlice";

const CartQuantity = ({ quantity, product }) => {
  
  const { productId, stock } = product;
  const dispatch = useDispatch();
  const handleChange = (e) => {
    const data = { id: productId, qty: e.target.value };
    dispatch(editCartItemQty(data));
  };
  return (
    <div className="py-2">
      <select
        name=""
        id=""
        value={quantity}
        onChange={handleChange}
        className="border border-gray-300 w-20 text-sm"
      >
        <option value={quantity}>Qty: {quantity}</option>
        <option className={`${stock < 1 ? "hidden" : ""}`} value={1}>
          1
        </option>
        <option className={`${stock < 2 ? "hidden" : ""}`} value={2}>
          2
        </option>
        <option className={`${stock < 3 ? "hidden" : ""}`} value={3}>
          3
        </option>
        <option className={`${stock < 4 ? "hidden" : ""}`} value={4}>
          4
        </option>
      </select>
    </div>
  );
};
export default CartQuantity;
