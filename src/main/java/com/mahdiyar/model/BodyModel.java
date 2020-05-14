package com.mahdiyar.model;

import com.mahdiyar.model.enums.BodyType;
import com.mahdiyar.model.enums.RawType;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mahdiyar
 */
@Data
@NoArgsConstructor
public class BodyModel {
    private BodyType type;
    private RawType rawType;
    private String value;
}
