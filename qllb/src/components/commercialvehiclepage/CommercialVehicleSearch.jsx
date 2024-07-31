import { useEffect, useRef, useState } from "react";
import { useSelector } from "react-redux";
import {
  useFetchBatteries,
  useFetchCommercialModels,
  useFetchSegments,
} from "../../reactquery/products/productQueryCustomHooks";
import CustomSelect from "../CustomSelect";
import ProductList from "../ProductList";

const CommercialVehicleSearch = ({ brands }) => {
  const scrollToRef = useRef(null);
  const [ searched, setSearched ] = useState(false);
  const [brand, setBrand] = useState("");
  const [brandId, setBrandId] = useState(null);
  const [segment, setSegment] = useState("");
  const [segmentId, setSegmentId] = useState(null);
  const [model, setModel] = useState("");
  const [modelId, setModelId] = useState(null);

  const { commercialVProductList } = useSelector((state) => state.productState);
  const { isSegmentsLoading, segments, segmentsRefetch } =
    useFetchSegments(brandId);

  const {
    isCommerialModelsLoading,
    commercialModels,
    commercialModelsRefetch,
  } = useFetchCommercialModels(brandId, segmentId);

  const { isBatteriesLoading, batteriesRefetch } = useFetchBatteries(
    "commercialv",
    modelId,
    null
  );

  const handleFetchtSegemnts = (newBrand) => {
    setBrand(newBrand);
    setBrandId(newBrand.brandId);
  };

  const handleFetchModels = (newSegment) => {
    setSegmentId(newSegment.segmentId);
    setSegment(newSegment);
  };

  const handleSetModel = (newModel) => {
    setModel(newModel);
    setModelId(newModel.modelId);
  };

  const handleSetBatteryList = (e) => {
    e.preventDefault();
    batteriesRefetch();
    setSearched(true);
    if (scrollToRef.current) {
      window.scrollTo({
        behavior: "smooth",
        top: scrollToRef.current.offsetTop,
      });
    }
  };

  useEffect(() => {
    segmentId && commercialModelsRefetch();
  }, [segmentId]);

  useEffect(() => {
    brandId && segmentsRefetch();
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
                setSelected={handleFetchtSegemnts}
                placeholder={"brand Name"}
                isLoading={false}
              />
            </div>
            <div>
              <label className=" text-gray-500 text-sm tracking-wider">
                Segment
              </label>
              <CustomSelect
                lists={segments}
                selected={segment}
                setSelected={handleFetchModels}
                placeholder={"segment Name"}
                isLoading={isSegmentsLoading}
              />
            </div>
            <div>
              <label className=" text-gray-500 text-sm tracking-wider">
                Model
              </label>
              <CustomSelect
                lists={commercialModels}
                selected={model}
                setSelected={handleSetModel}
                placeholder={"model Name"}
                isLoading={isCommerialModelsLoading}
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
        {isBatteriesLoading ? (
          <div>Loading...</div>
        ) : (
          commercialVProductList.length > 0 ? (
            <ProductList
              products={commercialVProductList}
              model={model.modelName}
              productType={"batteries"}
            />
          ) : searched && <div>No Batteries Found!!</div>
        )}
      </div>
    </section>
  );
};
export default CommercialVehicleSearch;
