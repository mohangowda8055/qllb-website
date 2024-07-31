import {
  FaSquareFacebook,
  FaSquareInstagram,
  FaSquareXTwitter,
  FaSquareYoutube,
} from "react-icons/fa6";
import { Link } from "react-router-dom";

const FollowUs = () => {
  return (
    <div className="bg-gray-900 text-white flex justify-center items-center  py-4 px-2 gap-8 flex-wrap ">
      <h4 className=" tracking-widest ">Follow Us:</h4>
      <div className="flex items-center gap-10">
        <Link to="#">
          <FaSquareXTwitter size={20} color={"white"} />
        </Link>
        <Link to="#">
          <FaSquareFacebook size={20} color={"white"} />
        </Link>
        <Link to="#">
          <FaSquareInstagram size={20} color={"white"} />
        </Link>
        <Link to="#">
          <FaSquareYoutube size={20} color={"white"} />
        </Link>
      </div>
    </div>
  );
};
export default FollowUs;
