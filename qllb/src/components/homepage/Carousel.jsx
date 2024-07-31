import { useEffect, useState } from "react";
import slide1 from "../../assets/slide1.jpg";
import slide2 from "../../assets/slide2.jpg";
import slide3 from "../../assets/slide3.jpg";
import { FaArrowCircleLeft, FaArrowCircleRight } from "react-icons/fa";

const Carousel = () => {
  const [currentSlide, setCurrentSlide] = useState(0);

  const slides = [slide1, slide2, slide3];
  const delay = 3000;

  useEffect(() => {
    const timer = setInterval(() => {
      setCurrentSlide((prevSlide) => (prevSlide + 1) % slides.length);
    }, delay);
    return () => clearInterval(timer);
  }, []);

  const handlePrevClick = () => {
    setCurrentSlide(
      (prevSlide) => (prevSlide - 1 + slides.length) % slides.length
    );
  };

  const handleNextClick = () => {
    setCurrentSlide((prevSlide) => (prevSlide + 1) % slides.length);
  };

  const handleDotClick = (index) => {
    setCurrentSlide(index);
  };
  return (
    <div className="relative">
      <div
        key={currentSlide}
        id="carousel"
        className=" h-56 md:mx-auto md:w-full md:h-96"
      >
        <img
          src={slides[currentSlide]}
          alt={`slide-${currentSlide + 1}`}
          className="w-full h-full md:mx-auto md:w-full md:h-96"
        />
      </div>

      <button
        onClick={handlePrevClick}
        className="absolute left-0 top-1/2 transform -translate-y-1/2 text-white p-2 sm:text-2xl"
      >
        <FaArrowCircleLeft color="black" />
      </button>
      <button
        onClick={handleNextClick}
        className="absolute right-0 top-1/2 transform -translate-y-1/2 text-white p-2 sm:text-2xl"
      >
        <FaArrowCircleRight color="black" />
      </button>

      <div className="absolute bottom-0 left-1/2 transform -translate-x-1/2 flex space-x-2 p-2">
        {slides.map((_, index) => (
          <button
            key={index}
            onClick={() => handleDotClick(index)}
            className={`w-2.5 h-2.5 md:w-3 md:h-3 rounded-full ${
              currentSlide === index ? "bg-black" : "bg-gray-400"
            }`}
          />
        ))}
      </div>
    </div>
  );
};
export default Carousel;
