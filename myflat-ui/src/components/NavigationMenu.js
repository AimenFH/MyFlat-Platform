import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from './AuthContext'; // Angenommen, dieser Pfad stimmt
import { Navbar, Nav, Container, Button } from 'react-bootstrap';
import './styles/NavigationMenu.css'; // Stellt sicher, dass der Pfad zu deiner CSS-Datei stimmt

function NavigationMenu() {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login'); // Leitet den Benutzer nach dem Logout zur Login-Seite um
  };

  return (
      <Navbar expand="lg" className="mb-4 shadow" style={{ backgroundColor: "#E3F2FD" }}>
        <Container>
          <Navbar.Brand as={Link} to="/" className="navbar-brand">MyFlat Management</Navbar.Brand>
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
                    {user?.role === 'propmgmt' && <Nav.Link as={Link} to="/manageProperties" className="nav-link">Manage Properties</Nav.Link>}
                    {user?.role === 'propmgmt' && <Nav.Link as={Link} to="/registerTenant" className="nav-link">Register Tenant</Nav.Link>}
                    {/* Anwendung der optionalLink-Klasse für nicht anklickbare Links */}
                    <Nav.Link className="optionalLink">Appointments/Notifications/Languages (optional)</Nav.Link>
                    <Nav.Link className="optionalLink">Feedback (optional)</Nav.Link>
                    <Nav.Link className="optionalLink">Customize Profile (optional)</Nav.Link>
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
