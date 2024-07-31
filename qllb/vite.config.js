import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";
import { API_URL } from "./src/utils/api";

export default defineConfig({
  plugins: [react()],
});
