/*
 * Copyright 2012 Database Lab., Korea Univ.
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

package tajo.engine.planner2.physical;

import tajo.TaskAttemptContext2;
import tajo.catalog.Schema;
import tajo.catalog.SortSpec;
import tajo.storage.Tuple;
import tajo.storage.TupleComparator;

import java.io.IOException;
import java.util.Comparator;

public abstract class SortExec extends UnaryPhysicalExec {
  private final Comparator<Tuple> comparator;
  private final SortSpec [] sortSpecs;

  public SortExec(TaskAttemptContext2 context, Schema inSchema,
                  Schema outSchema, PhysicalExec child, SortSpec [] sortSpecs) {
    super(context, inSchema, outSchema, child);
    this.sortSpecs = sortSpecs;
    this.comparator = new TupleComparator(inSchema, sortSpecs);
  }

  public SortSpec[] getSortSpecs() {
    return sortSpecs;
  }

  public Comparator<Tuple> getComparator() {
    return comparator;
  }

  @Override
  abstract public Tuple next() throws IOException;
}