package ru.practicum.location.model;

public final class LocationMapper {
    private LocationMapper() {
    }

    public static LocationDto locationToLocationDto(Location location) {
        return LocationDto.builder()
                .lat(location.getLat())
                .lon(location.getLon())
                .build();
    }

    public static Location locationDtoToLocation(LocationDto locationDto) {
        return Location.builder()
                .lat(locationDto.getLat())
                .lon(locationDto.getLon())
                .build();
    }
}
