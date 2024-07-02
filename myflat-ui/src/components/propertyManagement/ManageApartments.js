import React, {useEffect, useState} from 'react';
import {Container, Form, Button, Card} from 'react-bootstrap';
import axios from 'axios';
import { useAuth } from '../AuthContext';

const ManageApartments = () => {
    const [number, setNumber] = useState('');
    const [floor, setFloor] = useState('');
    const [area, setArea] = useState('');
    const [price, setPrice] = useState('');
    const [propertyId, setPropertyId] = useState('');
    const [submitted, setSubmitted] = useState(false);
    const [apartments, setApartments] = useState([]);

    const { user } = useAuth();

    const fetchApartments = () => {
        axios.get('http://localhost:8080/api/property-management/v1/apartments', {
            headers: {
                'Authorization': `Bearer ${user.jwt}`
            }
        })
            .then(response => {
                setApartments(response.data);
            })
            .catch(error => {
                console.error('Error:', error);
            });
    };

    useEffect(() => {
        fetchApartments();
    }, []);

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
                setSubmitted(true);
                fetchApartments();
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
            <h3>All Apartments</h3>
            {apartments.map(apartment => (
                <Card key={apartment.id} style={{ width: '100%', marginBottom: '1rem' }}>
                    <Card.Body>
                        <Card.Title>Apartment {apartment.number}</Card.Title>
                        <Card.Text>Floor: {apartment.floor}</Card.Text>
                        <Card.Text>Area: {apartment.area} sqm</Card.Text>
                        <Card.Text>Price: ${apartment.price}</Card.Text>
                        <Card.Text>Property ID: {apartment.propertyId}</Card.Text>
                    </Card.Body>
                </Card>
            ))}
        </Container>
    );
};

export default ManageApartments;