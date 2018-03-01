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

import io.mifos.core.lang.validation.constraints.ValidApplicationName;
import org.junit.Assert;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * @author Myrle Krantz
 */
public class ValidApplicationNameTest {
  @Test
  public void validApplicationName()
  {
    final AnnotatedClass annotatedInstance = new AnnotatedClass("blub-v1");
    Assert.assertTrue(isValid(annotatedInstance));
  }

  @Test
  public void invalidAppplicationName()
  {
    final AnnotatedClass annotatedInstance = new AnnotatedClass("blubv1");
    Assert.assertFalse(isValid(annotatedInstance));
  }

  @Test
  public void tooLongAppplicationName()
  {
    final StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < 65 -3; i++) {
      stringBuilder.append("b");
    }
    stringBuilder.append("-v1");

    final AnnotatedClass annotatedInstance = new AnnotatedClass(stringBuilder.toString());
    Assert.assertFalse(isValid(annotatedInstance));
  }

  @Test
  public void nullAppplicationName()
  {
    final AnnotatedClass annotatedInstance = new AnnotatedClass(null);
    Assert.assertFalse(isValid(annotatedInstance));
  }

  private boolean isValid(final AnnotatedClass annotatedInstance)
  {

    final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    final Validator validator = factory.getValidator();
    final Set<ConstraintViolation<AnnotatedClass>> errors = validator.validate(annotatedInstance);

    return errors.size() == 0;
  }

  private static class AnnotatedClass {
    @ValidApplicationName
    String applicationName;

    AnnotatedClass(final String applicationName) {
      this.applicationName = applicationName;
    }
  }
}
