import React, { useState } from 'react';
import { Container, Form, Button } from 'react-bootstrap';
import './styles/RegisterPage.css';
import axios from "axios";
import { useAuth } from './AuthContext';

function RegisterPageTenant() {
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [property, setProperty] = useState('');
  const [apartment, setApartment] = useState('');

  const { jwt } = useAuth();

  const handleSubmit = (e) => {
    e.preventDefault();

    const data = {
      email: email,
      name: `${firstName} ${lastName}`,
      password: password,
      phoneNumber: '' // todo Add logic to get the phone number
    };

    axios.post('http://localhost:8080/api/property-management/v1/tenant/create', data, {
      headers: {
        'Authorization': `Bearer ${jwt}`
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
    <Container className="register-container">
      <Form onSubmit={handleSubmit} className="register-form">
        <h2>Tenant Registration</h2>
        <Form.Group className="mb-3" controlId="firstName">
          <Form.Label>First Name:</Form.Label>
          <Form.Control
            type="text"
            value={firstName}
            onChange={(e) => setFirstName(e.target.value)}
            required
          />
        </Form.Group>
        <Form.Group className="mb-3" controlId="lastName">
          <Form.Label>Last Name:</Form.Label>
          <Form.Control
            type="text"
            value={lastName}
            onChange={(e) => setLastName(e.target.value)}
            required
          />
        </Form.Group>
        <Form.Group className="mb-3" controlId="email">
          <Form.Label>Email:</Form.Label>
          <Form.Control
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
        </Form.Group>
        <Form.Group className="mb-3" controlId="apartment">
          <Form.Label>Apartment/Top:</Form.Label>
          <Form.Control
            type="text"
            value={apartment}
            onChange={(e) => setApartment(e.target.value)}
            required
          />
        </Form.Group>
        <Form.Group className="mb-3" controlId="property">
          <Form.Label>Property:</Form.Label>
          <Form.Control
            type="text"
            value={property}
            onChange={(e) => setProperty(e.target.value)}
            required
          />
        </Form.Group>
        <Form.Group className="mb-3" controlId="password">
          <Form.Label>Password:</Form.Label>
          <Form.Control
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </Form.Group>
        <Button variant="primary" type="submit">
          Register
        </Button>
      </Form>
    </Container>
  );
}

export default RegisterPageTenant;
