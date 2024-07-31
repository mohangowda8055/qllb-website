import Title from "../components/cart/Title";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import { useCart } from "../reactquery/cart/cartCustomHooks";
import Cart from "../components/cart/Cart";
import { useEffect } from "react";

const CartPage = () => {
  const navigate = useNavigate();
  const { user } = useSelector((state) => state.userState);
  if (!user) {
    toast.warn("You must be logged in to add to cart");
    return navigate("/login");
  }
  const { cartRefetch, isLoading } = useCart(user);

  const { cartItems } = useSelector((state) => state.cartState);

  useEffect(() => {
    if (user) {
      cartRefetch();
    }
  }, []);

  return (
    <>
      <section>
        <div className="bg-primary">
          <div className="max-w-7xl mx-auto">
            <div>
              <Title />
            </div>
            {isLoading ? (
              <div className=" min-h-96 flex justify-center items-center">
                <h1 className=" text-xl font-semibold">Loading...</h1>
              </div>
            ) : cartItems.length === 0 ? (
              <div className="min-h-96 flex flex-col justify-center items-center">
                <h1 className=" text-xl font-semibold">Cart is Empty</h1>
                <div className="pt-2">
                  <button
                    type="button"
                    className="bg-secondary text-black font-medium w-full p-2 shadow-lg uppercase transition duration-200 ease-in-out transform hover:bg-secondary_dark hover:scale-105 active:bg-secondary_light active:scale-95"
                    onClick={() => navigate(-1)}
                  >
                    Go Back
                  </button>
                </div>
              </div>
            ) : (
              <Cart />
            )}
          </div>
        </div>
      </section>
    </>
  );
};
export default CartPage;
