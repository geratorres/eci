package com.encora.eci.dtos.reports;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class LocationReportDto {
    private List<CountryData> countries;

    @Data
    public static class CountryData {
        private String country;

        @Setter(AccessLevel.NONE)
        private Map<String, List<Object>> states = new HashMap<>();

        public void addStateInfo(String state, List<Object> stateData){
            states.put(state, stateData);
        }
    }
}
