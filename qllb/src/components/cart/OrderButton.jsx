import { useNavigate } from "react-router-dom";

const OrderButton = () => {
  const navigate = useNavigate()
  return (
    <div className="py-1 md:py-2">
      <button type="button" className="bg-secondary text-white border text-sm font-medium py-1 px-2 shadow-lg uppercase" onClick={()=>navigate("/checkout")}>
        Proceed To Order
      </button>
    </div>
  );
};
export default OrderButton;
