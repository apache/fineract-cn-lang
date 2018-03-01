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

import io.mifos.core.lang.TenantContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SuppressWarnings("WeakerAccess")
public final class TenantHeaderFilter extends OncePerRequestFilter {

  public static final String TENANT_HEADER = "X-Tenant-Identifier";

  public TenantHeaderFilter() {
    super();
  }

  @Override
  protected void doFilterInternal(final HttpServletRequest request,
                                  final HttpServletResponse response,
                                  final FilterChain filterChain) throws ServletException, IOException {
    final String tenantHeaderValue = request.getHeader(TenantHeaderFilter.TENANT_HEADER);

    if (tenantHeaderValue == null || tenantHeaderValue.isEmpty()) {
      response.sendError(400, "Header [" + TENANT_HEADER + "] must be given!");
    } else {
      TenantContextHolder.clear();
      TenantContextHolder.setIdentifier(tenantHeaderValue);
    }

    try {
      filterChain.doFilter(request, response);
    } finally {
      TenantContextHolder.clear();
    }
  }
}
