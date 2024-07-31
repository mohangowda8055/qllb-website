import { Outlet, useNavigation } from "react-router-dom";
import Loading from "../components/Loading";
import Header from "../components/Header";
import Navbar from "../components/navbar/Navbar";
import Footer from "../components/footer/Footer";
import ScrollToTop from "../components/ScrollToTop";
import Sidebar from "../components/sidebar/Sidebar";

const HomeLayout = () => {
  const navigation = useNavigation();
  const isPageLoading = navigation.state === "loading";
  return (
    <>
      <div className=" relative">
        <Header />
        <Navbar />
        <Sidebar />
        {isPageLoading ? (
          <Loading />
        ) : (
          <main className="mt-16 sm:mt-11 min-h-screen">
            <ScrollToTop />
            <Outlet />
          </main>
        )}
        <Footer />
      </div>
    </>
  );
};
export default HomeLayout;
