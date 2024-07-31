import { useSelector } from "react-redux";
import OrderDetail from "../components/orderdetail/OrderDetail";
import OrderDetailHeading from "../components/orderdetail/OrderDetailHeading";

const OrderDetailPage = () => {
  const { order } = useSelector((state) => state.orderState);
  return (
    <section className="bg-primary">
      <div className=" max-w-7xl mx-auto">
        <OrderDetailHeading />
        <div className="pb-4 ">
          {order?.orderItems?.map((item) => {
            return (
              <OrderDetail
                orderId={order?.orderId}
                productName={item?.product?.productName}
                productType={item?.product?.productType}
                grandTotal={order?.grandTotal}
                quantity={item?.quantity}
                isRebate={item?.rebate}
                orderedDate={order?.orderDate}
                deliveryDate={order?.deliveryDate}
                paymentMethod={order?.paymentMethod}
                paymentStatus={order?.paymentDetail?.paymentStatus}
                addressLine1={order?.shippingAddress?.addressLine1}
                addressLine2={order?.shippingAddress?.addressLine2}
                city={order?.shippingAddress?.city}
                state={order?.shippingAddress?.state}
                postalCode={order?.shippingAddress?.postalCode}
                phoneNumber={order?.shippingAddress?.phoneNumber}
                discountPercentage={item?.product?.discountPercentage}
                rebate={item?.product?.rebate}
                image={item?.product?.imageMainUrl}
                deliveryCost={order?.deliveryCost}
                mrp={item?.product?.mrp}
                key={item?.id?.productId}
              />
            );
          })}
        </div>
      </div>
    </section>
  );
};
export default OrderDetailPage;
