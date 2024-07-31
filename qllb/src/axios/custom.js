import axios from "axios";

const customFetch = axios.create({
  baseURL: "http://localhost:8080/api/v1",
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
