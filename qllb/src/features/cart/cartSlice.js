import { createSlice } from "@reduxjs/toolkit";
import { toast } from "react-toastify";

const initialState = {
  cartId: null,
  cartItems: [],
  numItemsInCart: 0,
  cartTotal: 0,
  totalDiscount: 0,
  totalRebate: 0,
  totalMrp: 0,
  shipping: 0,
  grandTotal: 0,
  pincode: null,
  storedCartItems: [],
};

const cartSlice = createSlice({
  name: "cart",
  initialState,
  reducers: {
    setCart: (state, action) => {
      state.cartId = action.payload?.cartId;
      state.cartItems = action.payload?.cartItems;
      state.storedCartItems = action.payload?.cartItems;
      state.numItemsInCart = action.payload?.cartItems.length;
      state.cartTotal = action.payload?.total;
      state.shipping = action.payload?.deliveryCost;
      state.grandTotal = action.payload?.total + action.payload?.deliveryCost;
      state.pincode = action.payload?.pincode;
      cartSlice.caseReducers.calcualteTotal(state);
      cartSlice.caseReducers.calcualteGrandTotal(state);
    },
    editCartItemQty: (state, action) => {
      const id = action.payload.id;
      const newQty = action.payload.qty;
      const cartItem = state.cartItems.find(
        (item) => item.product.productId === id
      );
      if (cartItem) {
        newQty <= cartItem.product.stock && newQty > 0
          ? (cartItem.quantity = newQty)
          : toast.warning("quantity exceeded stock");
        cartSlice.caseReducers.calcualteTotal(state);
        cartSlice.caseReducers.calcualteGrandTotal(state);
      }
    },
    editCartItemIsRebate: (state, action) => {
      const id = action.payload.id;
      const isChecked = action.payload.isChecked;
      const cartItem = state.cartItems.find(
        (item) => item.product.productId === id
      );
      if (cartItem) {
        cartItem.isRebate = isChecked;
        cartSlice.caseReducers.calcualteTotal(state);
        cartSlice.caseReducers.calcualteGrandTotal(state);
      }
    },
    removeCartItem: (state, action) => {
      const productId = action.payload;
      state.cartItems = state.cartItems.filter(
        (item) => item.id.productId !== productId
      );
      state.numItemsInCart = state.cartItems.length;
      cartSlice.caseReducers.calcualteTotal(state);
      cartSlice.caseReducers.calcualteGrandTotal(state);
    },
    clearCart: (state) => {
      state = initialState;
    },
    calcualteTotal: (state) => {
      let total = 0;
      let totalMrp = 0;
      let totalDiscount = 0;
      let totalRebate = 0;
      if (state.cartItems?.length > 0) {
        state.cartItems.forEach(({ product, quantity, isRebate }) => {
          const mrp = product.mrp;
          const discount = product.discountPercentage;
          total +=
            quantity *
            (mrp * (1 - discount / 100) - (isRebate ? product.rebate : 0));
          totalMrp += mrp * quantity;
          totalDiscount += quantity * (mrp * (discount / 100));
          totalRebate += isRebate ? product.rebate * quantity : 0;
        });
      }
      state.cartTotal = total;
      state.totalDiscount = totalDiscount;
      state.totalRebate = totalRebate;
      state.totalMrp = totalMrp;
    },
    calcualteGrandTotal: (state) => {
      state.grandTotal = state.cartTotal + state.shipping;
    },
    updatePincode: (state, action) => {
      state.pincode = action.payload;
    },
  },
});
export const {
  setCart,
  editCartItemQty,
  editCartItemIsRebate,
  removeCartItem,
  clearCart,
  calcualteTotal,
  calcualteGrandTotal,
  updatePincode,
  setIsModified
} = cartSlice.actions;
export default cartSlice.reducer;
