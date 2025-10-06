import React, { useState } from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";

import Home from "./pages/Home";
import Login from "./pages/Login";

export default function App() {
  const [darkMode, setDarkMode] = useState(true);

  return (
    <BrowserRouter>
      <div className={darkMode ? "min-h-screen bg-black text-white" : "min-h-screen bg-white text-black"}>
        <Routes>

          <Route path="/" element={<Home darkMode={darkMode} setDarkMode={setDarkMode} />} />
          
          <Route path="/login" element={<Login />} />

        </Routes>
      </div>
    </BrowserRouter>
  );
}
