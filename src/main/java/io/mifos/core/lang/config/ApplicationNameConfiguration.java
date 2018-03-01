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
package io.mifos.core.lang.config;

import io.mifos.core.lang.ApplicationName;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Myrle Krantz
 */
@SuppressWarnings("WeakerAccess")
@Configuration
public class ApplicationNameConfiguration {
  @Bean
  public ApplicationName applicationName(
      @Value("${spring.application.name}") final String springApplicationName) {
    return ApplicationName.fromSpringApplicationName(springApplicationName);
  }
}
