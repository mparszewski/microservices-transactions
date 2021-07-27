package com.mparszewski.warehouse.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewDto {

    private String comment;
    private int rating;
    private long productId;
}
