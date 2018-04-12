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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Myrle Krantz
 */
@SuppressWarnings("WeakerAccess")
public class TenantedEventListener {
  private final Map<EventKey, EventExpectation> eventExpectations = new ConcurrentHashMap<>();

  public EventExpectation expect(final EventKey eventKey) {
    final EventExpectation value = new EventExpectation(eventKey);
    eventExpectations.put(eventKey, value);
    return value;
  }

  public void notify(final EventKey eventKey) {
    final EventExpectation eventExpectation = eventExpectations.remove(eventKey);
    if (eventExpectation != null) {
      eventExpectation.setEventFound();
    }
  }

  public void withdrawExpectation(final EventExpectation eventExpectation) {
    final EventExpectation expectation = eventExpectations.remove(eventExpectation.getKey());
    if (expectation != null) {
      eventExpectation.setEventWithdrawn();
    }
  }
}