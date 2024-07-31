import { useDispatch, useSelector } from "react-redux";
import LogoutButton from "../navbar/LogoutButton";
import LoginButton from "../navbar/LoginButton";
import { MdClose } from "react-icons/md";
import { Link, useNavigate } from "react-router-dom";
import { setIsContactCollapsed, toggleSidebar } from "../../features/user/userSlice";
import { useState } from "react";
import { BiChevronDown } from "react-icons/bi";
import { serviceData } from "../../utils/data";

const Sidebar = () => {
  const navigate = useNavigate();
  const { user, isSidebarOpen } = useSelector((state) => state.userState);
  const [isOpen, setIsOpen] = useState(false);
  const dispatch = useDispatch();
  return (
    <aside
      className={` ${
        !isSidebarOpen && "hidden"
      } sm:hidden fixed top-0 left-0 h-screen w-11/12 transform translate-x-0 transition-transform duration-300 ease-in-out z-20 bg-green-300 `}
    >
      <nav className="py-2 px-4 h-full flex flex-col gap-2 bg-white border-r shadow-sm">
        <div className="flex justify-between items-center font-semibold py-2">
          {user ? <LogoutButton /> : <LoginButton />}
          <button
            className=" text-neutral focus:outline-none focus:text-neutral"
            onClick={() => dispatch(toggleSidebar())}
          >
            <MdClose size={20} color="red" />
          </button>
        </div>
        <div className=" border-t border-gray-400"></div>
        <div>
          <ul className=" divide-y divide-slate-200">
            <li className=" py-3" onClick={() => {
              dispatch(toggleSidebar());
              }}>
              <Link to="/">
              <p className=" w-full">Home</p></Link>
            </li>
            <li
              className={`py-4 ${isOpen && "pb-0"}`}
              onClick={() => setIsOpen(!isOpen)}
            >
              <div className={`flex justify-between ${isOpen && "pb-2"}`}>
                <span>Products</span>
                <span>
                  <BiChevronDown
                    size={20}
                    className={`transition-transform duration-300 ${
                      isOpen && "rotate-180"
                    }`}
                    color={"Black"}
                  />
                </span>
              </div>
              <div
                className={`${!isOpen && "hidden"} border-t border-slate-200`}
              ></div>
              <ul
                className={`overflow-hidden transition-max-height duration-500 ease-in-out ${
                  isOpen ? "max-h-screen" : "max-h-0"
                } divide-y divide-slate-200 `}
              >
                {serviceData.map((data) => {
                  return (
                    <li
                      className=" py-4 px-2 transition duration-200 ease-in-out transform hover:bg-secondary_dark hover:scale-[1.01] active:bg-secondary_light active:scale-95"
                      key={data.id}
                      onClick={() => dispatch(toggleSidebar())}
                    >
                      <Link to={data.link} >
                      <p className=" w-full">{data.title}</p></Link>
                    </li>
                  );
                })}
              </ul>
            </li>
            <li className=" py-4" onClick={() => dispatch(toggleSidebar())}>
              <a href="#whychoose"><p className=" w-full">About</p></a>
            </li>
            <li className=" py-4" onClick={() => {dispatch(toggleSidebar()), dispatch(setIsContactCollapsed(false))}}>
              <a href="#contact"><p className=" w-full">Contact</p></a>
            </li>
            {user && (
              <li className=" py-4" onClick={() => dispatch(toggleSidebar())}>
                <Link to="/cart"><p className=" w-full">Cart</p></Link>
              </li>
            )}
            {user && (
              <li className=" py-4" onClick={() => dispatch(toggleSidebar())}>
                <Link to="/order"><p className=" w-full">Orders</p></Link>
              </li>
            )}
          </ul>
        </div>
      </nav>
    </aside>
  );
};
export default Sidebar;
