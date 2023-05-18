package ru.clevertec.users.models.responses;

/**
 * Response with ID that was affected during operation and message about execution
 */
public record ModificationResponse(long id, String message) {
}
