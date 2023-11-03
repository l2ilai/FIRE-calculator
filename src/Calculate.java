public class Calculate {
    private static int year;
    private static final int START_CAPITAL= 1_000;
    double[] inflation_rate = Constants.INFLATION_RATE;         //процент инфляции
    double[] moex_rate = Constants.MOEX_RATE;                   //индекс биржи
    double[] revenue_share = new double[moex_rate.length - 1];   //доля дохода
    public  int getYear() {
        return year;
    }
    public void setYear (int year) throws YearException {
        if (!(year >= 2002 & year <= 2021)) {
            throw new YearException("Year should be from 2002 to 2021 inclusive");
        }
        Calculate.year = year;
    }
    //Вычесление долей дохода за все года
    public void getRevenueShare() {
        for (int i = 0; i < moex_rate.length - 1; i++) {
            revenue_share[i] = (moex_rate[i + 1] - moex_rate[i]) / moex_rate[i];
        }
    }
    public double getWithdrawalPercentage () {

        double maxWithdrawalPercentage = 54;                        //максимальный процент изъятия
        double capitalCurrentYear;                                   //вход
        double remainingCapital;                                     //отстаток
        double withdrawalCapitalBeginningYear = 0;                       //изымание в начале года
        double incomeCapital;                                        //доход
        double withdrawalCapitalEndYear;                             //изымания в следующем году

        getRevenueShare();

        //Вычесление максмального процента изъятия
        if (year != 2021) {
            for (double i = 0.5; maxWithdrawalPercentage > i; i += 0.5) {
                capitalCurrentYear = START_CAPITAL;
                //System.out.println(i);
                for (int currentYear = year; currentYear <= 2021; currentYear++) {
                    if (currentYear == year){
                        withdrawalCapitalBeginningYear = (i / 100) * capitalCurrentYear; //изымание в начале года
                    }
                    withdrawalCapitalEndYear = withdrawalCapitalBeginningYear * (inflation_rate[(currentYear % 2000) - 2] / 100)
                            + withdrawalCapitalBeginningYear; //изымания в следующем году
                    remainingCapital = capitalCurrentYear - withdrawalCapitalBeginningYear; //остаток
                    incomeCapital = remainingCapital * (1.0 + revenue_share[(currentYear % 2000) - 2]); //доход
                    //System.out.println((currentYear % 2000) - 2);

                    //System.out.println(currentYear + " " + capitalCurrentYear+ " " +withdrawalCapitalBeginningYear+ " "
                           // +remainingCapital+ " " +incomeCapital+ " " +withdrawalCapitalEndYear);

                    capitalCurrentYear = incomeCapital;                         //вход
                    withdrawalCapitalBeginningYear = withdrawalCapitalEndYear; //изымание в начале года

                    if (capitalCurrentYear < withdrawalCapitalEndYear && capitalCurrentYear < 0) {
                        maxWithdrawalPercentage = i;
                        break;
                    }
                }
            }
        } else {
            return maxWithdrawalPercentage = 100;
            }
        return maxWithdrawalPercentage - 0.5;
    }


}
