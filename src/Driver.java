/**
 * Created by Andrew on 4/19/15.
 * Have Questions? Contact me at andrew+support@sonnybrooksstudios.com
 */

import java.text.DecimalFormat;
import java.util.*;

public class Driver {
    public static void main(String[] args){
        SonnyUtilities.println("Welcome to Budzget!\n");
        Scanner stdIn = new Scanner(System.in);

        try
        {
            Double dSalary = -1.0;
            boolean isHourlyWage;
            Map<String, Double> monthlyExpense = new HashMap<String, Double>();
            //Initial setup

            //Get Yearly Salary
            while(dSalary == -1.0) {
                isHourlyWage = false;
                SonnyUtilities.println("\nPlease enter your yearly salary (XXXXX.XX) or type 'h' for hourly: $");
                String salaryResponse = stdIn.nextLine().replace(",", "").replace("$", "");
                if(salaryResponse.contains("h"))
                {
                    SonnyUtilities.println("Please enter your hourly salary: $");
                    salaryResponse = stdIn.nextLine().replace(",", "").replace("$", "");
                    isHourlyWage = true;
                }
                if(!salaryResponse.matches("^[0-9]+$"))
                {
                    SonnyUtilities.println("Invalid salary response. Please use format - 55,000");
                    continue;
                }
                dSalary = Double.parseDouble(salaryResponse);
                if(isHourlyWage)
                {
                    /*
                        On an hourly wage, you can estimate the yearly salary
                        by taking the hourly wage, multiplying it by 2, then
                        multiplying it by 1000. For example: Working full time
                        at $15/hr will come out to $15*2 = $30 * 1000 = $30,000/year.
                        This calculation only works for full time workers.
                     */
                    dSalary = dSalary * 2 * 1000;
                    DecimalFormat df = new DecimalFormat("#.##");
                    SonnyUtilities.println("Your yearly salary is approx. $" + df.format(dSalary));
                }
            }

            //Get Number of people in household.
            int iNumInHousehold = -1;
            try {
                while (iNumInHousehold <= 0) {
                    SonnyUtilities.println("\nPlease enter the number of people in your household: ");
                    String numPplResponse = stdIn.nextLine();
                    if(!numPplResponse.matches("^[0-9]+$")){
                        SonnyUtilities.println("Invalid num in household response. Please use format - 2");
                        continue;
                    }
                    iNumInHousehold = Integer.parseInt(numPplResponse);
                }
            }catch(Exception e) {
                SonnyUtilities.println("Exception occurred: Defaulting to iNumInHousehold = 1.");
                iNumInHousehold = 1;
            }

            //Get Average Cost of Utilities (Heat, Electric, Water)
            double dUtilityExpense = -1;
            while(dUtilityExpense == -1.0) {
                SonnyUtilities.println("\nPlease enter your average cost of utilities(Heat, Electric, Water)(XXXXX.XX): $");
                String utilityResponse = stdIn.nextLine().replace(",", "").replace("$", "");
                if(!utilityResponse.matches("^[0-9]+$"))
                {
                    SonnyUtilities.println("Invalid salary response. Please use format - 55,000");
                    continue;
                }
                dUtilityExpense = Double.parseDouble(utilityResponse);
            }

            //Get Monthly Expenses
            while(true)
            {
                SonnyUtilities.println("\nEnter a monthly expense(Name, Cost)(Ex: Netflix, 10.00)(-1 to move on): ");
                String response = stdIn.nextLine();
                if(response.equals("-1"))
                    break;
                else if(!response.contains(",") || response.length() - response.replace(",", "").length() > 1) {
                    SonnyUtilities.println("Invalid response. Please use the format - Netflix, 10");
                    continue;
                }
                else if(response.equals(""))
                    continue;
                if(response.split(",")[1].trim().matches("^[0-9]+$"))
                    monthlyExpense.put(response.split(",")[0].trim(), Double.parseDouble(response.split(",")[1].trim()));
                else {
                    SonnyUtilities.println("Invalid monthly expense. Please use specified format.");
                    continue;
                }
                SonnyUtilities.println(response + " was added!");
            }

            SonnyUtilities.println("\nCreating initial budget report...");

            Budget Budzget = new Budget(dSalary, iNumInHousehold);
            Budzget.setSdMonthlyExpense(monthlyExpense);

            SonnyUtilities.println(Budzget);

        }catch(Exception e) {
            SonnyUtilities.println("The program has encountered a problem! - Terminating\n" + e);
            return;
        }

        SonnyUtilities.println("\nGoodbye!");
    }

