import { motion, useAnimation } from "framer-motion";
import { FaShippingFast } from "react-icons/fa";
import { FaTruck } from "react-icons/fa6";
import { MdHandyman, MdWorkspacePremium } from "react-icons/md";

const WhyChooseUs = () => {
  const controls = useAnimation();

  return (
    <section id="whychoose" className="bg-secondary pt-10 pb-8">
      <motion.div
        className="max-w-7xl mx-auto"
        initial="hidden"
        whileInView="visible"
        animate={controls}
        variants={{
          visible: {
            opacity: 1,
            scale: 1,
            transition: { staggerChildren: 0.2 },
          },
          hidden: { opacity: 0, scale: 0.8 },
        }}
      >
        <div className="flex justify-around flex-wrap font-medium">
          <motion.div
            className="flex flex-col  items-center pb-2 transition duration-200 ease-in-out transform hover:font-semibold hover:scale-105 active:text-secondary active:scale-95"
            variants={{
              visible: { opacity: 1, scale: 1 },
              hidden: { opacity: 0, scale: 0.8 },
            }}
          >
            <div className="bg-black rounded-full w-16 h-16 flex justify-center items-center">
              <FaTruck size={28} className="text-white" />
            </div>
            <span>Free</span>
            <span>Delivery</span>
          </motion.div>
          <motion.div
            className="flex flex-col  items-center transition duration-200 ease-in-out transform hover:font-semibold hover:scale-105 active:text-secondary active:scale-95"
            variants={{
              visible: { opacity: 1, scale: 1 },
              hidden: { opacity: 0, scale: 0.8 },
            }}
          >
            <div className="bg-black rounded-full w-16 h-16 flex justify-center items-center">
              <MdWorkspacePremium size={28} className="text-white" />
            </div>
            <span>Assured </span>
            <span>Warranty</span>
          </motion.div>
          <motion.div
            className="flex flex-col  items-center transition duration-200 ease-in-out transform hover:font-semibold hover:scale-105 active:text-secondary active:scale-95"
            variants={{
              visible: { opacity: 1, scale: 1 },
              hidden: { opacity: 0, scale: 0.8 },
            }}
          >
            <div className="bg-black rounded-full w-16 h-16 flex justify-center items-center">
              <MdHandyman size={28} className="text-white " />
            </div>
            <span>Free</span>
            <span>Installation</span>
          </motion.div>
          <motion.div
            className="flex flex-col  items-center transition duration-200 ease-in-out transform hover:font-semibold hover:scale-105 active:text-secondary active:scale-95"
            variants={{
              visible: { opacity: 1, scale: 1 },
              hidden: { opacity: 0, scale: 0.8 },
            }}
          >
            <div className="bg-black rounded-full w-16 h-16 flex justify-center items-center">
              <FaShippingFast size={28} className="text-white" />
            </div>
            <span>Fast</span>
            <span>Shipping</span>
          </motion.div>
        </div>
      </motion.div>
    </section>
  );
};
export default WhyChooseUs;
