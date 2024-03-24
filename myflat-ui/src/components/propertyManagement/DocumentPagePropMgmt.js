import React, { useState } from 'react';
import { Container, Form, Card, Row, Col } from 'react-bootstrap';
import '../styles/DocumentPage.css';

const DocumentPagePropMgmt = () => {
  const [uploadedFiles, setUploadedFiles] = useState([]);
  const [generalFiles, setGeneralFiles] = useState([]);
  const [archivedFiles, setArchivedFiles] = useState([]);
  const [apartmentFiles, setApartmentFiles] = useState([]);

  const handleFileUpload = (event, category) => {
    const files = event.target.files;
    switch (category) {
      case 'general':
        setGeneralFiles([...generalFiles, ...files]);
        break;
      case 'archived':
        setArchivedFiles([...archivedFiles, ...files]);
        break;
      case 'apartment':
        setApartmentFiles([...apartmentFiles, ...files]);
        break;
      default:
        break;
    }
    setUploadedFiles([...uploadedFiles, ...files]);
  };

  const renderUploadedFiles = ({ title, files }) => {
    return (
      <div className="document-section">
        <h3>{title}</h3>
        <ul className="documents-list">
          {files && files.length > 0 ? (
            files.map((file, index) => (
              <li key={index} className="document">
                {file.name}
              </li>
            ))
          ) : (
            <li>No files uploaded</li>
          )}
        </ul>
      </div>
    );
  };
  

  return (
    <Container className="document-page">
      <h2>Document Management</h2>

      <Card className="bulletin-board mt-3">
        <Card.Header>Digital Bulletin Board</Card.Header>
        <Card.Body>
          <Card.Text>Beispieltext des ersten Posts auf dem Schwarzen Brett</Card.Text>
          <Card.Text>Beispieltext des zweiten Posts auf dem Schwarzen Brett</Card.Text>
        </Card.Body>
      </Card>

      <Row className="mb-3">
        <Col>
          <Form.Group controlId="generalFileUploadForm">
            <Form.Label>Upload General Documents:</Form.Label>
            <Form.Control type="file" multiple onChange={(e) => handleFileUpload(e, 'general')} />
          </Form.Group>
        </Col>
        <Col>
          <Form.Group controlId="archivedFileUploadForm">
            <Form.Label>Upload Archived Documents:</Form.Label>
            <Form.Control type="file" multiple onChange={(e) => handleFileUpload(e, 'archived')} />
          </Form.Group>
        </Col>
      </Row>

      <Row className="mb-3">
        <Col>
          <Form.Group controlId="apartmentFileUploadForm">
            <Form.Label>Upload Apartment Documents:</Form.Label>
            <Form.Control type="file" multiple onChange={(e) => handleFileUpload(e, 'apartment')} />
          </Form.Group>
        </Col>
      </Row>

      <Row>
        <Col>
          {renderUploadedFiles({ title: 'General Documents', files: generalFiles })}
        </Col>
        <Col>
          {renderUploadedFiles({ title: 'Archived Documents', files: archivedFiles })}
        </Col>
        <Col>
          {renderUploadedFiles({ title: 'Apartment Documents', files: apartmentFiles })}
        </Col>
      </Row>

   
    </Container>
  );
};

export default DocumentPagePropMgmt;
