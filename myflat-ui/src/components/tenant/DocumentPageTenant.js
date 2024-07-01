import React, { useEffect, useState } from 'react';
import { Button } from 'react-bootstrap';
import axios from 'axios';
import '../styles/DocumentPage.css';
import { useAuth } from '../AuthContext';
import { saveAs } from 'file-saver';

const DocumentPageTenant = () => {
    const [tenantDocuments, setTenantDocuments] = useState([]);
    const [generalDocuments, setGeneralDocuments] = useState([]);
    const [archivedDocuments, setArchivedDocuments] = useState([]);

    const { user } = useAuth();  // Get user and token

    useEffect(() => {
        const fetchDocuments = async () => {
            const headers = {
                Authorization: `Bearer ${user.jwt}`
            };

            const tenantResponse = await axios.get(`http://localhost:8080/api/tenant/v1/document/${user.userId}/1/tenant`, { headers });
            setTenantDocuments(tenantResponse.data);

            const generalResponse = await axios.get(`http://localhost:8080/api/tenant/v1/document/${user.userId}/1/general`, { headers });
            setGeneralDocuments(generalResponse.data);

            const archivedResponse = await axios.get(`http://localhost:8080/api/tenant/v1/document/${user.userId}/1/archived`, { headers });
            setArchivedDocuments(archivedResponse.data);
        };

        fetchDocuments();
    }, [user]);

    const handlePdfDownload = (doc) => {
        const byteCharacters = atob(doc.content);
        const byteNumbers = new Array(byteCharacters.length);
        for (let i = 0; i < byteCharacters.length; i++) {
            byteNumbers[i] = byteCharacters.charCodeAt(i);
        }
        const byteArray = new Uint8Array(byteNumbers);
        const blob = new Blob([byteArray], {type: "application/pdf"});
        saveAs(blob, `${doc.title}.pdf`);
    };

    return (
        <div className="document-page">
            <h2>Document Management</h2>

            <div className="document-section">
                <h3>Your Documents</h3>
                <ul className="documents-list">
                    {tenantDocuments.map((doc, index) => (
                        <li key={index} className="document">
                            <Button
                                variant="link"
                                onClick={() => handlePdfDownload(doc)}
                            >
                                {doc.title}
                            </Button>
                        </li>
                    ))}
                </ul>
            </div>

            <div className="document-section">
                <h3>General Documents & Announcements</h3>
                <ul className="documents-list">
                    {generalDocuments.map((doc, index) => (
                        <li key={index} className="document">
                            <Button
                                variant="link"
                                onClick={() => handlePdfDownload(doc)}
                            >
                                {doc.title}
                            </Button>
                        </li>
                    ))}
                </ul>
            </div>

            <div className="document-section">
                <h3>Archived Documents</h3>
                <ul className="documents-list">
                    {archivedDocuments.map((doc, index) => (
                        <li key={index} className="document">
                            <Button
                                variant="link"
                                onClick={() => handlePdfDownload(doc)}
                            >
                                {doc.title}
                            </Button>
                        </li>
                    ))}
                </ul>
            </div>
        </div>
    );
};

export default DocumentPageTenant;