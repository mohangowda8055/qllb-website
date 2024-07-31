import { MdArrowForwardIos } from "react-icons/md";
import { useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import { setIsFromOrders, setOrder } from "../../features/order/orderSlice";

const OrderItem = ({ order }) => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  return (
    <div className="pb-2">
      <div className=" flex sm:justify-between gap-1 bg-white border border-gray-300 h-36 rounded-lg shadow-sm ">
        <div className=" p-1">
          <img
            src={order?.orderItems[0]?.product?.imageMainUrl}
            alt="image"
            className="max-w-32 h-auto"
          />
        </div>
        <div className=" flex flex-col justify-center gap-2">
          <div>
            <h1>
              <span>Ordered on: </span>{" "}
              <span className=" font-medium">
                {order?.orderDate?.replace("T", " ")}
              </span>
            </h1>
          </div>
          <div>
            <h1 className=" text-sm">
              {order?.orderItems[0]?.product?.productName}{" "}
              {order?.orderItems?.length > 1 &&
                `+ ${order?.orderItems?.length - 1}`}
            </h1>
          </div>
          <div>
            <h1 className=" text-sm">
              <span>Order Status: </span>
              <span className=" font-medium">{order?.orderStatus}</span>
            </h1>
          </div>
        </div>
        <div className="flex justify-center items-center">
          <div>
            <button
              type="button" className=" hover:cursor-pointer"
              onClick={() => {
                dispatch(setOrder(order));
                dispatch(setIsFromOrders(true));
                navigate("/orderdetail");
              }}
            >
              <MdArrowForwardIos />
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};
export default OrderItem;
