// DarkModeContext.js
import React, { createContext, useContext, useState, useEffect } from 'react';

const DarkModeContext = createContext();

export const useDarkMode = () => {
    return useContext(DarkModeContext);
};

export const DarkModeProvider = ({ children }) => {
    const [isDarkMode, setIsDarkMode] = useState(() => {
        const savedMode = localStorage.getItem('darkMode');
        return savedMode ? JSON.parse(savedMode) : false;
    });

    const toggleDarkMode = () => {
        setIsDarkMode((prevMode) => !prevMode);
        console.log(isDarkMode)
    };

    useEffect(() => {
        if (isDarkMode) {
            document.documentElement.setAttribute('data-theme', 'dark');
            document.documentElement.style.setProperty('--bs-body-bg', '#181818'); // dark mode background color
            document.documentElement.style.setProperty('--bs-body-color', '#ffffff'); // dark mode text color
            document.documentElement.style.setProperty('--bs-gray', '#6c757d'); // dark mode gray color
            document.documentElement.style.setProperty('--bs-gray-dark', '#343a40'); // dark mode dark gray color
            document.documentElement.style.setProperty('--bs-secondary-bg', '#e9ecef'); // dark mode secondary background color
            document.documentElement.style.setProperty('--communication-page-bg', '#181818'); // dark mode background color
            document.documentElement.style.setProperty('--communication-page-shadow', 'rgba(255, 255, 255, 0.1)'); // dark mode box shadow
            document.documentElement.style.setProperty('--container-custom-bg', '#181818'); // dark mode background color
            document.documentElement.style.setProperty('--container-custom-shadow', 'rgba(255, 255, 255, 0.1)'); // dark mode box shadow
            document.documentElement.style.setProperty('--navbar-bg', '#181818'); // dark mode background color
            document.documentElement.style.setProperty('--navbar-shadow', 'rgba(255, 255, 255, 0.1)'); // dark mode box shadow
            document.documentElement.style.setProperty('--navbar-custom-bg', '#181818'); // dark mode background color
            document.documentElement.style.setProperty('--navbar-custom-shadow', 'rgba(255, 255, 255, 0.1)'); // dark mode box shadow

            // Add more lines here to update other CSS variables
        } else {
            document.documentElement.setAttribute('data-theme', 'light');
            document.documentElement.style.setProperty('--bs-body-bg', '#fff'); // light mode background color
            document.documentElement.style.setProperty('--bs-body-color', '#212529'); // light mode text color
            document.documentElement.style.setProperty('--bs-gray', '#6c757d'); // light mode gray color
            document.documentElement.style.setProperty('--bs-gray-dark', '#343a40'); // light mode dark gray color
            document.documentElement.style.setProperty('--bs-secondary-bg', '#e9ecef'); // light mode secondary background color
            document.documentElement.style.setProperty('--communication-page-bg', '#f5f5f5'); // light mode background color
            document.documentElement.style.setProperty('--communication-page-shadow', 'rgba(0, 0, 0, 0.1)'); // light mode box shadow

            document.documentElement.style.setProperty('--container-custom-bg', '#ffffff'); // light mode background color
            document.documentElement.style.setProperty('--container-custom-shadow', 'rgba(0, 0, 0, 0.1)'); // light mode box shadow

            document.documentElement.style.setProperty('--navbar-bg', '#ffffff'); // light mode background color
            document.documentElement.style.setProperty('--navbar-shadow', 'rgba(0, 0, 0, 0.1)'); // light mode box shadow
            document.documentElement.style.setProperty('--navbar-custom-bg', '#ffffff'); // light mode background color
            document.documentElement.style.setProperty('--navbar-custom-shadow', 'rgba(0, 0, 0, 0.1)'); // light mode box shado
            // Add more lines here to update other CSS variables
        }
        localStorage.setItem('darkMode', JSON.stringify(isDarkMode));
    }, [isDarkMode]);

    return (
        <DarkModeContext.Provider value={{ isDarkMode, toggleDarkMode }}>
            {children}
        </DarkModeContext.Provider>
    );
};
