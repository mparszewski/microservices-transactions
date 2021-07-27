package com.mparszewski.auth.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewRequestDto {
    private String username;
    private Long reviewId;
}
