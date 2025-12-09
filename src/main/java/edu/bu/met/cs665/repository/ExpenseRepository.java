/**
 * Name: Keith Kwan
 * Course: CS-665 Software Designs & Patterns
 * Date: 12/09/2025
 * File Name: ExpenseRepository.java
 * Description: Repository to manage Expense objects
 */
package edu.bu.met.cs665.repository;

import java.util.ArrayList;
import java.util.List;
import edu.bu.met.cs665.*;

public class ExpenseRepository {
    // This will act as our in-memory database
    private static final List<Expense> expenses = new ArrayList<>();

    public ExpenseRepository() {
    }

    /**
     * Adds a new Expense object to the in-memory list.
     * @param expense The Expense object to be added.
     */
    public static void addExpense(Expense expense) {
        expenses.add(expense);
    }
    
    /**
     * Removes a specific Expense object from the in-memory list.
     * @param expense The Expense object to be removed.
     * @return true if the expense was found and removed, false otherwise.
     */
    public static boolean removeExpense(Expense expense) {
        // Since the Expense class is unique, List.remove(Object) works without needing to search
        return expenses.remove(expense); 
    }

    /**
     * Retrieves all Expense objects from the in-memory list.
     * @return List of all Expense objects.
     */
    public static List<Expense> getAllExpenses() {
        // Return a copy to prevent external modification of the internal list
        return new ArrayList<>(expenses);
    }
}