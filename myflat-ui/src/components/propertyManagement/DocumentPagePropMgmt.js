import React, {useEffect, useState} from 'react';
import {Container, Form, Card, Row, Col, ListGroup, Button} from 'react-bootstrap';
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
  const [generalDocuments, setGeneralDocuments] = useState([]);
  const [archivedDocuments, setArchivedDocuments] = useState([]);

  useEffect(() => {
    fetchDocuments();
  }, []);

  const fetchDocuments = async () => {
    try {
      const generalResponse = await axios.get('http://localhost:8080/api/property-management/v1/document', {
        headers: { 'Authorization': `Bearer ${user.jwt}` }
      });
      setGeneralDocuments(generalResponse.data);

      const archivedResponse = await axios.get('http://localhost:8080/api/property-management/v1/document/archived', {
        headers: { 'Authorization': `Bearer ${user.jwt}` }
      });
      setArchivedDocuments(archivedResponse.data);
    } catch (error) {
      console.error('Error fetching documents:', error);
    }
  };

  const handleFileUpload = async (event, category) => {
    if (event.target.files.length === 0) {
      // No file selected, exit the function early
      return;
    }

    const files = event.target.files;
    let formData = new FormData();
    formData.append('file', files[0]);

    const documentDto = {
      "apartmentId": apartmentId,
      "title": files[0].name,
      "isArchived": false,
      "userId": userId
    };

    const documentDtoBlob = new Blob([JSON.stringify(documentDto)], { type: 'application/json' });
    formData.append('documentDto', documentDtoBlob);

    try {
      await axios.post('http://localhost:8080/api/property-management/v1/document', formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
          'Authorization': `Bearer ${user.jwt}`
        }
      });
      fetchDocuments();
    } catch (error) {
      console.error('Error uploading file:', error);
    }
  };

  const archiveDocument = async (documentId) => {
    try {
      await axios.put(`http://localhost:8080/api/property-management/v1/document/${documentId}/archive`, {}, {
        headers: { 'Authorization': `Bearer ${user.jwt}` }
      });
      fetchDocuments();
    } catch (error) {
      console.error('Error archiving document:', error);
    }
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
          <h3>General Documents</h3>
          <ListGroup>
            {generalDocuments.map((doc, index) => (
                <ListGroup.Item key={index} className="d-flex justify-content-between align-items-center">
                  {doc.title}
                  <Button
                      variant="outline-secondary"
                      size="sm"
                      onClick={() => archiveDocument(doc.id)}
                      style={{ width: '75px' }}> {/* Adjust the width as needed */}
                    Archive
                  </Button>
                </ListGroup.Item>
            ))}
          </ListGroup>
        </Col>
        <Col>
          <h3>Archived Documents</h3>
          <ListGroup>
            {archivedDocuments.map((doc, index) => (
                <ListGroup.Item key={index}>{doc.title}</ListGroup.Item>
            ))}
          </ListGroup>
        </Col>
      </Row>
    </Container>
  );
};

export default DocumentPagePropMgmt;
