import { BeatLoader } from "react-spinners";

const Loading = ({ isLoading }) => {
  return (
    <div>
      <BeatLoader loading={isLoading} color="#48e5c3" size={10} />
    </div>
  );
};
export default Loading;
