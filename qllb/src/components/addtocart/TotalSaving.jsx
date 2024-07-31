import { FaIndianRupeeSign } from "react-icons/fa6";
import { GiTakeMyMoney } from "react-icons/gi";

const TotalSaving = ({ discount, rebate, productType }) => {
  return (
    <div className="flex flex-col justify-center items-center gap-4">
      <div className="flex justify-center items-center font-semibold">
        <GiTakeMyMoney size={25} className="text-green-600 inline" />
        <span className="px-2">Total saving</span>
        <FaIndianRupeeSign size={14} className="text-green-600 inline" />
        <span className="text-green-600">{(discount + rebate).toFixed(2)}</span>
      </div>
      {
        productType !== "INVERTER" && (
      <div>
        <div className="bg-white p-2 text-xs border border-gray-300">
          <p className="text-center">
            <FaIndianRupeeSign size={12} className="inline" />
            <span className="font-semibold">{discount.toFixed(2)}</span>{" "}
            (Special discount) +{" "}
            <span className="font-semibold pr-1">
              upto <FaIndianRupeeSign size={12} className="inline" />
              {rebate.toFixed(2)}
            </span>
            (Rebate on old battery return)
          </p>
        </div>
      </div>
        )
      }
    </div>
  );
};
export default TotalSaving;
