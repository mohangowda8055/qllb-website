import logo from "../../assets/logo-white-no-background.svg";

const FooterTop = () => {
  return (
    <footer id="contact" className=" bg-black text-white">
      {/* Logo */}
      <div className=" bg-black pl-4 py-2 max-w-7xl mx-auto">
        <img src={logo} alt="Logo" className=" w-14 h-auto sm:w-24" />
      </div>
    </footer>
  );
};
export default FooterTop;
