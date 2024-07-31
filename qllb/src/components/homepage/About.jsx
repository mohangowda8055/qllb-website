import { aboutData } from "../../utils/data";
import { motion, useAnimation } from "framer-motion";
const About = () => {
  const controls = useAnimation();

  return (
    <article id="about" className="bg-primary">
      <div className="max-w-7xl mx-auto py-8">
        <div className="flex flex-col justify-center items-center gap-1">
          <h1 className="text-3xl font-bold tracking-wide text-center">
            About QLLB
          </h1>
          <div className=" border-b-2 border-secondary border-solid w-40 content-none"></div>
        </div>
        {/* Cards Container */}
        <motion.div
          className="grid grid-cols-1 sm:grid-cols-3 gap-4 md:gap-6"
          initial="hidden"
          whileInView="visible"
          animate={controls}
          variants={{
            visible: { opacity: 1, y: 0, transition: { staggerChildren: 0.5 } },
            hidden: { opacity: 0, y: 50 },
          }}
        >
          {aboutData.map(({ id, title, content }) => {
            return (
              <motion.div
                key={id}
                className="bg-white rounded-lg shadow-lg m-6"
                variants={{
                  visible: { opacity: 1, y: 0 },
                  hidden: { opacity: 0, y: 20 },
                }}
              >
                <h3 className="text-xl font-semibold tracking-widest px-6 pt-6 pb-1">
                  {title}
                </h3>
                <div className=" border-b-2 border-secondary border-solid content-none mx-6"></div>
                <p className="text-gray-700 text-justify p-6">{content}</p>
              </motion.div>
            );
          })}
        </motion.div>
        <p className=" pl-2 text-xs text-center">This portfolio project utilizes images and content solely for illustrative purposes; all rights to the original materials belong to their respective copyright holders.</p>
      </div>
    </article>
  );
};
export default About;
