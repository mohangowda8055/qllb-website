import { FaUserAlt } from "react-icons/fa";
import { useDispatch } from "react-redux";
import { Link } from "react-router-dom";
import { logoutUser } from "../../features/user/userSlice";

const LogoutButton = () => {
  const dispatch = useDispatch();
  return (
    <div className="flex justify-center items-center">
      <Link to="/" className="text-neutral">
        <div
          className="flex justify-between items-center"
          onClick={() => dispatch(logoutUser())}
        >
          <div>
            <FaUserAlt size={20} />
          </div>
          <div className=" pl-2">Logout</div>
        </div>
      </Link>
    </div>
  );
};
export default LogoutButton;
