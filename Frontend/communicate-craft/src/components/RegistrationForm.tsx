// src/components/RegistrationForm.tsx
import React, { useState } from "react";
import { postLocation, registerUser } from "../services/UserService";

const RegistrationForm: React.FC = () => {
  const [formData, setFormData] = useState({
    username: "",
    firstName: "",
    lastName: "",
    email: "",
    password: "",
    phoneNumber: "",
    locationId: null,
    city: "",
    state: "",
    country: "",
  });

  const handleLocationSubmit = async () => {
    try {
      const locationData = {
        cityName: formData.city,
        stateName: formData.state,
        countryName: formData.country,
      };
      const locationResponse = await postLocation(locationData);
      console.log("zffffffftttt");
      console.log(locationResponse.locationId);
      setFormData((prevState) => ({
        ...prevState,
        locationId: locationResponse.locationId,
      }));
    } catch (error: any) {
      console.error("Location error:", error.response?.data || error.message);
    }
  };

  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = event.target;
    setFormData((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    await handleLocationSubmit();
    if (!formData.locationId) {
      console.error(
        "No locationId available. Cannot proceed with registration."
      );
      return;
    }
    try {
      const response = await registerUser(formData);
      console.log("Registration successful!", response.data);
      // Handle post-registration logic here, like redirecting the user
    } catch (error: any) {
      console.error(
        "Registration error:",
        error.response?.data || error.message
      );
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <input
        type="text"
        name="city"
        value={formData.city}
        onChange={handleInputChange}
        placeholder="City"
        required
      />
      <input
        type="text"
        name="state"
        value={formData.state}
        onChange={handleInputChange}
        placeholder="State"
        required
      />
      <input
        type="text"
        name="country"
        value={formData.country}
        onChange={handleInputChange}
        placeholder="Country"
        required
      />
      <input
        type="text"
        name="username"
        value={formData.username}
        onChange={handleInputChange}
        placeholder="Username"
        required
      />
      <input
        type="text"
        name="firstName"
        value={formData.firstName}
        onChange={handleInputChange}
        placeholder="First Name"
        required
      />
      <input
        type="text"
        name="lastName"
        value={formData.lastName}
        onChange={handleInputChange}
        placeholder="Last Name"
        required
      />
      <input
        type="email"
        name="email"
        value={formData.email}
        onChange={handleInputChange}
        placeholder="Email"
        required
      />
      <input
        type="password"
        name="password"
        value={formData.password}
        onChange={handleInputChange}
        placeholder="Password"
        required
      />
      <input
        type="tel"
        name="phoneNumber"
        value={formData.phoneNumber}
        onChange={handleInputChange}
        placeholder="Phone Number"
        required
      />
      {/* Include locationId selection logic if needed */}
      <button type="submit">Register</button>
    </form>
  );
};

export default RegistrationForm;
