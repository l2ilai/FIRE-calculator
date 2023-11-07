import java.util.Scanner;

public class Calculate {
    private int year;
    private final int MIN_INPUT_YEAR = 2002;
    private final int MAX_INPUT_YEAR = 2021;

    //Принимём данных с клавиатуры и преобразорвание в целое число
    public void setValueYear() throws YearException {
        try {
            String scanner = new Scanner(System.in).nextLine();
            year = Integer.parseInt(scanner);
            if (!(year >= MIN_INPUT_YEAR & year <= MAX_INPUT_YEAR)) {
                throw new YearException("Year should be from 2002 to 2021 inclusive");
            }
        } catch (NumberFormatException e) {
            throw new YearException("String is incorrect ", e);
        }
    }

    //Вычесление массива долей дохода за все года
    private double[] getArrayRevenueShare() {
        double[] revenue_share = new double[Constants.MOEX_RATE.length - 1];
        for (int i = 0; i < Constants.MOEX_RATE.length - 1; i++) {
            revenue_share[i] = (Constants.MOEX_RATE[i + 1] - Constants.MOEX_RATE[i]) / Constants.MOEX_RATE[i];
        }
        return revenue_share;
    }

    //Вычесление кол-ва изымаемых средств в первом году
    private double calcWithdrawalCapitalStartingYear(double currentWithdrawalPercentage, double capitalCurrentYear) {
        double currentWithdrawalFractions = currentWithdrawalPercentage / 100;    // перевод из проценты в доли
        return currentWithdrawalFractions * capitalCurrentYear;
    }

    //Вычесление дохода с биржы за год
    private double calcIncomeCapital(double remainingCapital, int indexArrayInputYear) {
        double[] revenue_share = getArrayRevenueShare();                    //доли дохода с биржи за год
        return remainingCapital * revenue_share[indexArrayInputYear] + remainingCapital;
    }

    //Вычесление изымания в следующем году с учётом инфляции
    private double calcWithdrawalCapitalCurrentYear(double withdrawalCapitalCurrentYear, int indexArrayInputYear) {
        double inflation_fractions = Constants.INFLATION_RATE[indexArrayInputYear] / 100; // перевод из проценты в доли
        return withdrawalCapitalCurrentYear * inflation_fractions + withdrawalCapitalCurrentYear;
    }

    //Поиск минимального кипатала при текущем  проценте изъятия
    private boolean isMinCapitalCurrentYear(double currentWithdrawalPercentage) {
        double capitalCurrentYear;                       //капитал текуцего года
        double remainingCapital;                           //отстаток текучего года
        double withdrawalCapitalNextYear;                //изымания в следующем году с учётом инфляции
        int indexArrayInputYear = year - MIN_INPUT_YEAR;  // вычесление первого индекса массива для текущего года

        capitalCurrentYear = 1000.0;
        //изымание в начале первого года
        withdrawalCapitalNextYear = calcWithdrawalCapitalStartingYear(currentWithdrawalPercentage, capitalCurrentYear);
        for (int currentYear = year; currentYear <= MAX_INPUT_YEAR; currentYear++) {

            //остаток текучего года
            remainingCapital = capitalCurrentYear - withdrawalCapitalNextYear;

            //доход за текущий год
            capitalCurrentYear = calcIncomeCapital(remainingCapital, indexArrayInputYear);

            //изымания в следующем году с учётом инфляции
            withdrawalCapitalNextYear = calcWithdrawalCapitalCurrentYear(withdrawalCapitalNextYear, indexArrayInputYear);
            indexArrayInputYear += 1;
        }
        return capitalCurrentYear < 0;
    }

    //Вычесление максмального процента изъятия
    protected double calcMaxWithdrawalPercentage() {
        double maxWithdrawalPercentage = 100;              //максимальный процент изъятия

        if (year == MAX_INPUT_YEAR) {
            return maxWithdrawalPercentage;
        }
        for (double currentWithdrawalPercentage = 0.5; maxWithdrawalPercentage > currentWithdrawalPercentage; currentWithdrawalPercentage += 0.5) {
            if (isMinCapitalCurrentYear(currentWithdrawalPercentage)) {
                maxWithdrawalPercentage = currentWithdrawalPercentage - 0.5;
            }
        }
        return maxWithdrawalPercentage;
    }
}
