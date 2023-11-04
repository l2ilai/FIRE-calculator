public class Calculate {
    private static int year;
    private static final int START_CAPITAL= 1_000;              //стартовый капитал
    protected void setYear (int year) {
        Calculate.year = year;
    }

    //Вычесление массива долей дохода за все года
    private double[] getArrayRevenueShare (double [] moex_rate) {
        double[] revenue_share = new double[moex_rate.length - 1];
        for (int i = 0; i < moex_rate.length - 1; i++) {
            revenue_share[i] = (moex_rate[i + 1] - moex_rate[i]) / moex_rate[i];
        }
        return revenue_share;
    }

    //Вычесление кол-ва изымаемых средств в первом году
    private double calcWithdrawalCapitalStartingYear(double currentWithdrawalPercentage, double capitalCurrentYear) {
        return (currentWithdrawalPercentage / 100) * capitalCurrentYear;
    }

    //Вычесление дохода с биржы за год
    private double calcIncomeCapital(double remainingCapital, int currentYear) {
        double[] moex_rate = Constants.MOEX_RATE;                   //индекс биржи
        double[] revenue_share = getArrayRevenueShare(moex_rate);   //доли дохода с биржи

        return remainingCapital * (1.0 + revenue_share[(currentYear % 2000) - 2]);
    }

    //Вычесление изымания в следующем году с учётом инфляции
    private double calcWithdrawalCapitalCurrentYear(double withdrawalCapitalCurrentYear, int currentYear) {
        double[] inflation_rate = Constants.INFLATION_RATE;         //проценты инфляции
        return withdrawalCapitalCurrentYear * (inflation_rate[(currentYear % 2000)
                - 2] / 100) + withdrawalCapitalCurrentYear;
    }

    //Вычесление максмального процента изъятия
    protected double calcMaxWithdrawalPercentage () {
        double capitalCurrentYear;                         //капитал текуцего года
        double maxWithdrawalPercentage = 100;              //максимальный процент изъятия
        double remainingCapital;                           //отстаток текучего года
        double withdrawalCapitalNextYear;              //изымания в следующем году с учётом инфляции

        if (year == 2021) {
            return maxWithdrawalPercentage;
        }
        for (double currentWithdrawalPercentage = 0.5; maxWithdrawalPercentage > currentWithdrawalPercentage; currentWithdrawalPercentage += 0.5) {
            capitalCurrentYear = START_CAPITAL;

            //изымание в начале первого года
            withdrawalCapitalNextYear = calcWithdrawalCapitalStartingYear(currentWithdrawalPercentage,capitalCurrentYear);
            for (int currentYear = year; currentYear <= 2021; currentYear++) {

                //остаток текучего года
                remainingCapital = capitalCurrentYear - withdrawalCapitalNextYear;

                //доход за текущий год
                capitalCurrentYear = calcIncomeCapital(remainingCapital, currentYear);

                //изымания в следующем году с учётом инфляции
                withdrawalCapitalNextYear = calcWithdrawalCapitalCurrentYear(withdrawalCapitalNextYear, currentYear);
            }

            if (capitalCurrentYear < withdrawalCapitalNextYear && capitalCurrentYear < 0) {
                maxWithdrawalPercentage = currentWithdrawalPercentage - 0.5;
            }
        }
    return maxWithdrawalPercentage;
    }
}
