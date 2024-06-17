import React from 'react';
import {render, fireEvent} from '@testing-library/react';
import {AuthContext} from './AuthContext';
import LoginPage from './LoginPage';

const mockLogin = jest.fn();

const renderLoginPage = () => {
    return render(
        <AuthContext.Provider value={{login: mockLogin}}>
            <LoginPage/>
        </AuthContext.Provider>
    );
};

describe('LoginPage', () => {
    beforeEach(() => {
        mockLogin.mockClear();
    });

    it('should call the login function with the correct arguments when the form is submitted', () => {
        const {getByLabelText, getByText} = renderLoginPage();

        fireEvent.change(getByLabelText('Username:'), {target: {value: 'TENANT'}});
        fireEvent.change(getByLabelText('Password:'), {target: {value: 'TENANT'}});

        fireEvent.click(getByText('Login'));

        expect(mockLogin).toHaveBeenCalledWith({username: 'TENANT', role: 'TENANT'});
    });

    it('should call the login function with the correct arguments when the form is submitted with propmgmt credentials', () => {
        const {getByLabelText, getByText} = renderLoginPage();

        fireEvent.change(getByLabelText('Username:'), {target: {value: 'PROPERTY_MANAGEMENT'}});
        fireEvent.change(getByLabelText('Password:'), {target: {value: 'PROPERTY_MANAGEMENT'}});

        fireEvent.click(getByText('Login'));

        expect(mockLogin).toHaveBeenCalledWith({username: 'PROPERTY_MANAGEMENT', role: 'PROPERTY_MANAGEMENT'});
    });

    it('should not call the login function when the form is submitted with incorrect credentials', () => {
        const {getByLabelText, getByText} = renderLoginPage();

        fireEvent.change(getByLabelText('Username:'), {target: {value: 'wrong'}});
        fireEvent.change(getByLabelText('Password:'), {target: {value: 'wrong'}});

        fireEvent.click(getByText('Login'));

        expect(mockLogin).not.toHaveBeenCalled();
    });

    it('should show an alert when reset password button is clicked', () => {
        const { getByText } = renderLoginPage();

        const resetPasswordButton = getByText('Reset Password');
        window.alert = jest.fn();
        fireEvent.click(resetPasswordButton);

        expect(window.alert).toHaveBeenCalledWith('Reset Password functionality not implemented.');
    });
});