package org.rjung.alfred.objects;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;

public class Errors {

    Map<Path, Set<String>> failures;

    public Errors(ConstraintViolationException ex) {
        this.failures = new HashMap<Path, Set<String>>();
        for (ConstraintViolation<?> failure : ex.getConstraintViolations()) {
            Path propertyPath = failure.getPropertyPath();
            if (failures.containsKey(propertyPath)) {
                failures.get(propertyPath).add(failure.getMessage());
            } else {
                HashSet<String> messages = new HashSet<String>();
                messages.add(failure.getMessage());
                failures.put(propertyPath, messages);
            }
        }
    }

    public Map<Path, Set<String>> getFailures() {
        return failures;
    }
}
