import { FaUserAlt } from "react-icons/fa";
import { Link } from "react-router-dom";

const LoginButton = () => {
  return (
    <div className="flex justify-center items-center">
      <Link to="/login" className="text-neutral">
        <div className="flex justify-between items-center">
          <div>
            <FaUserAlt size={20} />
          </div>
          <div className=" pl-2">Login</div>
        </div>
      </Link>
    </div>
  );
};
export default LoginButton;
