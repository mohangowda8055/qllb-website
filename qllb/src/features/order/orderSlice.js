import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  orderId: null,
  savedAddresses: [],
  shippingAddress: null,
  order: null,
  orderItems: [],
  orders: [],
  isFromOrders: false,
};

const orderSlice = createSlice({
  name: "order",
  initialState,
  reducers: {
    setShippingAddress: (state, action) => {
      state.shippingAddress = action.payload;
    },
    setSavedAddresses: (state, action) => {
      state.savedAddresses = action.payload;
    },
    setOrderId: (state, action) => {
      state.orderId = action.payload;
    },
    setOrder: (state, action) => {
      state.order = action.payload;
      state.orderId = action.payload.orderId;
      state.orderItems = action.payload.orderItems;
    },
    setOrders: (state, action) => {
      state.orders = action.payload;
    },
    setIsFromOrders: (state, action) => {
      state.isFromOrders = action.payload;
    },
  },
});

export const {
  setShippingAddress,
  setSavedAddresses,
  setOrderId,
  setOrder,
  setOrders,
  setIsFromOrders,
} = orderSlice.actions;
export default orderSlice.reducer;
