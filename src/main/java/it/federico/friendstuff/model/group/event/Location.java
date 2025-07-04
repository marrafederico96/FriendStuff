package it.federico.friendstuff.model.group.event;

import java.util.List;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "locations")
public class Location {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "location_id", nullable = false, unique = true)
	private Long locationId;

	@Column(name = "location_name", nullable = false, unique = true)
	@NotBlank
	private String locationName;

	@Column(name = "city")
	@Nullable
	private String city;

	@Column(name = "street")
	@Nullable
	private String street;

	@Column(name = "street_number")
	@Nullable
	private String streetNumber;

	@OneToMany(mappedBy = "location")
	private List<Event> eventList;

	public Location() {
	}

	public List<Event> getEventList() {
		return eventList;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}

	public Long getLocationId() {
		return locationId;
	}

}
