/**
 * Name: Keith Kwan
 * Course: CS-665 Software Designs & Patterns
 * Date: 12/09/2025
 * File Name: OverdueState.java
 * Description: State representing an overdue expense
 */
package edu.bu.met.cs665.states;

import java.time.LocalDate;
import edu.bu.met.cs665.Expense;

public class OverdueState implements ExpenseState {
    
    /**
     * Overrides the handlePayment method to transition to PaidState upon payment.
     */
    @Override
    public void handlePayment(Expense expense) {
        // User paid the bill while it was Overdue
        System.out.println("Transition: Overdue payment received.");
        expense.setState(new PaidState());
    }

    /**
     * Overrides the handleDateCheck method; remains in OverdueState.
     */
    @Override
    public void handleDateCheck(Expense expense, LocalDate today) {
        // Once Overdue, the state remains Overdue until payment is handled.
        System.out.println("Date Check: Still Overdue. No automatic transition possible.");
    }
}