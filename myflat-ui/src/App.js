import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import LoginPage from './components/LoginPage';
import RegisterPageTenant from './components/RegisterPageTenant';
import RegisterPagePropertyMgmt from './components/RegisterPagePropertyMgmt';
import { AuthProvider, useAuth } from './components/AuthContext';
import CommunicationPageTenant from './components/tenant/CommunicationPageTenant';
import CommunicationPagePropMgmt from './components/propertyManagement/CommunicationPagePropMgmt';
import DocumentPageTenant from './components/tenant/DocumentPageTenant';
import DocumentPagePropMgmt from './components/propertyManagement/DocumentPagePropMgmt';
import MaintenancePageTenant from './components/tenant/MaintenancePageTenant';
import MaintenancePagePropMgmt from './components/propertyManagement/MaintenancePagePropMgmt';
import NavigationMenu from './components/NavigationMenu';
import KeyPage from './components/propertyManagement/KeyPage';
import ManageProperties from './components/propertyManagement/ManageProperties';
import HomePageTenant from './components/tenant/HomePageTenant';
import HomePagePropertyMgmt from './components/propertyManagement/HomePagePropertyMgmt';
import ManageApartments from './components/propertyManagement/ManageApartments';
import EditApartment from './components/propertyManagement/EditApartment';

import 'bootstrap/dist/css/bootstrap.min.css';

const App = () => {
  return (
    <Router>
      <AuthProvider>
        <NavigationMenu />
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/registerTenant" element={<RegisterPageTenant />} />
          <Route path="/registerPropertyMgmt" element={<RegisterPagePropertyMgmt />} />
          <Route path="/communication" element={<ProtectedRoute allowedRoles={['tenant', 'PROPERTY_MANAGEMENT']}><CommunicationPage /></ProtectedRoute>} />
          <Route path="/documents" element={<ProtectedRoute allowedRoles={['tenant', 'PROPERTY_MANAGEMENT']}><DocumentPage /></ProtectedRoute>} />
          <Route path="/maintenance" element={<ProtectedRoute allowedRoles={['tenant', 'PROPERTY_MANAGEMENT']}><MaintenancePage /></ProtectedRoute>} />
          <Route path="/manageProperties" element={<ProtectedRoute allowedRoles={['PROPERTY_MANAGEMENT']}><ManageProperties /></ProtectedRoute>} />
          <Route path="/keys" element={<ProtectedRoute allowedRoles={['PROPERTY_MANAGEMENT']}><KeyPage /></ProtectedRoute>} />
          <Route path="/properties/:propertyId/apartments" element={<ProtectedRoute allowedRoles={['PROPERTY_MANAGEMENT']}><ManageApartments /></ProtectedRoute>} />
          <Route path="/properties/:propertyId/apartments/:apartmentId/edit" element={<ProtectedRoute allowedRoles={['PROPERTY_MANAGEMENT']}><EditApartment /></ProtectedRoute>} />
          <Route path="/keys/:propertyId" element={<ProtectedRoute allowedRoles={['PROPERTY_MANAGEMENT']}><KeyPage /></ProtectedRoute>} />

        </Routes>
      </AuthProvider>
    </Router>
  );
}

const Home = () => {
  const { user } = useAuth();
  if (!user) return <Navigate to="/login" />;
  return user.role === 'tenant' ? <HomePageTenant /> : <HomePagePropertyMgmt />;
};

const DocumentPage = () => {
  const { user } = useAuth();
  return user.role === 'tenant' ? <DocumentPageTenant /> : <DocumentPagePropMgmt />;
};

const MaintenancePage = () => {
  const { user } = useAuth();
  return user.role === 'tenant' ? <MaintenancePageTenant /> : <MaintenancePagePropMgmt />;
};

const CommunicationPage = () => {
  const { user } = useAuth();
  return user.role === 'tenant' ? <CommunicationPageTenant /> : <CommunicationPagePropMgmt />;
};

// ProtectedRoute Komponente ist hier definiert, um den Zugriff basierend auf erlaubten Rollen zu steuern
const ProtectedRoute = ({ children, allowedRoles }) => {
  const { user } = useAuth();
  return user && allowedRoles.includes(user.role) ? children : <Navigate to="/login" replace />;
};

export default App;
