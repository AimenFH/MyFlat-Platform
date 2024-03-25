import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from './AuthContext'; // Ensure this path is correct
import { Navbar, Nav, Container, Button } from 'react-bootstrap';
import './styles/NavigationMenu.css'; // Ensure the path to your CSS file is correct

function NavigationMenu() {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login'); // Redirects the user to the login page after logout
  };

  return (
      <Navbar expand="lg" className="mb-4 shadow navbar-custom">
        <Container>
          <Navbar.Brand as={Link} to="/" className="navbar-brand">
            <img
                src="/myFlatLogo.png" // Update this path to your actual logo
                className="logo" // Use class for styling
                alt="MyFlat Management Logo"
            />
            MyFlat Management
          </Navbar.Brand>
          <Navbar.Toggle aria-controls="basic-navbar-nav" />
          <Navbar.Collapse id="basic-navbar-nav">
            <Nav className="me-auto">
              <Nav.Link as={Link} to="/" className="nav-link">Home</Nav.Link>
              {!user && <Nav.Link as={Link} to="/login" className="nav-link">Login</Nav.Link>}
              {user && (
                  <>
                    <Nav.Link as={Link} to="/communication" className="nav-link">Communication</Nav.Link>
                    <Nav.Link as={Link} to="/documents" className="nav-link">Documents</Nav.Link>
                    <Nav.Link as={Link} to="/maintenance" className="nav-link">Maintenance</Nav.Link>
                    {user?.role === 'propmgmt' && <Nav.Link as={Link} to="/manageProperties" className="nav-link">Properties</Nav.Link>}
                    {user?.role === 'propmgmt' && <Nav.Link as={Link} to="/registerTenant" className="nav-link">Tenants</Nav.Link>}
                    <Nav.Link className="optionalLink">Settings</Nav.Link>
                  </>
              )}
            </Nav>
            {user && (
                <Button variant="outline-primary" className="logout-button" onClick={handleLogout}>Logout</Button>
            )}
            <Navbar.Text className="ms-3 logged-in-info">
              Logged in as: <strong>{user ? (user.role === 'propmgmt' ? 'Property Management' : 'Tenant') : 'N/A'}</strong>
            </Navbar.Text>
          </Navbar.Collapse>
        </Container>
      </Navbar>
  );
}

export default NavigationMenu;
