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

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Myrle Krantz
 */
public class EventExpectation {
  private final EventKey key;
  private boolean eventFound = false;
  private boolean eventWithdrawn = false;

  private final ReentrantLock lock = new ReentrantLock();

  private final Condition found = lock.newCondition();

  EventExpectation(final EventKey key) {
    this.key = key;
  }

  EventKey getKey() {
    return key;
  }

  void setEventFound() {
    lock.lock();
    try {
      this.eventFound = true;
      found.signal();
    }
    finally {
      lock.unlock();
    }
  }

  void setEventWithdrawn() {
    lock.lock();
    try {
      this.eventWithdrawn = true;
      found.signal();
    }
    finally {
      lock.unlock();
    }
  }

  @SuppressWarnings("WeakerAccess")
  public boolean waitForOccurrence(long timeout, TimeUnit timeUnit) throws InterruptedException {

    lock.lock();
    try {
      if (eventFound)
        return true;

      if (eventWithdrawn)
        return false;

      found.await(timeout, timeUnit);

      return (eventFound);
    }
    finally {
      lock.unlock();
    }
  }

  @Override
  public String toString() {
    return key.toString();
  }
}
