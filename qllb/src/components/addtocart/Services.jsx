import { FaShippingFast } from "react-icons/fa";
import { MdHandyman, MdWorkspacePremium } from "react-icons/md";

const Services = ({ totalWarranty }) => {
  return (
    <div className="flex justify-between p-3 sm:p-0 font-medium">
      <div className="flex flex-col  items-center">
        <div className="bg-black rounded-full w-14 h-14 flex justify-center items-center">
          <FaShippingFast size={24} className="text-secondary" />
        </div>
        <span>Fast</span>
        <span>Shipping</span>
      </div>
      <div className="flex flex-col  items-center">
        <div className="bg-black rounded-full w-14 h-14 flex justify-center items-center">
          <MdHandyman size={24} className="text-secondary " />
        </div>
        <span>Free</span>
        <span>Installation</span>
      </div>
      <div className="flex flex-col  items-center">
        <div className="bg-black rounded-full w-14 h-14 flex justify-center items-center">
          <MdWorkspacePremium size={24} className="text-secondary" />
        </div>
        <span>{totalWarranty} Months </span>
        <span>Warranty</span>
      </div>
    </div>
  );
};
export default Services;
