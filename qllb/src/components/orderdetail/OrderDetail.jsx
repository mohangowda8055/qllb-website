import { FaIndianRupeeSign } from "react-icons/fa6";
import { useSelector } from "react-redux";

const OrderDetail = ({
  orderId,
  productName,
  productType,
  grandTotal,
  quantity,
  isRebate,
  orderedDate,
  deliveryDate,
  paymentMethod,
  paymentStatus,
  addressLine1,
  addressLine2,
  city,
  state,
  postalCode,
  phoneNumber,
  discountPercentage,
  rebate,
  image,
  deliveryCost,
  mrp,
}) => {
  const discount = mrp * (discountPercentage / 100);
  const totalPrice = mrp - discount - (isRebate ? rebate : 0);
  const finalAmount = totalPrice * quantity + deliveryCost;
  const { isFromOrders } = useSelector((state) => state.orderState);
  return (
    <div className={isFromOrders ? "pb-2" : "pb-2 sm:p-2 "}>
      <div className="bg-white border border-gray-300 sm:rounded-lg shadow-sm ">
        <div className="text-sm text-gray-500 p-2 border-b border-gray-300">
          <span>Order ID - </span>
          <span>{orderId}</span>
        </div>
        <div className=" flex justify-between gap-2 p-2 ">
          <div className=" flex flex-col justify-center gap-1">
            <div>
              <h1 className=" font-medium">{productName}</h1>
            </div>
            <div>
              <span>Total Amount to be paid - </span>
              <FaIndianRupeeSign className=" inline text-green-500" />
              <span>{grandTotal.toFixed(2)}</span>
            </div>
            <div>
              <span>Quantity: </span>
              <span className=" font-medium">{quantity}</span>
            </div>
            {
              productType !== "INVERTER" && (
              <div>
              <span>Old Battery Return: </span>
              <span className=" font-medium">{isRebate ? "Yes" : "No"}</span>
            </div>
              )
            }
            <div>
              <span>Order Confirmed, </span>
              <span className=" font-medium">
                {orderedDate.replace("T", " ")}
              </span>
            </div>
            <div>
              <span>Delivered, </span>
              <span className=" font-medium">
                {deliveryDate || "yet to be delivered"}
              </span>
            </div>
            <div>
              <span>Payment Method: </span>
              <span className=" font-medium">{paymentMethod}</span>
            </div>
            <div>
              <span>Payment Status: </span>
              <span className=" font-medium capitalize">
                {deliveryDate
                  ? paymentStatus || "Paid"
                  : paymentStatus || "Not Paid"}
              </span>
            </div>
          </div>
          <div className=" p-1">
            <img src={image} alt="image" className="max-w-24 h-auto" />
          </div>
        </div>
        <div className="text-sm text-gray-500 p-2 border-y border-gray-300">
          <span>Shipping Details </span>
        </div>
        <div className=" p-2">
          <div>{addressLine1}</div>
          <div>{addressLine2}</div>
          <div>
            {city}, {state} - {postalCode}
          </div>
          <div className="pt-1">{phoneNumber}</div>
        </div>
        <div className="text-sm text-gray-500 p-2 border-y border-gray-300">
          <span>Price Details </span>
        </div>
        <div>
          <div className="flex flex-col gap-2 p-3">
            <div className="flex justify-between items-center">
              <div className="flex flex-col">
                <span className="font-semibold tracking-wide">Base Price</span>
                <span className="text-xs">(inclusive of GST)</span>
              </div>
              <div className="font-semibold">
                <span className="flex justify-center items-center">
                  <FaIndianRupeeSign size={13} className="inline" />
                  <span>{mrp.toFixed(2)}</span>
                </span>
              </div>
            </div>
            <div className="flex justify-between font-semibold">
              <div>
                <span>Special Discount</span>
              </div>
              <div>
                <span className="flex justify-center items-center">
                 - <FaIndianRupeeSign size={13} className="inline" />
                  <span>{discount.toFixed(2)}</span>
                </span>
              </div>
            </div>
            <div className="flex justify-between">
              <div className="flex flex-col">
                <span className="font-semibold">Total Price</span>
                <span className="text-xs">(Inclusive of GST)</span>
              </div>
              <div>
                <span className="flex justify-center items-center font-semibold">
                  <FaIndianRupeeSign size={13} className="inline" />
                  <span>{totalPrice.toFixed(2)}</span>
                </span>
              </div>
            </div>
            <div className="flex justify-between font-semibold">
              <div>
                <span>Shipping Charge</span>
              </div>
              <div>
                <span className="flex justify-center items-center">
                  <FaIndianRupeeSign size={13} className="inline" />
                  <span>{deliveryCost.toFixed(2)}</span>
                </span>
              </div>
            </div>
            <div className="flex justify-between font-semibold text-green-600">
              <div>
                <span>Total Amount</span>
              </div>
              <div>
                <span className="flex justify-center items-center">
                  <FaIndianRupeeSign size={13} className="inline" />
                  <span>{finalAmount.toFixed(2)}</span>
                </span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
export default OrderDetail;
