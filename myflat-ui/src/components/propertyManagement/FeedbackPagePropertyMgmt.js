import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Container, Card } from 'react-bootstrap';
import { useAuth } from '../AuthContext';

const FeedbackPagePropertyMgmt = () => {
  const [feedbacks, setFeedbacks] = useState([]);
  const { user } = useAuth();

  useEffect(() => {
    if (user && user.jwt) {
      axios.get('http://localhost:8080/api/property-management/feedback', {
        headers: {
          'Authorization': `Bearer ${user.jwt}`
        }
      })
      .then(response => {
        setFeedbacks(response.data);
      })
      .catch(error => {
        console.error('Error fetching feedback:', error);
      });
    }
  }, []);

  return (
    <Container>
      <h2>Tenant Feedback</h2>
      {feedbacks.map((feedback, index) => (
        <Card key={index} style={{ marginBottom: '10px' }}>
          <Card.Body>
            <Card.Text>User ID: {feedback.tenantId}</Card.Text>
            <Card.Text>{feedback.message}</Card.Text>
            <Card.Subtitle className="mb-2 text-muted">
              {new Date(feedback.timestamp).toLocaleString()}
            </Card.Subtitle>
          </Card.Body>
        </Card>
      ))}
    </Container>
  );
};

export default FeedbackPagePropertyMgmt;