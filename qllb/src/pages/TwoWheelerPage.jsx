import Carousel from "../components/homepage/Carousel";
import TwoWheelerSearch from "../components/twowheelerpage/TwoWheelerSearch";
import About from "../components/homepage/About";
import customFetch from "../axios/custom";
import { useQuery } from "@tanstack/react-query";

const searchTwoVBrandQuery = () => {
  return {
    queryKey: ["twovbrands"],
    queryFn: async () => {
      const data = await customFetch.get("/twov/brands");
      return data.data.data;
    },
  };
};

export const loader = (queryClient) => async () => {
  await queryClient.ensureQueryData(searchTwoVBrandQuery());
  return null;
};

const TwoWheelerPage = () => {
  const data = useQuery(searchTwoVBrandQuery());
  return (
    <>
      <div>
        <Carousel />
        <TwoWheelerSearch brands={data.data} />
        <About />
      </div>
    </>
  );
};
export default TwoWheelerPage;
