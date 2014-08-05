/*
 *  CPAchecker is a tool for configurable software verification.
 *  This file is part of CPAchecker.
 *
 *  Copyright (C) 2007-2011  Dirk Beyer
 *  All rights reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 *  CPAchecker web page:
 *    http://cpachecker.sosy-lab.org
 */
package org.sosy_lab.cpachecker.core.algorithm.tiger.goals;

import org.sosy_lab.cpachecker.core.algorithm.tiger.fql.ecp.ElementaryCoveragePattern;
import org.sosy_lab.cpachecker.core.algorithm.tiger.fql.ecp.translators.GuardedEdgeLabel;
import org.sosy_lab.cpachecker.core.algorithm.tiger.fql.ecp.translators.GuardedLabel;
import org.sosy_lab.cpachecker.core.algorithm.tiger.fql.ecp.translators.ToGuardedAutomatonTranslator;
import org.sosy_lab.cpachecker.util.automaton.NondeterministicFiniteAutomaton;
import org.sosy_lab.cpachecker.util.predicates.interfaces.Region;

public class Goal {

  private ElementaryCoveragePattern mPattern;
  private NondeterministicFiniteAutomaton<GuardedEdgeLabel> mAutomaton;
  private int mIndex;
  private Region mPresenceCondition;

  public Goal(int pIndex, ElementaryCoveragePattern pPattern, GuardedEdgeLabel pAlphaLabel, GuardedEdgeLabel pInverseAlphaLabel, GuardedLabel pOmegaLabel,
      Region pPresenceCondition) {
    mIndex = pIndex;
    mPattern = pPattern;
    mAutomaton = ToGuardedAutomatonTranslator.toAutomaton(mPattern, pAlphaLabel, pInverseAlphaLabel, pOmegaLabel);
    mPresenceCondition = pPresenceCondition;
  }

  public Goal(int pIndex, ElementaryCoveragePattern pPattern, NondeterministicFiniteAutomaton<GuardedEdgeLabel> pAutomaton, Region pPresenceCondition) {
    mIndex = pIndex;
    mPattern = pPattern;
    mAutomaton = pAutomaton;
    mPresenceCondition = pPresenceCondition;
  }

  public int getIndex() {
    return mIndex;
  }
  public Region getPresenceCondition() {
    return mPresenceCondition;
  }

  public ElementaryCoveragePattern getPattern() {
    return mPattern;
  }

  public NondeterministicFiniteAutomaton<GuardedEdgeLabel> getAutomaton() {
    return mAutomaton;
  }

  public void setPresenceCondition(Region pPresenceCondition) {
    mPresenceCondition = pPresenceCondition;
  }

}
