package it.federico.friendstuff.model.group.event.expense;

import java.math.BigDecimal;

import it.federico.friendstuff.model.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "expense_contributions")
public class ExpenseContribution {

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Id
	@Column(name = "expense_contribution_id", nullable = false, unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long expenseContributionId;

	@Column(name = "amount_owed")
	@NotNull
	private BigDecimal amountOwed;

	@ManyToOne
	@JoinColumn(name = "participant_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "expense_id")
	private Expense expense;

	public ExpenseContribution() {
	}

	public BigDecimal getAmountOwed() {
		return amountOwed;
	}

	public void setAmountOwed(BigDecimal amountOwed) {
		this.amountOwed = amountOwed;
	}

	public Expense getExpense() {
		return expense;
	}

	public void setExpense(Expense expense) {
		this.expense = expense;
	}

	public long getExpenseContributionId() {
		return expenseContributionId;
	}
}
