import React from 'react';
import { render, screen } from '@testing-library/react';
import { useAuth } from './AuthContext';
import ProtectedRoute from './ProtectedRoute';
import { BrowserRouter as Router } from 'react-router-dom';

jest.mock('./AuthContext');

describe('ProtectedRoute', () => {
  it('redirects to login if user is not authenticated', () => {
    useAuth.mockReturnValue({ user: null });

    render(
      <Router>
        <ProtectedRoute allowedRoles={['tenant', 'propmgmt']}>
          <div>Protected content</div>
        </ProtectedRoute>
      </Router>
    );

    expect(screen.queryByText('Protected content')).toBeNull();
    expect(window.location.pathname).toEqual('/login');
  });

  it('renders children if user is authenticated and has correct role', () => {
    useAuth.mockReturnValue({ user: { role: 'tenant' } });

    render(
      <Router>
        <ProtectedRoute allowedRoles={['tenant', 'propmgmt']}>
          <div>Protected content</div>
        </ProtectedRoute>
      </Router>
    );

    expect(screen.getByText('Protected content')).toBeInTheDocument();
  });
});