package fhcampus.myflat.enums.attributeconverters;

import fhcampus.myflat.enums.DefectStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class DefectStatusAttributeConverter implements AttributeConverter<DefectStatus, String> {

    @Override
    public String convertToDatabaseColumn(DefectStatus defectStatus) {
        if (defectStatus == null) return null;
        return defectStatus.name();
    }

    @Override
    public DefectStatus convertToEntityAttribute(String defectStatusString) {
        if (defectStatusString == null) return null;
        return DefectStatus.valueOf(defectStatusString);
    }
}