package it.federico.friendstuff.dto.group.event.expense;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import it.federico.friendstuff.model.group.event.expense.ExpenseContribution;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ExpenseDTO(@NotBlank(message = "Expense name cannot be empty") String expenseName,
		@NotNull(message = "Amount cannot be empty") BigDecimal amount,
		@NotBlank(message = "Expense descritpion cannote be empty") String expenseDescription,
		@NotNull boolean expenseClose, @NotNull(message = "Expense date cannot be empty") LocalDate expenseDate,
		@Nullable List<ExpenseContribution> participant, String eventName) {

	public ExpenseDTO(String expenseName, BigDecimal amount, String expenseDescription, LocalDate expenseDate,
			boolean expenseClose, List<ExpenseContribution> participant, String eventName) {
		this(expenseName, amount, expenseDescription, expenseClose, expenseDate, List.of(), eventName);
	}

}
