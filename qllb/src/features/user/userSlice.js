import { createSlice } from "@reduxjs/toolkit";
import { toast } from "react-toastify";

const getUserFromLocalStorage = () => {
  return JSON.parse(localStorage.getItem("userQLLB")) || null;
};

const initialState = {
  user: getUserFromLocalStorage(),
  sessionExpiredFlag: true,
  isSidebarOpen: false,
  isStart: true,
  isContactCollapsed: true
};

const userSlice = createSlice({
  name: "user",
  initialState,
  reducers: {
    registerUser: (state, action) => {
      const user = {
        userId: action.payload.userId,
        token: action.payload.token,
      };
      state.user = user;
      localStorage.setItem("userQLLB", JSON.stringify(user));
    },
    loginUser: (state, action) => {
      const user = {
        userId: action.payload.userId,
        token: action.payload.token,
      };
      state.user = user;
      localStorage.setItem("userQLLB", JSON.stringify(user));
    },
    logoutUser: (state) => {
      state.user = null;
      localStorage.removeItem("userQLLB");
      toast.success("logged out successfully");
    },
    updateSessionExpiredFlag: (state, action) => {
      state.sessionExpiredFlag = action.payload;
    },
    toggleSidebar: (state) => {
      state.isSidebarOpen = !state.isSidebarOpen;
    },
    setIsStart: (state, action) => {
      state.isStart = action.payload;
    },
    setIsContactCollapsed: (state, action) => {
      state.isContactCollapsed = action.payload;
    }
  },
});

export const {
  registerUser,
  loginUser,
  logoutUser,
  updateSessionExpiredFlag,
  toggleSidebar,
  setIsStart,
  setIsContactCollapsed
} = userSlice.actions;

export default userSlice.reducer;
