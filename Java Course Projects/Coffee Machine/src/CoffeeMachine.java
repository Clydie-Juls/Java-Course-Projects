package machine;

import java.util.Scanner;

public class CoffeeMachine {

    public static void main(String[] args) {
        CoffeeMachineActions coffeeMachineActions =
                new CoffeeMachineActions(400, 540, 120, 9, 550);

        while (coffeeMachineActions.machineState != MachineState.EXIT) {
            Scanner scanner = new Scanner(System.in);
            String input = scanner.next();
            coffeeMachineActions.invokeAction(input);
        }
    }
}
