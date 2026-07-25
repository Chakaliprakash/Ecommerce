import axios from "axios";

const API = axios.create({
  baseURL: "http://localhost:8080/api",
});

// Attach the JWT (if we have one) to every outgoing request automatically.
// Public GET endpoints ignore the header; protected endpoints (POST/PUT/DELETE
// on /product) require it — see backend SecurityConfig.
API.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// If a protected call comes back 401, the token is missing/expired — clear it
// so the UI falls back to "logged out" state. A 403 means the token is valid
// but the user's role isn't allowed to do this — don't log them out for that.
API.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response && error.response.status === 401) {
      localStorage.removeItem("token");
      localStorage.removeItem("username");
      localStorage.removeItem("role");
    }
    return Promise.reject(error);
  }
);

export default API;
