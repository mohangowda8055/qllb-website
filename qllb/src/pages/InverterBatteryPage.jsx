import Carousel from "../components/homepage/Carousel";
import About from "../components/homepage/About";
import customFetch from "../axios/custom";
import { useQuery } from "@tanstack/react-query";
import InverterBatterySearch from "../components/inverterbatterypage/InverterBatterySearch";

const searchBackupDurationQuery = () => {
  return {
    queryKey: ["backupdurations"],
    queryFn: async () => {
      const data = await customFetch.get("/inverterbattery/backupdurations");
      return data.data.data;
    },
  };
};

export const loader = (queryClient) => async () => {
  await queryClient.ensureQueryData(searchBackupDurationQuery());
  return null;
};

const InverterBatteryPage = () => {
  const data = useQuery(searchBackupDurationQuery());
  return (
    <div>
      <Carousel />
      <InverterBatterySearch backupDurations={data.data} />
      <About />
    </div>
  );
};
export default InverterBatteryPage;
