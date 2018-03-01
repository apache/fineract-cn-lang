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
package io.mifos.core.lang.listening;

import java.util.Objects;

/**
 * @author Myrle Krantz
 */
@SuppressWarnings("WeakerAccess")
public class EventKey {
  private String tenantIdentifier;
  private String eventName;
  private Object event;

  public EventKey(
      final String tenantIdentifier,
      final String eventName,
      final Object event) {
    this.tenantIdentifier = tenantIdentifier;
    this.eventName = eventName;
    this.event = event;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    EventKey eventKey = (EventKey) o;
    return Objects.equals(tenantIdentifier, eventKey.tenantIdentifier) &&
        Objects.equals(eventName, eventKey.eventName) &&
        Objects.equals(event, eventKey.event);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tenantIdentifier,  eventName, event);
  }

  @Override
  public String toString() {
    return "EventKey{" +
        "tenantIdentifier='" + tenantIdentifier + '\'' +
        ", eventName='" + eventName + '\'' +
        ", event=" + event +
        '}';
  }
}
