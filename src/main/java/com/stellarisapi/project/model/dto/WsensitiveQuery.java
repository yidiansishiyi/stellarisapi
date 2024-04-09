package com.stellarisapi.project.model.dto;

import com.stellarisapi.project.common.PageRequest;
import lombok.Data;

@Data
public class WsensitiveQuery extends PageRequest {

    private String keyword;

}
