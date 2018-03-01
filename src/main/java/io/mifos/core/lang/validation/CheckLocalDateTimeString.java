/*
 * Copyright 2018 The Apache Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.mifos.core.lang.validation;

import io.mifos.core.lang.DateConverter;
import io.mifos.core.lang.validation.constraints.ValidLocalDateTimeString;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.DateTimeException;

/**
 * @author Myrle Krantz
 */
public class CheckLocalDateTimeString implements ConstraintValidator<ValidLocalDateTimeString, String> {
   public void initialize(ValidLocalDateTimeString constraint) {
   }

   public boolean isValid(final String obj, ConstraintValidatorContext context) {
      if (obj == null)
         return true;
      try {
         DateConverter.fromIsoString(obj);
         return true;
      }
      catch (final DateTimeException e) {
         return false;
      }
   }
}
