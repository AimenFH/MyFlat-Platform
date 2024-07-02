import React, {useEffect, useState} from 'react';
import {Container, Form, Button, Card} from 'react-bootstrap';
import axios from 'axios';
import { useAuth } from '../AuthContext';

const ManageProperties = () => {
    const [propertyName, setPropertyName] = useState('');
    const [propertyAddress, setPropertyAddress] = useState('');
    const [numberOfFloors, setNumberOfFloors] = useState('');
    const [numberOfApartments, setNumberOfApartments] = useState('');
    const [submitted, setSubmitted] = useState(false);
    const [properties, setProperties] = useState([]);

    const { user } = useAuth();

    const fetchProperties = () => {
        axios.get('http://localhost:8080/api/property-management/v1/properties', {
            headers: {
                'Authorization': `Bearer ${user.jwt}`
            }
        })
            .then(response => {
                setProperties(response.data);
            })
            .catch(error => {
                console.error('Error:', error);
            });
    };

    useEffect(() => {
        fetchProperties();
    }, []);

    const handleSubmit = (e) => {
        e.preventDefault();

        const propertyDto = {
            propertyName: propertyName,
            propertyAddress: propertyAddress,
            numberOfFloors: parseInt(numberOfFloors),
            numberOfApartments: parseInt(numberOfApartments)
        };

        axios.post('http://localhost:8080/api/property-management/v1/property', propertyDto, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${user.jwt}`
            }
        })
            .then(response => {
                console.log('Success:', response.data);
                setSubmitted(true);
                fetchProperties();
            })
            .catch(error => {
                console.error('Error:', error);
            });
    };

    return (
        <Container className="manage-properties">
            <h2>Manage Properties</h2>
            <Form onSubmit={handleSubmit}>
                <Form.Group className="mb-3">
                    <Form.Label>Property Name</Form.Label>
                    <Form.Control type="text" value={propertyName} onChange={(e) => setPropertyName(e.target.value)} required />
                </Form.Group>
                <Form.Group className="mb-3">
                    <Form.Label>Property Address</Form.Label>
                    <Form.Control type="text" value={propertyAddress} onChange={(e) => setPropertyAddress(e.target.value)} required />
                </Form.Group>
                <Form.Group className="mb-3">
                    <Form.Label>Number of Floors</Form.Label>
                    <Form.Control type="number" value={numberOfFloors} onChange={(e) => setNumberOfFloors(e.target.value)} required />
                </Form.Group>
                <Form.Group className="mb-3">
                    <Form.Label>Number of Apartments</Form.Label>
                    <Form.Control type="number" value={numberOfApartments} onChange={(e) => setNumberOfApartments(e.target.value)} required />
                </Form.Group>
                <Button variant="primary" type="submit">Submit</Button>
            </Form>
            {submitted && <div className="confirmation-message">Property created successfully.</div>}
            <h3>All Properties</h3>
            {properties.map(property => (
                <Card key={property.id} style={{ width: '100%', marginBottom: '1rem' }}>
                    <Card.Body>
                        <Card.Title>Property {property.propertyName}</Card.Title>
                        <Card.Text>Address: {property.propertyAddress}</Card.Text>
                        <Card.Text>Number of Floors: {property.numberOfFloors}</Card.Text>
                        <Card.Text>Number of Apartments: {property.numberOfApartments}</Card.Text>
                    </Card.Body>
                </Card>
            ))}
        </Container>
    );
};

export default ManageProperties;