/*
 *  CPAchecker is a tool for configurable software verification.
 *  This file is part of CPAchecker. 
 *
 *  Copyright (C) 2007-2008  Dirk Beyer and Erkan Keremoglu.
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
 *    http://www.cs.sfu.ca/~dbeyer/CPAchecker/
 */
package cpa.symbpredabs;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.Vector;
import java.util.logging.Level;

import cmdline.CPAMain;

import cfa.objectmodel.CFANode;

import common.Pair;

import cpa.common.interfaces.AbstractElementWithLocation;

public class AbstractReachabilityTree {
    private Map<AbstractElementWithLocation, 
                Collection<AbstractElementWithLocation>> tree;
    private AbstractElementWithLocation root;

    public AbstractReachabilityTree() {
        tree = new HashMap<AbstractElementWithLocation, 
                           Collection<AbstractElementWithLocation>>();
        root = null;
    }

    public void addChild(AbstractElementWithLocation parent, 
                         AbstractElementWithLocation child) {
        if (root == null) {
            root = parent;
        }
        if (!tree.containsKey(parent)) {
            tree.put(parent, new Vector<AbstractElementWithLocation>());
        }
        Collection<AbstractElementWithLocation> c = tree.get(parent);
        c.add(child);
    }
    
    public Collection<AbstractElementWithLocation> getChildren(
            AbstractElementWithLocation e) {
        if (tree.containsKey(e)) {
            return tree.get(e);
        }
        return Collections.emptySet();
    }

    public Collection<AbstractElementWithLocation> getSubtree(
            AbstractElementWithLocation root,
            boolean remove, boolean includeRoot) {
        Vector<AbstractElementWithLocation> ret = 
            new Vector<AbstractElementWithLocation>();

        Stack<AbstractElementWithLocation> toProcess = 
            new Stack<AbstractElementWithLocation>();
        toProcess.push(root);

        while (!toProcess.empty()) {
            AbstractElementWithLocation cur = toProcess.pop();
            ret.add(cur);
            if (tree.containsKey(cur)) {
                toProcess.addAll(remove ? tree.remove(cur) : tree.get(cur));
            }
        }
        if (!includeRoot) {
            AbstractElementWithLocation tmp = ret.lastElement();
            assert(ret.firstElement() == root);
            ret.setElementAt(tmp, 0);
            ret.remove(ret.size()-1);
        }
        return ret;
    }

    public AbstractElementWithLocation findHighest(CFANode loc) {
        if (root == null) return null;

        Queue<AbstractElementWithLocation> toProcess =
            new ArrayDeque<AbstractElementWithLocation>();
        toProcess.add(root);

        while (!toProcess.isEmpty()) {
            AbstractElementWithLocation e = toProcess.remove();
            if (e.getLocationNode().equals(loc)) {
                return e;
            }
            if (tree.containsKey(e)) {
                toProcess.addAll(tree.get(e));
            }
        }
        CPAMain.logManager.log(Level.WARNING, "ERROR, NOT FOUND: " + loc);
        //assert(false);
        //return null;
        return root;
    }
    
    public Collection<AbstractElementWithLocation> findAll(CFANode loc) {
        if (root == null) return null;

        Queue<AbstractElementWithLocation> toProcess =
            new ArrayDeque<AbstractElementWithLocation>();
        toProcess.add(root);
        
        Collection<AbstractElementWithLocation> ret = 
            new Vector<AbstractElementWithLocation>();

        while (!toProcess.isEmpty()) {
            AbstractElementWithLocation e = toProcess.remove();
            if (e.getLocationNode().equals(loc)) {
                ret.add(e);
            }
            if (tree.containsKey(e)) {
                toProcess.addAll(tree.get(e));
            }
        }
        return ret;        
    }
    
    public AbstractElementWithLocation getRoot() { return root; }
    
    public boolean contains(AbstractElementWithLocation n) {
        return tree.containsKey(n);
    }
    
    public boolean inTree(AbstractElementWithLocation n) {
        Stack<AbstractElementWithLocation> toProcess = 
            new Stack<AbstractElementWithLocation>();
        toProcess.push(root);
        while (!toProcess.empty()) {
            AbstractElementWithLocation e = toProcess.pop();
            if (e == n) return true;
            toProcess.addAll(getChildren(e));
        }
        return false;
    }
    
    public void clear() {
        root = null;
        tree.clear();
    }
    
    public void dump(String outfile) throws IOException {
        PrintWriter out = new PrintWriter(new File(outfile));
        out.println("digraph ART {");
        Stack<Pair<AbstractElementWithLocation, Integer>> toProcess = 
            new Stack<Pair<AbstractElementWithLocation, Integer>>();
        int i = 0;
        if (root != null) {
            toProcess.push(
                    new Pair<AbstractElementWithLocation, Integer>(root, i));
            out.println("" + (i++) + " [label=\"" + root + "\"];");
        }

        while (!toProcess.empty()) {
            Pair<AbstractElementWithLocation, Integer> e = toProcess.pop();
            for (AbstractElementWithLocation c : getChildren(e.getFirst())) {
                int cur = i;
                out.println("" + cur + " [label=\"" + c + "\"];");
                out.println("" + e.getSecond() + " -> " + cur);
                toProcess.push(
                        new Pair<AbstractElementWithLocation, Integer>(c, i));
                ++i;
            }
        }
        out.println("}");
        out.flush();
        out.close();
    }
}
