import React from 'react';
import { Button, Container, Row, Col, Card } from 'react-bootstrap';
import { Link } from 'react-router-dom';

const HomePageTenant = () => {
    return (
        <Container className="mt-5">
            <Row className="mb-4">
                <Col>
                    <h1>Willkommen bei MyFlat</h1>
                    <p>MyFlat revolutioniert die Kommunikation und Verwaltung in Mehrparteienhäusern. Als Mieter profitieren Sie von einer zentralisierten Plattform, die den Austausch mit dem Hausverwaltungsteam und anderen Mietern vereinfacht, wichtige Dokumente sicher verwahrt und es Ihnen ermöglicht, schnell auf gemeinsame Ressourcen und Ankündigungen zuzugreifen.</p>
                </Col>
            </Row>
            <Row xs={1} md={2} lg={3} className="g-4">
                <Col>
                    <Card>
                        <Card.Body>
                            <Card.Title>Defekt melden</Card.Title>
                            <Card.Text>Melden Sie schnell und einfach Schäden oder Defekte in Ihrer Wohnung oder im Gebäude.</Card.Text>
                            <Button variant="primary" as={Link} to="/maintenance">Zum Formular</Button>
                        </Card.Body>
                    </Card>
                </Col>
                <Col>
                    <Card>
                        <Card.Body>
                            <Card.Title>Digitales Schwarzes Brett</Card.Title>
                            <Card.Text>Informieren Sie sich über aktuelle Ankündigungen und Neuigkeiten rund um Ihr Wohnhaus.</Card.Text>
                            <Button variant="secondary" as={Link} to="/communication">Anschauen</Button>
                        </Card.Body>
                    </Card>
                </Col>
                <Col>
                    <Card>
                        <Card.Body>
                            <Card.Title>Ihre Dokumente</Card.Title>
                            <Card.Text>Finden Sie wichtige Dokumente wie Verträge und Betriebskostenabrechnungen an einem Ort.</Card.Text>
                            <Button variant="info" as={Link} to="/documents">Dokumente</Button>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    );
};

export default HomePageTenant;
