import { RouterProvider, createBrowserRouter } from "react-router-dom";
import {
  HomeLayout,
  ErrorPage,
  LandingPage,
  LoginPage,
  RegisterPage,
  TwoWheelerPage,
  AddToCartPage,
  CartPage,
  CheckoutPage,
  OrderPage,
  OrderDetailPage,
  PaymentSuccessPage,
  ThreeWheelerPage,
  FourWheelerPage,
  CommercialVehiclePage,
  InverterBatteryPage,
  InverterPage,
} from "./pages";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { ReactQueryDevtools } from "@tanstack/react-query-devtools";
import { loader as twovPageLoader } from "./pages/TwoWheelerPage";
import { loader as threevPageLoader } from "./pages/ThreeWheelerPage";
import { loader as fourvPageLoader } from "./pages/FourWheelerPage";
import { loader as commercialvPageLoader } from "./pages/CommercialVehiclePage";
import { loader as inverterbatteryPageLoader } from "./pages/InverterBatteryPage";
import { loader as inverterPageLoader } from "./pages/InverterPage";
import ScrollToTop from "./components/ScrollToTop";

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      staleTime: 1000 * 60 * 5,
    },
  },
});

const router = createBrowserRouter([
  {
    path: "/",
    element: <HomeLayout />,
    errorElement: <ErrorPage />,
    children: [
      {
        index: true,
        element: <LandingPage />,
      },
      {
        path: "/twov",
        element: <TwoWheelerPage />,
        loader: twovPageLoader(queryClient),
      },
      {
        path: "/threev",
        element: <ThreeWheelerPage />,
        loader: threevPageLoader(queryClient),
      },
      {
        path: "/fourv",
        element: <FourWheelerPage />,
        loader: fourvPageLoader(queryClient),
      },
      {
        path: "/commercialv",
        element: <CommercialVehiclePage />,
        loader: commercialvPageLoader(queryClient),
      },
      {
        path: "/inverterbattery",
        element: <InverterBatteryPage />,
        loader: inverterbatteryPageLoader(queryClient),
      },
      {
        path: "/inverter",
        element: <InverterPage />,
        loader: inverterPageLoader(queryClient),
      },
      {
        path: "/addtocart/:id",
        element: <AddToCartPage />,
      },
      {
        path: "/cart",
        element: <CartPage />,
      },
      {
        path: "/checkout",
        element: <CheckoutPage />,
      },
      {
        path: "/order",
        element: <OrderPage />,
      },
      {
        path: "/orderdetail",
        element: <OrderDetailPage />,
      },
      {
        path: "/payment/:orderId",
        element: <PaymentSuccessPage />,
      },
    ],
  },
  {
    path: "/login",
    element: <LoginPage />,
  },
  {
    path: "/register",
    element: <RegisterPage />,
  },
]);

const App = () => {
  return (
    <QueryClientProvider client={queryClient}>
      <RouterProvider router={router}>
        <ScrollToTop />
      </RouterProvider>
      <ReactQueryDevtools initialIsOpen={false} />
    </QueryClientProvider>
  );
};

export default App;
