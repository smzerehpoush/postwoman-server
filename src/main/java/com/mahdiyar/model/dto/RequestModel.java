package com.mahdiyar.model.dto;

import com.mahdiyar.model.BaseModel;
import com.mahdiyar.model.BodyModel;
import io.swagger.models.HttpMethod;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author mahdiyar
 */
@Data
@NoArgsConstructor
public class RequestModel extends BaseModel {
    private HttpMethod method;
    private String address;
    private Map<String, String> params;
    private Map<String, String> headers;
    private BodyModel body;
    private String preRequestScript;
    private String tests;
}
