import { MdArrowBack } from "react-icons/md";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";

const OrderDetailHeading = () => {
  const navigate = useNavigate();
  const { isFromOrders } = useSelector((state) => state.orderState);
  return (
    <div>
      <div className="flex items-center p-4 border-b border-gray-300">
        <button
          type="button"
          className="transition duration-200 ease-in-out transform hover:scale-105 active:bg-secondary_light active:scale-95"
          onClick={() => {
            if (isFromOrders === true) {
              navigate(-1);
            } else {
              navigate("/");
            }
          }}
        >
          <MdArrowBack />
        </button>{" "}
        <span className="pl-2 text-lg font-semibold">Order Detail</span>
      </div>
      {!isFromOrders && (
        <div className=" flex flex-col justify-center items-center gap-1 p-2">
          <h1 className=" font-semibold p-2 bg-green-400">Order Placed</h1>
          <h1 className="font-semibold pb-2">
            Thankyou, We wish to see you back
          </h1>
        </div>
      )}
    </div>
  );
};
export default OrderDetailHeading;
