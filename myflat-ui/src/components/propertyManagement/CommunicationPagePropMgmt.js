import React, { useState } from 'react';
import { Container, Form, Button, Alert, Dropdown, Image, Card } from 'react-bootstrap';
import '../styles/CommunicationPage.css';

function CommunicationPagePropMgmt() {
  const [message, setMessage] = useState('');
  const [recipientType, setRecipientType] = useState('');
  const [selectedTenants, setSelectedTenants] = useState([]);
  const [showAlert, setShowAlert] = useState(false);
  const [feedbackMessage, setFeedbackMessage] = useState('');
  const [file, setFile] = useState(null);
  const [sortType, setSortType] = useState('top');

  const tenants = [
      { id: 'tenant1', name: 'Tenant 1' },
      { id: 'tenant2', name: 'Tenant 2' },
  ];

  const messages = [
      { id: 1, topic: "Water Leak", property: "Property A", content: "There is a leak in the bathroom.", priority: "high" },
      { id: 2, topic: "Power Outage", property: "Property B", content: "No power in the kitchen since yesterday.", priority: "medium" },
  ];

  const sortedMessages = [...messages].sort((a, b) => {
      if (sortType === 'top') {
          return b.priority.localeCompare(a.priority);
      } else if (sortType === 'property') {
          return a.property.localeCompare(b.property);
      } else if (sortType === 'topic') {
          return a.topic.localeCompare(b.topic);
      }
  });

  const handleSendMessage = (e) => {
      e.preventDefault();
      setShowAlert(true);

      if (recipientType === 'allTenants') {
          setFeedbackMessage('Your message has been sent to all tenants!');
      } else if (recipientType === 'singleTenant') {
          const tenantNames = selectedTenants.map(id => tenants.find(tenant => tenant.id === id)?.name).join(", ");
          setFeedbackMessage(`Your message has been sent to ${tenantNames}!`);
      }

      setMessage('');
      setRecipientType('');
      setSelectedTenants([]);
      setTimeout(() => setShowAlert(false), 3000);
  };

  const handleTenantSelectionChange = (tenantId) => {
      setSelectedTenants(prev =>
          prev.includes(tenantId) ? prev.filter(id => id !== tenantId) : [...prev, tenantId]
      );
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
      <Image src="/myflatLogo.ico" alt="MyFlat Logo" className="mb-4" fluid />
      <h2 className="mb-4">Communication - Property Management</h2>
      {showAlert && <Alert variant="success" className="mb-4">{feedbackMessage}</Alert>}
  
      <Form onSubmit={handleSendMessage} className="mb-5">
        <Form.Group className="mb-3">
          <Dropdown onSelect={(eventKey) => setRecipientType(eventKey)}>
            <Dropdown.Toggle variant="success" id="dropdown-recipient-type">
              {recipientType ? (recipientType === 'allTenants' ? "All Tenants" : "Specific Tenants") : "Select Recipient Type"}
            </Dropdown.Toggle>
            <Dropdown.Menu>
              <Dropdown.Item eventKey="allTenants">All Tenants</Dropdown.Item>
              <Dropdown.Item eventKey="singleTenant">Specific Tenants</Dropdown.Item>
            </Dropdown.Menu>
          </Dropdown>
        </Form.Group>
  
        {recipientType === 'singleTenant' && (
          <Form.Group className="mb-3">
            <Form.Label>Select Tenants:</Form.Label>
            {tenants.map(tenant => (
              <Form.Check
                key={tenant.id}
                type="checkbox"
                id={`check-${tenant.id}`}
                label={tenant.name}
                onChange={() => handleTenantSelectionChange(tenant.id)}
                checked={selectedTenants.includes(tenant.id)}
                className="mb-2"
              />
            ))}
          </Form.Group>
        )}
  
        <Form.Group controlId="messageForm" className="mb-3">
          <Form.Label>Your Message:</Form.Label>
          <Form.Control
            as="textarea"
            rows={3}
            value={message}
            onChange={(e) => setMessage(e.target.value)}
            required
          />
        </Form.Group>
  
        <Button variant="primary" type="submit" disabled={!recipientType || (recipientType === 'singleTenant' && selectedTenants.length === 0)}>Send Message</Button>
      </Form>
  
      <Form.Group controlId="fileForm" className="mb-3">
        <Form.Label>Upload File:</Form.Label>
        <Form.Control 
          type="file" 
          onChange={handleFileChange}
          className="mb-2"
        />
        <Button 
          variant="secondary" 
          onClick={handleUploadFile} 
          disabled={!file}
        >
          Upload File
        </Button>
      </Form.Group>
  
      <Dropdown onSelect={(eventKey) => setSortType(eventKey)} className="mb-4">
        <Dropdown.Toggle variant="info" id="dropdown-sort-type">
          Sort By: {sortType.charAt(0).toUpperCase() + sortType.slice(1)}
        </Dropdown.Toggle>
        <Dropdown.Menu>
          <Dropdown.Item eventKey="top">Top Priority</Dropdown.Item>
          <Dropdown.Item eventKey="property">Property</Dropdown.Item>
          <Dropdown.Item eventKey="topic">Topic</Dropdown.Item>
        </Dropdown.Menu>
      </Dropdown>
  
      <div className="messages-list">
        {sortedMessages.map(message => (
          <Card key={message.id} className="mb-3">
            <Card.Body>
              <Card.Title>{message.topic} - {message.property}</Card.Title>
              <Card.Text>{message.content}</Card.Text>
            </Card.Body>
          </Card>
        ))}
      </div>
    </Container>
  );
  }
  export default CommunicationPagePropMgmt;
  