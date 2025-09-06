import React from "react";
import CitySelector from "./CitySelector";

export default function Header({ selectedCity, setSelectedCity }) {
  return (
    <header style={{ backgroundColor: "#1b3b8b", padding: "10px", color: "white", display: "flex", justifyContent: "space-between" }}>
      <h1>CineSeat</h1>
      <CitySelector selectedCity={selectedCity} setSelectedCity={setSelectedCity} />
    </header>
  );
}
