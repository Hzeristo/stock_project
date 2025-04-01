package com.haydenshui.stock.lib.dto.position;

import org.springframework.beans.BeanUtils;

import com.haydenshui.stock.lib.entity.position.Position;

public class PositionMapper {
    
    private PositionMapper() {}

    public static PositionDTO toDTO(Position position) {
        if(position == null) 
            return null;
        PositionDTO positionDTO = new PositionDTO();
        BeanUtils.copyProperties(position, positionDTO);
        return positionDTO;
    }

    public static Position toEntity(PositionDTO positionDTO) {
        if(positionDTO == null) 
            return null;
        Position position = new Position();
        BeanUtils.copyProperties(positionDTO, position);
        return position;
    }
}
