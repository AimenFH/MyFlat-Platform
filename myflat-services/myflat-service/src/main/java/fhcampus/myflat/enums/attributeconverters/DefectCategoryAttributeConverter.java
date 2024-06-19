package fhcampus.myflat.enums.attributeconverters;

import fhcampus.myflat.enums.DefectCategory;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class DefectCategoryAttributeConverter implements AttributeConverter<DefectCategory, String> {

    @Override
    public String convertToDatabaseColumn(DefectCategory defectCategory) {
        if (defectCategory == null) return null;
        return defectCategory.name();
    }

    @Override
    public DefectCategory convertToEntityAttribute(String defectCategoryString) {
        if (defectCategoryString == null) return null;
        return DefectCategory.valueOf(defectCategoryString);
    }
}