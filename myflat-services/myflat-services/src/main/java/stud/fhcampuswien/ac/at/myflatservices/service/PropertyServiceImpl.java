package stud.fhcampuswien.ac.at.myflatservices.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import stud.fhcampuswien.ac.at.myflatservices.entity.Property;
import stud.fhcampuswien.ac.at.myflatservices.service.base.PropertyService;

import java.util.List;

@AllArgsConstructor
@Service
public class PropertyServiceImpl implements PropertyService {
    @Override
    public Property saveProperty(Property property) {
        return null;
    }

    @Override
    public Property getProperty(Long id) {
        return null;
    }

    @Override
    public List<Property> getProperties() {
        return null;
    }

    @Override
    public Property deleteProperty(Long id) {
        return null;
    }
}
