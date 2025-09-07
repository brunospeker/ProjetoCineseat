import React from "react";
import CitySelector from "./CitySelector";
import styles from './Header.module.css';

export default function Header({ selectedCity, setSelectedCity }) {
  return (
    <header className={styles.header}>
      <h1 className={styles.title}>CineSeat</h1>
      <CitySelector selectedCity={selectedCity} setSelectedCity={setSelectedCity} />
    </header>
  );
}
