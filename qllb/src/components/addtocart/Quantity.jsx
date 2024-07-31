import { FaMinus, FaPlus } from "react-icons/fa6";

const Quantity = ({
  quantity,
  handleIncreaseQty,
  handleDecreaseQty,
  product,
}) => {
  return (
    <div className="flex items-center gap-2 p-3 sm:p-0 sm:pt-4 text-sm">
      <div>
        <span className=" tracking-widest">QTY</span>
      </div>
      <div className="flex bg-white">
        <button
        type="button"
          className=" border border-gray-300 px-2 hover:cursor-pointer"
          onClick={handleDecreaseQty}
        >
          <FaMinus size={12} />
        </button>
        <span className=" border border-gray-300 px-4 py-1 ">{quantity}</span>
        <button
        type="button"
          className=" border border-gray-300 px-2 py-1 hover:cursor-pointer"
          onClick={handleIncreaseQty}
        >
          <FaPlus size={12} />
        </button>
      </div>
      <div>
        <span>{`${product?.stock == 0 ? "Out of Stock" : "In Stock"}`}</span>
      </div>
    </div>
  );
};
export default Quantity;
