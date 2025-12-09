/**
 * Name: Keith Kwan
 * Course: CS-665 Software Designs & Patterns
 * Date: 12/09/2025
 * File Name: DueSoonState.java
 * Description: State representing an expense that is due soon
 */
package edu.bu.met.cs665.states;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import edu.bu.met.cs665.Expense;

public class DueSoonState implements ExpenseState {
    
    /**
     * Overrides the handlePayment method to transition to PaidState upon payment.
     */
    @Override
    public void handlePayment(Expense expense) {
        // User paid the bill while it was Due Soon
        System.out.println("Transition: Payment received successfully.");
        expense.setState(new PaidState());
    }

    /**
     * Overrides the handleDateCheck method to transition to OverdueState if the due date has passed.
     */
    @Override
    public void handleDateCheck(Expense expense, LocalDate today) {
        // Check if the due date has now passed
        long daysUntilDue = ChronoUnit.DAYS.between(today, expense.getInitialDueDate());

        if (daysUntilDue < 0) {
            // Due date passed without payment
            System.out.println("Date Check: DUE DATE MISSED. Escalating to Overdue.");
            expense.setState(new OverdueState());
        }
    }
}