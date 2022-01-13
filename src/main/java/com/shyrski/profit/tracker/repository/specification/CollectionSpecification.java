package com.shyrski.profit.tracker.repository.specification;


import static org.apache.commons.lang3.ObjectUtils.isEmpty;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.shyrski.profit.tracker.model.db.Collection;
import com.shyrski.profit.tracker.model.dto.collection.CollectionSearchDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CollectionSpecification implements Specification<Collection> {

    private final CollectionSearchDto collectionSearchDto;

    @Override
    public Predicate toPredicate(Root<Collection> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicateList = new ArrayList<>();

        if (collectionSearchDto == null || isEmpty(collectionSearchDto.getPortfolioId())) {
            return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
        }

        predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("portfolio").get("portfolioId"),
                collectionSearchDto.getPortfolioId())));

        if (isNotEmpty(collectionSearchDto.getNetwork())) {
            predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("network").get("name"),
                    collectionSearchDto.getNetwork())));
        }

        if (isNotEmpty(collectionSearchDto.getName())) {
            predicateList.add(criteriaBuilder.and(criteriaBuilder.like(root.get("name"),
                    "%" + collectionSearchDto.getName() + "%")));
        }

        return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
    }

    public CollectionSearchDto collectionSearchDto() {
        return collectionSearchDto;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (CollectionSpecification) obj;
        return Objects.equals(this.collectionSearchDto, that.collectionSearchDto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(collectionSearchDto);
    }

    @Override
    public String toString() {
        return "CollectionSpecification[" +
                "collectionSearchDto=" + collectionSearchDto + ']';
    }

}
