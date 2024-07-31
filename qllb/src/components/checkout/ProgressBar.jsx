const ProgressBar = ({ step }) => {
  const steps = ["Address", "Order Summary", "Payment"];
  return (
    <div className="w-full flex justify-between items-center py-4 pt-6">
      {steps.map((label, index) => (
        <div key={index} className="flex-1">
          <div className={`relative flex items-center justify-center`}>
            <div
              className={`rounded-full h-10 w-10 flex items-center justify-center ${
                index <= step
                  ? "bg-blue-600 text-white"
                  : "bg-gray-300 text-gray-600"
              }`}
            >
              {index + 1}
            </div>
            {index < steps.length - 1 && (
              <div
                className={`absolute top-1/2 transform -translate-y-1/2 w-[71%] sm:w-11/12 left-[65.5%] sm:left-[54.5%] h-1 ${
                  index < step ? "bg-blue-600" : "bg-gray-300"
                }`}
              ></div>
            )}
          </div>
          <div
            className={`text-center mt-2 ${
              index <= step ? "text-blue-600" : "text-gray-600"
            }`}
          >
            {label}
          </div>
        </div>
      ))}
    </div>
  );
};
export default ProgressBar;
