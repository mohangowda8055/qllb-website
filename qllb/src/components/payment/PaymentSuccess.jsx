import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { useUpdatePayment } from "../../reactquery/order/orderQueryCustomHooks";
import OrderDetail from "../orderdetail/OrderDetail";
import { useDispatch, useSelector } from "react-redux";
import { clearCart } from "../../features/cart/cartSlice";

const PaymentSuccess = () => {
  const dispatch = useDispatch();
  const { order } = useSelector((state) => state.orderState);
  const { user } = useSelector((state) => state.userState);
  const [paymentId, setPaymentId] = useState("");
  const [paymentLinkId, setPaymentLinkId] = useState("");
  const [referenceId, setReferenceId] = useState("");
  const [paymentStatus, setPaymentStatus] = useState("");
  const { orderId } = useParams();
  const { isLoading, paymentRefetch } = useUpdatePayment({
    orderId: orderId,
    user: user,
    payment: {
      razorpayPaymentId: paymentId,
      razorpayPaymentLinkId: paymentLinkId,
      razorpayPaymentLinkReferenceId: referenceId,
      razorpayPaymentLinkStatus: paymentStatus,
    },
  });

  const grandTotal = order?.grandTotal;
  const orderDate = order?.orderDate;
  const deliveryDate = order?.deliveryDate;
  const paymentMethod = order?.paymentMethod;
  const paymentStatus1 = order?.paymentDetail?.paymentStatus;
  const addressLine1 = order?.shippingAddress?.addressLine1;
  const addressLine2 = order?.shippingAddress?.addressLine2;
  const city = order?.shippingAddress?.city;
  const state = order?.shippingAddress?.state;
  const postalCode = order?.shippingAddress?.postalCode;
  const phoneNumber = order?.shippingAddress?.phoneNumber;
  const deliveryCost = order?.deliveryCost;

  useEffect(() => {
    if (paymentId && orderId) {
      paymentRefetch();
    }
  }, [paymentId, orderId]);

  useEffect(() => {
    const urlParam = new URLSearchParams(window.location.search);
    setPaymentId(urlParam.get("razorpay_payment_id"));
    setPaymentLinkId(urlParam.get("razorpay_payment_link_id"));
    setPaymentStatus(urlParam.get("razorpay_payment_link_status"));
    setReferenceId(urlParam.get("razorpay_payment_link_reference_id"));
    dispatch(clearCart());
  }, []);
  isLoading ? (
    <div className=" min-h-96 flex justify-center items-center">
      <h1 className=" text-xl font-semibold">Loading...</h1>
    </div>
  ) : (
    <div className="pb-4">
      {order?.orderItems?.map((item) => {
        return (
          <OrderDetail
            orderId={orderId}
            productName={item?.product?.productName}
            productType={item?.product?.productType}
            grandTotal={grandTotal}
            quantity={item?.quantity}
            isRebate={item?.rebate}
            orderedDate={orderDate}
            deliveryDate={deliveryDate}
            paymentMethod={paymentMethod}
            paymentStatus={paymentStatus1}
            addressLine1={addressLine1}
            addressLine2={addressLine2}
            city={city}
            state={state}
            postalCode={postalCode}
            phoneNumber={phoneNumber}
            discountPercentage={item?.product?.discountPercentage}
            rebate={item?.product?.rebate}
            deliveryCost={deliveryCost}
            image={item?.product?.image_main_url}
            mrp={item?.product?.mrp}
            key={item?.id?.productId}
          />
        );
      })}
    </div>
  );
};
export default PaymentSuccess;
