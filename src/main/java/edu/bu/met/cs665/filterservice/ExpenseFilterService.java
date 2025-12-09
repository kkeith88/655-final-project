/**
 * Name: Keith Kwan
 * Course: CS-665 Software Designs & Patterns
 * Date: 12/09/2025
 * File Name: ExpenseFilterService.java
 * Description: Service to filter expenses based on criteria
 */
package edu.bu.met.cs665.filterservice;

import edu.bu.met.cs665.*;
import edu.bu.met.cs665.repository.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ExpenseFilterService {
    
    /**
     * Filters expenses to show transactions within the last N days 
     * based on their initialDueDate.
     */
    public List<Expense> filterLastNDays(int days) {

        LocalDate today = LocalDate.now(); 
        LocalDate cutoffDate = today.minusDays(days); 

        return ExpenseRepository.getAllExpenses().stream()
            .filter(expense -> {
                LocalDate dueDate = expense.getInitialDueDate();
                
                //Lower Bound: Due date must be AFTER the cutoff date (30 days ago)
                boolean isRecent = dueDate.isAfter(cutoffDate) || dueDate.isEqual(cutoffDate);
                
                //Upper Bound: Due date must be ON or BEFORE today
                boolean isPastOrPresent = dueDate.isBefore(today) || dueDate.isEqual(today);

                //Both conditions must be met to be included in the "last N days"
                return isRecent && isPastOrPresent;
            })
            .collect(Collectors.toList());
    }

    /**
     * Additional filtering methods can be added here as needed.
     */

}