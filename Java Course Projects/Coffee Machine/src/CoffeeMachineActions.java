package machine;

public class CoffeeMachineActions {
    MachineState machineState = MachineState.STATIC;
    private int water = 400;
    private int milk = 540;
    private int coffeeBeans = 120;
    private int cups = 9;
    private int money = 550;

    public CoffeeMachineActions (int water, int milk, int coffeeBeans, int cups, int money) {
        this.water = water;
        this.milk = milk;
        this.coffeeBeans = coffeeBeans;
        this.cups = cups;
        this.money = money;
        displayActions();
    }

     void invokeAction(String input) {
         if (machineState.name().startsWith("FILL"))
         {
             fill(Integer.parseInt(input));
         }

        switch (machineState) {
            case STATIC:
                setAction(input);
                break;
            case BUYING:
                String order = input;
                if (!order.equals("back")) {
                    buy(Integer.parseInt(order));
                }
                machineState = MachineState.STATIC;
                displayActions();
                break;
        }
    }

    private void setAction(String input) {
        switch (input) {
            case "buy":
                machineState = machineState.BUYING;
                System.out.println("\nWhat do you want to buy? 1 - espresso," +
                        " 2 - latte, 3 - cappuccino, back - to main menu:");
                break;
            case "fill":
                machineState = machineState.FILL_WATER;
                System.out.println("\nWrite how many ml of water you want to add:");
                break;
            case "take":
                take();
                displayActions();
                break;
            case "remaining":
                displayResources();
                displayActions();
                break;
            case "exit":
                machineState = MachineState.EXIT;
                break;
        }
    }

    private void displayActions(){
        System.out.println("Write action (buy, fill, take, remaining, exit):");
    }

    private void displayResources() {
        System.out.println("\nThe coffee machine has:");
        System.out.println(water + " ml of water");
        System.out.println(milk + " ml of milk");
        System.out.println(coffeeBeans + " g of coffee beans");
        System.out.println(cups + " disposable cups");
        System.out.println("$" + money + " of money\n");
    }

    private boolean isEnoughResources(int waterReq, int coffeeBeansReq) {
        int missing = 0;

        if (water / waterReq == 0 || coffeeBeans / coffeeBeansReq == 0 || cups == 0) {
            System.out.print("Sorry, not enough ");
            System.out.print(water / waterReq == 0 ? "water" : "");
            missing += water / waterReq == 0 ? 1 : 0;
            System.out.print(coffeeBeans / coffeeBeansReq == 0 && missing > 0 ? ", coffee beans" :
                    coffeeBeans / coffeeBeansReq == 0 && missing > 0 ? ", coffee beans" : "");
            missing += coffeeBeans / coffeeBeansReq == 0 ? 1 : 0;
            System.out.print(cups == 0 && missing > 0 ? ", cups" : cups == 0 ? "cups" : "");
            System.out.println("!\n");
            return false;
        }
        return true;
    }
    private boolean isEnoughResources(int waterReq, int milkReq, int coffeeBeansReq) {
        int missing = 0;

        if (water / waterReq == 0 || milk / milkReq == 0 || coffeeBeans / coffeeBeansReq == 0 || cups == 0) {
            System.out.print("Sorry, not enough ");
            System.out.print(water / waterReq == 0 ? "water" : "");
            missing += water / waterReq == 0 ? 1 : 0;
            System.out.print(milk / milkReq == 0 && missing == 0 ? ", milk" : milk / milkReq == 0 ? "milk" : "");
            missing += milk / milkReq == 0 ? 1 :0;
            System.out.print(coffeeBeans / coffeeBeansReq == 0 && missing > 0 ? ", coffee beans" :
                    coffeeBeans / coffeeBeansReq == 0 && missing > 0 ? ", coffee beans" : "");
            missing += coffeeBeans / coffeeBeansReq == 0 ? 1 : 0;
            System.out.print(cups == 0 && missing > 0 ? ", cups" : cups == 0 ? "cups" : "");
            System.out.println("!\n");
            return false;
        }
        return true;
    }

    private void buy(int order) {
        switch (order) {
            case 1:
                if (isEnoughResources(250, 16)) {
                    System.out.println("I have enough resources, making you a coffee!\n");
                    water -= 250;
                    coffeeBeans -= 16;
                    money += 4;
                    cups--;
                }
                break;
            case 2:
                if (isEnoughResources(350, 75, 20)) {
                    System.out.println("I have enough resources, making you a coffee!\n");
                    water -= 350;
                    milk -= 75;
                    coffeeBeans -= 20;
                    money += 7;
                    cups--;
                }
                break;
            case 3:
                if (isEnoughResources(200, 100,12)) {
                    System.out.println("I have enough resources, making you a coffee!\n");
                    water -= 200;
                    milk -= 100;
                    coffeeBeans -= 12;
                    money += 6;
                    cups--;
                }
                break;
        }
    }

    private void fill(int input) {
        switch (machineState) {
            case FILL_WATER:
                water += input;
                System.out.println("Write how many ml of milk you want to add:");
                machineState = MachineState.FILL_MILK;
                break;
            case FILL_MILK:
                milk += input;
                System.out.println("Write how many grams of coffee beans you want to add:");
                machineState = MachineState.FILL_COFFEE_BEANS;
                break;
            case FILL_COFFEE_BEANS:
                coffeeBeans += input;
                System.out.println("Write how many disposable cups you want to add:");
                machineState = MachineState.FILL_CUPS;
                break;
            case FILL_CUPS:
                cups += input;
                machineState = MachineState.STATIC;
                System.out.println();
                displayActions();
                break;
        }
    }

    private void take() {
        System.out.println("\nI gave you $" + money);
        System.out.println();
        money = 0;
    }
}
