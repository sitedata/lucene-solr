package org.apache.lucene.search;

/* ====================================================================
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2001 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "Apache" and "Apache Software Foundation" and
 *    "Apache Lucene" must not be used to endorse or promote products
 *    derived from this software without prior written permission. For
 *    written permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache",
 *    "Apache Lucene", nor may "Apache" appear in their name, without
 *    prior written permission of the Apache Software Foundation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 */

import junit.framework.TestCase;
import org.apache.lucene.analysis.WhitespaceAnalyzer;

public class TestQueryTermVector extends TestCase {


  public TestQueryTermVector(String s) {
    super(s);
  }

  protected void setUp() {
  }

  protected void tearDown() {

  }

  public void testConstructor() {
    String [] queryTerm = {"foo", "bar", "foo", "again", "foo", "bar", "go", "go", "go"};
    //Items are sorted lexicographically
    String [] gold = {"again", "bar", "foo", "go"};
    int [] goldFreqs = {1, 2, 3, 3};
    QueryTermVector result = new QueryTermVector(queryTerm);
    assertTrue(result != null);
    String [] terms = result.getTerms();
    assertTrue(terms.length == 4);
    int [] freq = result.getTermFrequencies();
    assertTrue(freq.length == 4);
    checkGold(terms, gold, freq, goldFreqs);
    result = new QueryTermVector(null);
    assertTrue(result.getTerms().length == 0);
    
    result = new QueryTermVector("foo bar foo again foo bar go go go", new WhitespaceAnalyzer());
    assertTrue(result != null);
    terms = result.getTerms();
    assertTrue(terms.length == 4);
    freq = result.getTermFrequencies();
    assertTrue(freq.length == 4);
    checkGold(terms, gold, freq, goldFreqs);
  }

  private void checkGold(String[] terms, String[] gold, int[] freq, int[] goldFreqs) {
    for (int i = 0; i < terms.length; i++) {
      assertTrue(terms[i].equals(gold[i]));
      assertTrue(freq[i] == goldFreqs[i]);
    }
  }
}