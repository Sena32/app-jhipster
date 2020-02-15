package com.sena.app.service.mapper;


import com.sena.app.domain.*;
import com.sena.app.service.dto.FestaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Festa} and its DTO {@link FestaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FestaMapper extends EntityMapper<FestaDTO, Festa> {



    default Festa fromId(Long id) {
        if (id == null) {
            return null;
        }
        Festa festa = new Festa();
        festa.setId(id);
        return festa;
    }
}
