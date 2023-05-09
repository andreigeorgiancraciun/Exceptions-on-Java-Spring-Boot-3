package com.myloanz.partnership.exception;
 
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
 
import java.time.LocalDate;
import java.time.Period;
 
public class AgeValidator implements ConstraintValidator<Age, LocalDate> {
 
    private int min;
    private int max;
 
    @Override
    public void initialize(Age constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }
 
    @Override
    public boolean isValid(LocalDate birthDate, ConstraintValidatorContext context) {
        var age = Period.between(birthDate, LocalDate.now()).getYears();
 
        if (age < this.min || age > this.max) {
            ((ConstraintValidatorContextImpl) context).addMessageParameter("currentAge", age);
            ((ConstraintValidatorContextImpl) context).addMessageParameter("minAge", this.min);
            ((ConstraintValidatorContextImpl) context).addMessageParameter("maxAge", this.max);
 
            return false;
        }
 
        return true;
    }
}