import { useDispatch } from "react-redux";
import { editCartItemIsRebate } from "../../features/cart/cartSlice";

const OldBattery = ({ isRebate, productId }) => {
  const dispatch = useDispatch();
  const handleCheckboxChange = (e) => {
    const data = { id: productId, isChecked: e.target.checked };
    dispatch(editCartItemIsRebate(data));
  };
  return (
    <div className="py-2 sm:p-0">
      <input
        type="checkbox"
        checked={isRebate}
        onChange={handleCheckboxChange}
        className="bg-white border border-gray-300"
      />
      <span className=" text-sm font-medium pl-2">Old Battery Return</span>
    </div>
  );
};
export default OldBattery;
