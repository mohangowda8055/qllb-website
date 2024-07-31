import Carousel from "../components/homepage/Carousel";
import About from "../components/homepage/About";
import customFetch from "../axios/custom";
import { useQuery } from "@tanstack/react-query";
import ThreeWheelerSearch from "../components/threewheelerpage/ThreeWheelerSearch";

const searchThreeVBrandQuery = () => {
  return {
    queryKey: ["threevbrands"],
    queryFn: async () => {
      const data = await customFetch.get("/threev/brands");
      return data.data.data;
    },
  };
};

export const loader = (queryClient) => async () => {
  await queryClient.ensureQueryData(searchThreeVBrandQuery());
  return null;
};

const ThreeWheelerPage = () => {
  const data = useQuery(searchThreeVBrandQuery());
  return (
    <div>
      <Carousel />
      <ThreeWheelerSearch brands={data.data} />
      <About />
    </div>
  );
};
export default ThreeWheelerPage;
