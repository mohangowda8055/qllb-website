import axios from "axios";

const customFetch = axios.create({
  baseURL: "https://ec2-43-204-116-202.ap-south-1.compute.amazonaws.com:8080/api/v1",
});

customFetch.interceptors.request.use(
  (request) => {
    request.headers["Accept"] = "application/json";
    return request;
  },
  (error) => {
    return Promise.reject(error);
  }
);

customFetch.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default customFetch;
