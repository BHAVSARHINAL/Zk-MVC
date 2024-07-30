package com.controller;
import java.text.SimpleDateFormat;
import java.util.List;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;

import com.model.User;
import com.service.UserService;
import com.service.UserServiceImpl;
/**
 * <h3>This class represents that create, read, edit and delete methods.</h3>
 * @author : Hinal Bhavsar
 * @version 1.01 20-07-2024
 */
public class UserController extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;

	@WireVariable
	private UserService userService = new UserServiceImpl();

	@Wire
	private Textbox name, address, email, contact;

	@Wire
	private Datebox dateOfBirth;

	@Wire
	private Radiogroup gender;

	@Wire
	private Listbox status, userList, rowsPerPage;

	@Wire
	private Label nameError, addressError, emailError, contactError, dateOfBirthError, statusError, genderError;

	@Wire
	private Button save, reset;

	@Wire
	private Paging paging;

	private int pageSize = 5;

	private int currentPage = 0;

	private User selectedUser;

	private boolean formValid, fieldChanged = false;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		loadUserList();
		updateButtonVisibility();
	}

	@Listen("onChange=#name")
	public void validName() {
		boolean nameValid = nameValid();
		formValid = nameValid;
		fieldChanged = true;
		userValidForm();
	}

	@Listen("onChange=#email")
	public void validEmail() {
		boolean emailValid = emailvalid();
		formValid = emailValid;
		fieldChanged = true;
		userValidForm();
	}

	@Listen("onChange=#contact")
	public void validContactNumber() {
		boolean contactValid = contactValid();
		formValid = contactValid;
		fieldChanged = true;
		userValidForm();
	}

	@Listen("onChange=#address")
	public void validAddress() {
		boolean addressValid = addressValid();
		formValid = addressValid;
		fieldChanged = true;
		userValidForm();
	}

	@Listen("onSelect=#status")
	public void validateStatus() {
		boolean statusValid = statusValid();
		formValid = statusValid;
		fieldChanged = true;
		userValidForm();
	}

	@Listen("onChange=#dateOfBirth")
	public void validDateOfBirth() {
		boolean dateOfBirthValid = dateOfBirthValid();
		formValid = dateOfBirthValid;
		userValidForm();
	}

	@Listen("onClick=#gender")
	public void validgenderr() {
		boolean genderValided = genderValid();
		formValid = genderValided;
		fieldChanged = true;
		userValidForm();
	}

	public void userValidForm() {
		formValid = nameValid() && emailvalid() && addressValid() && contactValid() && genderValid() && dateOfBirthValid();
		updateButtonVisibility();
	}

	public void updateButtonVisibility() {
		save.setVisible(formValid && fieldChanged);
	}

	@Listen("onPaging = #paging")
	public void onPaging() {
		currentPage = paging.getActivePage();
		loadUserList();
	}

	@Listen("onSelect = #rowsPerPage")
	public void updateRowsPerPage() {
		pageSize = Integer.parseInt(rowsPerPage.getSelectedItem().getValue());
		paging.setPageSize(pageSize);
		currentPage = 0;
		loadUserList();
	}

	// Name validation
	public boolean nameValid() {
		boolean isValid = true;
		String nameValue = name.getValue().trim();
		if (name.getValue().startsWith(" ")) {
			nameError.setValue("Name should not start with space");
			isValid = false;
		} else if (nameValue.isEmpty()) {
			nameError.setValue("Name can't be empty.");
			isValid = false;
		} else if (!nameValue.matches("^[A-Z a-z]+$")) {
			nameError.setValue("Invalid name.");
			isValid = false;
		} else {
			nameError.setValue("");
		}
		return isValid;
	}

	// Address validation
	public boolean addressValid() {
		boolean isValid = true;
		if (address.getValue().startsWith(" ")) {
			addressError.setValue("Address Should not start with space");
			isValid = false;
		} else if (address.getValue().trim().isEmpty()) {
			addressError.setValue("Address can't be empty.");
			isValid = false;
		} else {
			addressError.setValue("");
		}
		return isValid;
	}

	// Email validation
	public boolean emailvalid() {
		boolean isValid = true;
		String emailValue = email.getValue().trim();
		if (email.getValue().startsWith(" ")) {
			emailError.setValue("Email should not start with space");
			isValid = false;
		} else if (emailValue.isEmpty()) {
			emailError.setValue("Email can't be empty.");
			isValid = false;
		} else if (!emailValue.matches("^[a-z0-9]+@[a-z0-9]+\\.[a-z]{2,}$")) {
			emailError.setValue("Invalid email address.");
			isValid = false;
		} else if (userService.emailExists(emailValue) && (selectedUser == null || !selectedUser.getEmail().equals(emailValue))) {
			emailError.setValue("Already exists email address.");
			isValid = false;
		} else {
			emailError.setValue("");
		}
		return isValid;
	}

	// Contact validation
	public boolean contactValid() {
		boolean isValid = true;
		String contactValue = contact.getValue().trim();
		if (contactValue.isEmpty()) {
			contactError.setValue("Contact can't be empty.");
			isValid = false;
		} else if (!contactValue.matches("^([+]\\d{2})?\\d{10}$")) {
			contactError.setValue("Invalid contact number.");
			isValid = false;
		} else if (userService.contactExists(contactValue) && (selectedUser == null || !selectedUser.getContact().equals(contactValue))) {
			contactError.setValue("Already exists contact number");
			isValid = false;
		} else {
			contactError.setValue("");
		}
		return isValid;
	}

	// Birthdate validation
	public boolean dateOfBirthValid() {
		boolean isValid = true;
		if (dateOfBirth.getValue() == null) {
			dateOfBirthError.setValue("Birth date can't be empty.");
			isValid = false;
		} else {
			dateOfBirthError.setValue("");
		}
		return isValid;
	}

	// Gender Validation
	public boolean genderValid() {
		boolean isValid = true;
		if (gender.getSelectedItem() == null) {
			genderError.setValue("Gender must be selected.");
			isValid = false;
		} else {
			genderError.setValue("");
		}
		return isValid;
	}

	// Status validation
	public boolean statusValid() {
		boolean isValid = true;
		if (status.getSelectedItem() == null) {
			statusError.setValue("Status must be selected.");
			isValid = false;
		} else {
			statusError.setValue("");
		}
		return isValid;
	}

	@Listen("onClick=#save")
	public void save() {
		User user;
		try {
			if (formValid) {
				if (selectedUser == null) {
					user = new User();
				} else {
					user = selectedUser;
				}
				user.setName(name.getValue());
				user.setAddress(address.getValue());
				user.setDateOfBirth(dateOfBirth.getValue());
				user.setGender(gender.getSelectedItem().getLabel());
				user.setEmail(email.getValue());
				user.setContact(contact.getValue());
				user.setStatus(status.getSelectedItem() != null ? status.getSelectedItem().getLabel() : "Active");
				if (selectedUser == null) {
					userService.addUser(user);
					Messagebox.show("User saved successfully.");
				} else {
					userService.updateUser(user);
					Messagebox.show("User updated successfully.");
				}
				reset();
				loadUserList();
			} 
		} catch (Exception e) {
			Messagebox.show("Error saving user: " + e.getMessage());
		}
	}

	// Reset form
	@Listen("onClick=#reset")
	public void reset() {
		name.setValue("");
		address.setValue("");
		dateOfBirth.setValue(null);
		gender.setSelectedItem(null);
		email.setValue("");
		contact.setValue("");
		status.setSelectedIndex(0);
		selectedUser = null;
		clearFormError();
		save.setLabel("Save");
	}

	public void clearFormError() {
		nameError.setValue("");
		addressError.setValue("");
		dateOfBirthError.setValue("");
		genderError.setValue("");
		emailError.setValue("");
		contactError.setValue(""); 
		statusError.setValue("");
		formValid = false;
		updateButtonVisibility();
	}

	// Get users in list
	public void loadUserList() {
		int totalRecords = userService.getTotalUserCount();
		paging.setTotalSize(totalRecords);
		List<User> userListData = userService.getUsers(currentPage * pageSize, pageSize);
		SimpleDateFormat dateFormate = new SimpleDateFormat("dd/MM/yyyy");
		userList.getItems().clear();
		for (User user : userListData) {
			Listitem item = new Listitem();
			item.appendChild(new Listcell(user.getName()));
			item.appendChild(new Listcell(user.getEmail()));
			item.appendChild(new Listcell(user.getAddress()));
			item.appendChild(new Listcell(user.getContact()));
			item.appendChild(new Listcell(user.getGender()));
			String date = dateFormate.format(user.getDateOfBirth());
			item.appendChild(new Listcell(date));
			item.appendChild(new Listcell(user.getStatus()));
			Listcell actionCell = new Listcell();
			Button editButton = new Button("Edit");
			editButton.addEventListener("onClick", event -> editUser(user));
			actionCell.appendChild(editButton);
			editButton.setStyle("background-color: green");
			Button deleteButton = new Button("Delete");
			deleteButton.addEventListener("onClick", event -> deleteUser(user));
			actionCell.appendChild(deleteButton);
			item.appendChild(actionCell);
			deleteButton.setStyle("margin-left:10px;background-color:red");
			userList.appendChild(item);
		}
	}

	// Update existing user
	public void editUser(User user) {
		clearFormError();
		selectedUser = user;
		name.setValue(user.getName());
		address.setValue(user.getAddress());
		dateOfBirth.setValue(user.getDateOfBirth());
		gender.setSelectedItem(gender.getItemAtIndex("Male".equals(user.getGender()) ? 0 : 1));
		email.setValue(user.getEmail());
		contact.setValue(user.getContact());
		status.setSelectedItem(status.getItemAtIndex("Active".equals(user.getStatus()) ? 0 : 1));
		save.setLabel("Update");
		updateButtonVisibility();
		
	}

	// delete existing user
	public void deleteUser(User user) {
		userService.deleteUser(user);
		Messagebox.show("User deleted successfully.");
		if (currentPage > 0 && userList.getItemCount() == 1) {
			currentPage--;
			paging.setActivePage(currentPage);
		}
		reset();
		clearFormError();
		loadUserList();
	}

}