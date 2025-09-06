import React from "react";

export default function CitySelector({ selectedCity, setSelectedCity }) {
  const cities = ["São Paulo", "Rio de Janeiro", "Belo Horizonte"];

  return (
    <select value={selectedCity} onChange={(e) => setSelectedCity(e.target.value)}>
      {cities.map((city) => (
        <option key={city} value={city}>{city}</option>
      ))}
    </select>
  );
}
