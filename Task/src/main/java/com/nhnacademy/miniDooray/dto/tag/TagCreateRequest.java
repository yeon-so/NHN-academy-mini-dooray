package com.nhnacademy.miniDooray.dto.tag;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TagCreateRequest {
    @NotNull
    String tagName;
}
