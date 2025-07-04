package it.federico.friendstuff.dto.group.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;

public class LocationDTO {

	@NotBlank(message = "Location name cannot be empty")
	private final String locationName;
	@Nullable
	private final String city;
	@Nullable
	private final String street;
	@Nullable
	private final String streetNumber;

	@JsonCreator
	public LocationDTO(@JsonProperty("locationName") String locationName, @JsonProperty("city") String city,
			@JsonProperty("street") String street, @JsonProperty("streetNumber") String streetNumber) {
		this.locationName = locationName;
		this.city = city != null ? city : "";
		this.street = street != null ? street : "";
		this.streetNumber = streetNumber != null ? streetNumber : "";
	}

	public String getLocationName() {
		return locationName;
	}

	public String getCity() {
		return city;
	}

	public String getStreet() {
		return street;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

}
