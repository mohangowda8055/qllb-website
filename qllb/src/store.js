import { configureStore } from "@reduxjs/toolkit";
import userReducer from "./features/user/userSlice";
import cartReducer from "./features/cart/cartSlice";
import orderReducer from "./features/order/orderSlice";
import productReducer from "./features/product/productSlice";
import addToCartReducer from "./features/cart/addToCartSlice";

export const store = configureStore({
  reducer: {
    userState: userReducer,
    cartState: cartReducer,
    orderState: orderReducer,
    productState: productReducer,
    addToCartState: addToCartReducer,
  },
});
