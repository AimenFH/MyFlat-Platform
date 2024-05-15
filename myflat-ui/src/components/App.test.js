import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import LoginPage from './LoginPage'; // Pfad zu deiner LoginPage-Komponente anpassen
import { AuthContext } from './AuthContext'; // Pfad zum AuthContext anpassen

// Mock des AuthContext, um die login-Funktion zu simulieren
const mockLogin = jest.fn();

// Hilfsfunktion zum Rendern der LoginPage mit gemocktem AuthContext
const renderLoginPage = () =>
    render(
        <AuthContext.Provider value={{ login: mockLogin }}>
          <LoginPage />
        </AuthContext.Provider>
    );

describe('LoginPage', () => {
  beforeEach(() => {
    // Clear all information stored in jest.fn() instances
    mockLogin.mockClear();
  });
  it('should log in successfully when correct username and password are entered', () => {
    // Arrange
    const mockLogin = jest.fn();
    const mockWindowLocation = jest.spyOn(window.location, 'href', 'set');
    const mockEvent = { preventDefault: jest.fn() };
    const mockSetUsername = jest.fn(je);
    const mockSetPassword = jest.fn();

    // Act
    const { getByLabelText, getByText } = render(<LoginPage />);
    fireEvent.change(getByLabelText('Username:'), { target: { value: 'tenant' } });
    fireEvent.change(getByLabelText('Password:'), { target: { value: 'tenant' } });
    fireEvent.click(getByText('Login'));

    // Assert
    expect(mockEvent.preventDefault).toHaveBeenCalled();
    expect(mockLogin).toHaveBeenCalledWith({ username: 'tenant', role: 'tenant' });
    expect(mockWindowLocation).toHaveBeenCalledWith('/');
  });

});

