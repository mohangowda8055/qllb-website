import bike from "../assets/bike.png";
import car from "../assets/car.png";
import auto from "../assets/auto.png";
import inverter from "../assets/inverter.png";
import inverterBattery from "../assets/inverter-battery.png";
import truck from "../assets/truck.png";

export const serviceData = [
  {
    id: 1,
    title: "Two Wheeler",
    image: bike,
    link: "/twov",
  },
  {
    id: 2,
    title: "Four Wheeler",
    image: car,
    link: "/fourv",
  },
  {
    id: 3,
    title: "Three Wheeler",
    image: auto,
    link: "/threev",
  },
  {
    id: 4,
    title: "Commercial Vehicle",
    image: truck,
    link: "/commercialv",
  },
  {
    id: 5,
    title: "Inverter Battery",
    image: inverterBattery,
    link: "/inverterbattery",
  },
  {
    id: 6,
    title: "Home Inverter System",
    image: inverter,
    link: "/inverter",
  },
];

export const aboutData = [
  {
    id: 1,
    title: "Batteries",
    content:
      "Our top-of-the-line automotive batteries are engineered to deliver reliable power and exceptional performance for all types of vehicles. Experience longer-lasting power with our range of high-quality car batteries, designed to meet the demands of modern vehicles.Trust our durable and efficient automotive batteries to keep your vehicle running smoothly, no matter the conditions.",
  },
  {
    id: 2,
    title: "Inverter Batteries",
    content:
      "Our inverter batteries are crafted to provide uninterrupted power supply, ensuring your home or business stays powered even during outages. Enjoy seamless power backup with our robust and long-lasting inverter batteries, tailored to fit your specific needs. Choose from our selection of high-performance inverter batteries, designed to deliver reliable power for all your critical devices.",
  },
  {
    id: 3,
    title: "Inverters",
    content:
      "Stay powered up with our advanced inverters, offering efficient and reliable performance for both home and commercial use. Our range of inverters provides the perfect solution for your power needs, delivering consistent and clean energy when you need it most. Discover the power of our innovative inverters, engineered to ensure optimal performance and energy efficiency.",
  },
];

export const registerInputs = [
  {
    id: 1,
    name: "firstName",
    type: "text",
    errorMessage: "First name must be between 2 and 20 characters",
    placeholder: "Enter Your First Name",
    label: "First Name",
    pattern: "^[a-zA-Z]{3,20}$",
    required: true,
  },
  {
    id: 2,
    name: "lastName",
    type: "text",
    errorMessage: "Last name must be below 20 characters",
    placeholder: "Enter Your Last Name",
    pattern: "^[a-zA-Z]{0,20}$",
    label: "Last Name",
  },
  {
    id: 3,
    name: "phoneNumber",
    type: "text",
    errorMessage: "Phone number should be 10 digits",
    placeholder: "Enter Your Phone Number",
    label: "Phone Number",
    pattern: "^[0-9]{10}$",
    required: true,
  },
  {
    id: 4,
    name: "email",
    type: "email",
    errorMessage: "Please provide a valid email address",
    placeholder: "Enter Your Email",
    label: "Email",
  },
  {
    id: 5,
    name: "password",
    type: "password",
    errorMessage:
      "Password must contain at least 8 characters, one uppercase letter, one lowercase letter, one digit, and one special character",
    placeholder: "Enter Your Password",
    label: "Password",
    pattern:
      "^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,20}$",
    required: true,
  },
  {
    id: 6,
    name: "confirmPassword",
    type: "password",
    errorMessage: "Password didn't match",
    placeholder: "Confirm Password",
    label: "Confirm Password",
    required: true,
  },
];

export const loginInputs = [
  {
    id: 1,
    name: "phoneNumber",
    type: "text",
    errorMessage: "Phone number should be 10 digits",
    placeholder: "Enter Your Phone Number",
    label: "Phone Number",
    pattern: "^[0-9]{10}$",
    required: true,
  },
  {
    id: 2,
    name: "password",
    type: "password",
    errorMessage: "Password required",
    placeholder: "Enter Your Password",
    label: "Password",
    pattern: "^[a-zA-Z0-9!@#$%^&*]{0,100}$",
    required: true,
  },
];
