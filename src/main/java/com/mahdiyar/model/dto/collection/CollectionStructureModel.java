package com.mahdiyar.model.dto.collection;

import com.mahdiyar.model.BaseModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author mahdiyar
 */
@Data
@NoArgsConstructor
public class CollectionStructureModel extends BaseModel {
    private String name;
    private boolean favorite;
    private int requestsCount;
    private List<DirectoryModel> directories;
}
