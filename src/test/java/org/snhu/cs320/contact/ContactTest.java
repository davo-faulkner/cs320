package org.snhu.cs320.contact;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.snhu.cs320.exceptions.ValidationException;

class ContactTest {
	
	static Contact contact;
	
	@BeforeEach
	void init() throws ValidationException {
		contact = new Contact("1", "First", "Last", "5553334444", 
				"1234 Loblolly Lane");
	}

	@Test
	void testConstructorSuccessPath() {
		assertThat(contact)
			.isNotNull()
			.hasFieldOrPropertyWithValue("id", "1")
			.hasFieldOrPropertyWithValue("firstName", "First")
			.hasFieldOrPropertyWithValue("lastName", "Last")
			.hasFieldOrPropertyWithValue("phone", "5553334444")
			.hasFieldOrPropertyWithValue("address", "1234 Loblolly Lane");
	}
	
	@ParameterizedTest
	@CsvSource({
		"'',First,Last,5553334444,1234 Loblolly Lane", // Blank ID
		",First,Last,5553334444,1234 Loblolly Lane", // Null ID
		"12345678901,First,Last,5553334444,1234 Loblolly Lane", // Too Long ID
		"12345,'',Last,5553334444,1234 Loblolly Lane", // Blank First Name
		"12345,,Last,5553334444,1234 Loblolly Lane", // Null First Name
		"12345,FirstFirstF,Last,5553334444,1234 Loblolly Lane", // Too Long First 
			// Name
		"12345,First,'',5553334444,1234 Loblolly Lane", // Blank Last Name
		"12345,First,,5553334444,1234 Loblolly Lane", // Null Last Name
		"12345,First,LastLastLas,5553334444,1234 Loblolly Lane", // Too Long Last 
			// Name
		"12345,First,Last,'',1234 Loblolly Lane", // Blank Phone
		"12345,First,Last,,1234 Loblolly Lane", // Null Phone
		"12345,First,Last,55533344449,1234 Loblolly Lane", // Too Long Phone
		"12345,First,Last,555333444A,1234 Loblolly Lane", // Phone with Letters
		"12345,First,Last,555333-444,1234 Loblolly Lane", // Phone with Punctuation
		"12345,First,Last,555333 444,1234 Loblolly Lane", // Phone with Spaces
		"12345,First,Last,5553334444,''", // Blank Address
		"12345,First,Last,5553334444,", // Null Address
		"12345,First,Last,5553334444,1234 Loblolly Lane 1234 Lobloll", // Too Long 
			// Address
	})
	void invalidConstructorDataThrowsException(String id, String first, String last, 
			String phone, String address) {
		assertThatThrownBy(() -> new Contact(id, first, last, phone, 
				address))
			.isInstanceOf(ValidationException.class);
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"NewFirst"})
	void testFirstNameSetterSuccessPath(String newFirstName) 
			throws ValidationException {
		contact.setFirstName(newFirstName);
		assertThat(contact.getFirstName())
			.isNotNull()
			.isEqualTo(newFirstName);
	}
	
	@ParameterizedTest
	@CsvSource(value = {"''", // Blank First Name
		"null", // Null First Name
		"FirstFirstF"}, // Too Long First Name
		nullValues = {"null"})
	void testFirstNameSetterFailPaths(String newFirstName) 
			throws ValidationException {
		assertThatThrownBy(() -> contact.setFirstName(newFirstName))
			.isInstanceOf(ValidationException.class);
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"NewLast"})
	void testLastNameSetterSuccessPath(String newLastName) 
			throws ValidationException {
		contact.setLastName(newLastName);
		assertThat(contact.getLastName())
			.isNotNull()
			.isEqualTo(newLastName);
	}
	
	@ParameterizedTest
	@CsvSource(value = {"''", // Blank Last Name
		"null", // Null Last Name
		"LastLastLas"}, // Too Long Last Name
		nullValues = {"null"})
	void testLastNameSetterFailPaths(String newLastName) 
			throws ValidationException {
		assertThatThrownBy(() -> contact.setLastName(newLastName))
			.isInstanceOf(ValidationException.class);
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"3334445555"})
	void testPhoneSetterSuccessPath(String newPhone) 
			throws ValidationException {
		contact.setPhone(newPhone);
		assertThat(contact.getPhone())
			.isNotNull()
			.isEqualTo(newPhone);
	}
	
	@ParameterizedTest
	@CsvSource(value = {"''", // Blank Phone
		"null", // Null Phone
		"33344455556", // Too Long Phone
		"333444555A", // Phone with Letters
		"333444+555", // Phone with Punctuation
		"3334 45555"}, // Phone with Spaces
		nullValues = {"null"})
	void testPhoneSetterFailPaths(String newPhone) 
			throws ValidationException {
		assertThatThrownBy(() -> contact.setPhone(newPhone))
			.isInstanceOf(ValidationException.class);
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"NewAddress"})
	void testAddressSetterSuccessPath(String newAddress) 
			throws ValidationException {
		contact.setAddress(newAddress);
		assertThat(contact.getAddress())
			.isNotNull()
			.isEqualTo(newAddress);
	}
	
	@ParameterizedTest
	@CsvSource(value = {"''", // Blank Address
		"null", // Null Address
		"NewAddressNewAddressNewAddressN"}, // Too Long Address
		nullValues = {"null"})
	void testAddressSetterFailPaths(String newAddress) 
			throws ValidationException {
		assertThatThrownBy(() -> contact.setAddress(newAddress))
			.isInstanceOf(ValidationException.class);
	}
	
}
