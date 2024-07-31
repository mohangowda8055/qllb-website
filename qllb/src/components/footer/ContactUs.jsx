import { useEffect, useRef } from "react";
import { BsTelephoneOutbound } from "react-icons/bs";
import { FaCircleChevronDown, FaCircleChevronUp } from "react-icons/fa6";
import { MdOutlineMail } from "react-icons/md";
import { TfiLocationPin } from "react-icons/tfi";
import { useDispatch } from "react-redux";
import { setIsContactCollapsed } from "../../features/user/userSlice";

const ContactUs = ({ contactCollapsed }) => {
  const footerContactRef = useRef(null);
  const dispatch = useDispatch();

  useEffect(() => {
    if (!contactCollapsed) {
      footerContactRef.current.scrollIntoView({ behavior: "smooth" });
    }
  }, [contactCollapsed]);
  return (
    <div className=" flex flex-col gap-2 max-h-screen">
      <div className="flex items-center justify-between gap-2 text-base font-semibold px-4 sm:hidden">
        <button
          className="text-black text-left w-full"
          onClick={() => dispatch(setIsContactCollapsed(!contactCollapsed))}
        >
          Contact Us{" "}
        </button>
        <button onClick={() => dispatch(setIsContactCollapsed(!contactCollapsed))}>
          {contactCollapsed ? <FaCircleChevronDown /> : <FaCircleChevronUp />}
        </button>
      </div>
      <div>
        <h1 className="hidden sm:block text-black pt-2 pb-1 text-medium font-semibold">
          Contact Us
        </h1>
        <div className="hidden sm:block border border-secondary w-2/5 mb-2"></div>
        <ul
          className={`max-h-screen list-none px-4 py-2 flex flex-col gap-2 font-medium text-sm text-gray-700 sm:text-black ${
            contactCollapsed ? "hidden sm:flex" : ""
          } sm:flex-col sm:gap-2 sm:px-0`}
        >
          <li>
            <a
              href="mailto:gagan@gmail.com"
              className="flex items-center gap-1"
            >
              <span>
                <MdOutlineMail size={24} className=" text-gray-800" />
              </span>{" "}
              <span>gagan@gmail.com</span>
            </a>
          </li>
          <li>
            <a
              href="tel:+91 998805624751"
              className="mt-2 mb-2 flex items-center gap-1"
            >
              <span className="pe-2">
                <BsTelephoneOutbound size={19} className=" text-gray-800" />
              </span>
              <span>+91 998805624751</span>
            </a>
          </li>
          <address className=" flex items-center gap-1">
            <span className=" self-start">
              <TfiLocationPin size={20} className=" text-gray-800" />
            </span>{" "}
            <div>
              <span>No. 399, Agrahara Dasarahalli,</span>
              <br /> <span>Magadi Main Road,</span> <br />{" "}
              <span>(near Veeresh Cinemas),</span>
              <br />{" "}
              <span ref={footerContactRef}>
                Bangalore <br />
                <span>PinCode: 560079</span>
              </span>
            </div>
          </address>
        </ul>
      </div>
    </div>
  );
};
export default ContactUs;
