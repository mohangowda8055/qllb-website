const OldBattery = ({ isRebate, setIsRebate }) => {
  const handleCheckboxChange = (e) => {
    setIsRebate(e.target.checked);
  };
  return (
    <div className="p-2 sm:p-0">
      <input
        type="checkbox"
        checked={isRebate}
        onChange={handleCheckboxChange}
        className="bg-white border border-gray-300"
      />
      <span className=" text-sm font-medium pl-2">Old Battery Return</span>
    </div>
  );
};
export default OldBattery;
