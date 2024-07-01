import React, { useState } from 'react';
import { Container, Form, Button } from 'react-bootstrap';
import axios from 'axios';
import { useAuth } from '../AuthContext';

const ManageApartments = () => {
    const [number, setNumber] = useState('');
    const [floor, setFloor] = useState('');
    const [area, setArea] = useState('');
    const [price, setPrice] = useState('');
    const [propertyId, setPropertyId] = useState('');
    const [submitted, setSubmitted] = useState(false);

    const { user } = useAuth();

    const handleSubmit = (e) => {
        e.preventDefault();

        const apartmentDto = {
            number: parseInt(number),
            floor: parseInt(floor),
            area: parseFloat(area),
            price: parseInt(price),
            propertyId: parseInt(propertyId)
        };

        axios.post('http://localhost:8080/api/property-management/v1/apartment', apartmentDto, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${user.jwt}`
            }
        })
            .then(response => {
                console.log('Success:', response.data);
                setSubmitted(true); // Set submitted to true when the request is successful
            })
            .catch(error => {
                console.error('Error:', error);
            });
    };

    return (
        <Container className="manage-apartments">
            <h2>Manage Apartments</h2>
            <Form onSubmit={handleSubmit}>
                <Form.Group className="mb-3">
                    <Form.Label>Number</Form.Label>
                    <Form.Control type="number" value={number} onChange={(e) => setNumber(e.target.value)} required />
                </Form.Group>
                <Form.Group className="mb-3">
                    <Form.Label>Floor</Form.Label>
                    <Form.Control type="number" value={floor} onChange={(e) => setFloor(e.target.value)} required />
                </Form.Group>
                <Form.Group className="mb-3">
                    <Form.Label>Area</Form.Label>
                    <Form.Control type="number" step="0.01" value={area} onChange={(e) => setArea(e.target.value)} required />
                </Form.Group>
                <Form.Group className="mb-3">
                    <Form.Label>Price</Form.Label>
                    <Form.Control type="number" value={price} onChange={(e) => setPrice(e.target.value)} required />
                </Form.Group>
                <Form.Group className="mb-3">
                    <Form.Label>Property ID</Form.Label>
                    <Form.Control type="number" value={propertyId} onChange={(e) => setPropertyId(e.target.value)} required />
                </Form.Group>
                <Button variant="primary" type="submit">Submit</Button>
            </Form>
            {submitted && <div className="confirmation-message">Apartment created successfully.</div>}
        </Container>
    );
};

export default ManageApartments;