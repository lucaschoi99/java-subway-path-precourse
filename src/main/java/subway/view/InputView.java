package subway.view;

import subway.validation.ValidateUserInput;

import java.util.Scanner;

public class InputView {

    // 메인 화면 기능 선택
    public String chooseMode(Scanner scanner) {
        String input = scanner.nextLine();
        if (ValidateUserInput.validateMode(input)) {
            return input;
        }
        throw new IllegalArgumentException();
    }

    // 경로 기준 선택
    public String selectFunc(Scanner scanner) {
        String input = scanner.nextLine();
        if (ValidateUserInput.validateFunc(input)) {
            return input;
        }
        throw new IllegalArgumentException();
    }

    // 출발지, 도착지 입력
    public String inputStation(Scanner scanner) {
        String input = scanner.nextLine();
        if (ValidateUserInput.validateStation(input)) {
            return input;
        }
        throw new IllegalArgumentException();
    }
}
