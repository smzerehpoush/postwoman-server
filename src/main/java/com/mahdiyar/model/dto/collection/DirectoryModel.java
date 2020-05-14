package com.mahdiyar.model.dto.collection;

import com.mahdiyar.model.BaseModel;
import com.mahdiyar.model.dto.RequestModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author mahdiyar
 */
@Data
@NoArgsConstructor
public class DirectoryModel extends BaseModel {
    private String name;
    private List<RequestModel> requests;
    private List<DirectoryModel> directories;
}
