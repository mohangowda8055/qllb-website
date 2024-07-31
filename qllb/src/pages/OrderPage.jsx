import { useSelector } from "react-redux";
import OrderHeading from "../components/order/OrderHeading";
import OrderItem from "../components/order/OrderItem";
import { useOrders } from "../reactquery/order/orderQueryCustomHooks";

const OrderPage = () => {
  const { user } = useSelector((state) => state.userState);
  const { data, isLoading } = useOrders(user);
  return (
    <section className="bg-primary">
      <div className=" max-w-7xl m-auto">
        <OrderHeading />
        {isLoading ? (
          <div className=" min-h-96 flex justify-center items-center">
            <h1 className=" text-xl font-semibold">Loading...</h1>
          </div>
        ) : (
          <div className="p-2">
            {data?.map((order) => {
              return <OrderItem order={order} key={order.orderId} />;
            })}
          </div>
        )}
      </div>
    </section>
  );
};
export default OrderPage;
