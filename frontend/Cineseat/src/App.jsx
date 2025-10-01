import React, { useState } from "react";
import Home from "./pages/Home";

export default function App() {
  const [darkMode, setDarkMode] = useState(true);

  return (
    <div className={darkMode ? "min-h-screen bg-black text-white" : "min-h-screen bg-white text-black"}>
      <Home darkMode={darkMode} setDarkMode={setDarkMode} />
    </div>
  );
}
