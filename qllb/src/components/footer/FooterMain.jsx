import { useState } from "react";
import FollowUs from "./FollowUs";
import Information from "./Information";
import ContactUs from "./ContactUs";
import { useSelector } from "react-redux";

const FooterMain = () => {
  const [infoCollapsed, setInfoCollapsed] = useState(true);
  // const [contactCollapsed, setContactCollapsed] = useState(true);
  const isContactCollapsed = useSelector(state => state.userState.isContactCollapsed);

  return (
    <footer className="bg-primary pb-10 sm:pb-0 ">
      <FollowUs />
      <div className="flex flex-col gap-2 text-lg sm:flex sm:flex-row sm:justify-around sm:item-center sm:gap-4 tracking-widest">
        <Information
          infoCollapsed={infoCollapsed}
          setInfoCollapsed={setInfoCollapsed}
        />
        <ContactUs
          contactCollapsed={isContactCollapsed}
        />
      </div>
    </footer>
  );
};
export default FooterMain;
