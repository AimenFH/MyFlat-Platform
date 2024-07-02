import React from 'react';
import { Button, Container, Row, Col, Card } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import '../styles/HomePage.css';

const HomePageTenant = () => {
    return (
        <Container className="mt-5 container-custom">
            <Row className="mb-4">
                <Col>
                    <h1>Welcome to MyFlat</h1>
                    <p>MyFlat revolutionizes communication and management in multi-party houses. As a tenant, you benefit from a centralized platform that simplifies exchange with the property management team and other tenants, securely stores important documents, and allows you to quickly access common resources and announcements.</p>
                </Col>
            </Row>
            <Row xs={1} md={2} lg={4} className="g-4">
                <Col>
                    <Card className="card-custom">
                        <Card.Body className="text-center">
                            <Card.Title className="card-title">Report a defect</Card.Title>
                            <Card.Text className="card-text">Quickly and easily report damages or defects in your apartment or the building.</Card.Text>
                            <Button variant="primary" as={Link} to="/maintenance" className="button-custom">To the form</Button>
                        </Card.Body>
                    </Card>
                </Col>
                <Col>
                    <Card className="card-custom">
                        <Card.Body>
                            <Card.Title className="card-title">Digital Bulletin Board</Card.Title>
                            <Card.Text className="card-text">Find out about current announcements and news around your residential building.</Card.Text>
                            <Button variant="secondary" as={Link} to="/communication" className="button-custom">View</Button>
                        </Card.Body>
                    </Card>
                </Col>
                <Col>
                    <Card className="card-custom">
                        <Card.Body>
                            <Card.Title className="card-title">Your Documents</Card.Title>
                            <Card.Text className="card-text">Find important documents such as contracts and operating cost statements in one place.</Card.Text>
                            <Button variant="info" as={Link} to="/documents" className="button-custom">Documents</Button>
                        </Card.Body>
                    </Card>
                </Col>
                <Col>
                    <Card className="card-custom">
                        <Card.Body>
                            <Card.Title className="card-title">Appointments</Card.Title>
                            <Card.Text className="card-text">Manage your appointments with the property management team or book resources.</Card.Text>
                            <Button variant="danger" as={Link} to="/appointments" className="button-custom">View appointments</Button>
                        </Card.Body>
                    </Card>
                </Col>
                <Col>
                    <Card className="card-custom">
                        <Card.Body>
                            <Card.Title className="card-title">Feedback and Suggestions</Card.Title>
                            <Card.Text className="card-text">Your feedback is important to us. Share your suggestions or feedback with the property management team.</Card.Text>
                            <Button variant="warning" as={Link} to="/feedback" className="button-custom">Give Feedback</Button>
                        </Card.Body>
                    </Card>
                </Col>
                <Col>
                    <Card className="card-custom">
                        <Card.Body>
                            <Card.Title className="card-title">Community Events</Card.Title>
                            <Card.Text className="card-text">Discover upcoming events and meetings in your residential complex.</Card.Text>
                            <Button variant="success" as={Link} to="/events" className="button-custom">Events</Button>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    );
};

export default HomePageTenant;