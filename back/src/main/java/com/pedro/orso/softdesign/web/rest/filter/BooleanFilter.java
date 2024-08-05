package com.pedro.orso.softdesign.web.rest.filter;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BooleanFilter {

    private Boolean equals;

    public BooleanFilter(BooleanFilter filter) {
        this.equals = filter.equals;
    }
}