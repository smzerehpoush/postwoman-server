package com.mahdiyar.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mahdiyar
 */
@Data
@NoArgsConstructor
public class BaseCollectionModel extends BaseModel {
    private String name;
    private int requestsCount;
}
