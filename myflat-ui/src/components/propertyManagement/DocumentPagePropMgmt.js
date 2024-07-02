import React, { useState } from 'react';
import { Container, Form, Card, Row, Col } from 'react-bootstrap';
import '../styles/DocumentPage.css';
import axios from "axios";
import {useAuth} from "../AuthContext";

const DocumentPagePropMgmt = () => {
  const { user } = useAuth();
  const [uploadedFiles, setUploadedFiles] = useState([]);
  const [generalFiles, setGeneralFiles] = useState([]);
  const [archivedFiles, setArchivedFiles] = useState([]);
  const [apartmentFiles, setApartmentFiles] = useState([]);
  const [apartmentId, setApartmentId] = useState('');
  const [userId, setUserId] = useState('');

  const handleFileUpload = async (event, category) => {
    const files = event.target.files;
    let formData = new FormData();
    formData.append('file', files[0]);

    const documentDto = {
      "apartmentId": apartmentId, // todo should this really be retrieved from the front end?
      "title": files[0].name,
      "isArchived": false,
      "userId": userId // todo should this really be retrieved from the front end?
    };

    const documentDtoBlob = new Blob([JSON.stringify(documentDto)], { type: 'application/json' });
    formData.append('documentDto', documentDtoBlob);

    try {
      const response = await axios.post('http://localhost:8080/api/property-management/v1/document', formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
          'Authorization': `Bearer ${user.jwt}`
        }
      });
      console.log('File upload response:', response.data);
    } catch (error) {
      console.error('Error uploading file:', error);
    }

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
      </Row>
      <Row className="mb-3">
        <Col>
          <Form.Group controlId="apartmentFileUploadForm">
            <Form.Label>Upload Apartment Documents:</Form.Label>
            <Form.Control type="text" value={userId} onChange={(e) => setUserId(e.target.value)} placeholder="Enter User ID"/>
            <Form.Control type="text" value={apartmentId} onChange={(e) => setApartmentId(e.target.value)} placeholder="Enter Apartment ID"/>
            <Form.Control type="file" multiple onChange={(e) => handleFileUpload(e, 'apartment')}/>
          </Form.Group>
        </Col>
      </Row>

      <Row>
        <Col>
          {renderUploadedFiles({ title: 'General Documents', files: generalFiles })}
        </Col>
        <Col>
          {renderUploadedFiles({ title: 'Apartment Documents', files: apartmentFiles })}
        </Col>
      </Row>
    </Container>
  );
};

export default DocumentPagePropMgmt;
