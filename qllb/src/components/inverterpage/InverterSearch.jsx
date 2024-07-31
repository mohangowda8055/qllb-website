import { useRef, useState } from "react";
import { useSelector } from "react-redux";
import { useFetchInverters } from "../../reactquery/products/productQueryCustomHooks";
import CustomSelect from "../CustomSelect";
import InverterProductList from "./InverterProductList";

const InverterSearch = ({ capacities }) => {
  const scrollToRef = useRef(null);
  const [ searched, setSearched ] = useState(false);
  const [capacity, setCapacity] = useState("");
  const [capacityId, setCapacityId] = useState(null);
  const { inverterProductList } = useSelector((state) => state.productState);

  const { isInvertersLoading, invertersRefetch } =
    useFetchInverters(capacityId);

  const handleSetCapacity = (newCapacity) => {
    setCapacity(newCapacity);
    setCapacityId(newCapacity.capacityId);
  };

  const handleSetBatteryList = (e) => {
    e.preventDefault();
    invertersRefetch();
    setSearched(true);
    if (scrollToRef.current) {
      window.scrollTo({
        behavior: "smooth",
        top: scrollToRef.current.offsetTop,
      });
    }
  };

  return (
    <section className="bg-white min-h-80">
      <div className="p-2 pt-4 md:p-4 pb-6 md:pb-8 max-w-7xl mx-auto">
        <div className="capitalize">
          <h1 className="text-xl font-medium tracking-widest pb-1">
            Choose Your Battery
          </h1>
          <div className=" border-b-2 border-secondary border-solid w-40 content-none"></div>
        </div>
        <form className="mt-4" onSubmit={handleSetBatteryList}>
          <div className="flex flex-col gap-1 sm:flex-row sm:items-center sm:gap-4">
            <div>
              <label className="text-gray-500 text-sm  tracking-wider">
                Capacity
              </label>
              <CustomSelect
                lists={capacities}
                selected={capacity}
                setSelected={handleSetCapacity}
                placeholder={"capacity"}
                isLoading={false}
              />
            </div>
            <div className="sm:self-center sm:mt-4">
              <button className="bg-secondary text-white  font-medium tracking-wider uppercase py-1 px-2 shadow-lg transition duration-200 ease-in-out transform hover:bg-secondary_dark hover:scale-105 active:bg-secondary_light active:scale-95">
                Find Now
              </button>
            </div>
          </div>
        </form>
      </div>
      <div ref={scrollToRef}>
        <div className=" h-24 md:h-20"></div>
        {isInvertersLoading ? (
          <div>Loading...</div>
        ) : (
          inverterProductList.length > 0 ? (
            <InverterProductList
              products={inverterProductList}
              capacity={capacity.capacity}
            />
          ) : searched && <div>No Inverters Found!!</div>
        )}
      </div>
    </section>
  );
};
export default InverterSearch;
