import React, { useState } from 'react';
import { Form, Dropdown, DropdownButton } from 'react-bootstrap';
import axios from 'axios';
import '../styles/MaintenancePage.css';
import { useAuth } from '../AuthContext';

const MaintenancePagePropMgmt = () => {
    const [description, setDescription] = useState('');
    const [apartmentId, setApartmentId] = useState('');
    const [category, setCategory] = useState('');
    const [location, setLocation] = useState('');
    const [submitted, setSubmitted] = useState(false);
    const [image, setImage] = useState(null);

    const { user } = useAuth();

    const handleSubmit = (e) => {
        e.preventDefault();

        const timestamp = new Date().toISOString();
        const status = 'OPEN';

        const formData = new FormData();
        const defectDto = JSON.stringify({
            userId: user.id,
            apartmentId,
            description,
            timestamp,
            status,
            category: category.toUpperCase(),
            location
        });

        formData.append('defectDto', new Blob([defectDto], { type: 'application/json' }));
        formData.append('image', image);

        axios.post('http://localhost:8080/api/property-management/v1/defect', formData, {
            headers: {
                'Content-Type': 'multipart/form-data',
                Authorization: `Bearer ${user.jwt}`
            }
        })
            .then(response => {
                console.log('Success:', response.data);
                setSubmitted(true);
            })
            .catch(error => {
                console.error('Error:', error);
            });
    };

    const options = Array.from({ length: 10 }, (_, i) => i + 1);

    return (
        <div className="maintenance-page">
            <h2>Maintenance Requests</h2>
            <form onSubmit={handleSubmit} className="maintenance-form">
                <label htmlFor="description">Describe the issue:</label>
                <textarea
                    id="description"
                    value={description}
                    onChange={(e) => setDescription(e.target.value)}
                    required
                ></textarea>

                <label htmlFor="apartmentId">Apartment:</label>
                <select
                    id="apartmentId"
                    value={apartmentId}
                    onChange={(e) => setApartmentId(e.target.value)}
                    required
                >
                    <option value="">Select...</option>
                    {options.map(option => (
                        <option key={option} value={option}>{option}</option>
                    ))}
                </select>

                <Form.Group className="mb-3">
                    <Form.Label>Location:</Form.Label>
                    <DropdownButton
                        id="dropdown-item-button-location"
                        title={location ? location.replace(/_/g, ' ') : "Select Location"}
                        onSelect={(e) => setLocation(e)}
                        variant="secondary"
                    >
                        <Dropdown.Item eventKey="COMMON_AREA">Common Area</Dropdown.Item>
                        <Dropdown.Item eventKey="RENTAL_PROPERTY">Rental Property</Dropdown.Item>
                    </DropdownButton>
                </Form.Group>

                <Form.Group className="mb-3">
                    <Form.Label>Category:</Form.Label>
                    <DropdownButton
                        id="dropdown-item-button"
                        title={category || "Select Category"}
                        onSelect={(e) => setCategory(e)}
                        variant="secondary"
                    >
                        <Dropdown.Item eventKey="PLUMBING">Plumbing</Dropdown.Item>
                        <Dropdown.Item eventKey="ELECTRICAL">Electrical</Dropdown.Item>
                        <Dropdown.Item eventKey="CONSTRUCTION">Construction</Dropdown.Item>
                    </DropdownButton>
                </Form.Group>

                <Form.Group className="mb-3">
                    <Form.Label>Upload Image:</Form.Label>
                    <Form.Control
                        type="file"
                        onChange={(e) => setImage(e.target.files[0])}
                    />
                </Form.Group>

                <button type="submit" className="submit-btn">Submit Request</button>
            </form>
            {submitted && <div className="confirmation-message">Your maintenance request has been submitted.</div>}
        </div>
    );
};

export default MaintenancePagePropMgmt;