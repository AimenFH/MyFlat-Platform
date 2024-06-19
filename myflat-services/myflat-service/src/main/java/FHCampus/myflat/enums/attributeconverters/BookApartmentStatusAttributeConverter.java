package fhcampus.myflat.enums.attributeconverters;

import fhcampus.myflat.enums.BookApartmentStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class BookApartmentStatusAttributeConverter implements AttributeConverter<BookApartmentStatus, String> {

    @Override
    public String convertToDatabaseColumn(BookApartmentStatus bookApartmentStatus) {
        if (bookApartmentStatus == null) return null;
        return bookApartmentStatus.name();
    }

    @Override
    public BookApartmentStatus convertToEntityAttribute(String bookApartmentStatusString) {
        if (bookApartmentStatusString == null) return null;
        return BookApartmentStatus.valueOf(bookApartmentStatusString);
    }
}