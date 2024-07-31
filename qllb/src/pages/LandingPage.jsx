import Carousel from "../components/homepage/Carousel";
import Services from "../components/homepage/Services";
import About from "../components/homepage/About";
import WhyChooseUs from "../components/homepage/WhyChooseUs";
import { useDispatch, useSelector } from "react-redux";
import { useCart } from "../reactquery/cart/cartCustomHooks";
import { setIsStart } from "../features/user/userSlice";

const LandingPage = () => {
  const dispatch = useDispatch()
  const { user, isStart } = useSelector((state) => state.userState);
  const { cartRefetch } = useCart(user);
    if (user && isStart) {
      cartRefetch();
      dispatch(setIsStart(false))
    }
  return (
    <>
      <Carousel />
      <Services />
      <WhyChooseUs />
      <About />
    </>
  );
};
export default LandingPage;
