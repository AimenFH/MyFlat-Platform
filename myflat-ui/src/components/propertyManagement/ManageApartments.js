import React, { useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Container, Button, ListGroup, ListGroupItem } from 'react-bootstrap';


const ManageApartments = () => {
  let { propertyId } = useParams();
  let navigate = useNavigate();

  const [apartments, setApartments] = useState([
    { id: 1, name: 'Top1' },
    { id: 2, name: 'Top2' },
  ]);

  const addApartment = () => {
    const newId = apartments.length > 0 ? apartments[apartments.length - 1].id + 1 : 1;
    const newName = `Top${newId}`;
    setApartments([...apartments, { id: newId, name: newName }]);
  };

  const deleteApartment = (id) => {
    setApartments(apartments.filter(apartment => apartment.id !== id));
  };

  const editApartment = (apartmentId) => {
    navigate(`/properties/${propertyId}/apartments/${apartmentId}/edit`);
  };

  return (
    <div>
       <Container className="mt-5">
    <h2 className="mb-3">Verwaltung der Apartments für Eigentum ID: {propertyId}</h2>
    <Button onClick={addApartment} variant="primary" className="mb-3">Neues Apartment hinzufügen</Button>
    <ListGroup>
      {apartments.map(apartment => (
        <ListGroupItem key={apartment.id} className="d-flex justify-content-between align-items-center">
          {apartment.name}
          <div>
            <Button onClick={() => editApartment(apartment.id)} variant="outline-secondary" className="me-2">Bearbeiten</Button>
            <Button onClick={() => deleteApartment(apartment.id)} variant="outline-danger">Löschen</Button>
          </div>
        </ListGroupItem>
      ))}
    </ListGroup>
  </Container>
    </div>
  );
};

export default ManageApartments;
