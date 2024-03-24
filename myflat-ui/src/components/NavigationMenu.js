import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from './AuthContext';
import { Navbar, Nav, Button, Container } from 'react-bootstrap';

function NavigationMenu() {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login'); // Redirect the user to the login page after logout
  };

  return (
    <Navbar style={{ backgroundColor: "#E3F2FD" }} expand="lg" className="mb-4 shadow">
      <Container>
        <Navbar.Brand as={Link} to="/" style={{ color: "#1976D2" }}>MyFlat Management</Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="me-auto">
            <Nav.Link as={Link} to="/" style={{ color: "#0D47A1" }}>Home</Nav.Link>
            {!user && <Nav.Link as={Link} to="/login" style={{ color: "#0D47A1" }}>Login</Nav.Link>}
            {user && (
              <>
                <Nav.Link as={Link} to="/communication" style={{ color: "#0D47A1" }}>Communication</Nav.Link>
                <Nav.Link as={Link} to="/documents" style={{ color: "#0D47A1" }}>Documents</Nav.Link>
                <Nav.Link as={Link} to="/maintenance" style={{ color: "#0D47A1" }}>Maintenance</Nav.Link>
                {user?.role === 'propmgmt' && <Nav.Link as={Link} to="/manageProperties" style={{ color: "#0D47A1" }}>Manage Properties</Nav.Link>}
                {user?.role === 'propmgmt' && <Nav.Link as={Link} to="/registerTenant" style={{ color: "#0D47A1" }}>Register Tenant</Nav.Link>}
              </>
            )}
          </Nav>
          {user && (
            <Button variant="outline-primary" onClick={handleLogout}>Logout</Button>
          )}
          <Navbar.Text className="ms-3">
            Logged in as: <strong>{user ? (user.role === 'propmgmt' ? 'Property Management' : 'Tenant') : 'N/A'}</strong>
          </Navbar.Text>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
}

export default NavigationMenu;
