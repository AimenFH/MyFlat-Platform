import React, { useState } from 'react';
import { Container, Form, Button, Card, Alert, DropdownButton, Dropdown, Badge } from 'react-bootstrap';
import '../styles/CommunicationPage.css';

function CommunicationPageTenant() {
    const [message, setMessage] = useState('');
    const [file, setFile] = useState(null);
    const [recipient, setRecipient] = useState('');
    const [tenantName, setTenantName] = useState('');
    const [showAlert, setShowAlert] = useState(false);
    const [feedbackMessage, setFeedbackMessage] = useState('');

    const handleSendMessage = (e) => {
        e.preventDefault();
        setShowAlert(true);
        const recipientText = recipient === 'propertyManagement' ? 'Property Management' : recipient === 'tenant' ? `Other Tenant: ${tenantName}` : 'Group Chat';
        setFeedbackMessage(`Your message to ${recipientText} has been sent.`);
        setMessage('');
        setRecipient('');
        setTenantName('');
        setTimeout(() => setShowAlert(false), 3000);
    };

    const handleRecipientChange = (eventKey) => {
        setRecipient(eventKey);
        if (eventKey !== 'tenant') {
            setTenantName('');
        }
    };

    const handleFileChange = (e) => {
        setFile(e.target.files[0]);
    };

    const handleUploadFile = () => {
        console.log('File uploaded:', file ? file.name : 'No file');
        setFile(null);
    };

    return (
        <Container className="communication-page">
            <h2 className="text-center mb-4">Tenant Communication Portal</h2>

            {showAlert && <Alert variant="info">{feedbackMessage}</Alert>}

            <Form onSubmit={handleSendMessage} className="mb-5">
                <Form.Group className="mb-3">
                    <DropdownButton
                        id="dropdown-basic-button"
                        title={recipient ? (recipient === 'propertyManagement' ? "Property Management" : recipient === 'tenant' ? `Other Tenant: ${tenantName || 'Select'}` : "Group Chat") : "Select Recipient"}
                        variant="outline-secondary"
                        onSelect={handleRecipientChange}
                    >
                        <Dropdown.Item eventKey="propertyManagement">Property Management</Dropdown.Item>
                        <Dropdown.Item eventKey="tenant">Other Tenant</Dropdown.Item>
                        <Dropdown.Item eventKey="group">Group Chat</Dropdown.Item>
                    </DropdownButton>
                </Form.Group>

                {recipient === 'tenant' && (
                    <Form.Group controlId="tenantName" className="mb-3">
                        <Form.Label>Tenant Name:</Form.Label>
                        <Form.Control
                            type="text"
                            placeholder="Enter tenant's name"
                            value={tenantName}
                            onChange={(e) => setTenantName(e.target.value)}
                            required={recipient === 'tenant'}
                        />
                    </Form.Group>
                )}

                <Form.Group controlId="messageForm" className="mb-3">
                    <Form.Label>Your Message:</Form.Label>
                    <Form.Control
                        as="textarea"
                        placeholder="Type your message here"
                        rows={3}
                        value={message}
                        onChange={(e) => setMessage(e.target.value)}
                        required
                    />
                </Form.Group>

                <Button variant="outline-success" type="submit" disabled={!recipient || (recipient === 'tenant' && !tenantName)} className="me-2">Send Message</Button>
                <Form.Group controlId="fileForm" className="d-inline-block">
                    <Form.Control
                        type="file"
                        onChange={handleFileChange}
                        size="sm"
                        className="d-inline-block me-2"
                    />
                    <Button
                        variant="outline-primary"
                        onClick={handleUploadFile}
                        disabled={!file}
                    >
                        Upload File
                    </Button>
                </Form.Group>
            </Form>

            <Card className="mb-4">
                <Card.Header as="h5">Digital Bulletin Board <Badge bg="secondary">New</Badge></Card.Header>
                <Card.Body>
                    <Card.Text>Sample text of the first post on the bulletin board.</Card.Text>
                    <Card.Text>Sample text of the second post on the bulletin board.</Card.Text>
                </Card.Body>
            </Card>
        </Container>
    );
}

export default CommunicationPageTenant;
