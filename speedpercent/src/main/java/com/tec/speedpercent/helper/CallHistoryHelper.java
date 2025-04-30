package com.tec.speedpercent.helper;

import com.tec.speedpercent.domain.dto.CallHistoryRequestDto;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;

public class CallHistoryHelper {

    private CallHistoryHelper() {
    }

    public static CallHistoryRequestDto loadData(LocalDateTime date, String endpoint, String parameterJson, double response, String error) {
        return new CallHistoryRequestDto(
                date, endpoint, parameterJson, response, error
        );
    }

    //for validation for openapi pageable
    public static boolean validateSorName(Sort sort) {
        if (sort.isSorted()) {
            return !sort.iterator().next().getProperty().equals("string");
        } else {
            System.out.println("No sort criteria applied");
            return true;
        }
    }
}
