package it.federico.friendstuff.model.group.event.expense;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import it.federico.friendstuff.model.group.event.Event;
import it.federico.friendstuff.model.user.User;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "expenses")
public class Expense {

	@Id
	@Column(name = "expense_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long expenseId;

	@Column(name = "expense_name", nullable = false)
	@NotBlank
	private String expenseName;

	@Column(name = "expense_description")
	@Nullable
	private String expenseDescription;

	@Column(name = "expense_date")
	@NotNull
	private LocalDate expenseDate;

	@Column(name = "amount", nullable = false)
	@NotNull
	private BigDecimal amount;

	@ManyToOne
	@JoinColumn(name = "payer_user_id")
	private User user;

	@Column(name = "expense_close", nullable = false)
	@NotNull
	private boolean expenseClose;

	@ManyToOne
	@JoinColumn(name = "event_id")
	private Event event;

	@OneToMany(mappedBy = "expense")
	private List<ExpenseContribution> expenseContributions;

	public Expense() {

	}

	public String getExpenseName() {
		return expenseName;
	}

	public boolean isExpenseClose() {
		return expenseClose;
	}

	public void setExpenseClose(boolean expenseClose) {
		this.expenseClose = expenseClose;
	}

	public void setExpenseName(String expenseName) {
		this.expenseName = expenseName;
	}

	public String getExpenseDescription() {
		return expenseDescription;
	}

	public void setExpenseDescription(String expenseDescription) {
		this.expenseDescription = expenseDescription;
	}

	public LocalDate getExpenseDate() {
		return expenseDate;
	}

	public void setExpenseDate(LocalDate expenseDate) {
		this.expenseDate = expenseDate;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public List<ExpenseContribution> getExpenseContributions() {
		return expenseContributions;
	}

	public void setExpenseContributions(List<ExpenseContribution> expenseContributions) {
		this.expenseContributions = expenseContributions;
	}

	public long getExpenseId() {
		return expenseId;
	}
}
