import { useEffect, useRef, useState } from "react";
import CustomSelect from "../CustomSelect";
import {
  useFetchBatteries,
  useFetchModels,
} from "../../reactquery/products/productQueryCustomHooks";
import ProductList from "../ProductList";
import { useSelector } from "react-redux";

const TwoWheelerSearch = ({ brands }) => {
  const scrollToRef = useRef(null);
  const [ searched, setSearched ] = useState(false);
  const [brand, setBrand] = useState("");
  const [brandId, setBrandId] = useState(null);
  const [model, setModel] = useState("");
  const [modelId, setModelId] = useState(null);
  const { twoVProductList } = useSelector((state) => state.productState);

  const { isModelsLoading, models, modelsRefetch } = useFetchModels(
    "twov",
    brandId
  );

  const { isBatteriesLoading, batteriesRefetch } = useFetchBatteries(
    "twov",
    modelId,
    null
  );

  const handleFetchModels = (newBrand) => {
    setBrandId(newBrand.brandId);
    setBrand(newBrand);
  };

  const handleSetModel = (newModel) => {
    setModel(newModel);
    setModelId(newModel.modelId);
  };

  const handleSetBatteryList = (e) => {
    e.preventDefault();
    batteriesRefetch();
    setSearched(true)
    if (scrollToRef.current) {
      window.scrollTo({
        behavior: "smooth",
        top: scrollToRef.current.offsetTop,
      });
    }
  };

  useEffect(() => {
    brandId && modelsRefetch();
  }, [brandId]);

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
                Vehicle Make
              </label>
              <CustomSelect
                lists={brands}
                selected={brand}
                setSelected={handleFetchModels}
                placeholder={"brand Name"}
                isLoading={false}
              />
            </div>
            <div>
              <label className=" text-gray-500 text-sm tracking-wider">
                Model
              </label>
              <CustomSelect
                lists={models}
                selected={model}
                setSelected={handleSetModel}
                placeholder={"model Name"}
                isLoading={isModelsLoading}
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
        {isBatteriesLoading ? (
          <div className=" p-2">Loading...</div>
        ) : (
          twoVProductList.length > 0 ? (
            <ProductList
              products={twoVProductList}
              model={model.modelName}
              productType={"batteries"}
            />
          ) : searched && <div>No Batteries Found!!</div>
        )}
      </div>
    </section>
  );
};
export default TwoWheelerSearch;
