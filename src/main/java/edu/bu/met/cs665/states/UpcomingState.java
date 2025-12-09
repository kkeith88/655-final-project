/**
 * Name: Keith Kwan
 * Course: CS-665 Software Designs & Patterns
 * Date: 12/09/2025
 * File Name: UpcomingState.java
 * Description: State representing an upcoming expense
 */
package edu.bu.met.cs665.states;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import edu.bu.met.cs665.Expense;

public class UpcomingState implements ExpenseState {

    /**
     * Overrides the handlePayment method to transition to PaidState upon payment.
     */
    @Override
    public void handlePayment(Expense expense) {
        // Transition to PaidState
        expense.setState(new PaidState());
    }

    /**
     * Overrides the handleDateCheck method to transition to DueSoonState or OverdueState based on the due date.
     */
    @Override
    public void handleDateCheck(Expense expense, LocalDate today) {
        long daysUntilDue = ChronoUnit.DAYS.between(today, expense.getInitialDueDate());
        
        if (daysUntilDue <= 7 && daysUntilDue >= 0) {
            // Transition to DueSoonState
            expense.setState(new DueSoonState());
        } else if (daysUntilDue < 0) {
            // Due date passed without payment
            expense.setState(new OverdueState());
        }
        // Else: remain in UpcomingState
    }
}