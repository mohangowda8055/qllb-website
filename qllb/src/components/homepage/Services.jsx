import { serviceData } from "../../utils/data";
import { Link } from "react-router-dom";
import { useEffect } from "react";
import { motion, useAnimation } from "framer-motion";

const Services = () => {
  const controls = useAnimation();
  useEffect(() => {
    controls.start("visible");
  }, [controls]);

  return (
    <section
      id="services"
      className=" bg-white max-w-7xl mx-auto pt-3 md:pt-0 pb-8"
    >
      <div className=" capitalize pl-2 pt-4">
        <h1 className=" text-xl font-medium tracking-widest pb-1 ">
          Choose Your Vehicle
        </h1>
        <div className=" border-b-2 border-secondary border-solid w-40 content-none"></div>
      </div>
      <div>
        <motion.div
          className="grid grid-cols-3 sm:grid-cols-6"
          initial="hidden"
          animate={controls}
          variants={{
            visible: { opacity: 1, y: 0, transition: { staggerChildren: 0.2 } },
            hidden: { opacity: 0, y: 50 },
          }}
        >
          {serviceData.map(({ id, title, image, link }) => {
            return (
              <motion.div
                key={id}
                className=" pt-6 px-6 sm:px-8"
                variants={{
                  visible: { opacity: 1, y: 0 },
                  hidden: { opacity: 0, y: 20 },
                }}
              >
                <Link to={link}>
                  <div className="flex flex-col justify-center items-center">
                    <span className=" h-10">
                      <img
                        className=" w-auto h-auto max-h-10"
                        src={image}
                        alt={title}
                      />
                    </span>
                    <span className=" text-center font-medium sm:font-normal transition duration-200 ease-in-out transform hover:font-semibold hover:scale-105 active:text-secondary active:scale-95">
                      {title.split(" ")[0]} <br />{" "}
                      {title.split(" ").length > 1
                        ? title.split(" ").slice(1).join(" ")
                        : ""}
                    </span>
                  </div>
                </Link>
              </motion.div>
            );
          })}
        </motion.div>
      </div>
    </section>
  );
};
export default Services;
