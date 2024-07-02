import React, { useState } from 'react';
import axios from 'axios';
import { useAuth } from '../AuthContext';
import '../styles/FeedbackPage.css';

const FeedbackPageTenant = () => {
  const [message, setMessage] = useState('');
  const [submitted, setSubmitted] = useState(false);
  const { user } = useAuth();

  const handleSubmit = async (e) => {
    e.preventDefault();

    const feedback = {
      message: message,
      timestamp: new Date().toISOString(),
    };

    try {
      await axios.post('http://localhost:8080/api/tenant/feedback', feedback, {
        headers: {
          Authorization: `Bearer ${user.jwt}`,
        },
      });
      setSubmitted(true);
      setMessage('');
    } catch (error) {
      console.error('Error submitting feedback:', error);
      alert('Error submitting feedback');
    }
  };

  return (
    <div className="feedback-page">
      <h2>Feedback</h2>
      {!submitted ? (
        <form onSubmit={handleSubmit} className="feedback-form">
          <label htmlFor="message">Your feedback:</label>
          <textarea
            id="message"
            value={message}
            onChange={(e) => setMessage(e.target.value)}
            required
          ></textarea>
          <button type="submit" className="submit-btn">Submit Feedback</button>
        </form>
      ) : (
        <div className="confirmation-message">Your feedback has been submitted. Thank you!</div>
      )}
    </div>
  );
};

export default FeedbackPageTenant;