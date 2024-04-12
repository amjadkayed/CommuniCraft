import axios from "axios";
axios.defaults.baseURL = "http://localhost:1218/api";
axios.defaults.headers.post["Content-Type"] = "application/json";

export const request = (
  method: "get" | "post" | "put" | "delete" | "options",
  url: string,
  data?: any
) => {
  return axios({ method, url, data });
};
