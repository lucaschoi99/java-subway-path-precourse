package subway.validation;

import subway.domain.StationRepository;

import static subway.ErrorMessage.INVALID_STATION_NAME;

public class ValidateUserInput {

    public static boolean validateMode(String input) {
        if (input.equals("1") || input.equals("Q")) {
            return true;
        }
        return false;
    }

    public static boolean validateFunc(String input) {
        if (input.equals("1") || input.equals("2") || input.equals("B")) {
            return true;
        }
        return false;
    }

    public static boolean validateStation(String input) {
        try {
            StationRepository.findStationByName(input);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
