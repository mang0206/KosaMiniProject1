package com.example.unimeeting.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CudResponse {
    private boolean isSuccess;
    private String message;
}
