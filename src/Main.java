public class Main {
    public static void main(String[] args) throws YearException {

        Welcome welcome = new Welcome();
        InputValue value = new InputValue();
        Calculate calc = new Calculate();

        //Выводим окно приветсвия в терминал
        welcome.printWelcome();

        //Вводим данные с клавиатуры
        value.setValueYear();

        //Рассчитываем и выводим результат
        System.out.println(calc.calcMaxWithdrawalPercentage());

    }
}