package fhcampus.myflat.enums.attributeconverters;

import fhcampus.myflat.enums.DefectLocation;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class DefectLocationAttributeConverter implements AttributeConverter<DefectLocation, String> {

    @Override
    public String convertToDatabaseColumn(DefectLocation defectLocation) {
        if (defectLocation == null) return null;
        return defectLocation.name();
    }

    @Override
    public DefectLocation convertToEntityAttribute(String defectLocationString) {
        if (defectLocationString == null) return null;
        return DefectLocation.valueOf(defectLocationString);
    }
}