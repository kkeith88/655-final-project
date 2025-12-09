/**
 * Name: Keith Kwan
 * Course: CS-665 Software Designs & Patterns
 * Date: 12/09/2025
 * File Name: PaidState.java
 * Description: State representing a paid expense
 */
package edu.bu.met.cs665.states;

import java.time.LocalDate;
import edu.bu.met.cs665.Expense;

public class PaidState implements ExpenseState {
    
    /**
     * Overrides the handlePayment method; remains in PaidState.
     */
    @Override
    public void handlePayment(Expense expense) {
        // Cannot pay a bill that is already paid
        System.out.println("Action Denied: Expense '" + expense.getName() + "' is already Paid.");
    }

    /**
     * Overrides the handleDateCheck method; remains in PaidState.
     */
    @Override
    public void handleDateCheck(Expense expense, LocalDate today) {
        // A paid bill does not change state based on the date
        // Note: future work for recurring expenses could be added here
    }
}