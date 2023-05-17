package com.example.travelagency.model;

public enum ResponseMessage {
    INVALID_CREDENTIALS("Invalid_Credentials."),
    INVALID_CLIENT_VACATION("Vacation id or Client id are invalid."),
    INVALID_BODY("Error parsing JSON body"),
    INVALID_DATE_RANGE("Invalid date range"),
    //client messages
    CLIENT_CREATED_SUCCESSFULLY("Client created successfully."),
    CLIENT_ALREADY_EXISTS("Client with this email already exists."),
    CLIENT_NOT_FOUND("Client not found."),
    CLIENT_UPDATED_SUCCESSFULLY("Client updated successfully"),

    //vacation messages
    VACATION_CREATED_SUCCESSFULLY("Vacation created successfully."),
    VACATION_CREATION_FAILED("Failed to create vacation."),
    VACATION_NOT_FOUND("Vacation not found."),
    VACATION_UPDATED_SUCCESSFULLY("Vacation updated successfully."),
    VACATION_INVALID_TYPE("Invalid vacation type."),
    VACATION_DELETED_SUCCESSFULLY("Vacation deleted successfully."),

    //reservation messages
    RESERVATION_CREATED_SUCCESSFULLY("Reservation created successfully."),
    RESERVATION_CREATION_FAILED("Failed to create reservation."),
    RESERVATION_NOT_FOUND("Reservation not found."),

    EMPLOYEE_NOT_FOUND("Employee not found"),
    EMPLOYEE_UPDATED_SUCCESSFULLY("Employee updated successfully"),
    EMPLOYEE_DELETED_SUCCESSFULLY("Employee deleted successfully"),
    EMPLOYEE_ALREADY_EXISTS("Employee already exists."),
    EMPLOYEE_CREATED_SUCCESSFULLY("Employee created successfully."),
    RESERVATION_NOT_ENOUGH_SEATS("Not enough available seats"),
    RESERVATION_UPDATE_FAILED("Update failed."),
    RESERVATION_UPDATED_SUCCESSFULLY("Reservation updated successfully.");

    private final String message;

    ResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
