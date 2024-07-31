import { useState } from "react";
import FormInput from "../components/FormInput";
import { registerInputs } from "../utils/data";
import { Link, useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import { useRegister } from "../reactquery/user/userQueryCustomHooks";

const RegisterPage = () => {
  const navigate = useNavigate();
  const { registerMutation, isPending } = useRegister();
  const [values, setValues] = useState({
    firstName: "",
    lastName: "",
    phoneNumber: "",
    email: "",
    password: "",
    confirmPassword: "",
  });

  const onValueChange = (e) => {
    setValues({ ...values, [e.target.name]: e.target.value });
  };

  const handleFormSubmit = (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);
    const data = Object.fromEntries(formData);

    registerMutation(
      {
        url: "/auth/register",
        data: data,
      },
      {
        onSuccess: () => {
          toast.success("Registered Successfully");
          navigate(-2);
        },
      }
    );
  };

  return (
    <section className="bg-primary ">
      <div className="flex justify-center items-center max-w-7xl mx-auto min-h-screen p-2">
        <div className="bg-primary sm:bg-white sm:border border-gray-300 p-2 w-full sm:w-fit">
          <div className="flex flex-col justify-center items-center pt-2 pb-6">
            <h1 className="text-xl font-bold tracking-wide">
              Create an account
            </h1>
            <p>
              Already have an account ?
              <Link to="/login" className=" text-blue-600 p-1">
                Login in here
              </Link>
            </p>
          </div>
          <div className="px-4">
            <form onSubmit={handleFormSubmit}>
              {registerInputs.map((input) => {
                const inputProp = {
                  ...input,
                  pattern:
                    input.name === "confirmPassword"
                      ? values.password.replace(/[\\^$.*+?()[\]{}|-]/g, "\\$&")
                      : input.pattern,
                };
                return (
                  <div className="py-2" key={input.id}>
                    <FormInput
                      {...inputProp}
                      value={values[input.name]}
                      onChange={onValueChange}
                    />
                  </div>
                );
              })}
              <div className=" pt-3">
                <button
                  type="submit"
                  disabled={isPending}
                  className="bg-secondary text-white font-medium w-full p-2 shadow-lg uppercase transition duration-200 ease-in-out transform hover:bg-secondary_dark hover:scale-105 active:bg-secondary_light active:scale-95"
                >
                  {isPending ? "submitting" : "submit"}
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
                className="bg-secondary text-white font-medium w-full p-2 shadow-lg uppercase transition duration-200 ease-in-out transform hover:bg-secondary_dark hover:scale-105 active:bg-secondary_light active:scale-95"
                onClick={() => navigate(-1)}
              >
                Go back
              </button>
            </div>
            <div className="flex justify-center items-center gap-1 text-lg">
              <span>Already have an account?</span>
              <Link to="/login" className=" text-blue-600">
                Login
              </Link>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
};
export default RegisterPage;
