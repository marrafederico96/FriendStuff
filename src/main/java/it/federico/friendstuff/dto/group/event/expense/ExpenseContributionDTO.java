package it.federico.friendstuff.dto.group.event.expense;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;

public record ExpenseContributionDTO(@NotNull BigDecimal amountOwed) {

}
