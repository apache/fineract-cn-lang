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
package io.mifos.core.lang.security;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;

/**
 * @author Myrle Krantz
 */
@SuppressWarnings("WeakerAccess")
public class RsaPublicKeyBuilder {

  private BigInteger publicKeyMod;
  private BigInteger publicKeyExp;

  public RsaPublicKeyBuilder setPublicKeyMod(final BigInteger publicKeyMod) {
    this.publicKeyMod = publicKeyMod;
    return this;
  }

  public RsaPublicKeyBuilder setPublicKeyExp(final BigInteger publicKeyExp) {
    this.publicKeyExp = publicKeyExp;
    return this;
  }

  public PublicKey build() {
    try {
      final KeyFactory keyFactory = KeyFactory.getInstance("RSA");
      final RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(publicKeyMod, publicKeyExp);
      return keyFactory.generatePublic(rsaPublicKeySpec);
    } catch (final NoSuchAlgorithmException | InvalidKeySpecException e) {
      throw new IllegalStateException("Could not read public RSA key pair!", e);
    }
  }
}
