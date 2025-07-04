package it.federico.friendstuff.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.federico.friendstuff.model.group.event.expense.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

	public Optional<Expense> findByExpenseName(String expenseName);

}
