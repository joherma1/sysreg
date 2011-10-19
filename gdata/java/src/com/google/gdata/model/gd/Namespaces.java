/* Copyright (c) 2008 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.google.gdata.model.gd;

import com.google.gdata.util.common.xml.XmlNamespace;

/**
 * GData namespace definitions related to Google.
 *
 * 
 */
public class Namespaces {

  private Namespaces() {}

  /** Google data (GD) namespace */
  public static final String g = "http://schemas.google.com/g/2005";

  /** Google data (GD) namespace prefix */
  public static final String gPrefix = g + "#";

  /** Google data (GD) namespace alias */
  public static final String gAlias = "gd";

  /** XML writer namespace for Google data (GD) */
  public static final XmlNamespace gNs = new XmlNamespace(gAlias, g);

}
