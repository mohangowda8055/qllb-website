const CustomModal = ({ isModalOpen, setIsModalOpen, text }) => {
  return (
    isModalOpen && (
      <div
        className=" fixed inset-0 bg-black bg-opacity-30 backdrop-blur-sm flex justify-center items-center"
        onClick={() => setIsModalOpen(false)}
      >
        <div className="bg-white p-6 rounded-xl shadow transition-all scale-100 opacity-100">
          <button
            className=" absolute top-2 right-2 p-1 rounded-lg text-gray-400 bg-white hover:bg-gray-50 hover:text-gray-600"
            onClick={() => setIsModalOpen(false)}
          >
            {" "}
            X
          </button>
          <div className=" text-center w-56">
            <div className=" mx-auto my-4 w-48">
              <h3 className=" text-lg font-black text-gray-800">Message</h3>
              <p className=" text-sm text-gray-500">{text}</p>
            </div>
            <div className=" flex gap-4">
              <button
                className=" bg-red-500 text-white shadow rounded w-full hover:cursor-pointer"
                onClick={() => setIsModalOpen(false)}
              >
                Cancel
              </button>
            </div>
          </div>
        </div>
      </div>
    )
  );
};
export default CustomModal;
