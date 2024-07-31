import { useState } from "react";
import Address from "../components/checkout/Address";
import OrderSummary from "../components/checkout/OrderSummary";
import ProgressBar from "../components/checkout/ProgressBar";
import { useNavigate } from "react-router-dom";

const CheckoutPage = () => {
  const navigate = useNavigate()
  const [step, setStep] = useState(0);

  const nextStep = () => {
    setStep((prevStep) => Math.min(prevStep + 1, 2));
  };

  const prevStep = () => {
    setStep((prevStep) => Math.max(prevStep - 1, 0));
  };

  return (
    <section className=" bg-primary">
      <div className=" max-w-7xl m-auto">
        <ProgressBar step={step} />
        {step === 0 && <Address nextStep={nextStep} />}
        {step === 1 && <OrderSummary prevStep={prevStep} nextStep={nextStep} />}
        <div className="flex justify-between p-2">
          <button
            onClick={prevStep}
            disabled={step === 0}
            className="bg-gray-300 px-4 py-2 rounded transition duration-200 ease-in-out transform hover:scale-105 active:bg-secondary_light active:scale-95"
          >
            Previous Step
          </button>
          <button type="button"
          onClick={()=>navigate(-1)}
          className=" bg-secondary text-black font-medium py-1 px-2 shadow-lg uppercase transition duration-200 ease-in-out transform hover:bg-secondary_dark hover:scale-105 active:bg-secondary_light active:scale-95">Go Back To Cart</button>
        </div>
      </div>
    </section>
  );
};
export default CheckoutPage;
