import React from 'react';
import { Button, Container, Row, Col, Card } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import '../styles/HomePage.css'; // Pfad anpassen, falls nötig

const HomePageTenant = () => {
    return (
        <Container className="mt-5 container-custom">
            <Row className="mb-4">
                <Col>
                    <h1>Willkommen bei MyFlat</h1>
                    <p>MyFlat revolutioniert die Kommunikation und Verwaltung in Mehrparteienhäusern. Als Mieter profitieren Sie von einer zentralisierten Plattform, die den Austausch mit dem Hausverwaltungsteam und anderen Mietern vereinfacht, wichtige Dokumente sicher verwahrt und es Ihnen ermöglicht, schnell auf gemeinsame Ressourcen und Ankündigungen zuzugreifen.</p>
                </Col>
            </Row>
            <Row xs={1} md={2} lg={4} className="g-4"> {/* Aktualisiert für ein 4-Spalten-Layout auf größeren Bildschirmen */}
                {/* Defekt melden Karte */}
                <Col>
                    <Card className="card-custom">
                        <Card.Body>
                            <Card.Title className="card-title">Defekt melden</Card.Title>
                            <Card.Text className="card-text">Melden Sie schnell und einfach Schäden oder Defekte in Ihrer Wohnung oder im Gebäude.</Card.Text>
                            <Button variant="primary" as={Link} to="/maintenance" className="button-custom">Zum Formular</Button>
                        </Card.Body>
                    </Card>
                </Col>
                {/* Digitales Schwarzes Brett Karte */}
                <Col>
                    <Card className="card-custom">
                        <Card.Body>
                            <Card.Title className="card-title">Digitales Schwarzes Brett</Card.Title>
                            <Card.Text className="card-text">Informieren Sie sich über aktuelle Ankündigungen und Neuigkeiten rund um Ihr Wohnhaus.</Card.Text>
                            <Button variant="secondary" as={Link} to="/communication" className="button-custom">Anschauen</Button>
                        </Card.Body>
                    </Card>
                </Col>
                {/* Ihre Dokumente Karte */}
                <Col>
                    <Card className="card-custom">
                        <Card.Body>
                            <Card.Title className="card-title">Ihre Dokumente</Card.Title>
                            <Card.Text className="card-text">Finden Sie wichtige Dokumente wie Verträge und Betriebskostenabrechnungen an einem Ort.</Card.Text>
                            <Button variant="info" as={Link} to="/documents" className="button-custom">Dokumente</Button>
                        </Card.Body>
                    </Card>
                </Col>
                {/* Beispiel für eine zusätzliche Karte */}
                <Col>
                    <Card className="card-custom">
                        <Card.Body>
                            <Card.Title className="card-title">Community Events</Card.Title>
                            <Card.Text className="card-text">Entdecken Sie kommende Events und Treffen in Ihrer Wohnanlage.</Card.Text>
                            <Button variant="success" as={Link} to="/events" className="button-custom">Events</Button>
                        </Card.Body>
                    </Card>
                </Col>
                <Col>
                    <Card className="card-custom">
                        <Card.Body>
                            <Card.Title className="card-title">Termine</Card.Title>
                            <Card.Text className="card-text">Verwalten Sie Ihre Termine mit dem Hausverwaltungsteam oder buchen Sie Ressourcen.</Card.Text>
                            <Button variant="danger" as={Link} to="/appointments" className="button-custom">Termine anzeigen</Button>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    );
};

export default HomePageTenant;
