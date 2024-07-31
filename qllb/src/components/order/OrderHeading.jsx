import { MdArrowBack } from "react-icons/md";
import { useNavigate } from "react-router-dom";

const OrderHeading = () => {
  const navigate = useNavigate();
  return (
    <div className="flex items-center p-4">
      <button type="button" className=" hover:cursor-pointer" onClick={() => navigate(-1)}>
        <MdArrowBack />
      </button>{" "}
      <span className="pl-2 text-lg font-semibold">Your Orders</span>
    </div>
  );
};
export default OrderHeading;
