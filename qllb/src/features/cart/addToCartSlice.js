import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  cartItem: null,
};

const addToCartSlice = createSlice({
  name: "addtocart",
  initialState,
  reducers: {
    setCartItem: (state, action) => {
      state.cartItem = action.payload;
    },
  },
});

export const { setCartItem } = addToCartSlice.actions;
export default addToCartSlice.reducer;
