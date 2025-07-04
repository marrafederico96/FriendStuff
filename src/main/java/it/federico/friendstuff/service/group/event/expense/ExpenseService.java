package it.federico.friendstuff.service.group.event.expense;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.federico.friendstuff.dto.group.event.expense.ExpenseDTO;
import it.federico.friendstuff.exception.EventException;
import it.federico.friendstuff.exception.ExpenseException;
import it.federico.friendstuff.model.group.event.Event;
import it.federico.friendstuff.model.group.event.expense.Expense;
import it.federico.friendstuff.model.group.event.expense.ExpenseContribution;
import it.federico.friendstuff.model.user.User;
import it.federico.friendstuff.repository.EventRepository;
import it.federico.friendstuff.repository.ExpenseContributionRepository;
import it.federico.friendstuff.repository.ExpenseRepository;
import it.federico.friendstuff.repository.UserRepository;

@Service
public class ExpenseService {

	private final ExpenseRepository expenseRepository;
	private final ExpenseContributionRepository expenseContributionRepository;
	private final UserRepository userRepository;
	private final EventRepository eventRepository;

	public ExpenseService(ExpenseRepository expenseRepository, UserRepository userRepository,
			EventRepository eventRepository, ExpenseContributionRepository expenseContributionRepository) {
		this.expenseRepository = expenseRepository;
		this.expenseContributionRepository = expenseContributionRepository;
		this.userRepository = userRepository;
		this.eventRepository = eventRepository;
	}

	@Transactional
	public void addExpense(Authentication authentication, ExpenseDTO expenseDTO) {
		User user = userRepository.findByUsername(authentication.getName())
				.orElseThrow(() -> new UsernameNotFoundException("user " + authentication.getName() + "not found"));

		Event event = eventRepository.findByEventName(expenseDTO.eventName())
				.orElseThrow(() -> new EventException("Event" + expenseDTO.eventName() + " not found"));

		Expense expense = new Expense();
		expense.setExpenseName(expenseDTO.expenseName());
		expense.setExpenseDescription(expenseDTO.expenseDescription());
		expense.setAmount(expenseDTO.amount());
		expense.setExpenseDate(expenseDTO.expenseDate());
		expense.setExpenseClose(expenseDTO.expenseClose());
		expense.setUser(user);
		expense.setEvent(event);

		expenseRepository.save(expense);

		List<ExpenseContribution> expenseContribtions = expenseDTO.participant();
		if (expenseContribtions == null || expenseContribtions.isEmpty()) {
			throw new ExpenseException("No participants provided");
		}

		BigDecimal splitAmount = expenseDTO.amount().divide(BigDecimal.valueOf(expenseContribtions.size()), 2,
				RoundingMode.HALF_UP);

		expenseDTO.participant().stream().forEach(expenseUser -> {
			User participant = userRepository.findByUsername(
					expenseUser.getUser().getUsername())
					.orElseThrow(() -> new UsernameNotFoundException(
							"Participant not found: " + expenseUser.getUser().getUsername()));
			ExpenseContribution contribution = new ExpenseContribution();
			contribution.setUser(participant);
			contribution.setExpense(expense);
			contribution.setAmountOwed(splitAmount);
			expenseContributionRepository.save(contribution);
		});
	}
}
