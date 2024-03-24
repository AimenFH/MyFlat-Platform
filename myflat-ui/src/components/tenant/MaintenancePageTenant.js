import React, { useState } from 'react';
import { Form, Dropdown, DropdownButton } from 'react-bootstrap';
import '../styles/MaintenancePage.css';

const MaintenancePageTenant = () => {
  const [issue, setIssue] = useState('');
  const [apartment, setApartment] = useState('');
  const [property, setProperty] = useState('');
  const [submitted, setSubmitted] = useState(false);
  const [category, setCategory] = useState(''); // Kategoriezustand hinzufügen

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log('Submitted Issue:', issue, 'Apartment:', apartment, 'Property:', property);
    setSubmitted(true);
  };

  const options = Array.from({ length: 10 }, (_, i) => i + 1);

  return (
    <div className="maintenance-page">
      <h2>Maintenance Requestssss</h2>
      <form onSubmit={handleSubmit} className="maintenance-form">
        <label htmlFor="issue">Describe the issue:</label>
        <textarea
          id="issue"
          value={issue}
          onChange={(e) => setIssue(e.target.value)}
          required
        ></textarea>

        <label htmlFor="apartment">Apartment:</label>
        <select
          id="apartment"
          value={apartment}
          onChange={(e) => setApartment(e.target.value)}
          required
        >
          <option value="">Select...</option>
          {options.map(option => (
            <option key={option} value={option}>{option}</option>
          ))}
        </select>

        <label htmlFor="property">Property:</label>
        <select
          id="property"
          value={property}
          onChange={(e) => setProperty(e.target.value)}
          required
        >
          <option value="">Select...</option>
          {options.map(option => (
            <option key={option} value={option}>{option}</option>
          ))}
        </select>
        <Form.Group className="mb-3">
              <Form.Label>Category:</Form.Label>
              <DropdownButton
                id="dropdown-item-button"
                title={category || "Select Category"}
                onSelect={(e) => setCategory(e)}
                variant="secondary"
              >
                <Dropdown.Item eventKey="Electrical">Electrical</Dropdown.Item>
                <Dropdown.Item eventKey="Plumbing">Plumbing</Dropdown.Item>
                <Dropdown.Item eventKey="Construction">Construction</Dropdown.Item>
                <Dropdown.Item eventKey="Key">New Key</Dropdown.Item>

              </DropdownButton>
            </Form.Group>
        <button type="submit" className="submit-btn">Submit Request</button>
      </form>
      {submitted && <div className="confirmation-message">Your maintenance request has been submitted.</div>}
              
    </div>
  );
};

export default MaintenancePageTenant;
