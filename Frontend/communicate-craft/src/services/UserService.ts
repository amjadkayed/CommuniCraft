// src/services/UserService.ts
import axios from "axios";

const API_BASE_URL = "http://localhost:1218/api/";

export const postLocation = async (locationData: {
  cityName: string;
  stateName: string;
  countryName: string;
}) => {
  const response = await axios.post(`${API_BASE_URL}public/locations`, locationData);
  return response.data; // This should include the locationId
};

export const registerUser = async (userData: {
  username: string;
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  phoneNumber: string;
  locationId: number | null; // Assuming the backend expects locationId to be a number.
}) => {
  const response = await axios.post(`${API_BASE_URL}auth/register`, {
    ...userData,
    password: userData.password, // Rename to passwordHash if necessary
  });
  return response.data;
};
