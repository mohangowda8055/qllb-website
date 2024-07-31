import About from "../components/homepage/About";
import {
  AddToCartButton,
  AddToCartImage,
  AddToCartProductDetail,
  Services,
  PinCode,
  PriceCard,
  Quantity,
} from "../components/addtocart";
import { useDispatch, useSelector } from "react-redux";
import { useState } from "react";
import { setCartItem } from "../features/cart/addToCartSlice";
import {
  useCreateCartItem,
  useUpdateCartPincode,
} from "../reactquery/cart/cartCustomHooks";
import { useNavigate } from "react-router-dom";
import CustomModal from "../components/CustomModal";
import { MdArrowBack } from "react-icons/md";
import OldBattery from "../components/addtocart/OldBattery";
import ProductDetail from "../components/ProductDetail";
import InverterProductDetail from "../components/inverterpage/InverterProductDetail";
import { toast } from "react-toastify";

const AddToCartPage = () => {
  const navigate = useNavigate();
  const [isModalOpen, setIsModalOpen] = useState(false);
  const { product, pincode } = useSelector((state) => state.productState);
  const user = useSelector((state) => state.userState.user);
  const { createCartItem } = useCreateCartItem();
  const { updateCartPincode } = useUpdateCartPincode();
  const dispatch = useDispatch();
  const warranty = product.warranty || 0;
  const guarantee = product.guarantee || 0;

  const [quantity, setQuantity] = useState(1);
  const [isRebate, setIsRebate] = useState(true);
  const [isSubmitting, setIsSubmitting] = useState(false);

  const handleIncreaseQty = () => {
    if (quantity < product.stock) {
      const newQty = quantity + 1;
      setQuantity(newQty);
    }
  };

  const handleDecreaseQty = () => {
    if (quantity > 1) {
      const newQty = quantity - 1;
      setQuantity(newQty);
    }
  };

  const checkPincode = () => {
    const pincodes = [560059, 560070, 560057, 560060];
    const pin = pincodes.find((pin) => pin == pincode);
    return pin;
  };

  const handleAddToCart = (e) => {
    e.preventDefault();
    setIsSubmitting(true);
    const pin = checkPincode();
    if (pin) {
      const cartItem = {
        productId: product.productId,
        quantity: quantity,
        isRebate: isRebate,
      };
      dispatch(setCartItem(cartItem));
      if (!user) {
        setIsSubmitting(false);
        toast.warn("You must be logged in to add to cart");
        navigate("/login");
      }
      if (user) {
        updateCartPincode(
          { data: { pincode: pincode }, user },
          {
            onSuccess: () => {
              createCartItem({ cartItem, user },
                {
                  onSuccess: () =>{
                    setIsSubmitting(false)
                    navigate("/cart");
                  },
                  onError: (error) => {
                    if (error?.response?.data?.message == "Token Expired") {
                      navigate("/login?sessionExpired=true");
                    }
                  }
                }
              );
            },
          },
          {
            onError: () => {
              setIsSubmitting(false);
            }
          }
        );
      }
    } else {
      setIsModalOpen(true);
      setIsSubmitting(false);
    }
  };
  return (
    <>
      <div className="flex items-center px-4 py-2 bg-black text-white ">
        <button type="button" onClick={() => navigate(-1)}>
          <MdArrowBack size={20} className=" hover:cursor-pointer" />
        </button>{" "}
        <span className="pl-2 text-lg font-semibold">{product?.modelName}</span>
      </div>
      <div className="bg-primary">
        <div>
          <form onSubmit={handleAddToCart}>
            <div className="max-w-7xl mx-auto sm:flex">
              <AddToCartImage product={product} />
              <AddToCartProductDetail
                product={product}
                quantity={quantity}
                handleIncreaseQty={handleIncreaseQty}
                handleDecreaseQty={handleDecreaseQty}
                handleAddToCart={handleAddToCart}
                isRebate={isRebate}
                setIsRebate={setIsRebate}
              />
              <PriceCard product={product} />
            </div>
            <div className="flex flex-col gap-2 max-w-7xl mx-auto sm:hidden">
              <Quantity
                quantity={quantity}
                handleIncreaseQty={handleIncreaseQty}
                handleDecreaseQty={handleDecreaseQty}
                product={product}
              />
              <PinCode />
              {
                product.productType !== "INVERTER" && <OldBattery isRebate={isRebate} setIsRebate={setIsRebate} />
              }
              <AddToCartButton product={product} isSubmitting={isSubmitting} />
              <Services totalWarranty={warranty + guarantee} />
            </div>
            </form>
            <div className="max-w-7xl mx-auto mt-6 pb-4 px-3">
              <h1 className="tracking-wider font-semibold text-lg">
                Product Information
              </h1>
              {product?.productType === "INVERTER" ? (
                <InverterProductDetail product={product} isCollapsed={false} />
              ) : (
                <ProductDetail product={product} isCollapsed={false} />
              )}
            </div>
            <div className="hidden md:block">
              <About />
            </div>
          {/* </form> */}
        </div>
        <CustomModal
          isModalOpen={isModalOpen}
          setIsModalOpen={setIsModalOpen}
          text={
            "Our Service is not available at entered pincode !!, check for another pincode "
          }
        />
      </div>
    </>
  );
};
export default AddToCartPage;
