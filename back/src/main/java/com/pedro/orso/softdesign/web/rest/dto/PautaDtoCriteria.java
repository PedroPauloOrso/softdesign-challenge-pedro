package com.pedro.orso.softdesign.web.rest.dto;

import com.pedro.orso.softdesign.web.rest.filter.BooleanFilter;
import com.pedro.orso.softdesign.web.rest.filter.LongFilter;
import com.pedro.orso.softdesign.web.rest.filter.StringFilter;
import com.pedro.orso.softdesign.web.rest.filter.ZonedDateTimeFilter;
import lombok.Data;

import java.io.Serializable;

@Data
public class PautaDtoCriteria implements Serializable {

    private LongFilter id;

    private StringFilter title;

    private BooleanFilter isStarted;

    public PautaDtoCriteria() {
        this.id = new LongFilter();
        this.title = new StringFilter();
        this.isStarted = new BooleanFilter();
    }

    public PautaDtoCriteria(PautaDtoCriteria other) {
        this.id = other.id == null ? null : new LongFilter(other.id);
        this.title = other.title == null ? null : new StringFilter(other.title);
        this.isStarted = other.isStarted == null ? null : new BooleanFilter(other.isStarted);
    }

    public PautaDtoCriteria copy() {
        return new PautaDtoCriteria(this);
    }

}
