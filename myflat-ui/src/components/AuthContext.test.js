import {render, fireEvent} from '@testing-library/react';
import {AuthProvider, useAuth} from './AuthContext';
import React from "react";

const MockComponent = () => {
    const {user, login, logout} = useAuth();

    return (
        <div>
            <div>{user ? user.username : 'No User'}</div>
            <button onClick={() => login({username: 'testUser'})}>Login</button>
            <button onClick={() => logout()}>Logout</button>
        </div>
    );
};

describe('AuthProvider', () => {
    beforeEach(() => {
        localStorage.clear();
    });

    it('provides an auth context with no user initially', () => {
        const {getByText} = render(
            <AuthProvider>
                <MockComponent/>
            </AuthProvider>
        );

        expect(getByText('No User')).toBeInTheDocument();
    });

    it('provides an auth context with a user after login', () => {
        const {getByText} = render(
            <AuthProvider>
                <MockComponent/>
            </AuthProvider>
        );

        fireEvent.click(getByText('Login'));
        expect(getByText('testUser')).toBeInTheDocument();
    });

    it('provides an auth context with no user after logout', () => {
        const {getByText} = render(
            <AuthProvider>
                <MockComponent/>
            </AuthProvider>
        );

        fireEvent.click(getByText('Login'));
        fireEvent.click(getByText('Logout'));
        expect(getByText('No User')).toBeInTheDocument();
    });

    it('updates localStorage when the user state changes', () => {
        const {getByText} = render(
            <AuthProvider>
                <MockComponent/>
            </AuthProvider>
        );

        fireEvent.click(getByText('Login'));
        expect(localStorage.getItem('user')).toEqual(JSON.stringify({username: 'testUser'}));

        fireEvent.click(getByText('Logout'));
        expect(localStorage.getItem('user')).toBe("null");
    });

    it('initializes the user state from localStorage when the component is first rendered', () => {
        localStorage.setItem('user', JSON.stringify({username: 'testUser'}));

        const {getByText} = render(
            <AuthProvider>
                <MockComponent/>
            </AuthProvider>
        );

        expect(getByText('testUser')).toBeInTheDocument();
    });
});