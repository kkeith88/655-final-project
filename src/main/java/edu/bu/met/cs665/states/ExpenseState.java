/**
 * Name: Keith Kwan
 * Course: CS-665 Software Designs & Patterns
 * Date: 12/09/2025
 * File Name: ExpenseState.java
 * Description: Interface for different states of an expense
 */
package edu.bu.met.cs665.states;

import edu.bu.met.cs665.Expense;

public interface ExpenseState {

    /**
     * Handles payment action for the expense.
     * @param expense
     */
    void handlePayment(Expense expense);
    
    /**
     * Handles due date check for the expense.
     * @param expense
     * @param today
     */
    void handleDateCheck(Expense expense, java.time.LocalDate today);
}