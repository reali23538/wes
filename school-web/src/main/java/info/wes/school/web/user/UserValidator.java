package info.wes.school.web.user;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
		// (errors, user의 id, messages properties, default message)
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id", "required.user.id", "required");
	}

}
