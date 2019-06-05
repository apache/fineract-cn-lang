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
package org.apache.fineract.cn.lang.security;

import static junit.framework.TestCase.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RsaKeyPairFactoryTest {

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;

  @Before
  public void setUpStreams() {
    System.setOut(new PrintStream(outContent));
  }

  @After
  public void restoreStreams() {
    System.setOut(originalOut);
  }

  @Test
  public void shouldCreateRsaKeys() throws Exception {
    final RsaKeyPairFactory.KeyPairHolder keyPairHolder = RsaKeyPairFactory.createKeyPair();
    Assert.assertNotNull(keyPairHolder);
    Assert.assertNotNull(keyPairHolder.getTimestamp());
    Assert.assertFalse(keyPairHolder.getTimestamp().contains("."));
    Assert.assertNotNull(keyPairHolder.publicKey());
    Assert.assertNotNull(keyPairHolder.privateKey());
  }

  @Test
  public void shouldPrintSpringStyle() {
    RsaKeyPairFactory.main(new String[]{"SPRING"});

    String systemOutput = outContent.toString();
    assertTrue(systemOutput.contains("system.publicKey.exponent="));
    assertTrue(systemOutput.contains("system.publicKey.modulus="));
    assertTrue(systemOutput.contains("system.publicKey.timestamp="));
    assertTrue(systemOutput.contains("system.privateKey.modulus="));
    assertTrue(systemOutput.contains("system.privateKey.exponent="));
  }

  @Test
  public void shouldPrintUnixStyle() {
    RsaKeyPairFactory.main(new String[]{"UNIX"});

    String systemOutput = outContent.toString();
    assertTrue(systemOutput.contains("PUBLIC_KEY_EXPONENT="));
    assertTrue(systemOutput.contains("PUBLIC_KEY_MODULUS="));
    assertTrue(systemOutput.contains("PUBLIC_KEY_TIMESTAMP="));
    assertTrue(systemOutput.contains("PRIVATE_KEY_MODULUS="));
    assertTrue(systemOutput.contains("PRIVATE_KEY_EXPONENT="));
  }

}
