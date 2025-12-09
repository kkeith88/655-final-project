/**
 * Name: Keith Kwan
 * Course: CS-665 Software Designs & Patterns
 * Date: 12/09/2025
 * File Name: TestExpense.java
 * Description: Test cases for Expense class and its behaviors
 */
package edu.bu.met.cs665;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.Test;

import edu.bu.met.cs665.states.OverdueState;
import edu.bu.met.cs665.states.PaidState;
import edu.bu.met.cs665.states.UpcomingState;

public class TestExpense {
    
    public TestExpense() {
    }

    /**
     * Test that the Builder creates a valid Expense object with all fields set correctly.
     */
    @Test
    public void testBuilderCreatesValidExpenseObject() {
        // Arrange
        LocalDate dueDate = LocalDate.of(2025, 12, 15);
        
        // Act (Use the Builder to construct the object)
        Expense expense = new ConcreteExpenseBuilder()
            .setName("Internet Bill")
            .setAmount(65.50)
            .setDueDate(dueDate)
            .setCategory("Utilities")
            .setRecurrenceType("Monthly")
            .setPaymentMethod("ACH")
            .build();

        // Assert (Verify all fields were set correctly)
        assertEquals("Internet Bill", expense.getName());
        assertEquals(65.50, expense.getAmount(), 0.001); // Use delta for double comparison
        assertEquals(dueDate, expense.getInitialDueDate());
        assertEquals("Utilities", expense.getCategory());
        assertEquals("Monthly", expense.getRecurrenceType());
        
        // Verify the initial state is correct
        assertTrue(expense.getCurrentState() instanceof UpcomingState);
    }

    /**
     * Test that the Builder throws an exception when mandatory fields are missing.
     */
    @Test
    public void testBuilderFailsWithoutMandatoryAmount() {
        // Assert that calling build() throws an IllegalStateException
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            new ConcreteExpenseBuilder()
                .setName("Faulty Bill")
                // Missing setAmount()
                .setDueDate(LocalDate.now())
                .build();
        });
        
        // Verify the error message is clear
        assertTrue(exception.getMessage().contains("Mandatory fields"));
    }

    /**
     * Test state transition from Upcoming to Paid when marked as paid.
     */
    @Test
    public void testUpcomingToPaidTransition() {
        // Arrange: Build an upcoming expense
        Expense expense = new ConcreteExpenseBuilder()
            .setName("Book Purchase")
            .setAmount(30.00)
            .setDueDate(LocalDate.of(2026, 3, 1))
            .build();
        
        // Assert initial state
        assertTrue(expense.getCurrentState() instanceof UpcomingState);

        // Act: Simulate user marking the expense as paid
        expense.markAsPaid();

        // Assert final state
        assertTrue(expense.getCurrentState() instanceof PaidState);
    }

    /**
     * Test state transition from Overdue to Paid when marked as paid, and denial of re-payment.
     */
    @Test
    public void testOverdueToPaidTransitionAndDenial() {
        // Arrange: Build an expense that is already overdue (simulated via checkDueDate)
        Expense overdueExpense = new ConcreteExpenseBuilder()
            .setName("Late Fee")
            .setAmount(15.00)
            .setDueDate(LocalDate.of(2025, 10, 1)) // Due in the past
            .build();
            
        // Force the transition to OverdueState (simulating the system daily check)
        overdueExpense.checkDueDate(LocalDate.of(2025, 10, 2));
        assertTrue(overdueExpense.getCurrentState() instanceof OverdueState);

        // Act Mark the overdue expense as paid
        overdueExpense.markAsPaid();

        // Assert State should be PaidState
        assertTrue(overdueExpense.getCurrentState() instanceof PaidState);

        // Act Attempt to mark as paid again (should fail/be denied by PaidState logic)
        overdueExpense.markAsPaid();
        assertTrue(overdueExpense.getCurrentState() instanceof PaidState); // Remains PaidState
    }
}
