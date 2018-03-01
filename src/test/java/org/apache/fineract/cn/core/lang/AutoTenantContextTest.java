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
package org.apache.fineract.cn.core.lang;

import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

/**
 * @author Myrle Krantz
 */
public class AutoTenantContextTest {
  @Test
  public void stateIsUndisturbedOutsideOfTryBlock()
  {
    TenantContextHolder.setIdentifier("x");

    //noinspection EmptyTryBlock
    try (final AutoTenantContext ignored = new AutoTenantContext("m"))
    {
      Assert.assertEquals(TenantContextHolder.identifier(), Optional.of("m"));
    }

    Assert.assertEquals(TenantContextHolder.identifier(), Optional.of("x"));

    TenantContextHolder.clear();
  }

}