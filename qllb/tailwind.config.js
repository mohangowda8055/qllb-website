/** @type {import('tailwindcss').Config} */
export default {
  content: ["./index.html", "./src/**/*.{js,ts,jsx,tsx}"],
  theme: {
    extend: {
      colors: {
        primary: "hsl(var(--color-primary))",
        secondary: "hsl(var(--color-secondary))",
        secondary_dark: "hsl(var(--color-secondary-dark))",
        secondary_light: "hsl(var(--color-secondary-light))",
        neutral: "hsl(var(--color-neutral))",
        content: "hsl(var(--color-content))",
      },
    },
  },
  plugins: [],
};
