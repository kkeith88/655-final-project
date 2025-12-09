/**
 * Name: Keith Kwan
 * Course: CS-665 Software Designs & Patterns
 * Date: 12/09/2025
 * File Name: ConcreteExpenseBuilder.java
 * Description: Concrete builder for creating Expense objects
 */
package edu.bu.met.cs665;

import java.time.LocalDate;
import edu.bu.met.cs665.repository.*;
import edu.bu.met.cs665.states.*;

public class ConcreteExpenseBuilder implements ExpenseBuilder {
    private String name;
    private double amount;
    private LocalDate dueDate;
    private String category;
    private String paymentMethod;
    private String recurrenceType;

    /**
     * Overrides setName to set the expense name.
     * @param name String The name of the expense.
     */
    @Override
    public ExpenseBuilder setName(String name) {
        this.name = name;
        return this;
    }
    
    /**
     * Overrides setAmount to set the expense amount.
     * @param amount Double The amount of the expense.
     */
    @Override
    public ExpenseBuilder setAmount(double amount) {
        this.amount = amount;
        return this;
    }
    
    /**
     * Overrides setDueDate to set the expense due date.
     * @param date LocalDate The due date of the expense.
     */
    @Override
    public ExpenseBuilder setDueDate(LocalDate date) {
        this.dueDate = date;
        return this;
    }

    /**
     * Overrides setCategory to set the expense category.
     * @param category String The category of the expense.
     */
    @Override
    public ExpenseBuilder setCategory(String category) {
        this.category = category;
        return this;
    }

    /**
     * Overrides setRecurrenceType to set the expense recurrence type.
     * @param recurrenceType String The recurrence type of the expense.
     */
    @Override
    public ExpenseBuilder setRecurrenceType(String recurrenceType) {
        this.recurrenceType = recurrenceType;
        return this;
    }

    /**
     * Overrides setPaymentMethod to set the expense payment method.
     * @param paymentMethod String The payment method of the expense.
     */
    @Override
    public ExpenseBuilder setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }   

    /**
     * Overrides build to create and return the Expense object.
     * Also adds the new expense to the ExpenseRepository.
     * @return Expense The constructed Expense object.
     */
    @Override
    public Expense build() {
        // Validation and Defaults
        if (name == null || amount <= 0 || dueDate == null) {
            throw new IllegalStateException("Mandatory fields (Name, Amount, Due Date) must be set.");
        }
        
        // Apply default values if optional fields were skipped
        if (category == null) this.category = "Uncategorized";
        if (recurrenceType == null) this.recurrenceType = "None";

        Expense newExpense = new Expense(
            this.name, 
            this.amount, 
            this.dueDate,
            this.category,
            this.paymentMethod,
            this.recurrenceType
        );
        
        newExpense.setState(new UpcomingState()); 
        ExpenseRepository.addExpense(newExpense);
        return newExpense;
    }
}