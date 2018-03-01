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
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

import java.time.LocalDateTime;

import static io.mifos.core.lang.validation.TestHelper.isValid;

/**
 * @author Myrle Krantz
 */
public class ValidLocalDateTimeStringTest {

  @Test
  public void now()
  {
    final AnnotatedClassNullable annotatedInstance = new AnnotatedClassNullable(DateConverter.toIsoString(LocalDateTime.now()));
    Assert.assertTrue(isValid(annotatedInstance));
  }

  @Test
  public void invalidString()
  {
    final AnnotatedClassNullable annotatedInstance = new AnnotatedClassNullable("This is not a date time.");
    Assert.assertFalse(isValid(annotatedInstance));
  }

  @Test
  public void nullLocalDateTimeStringAllowed()
  {
    final AnnotatedClassNullable annotatedInstance = new AnnotatedClassNullable(null);
    Assert.assertTrue(isValid(annotatedInstance));
  }

  @Test
  public void nullLocalDateTimeStringNotAllowed()
  {
    final AnnotatedClassNotNull annotatedInstance = new AnnotatedClassNotNull(null);
    Assert.assertFalse(isValid(annotatedInstance));
  }

  private static class AnnotatedClassNullable {
    @Nullable
    @ValidLocalDateTimeString()
    String localDateTimeString;

    AnnotatedClassNullable(final String localDateTimeString) {
      this.localDateTimeString = localDateTimeString;
    }
  }

  private static class AnnotatedClassNotNull {
    @NotNull
    @ValidLocalDateTimeString()
    String localDateTimeString;

    AnnotatedClassNotNull(final String localDateTimeString) {
      this.localDateTimeString = localDateTimeString;
    }
  }
}
