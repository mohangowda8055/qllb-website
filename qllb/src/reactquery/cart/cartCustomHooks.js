import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import customFetch from "../../axios/custom";
import { useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import { setCart } from "../../features/cart/cartSlice";

export const useCart = (user) => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const {
    isLoading,
    data,
    isError,
    error,
    refetch: cartRefetch,
  } = useQuery({
    queryKey: ["cart", user],
    queryFn: async () => {
      const data = await customFetch.get(`/cart/users/${user.userId}/carts`, {
        headers: {
          Authorization: `Bearer ${user.token}`,
        },
      });
      dispatch(setCart(data?.data?.data));
      return data?.data?.data || null;
    },
    enabled: false,
  });
  if (isError) {
    if (error?.response?.data?.message == "Token Expired") {
      navigate("/login?sessionExpired=true");
    }
  }
  return { data, isLoading, cartRefetch };
};

const addCartItem = async ({ cartItem, user }) => {
  const response = await customFetch.post(
    `/cart/carts/${user.userId}/products/${cartItem.productId}/cartitems`,
    { quantity: cartItem.quantity, isRebate: cartItem.isRebate },
    {
      headers: {
        Authorization: `Bearer ${user.token}`,
      },
    }
  );
};

export const useCreateCartItem = () => {
  const navigate = useNavigate();
  const {
    mutate: createCartItem,
    isLoading,
    isPending,
  } = useMutation({
    mutationFn: addCartItem,
  });
  return { createCartItem, isLoading, isPending };
};

const editCart = async ({ data, user }) => {
  const response = await customFetch.patch(
    `/cart/users/${user.userId}/carts`,
    {
      cartId: data.cartId,
      total: data.total,
      deliveryCost: data.deliveryCost,
      cartItems: data.cartItems,
    },
    {
      headers: {
        Authorization: `Bearer ${user.token}`,
      },
    }
  );
};

export const useUpdateCart = () => {
  const navigate = useNavigate();
  const queryClient = useQueryClient();
  const {
    mutate: updateCart,
    isLoading: updateCartLoading,
    isPending,
  } = useMutation({
    mutationFn: editCart,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["cart"] });
    },
    onError: (error) => {
      if (error?.response?.data?.message == "Token Expired") {
        navigate("/login?sessionExpired=true");
      }
    },
  });
  return { updateCart, updateCartLoading, isPending };
};

const updatePin = async ({ data, user }) => {
  const response = await customFetch.patch(
    `/cart/users/${user.userId}/pincodes/carts`,
    {
      pincode: data.pincode,
    },
    {
      headers: {
        Authorization: `Bearer ${user.token}`,
      },
    }
  );
};

export const useUpdateCartPincode = () => {
  const navigate = useNavigate();
  const { mutate: updateCartPincode, isPending } = useMutation({
    mutationFn: updatePin,
    onError: (error) => {
      if (error?.response?.data?.message == "Token Expired") {
        navigate("/login?sessionExpired=true");
      }
    },
  });
  return { updateCartPincode, isPending };
};

const delCartItem = async ({ cartId, productId, user }) => {
  const response = await customFetch.delete(
    `/cart/carts/${cartId}/products/${productId}/cartitems`,
    {
      headers: {
        Authorization: `Bearer ${user.token}`,
      },
    }
  );
};

export const useDeleteCartItem = () => {
  const navigate = useNavigate();
  const queryClient = useQueryClient();
  const { mutate: deleteCartItem, isLoading: deleteCartItemLoading } =
    useMutation({
      mutationFn: delCartItem,
      onSuccess: () => {
        queryClient.invalidateQueries({ queryKey: ["cart"] });
      },
      onError: (error) => {
        if (error?.response?.data?.message == "Token Expired") {
          navigate("/login?sessionExpired=true");
        }
      },
    });
  return { deleteCartItem, deleteCartItemLoading };
};

const delAllCartItem = async ({ cartId, user }) => {
  const response = await customFetch.delete(`/cart/carts/${cartId}/cartitems`, {
    headers: {
      Authorization: `Bearer ${user.token}`,
    },
  });
};

export const useDeleteAllCartItem = () => {
  const navigate = useNavigate();
  const queryClient = useQueryClient();
  const { mutate: deleteAllCartItem, isLoading: deleteAllCartItemLoading } =
    useMutation({
      mutationFn: delAllCartItem,
      onSuccess: () => {
        queryClient.invalidateQueries({ queryKey: ["cart"] });
      },
      onError: (error) => {
        if (error?.response?.data?.message == "Token Expired") {
          navigate("/login?sessionExpired=true");
        }
      },
    });
  return { deleteAllCartItem, deleteAllCartItemLoading };
};
