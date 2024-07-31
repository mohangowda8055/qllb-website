import React from 'react'
import { serviceData } from '../../utils/data';
import { Link } from 'react-router-dom';

const FlyoutContent = () => {
    return (
        <div className="w-72 bg-white p-6 shadow-xl">
          <div className="mb-3 ">
            <ul>
            {serviceData.map((data) => {
                  return (
                    <li
                      className=" p-2 transition duration-200 ease-in-out transform hover:bg-secondary active:bg-secondary_light active:scale-95"
                      key={data.id}
                    >
                      <Link to={data.link} >
                      <p className=" w-full">{data.title}</p></Link>
                    </li>
                  );
                })}
            </ul>
          </div>
        </div>
      );
}

export default FlyoutContent
