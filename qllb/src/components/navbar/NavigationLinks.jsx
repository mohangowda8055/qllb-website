import { useSelector } from "react-redux";
import { Link } from "react-router-dom";
import FlyoutLink from "./FlyoutLink";
import FlyoutContent from "./FlyoutContent";

const NavigationLinks = () => {
  const { user } = useSelector((state) => state.userState);
  return (
    <div className="hidden sm:flex justify-center items-center gap-x-4">
      <Link
        to="/"
        className="text-neutral transition duration-200 ease-in-out transform hover:text-secondary_dark active:text-secondary active:scale-95"
      >
        Home
      </Link>
      <FlyoutLink
        className="text-neutral transition duration-200 ease-in-out transform hover:text-secondary_dark active:text-secondary active:scale-95"
        FlyoutContent={FlyoutContent}
      >
        Products
      </FlyoutLink>
      <a
        href="#whychoose"
        className="text-neutral transition duration-200 ease-in-out transform hover:text-secondary_dark active:text-secondary active:scale-95"
      >
        About
      </a>
      <a
        href="#contact"
        className="text-neutral transition duration-200 ease-in-out transform hover:text-secondary_dark active:text-secondary active:scale-95"
      >
        Contact
      </a>
      {user && (
        <Link
          to="/cart"
          className="text-neutral transition duration-200 ease-in-out transform hover:text-secondary_dark active:text-secondary active:scale-95"
        >
          Cart
        </Link>
      )}
      {user && (
        <Link
          to="/order"
          className="text-neutral transition duration-200 ease-in-out transform hover:text-secondary_dark active:text-secondary active:scale-95"
        >
          Orders
        </Link>
      )}
    </div>
  );
};
export default NavigationLinks;
