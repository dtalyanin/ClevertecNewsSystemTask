package ru.clevertec.nms.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import ru.clevertec.nms.exceptions.AccessException;
import ru.clevertec.nms.exceptions.NotFoundException;
import ru.clevertec.nms.models.responses.validation_responses.ValidationResponse;

import static ru.clevertec.nms.utils.ValidationResponsesFactory.*;
import static ru.clevertec.nms.utils.constants.MessageConstants.HEADER_NOT_PRESENT;

@ControllerAdvice
public class GlobalExceptionHandler {


}
