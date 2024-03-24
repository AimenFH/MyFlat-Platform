import React, { useState } from 'react';
import { Button, Form, Card, Alert, Container, Row, Col, Dropdown, DropdownButton } from 'react-bootstrap';
import '../styles/MaintenancePage.css';
import { FiCheckCircle } from 'react-icons/fi';

const MaintenancePagePropMgmt = () => {
  const [maintenances, setMaintenances] = useState([
    { id: 1, issue: "Leaky faucet in apartment 3", category: "Plumbing", apartment: 3, property: 1 },
    { id: 2, issue: "Air conditioning not working in apartment 5", category: "Electrical", apartment: 5, property: 2 },
  ]);

  const [showAlert, setShowAlert] = useState(false);
  const [newMaintenance, setNewMaintenance] = useState('');
  const [category, setCategory] = useState(''); 

  const handlePublish = (e) => {
    e.preventDefault();
    const newId = maintenances.length + 1;
    setMaintenances([...maintenances, { id: newId, issue: newMaintenance, category, apartment: 'N/A', property: 'N/A' }]);
    setNewMaintenance('');
    setCategory(''); 
    setShowAlert(true);
    setTimeout(() => setShowAlert(false), 3000);
  };

  const markAsResolved = (id) => {
    setMaintenances(maintenances.map(maintenance => 
      maintenance.id === id ? { ...maintenance, resolved: true } : maintenance
    ));
  };

  return (
    <Container className="maintenance-page-propmgmt">
      <Row className="justify-content-md-center">
        <Col xs={12} md={8}>
          <h2>Maintenance Management</h2>
          {showAlert && <Alert variant="success">New maintenance issue published!</Alert>}
          <Form onSubmit={handlePublish}>
            <Form.Group className="mb-3">
              <Form.Label>Publish a maintenance issue:</Form.Label>
              <Form.Control
                as="textarea"
                rows={3}
                value={newMaintenance}
                onChange={(e) => setNewMaintenance(e.target.value)}
                required
              />
            </Form.Group>
            {/* Kategorie-Dropdown */}
            <Form.Group className="mb-3">
              <Form.Label>Category:</Form.Label>
              <DropdownButton
                id="dropdown-item-button"
                title={category || "Select Category"}
                onSelect={(e) => setCategory(e)}
                variant="secondary"
              >
                <Dropdown.Item eventKey="Electrical">Electrical</Dropdown.Item>
                <Dropdown.Item eventKey="Plumbing">Plumbing</Dropdown.Item>
                <Dropdown.Item eventKey="Construction">Construction</Dropdown.Item>
                <Dropdown.Item eventKey="Key">New Key</Dropdown.Item>
              </DropdownButton>
            </Form.Group>
            <Button variant="primary" type="submit">
              Publish Issue
            </Button>
          </Form>

          <h3 className="mt-4">Submitted Maintenance Requests</h3>
          {maintenances.map((maintenance) => (
            <Card key={maintenance.id} className="mb-3" style={{ opacity: maintenance.resolved ? 0.4 : 1 }}>
              <Card.Body>
                <Card.Title>
                  Issue {maintenance.id} - {maintenance.category}
                  {maintenance.resolved && <FiCheckCircle className="resolved-icon" />}
                </Card.Title>
                <Card.Text>{maintenance.issue}</Card.Text>
                {!maintenance.resolved && (
                  <Button variant="warning" onClick={() => markAsResolved(maintenance.id)}>Mark as resolved</Button>
                )}
              </Card.Body>
            </Card>
          ))}
        </Col>
      </Row>
    </Container>
  );
};

export default MaintenancePagePropMgmt;
