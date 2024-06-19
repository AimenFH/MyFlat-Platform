import React, { useState } from 'react';
import { useAuth } from './AuthContext';
import './styles/LoginPage.css';
import axios from "axios";
import { useNavigate } from 'react-router-dom';

function LoginPage() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const { login } = useAuth();
  const navigate = useNavigate();

  const handleLogin = (e) => {
    e.preventDefault();

    const data = {
      email: username,
      password: password
    };

    axios.post('http://localhost:8080/api/auth/v1/login', data)
        .then(async response => {
          console.log('Success:', response.data);
          await login({username: data.email, role: response.data.userRole, jwt: response.data.jwt});
          navigate('/');
        })
        .catch(error => {
          console.error('Error:', error);
        });
        /*if (username === 'tenant' && password === 'tenant') {
          login({ username: 'tenant', role: 'tenant' });
          window.location.href = '/';
        } else if (username === 'propmgmt' && password === 'propmgmt') {
          login({ username: 'propmgmt', role: 'propmgmt' });
          window.location.href = '/';
        } else {
          alert('Login failed: Incorrect username or password.');
        }*/
  };

  const handleResetPassword = () => {
    alert("Reset Password functionality not implemented.");
  };

  return (
    <div className="login-container">
      <form className="login-form" onSubmit={handleLogin}>
        <h2>Login Page</h2>
        <div className="form-group">
          <label htmlFor="username">Username:</label>
          <input
            type="text"
            id="username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
          />
        </div>
        <div className="form-group">
          <label htmlFor="password">Password:</label>
          <input
            type="password"
            id="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>
        <button type="submit" className="login-btn">Login</button>
        <button type="button" className="reset-btn" onClick={handleResetPassword}>Reset Password</button>
      </form>
    </div>
  );
}

export default LoginPage;