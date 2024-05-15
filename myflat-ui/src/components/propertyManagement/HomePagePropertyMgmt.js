import React, { useState } from 'react';
import { Button, Container, Modal, Navbar, Nav, Form } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import '../styles/HomePage.css'

const HomePagePropertyMgmt = () => {
    const [showSettings, setShowSettings] = useState(false);
    const [visibility, setVisibility] = useState({
        tenantManagement: true,
        documentArchive: true,
        keyManagement: true,
        maintenanceRequests: true,
        propertyManagement: true,
    });

    const toggleVisibility = key => {
        setVisibility(prevVisibility => ({
            ...prevVisibility,
            [key]: !prevVisibility[key]
        }));
    };

    const managementButtons = [
        { key: 'tenantManagement', text: 'Mieterverwaltung', variant: 'primary', href: '/tenant-management' },
        { key: 'documentArchive', text: 'Dokumentenarchiv', variant: 'secondary', href: '/document-archive' },
        { key: 'keyManagement', text: 'Schlüsselverwaltung', variant: 'success', href: '/key-management', className: 'custom-key-management' },
        { key: 'maintenanceRequests', text: 'Wartungsanfragen', variant: 'warning', href: '/maintenance-requests' },
        { key: 'propertyManagement', text: 'Immobilienverwaltung', variant: 'info', href: '/property-management' },
    ];

    return (
        <>
            <Navbar className="navbar-custom" bg="light" expand="lg">
                <Container>
                    <Navbar.Brand href="/">MyFlat Verwaltungsbereich</Navbar.Brand>
                    <Nav className="me-auto">
                        <Button variant="outline-secondary" onClick={() => setShowSettings(true)}>
                            Anpassen <i className="bi bi-gear-fill"></i>
                        </Button>
                    </Nav>
                </Container>
            </Navbar>

            <Container className="container-custom my-5">
                <h1>Willkommen im Verwaltungsbereich von MyFlat</h1>
                <p>Hier finden Sie alle Tools zur effizienten Verwaltung Ihrer Immobilien.</p>
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
                    <Modal.Title>Shortcuts Anpassen</Modal.Title>
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
                        Schließen
                    </Button>
                </Modal.Footer>
            </Modal>
        </>
    );
};

export default HomePagePropertyMgmt;
