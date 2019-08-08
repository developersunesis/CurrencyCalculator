package com.android.assessment.currencycalculator;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Email address validation
 */
public class EmailValidatorTest {

    @Test
    public void email_isWellformed() {
        assertTrue(ValidateEmail.isValidEmail("developersunesis@gmail.com"));
    }

    @Test
    public void email_isNotWellformed() {
        assertFalse(ValidateEmail.isValidEmail("developersunesis@gmail."));
    }
}
