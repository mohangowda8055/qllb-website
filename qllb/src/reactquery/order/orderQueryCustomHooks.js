import { useMutation, useQuery } from "@tanstack/react-query";
import { useDispatch } from "react-redux";
import customFetch from "../../axios/custom";
import {
  setOrder,
  setOrders,
  setSavedAddresses,
} from "../../features/order/orderSlice";
import { useNavigate } from "react-router-dom";

export const useAddress = (user) => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { isLoading, data, isError, error } = useQuery({
    queryKey: ["address", user],
    queryFn: async () => {
      const data = await customFetch.get(
        `/user/users/${user.userId}/addresses`,
        {
          headers: {
            Authorization: `Bearer ${user.token}`,
          },
        }
      );
      dispatch(setSavedAddresses(data?.data?.data));
      return data?.data?.data || null;
    },
  });
  if (isError) {
    if (error?.response?.data?.message == "Token Expired") {
      navigate("/login?sessionExpired=true");
    }
  }
  return { data, isLoading };
};

const addAddress = async ({ address, user }) => {
  const response = await customFetch.post(
    `user/users/${user.userId}/addresses`,
    address,
    {
      headers: {
        Authorization: `Bearer ${user.token}`,
      },
    }
  );
};

export const useCreateAddress = () => {
  const navigate = useNavigate();
  const {
    mutate: createAddress,
    isLoading,
    isError,
    isPending,
  } = useMutation({
    mutationFn: addAddress,
    onError: (error) => {
      if (error?.response?.data?.message == "Token Expired") {
        navigate("/login?sessionExpired=true");
      }
    },
  });
  return { createAddress, isLoading, isError, isPending };
};

const addOrder = async ({ order, user }) => {
  const response = await customFetch.post(
    `/order/users/${user.userId}/orders`,
    order,
    {
      headers: {
        Authorization: `Bearer ${user.token}`,
      },
    }
  );
  return response;
};

export const useCreateOrder = () => {
  const navigate = useNavigate();
  const {
    mutate: createOrder,
    isPending,
    data,
  } = useMutation({
    mutationFn: addOrder,
    onError: (error) => {
      if (error?.response?.data?.message == "Token Expired") {
        navigate("/login?sessionExpired=true");
      }
    },
  });
  return { createOrder, isPending, data };
};

const addPayment = async ({ orderId, user }) => {
  const response = await customFetch.post(
    `/order/orders/${orderId}/payments`,
    {},
    {
      headers: {
        Authorization: `Bearer ${user.token}`,
      },
    }
  );
  return response;
};

export const useCreatePayment = () => {
  const navigate = useNavigate();
  const {
    mutate: createPayment,
    isPending,
    data,
    isError,
  } = useMutation({
    mutationFn: addPayment,
    onError: (error) => {
      if (error?.response?.data?.message == "Token Expired") {
        navigate("/login?sessionExpired=true");
      }
    },
  });
  return { createPayment, isPending, data, isError };
};

export const useUpdatePayment = ({ orderId, user, payment }) => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const {
    isLoading,
    data,
    isError,
    error,
    refetch: paymentRefetch,
  } = useQuery({
    queryKey: ["updatepayment", orderId, user, payment],
    queryFn: async () => {
      const data = await customFetch.post(
        `/order/orders/${orderId}/paymentdetails`,
        payment,
        {
          headers: {
            Authorization: `Bearer ${user.token}`,
          },
        }
      );
      dispatch(setOrder(data?.data?.data));
      return data?.data?.data || null;
    },
    enabled: false,
  });
  if (isError) {
    if (error?.response?.data?.message == "Token Expired") {
      navigate("/login?sessionExpired=true");
    }
  }
  return { data, isLoading, paymentRefetch };
};

export const useOrders = (user) => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { isLoading, data, isError, error } = useQuery({
    queryKey: ["orders", user],
    queryFn: async () => {
      const data = await customFetch.get(`/order/users/${user.userId}/orders`, {
        headers: {
          Authorization: `Bearer ${user.token}`,
        },
      });
      dispatch(setOrders(data?.data?.data));
      return data?.data?.data || null;
    },
    retry: false,
  });
  if (isError) {
    if (error?.response?.data?.message == "Token Expired") {
      navigate("/login?sessionExpired=true");
    }
  }
  return { data, isLoading };
};
