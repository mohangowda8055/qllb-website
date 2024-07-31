import Carousel from "../components/homepage/Carousel";
import About from "../components/homepage/About";
import customFetch from "../axios/custom";
import { useQuery } from "@tanstack/react-query";
import CommercialVehicleSearch from "../components/commercialvehiclepage/CommercialVehicleSearch";

const searchCommercialVBrandQuery = () => {
  return {
    queryKey: ["commercialvbrands"],
    queryFn: async () => {
      const data = await customFetch.get("/commercialv/brands");
      return data.data.data;
    },
  };
};

export const loader = (queryClient) => async () => {
  await queryClient.ensureQueryData(searchCommercialVBrandQuery());
  return null;
};

const CommercialVehiclePage = () => {
  const data = useQuery(searchCommercialVBrandQuery());
  return (
    <div>
      <Carousel />
      <CommercialVehicleSearch brands={data.data} />
      <About />
    </div>
  );
};
export default CommercialVehiclePage;
