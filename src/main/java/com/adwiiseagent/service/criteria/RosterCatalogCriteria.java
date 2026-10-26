package com.adwiiseagent.service.criteria;

import java.io.Serializable;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Filters for roster catalog lists. {@code tenantId.equals} is intentionally absent: lists always
 * return shared rows plus the caller tenant.
 */
@ParameterObject
public class RosterCatalogCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;
    private StringFilter code;
    private StringFilter label;
    private BooleanFilter isActive;
    private IntegerFilter sortOrder;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCode() {
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public StringFilter getLabel() {
        return label;
    }

    public void setLabel(StringFilter label) {
        this.label = label;
    }

    public BooleanFilter getIsActive() {
        return isActive;
    }

    public void setIsActive(BooleanFilter isActive) {
        this.isActive = isActive;
    }

    public IntegerFilter getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(IntegerFilter sortOrder) {
        this.sortOrder = sortOrder;
    }
}
