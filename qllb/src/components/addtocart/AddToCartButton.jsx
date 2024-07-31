const AddToCartButton = ({ product, isSubmitting }) => {
  const isDisabled = product?.stock == 0;
  return (
    <div className="p-3 pt-1 sm:p-0">
      <button
        type="submit"
        className={`${
          isDisabled ? "bg-red" : "bg-secondary"
        } text-black  font-medium py-1 px-2 shadow-lg uppercase transition duration-200 ease-in-out transform hover:bg-secondary_dark hover:scale-105 active:bg-secondary_light active:scale-95`}
        disabled={isDisabled || isSubmitting}
      >
        {isSubmitting ? "Submitting" : "Add to cart"}
      </button>
    </div>
  );
};
export default AddToCartButton;
