import { useState } from "react";
import { FaCircleChevronDown, FaCircleChevronUp } from "react-icons/fa6";

const ProductDetail = ({ product, isCollapsed }) => {
  const [infoCollapsed, setInfoCollapsed] = useState(isCollapsed);
  const {brandName, series, modelName, voltage, ah, warranty, guarantee, terminalLayout} = product;
  return (
    <div className="mt-3">
      <div
        className="bg-stone-100 flex items-center justify-between gap-2 border border-gray-300 py-1 px-2 text-black font-medium hover:cursor-pointer"
        onClick={() => setInfoCollapsed(!infoCollapsed)}
      >
        <button onClick={() => setInfoCollapsed(!infoCollapsed)}>
          More Info
        </button>
        <button onClick={() => setInfoCollapsed(!infoCollapsed)}>
          {infoCollapsed ? <FaCircleChevronDown /> : <FaCircleChevronUp />}
        </button>
      </div>
      <div
        className={`grid grid-cols-2 bg-stone-100 text-black mt-3 ${
          infoCollapsed ? "hidden" : ""
        }`}
      >
        <div className="flex items-center border border-gray-300 p-1">
          <h1>Brand</h1>
        </div>
        <div className="bg-white flex justify-center items-center border border-gray-300 p-1">
          <h1>{brandName}</h1>
        </div>
        <div className="flex items-center border border-gray-300 p-1">
          <h1>Series</h1>
        </div>
        <div className="bg-white flex justify-center items-center border border-gray-300 p-1">
          <h1>{series}</h1>
        </div>
        <div className="flex items-center border border-gray-300 p-1">
          <h1>Model</h1>
        </div>
        <div className="bg-white flex justify-center items-center border border-gray-300 p-1">
          <h1>{modelName}</h1>
        </div>
        <div className="flex items-center border border-gray-300 p-1">
          <h1>Voltage</h1>
        </div>
        <div className="bg-white flex justify-center items-center border border-gray-300 p-1">
          <h1>{voltage}</h1>
        </div>
        <div className="flex items-center border border-gray-300 p-1">
          <h1>Ref. Amphere Hour (AH)</h1>
        </div>
        <div className="bg-white flex justify-center items-center border border-gray-300 p-1">
          <h1>{ah}</h1>
        </div>
        <div className="flex items-center border border-gray-300 p-1">
          <h1>Total Warranty (Months)</h1>
        </div>
        <div className="bg-white flex justify-center items-center border border-gray-300 p-1">
          <h1>{warranty + guarantee}</h1>
        </div>
        <div className="flex items-center border border-gray-300 p-1">
          <h1>Free Warranty (Months)</h1>
        </div>
        <div className="bg-white flex justify-center items-center border border-gray-300 p-1">
          <h1>{guarantee}</h1>
        </div>
        <div className="flex items-center border border-gray-300 p-1">
          <h1>Pro-rata Warranty (Months)</h1>
        </div>
        <div className="bg-white flex justify-center items-center border border-gray-300 p-1">
          <h1>{warranty}</h1>
        </div>
        <div className="flex items-center border border-gray-300 p-1">
          <h1>Terminal Layout</h1>
        </div>
        <div className="bg-white flex justify-center items-center border border-gray-300 p-1">
          <h1>{terminalLayout}</h1>
        </div>
      </div>
    </div>
  );
};
export default ProductDetail;
