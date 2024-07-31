import { FaIndianRupeeSign } from "react-icons/fa6";
import { Link } from "react-router-dom";
import { useDispatch } from "react-redux";
import { setProduct } from "../features/product/productSlice";
import ProductDetail from "./ProductDetail";

const ProductList = ({ products, model, productType }) => {
  const dispatch = useDispatch();
  return (
    <div className="bg-primary p-0 md:px-4">
      <div className="max-w-7xl mx-auto">
        <div className="capitalize pl-2">
          <h1 className=" text-xl font-medium tracking-widest py-2 ">
            {`Suitable ${productType } ${model ? `for ${model}` : "" } ${
              productType === "inverter batteries" ? "months warranty" : ""
            }`}
          </h1>
          <div className=" border-b-2 border-secondary border-solid w-40 content-none"></div>
        </div>
        {!products ? (
          <>
            <div className="p-2 ">
              <h1>{`No ${productType} found`}</h1>
            </div>
          </>
        ) : (
          products?.map((product) => {
            const discount = product.mrp * (product.discountPercentage / 100);
            return (
              <div className="px-4" key={product.productId}>
                <div className="grid grid-cols-2">
                  <div></div>
                  <div className="flex flex-col items-end">
                    <div className="bg-white p-2 border border-gray-300 mt-4">
                    <img
                      src={product.imageMainUrl}
                      alt={product.brandName}
                      className=" w-44 h-44"
                    />
                    </div>
                    <p className="w-44 break-words pr-4 mt-2 text-sm font-medium text-center">
                      {product.productName}
                    </p>
                    <Link to={`/addtocart/${product.productId}`} className=" pr-5">
                      <button
                        className="bg-secondary text-white border border-secondary font-bold tracking-wider uppercase py-1 px-6 mt-4 mb-2 mr-4 shadow-lg transition duration-200 ease-in-out transform hover:bg-secondary_dark hover:scale-105 active:bg-secondary_light active:scale-95"
                        onClick={() => dispatch(setProduct(product))}
                      >
                        Select
                      </button>
                    </Link>
                  </div>
                </div>
                <div className="grid grid-cols-2 bg-stone-100 text-black mt-3">
                  <div className="flex items-center border border-gray-300 p-1">
                    <h1>Base Price (Inclusive of GST)</h1>
                  </div>
                  <div className="flex justify-center items-center border border-gray-300 p-1">
                    <FaIndianRupeeSign size={14} />
                    <h1>{product.mrp.toFixed(2)}</h1>
                  </div>
                  <div className="flex items-center border border-gray-300 p-1">
                    <h1>Special Discount </h1>
                  </div>
                  <div className="flex justify-center items-center border border-gray-300 p-1">
                    <FaIndianRupeeSign size={14} />
                    <h1>{discount.toFixed(2)}</h1>
                  </div>
                  <div className="flex items-center border border-gray-300 p-1">
                    <h1>Total Price (Inclusive of GST)</h1>
                  </div>
                  <div className="flex justify-center items-center border border-gray-300 p-1">
                    <FaIndianRupeeSign size={14} />
                    <h1>{(product.mrp - discount).toFixed(2)}</h1>
                  </div>
                </div>
                <div className="grid grid-cols-2 bg-stone-100 text-black mt-3">
                  <div className="flex justify-center items-center border border-gray-300 p-1">
                    <h1>Rebate on Return of old battery</h1>
                  </div>
                  <div className="flex flex-col items-center justify-center border border-gray-300 p-1">
                    <div className="flex items-center">
                      <FaIndianRupeeSign size={14} />
                      <h1>{product.rebate}</h1>
                    </div>
                    <p className="text-center text-sm">
                      <span>
                        *Additionally, rebate upto Rs {product.rebate} per unit
                        on return of similar old battery
                      </span>
                    </p>
                  </div>
                </div>
                <ProductDetail product={product} isCollapsed={true} />
              </div>
            );
          })
        )}
      </div>
    </div>
  );
};
export default ProductList;
