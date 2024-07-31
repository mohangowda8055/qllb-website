import { FaBars } from "react-icons/fa";
import { Link } from "react-router-dom";
import logo from "../../assets/logo-no-background.svg";
import CartButton from "./CartButton";
import NavigationLinks from "./NavigationLinks";
import LoginButton from "./LoginButton";
import LogoutButton from "./LogoutButton";
import { useDispatch, useSelector } from "react-redux";
import { toggleSidebar } from "../../features/user/userSlice";

const Navbar = () => {
  const { user } = useSelector((state) => state.userState);
  const dispatch = useDispatch();
  return (
    <nav className=" bg-primary text-neutral font-medium text-xl p-4 fixed top-0 left-0 w-full z-10 shadow-lg">
      <div className=" flex-row max-w-7xl sm:mx-auto sm:px-4">
        <div className="flex justify-between">
          {/* Mobile Menu Button */}
          <div className="flex justify-start items-center">
            <button
              className="sm:hidden text-neutral focus:outline-none focus:text-neutral"
              onClick={() => dispatch(toggleSidebar())}
            >
              <FaBars size={26} />
            </button>
            {/* Logo */}
            <div className="flex-shrink-0 pl-2 sm:pl-0 pt-1">
              <Link to="/">
                <img src={logo} alt="logo" className="w-16 h-auto sm:w-24" />
              </Link>
            </div>
          </div>
          <NavigationLinks />
          <div className="flex items-center sm:space-x-4">
            <CartButton />
            {user ? (
              <div className=" hidden sm:block">
                <LogoutButton />
              </div>
            ) : (
              <div className=" hidden sm:block">
                <LoginButton />
              </div>
            )}
          </div>
        </div>
      </div>
    </nav>
  );
};
export default Navbar;
