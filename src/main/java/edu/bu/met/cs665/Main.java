/**
 * Name: Keith Kwan
 * Course: CS-665 Software Designs & Patterns
 * Date: 12/09/2025
 * File Name: Main.java
 * Description: Entry point for the application
 */
package edu.bu.met.cs665;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import edu.bu.met.cs665.filterservice.ExpenseFilterService;

/**
 * This is the Main class.
 */
public class Main {
    private static final ExpenseBuilder builder = new ConcreteExpenseBuilder();
    private static final ExpenseFilterService filterService = new ExpenseFilterService();
  public static void main(String[] args) {
    
    System.out.println("--- Expense Tracker System Demo (Builder & State) ---");
        
        System.out.println("\n\n=== DEMO 1: Building a Monthly Rent Expense ===");
        demonstrateBuilderCreation();

        System.out.println("\n=== DEMO 2: User Action on Rent ===");
        demonstrateStateTransitions();
       
        System.out.println("\n=== DEMO 3: Time Passing Simulation ===");
        demonstrateTimeTransitions();

        System.out.println("\n\n=== DEMO 4: Filtering Expenses ===");
        demonstrateFiltering();

    }

    /**
     * Demonstrates building an Expense using the Builder pattern.
     */
    private static void demonstrateBuilderCreation(){
        Expense rent = builder
            .setName("Monthly Rent")
            .setAmount(1500.00)
            .setDueDate(LocalDate.of(2026, Month.JANUARY, 5)) // Set due date in the future
            .setCategory("Housing")
            .setRecurrenceType("Monthly")
            .build();
            
        System.out.println("Expense Built: " + rent.getName());
        System.out.println("Initial State: " + rent.getCurrentState().getClass().getSimpleName());
    }

    /**
     * Demonstrates state transitions based on user actions.
     * Paying the bill
     */
    private static void demonstrateStateTransitions(){
        Expense rent = builder
            .setName("Monthly Rent")
            .setAmount(1500.00)
            .setDueDate(LocalDate.of(2026, Month.JANUARY, 5)) // Set due date in the future
            .setCategory("Housing")
            .setRecurrenceType("Monthly")
            .build();
        // Assume today is Jan 3, 2026 (Rent is still Upcoming)
        LocalDate today_pre_due = LocalDate.of(2026, Month.JANUARY, 3);
        System.out.println("Today is: " + today_pre_due);
        
        // User pays the rent early (State should transition from Upcoming to Paid)
        rent.markAsPaid();
        System.out.println("New State: " + rent.getCurrentState().getClass().getSimpleName());
        
        // Attempt to mark as paid again (PaidState should reject this)
        rent.markAsPaid(); 
    }

    /**
     * Demonstrates filtering expenses due within the last N days.
     * Built 3 objects, expected 2 objectts to be returned by the filter.
     */
    private static void demonstrateFiltering(){
        // Create historical/past expenses for filtering purposes
        LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);

        // Expense 1: Paid today (within 30 days)
        new ConcreteExpenseBuilder().setName("Recent Lunch").setAmount(15.00)
            .setDueDate(LocalDate.now()).build().markAsPaid();

        // Expense 2: Due date within 30 days
        new ConcreteExpenseBuilder().setName("Gas Refill").setAmount(45.00)
            .setDueDate(LocalDate.now().minusDays(10)).build();

        // Expense 3: Old expense (outside 30 days)
        new ConcreteExpenseBuilder().setName("Old Trip").setAmount(300.00)
            .setDueDate(LocalDate.now().minusDays(45)).build();

        // Run the filter for the last 30 days
        List<Expense> recentExpenses = filterService.filterLastNDays(30);

        System.out.println("\nExpenses due in the last 30 days (Cutoff: " + thirtyDaysAgo + "): " + recentExpenses.size() + " found.");

        for (Expense exp : recentExpenses) {
            System.out.println("- " + exp.getName() + " (Due: " + exp.getInitialDueDate() + ")" + 
                               " | Amount due: " + exp.getAmount());
        }
    }

    /**
     * Demonstrates state transitions based on time passing.
     * Due Soon and Overdue transitions.
     */
    private static void demonstrateTimeTransitions(){

        // Build a new expense instance that is currently Upcoming
        Expense insurance = new ConcreteExpenseBuilder()
            .setName("Quarterly Insurance")
            .setAmount(450.00)
            .setDueDate(LocalDate.of(2026, Month.JANUARY, 10))
            .setCategory("Insurance")
            .setRecurrenceType("Quarterly")
            .setPaymentMethod("Credit Card")
            .build();
            
        System.out.println("The insurance bill is due January 10, 2026.");
        System.out.println("Insurance Initial State: " + insurance.getCurrentState().getClass().getSimpleName());

        // Simulate day check when it's DUE SOON (Jan 5th)
        LocalDate day_due_soon = LocalDate.of(2026, Month.JANUARY, 5);
        System.out.println("\nSimulating day check on: " + day_due_soon);
        insurance.checkDueDate(day_due_soon);
        System.out.println("Insurance Status: " + insurance.getCurrentState().getClass().getSimpleName()); // Expected: DueSoonState
        // Simulate day check when it becomes OVERDUE (Jan 11th)
        LocalDate day_overdue = LocalDate.of(2026, Month.JANUARY, 11);
        System.out.println("\nSimulating day check on: " + day_overdue);
        insurance.checkDueDate(day_overdue);
        System.out.println("Insurance Status: " + insurance.getCurrentState().getClass().getSimpleName()); // Expected: OverdueState
        
        // Simulate paying the overdue bill
        insurance.markAsPaid();
        System.out.println("Insurance Status: " + insurance.getCurrentState().getClass().getSimpleName()); // Expected: PaidState

    }
}
