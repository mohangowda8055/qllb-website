const AddToCartImage = ({ product }) => {
  return (
    <div className="flex flex-col gap-2 sm:flex-row-reverse justify-center items-start p-2">
      <div className="bg-white p-2 border border-gray-300 shadow-lg">
        <img src={product?.imageMainUrl} alt="image" />
      </div>
      <div className="flex justify-center items-center gap-1 sm:flex-col">
        <div className="bg-white p-2 border border-gray-300 shadow-lg">
          <img
            src={product.imageOneUrl}
            alt="image"
            className="w-20 sm:w-28 "
          />
        </div>
        <div className="bg-white p-2 border border-gray-300 shadow-lg">
          <img
            src={product.imageTwoUrl}
            alt="image1"
            className="w-20 sm:w-28"
          />
        </div>
        <div className="bg-white p-2 border border-gray-300 shadow-lg">
          <img
            src={product.imageThreeUrl}
            alt="image2"
            className="w-20 sm:w-28"
          />
        </div>
      </div>
    </div>
  );
};
export default AddToCartImage;
