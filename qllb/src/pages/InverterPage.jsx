import Carousel from "../components/homepage/Carousel";
import About from "../components/homepage/About";
import customFetch from "../axios/custom";
import { useQuery } from "@tanstack/react-query";
import InverterSearch from "../components/inverterpage/InverterSearch";

const searchCapacityQuery = () => {
  return {
    queryKey: ["capacities"],
    queryFn: async () => {
      const data = await customFetch.get("/inverter/capacities");
      return data.data.data;
    },
  };
};

export const loader = (queryClient) => async () => {
  await queryClient.ensureQueryData(searchCapacityQuery());
  return null;
};

const InverterPage = () => {
  const data = useQuery(searchCapacityQuery());
  return (
    <div>
      <Carousel />
      <InverterSearch capacities={data.data} />
      <About />
    </div>
  );
};
export default InverterPage;
