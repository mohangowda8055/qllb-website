import { useState } from "react";
import { AiOutlineSearch } from "react-icons/ai";
import { BiChevronDown } from "react-icons/bi";
import Loading from "./Loading";

const CustomSelect = ({
  lists,
  selected,
  setSelected,
  placeholder,
  isLoading,
}) => {
  const [inputValue, setInputValue] = useState("");
  const [open, setOpen] = useState(false);
  const firstPart = placeholder.split(" ")[0];
  const fullPart = placeholder.split(" ").join("");
  return (
    <div className="relative pt-1">
      <div
        onClick={() => setOpen(!open)}
        className={`bg-primary p-1 mb-2 sm:max-w-80 sm:min-w-60 flex items-center justify-between border border-gray-300 rounded-md capitalize ${
          !selected && "text-black"
        }`}
      >
        {selected
          ? selected?.[fullPart]?.toString().length > 25
            ? selected?.[fullPart]?.toString().substring(0, 25) + "..."
            : selected[fullPart]
          : `Select ${firstPart}`}
        <span className=" flex justify-center items-center">
          <span>{isLoading && <Loading isLoading={isLoading} />}</span>
          <span>
            <BiChevronDown
              size={20}
              className={`${open && "rotate-180"}`}
              color={"Black"}
            />
          </span>
        </span>
      </div>
      <ul
        className={`absolute z-10 overflow-hidden bg-white border border-gray-300 rounded-md w-full py-2 mb-2 ${
          open ? "max-h-60" : "hidden"
        } overflow-y-auto `}
      >
        <div className="flex items-center px-2 bg-white gap-2 ">
          <div className="flex items-center gap-2 border border-gray-300 rounded-lg mb-2 w-11/12">
            <div className="">
              <AiOutlineSearch size={20} color={"Black"} />
            </div>
            <input
              type="text"
              value={inputValue}
              onChange={(e) => setInputValue(e.target.value)}
              placeholder={`Enter ${placeholder} `}
              className="placeholder:text-gray-700 placeholder:capitalize p-1 w-full "
            />
          </div>
        </div>
        {lists?.map((list) => (
          <li
            key={list?.[`${firstPart}Id`]}
            className={`p-2 px-3 text-sm border-t border-gray-300 hover:bg-gray-400 hover:text-white
            ${
              list?.[fullPart]?.toString().toLowerCase() ===
                selected?.[fullPart]?.toString().toLowerCase() &&
              "bg-sky-600 text-white"
            }
            ${
              list?.[fullPart]?.toString().toLowerCase().startsWith(inputValue)
                ? "block"
                : "hidden"
            }`}
            onClick={() => {
              if (
                list?.[fullPart]?.toString().toLowerCase() !==
                selected?.[fullPart]?.toString().toLowerCase()
              ) {
                setSelected(list);
                setOpen(false);
                setInputValue("");
              }
            }}
          >
            {list?.[fullPart]}
          </li>
        ))}
      </ul>
    </div>
  );
};
export default CustomSelect;
