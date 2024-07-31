import { useRouteError, Link } from "react-router-dom";

const ErrorPage = () => {
  const error = useRouteError();
  console.log(error);
  if (error.status === 404) {
    return (
      <main className="grid min-h-screen place-items-center px-8">
        <div className="text-center">
          <p className="text-9xl font-semibold text-secondary">404</p>
          <h1 className="mt-4 text-3xl font-bold -tracking-tight sm:text-5xl">
            page not found
          </h1>
          <p className="mt-6 text-lg leading-7">
            Sorry, we couldn't find the page you're looking for
          </p>
          <div className="mt-10">
            <Link
              to="/"
              className="py-1 px-4 bg-secondary text-white tracking-wide"
            >
              Home Page
            </Link>
          </div>
        </div>
      </main>
    );
  }
  if (error.message === "Network Error") {
    return (
      <main className="grid min-h-screen place-items-center px-8">
        <div className="text-center">
          <p className="text-9xl font-semibold text-secondary">500</p>
          <h1 className="mt-4 text-3xl font-bold -tracking-tight sm:text-5xl">
            Server is down, Please try later
          </h1>
          <p className="mt-6 text-lg leading-7">
            Sorry, we couldn't find the page you're looking for
          </p>
          <div className="mt-10">
            <Link
              to="/"
              className="py-1 px-4 bg-secondary text-white tracking-wide transition duration-200 ease-in-out transform hover:bg-secondary_dark hover:scale-105 active:bg-secondary_light active:scale-95"
            >
              Home Page
            </Link>
          </div>
        </div>
      </main>
    );
  }
  return (
    <main className="grid min-h-screen place-items-center px-8">
      <h4 className="text-center font-bold text-4xl">There was an error...</h4>
    </main>
  );
};
export default ErrorPage;
