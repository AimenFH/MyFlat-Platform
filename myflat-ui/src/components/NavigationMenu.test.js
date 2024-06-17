import React from 'react';
import {render, fireEvent} from '@testing-library/react';
import {BrowserRouter as Router} from 'react-router-dom';
import {AuthContext} from './AuthContext';
import NavigationMenu from './NavigationMenu';

const mockLogout = jest.fn();

const renderNavigationMenu = (user) => {
    return render(
        <AuthContext.Provider value={{user, logout: mockLogout}}>
            <Router>
                <NavigationMenu/>
            </Router>
        </AuthContext.Provider>
    );
};

describe('NavigationMenu', () => {
    beforeEach(() => {
        mockLogout.mockClear();
    });

    it('should call the logout function when the logout button is clicked', () => {
        const user = {username: 'tenant', role: 'tenant'};
        const {getByText} = renderNavigationMenu(user);

        fireEvent.click(getByText('Logout'));

        expect(mockLogout).toHaveBeenCalled();
    });

    it('should display the Login link when no user is logged in', () => {
        const {getByText} = renderNavigationMenu(null);

        expect(getByText('Login')).toBeInTheDocument();
    });

    it('should display tenant links when a tenant is logged in', () => {
        const user = {username: 'tenant', role: 'tenant'};
        const {getByText} = renderNavigationMenu(user);

        expect(getByText('Communication')).toBeInTheDocument();
        expect(getByText('Documents')).toBeInTheDocument();
        expect(getByText('Maintenance')).toBeInTheDocument();
    });

    it('should display property management links when property management is logged in', () => {
        const user = {username: 'PROPERTY_MANAGEMENT', role: 'PROPERTY_MANAGEMENT'};
        const {getByText} = renderNavigationMenu(user);

        expect(getByText('Properties')).toBeInTheDocument();
        expect(getByText('Tenants')).toBeInTheDocument();
    });

    it('should display the logged-in info when a user is logged in', () => {
        const user = {username: 'tenant', role: 'tenant'};
        const {getByText} = renderNavigationMenu(user);

        expect(getByText(/Logged in as:/i)).toBeInTheDocument();
    });

    it('should not display the logged-in info when no user is logged in', () => {
        const {queryByText} = renderNavigationMenu(null);

        expect(queryByText(/Logged in as: Tenant/i)).not.toBeInTheDocument();
        expect(queryByText(/Logged in as: Property Management/i)).not.toBeInTheDocument();
    });
});