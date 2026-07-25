import { createContext, useState } from "react";
import API from "../axios";

const AuthContext = createContext({
  token: null,
  username: null,
  role: null,
  isAuthenticated: false,
  isAdmin: false,
  login: async (username, password) => {},
  register: async (username, password) => {},
  logout: () => {},
});

export const AuthProvider = ({ children }) => {
  const [token, setToken] = useState(localStorage.getItem("token"));
  const [username, setUsername] = useState(localStorage.getItem("username"));
  const [role, setRole] = useState(localStorage.getItem("role"));

  const login = async (usernameInput, password) => {
    // Throws on failure — callers (Login.jsx) catch and display the message.
    const response = await API.post("/auth/login", {
      username: usernameInput,
      password,
    });
    const { token: newToken, role: newRole } = response.data;
    localStorage.setItem("token", newToken);
    localStorage.setItem("username", usernameInput);
    localStorage.setItem("role", newRole);
    setToken(newToken);
    setUsername(usernameInput);
    setRole(newRole);
    return response.data;
  };

  const register = async (usernameInput, password) => {
    const response = await API.post("/auth/register", {
      username: usernameInput,
      password,
    });
    return response.data;
  };

  const logout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("username");
    localStorage.removeItem("role");
    setToken(null);
    setUsername(null);
    setRole(null);
  };

  return (
    <AuthContext.Provider
      value={{
        token,
        username,
        role,
        isAuthenticated: !!token,
        isAdmin: role === "ADMIN",
        login,
        register,
        logout,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export default AuthContext;
