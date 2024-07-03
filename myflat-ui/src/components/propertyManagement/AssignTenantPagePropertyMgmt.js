import React, { useState } from 'react';
import { Container, Form, Button } from 'react-bootstrap';
import axios from 'axios';
import { useAuth } from '../AuthContext';

function AssignTenantPagePropertyMgmt() {
  const [fromDate, setFromDate] = useState('');
  const [toDate, setToDate] = useState('');
  const [amount, setAmount] = useState('');
  const [userId, setUserId] = useState('');
  const [propertyId, setPropertyId] = useState('');
  const [apartmentId, setApartmentId] = useState('');
  const [top, setTop] = useState('');
  const [bookApartmentStatus, setBookApartmentStatus] = useState('');

  const { user } = useAuth();

  const handleSubmit = (e) => {
    e.preventDefault();

    setBookApartmentStatus("CURRENTENANT");

    const data = {
      fromDate,
      toDate,
      amount: parseInt(amount),
      userId: parseInt(userId),
      propertyId: parseInt(propertyId),
      top: parseInt(top),
      bookApartmentStatus
    };

    axios.post('http://localhost:8080/api/property-management/v1/apartment/book/' + apartmentId, data, {
      headers: {
        'Authorization': `Bearer ${user.jwt}`
      }
    })
    .then(response => {
      console.log('Success:', response.data);
    })
    .catch(error => {
      console.error('Error:', error);
    });
  };

  return (
    <Container>
      <Form onSubmit={handleSubmit}>
        <h2>Assign Tenant to Apartment</h2>
        {/* From Date Field */}
        <Form.Group className="mb-3">
          <Form.Label>From Date:</Form.Label>
          <Form.Control type="date" value={fromDate} onChange={(e) => setFromDate(e.target.value)} required />
        </Form.Group>

        {/* To Date Field */}
        <Form.Group className="mb-3">
          <Form.Label>To Date:</Form.Label>
          <Form.Control type="date" value={toDate} onChange={(e) => setToDate(e.target.value)} required />
        </Form.Group>

        {/* Amount Field */}
        <Form.Group className="mb-3">
          <Form.Label>Amount:</Form.Label>
          <Form.Control type="number" value={amount} onChange={(e) => setAmount(e.target.value)} required />
        </Form.Group>

        {/* User ID Field */}
        <Form.Group className="mb-3">
          <Form.Label>User ID:</Form.Label>
          <Form.Control type="number" value={userId} onChange={(e) => setUserId(e.target.value)} required />
        </Form.Group>

        {/* Property ID Field */}
        <Form.Group className="mb-3">
          <Form.Label>Property ID:</Form.Label>
          <Form.Control type="number" value={propertyId} onChange={(e) => setPropertyId(e.target.value)} required />
        </Form.Group>

        {/* Apartment ID Field */}
        <Form.Group className="mb-3">
          <Form.Label>Apartment ID:</Form.Label>
          <Form.Control type="number" value={apartmentId} onChange={(e) => setApartmentId(e.target.value)} required />
        </Form.Group>

        {/* Top Field */}
        <Form.Group className="mb-3">
          <Form.Label>Top ID:</Form.Label>
          <Form.Control type="number" value={top} onChange={(e) => setTop(e.target.value)} />
        </Form.Group>
        <Button variant="primary" type="submit">Assign Tenant</Button>
      </Form>
    </Container>
  );
}

export default AssignTenantPagePropertyMgmt;