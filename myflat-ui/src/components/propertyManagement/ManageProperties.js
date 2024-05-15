import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Button, Container, Modal, Form } from 'react-bootstrap';
import '../styles/ManageProperties.css';

const initialProperties = [
  { id: 1, name: 'Eigentum 1', address: 'Straße 1, Stadt', tenants: 5 },
  { id: 2, name: 'Eigentum 2', address: 'Straße 2, Stadt', tenants: 3 },
];

function ManageProperties() {
  const [properties, setProperties] = useState(initialProperties);
  const [showModal, setShowModal] = useState(false);
  const [newPropertyName, setNewPropertyName] = useState('');
  const [newPropertyAddress, setNewPropertyAddress] = useState('');
  const [newPropertyTenants, setNewPropertyTenants] = useState('');
  const navigate = useNavigate();

  const handleShowModal = () => setShowModal(true);
  const handleCloseModal = () => setShowModal(false);

  const handleAddProperty = () => {
    const newProperty = {
      id: properties.length + 1,
      name: newPropertyName,
      address: newPropertyAddress,
      tenants: parseInt(newPropertyTenants, 10),
    };
    setProperties([...properties, newProperty]);
    handleCloseModal(); // Modal schließen
    // Eingabefelder zurücksetzen
    setNewPropertyName('');
    setNewPropertyAddress('');
    setNewPropertyTenants('');
  };

  const manageApartments = (id) => {
    navigate(`/properties/${id}/apartments`);
  };

  const deleteProperty = (id) => {
    setProperties(properties.filter(property => property.id !== id));
  };
  return (
    <Container className="manage-properties">
      <h2>Verwalte deine Immobilien</h2>
      <Button onClick={handleShowModal} className="add-btn">Eigentum hinzufügen</Button>
  
      <Modal show={showModal} onHide={handleCloseModal}>
        <Modal.Header closeButton>
          <Modal.Title>Neue Immobilie hinzufügen</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form>
            <Form.Group className="mb-3">
              <Form.Label>Name der Immobilie</Form.Label>
              <Form.Control type="text" value={newPropertyName} onChange={e => setNewPropertyName(e.target.value)} />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Adresse der Immobilie</Form.Label>
              <Form.Control type="text" value={newPropertyAddress} onChange={e => setNewPropertyAddress(e.target.value)} />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Anzahl der Mieter</Form.Label>
              <Form.Control type="number" value={newPropertyTenants} onChange={e => setNewPropertyTenants(e.target.value)} />
            </Form.Group>
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleCloseModal}>Schließen</Button>
          <Button variant="primary" onClick={handleAddProperty}>Speichern</Button>
        </Modal.Footer>
      </Modal>
  
      <div className="properties-list">
        {properties.map((property) => (
          <div key={property.id} className="property-item">
            <h3>{property.name}</h3>
            <p>Adresse: {property.address}</p>
            <p>Mieter: {property.tenants}</p>
            <Button variant="info" onClick={() => manageApartments(property.id)}>Wohnungen verwalten</Button>
            <Button variant="info" onClick={() => navigate(`/keys/${property.id}`)}>Schlüssel verwalten</Button>
            <Button variant="danger" onClick={() => deleteProperty(property.id)} className="delete-btn">Löschen</Button>
          </div>
        ))}
      </div>
    </Container>
  );}  

export default ManageProperties;
