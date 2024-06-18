package fhcampus.myflat.enums.attributeconverters;

import fhcampus.myflat.enums.UserRole;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class UserRoleAttributeConverter implements AttributeConverter<UserRole, String> {

    @Override
    public String convertToDatabaseColumn(UserRole userRole) {
        if (userRole == null) return null;
        return userRole.name();
    }

    @Override
    public UserRole convertToEntityAttribute(String userRoleString) {
        if (userRoleString == null) return null;
        return UserRole.valueOf(userRoleString);
    }
}