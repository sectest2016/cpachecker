/*
 *  CPAchecker is a tool for configurable software verification.
 *  This file is part of CPAchecker.
 *
 *  Copyright (C) 2007-2012  Dirk Beyer
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
package org.sosy_lab.cpachecker.cpa.predicate.blocking;

import org.sosy_lab.common.LogManager;
import org.sosy_lab.common.configuration.Configuration;
import org.sosy_lab.common.configuration.InvalidConfigurationException;
import org.sosy_lab.common.configuration.Options;
import org.sosy_lab.cpachecker.cfa.CFA;
import org.sosy_lab.cpachecker.cfa.objectmodel.CFAEdge;
import org.sosy_lab.cpachecker.cfa.objectmodel.CFANode;
import org.sosy_lab.cpachecker.core.interfaces.AbstractElement;
import org.sosy_lab.cpachecker.cpa.predicate.interfaces.BlockOperator;
import org.sosy_lab.cpachecker.util.clustering.CallgraphClusterer;
import org.sosy_lab.cpachecker.util.predicates.PathFormula;

@Options(prefix="cpa.predicate.blk.controlflowcluster")
public class ControlFlowClusterBlockOperator extends AbstractBlockOperator implements BlockOperator {

  public ControlFlowClusterBlockOperator(Configuration pConfig, LogManager pLogger, CFA pCFA) throws InvalidConfigurationException {
    super(pConfig, pLogger, pCFA);
    pConfig.inject(this);

    CallgraphClusterer clusterer = new CallgraphClusterer(pConfig, pLogger);
    clusterer.computeAndAssignClustering(pCFA);
  }

  @Override
  public boolean isBlockEnd(AbstractElement pElement, CFAEdge pCfaEdge, PathFormula pPf) {
    CFANode predLoc = pCfaEdge.getPredecessor();
    CFANode succLoc = pCfaEdge.getSuccessor();

    if (isLoopHead(succLoc)) {
      return true;
    } else if (predLoc.getClusterId() != succLoc.getClusterId()) {
      assert(isFunctionCall(succLoc));
      return true;
    } else {
      return false;
    }
  }

}
