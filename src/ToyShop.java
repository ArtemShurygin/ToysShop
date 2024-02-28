import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.PriorityQueue;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class ToyShop {

    /**
     * Возвращает отформатированную дату dd.MM.yyyy HH:mm:ss
     * @return
     */
    public String dateNow() {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    /**
     * Записывает время запуска программу в файл prizeList.txt
     */
    public void startDate() {
//        System.out.println("startDate:");
//        System.out.println(dateNow());

        String thisDirectory = System.getProperty("user.dir");
        try{
            File file = new File(thisDirectory   + "\\prizeList.txt");

            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file,true);
            BufferedWriter bw = new BufferedWriter(fw);
            fw.write("\nProgram start on: " + dateNow() + "\n");
            bw.close();
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Выводит список игрушек
     * @param toyList список игрушек
     */
    public void showToys(List<Toy>  toyList) {
        System.out.println("Список игрушек:");
        if (toyList.isEmpty()) {
            System.out.println("Список игрушек пуст.");
        }
        else {
            for (Toy toy : toyList) {
                System.out.println(toy);
            }
        }
    }

    /**
     * Выводит призовой список/очередь
     * @param prizeList призовой список
     */
    public void showPrizeList(PriorityQueue<Toy> prizeList) {
        System.out.println("Призовой список игрушек:");
        if (prizeList.isEmpty()) {
            System.out.println("Призовой список игрушек пуст.");
        }
        else {
            for (Toy toy : prizeList) {
                System.out.println("id: " + toy.getId() + ", name: " + toy.getName() + ", weight: " + toy.getWeight());
            }
        }
    }

    /**
     * Задает частоту для каждой игрушки в зависимости от количества игрушек frequencyCoef = (number of these toys) / (number of all toys)
     * @param toyList список игрушек
     */
    public void uniformFrequency(List<Toy>  toyList) {
//        System.out.println("uniformFrequency:");
        int totalQuantity = 0;
        for (Toy toy : toyList) {
            totalQuantity = totalQuantity + toy.getQuantity();
        }
        //System.out.println("totalQuantity: " + totalQuantity);
        for (Toy toy : toyList) {
            toy.setFrequencyCoef((double) toy.getQuantity() / (double) totalQuantity);
        }
    }

    /**
     * Создает призовой список/очередь, очередь отсортирована по весу игрушек
     * @param toyList список игрушек
     * @param prizeList призовая очередь
     */
    public void createPrizeList(List<Toy>  toyList, PriorityQueue<Toy> prizeList) {
//        System.out.println("createPrizeList");
        prizeList.clear();
        int prizeId = -1;
        String prizeName = null;

        for (Toy toy : toyList) {
            if (toy.getFrequencyCoef() > 0) {
                prizeId = toy.getId();
                prizeName = toy.getName();
                for (int i = 0; i < toy.getQuantity(); i++) {
                    Toy prizeToy = new Toy(prizeId, prizeName, (int) (Math.random() * toy.getFrequencyCoef()*Math.pow(10,6)));
                    prizeList.add(prizeToy);
                }
            }
        }
    }

    /**
     * Выдает следующую игрушку из призового списка и записывает в файл prizeList.txt
     * @param prizeList
     * @param toyList
     */
    public void getPrize(PriorityQueue<Toy> prizeList, List<Toy>  toyList) {
        // System.out.println("getPrize:");
        // System.out.println(dateNow());

        if (prizeList.isEmpty()) {
            System.out.println("Игрушки закончились.");
        }
        else {
            Toy newPrizeToy = prizeList.poll();
            System.out.println("Вы получили: " + newPrizeToy.getName());

            // Уменьшает количество игрушек в списке по видам игрушек
            Toy toyById = findToyById(toyList, newPrizeToy.getId());
            toyById.setQuantity(toyById.getQuantity() - 1);

            String thisDirectory = System.getProperty("user.dir");
            try{
                File file = new File(thisDirectory   + "\\prizeList.txt");

                if (!file.exists()) {
                    file.createNewFile();
                }

                FileWriter fw = new FileWriter(file,true);
                BufferedWriter bw = new BufferedWriter(fw);
                fw.write(dateNow() + ", Prize: ");
                fw.write(String.valueOf(newPrizeToy.getName()));
                bw.newLine();
                bw.close();
            }
            catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Изменяет количество игрушек игрушки с заданным id
     * @param toyList список игрушек
     * @return возвращает 1, если были совершены изменения
     */
    public int setQuantity(List<Toy>  toyList) {
        try {
            int idFind = promptInt("Введите id игрушки, количество которых вы хотите изменить: ");
            if (findToyById(toyList, idFind) != null){
                int newQuantity = promptInt("Введите новое количество игрушек: ");
                if (newQuantity >= 0) {
                    findToyById(toyList, idFind).setQuantity(newQuantity);
                    System.out.println("Количество игрушек с id:" + idFind + " изменено на " + newQuantity +".");
                    return 1;
                }
                else {
                    System.out.println("Ошибка ввода, количество не может быть отрицательным. Действие отменено.");
                }
            }
            else {
                System.out.println("Игрушки с таким id не существует.");
            }
        }
        catch (Exception e) {
            System.out.println("Ошибка ввода, нужно ввести число. Действие отменено.\n" + e.getMessage());
        }
        return 0;
    }

    /**
     * Изменяет коэффициент частоты игрушки с заданным id
     * @param toyList список игрушек
     * @return возвращает 1, если были совершены изменения
     */
    int newFrequencyCoef (List<Toy>  toyList) {
        try {
            int idFind = promptInt("Введите id игрушки, для которой хотите изменить коэффициент частоты: ");
            if (findToyById(toyList, idFind) != null){
                int newFrequencyCoef = promptInt("Введите новый коэффициент частоты: ");
                if (newFrequencyCoef >= 0) {
                    findToyById(toyList, idFind).setFrequencyCoef(newFrequencyCoef);
                    System.out.println("Коэффициент частоты игрушек с id:" + idFind + " изменен на " + newFrequencyCoef +".");
                    return 1;
                }
                else {
                    System.out.println("Ошибка ввода, коэффициент частоты не может быть отрицательным. Действие отменено.");
                }
            }
            else {
                System.out.println("Игрушки с таким id не существует.");
            }
        }
        catch (Exception e) {
            System.out.println("Ошибка ввода, нужно ввести число. Действие отменено.\n" + e.getMessage());
        }
        return 0;
    }

    /**
     * Добавляет новый вид игрушек с уникальным id
     * @param toyList список игрушек
     * @return возвращает 1, если были совершены изменения
     */
    public int addNewToy(List<Toy>  toyList) {
        try {
            int id = 0;
            if (toyList.isEmpty()){
                id = 1;
            }
            else {
                id = toyList.get(toyList.size() - 1).getId() + 1;
            }
            String name = prompt("Введите название новой игрушки: ");
            if (name != "") {
                int quantity = promptInt("Введите количество игрушек: ");
                if (quantity >= 0) {
                    int frequencyCoef = promptInt("Введите частотный коэффициент: ");
                    if (frequencyCoef >= 0) {
                        Toy newToy = new Toy(id, name, quantity, frequencyCoef);
                        toyList.add(newToy);
                        System.out.println("Добавлено игрушка: " + newToy);
                        return 1;
                    }
                    else {
                        System.out.println("Ошибка ввода, частотный коэффициент не может быть отрицательным. Действие отменено.");
                    }
                }
                else {
                    System.out.println("Ошибка ввода, количество не может быть отрицательным. Действие отменено.");
                }
            }
            else {
                System.out.println("Ошибка ввода, название должно содержать хотя бы 1 символ. Действие отменено.");
            }
        }
        catch (Exception e) {
            System.out.println("Ошибка ввода. Действие отменено.\n" + e.getMessage());
        }
        return 0;
    }

    /**
     * Удаляет игрушку с заданным id
     * @param toyList список игрушек
     * @return
     */
    int deliteToy(List<Toy>  toyList) {
        try {
            int idFind = promptInt("Введите id игрушки, которую хотите удалить из списка: ");
            for (int  i = 0;i  < toyList.size(); i++) {
                if (toyList.get(i).getId() == idFind) {
                    toyList.remove(i);
                    //ok = 0;
                    System.out.println("Игрушки с id:" + idFind + " удалена.");
                    return 1;
                }
            }
            System.out.println("Игрушки с таким id не существует.");
        }
        catch (Exception e) {
            System.out.println("Ошибка ввода, нужно ввести число. Действие отменено.\n" + e.getMessage());
        }
        return 0;
    }



    /**
     * Если были совершены изменения, то предлагает рассчитать вес игрушек в призовом списке в зависимости от количества
     * игрушек или на основе введенных частотных коэффициентов; создает новый призовой список, если совершены изменения
     * @param toyList список игрушек
     * @param prizeList призовой список
     * @param changes проверяет, были ли совершены какие-либо изменения
     * @return возвращает 0, если введена неверно команда для расчета частоты
     */
    public int applyChanges(List<Toy>  toyList, PriorityQueue<Toy> prizeList, int changes) {
        if (changes != 0){
            String cmd = prompt("Введите:\n1 - для формирования призового списка на основе введенных частотных коэффициентов" +
                    "\n2 - Задать частотные коэффициенты в зависимости от количества игрушек (frequencyCoef = (number of these toys) / (number of all toys))\nВаша команда: ");
            if(cmd.equals("1") || cmd.equals("2")) {
                if (cmd.equals("2")) {
                    uniformFrequency(toyList);
                }
                createPrizeList(toyList, prizeList);
                System.out.println("Настройки применены.");
                return 1;
            }
            else {
                System.out.println("Команда введена неверно, действие отменено");
                return 0;
            }
        }
        else {
            System.out.println("Изменения не производились, ничего не изменено.");
            return 1;
        }
    }


    /**
     * Ищет игрушку в списке с заданным id
     * @param toyList список игрушек
     * @param id параметр поиска
     * @return
     */
    public Toy findToyById(List<Toy>  toyList, int id) {

        for (int  i = 0;i  < toyList.size(); i++) {
            if(toyList.get(i).getId() == id){
                return toyList.get(i);
            }
        }
        return null;
    }

    private String prompt(String message) {
        Scanner in = new Scanner(System.in);
        System.out.print(message);
        return in.nextLine();
    }

    private int promptInt(String message) {
        Scanner in = new Scanner(System.in);
        System.out.print(message);
        return Integer.parseInt(in.nextLine());
    }
}