    public static class Budget {
        private int iNumberOfPeopleInHousehold;
        private Double dIncome;
        private Double dMonthlyIncome;
        private Double dHomeExpense;
        private Double dUtilitesExpense;
        private Double dTransportationExpense;
        private Double dFoodExpense;
        Map<String, Double> sdMonthyExpense;

        public Budget() {
        }

        public Budget(double dSalary, int iNumberOfPeopleInHousehold)
        {
            this.dIncome = dSalary;
            this.iNumberOfPeopleInHousehold = iNumberOfPeopleInHousehold;
            setdDefaultMonthlyIncome();
            setdDefaultFoodExpense();
            setdHomeExpense(getdMonthlyIncome() * .30);
            setdTransportationExpense(dMonthlyIncome * .15);
        }

        public Budget(Double dIncome, Double dHomeExpense, Double dTransportationExpense, Double dFoodExpense, Map<String, Double> sdMonthyExpense) {
            this.dIncome = dIncome;
            this.dHomeExpense = dHomeExpense;
            this.dTransportationExpense = dTransportationExpense;
            this.dFoodExpense = dFoodExpense;
            this.sdMonthyExpense = sdMonthyExpense;
        }

        public int getdNumberOfPeopleInHousehold() {
            return iNumberOfPeopleInHousehold;
        }

        public void setdNumberOfPeopleInHousehold(int iNumberOfPeopleInHousehold) {
            this.iNumberOfPeopleInHousehold = iNumberOfPeopleInHousehold;
        }

        public Double getdIncome() {
            return dIncome;
        }

        public void setdIncome(Double dIncome) {
            this.dIncome = dIncome;
        }

        public Double getdHomeExpense() {
            return dHomeExpense;
        }

        public void setdHomeExpense(Double dHomeExpense) {
            this.dHomeExpense = dHomeExpense;
        }

        public void setDefaultHomeExpense() {
            this.dHomeExpense = (dMonthlyIncome * .15);
        }

        public Double getdUtilitesExpense() {
            return dUtilitesExpense;
        }

        public void setdUtilitesExpense(Double dUtilitesExpense) {
            this.dUtilitesExpense = dUtilitesExpense;
        }

        public Double getdTransportationExpense() {
            return dTransportationExpense;
        }

        public void setdTransportationExpense(Double dTransportationExpense) {
            this.dTransportationExpense = dTransportationExpense;
        }

        public Double getdMonthlyIncome() {
            return dMonthlyIncome;
        }

        public void setdMonthlyIncome(Double dMonthlyIncome) {
            this.dMonthlyIncome = dMonthlyIncome;
        }

        public void setdDefaultMonthlyIncome() {
            this.dMonthlyIncome = (this.dIncome * .80) / 12;
        }

        public Double getdFoodExpense() {
            return dFoodExpense;
        }

        public void setdDefaultFoodExpense(){
            setdFoodExpense(300.00 * this.iNumberOfPeopleInHousehold);
        }

        public void setdFoodExpense(Double dFoodExpense) {
            this.dFoodExpense = dFoodExpense;
        }

        public Map<String, Double> getSdMonthyExpense() {
            return sdMonthyExpense;
        }

        public void setSdMonthlyExpense(Map<String, Double> sdMonthlyExpense) {
            this.sdMonthyExpense = sdMonthlyExpense;
        }

        public double getTotalMonthlyExpense()
        {
            double dTotal = 0;
            for(double d: sdMonthyExpense.values())
            {
                dTotal += d;
            }
            return dTotal;
        }

        public double getBalance() {
            return dMonthlyIncome - dHomeExpense - dTransportationExpense - dFoodExpense - getTotalMonthlyExpense();
        }

        @Override
        public String toString() {
            DecimalFormat df = new DecimalFormat("#.##");
            String sInDebt;
            if(getBalance() < 0)
                sInDebt = "You're in debt! Try to cut down on monthly expenses.";
            else
                sInDebt = "You're in the green! Try investing! - https://www.investopedia.com/";
            return "\nYour Budget:" +
                    "\nMonthly Income: $" + df.format(dMonthlyIncome) +
                    "\nHousing Allocation: $" + df.format(dHomeExpense) +
                    "\nTransportation Allocation: $" + df.format(dTransportationExpense) +
                    "\nFood Allocation: $" + df.format(dFoodExpense) +
                    "\nMonthly Expenses: $" + df.format(getTotalMonthlyExpense()) +
                    "\nBalance: $" + df.format(getBalance()) +
                    "\n" + sInDebt + "\n";
        }
    }
}
