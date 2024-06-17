import React, { useState } from 'react';
import { Container, Form, Button } from 'react-bootstrap';
import './styles/RegisterPage.css';
import axios from "axios";

function RegisterPagePropertyMgmt() {
  const [agentName, setAgentName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();

    const data = {
      email: email,
      name: agentName,
      password: password,
      phoneNumber: '' // todo Add logic to get the phone number
    };

    axios.post('http://localhost:8080/api/auth/v1/register/property-management', data)
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
        <h2>Property Management Registration</h2>
        <Form.Group className="mb-3" controlId="agentName">
          <Form.Label>Agent Name:</Form.Label>
          <Form.Control
            type="text"
            value={agentName}
            onChange={(e) => setAgentName(e.target.value)}
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

export default RegisterPagePropertyMgmt;
