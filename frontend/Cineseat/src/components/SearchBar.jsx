import React from "react";
import { Search } from "lucide-react";

export default function SearchBar({ darkMode }) {
  return (
    <div
      className={`flex items-center border rounded px-4 py-2 w-full max-w-xl mx-auto ${
        darkMode
          ? "border-white bg-black bg-opacity-40 text-white"
          : "border-black bg-white bg-opacity-60 text-black"
      }`}
    >
      <input
        type="text"
        placeholder="Buscar"
        className={`bg-transparent outline-none flex-1 ${
          darkMode ? "text-white placeholder-gray-300" : "text-black placeholder-gray-600"
        }`}
      />
      <Search className={darkMode ? "text-white" : "text-black"} />
    </div>
  );
}
