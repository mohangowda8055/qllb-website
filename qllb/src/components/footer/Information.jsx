import { useEffect, useRef } from "react";
import { FaCircleChevronDown, FaCircleChevronUp } from "react-icons/fa6";
import { Link } from "react-router-dom";

const Information = ({ infoCollapsed, setInfoCollapsed }) => {
  const footerInfoRef = useRef(null);

  useEffect(() => {
    if (!infoCollapsed) {
      footerInfoRef.current.scrollIntoView({ behavior: "smooth" });
    }
  }, [infoCollapsed]);
  return (
    <div className="flex flex-col sm:gap-2 max-h-screen">
      <div className="flex items-center justify-between text-base font-semibold py-2 px-4 sm:hidden">
        <button
          className="text-black text-left w-full"
          onClick={() => setInfoCollapsed(!infoCollapsed)}
        >
          Information{" "}
        </button>
        <button onClick={() => setInfoCollapsed(!infoCollapsed)}>
          {infoCollapsed ? <FaCircleChevronDown /> : <FaCircleChevronUp />}
        </button>
      </div>
      <div>
        <h1 className="hidden sm:block text-black pt-2 pb-1 text-medium font-semibold">
          Information
        </h1>
        <div className="hidden sm:block border border-secondary w-4/5 mb-2"></div>
        <ul
          className={`max-h-screen text-sm text-gray-700 sm:text-black list-none font-medium px-4 py-2 flex flex-col gap-2 ${
            infoCollapsed ? "hidden sm:flex" : ""
          } sm:flex-col sm:gap-2 sm:px-0`}
        >
          <li>
            <Link to="#">Privacy Policy</Link>
          </li>
          <li>
            <Link to="#">Shipping Policy</Link>
          </li>
          <li>
            <Link to="#">Terms & Conditions</Link>
          </li>
          <li ref={footerInfoRef}>
            <Link to="#">FAQs</Link>
          </li>
        </ul>
        <hr className="border-gray-500 sm:hidden" />
      </div>
    </div>
  );
};
export default Information;
