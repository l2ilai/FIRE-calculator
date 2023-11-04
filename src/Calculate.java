public class Calculate {
    private static int year;
    private static final int START_CAPITAL= 1_000;              //стартовый капитал
    private double maxWithdrawalPercentage = 100;                //максимальный процент изъятия
    double[] inflation_rate = Constants.INFLATION_RATE;         //процент инфляции
    double[] moex_rate = Constants.MOEX_RATE;                   //индекс биржи
    protected double[] revenue_share = new double[moex_rate.length - 1];   //доля дохода
    protected void setYear (int year) {
        Calculate.year = year;
    }

    //Вычесление долей дохода за все года
    private void getRevenueShare() {
        for (int i = 0; i < moex_rate.length - 1; i++) {
            revenue_share[i] = (moex_rate[i + 1] - moex_rate[i]) / moex_rate[i];
        }
    }

    public double getMaxWithdrawalPercentage() {
        return maxWithdrawalPercentage;
    }

    //Вычесление максмального процента изъятия
    protected void calcWithdrawalPercentage () {
        double capitalCurrentYear;                                   //вход
        double remainingCapital;                                     //отстаток
        double incomeCapital;                                        //доход
        double withdrawalCapitalEndYear;                             //изымания в следующем году
        double withdrawalCapitalBeginningYear = 0;                   //изымание в начале года

        getRevenueShare();

        if (year != 2021) {
            for (double i = 0.5; maxWithdrawalPercentage > i; i += 0.5) {
                capitalCurrentYear = START_CAPITAL;
                //System.out.println(i);
                for (int currentYear = year; currentYear <= 2021; currentYear++) {
                    if (currentYear == year){
                        withdrawalCapitalBeginningYear = (i / 100) * capitalCurrentYear; //изымание в начале года
                    }
                    withdrawalCapitalEndYear = withdrawalCapitalBeginningYear * (inflation_rate[(currentYear % 2000)
                            - 2] / 100) + withdrawalCapitalBeginningYear; //изымания в следующем году
                    remainingCapital = capitalCurrentYear - withdrawalCapitalBeginningYear; //остаток
                    incomeCapital = remainingCapital * (1.0 + revenue_share[(currentYear % 2000) - 2]); //доход
                    //System.out.println((currentYear % 2000) - 2);
                    //System.out.println(currentYear + " " + capitalCurrentYear+ " " +withdrawalCapitalBeginningYear+ " "
                           // +remainingCapital+ " " +incomeCapital+ " " +withdrawalCapitalEndYear);

                    capitalCurrentYear = incomeCapital;                         //вход
                    withdrawalCapitalBeginningYear = withdrawalCapitalEndYear; //изымание в начале года

                    if (capitalCurrentYear < withdrawalCapitalEndYear && capitalCurrentYear < 0) {
                        maxWithdrawalPercentage = i - 0.5;
                        break;
                    }
                }
            }
        }
    }
}
