import React, {useEffect, useState} from 'react';
import {Button, Container, Modal, Form, Card} from 'react-bootstrap';
import { useAuth } from '../AuthContext';

const KeyManagementPage = () => {
    const [keys, setKeys] = useState([]);
    const [keyId, setKeyId] = useState(null);
    const [showModal, setShowModal] = useState(false);

    const [propertyId, setPropertyId] = useState('');
    const [userId, setUserId] = useState('');
    const [apartmentId, setApartmentId] = useState('');
    const [issuanceDate, setIssuanceDate] = useState('');
    const [redemptionDate, setRedemptionDate] = useState('');
    const [replacementRequested, setReplacementRequested] = useState(false);
    const [keyNumber, setKeyNumber] = useState('');

    const handleShowModal = () => setShowModal(true);

    const { user } = useAuth();

    useEffect(() => {
        fetchKeys();
    }, []);

    const handleShowChangeModal = (key) => {
        setKeyId(key.id);
        setPropertyId(key.propertyId);
        setUserId(key.userId);
        setApartmentId(key.apartmentId);
        setIssuanceDate(key.issuanceDate);
        setRedemptionDate(key.redemptionDate);
        setReplacementRequested(key.replacementRequested);
        setKeyNumber(key.keysNumber);
        setShowModal(true);
    };

    const handleCloseModal = () => {
        setKeyId(null);
        setPropertyId('');
        setUserId('');
        setApartmentId('');
        setIssuanceDate('');
        setRedemptionDate('');
        setReplacementRequested(false);
        setKeyNumber('');
        setShowModal(false);
    };

    const fetchKeys = () => {
        fetch('http://localhost:8080/api/property-management/v1/key-management', {
            headers: {
                'Authorization': `Bearer ${user.jwt}`
            },
        })
            .then(response => response.json())
            .then(data => setKeys(data))
            .catch((error) => {
                console.error('Error:', error);
            });
    };

    const handleAddKey = () => {
        const key = {
            id: keyId,
            propertyId: propertyId,
            userId: userId,
            apartmentId: apartmentId,
            issuanceDate: issuanceDate,
            redemptionDate: redemptionDate,
            replacementRequested: replacementRequested,
            keysNumber: keyNumber
        };

        const method = keyId ? 'PUT' : 'POST';

        fetch('http://localhost:8080/api/property-management/v1/key-management', {
            method: method,
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${user.jwt}`
            },
            body: JSON.stringify(key)
        })
            .then(response => {
                if (response.ok) {
                    handleCloseModal();
                    fetchKeys();
                } else {
                    console.error('Error:', response);
                }
            })
            .catch((error) => {
                console.error('Error:', error);
            });
    };

    const handleDeleteKey = (keyId) => {
        fetch(`http://localhost:8080/api/property-management/v1/key-management?keyId=${keyId}`, {
            method: 'DELETE',
            headers: {
                'Authorization': `Bearer ${user.jwt}`
            },
        })
            .then(response => {
                if (response.ok) {
                    fetchKeys();
                } else {
                    console.error('Error:', response);
                }
            })
            .catch((error) => {
                console.error('Error:', error);
            });
    };

    const formatDate = (dateString) => {
        const options = { year: 'numeric', month: 'long', day: 'numeric' };
        return new Date(dateString).toLocaleDateString(undefined, options);
    }

    return (
        <Container className="key-management-page">
            <h2>Key Management</h2>
            <Button onClick={handleShowModal} className="add-btn mb-4">Add Key Record</Button>

            <Modal show={showModal} onHide={handleCloseModal}>
                <Modal.Header closeButton>
                    <Modal.Title>Add Key Record</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form>
                        <Form.Group className="mb-3">
                            <Form.Label>Property ID</Form.Label>
                            <Form.Control type="number" value={propertyId}
                                          onChange={e => setPropertyId(e.target.value)}/>
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Label>User ID</Form.Label>
                            <Form.Control type="number" value={userId} onChange={e => setUserId(e.target.value)}/>
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Label>Apartment ID</Form.Label>
                            <Form.Control type="number" value={apartmentId}
                                          onChange={e => setApartmentId(e.target.value)}/>
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Label>Issuance Date</Form.Label>
                            <Form.Control type="date" value={issuanceDate}
                                          onChange={e => setIssuanceDate(e.target.value)}/>
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Label>Redemption Date</Form.Label>
                            <Form.Control type="date" value={redemptionDate}
                                          onChange={e => setRedemptionDate(e.target.value)}/>
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Check type="checkbox" label="Replacement Requested" checked={replacementRequested}
                                        onChange={e => setReplacementRequested(e.target.checked)}/>
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Label>Key Number</Form.Label>
                            <Form.Control type="number" value={keyNumber} onChange={e => setKeyNumber(e.target.value)}/>
                        </Form.Group>
                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleCloseModal}>Close</Button>
                    <Button variant="primary" onClick={handleAddKey}>Save</Button>
                </Modal.Footer>
            </Modal>

            <div className="keys-list">
                {keys.map((key) => (
                    <Card key={key.id} className="mb-3">
                        <Card.Body>
                            <Card.Title>Key ID: {key.id}</Card.Title>
                            <Card.Text>Property ID: {key.propertyId}</Card.Text>
                            <Card.Text>User ID: {key.userId}</Card.Text>
                            <Card.Text>Apartment ID: {key.apartmentId}</Card.Text>
                            <Card.Text>Issuance Date: {formatDate(key.issuanceDate)}</Card.Text>
                            <Card.Text>Redemption Date: {formatDate(key.redemptionDate)}</Card.Text>
                            <Card.Text>Replacement Requested: {key.replacementRequested ? 'Yes' : 'No'}</Card.Text>
                            <Card.Text>Key Number: {key.keysNumber}</Card.Text>
                            <Button variant="danger" onClick={() => handleDeleteKey(key.id)}
                                    className="delete-btn">Delete</Button>
                            <Button variant="info" onClick={() => handleShowChangeModal(key)}
                                    className="change-btn">Change</Button>
                        </Card.Body>
                    </Card>
                ))}
            </div>
        </Container>
    );
};

export default KeyManagementPage;