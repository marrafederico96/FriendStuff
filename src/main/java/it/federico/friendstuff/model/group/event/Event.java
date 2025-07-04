package it.federico.friendstuff.model.group.event;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import it.federico.friendstuff.model.group.Group;
import it.federico.friendstuff.model.group.event.expense.Expense;
import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "events")
@EventDateConstraint
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "event_id", nullable = false, unique = true)
	private Long eventId;

	@Column(name = "event_name", nullable = false)
	@NotBlank
	private String eventName;

	@Column(name = "event_description")
	@Nullable
	private String description;

	@Enumerated(EnumType.STRING)
	@Column(name = "event_category", nullable = false)
	@NotNull
	private EventCategory category;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name = "start_date", nullable = false)
	@NotNull
	private LocalDate startDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name = "end_date", nullable = false)
	@NotNull
	private LocalDate endDate;

	@ManyToOne
	@JoinColumn(name = "group_id", nullable = false)
	private Group group;

	@ManyToOne
	@JoinColumn(name = "location_id", nullable = false)
	private Location location;

	@OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Expense> expenseList;

	public Event() {
	}

	public List<Expense> getExpenseList() {
		return expenseList;
	}

	public Group getgroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Location getLocation() {
		return location;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public EventCategory getCategory() {
		return category;
	}

	public void setCategory(EventCategory category) {
		this.category = category;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate end_date) {
		this.endDate = end_date;
	}

	public Long getEventId() {
		return eventId;
	}

}
