/**
 * Name: Keith Kwan
 * Course: CS-665 Software Designs & Patterns
 * Date: 12/09/2025
 * File Name: ExpenseBuilder.java
 * Description: Interface for building Expense objects
 */
package edu.bu.met.cs665;

import java.time.LocalDate;

public interface ExpenseBuilder {
    ExpenseBuilder setName(String name);
    ExpenseBuilder setAmount(double amount);
    ExpenseBuilder setDueDate(LocalDate date);
    ExpenseBuilder setCategory(String category);
    ExpenseBuilder setRecurrenceType(String type);
    ExpenseBuilder setPaymentMethod(String method);
    Expense build();
}