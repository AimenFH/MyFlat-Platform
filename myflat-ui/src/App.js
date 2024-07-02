import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import LoginPage from './components/LoginPage';
import RegisterPageTenant from './components/RegisterPageTenant';
import RegisterPagePropertyMgmt from './components/RegisterPagePropertyMgmt';
import { AuthProvider, useAuth } from './components/AuthContext';
import { DarkModeProvider } from './DarkModeContext';
import CommunicationPageTenant from './components/tenant/CommunicationPageTenant';
import CommunicationPagePropMgmt from './components/propertyManagement/CommunicationPagePropMgmt';
import DocumentPageTenant from './components/tenant/DocumentPageTenant';
import DocumentPagePropMgmt from './components/propertyManagement/DocumentPagePropMgmt';
import MaintenancePageTenant from './components/tenant/MaintenancePageTenant';
import MaintenancePagePropMgmt from './components/propertyManagement/MaintenancePagePropMgmt';
import NavigationMenu from './components/NavigationMenu';
import ManageProperties from './components/propertyManagement/ManageProperties';
import HomePageTenant from './components/tenant/HomePageTenant';
import HomePagePropertyMgmt from './components/propertyManagement/HomePagePropertyMgmt';
import ManageApartments from './components/propertyManagement/ManageApartments';
import EditApartment from './components/propertyManagement/EditApartment';
import KeyManagementPagePropMgmt from "./components/propertyManagement/KeyManagementPagePropMgmt";
import AppointmentPageTenant from "./components/tenant/AppointmentPageTenant";
import AppointmentPagePropertyMgmt from "./components/propertyManagement/AppointmentPagePropertyMgmt";
import FeedbackPageTenant from './components/tenant/FeedbackPageTenant';
import FeedbackPagePropertyMgmt from './components/propertyManagement/FeedbackPagePropertyMgmt';

import 'bootstrap/dist/css/bootstrap.min.css';

const App = () => {
  return (
      <Router>
        <AuthProvider>
          <DarkModeProvider>
            <NavigationMenu />
            <Routes>
              <Route path="/" element={<Home />} />
              <Route path="/login" element={<LoginPage />} />
              <Route path="/registerTenant" element={<RegisterPageTenant />} />
              <Route path="/registerPropertyMgmt" element={<RegisterPagePropertyMgmt />} />
              <Route path="/communication" element={<ProtectedRoute allowedRoles={['TENANT', 'PROPERTY_MANAGEMENT']}><CommunicationPage /></ProtectedRoute>} />
              <Route path="/documents" element={<ProtectedRoute allowedRoles={['TENANT', 'PROPERTY_MANAGEMENT']}><DocumentPage /></ProtectedRoute>} />
              <Route path="/maintenance" element={<ProtectedRoute allowedRoles={['TENANT', 'PROPERTY_MANAGEMENT']}><MaintenancePage /></ProtectedRoute>} />
              <Route path="/manage-properties" element={<ProtectedRoute allowedRoles={['PROPERTY_MANAGEMENT']}><ManageProperties /></ProtectedRoute>} />
              <Route path="/manage-apartments" element={<ProtectedRoute allowedRoles={['PROPERTY_MANAGEMENT']}><ManageApartments /></ProtectedRoute>} />
              <Route path="/key-management" element={<ProtectedRoute allowedRoles={['PROPERTY_MANAGEMENT']}><KeyManagementPagePropMgmt /></ProtectedRoute>} />
              <Route path="/properties/:propertyId/apartments" element={<ProtectedRoute allowedRoles={['PROPERTY_MANAGEMENT']}><ManageApartments /></ProtectedRoute>} />
              <Route path="/properties/:propertyId/apartments/:apartmentId/edit" element={<ProtectedRoute allowedRoles={['PROPERTY_MANAGEMENT']}><EditApartment /></ProtectedRoute>} />
              <Route path="/appointments" element={<AppointmentPage />} />
              <Route path="/feedback" element={<FeedbackPage />} />
            </Routes>
          </DarkModeProvider>
        </AuthProvider>
      </Router>
  );
}

const Home = () => {
  const { user } = useAuth();
  if (!user) return <Navigate to="/login" />;
  return user.role === 'TENANT' ? <HomePageTenant /> : <HomePagePropertyMgmt />;
};

const DocumentPage = () => {
  const { user } = useAuth();
  return user.role === 'TENANT' ? <DocumentPageTenant /> : <DocumentPagePropMgmt />;
};

const MaintenancePage = () => {
  const { user } = useAuth();
  return user.role === 'TENANT' ? <MaintenancePageTenant /> : <MaintenancePagePropMgmt />;
};

const CommunicationPage = () => {
  const { user } = useAuth();
  return user.role === 'TENANT' ? <CommunicationPageTenant /> : <CommunicationPagePropMgmt />;
};

const AppointmentPage = () => {
  const { user } = useAuth();
  return user.role === 'TENANT' ? <AppointmentPageTenant /> : <AppointmentPagePropertyMgmt />;
};

const FeedbackPage = () => {
  const { user } = useAuth();
  return user.role === 'TENANT' ? <FeedbackPageTenant /> : <FeedbackPagePropertyMgmt />;
};

const ProtectedRoute = ({ children, allowedRoles }) => {
  const { user } = useAuth();
  return user && allowedRoles.includes(user.role) ? children : <Navigate to="/login" replace />;
};

export default App;
