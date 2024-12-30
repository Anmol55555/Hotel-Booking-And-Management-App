// Will be used as a guard for some endpoints.
// Like is the user is not logged in and tries to click the button to book a particular room,
// then this will first redirect the user to first login page
// and after successfull login, the user will be redirected to again the same book end point which he was clicking earlier

import React from "react";
import {Navigate, useLocation } from "react-router-dom";
import ApiService from "./ApiService";

export const ProtectedRoute = ({ element: Component }) => {
    const location = useLocation();
  
    return ApiService.isAuthenticated() ? (
      Component
    ) : (
      <Navigate to="/login" replace state={{ from: location }} />       // So after login, the user will be redirected to the exact location from where is has bee redirected to login page before
    );
  };
  
  
  export const AdminRoute = ({ element: Component }) => {
    const location = useLocation();
  
    return ApiService.isAdmin() ? (
      Component
    ) : (
      <Navigate to="/login" replace state={{ from: location }} />
    );
  };