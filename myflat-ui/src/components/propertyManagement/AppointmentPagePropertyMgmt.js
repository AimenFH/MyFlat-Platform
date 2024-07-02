import React, { useState, useEffect } from 'react';
import {Button, Container, Modal, Form, Card} from 'react-bootstrap';
import { useAuth } from '../AuthContext';
import axios from "axios";

const AppointmentPagePropertyMgmt = () => {
    const [appointments, setAppointments] = useState([]);
    const [showModal, setShowModal] = useState(false);
    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [date, setDate] = useState('');
    const { user } = useAuth();

    useEffect(() => {
        fetchAppointments();
    }, []);

    const fetchAppointments = () => {
        axios.get('http://localhost:8080/api/property-management/v1/appointment/all', {
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
    };

    const handleShowModal = () => setShowModal(true);
    const handleCloseModal = () => setShowModal(false);

    const handleAddAppointment = () => {
        const appointment = {
            title,
            description,
            date
        };

        axios.post('http://localhost:8080/api/property-management/v1/appointment/create', appointment, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${user.jwt}`
            }
        })
            .then(response => {
                if (response.status === 200) {
                    handleCloseModal();
                    fetchAppointments();
                } else {
                    console.error('Error:', response);
                }
            })
            .catch(error => {
                console.error('Error:', error);
            });
    };

    const handleDeleteAppointment = (appointmentId) => {
        axios.delete(`http://localhost:8080/api/property-management/v1/appointment/delete/${appointmentId}`, {
            headers: {
                'Authorization': `Bearer ${user.jwt}`
            }
        })
            .then(response => {
                if (response.status === 200) {
                    fetchAppointments(); // Refresh the list of appointments
                } else {
                    console.error('Failed to delete the appointment:', response);
                }
            })
            .catch(error => {
                console.error('Error:', error);
            });
    };

    return (
        <Container className="appointment-management-page">
            <h2>Appointment Management</h2>
            <Button onClick={handleShowModal} className="add-btn mb-4">Add Appointment</Button>

            <Modal show={showModal} onHide={handleCloseModal}>
                <Modal.Header closeButton>
                    <Modal.Title>Add Appointment</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form>
                        <Form.Group className="mb-3">
                            <Form.Label>Title</Form.Label>
                            <Form.Control type="text" value={title} onChange={e => setTitle(e.target.value)}/>
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Label>Description</Form.Label>
                            <Form.Control as="textarea" rows={3} value={description}
                                          onChange={e => setDescription(e.target.value)}/>
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Label>Date</Form.Label>
                            <Form.Control type="datetime-local" value={date} onChange={e => setDate(e.target.value)}/>
                        </Form.Group>
                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleCloseModal}>Close</Button>
                    <Button variant="primary" onClick={handleAddAppointment}>Save</Button>
                </Modal.Footer>
            </Modal>

            <div className="appointments-list">
                {appointments.map((appointment) => (
                    <Card key={appointment.id} className="mb-3">
                        <Card.Body>
                            <Card.Title>{appointment.title}</Card.Title>
                            <Card.Text>{appointment.description}</Card.Text>
                            <Card.Text>{new Date(appointment.date).toLocaleString()}</Card.Text>
                            <Button variant="danger" onClick={() => handleDeleteAppointment(appointment.id)}
                                    className="delete-btn">Delete</Button>
                        </Card.Body>
                    </Card>
                ))}
            </div>
        </Container>
    );
};

export default AppointmentPagePropertyMgmt;