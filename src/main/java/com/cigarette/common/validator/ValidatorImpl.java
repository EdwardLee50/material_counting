//package com.cigarette.common.validator;
//
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.stereotype.Component;
//
//import javax.validation.ConstraintViolation;
//import javax.validation.Validation;
//import javax.validation.Validator;
//import java.util.Set;
//
///**
// * @author EdwardLee <br>
// */
//@Component
//public class ValidatorImpl implements InitializingBean {
//
//    private Validator validator;
//
//    public ValidationResult validate(Object bean){
//        ValidationResult result = new ValidationResult();
//        Set<ConstraintViolation<Object>> constraintViolationSet = validator.validate(bean);
//        if (constraintViolationSet.size() > 0){
//            result.setHasErrors(true);
//            constraintViolationSet.forEach(constraintViolation -> {
//                String propertyName = constraintViolation.getPropertyPath().toString();
//                String errorMessage = constraintViolation.getMessage();
//                result.getErrorMessageMap().put(propertyName,errorMessage);
//            });
//        }
//        return result;
//    }
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
//    }
//}
