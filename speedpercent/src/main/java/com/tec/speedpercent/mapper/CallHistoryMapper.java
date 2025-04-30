package com.tec.speedpercent.mapper;

import com.tec.speedpercent.domain.dto.CallHistoryRequestDto;
import com.tec.speedpercent.domain.dto.CallHistoryResponseDto;
import com.tec.speedpercent.domain.entity.CallHistory;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CallHistoryMapper {

    CallHistoryMapper MAPPER = Mappers.getMapper(CallHistoryMapper.class);


    CallHistoryResponseDto callHistoryResponseDto(CallHistory callHistory);


    CallHistory toCallHistory(CallHistoryRequestDto callHistoryRequestDto);
}
