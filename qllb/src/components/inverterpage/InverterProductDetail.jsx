import { useState } from "react";
import { FaCircleChevronDown, FaCircleChevronUp } from "react-icons/fa6";

const InverterProductDetail = ({ product, isCollapsed }) => {
  const [infoCollapsed, setInfoCollapsed] = useState(isCollapsed);
  const {
    brandName,
    noOfBatteryReq,
    warranty,
    inverterType,
    recBatteryCapacity,
    modelName,
    voltage,
  } = product;
  return (
    <div className="mt-3">
      <div
        className="bg-stone-100 flex items-center justify-between gap-2 border border-gray-300 py-1 px-2 text-black font-medium"
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
          <h1>No of Battery Required</h1>
        </div>
        <div className="bg-white flex justify-center items-center border border-gray-300 p-1">
          <h1>{noOfBatteryReq}</h1>
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
          <h1>Rec. Battery Capacity</h1>
        </div>
        <div className="bg-white flex justify-center items-center border border-gray-300 p-1">
          <h1>{recBatteryCapacity}</h1>
        </div>
        <div className="flex items-center border border-gray-300 p-1">
          <h1>Total Warranty (Months)</h1>
        </div>
        <div className="bg-white flex justify-center items-center border border-gray-300 p-1">
          <h1>{warranty}</h1>
        </div>
        <div className="flex items-center border border-gray-300 p-1">
          <h1>Inverter Type</h1>
        </div>
        <div className="bg-white flex justify-center items-center border border-gray-300 p-1">
          <h1>{inverterType}</h1>
        </div>
      </div>
    </div>
  );
};
export default InverterProductDetail;
