import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class ViewProgram {

    public void run() {

        int settingsExit = 0;
        int changes = 0;

        ToyShop ts = new ToyShop();
        List<Toy> toyList = new ArrayList<Toy>();
        PriorityQueue<Toy> prizeList2 = new PriorityQueue<Toy>(new ToyComparator());

        ts.startDate();

// Код для быстрой проверки программы, при необходимости закомментировать.
// Начало тестового блока:
        Toy toy1 = new Toy(1, "toy1", 5, 1);
        Toy toy2 = new Toy(2, "toy2", 3, 1);
        Toy toy3 = new Toy(3, "toy3", 2, 1);

        toyList.add(toy1);
        toyList.add(toy2);
        toyList.add(toy3);

        ts.uniformFrequency(toyList);
        ts.showToys(toyList);
        ts.createPrizeList(toyList, prizeList2);
        ts.showPrizeList(prizeList2);

        for (int i = 0; i < 10; i++) {
            ts.getPrize(prizeList2, toyList);
        }
// Конец тестового блока.

        while (true) {
            settingsExit = 0;
            changes = 0;

            String cmd = prompt("\nВыберите команду:\n1 - Получить приз\n2 - Настройки\n3 - Завершить программу\n\nВведите команду: ");

            switch (cmd) {
                case "1":
                    ts.getPrize(prizeList2, toyList);
                    break;
                case "2":
                    while (settingsExit != 1) {
                        System.out.println("changes: " + changes);
                        String cmd2 = prompt("\nВыберите команду:\n1 - Показать список игрушек\n2 - Показать призовой список игрушек для выдачи\n3 - Изменить количество игрушек" +
                                "\n4 - Изменить коэффициент частоты\n5 - Добавить новый вид игрушек\n6 - Удалить вид игрушек" +
                                "\n7 - Создать новый призовой список согласно новым настройкам и выйти из настроек\n\nВведите команду: ");
                        switch (cmd2) {
                            case "1":
                                ts.showToys(toyList);
                                break;
                            case "2":
                                ts.showPrizeList(prizeList2);
                                break;
                            case "3":
                                changes = changes + ts.setQuantity(toyList);
                                break;
                            case "4":
                                changes = changes + ts.newFrequencyCoef(toyList);
                                break;
                            case "5":
                                changes = changes + ts.addNewToy(toyList);
                                break;
                            case "6":
                                changes = changes + ts.deliteToy(toyList);
                                break;
                            case "7":
                                settingsExit = ts.applyChanges(toyList, prizeList2, changes);;
                                break;
                            default:
                                System.out.println("Команда введена неверно.");
                        }
                    }
                    break;
                case "3":
                    System.out.println("Программа завершена.");
                    System.exit(0);

//                case "5":
//                    System.out.println("TEST");
//
//                    ts.newFrequencyCoef(toyList);
//                    break;

                default:
                    System.out.println("Команда введена неверно.");
            }
        }
    }

    private String prompt(String message) {
        Scanner in = new Scanner(System.in);
        System.out.print(message);
        return in.nextLine();
    }

}
