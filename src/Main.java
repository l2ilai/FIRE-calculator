public class Main {
    public static void main(String[] args) {

        Welcome welcome = new Welcome();
        InputValue value = new InputValue();
        Calculate calc = new Calculate();

        //Выводим окно приветсвия в терминал
        welcome.printWelcome();

        //Вводим данные с клавиатуры
        try {
            value.setValueYear();
        } catch (YearException e) {
            e.printStackTrace();
            System.exit(1);
        }

        //Рассчитываем и выводим на экран
        System.out.println(calc.getWithdrawalPercentage());

    }
}