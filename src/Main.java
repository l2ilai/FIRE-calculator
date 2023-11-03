public class Main {
    public static void main(String[] args) {

        InputValue value = new InputValue();

        Calculate calc = new Calculate();

        System.out.println( "//======================================\\\\\n" +
                            "//                                      \\\\\n" +
                            "//            Live to 2022              \\\\\n" +
                            "//                                      \\\\\n" +
                            "//======================================\\\\\n");

        System.out.println("Введите год начала жизни на проценты: ");

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