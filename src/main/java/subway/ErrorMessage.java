package subway;

public enum ErrorMessage {

    INVALID_USER_INPUT("[ERROR] 올바른 기능을 입력해주세요."),
    INVALID_STATION_NAME("[ERROR] 존재하지 않는 역 이름입니다."),
    SAME_START_AND_END("[ERROR] 출발역과 도착역이 동일합니다."),
    ;

    private String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
