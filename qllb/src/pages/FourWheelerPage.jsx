import Carousel from "../components/homepage/Carousel";
import About from "../components/homepage/About";
import customFetch from "../axios/custom";
import { useQuery } from "@tanstack/react-query";
import FourWheelerSearch from "../components/fourwheelerpage/FourWheelerSearch";

const searchFourVBrandQuery = () => {
  return {
    queryKey: ["fourvbrands"],
    queryFn: async () => {
      const data = await customFetch.get("/fourv/brands");
      return data.data.data;
    },
  };
};

export const loader = (queryClient) => async () => {
  await queryClient.ensureQueryData(searchFourVBrandQuery());
  return null;
};

const FourWheelerPage = () => {
  const data = useQuery(searchFourVBrandQuery());
  return (
    <div>
      <Carousel />
      <FourWheelerSearch brands={data.data} />
      <About />
    </div>
  );
};
export default FourWheelerPage;
