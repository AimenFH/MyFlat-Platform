package stud.fhcampuswien.ac.at.myflatservices.service.base;

import stud.fhcampuswien.ac.at.myflatservices.entity.Property;

import java.util.List;

public interface PropertyService {
    Property saveProperty(Property property);

    Property getProperty(Long id);

    List<Property> getProperties();

    Property deleteProperty(Long id);
}
