import React, { useState, useEffect } from 'react';
import { Button, Form, Card, Alert, Container, Row, Col, Dropdown, DropdownButton } from 'react-bootstrap';
import '../styles/MaintenancePage.css';
import { FiCheckCircle } from 'react-icons/fi';
import axios from 'axios';
import { useAuth } from '../AuthContext';

axios.defaults.baseURL = 'http://localhost:8080';

const MaintenancePagePropMgmt = () => {
  const [defects, setDefects] = useState([]);
  const [showAlert, setShowAlert] = useState(false);
  const [newMaintenance, setNewMaintenance] = useState('');
  const [category, setCategory] = useState('');

  const { user } = useAuth();

  useEffect(() => {
    fetchDefects();
  }, []);

  const fetchDefects = async () => {
    try {
      const response = await axios.get('/api/property-management/v1/defects', {
        headers: {
          'Authorization': `Bearer ${user.jwt}`
        }
      });
      console.log('Fetched defects:', response.data);
      setDefects(response.data);
    } catch (error) {
      console.error('Error fetching defects:', error);
    }
  };

  const handlePublish = async (e) => {
    e.preventDefault();
    const newId = defects.length + 1;

    const newDefect = {
      id: newId,
      issue: newMaintenance,
      category: category.toUpperCase(),
      apartment: 'N/A',
      property: 'N/A',
      resolved: false
    };

    try {
      await axios.post('/api/defects', newDefect, {
        headers: {
          'Authorization': `Bearer ${user.jwt}`
        }
      });
      setDefects([...defects, newDefect]);
      setNewMaintenance('');
      setCategory('');
      setShowAlert(true);
      setTimeout(() => setShowAlert(false), 3000);
    } catch (error) {
      console.error('Error publishing defect:', error);
    }
  };

  const markAsResolved = async (id) => {
    try {
      const updatedDefect = defects.find(defect => defect.id === id);
      updatedDefect.resolved = true;

      await axios.put(`/api/defects/${id}`, updatedDefect, {
        headers: {
          'Authorization': `Bearer ${user.jwt}`
        }
      });

      setDefects(defects.map(defect =>
          defect.id === id ? { ...defect, resolved: true } : defect
      ));
    } catch (error) {
      console.error('Error marking defect as resolved:', error);
    }
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
            {defects.map((defect) => (
                <Card key={defect.id} className="mb-3" style={{ opacity: defect.resolved ? 0.4 : 1 }}>
                  <Card.Body>
                    <Card.Title>
                      Issue {defect.id} - {defect.category}
                      {defect.resolved && <FiCheckCircle className="resolved-icon" />}
                    </Card.Title>
                    <Card.Text>{defect.issue}</Card.Text>
                    {!defect.resolved && (
                        <Button variant="warning" onClick={() => markAsResolved(defect.id)}>Mark as resolved</Button>
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
