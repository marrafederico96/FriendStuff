package it.federico.friendstuff.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.federico.friendstuff.model.group.event.expense.ExpenseContribution;

public interface ExpenseContributionRepository extends JpaRepository<ExpenseContribution, Long> {

}
