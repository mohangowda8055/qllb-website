import { useState } from "react";
import {
  useCreateOrder,
  useCreatePayment,
} from "../../reactquery/order/orderQueryCustomHooks";
import { useDispatch, useSelector } from "react-redux";
import { toast } from "react-toastify";
import { setOrder } from "../../features/order/orderSlice";
import { useNavigate } from "react-router-dom";
import { clearCart } from "../../features/cart/cartSlice";

const PayButton = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { createOrder, isPending } = useCreateOrder();
  const { createPayment } = useCreatePayment();
  const { user } = useSelector((state) => state.userState);
  const { grandTotal } = useSelector((state) => state.cartState);
  const [selected, setSelected] = useState("onlinePayment");

  const handleSelected = (e) => {
    setSelected(e.target.value);
  };

  const handleSubmit = () => {
    if (selected === "onlinePayment") {
      createOrder(
        { order: { paymentMethod: "ONLINE" }, user },
        {
          onSuccess: (data) => {
            dispatch(setOrder(data.data.data));
            createPayment(
              { orderId: data.data.data.orderId, user },
              {
                onSuccess: (paymentData) => {
                  window.location.href = paymentData.data.data.payment_link_url;
                },
              }
            );
          },
        }
      );
    } else {
      createOrder(
        { order: { paymentMethod: "CASH_ON_DELIVERY" }, user },
        {
          onSuccess: (data) => {
            dispatch(setOrder(data.data.data));
            dispatch(clearCart());
            toast.success("Order Created");
            navigate("/orderdetail");
          },
        }
      );
    }
  };

  return (
    <div>
      <div>
        <div>
          <input
            type="radio"
            name="payment"
            value={"onlinePayment"}
            checked={selected === "onlinePayment"}
            onChange={handleSelected}
          />
          <label htmlFor="" className="pl-1">
            Online Payment
          </label>
        </div>
        <div>
          <input
            type="radio"
            name="payment"
            value={"cashOnDelivery"}
            checked={selected === "cashOnDelivery"}
            onChange={handleSelected}
          />
          <label htmlFor="" className="pl-1">
            Cash on Delivery
          </label>
        </div>
      </div>
      <div className="flex gap-4 pt-2">
        <button
          type="button"
          onClick={handleSubmit}
          className=" bg-secondary text-black font-medium py-1 px-2 shadow-lg uppercase transition duration-200 ease-in-out transform hover:bg-secondary_dark hover:scale-105 active:bg-secondary_light active:scale-95"
        >
          {isPending
            ? "Submitting"
            : selected === "onlinePayment"
            ? "Pay " + grandTotal.toFixed(2)
            : "Place Order"}
        </button>
      </div>
    </div>
  );
};
export default PayButton;
