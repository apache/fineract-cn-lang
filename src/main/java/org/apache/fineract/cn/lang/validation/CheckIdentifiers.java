/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.fineract.cn.lang.validation;

import org.apache.fineract.cn.lang.validation.constraints.ValidIdentifiers;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

/**
 * @author Myrle Krantz
 */
public class CheckIdentifiers implements ConstraintValidator<ValidIdentifiers, List<String>> {
  private int maximumLength = 32;
  private boolean optional;

  @Override
  public void initialize(final ValidIdentifiers constraintAnnotation) {
    maximumLength = constraintAnnotation.maxLength();
    optional = constraintAnnotation.optional();

  }

  @Override
  public boolean isValid(final List<String> value, final ConstraintValidatorContext context) {
    if (optional && value == null)
      return true;

    return value != null && value.stream().allMatch(x -> x != null && CheckIdentifier.validate(x, maximumLength));
  }
}
