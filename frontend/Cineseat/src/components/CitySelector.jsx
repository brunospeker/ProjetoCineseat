import React from "react";
import styles from './CitySelector.module.css';

export default function CitySelector({ selectedCity, setSelectedCity }) {
  const cities = ["São Paulo", "Rio de Janeiro", "Belo Horizonte"];

  return (
    <select value={selectedCity} onChange={(e) => setSelectedCity(e.target.value)} className={styles.citySelect}>
      {cities.map((city) => (
        <option key={city} value={city}>{city}</option>
      ))}
    </select>
  );
}
