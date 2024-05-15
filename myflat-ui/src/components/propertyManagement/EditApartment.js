// In ./components/EditApartment.js
import React from 'react';
import { useParams, useNavigate } from 'react-router-dom';

const EditApartment = () => {
  let { apartmentId } = useParams();
  let navigate = useNavigate();


  const saveApartmentDetails = () => {
    navigate(-1); // Zurück zur vorherigen Seite navigieren
  };

  return (
    <div>
      <h2>Apartment bearbeiten: {apartmentId}</h2>
      <form onSubmit={saveApartmentDetails}>
        <label>
          Apartment-Name:
          <input type="text" defaultValue={`Apartment ${apartmentId}`} />
        </label>
        <br />
        <label>
          Mieteranzahl:
          <input type="number" defaultValue="3" />
        </label>
        <br />
        <button type="submit">Speichern</button>
      </form>
    </div>
  );
};

export default EditApartment;
