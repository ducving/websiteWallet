package com.example.websitewallet.mapper;

import com.example.websitewallet.dto.request.BudgetCreateRequest;
import com.example.websitewallet.dto.request.BudgetUpdateRequest;
import com.example.websitewallet.dto.response.BudgetResponse;
import com.example.websitewallet.entity.Budget;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BudgetMapper {
    @Mapping(target = "classify.id", source = "id_classify")
    Budget tobudget(BudgetCreateRequest request);

    @Mapping(target = "id_classify", source = "classify.id")
    BudgetResponse toBudgetResponse(Budget budget);

    @Mapping(target = "user", ignore = true)
    void updateBudget(@MappingTarget Budget budget, BudgetUpdateRequest request);
}
