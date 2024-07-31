import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  product: null,
  twoVProductList: [],
  threeVProductList: [],
  fourVProductList: [],
  commercialVProductList: [],
  inverterBatteryProductList: [],
  inverterProductList: [],
  pincode: "",
};

const productSlice = createSlice({
  name: "product",
  initialState,
  reducers: {
    setProduct: (state, action) => {
      state.product = action.payload;
    },
    setTwoVProductList: (state, action) => {
      state.twoVProductList = action.payload;
    },
    setThreeVProductList: (state, action) => {
      state.threeVProductList = action.payload;
    },
    setFourVProductList: (state, action) => {
      state.fourVProductList = action.payload;
    },
    setCommercialVProductList: (state, action) => {
      state.commercialVProductList = action.payload;
    },
    setInverterBatteryProductList: (state, action) => {
      state.inverterBatteryProductList = action.payload;
    },
    setInverterProductList: (state, action) => {
      state.inverterProductList = action.payload;
    },
    setPincode: (state, action) => {
      state.pincode = action.payload;
    },
  },
});

export const {
  setProduct,
  setTwoVProductList,
  setThreeVProductList,
  setFourVProductList,
  setCommercialVProductList,
  setInverterBatteryProductList,
  setInverterProductList,
  setPincode,
} = productSlice.actions;

export default productSlice.reducer;
