import { useDispatch } from "react-redux";
import {
  loginUser,
  registerUser,
  updateSessionExpiredFlag,
} from "../../features/user/userSlice";
import customFetch from "../../axios/custom";
import { useMutation } from "@tanstack/react-query";
import { toast } from "react-toastify";

export const useLogin = () => {
  const dispatch = useDispatch();
  const { mutate: loginMutation, isPending } = useMutation({
    mutationFn: async ({ url, data }) => {
      const { data: response } = await customFetch.post(url, data);
      console.log(response);
      dispatch(loginUser(response));
      dispatch(updateSessionExpiredFlag(true));
      return response || null;
    },
    onError: (error) =>{
      dispatch(updateSessionExpiredFlag(false));
          if (error.response.data.message === "Invalid Username or Password") {
            toast.error("Invalid Phone number or Password, try again!!");
          } else if(error.response.data.message === "Cannot login - user not found with phone number"){
            toast.error("Invalid Phone number, try again!!");
          }
    }
  });
  return { loginMutation, isPending };
};

export const useRegister = () => {
  const dispatch = useDispatch();
  const { mutate: registerMutation, isPending } = useMutation({
    mutationFn: async ({ url, data }) => {
      const { data: response } = await customFetch.post(url, data);
      dispatch(registerUser(response));
      return response || null;
    },
    onError: (error) =>{
      console.log(error);
          if (error.response.data.message === "An error occurred while saving user - Duplicate Entry detected") {
            toast.error("Phone number or Email already exists, try with another number or email !!");
          }
    }
  });
  return { registerMutation, isPending };
};
