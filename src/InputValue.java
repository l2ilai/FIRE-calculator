import java.util.Scanner;

public class InputValue {
    public void setValueYear() throws YearException {

        Calculate inputYear = new Calculate();

        //Принимём данных с клавиатуры и преобразорвание в целое число
        try {
            String scanner = new Scanner(System.in).nextLine();
            int year = Integer.parseInt(scanner);
            if (!(year >= 2002 & year <= 2021)) {
                throw new YearException("Year should be from 2002 to 2021 inclusive");
            }
            inputYear.setYear(year);
        } catch (NumberFormatException e) {
            throw new YearException("String is incorrect ", e);
        }
    }
}
