package br.com.css.radarsaude.domain.model.representation.util.mapper;

import org.springframework.data.domain.Page;

import java.util.Collection;

public interface MapperInterface<RESPONSEMODEL, ENTITY> {

    RESPONSEMODEL toResponseModel(ENTITY entity);

    Page<RESPONSEMODEL> toPage(Page<ENTITY> page);

    Collection<RESPONSEMODEL> toCollection(Page<ENTITY> page);

    Collection<RESPONSEMODEL> toCollection(Collection<ENTITY> collection);
}

