/*
 *  CPAchecker is a tool for configurable software verification.
 *  This file is part of CPAchecker.
 *
 *  Copyright (C) 2007-2016  Dirk Beyer
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
package org.sosy_lab.cpachecker.core.algorithm.pdr;

import org.sosy_lab.common.time.Timer;
import org.sosy_lab.cpachecker.core.CPAcheckerResult.Result;
import org.sosy_lab.cpachecker.core.interfaces.Statistics;
import org.sosy_lab.cpachecker.core.reachedset.ReachedSet;

import java.io.PrintStream;

import javax.annotation.Nullable;

public class PDRStatistics implements Statistics {

  private final Timer errorPathCreation = new Timer();

  @Override
  public void printStatistics(PrintStream pOut, Result pResult, ReachedSet pReached) {
    if (errorPathCreation.getNumberOfIntervals() > 0) {
      pOut.println("Time for error path creation:        " + errorPathCreation);
    }
  }

  @Override
  public @Nullable String getName() {
    return "PDR algorithm";
  }

  Timer getErrorPathCreationTimer() {
    return errorPathCreation;
  }
}
