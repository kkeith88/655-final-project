/**
 * Name: Keith Kwan
 * Course: CS-665 Software Designs & Patterns
 * Date: 12/09/2025
 * File Name: Expense.java
 * Description: Class representing an Expense with State pattern implementation
 */
package edu.bu.met.cs665;

import java.time.LocalDate;
import edu.bu.met.cs665.states.ExpenseState;
import edu.bu.met.cs665.states.UpcomingState;

public class Expense {
    private final String name;
    private final double amount;
    private final LocalDate initialDueDate;
    private final String category; 
    private final String paymentMethod; 
    private final String recurrenceType; 

    private ExpenseState currentState;

    public Expense(String name, double amount, LocalDate initialDueDate,
                   String category, String paymentMethod, String recurrenceType) {
        this.name = name;
        this.amount = amount;
        this.initialDueDate = initialDueDate;
        this.category = category;
        this.paymentMethod = paymentMethod;
        this.recurrenceType = recurrenceType;
        
        this.currentState = new UpcomingState();
    }

    
    /**
     * Sets the current state of the expense.
     * @param newState
     */
    public void setState(ExpenseState newState) {
        if (this.currentState != newState) {
            this.currentState = newState;
        }
    }

    /**
     * Delegates the payment handling to the current state object.
     */
    public void markAsPaid() {
        System.out.println("Attempting to mark expense '" + name + "' as Paid...");
        if (currentState != null) {
            currentState.handlePayment(this);
        }
    }

    /**
     * Delegates the due date check to the current state object.
     * @param today The current date for comparison.
     */
    public void checkDueDate(LocalDate today) {
        if (currentState != null) {
            currentState.handleDateCheck(this, today);
        }
    }
    //Getters for Expense attributes
    //------------------------------
    public String getName() {
        return name;
    }
    public double getAmount() { 
        return amount; 
    }
    public LocalDate getInitialDueDate() { 
        return initialDueDate; 
    }
    public String getRecurrenceType() { 
        return recurrenceType; 
    }
    public ExpenseState getCurrentState() { 
        return currentState; 
    }
    public String getCategory() {
        return category;
    }
    public String getPaymentMethod() {
        return paymentMethod;
    }
}