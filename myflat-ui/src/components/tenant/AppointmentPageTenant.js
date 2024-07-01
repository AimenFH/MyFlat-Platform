import React, { useEffect, useState } from 'react';
import { Container, Card } from 'react-bootstrap';
import axios from 'axios';
import { useAuth } from '../AuthContext';

const AppointmentPageTenant = () => {
    const [appointments, setAppointments] = useState([]);
    const { user } = useAuth();

    useEffect(() => {
        axios.get('http://localhost:8080/api/tenant/appointment/all', {
            headers: {
                'Authorization': `Bearer ${user.jwt}`
            }
        })
            .then(response => {
                setAppointments(response.data);
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }, []);

    return (
        <Container className="tenant-appointments">
            <h2>Your Appointments</h2>
            {appointments.map(appointment => (
                <Card style={{ width: '18rem', marginBottom: '10px' }} key={appointment.id}>
                    <Card.Body>
                        <Card.Title>{appointment.title}</Card.Title>
                        <Card.Subtitle className="mb-2 text-muted">{new Date(appointment.date).toLocaleString()}</Card.Subtitle>
                        <Card.Text>{appointment.description}</Card.Text>
                    </Card.Body>
                </Card>
            ))}
        </Container>
    );
};

export default AppointmentPageTenant;