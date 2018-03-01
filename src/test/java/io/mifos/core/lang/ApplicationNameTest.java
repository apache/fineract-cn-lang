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
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Myrle Krantz
 */
@RunWith(Parameterized.class)
public class ApplicationNameTest {
  private final TestCase testCase;

  public ApplicationNameTest(final TestCase testCase) {
    this.testCase = testCase;
  }

  @Parameterized.Parameters
  public static Collection testCases() {
    final Collection<TestCase> ret = new ArrayList<>();

    ret.add(new TestCase("1isis-v1").exception("app name should begin with a letter"));
    ret.add(new TestCase("isis-va1").exception("the version marker v should be followed by a number"));
    ret.add(new TestCase("isis-1").exception("the version needs to be preceeded by 'v'"));
    ret.add(new TestCase("isis-V1").exception("capital V for version part is not allowed"));
    ret.add(new TestCase("i-v1").exception("one character is not allowed"));
    ret.add(new TestCase("i/v-v1").exception("/ is not allowed"));
    ret.add(new TestCase("i.v-v1").exception(". is not allowed"));
    ret.add(new TestCase("isisHorus-v1").exception("camel case is not allowed"));
    ret.add(new TestCase("isis").exception("app name must have a version"));

    ret.add(new TestCase("/isis-v1")
        .appNameWithVersion("isis", "1")
        .toStringOutput("isis-v1")
    );
    ret.add(new TestCase("isis-v1_2.34")
        .appNameWithVersion("isis", "1_2.34")
        .toStringOutput("isis-v1_2.34")
    );
    ret.add(new TestCase("isis_horus-v1")
        .appNameWithVersion("isis_horus", "1")
        .toStringOutput("isis_horus-v1")
    );

    return ret;
  }

  @Test()
  public void testParse() {
    try {
      final ApplicationName testSubject =
          ApplicationName.parse(testCase.springApplicationName);

      Assert.assertFalse("An exception was expected for the application name "
              + testCase.springApplicationName + " because " + testCase.exceptionReason + ".",
          testCase.exceptionExpected);

      Assert.assertEquals(testCase.expectedOutput, testSubject);
      Assert.assertEquals(testCase.expectedOutput.getServiceName(), testSubject.getServiceName());
      Assert.assertEquals(testCase.expectedOutput.getVersionString(), testSubject.getVersionString());
      Assert.assertEquals(testCase.toStringOutput, testSubject.toString());
      Assert.assertEquals(testCase.expectedOutput.hashCode(), testSubject.hashCode());
    } catch (final IllegalArgumentException e) {
      Assert.assertTrue("An exception was not expected for the application name "
          + testCase.springApplicationName, testCase.exceptionExpected);
    }
  }

  private static class TestCase {
    final String springApplicationName;
    ApplicationName expectedOutput;
    boolean exceptionExpected;
    private String exceptionReason;
    private String toStringOutput;

    TestCase(final String springApplicationName) {
      this.springApplicationName = springApplicationName;
      this.exceptionExpected = false;
    }

    TestCase appNameWithVersion(final String name, final String version) {
      expectedOutput = ApplicationName.appNameWithVersion(name, version);
      return this;
    }

    TestCase exception(final String reason) {
      this.exceptionReason = reason;
      this.exceptionExpected = true;
      return this;
    }

    TestCase toStringOutput(final String toStringOutput) {
      this.toStringOutput = toStringOutput;
      return this;
    }
  }
}
