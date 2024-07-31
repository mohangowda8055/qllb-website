import { Link, useLocation, useNavigate } from "react-router-dom";
import { loginInputs } from "../utils/data";
import FormInput from "../components/FormInput";
import { useEffect, useState } from "react";
import { toast } from "react-toastify";
import { updateSessionExpiredFlag } from "../features/user/userSlice";
import { useLogin } from "../reactquery/user/userQueryCustomHooks";
import { useDispatch, useSelector } from "react-redux";

const LoginPage = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const dispatch = useDispatch();
  const { loginMutation, isPending } = useLogin();
  const [values, setValues] = useState({
    phoneNumber: "",
    password: "",
  });
  const { sessionExpiredFlag } = useSelector((state) => state.userState);

  const onValueChange = (e) => {
    setValues({ ...values, [e.target.name]: e.target.value });
  };

  const handleFormSubmit = (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);
    const data = Object.fromEntries(formData);

    loginMutation(
      {
        url: "/auth/authenticate",
        data: data,
      },
      {
        onSuccess: () => {
          dispatch(updateSessionExpiredFlag(false));
          toast.success("Login Successful");
          navigate(-1);
        },
      },
    );
  
  };

  useEffect(() => {
    const params = new URLSearchParams(location.search);
    const sessionExpired = params.get("sessionExpired");

    if (sessionExpired && sessionExpiredFlag) {
      toast.error("Session expired, please login again");
    }
  }, [location]);

  return (
    <section className="bg-primary ">
      <div className="flex justify-center items-center max-w-7xl mx-auto min-h-screen p-2">
        <div className="bg-primary sm:bg-white sm:border border-gray-300 p-2 w-full sm:w-fit">
          <div className="flex flex-col justify-center items-center pt-2 pb-6">
            <h1 className="text-xl font-bold tracking-wide">
              Hi, Welcome Back!
            </h1>
            <p>
              Dont't have an account ?
              <Link to="/register" className=" text-blue-600 p-1">
                Sign Up
              </Link>
            </p>
          </div>
          <div className="px-4">
            <form onSubmit={handleFormSubmit}>
              {loginInputs.map((input) => {
                return (
                  <div className="py-2" key={input.id}>
                    <FormInput
                      {...input}
                      value={values[input.name]}
                      onChange={onValueChange}
                    />
                  </div>
                );
              })}
              <div className=" pt-3">
                <button
                  type="submit"
                  className="bg-secondary text-white font-medium w-full p-2 shadow-lg uppercase transition duration-200 ease-in-out transform hover:bg-secondary_dark hover:scale-105 active:bg-secondary_light active:scale-95"
                  disabled={isPending}
                >
                  {isPending ? "logging in" : "login"}
                </button>
              </div>
            </form>
            <div className="flex justify-center items-center pt-6">
              <div className="border-b-2 border-black border-solid w-40 content-none"></div>
              <span className="px-2">Or</span>
              <div className="border-b-2 border-black border-solid w-40 content-none"></div>
            </div>
            <div className=" py-2">
              <button
                type="button"
                className="bg-secondary text-white font-medium w-full p-2 shadow-lg uppercase transition duration-200 ease-in-out transform hover:bg-secondary_dark hover:scale-105 active:bg-secondary_light active:scale-95"
                onClick={() => navigate(-1)}
              >
                Go back
              </button>
            </div>
            <div className="flex justify-center items-center gap-1 text-lg">
              <span>Dont't have an account?</span>
              <Link to="/register" className=" text-blue-600">
                Sign Up
              </Link>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
};
export default LoginPage;
