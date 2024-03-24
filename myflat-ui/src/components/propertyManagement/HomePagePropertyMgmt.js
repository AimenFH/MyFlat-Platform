import React, { useState } from 'react';
import { Button, Container, Modal, Navbar, Nav, Form } from 'react-bootstrap';

const HomePagePropertyMgmt = () => {
    const [showSettings, setShowSettings] = useState(false);
    const [visibility, setVisibility] = useState({
        tenantManagement: true,
        documentArchive: true,
        keyManagement: true,
        maintenanceRequests: true,
        propertyManagement: true,
    });

    // Funktion zum Umschalten der Sichtbarkeit
    const toggleVisibility = key => {
        setVisibility(prevVisibility => ({
            ...prevVisibility,
            [key]: !prevVisibility[key]
        }));
    };

    // Button-Konfigurationen für die verschiedenen Management-Bereiche
    const managementButtons = [
        { key: 'tenantManagement', text: 'Mieterverwaltung', variant: 'primary' },
        { key: 'documentArchive', text: 'Dokumentenarchiv', variant: 'secondary' },
        { key: 'keyManagement', text: 'Schlüsselverwaltung', variant: 'success' },
        { key: 'maintenanceRequests', text: 'Wartungsanfragen', variant: 'warning' },
        { key: 'propertyManagement', text: 'Immobilienverwaltung', variant: 'info' },
    ];

    return (
        <>
            <Navbar bg="light" expand="lg">
                <Container>
                    <Navbar.Brand href="/">MyFlat Verwaltungsbereich</Navbar.Brand>
                    <Nav className="me-auto">
                        <Button variant="outline-secondary" onClick={() => setShowSettings(true)}>
                            Anpassen <i className="bi bi-gear-fill"></i>
                        </Button>
                    </Nav>
                </Container>
            </Navbar>

            <Container className="my-5">
                <h1>Willkommen im Verwaltungsbereich von MyFlat</h1>
                <p>Hier finden Sie alle Tools zur effizienten Verwaltung Ihrer Immobilien.</p>
                <div className="d-flex flex-wrap">
                    {managementButtons.map(({ key, text, variant }) => visibility[key] && (
                        <Button key={key} variant={variant} className="m-2">
                            {text}
                        </Button>
                    ))}
                </div>
            </Container>

            <Modal show={showSettings} onHide={() => setShowSettings(false)} centered>
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
