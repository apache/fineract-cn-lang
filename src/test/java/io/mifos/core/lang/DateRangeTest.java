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
package io.mifos.core.lang;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

/**
 * @author Myrle Krantz
 */
public class DateRangeTest {

  @Test
  public void dateRangeFromIsoString() {
    final String startString = "2017-07-13Z";
    final String endString = "2017-07-16Z";
    final LocalDate start = DateConverter.dateFromIsoString(startString);
    final LocalDate end = DateConverter.dateFromIsoString(endString);
    final String dateRangeString = startString + ".." + endString;

    final DateRange dateRange = DateRange.fromIsoString(dateRangeString);
    Assert.assertEquals(dateRangeString, dateRange.toString());
    Assert.assertEquals(start.atStartOfDay(), dateRange.getStartDateTime());
    Assert.assertEquals(end.plusDays(1).atStartOfDay(), dateRange.getEndDateTime());
    Assert.assertEquals(4, dateRange.stream().count());

    final DateRange dateRangeToday = DateRange.fromIsoString(null);
    Assert.assertEquals(1, dateRangeToday.stream().count());

    try {
      final DateRange dateRangeHalf = DateRange.fromIsoString(startString);
      Assert.fail("Invalid date range format should throw exception.");
    }
    catch (final ServiceException ignore) {

    }

    try {
      final DateRange dateRangeHalf = DateRange.fromIsoString("notanisostringZ..anothernonisostringZ");
      Assert.fail("Invalid date range format should throw exception.");
    }
    catch (final ServiceException ignore) {

    }
  }
}
