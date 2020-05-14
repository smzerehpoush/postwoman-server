package com.mahdiyar.model.converter;

import com.google.gson.Gson;
import com.mahdiyar.model.dto.collection.CollectionStructureModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;

/**
 * @author mahdiyar
 */
@RequiredArgsConstructor
@Component
public class CollectionStructureConverter implements AttributeConverter<CollectionStructureModel, String> {
    private final Gson gson;

    public String convertToDatabaseColumn(CollectionStructureModel attribute) {
        return gson.toJson(attribute);
    }

    @Override
    public CollectionStructureModel convertToEntityAttribute(String dbData) {
        return gson.fromJson(dbData, CollectionStructureModel.class);
    }
}
