import { Link } from "react-router-dom";

const Header = () => {
  return (
    <header className=" hidden sm:block bg-primary py-2 text-content">
      <div className="flex max-w-7xl justify-center sm:justify-end sm:mx-auto sm:px-4">
        <div className="flex gap-x-6 justify-center items-center">
          <Link to="/login" className=" text-xs sm:text-sm">
            Sign in
          </Link>
          <Link to="/register" className=" text-xs sm:text-sm">
            Create Account
          </Link>
        </div>
      </div>
    </header>
  );
};
export default Header;
