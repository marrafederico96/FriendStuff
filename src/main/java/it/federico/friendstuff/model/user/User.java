package it.federico.friendstuff.model.user;

import java.util.ArrayList;
import java.util.List;

import it.federico.friendstuff.model.group.GroupMember;
import it.federico.friendstuff.model.group.event.expense.Expense;
import it.federico.friendstuff.model.group.event.expense.ExpenseContribution;
import it.federico.friendstuff.model.token.RefreshToken;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id", unique = true, nullable = false)
	private Long userId;

	@Column(name = "username", nullable = false, unique = true)
	@NotBlank
	private String username;

	@Column(name = "email", unique = true, nullable = false)
	@NotBlank
	@Email
	private String email;

	@Column(name = "password", nullable = false)
	@NotBlank
	private String password;

	@Column(name = "first_name", nullable = false)
	@NotBlank
	private String firstName;

	@Column(name = "last_name", nullable = false)
	@NotBlank
	private String lastName;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private final List<RefreshToken> refreshTokens = new ArrayList<>();

	@OneToMany(mappedBy = "userSender", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Friendship> sentFriendRequests = new ArrayList<>();

	@OneToMany(mappedBy = "userReceiver", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Friendship> receiveFriendRequests = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<GroupMember> groupMembership;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Expense> expenseList;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ExpenseContribution> expenseContributions;

	public User() {
	}

	public List<GroupMember> getGroupMembership() {
		return groupMembership;
	}

	public Long getUserId() {
		return userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<RefreshToken> getRefreshTokens() {
		return refreshTokens;
	}
}
