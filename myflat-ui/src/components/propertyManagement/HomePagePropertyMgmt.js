import React, { useState } from 'react';
import { Button, Container, Modal, Navbar, Nav, Form } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import '../styles/HomePage.css'

const HomePagePropertyMgmt = () => {
    const [showSettings, setShowSettings] = useState(false);
    const [visibility, setVisibility] = useState({
        tenantManagement: true,
        keyManagement: true,
        maintenanceRequests: true,
        manageProperties: true,
        manageApartments: true,
        manageAppointments: true,
        viewFeedback: true,
    });

    const toggleVisibility = key => {
        setVisibility(prevVisibility => ({
            ...prevVisibility,
            [key]: !prevVisibility[key]
        }));
    };

    const managementButtons = [
        { key: 'tenantManagement', text: 'Tenant Management', variant: 'primary', href: '/registerTenant' },
        { key: 'keyManagement', text: 'Key Management', variant: 'success', href: '/key-management', className: 'custom-key-management' },
        { key: 'maintenanceRequests', text: 'Maintenance Requests', variant: 'warning', href: '/maintenance' },
        { key: 'manageProperties', text: 'Manage Properties', variant: 'info', href: '/manage-properties' },
        { key: 'manageApartments', text: 'Manage Apartments', variant: 'info', href: '/manage-apartments' },
        { key: 'manageAppointments', text: 'Manage Appointments', variant: 'dark', href: '/appointments' },
        { key: 'viewFeedback', text: 'View Feedback', variant: 'secondary', href: '/feedback' },
    ];

    return (
        <>
            <Navbar className="navbar-custom" bg="light" expand="lg">
                <Container>
                    <Navbar.Brand href="/">MyFlat Administration Area</Navbar.Brand>
                    <Nav className="me-auto">
                        <Button variant="outline-secondary" onClick={() => setShowSettings(true)}>
                            Customize <i className="bi bi-gear-fill"></i>
                        </Button>
                    </Nav>
                </Container>
            </Navbar>

            <Container className="container-custom my-5">
                <h1>Welcome to the administration area of MyFlat</h1>
                <p>Here you will find all the tools for efficient management of your properties.</p>
                <div className="d-flex flex-wrap">
                    {managementButtons.map(({ key, text, variant, href, className }) => visibility[key] && (
                        <Link to={href} className={`button-custom ${className || ''}`} key={key}>
                            <Button variant={variant}>
                                {text}
                            </Button>
                        </Link>
                    ))}
                </div>

            </Container>

            <Modal className="modal-custom" show={showSettings} onHide={() => setShowSettings(false)} centered>
                <Modal.Header closeButton>
                    <Modal.Title>Customize Shortcuts</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    {managementButtons.map(({ key, text }) => (
                        <Form.Check
                            type="switch"
                            id={key}
                            label={text}
                            checked={visibility[key]}
                            onChange={() => toggleVisibility(key)}
                            key={key}
                            className="mb-2"
                        />
                    ))}
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={() => setShowSettings(false)}>
                        Close
                    </Button>
                </Modal.Footer>
            </Modal>
        </>
    );
};

export default HomePagePropertyMgmt;