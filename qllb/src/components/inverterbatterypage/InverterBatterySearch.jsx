import { useRef, useState } from "react";
import { useSelector } from "react-redux";
import {
  useFetchInverterBatteries,
  useFetchWarranties,
} from "../../reactquery/products/productQueryCustomHooks";
import ProductList from "../ProductList";
import CustomSelect from "../CustomSelect";

const InverterBatterySearch = ({ backupDurations }) => {
  const scrollToRef = useRef(null);
  const [ searched, setSearched] = useState(false);
  const [backupDuration, setBackupDuration] = useState("");
  const [backupDurationId, setBackupDurationId] = useState(null);
  const [warranty, setWarranty] = useState("");
  const [warrantyId, setWarrantyId] = useState(null);
  const { inverterBatteryProductList } = useSelector(
    (state) => state.productState
  );

  const { isWarrantiesLoading, warranties } = useFetchWarranties();

  const { isInverterBatteriesLoading, inverterBatteriesRefetch } =
    useFetchInverterBatteries(backupDurationId, warrantyId);

  const handleSetBackupDuration = (newBackup) => {
    setBackupDurationId(newBackup.backupDurationId);
    setBackupDuration(newBackup);
  };

  const handleSetWarranty = (newWarranty) => {
    setWarranty(newWarranty);
    setWarrantyId(newWarranty.warrantyId);
  };

  const handleSetBatteryList = (e) => {
    e.preventDefault();
    inverterBatteriesRefetch();
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
                Backup Duration
              </label>
              <CustomSelect
                lists={backupDurations}
                selected={backupDuration}
                setSelected={handleSetBackupDuration}
                placeholder={"backupDuration"}
                isLoading={false}
              />
            </div>
            <div>
              <label className=" text-gray-500 text-sm tracking-wider">
                Warranty
              </label>
              <CustomSelect
                lists={warranties}
                selected={warranty}
                setSelected={handleSetWarranty}
                placeholder={"warranty"}
                isLoading={isWarrantiesLoading}
              />
            </div>
            <div className="sm:self-center sm:mt-4">
              <button className="bg-secondary text-white  font-medium tracking-wider uppercase py-1 px-2 shadow-lg hover:text-black">
                Find Now
              </button>
            </div>
          </div>
        </form>
      </div>
      <div ref={scrollToRef}>
        <div className=" h-24 md:h-20"></div>
        {isInverterBatteriesLoading ? (
          <div>Loading...</div>
        ) : (
          inverterBatteryProductList.length > 0 ? (
            <ProductList
              products={inverterBatteryProductList}
              model={warranty.warranty}
              productType={"inverter batteries"}
            />
          ) : searched && <div>No Batteries Found!!</div>
        )}
      </div>
    </section>
  );
};
export default InverterBatterySearch;
