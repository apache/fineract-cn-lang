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
package org.apache.fineract.cn.lang.listening;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author Myrle Krantz
 */
public class TenantedEventListenerTest {
  static class SomeState {
    boolean mutableBit = false;
  }

  @Test
  public void shouldWaitForEventOccurenceTrue() throws InterruptedException {
    final TenantedEventListener testSubject = new TenantedEventListener();
    final EventKey eventKey = new EventKey("shouldWaitForEventOccurenceTrue", "blah", "blah");
    final EventExpectation eventExpectation = testSubject.expect(eventKey);
    final SomeState someState = stateThatMutatesIn60MillisThenNotifiesOnEventKey(testSubject, eventKey);

    Assert.assertTrue(eventExpectation.waitForOccurrence(600, TimeUnit.MILLISECONDS));
    Assert.assertTrue(someState.mutableBit);
  }

  @Test
  public void shouldWaitForEventOccurenceFalse() throws InterruptedException {
    final TenantedEventListener testSubject = new TenantedEventListener();
    final EventKey eventKey = new EventKey("shouldWaitForEventOccurenceFalse", "blah", "blah");
    final EventExpectation eventExpectation = testSubject.expect(eventKey);
    final SomeState someState = stateThatMutatesIn60MillisThenNotifiesOnEventKey(testSubject, eventKey);

    Assert.assertFalse(eventExpectation.waitForOccurrence(6, TimeUnit.MILLISECONDS));
    Assert.assertFalse(someState.mutableBit); //Some potential for flakiness here, because this is timing dependent.
  }

  @Test
  public void shouldNotWaitForEventOccurence() throws InterruptedException {
    final TenantedEventListener testSubject = new TenantedEventListener();
    final EventKey eventKey = new EventKey("shouldNotWaitForEventOccurence", "blah", "blah");
    final EventExpectation eventExpectation = testSubject.expect(eventKey);
    final SomeState someState = stateThatMutatesIn60MillisThenNotifiesOnEventKey(testSubject, eventKey);

    testSubject.withdrawExpectation(eventExpectation);

    Assert.assertFalse(eventExpectation.waitForOccurrence(600, TimeUnit.MILLISECONDS));
    Assert.assertFalse(someState.mutableBit); //Some potential for flakiness here, because this is timing dependent.
  }

  private SomeState stateThatMutatesIn60MillisThenNotifiesOnEventKey(
      final TenantedEventListener testSubject,
      final EventKey eventKey) {
    final SomeState someState = new SomeState();
    new Thread(() -> {
      try {
        TimeUnit.MILLISECONDS.sleep(60);
        someState.mutableBit = true;
        testSubject.notify(eventKey);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }).start();
    return someState;
  }
}