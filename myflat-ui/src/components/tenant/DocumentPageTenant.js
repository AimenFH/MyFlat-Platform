import React from 'react';
import { Button } from 'react-bootstrap';
import '../styles/DocumentPage.css';

const DocumentPageTenant = () => {
  const tenantDocuments = ['Mietvertrag.pdf', 'Wartungsprotokoll_Bad.pdf'];
  const generalDocuments = ['Hausordnung.pdf', 'neue_Ankündigung.pdf', 'myflattestpdf.pdf'];
  const archivedDocuments = ['Archived_Reports.pdf', 'Alte_Mietverträge.pdf', 'Endabrechnung_2023.pdf'];


  return (
    <div className="document-page">
      <h2>Document Management</h2>
      
      <div className="document-section">
        <h3>Your Documents</h3>
        <ul className="documents-list">
          {tenantDocuments.map((doc, index) => (
            <li key={index} className="document">
              <Button variant="link" href={`${process.env.PUBLIC_URL}/documents/${doc}`} download>{doc}</Button>
            </li>
          ))}
        </ul>
      </div>

      <div className="document-section">
        <h3>General Documents & Announcements</h3>
        <ul className="documents-list">
          {generalDocuments.map((doc, index) => (
            <li key={index} className="document">
              {/* direkter download für myflattestpdf.pdf */}
              {doc === 'myflattestpdf.pdf' ? (
                <a href={`${process.env.PUBLIC_URL}/documents/myflattestpdf.pdf`} download>
                  <Button variant="link">{doc}</Button>
                </a>
              ) : (
                <Button variant="link" href={`${process.env.PUBLIC_URL}/documents/${doc}`} download>{doc}</Button>
              )}
            </li>
          ))}
        </ul>
      </div>

      <div className="document-section">
        <h3>Archived Documents</h3>
        <ul className="documents-list">
          {archivedDocuments.map((doc, index) => (
            <li key={index} className="document">
              <Button variant="link" href={`${process.env.PUBLIC_URL}/documents/${doc}`} download>{doc}</Button>
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
};

export default DocumentPageTenant;
