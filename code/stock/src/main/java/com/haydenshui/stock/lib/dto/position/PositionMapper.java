package com.haydenshui.stock.lib.dto.position;

import org.springframework.beans.BeanUtils;

import com.haydenshui.stock.lib.entity.position.Position;
import com.haydenshui.stock.lib.entity.position.PositionChangeLog;

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

    public static PositionChangeLogDTO toDTO(PositionChangeLog positionChangeLog) {
        if(positionChangeLog == null) 
            return null;
        PositionChangeLogDTO positionChangeLogDTO = new PositionChangeLogDTO();
        BeanUtils.copyProperties(positionChangeLog, positionChangeLogDTO);
        return positionChangeLogDTO;
    }

    public static PositionChangeLog toEntity(PositionChangeLogDTO positionChangeLogDTO) {
        if(positionChangeLogDTO == null) 
            return null;
        PositionChangeLog positionChangeLog = new PositionChangeLog();
        BeanUtils.copyProperties(positionChangeLogDTO, positionChangeLog);
        return positionChangeLog;
    }

    public static PositionTransactionalDTO toTransactionalDTO(Position position, String service, String bizType) {
        if(position == null) 
            return null;
        PositionTransactionalDTO positionTransactionalDTO = new PositionTransactionalDTO();
        BeanUtils.copyProperties(position, positionTransactionalDTO);
        return positionTransactionalDTO;
    }
}
