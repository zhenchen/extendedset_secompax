/*
* Copyright 2012 Metamarkets Group Inc.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

//package it.uniroma3.mat.extendedset.intset;

import com.google.common.collect.Lists;
import junit.framework.Assert;
//import org.junit.Test;
import it.uniroma3.mat.extendedset.intset.ConciseSet;
import it.uniroma3.mat.extendedset.intset.ConciseSetUtils;
import it.uniroma3.mat.extendedset.intset.ImmutableConciseSet;
import it.uniroma3.mat.extendedset.intset.IntSet;
import it.uniroma3.mat.extendedset.intset.ImmutableConciseSet.WordIterator;

import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;



public class ImmutableConciseSetTest
{
  public static final int NO_COMPLEMENT_LENGTH = -1;
  public static void main(String[] args){
	  
	  testUnion2();
  }
  public static void testCompactOneLitPureOneFill()
  {
    int[] words = {0x80040000,0x10000001,0x80000100};
    int i = 0;
    IntBuffer buffer = IntBuffer.wrap(words);
    ImmutableConciseSet set = new ImmutableConciseSet(buffer);
    ImmutableConciseSet res = ImmutableConciseSet.compact(set);
    ImmutableConciseSet.WordIterator itr = res.newWordIterator();
    String string = res.toString();
    int count = res.getLastWordIndex();
    for(i = 0; i<=count;i++)
    {
    	//System.out.print(res.getWords().get(i));
    }
    //Assert.assertEquals(new Integer(0x40000005), itr.next());
    //Assert.assertEquals(itr.hasNext(), false);
  }
  public static void testWordIteratorNext1()
  {
    final int[] ints = {1, 2, 3, 4, 32};
    ConciseSet set = new ConciseSet();
    for (int i : ints) {
      set.add(i);
    }
    ImmutableConciseSet iSet = ImmutableConciseSet.newImmutableFromMutable(set);
    
    ImmutableConciseSet.WordIterator itr = iSet.newWordIterator();
    int count = iSet.getLastWordIndex();
    for(int i = 0; i<=count;i++)
    {
    	//System.out.printf("%x\n",iSet.getWords().get(i));
    	//System.out.print("\n");
    }
    //Assert.assertEquals(new Integer(0x8000003E), itr.next());

    //Assert.assertEquals(itr.hasNext(), false);
  }
  
  
  public static void testWordIteratorNext2()
  {
    ConciseSet set = new ConciseSet();
    for (int i = 0; i < 100000; i++) {
      set.add(i);

    }
    ImmutableConciseSet iSet = ImmutableConciseSet.newImmutableFromMutable(set);

    ImmutableConciseSet.WordIterator itr = iSet.newWordIterator();
    int count = iSet.getLastWordIndex();
    for(int i = 0; i<=count;i++)
    {
    	//System.out.printf("%x\n",iSet.getWords().get(i));
    	//System.out.print("\n");
    }
    //Assert.assertEquals(new Integer(0x40000C98), itr.next());
    //Assert.assertEquals(new Integer(0x81FFFFFF), itr.next());
    //Assert.assertEquals(itr.hasNext(), false);
  }

  
  
  public static void testWordIteratorAdvanceTo1()
  {
    ConciseSet set = new ConciseSet();
    for (int i = 0; i < 100000; i++) {
      set.add(i);

    }
    ImmutableConciseSet iSet = ImmutableConciseSet.newImmutableFromMutable(set);

    ImmutableConciseSet.WordIterator itr = iSet.newWordIterator();
    itr.advanceTo(50);
    Assert.assertEquals(new Integer(1073744998), itr.next());
    Assert.assertEquals(new Integer(0x81FFFFFF), itr.next());
    Assert.assertEquals(itr.hasNext(), false);
  }


  
  public static void testWordIteratorAdvanceTo2()
  {
    ConciseSet set = new ConciseSet();
    for (int i = 0; i < 100000; i++) {
      set.add(i);

    }
    ImmutableConciseSet iSet = ImmutableConciseSet.newImmutableFromMutable(set);

    ImmutableConciseSet.WordIterator itr = iSet.newWordIterator();
    itr.advanceTo(3225);
    Assert.assertEquals(new Integer(0x81FFFFFF), itr.next());
    Assert.assertEquals(itr.hasNext(), false);
  }

  
  public static void testCompactOneLitOneLit()
  {
    int[] words = {-1, -1};
    ImmutableConciseSet set = new ImmutableConciseSet(IntBuffer.wrap(words));
    ImmutableConciseSet res = ImmutableConciseSet.compact(set);

    ImmutableConciseSet.WordIterator itr = res.newWordIterator();

    Assert.assertEquals(new Integer(0x40000001), itr.next());
    Assert.assertEquals(itr.hasNext(), false);
  }

  
  /*public static void testCompactOneLitPureOneFill()
  {
    int[] words = {-1, 0x40000004};

    ImmutableConciseSet res = ImmutableConciseSet.compact(new ImmutableConciseSet(IntBuffer.wrap(words)));
    ImmutableConciseSet.WordIterator itr = res.newWordIterator();

    Assert.assertEquals(new Integer(0x40000005), itr.next());
    Assert.assertEquals(itr.hasNext(), false);
  }*/

  
  public static void testCompactOneLitDirtyOneFill()
  {
    int[] words = {-1, 0x42000004};

    ImmutableConciseSet res = ImmutableConciseSet.compact(new ImmutableConciseSet(IntBuffer.wrap(words)));
    ImmutableConciseSet.WordIterator itr = res.newWordIterator();

    Assert.assertEquals(new Integer(-1), itr.next());
    Assert.assertEquals(new Integer(0x42000004), itr.next());
    Assert.assertEquals(itr.hasNext(), false);
  }

  
  public static void testCompactOneFillOneLit()
  {
    int[] words = {0x40000004, -1};
    ImmutableConciseSet set = new ImmutableConciseSet(IntBuffer.wrap(words));
    ImmutableConciseSet res = ImmutableConciseSet.compact(set);
    ImmutableConciseSet.WordIterator itr = res.newWordIterator();

    Assert.assertEquals(new Integer(0x40000005), itr.next());
    Assert.assertEquals(itr.hasNext(), false);
  }

  
  public static void testCompactOneFillPureOneFill()
  {
    int[] words = {0x40000004, 0x40000004};

    ImmutableConciseSet res = ImmutableConciseSet.compact(new ImmutableConciseSet(IntBuffer.wrap(words)));
    ImmutableConciseSet.WordIterator itr = res.newWordIterator();

    Assert.assertEquals(new Integer(0x40000009), itr.next());
    Assert.assertEquals(itr.hasNext(), false);
  }

  
  public static void testCompactOneFillDirtyOneFill()
  {
    int[] words = {0x40000004, 0x42000004};

    ImmutableConciseSet res = ImmutableConciseSet.compact(new ImmutableConciseSet(IntBuffer.wrap(words)));
    ImmutableConciseSet.WordIterator itr = res.newWordIterator();

    Assert.assertEquals(new Integer(0x40000004), itr.next());
    Assert.assertEquals(new Integer(0x42000004), itr.next());
    Assert.assertEquals(itr.hasNext(), false);
  }

  
  public static void testCompactZeroLitZeroLit()
  {
    int[] words = {0x80000000, 0x80000000, -1};

    ImmutableConciseSet res = ImmutableConciseSet.compact(new ImmutableConciseSet(IntBuffer.wrap(words)));
    ImmutableConciseSet.WordIterator itr = res.newWordIterator();

    Assert.assertEquals(new Integer(0x00000001), itr.next());
    Assert.assertEquals(new Integer(-1), itr.next());
    Assert.assertEquals(itr.hasNext(), false);
  }

  
  public static void testCompactZeroLitPureZeroFill()
  {
    int[] words = {0x80000000, 0x00000004, -1};

    ImmutableConciseSet res = ImmutableConciseSet.compact(new ImmutableConciseSet(IntBuffer.wrap(words)));
    ImmutableConciseSet.WordIterator itr = res.newWordIterator();

    Assert.assertEquals(new Integer(0x00000005), itr.next());
    Assert.assertEquals(new Integer(-1), itr.next());
    Assert.assertEquals(itr.hasNext(), false);
  }

  
  public static void testCompactZeroLitDirtyZeroFill()
  {
    int[] words = {0x80000000, 0x02000004, -1};

    ImmutableConciseSet res = ImmutableConciseSet.compact(new ImmutableConciseSet(IntBuffer.wrap(words)));
    ImmutableConciseSet.WordIterator itr = res.newWordIterator();

    Assert.assertEquals(new Integer(0x80000000), itr.next());
    Assert.assertEquals(new Integer(0x02000004), itr.next());
    Assert.assertEquals(new Integer(-1), itr.next());
    Assert.assertEquals(itr.hasNext(), false);
  }

  
  public static void testCompactZeroFillZeroLit()
  {
    int[] words = {0x00000004, 0x80000000, -1};

    ImmutableConciseSet res = ImmutableConciseSet.compact(new ImmutableConciseSet(IntBuffer.wrap(words)));
    ImmutableConciseSet.WordIterator itr = res.newWordIterator();

    Assert.assertEquals(new Integer(0x00000005), itr.next());
    Assert.assertEquals(new Integer(-1), itr.next());
    Assert.assertEquals(itr.hasNext(), false);
  }

  
  public static void testCompactZeroFillPureZeroFill()
  {
    int[] words = {0x00000004, 0x00000004, -1};

    ImmutableConciseSet res = ImmutableConciseSet.compact(new ImmutableConciseSet(IntBuffer.wrap(words)));
    ImmutableConciseSet.WordIterator itr = res.newWordIterator();

    Assert.assertEquals(new Integer(0x00000009), itr.next());
    Assert.assertEquals(new Integer(-1), itr.next());
    Assert.assertEquals(itr.hasNext(), false);
  }

  
  public static void testCompactZeroFillDirtyZeroFill()
  {
    int[] words = {0x00000004, 0x02000004, -1};

    ImmutableConciseSet res = ImmutableConciseSet.compact(new ImmutableConciseSet(IntBuffer.wrap(words)));
    ImmutableConciseSet.WordIterator itr = res.newWordIterator();

    Assert.assertEquals(new Integer(0x00000004), itr.next());
    Assert.assertEquals(new Integer(0x02000004), itr.next());
    Assert.assertEquals(new Integer(-1), itr.next());
    Assert.assertEquals(itr.hasNext(), false);
  }

  
  public static void testCompactSingleOneBitLitZeroLit()
  {
    int[] words = {0x80000001, 0x80000000, -1};

    ImmutableConciseSet res = ImmutableConciseSet.compact(new ImmutableConciseSet(IntBuffer.wrap(words)));
    ImmutableConciseSet.WordIterator itr = res.newWordIterator();

    Assert.assertEquals(new Integer(0x02000001), itr.next());
    Assert.assertEquals(new Integer(-1), itr.next());
    Assert.assertEquals(itr.hasNext(), false);
  }

  
  public static void testCompactDoubleOneBitLitZeroLit()
  {
    int[] words = {0x80000003, 0x80000000, -1};

    ImmutableConciseSet res = ImmutableConciseSet.compact(new ImmutableConciseSet(IntBuffer.wrap(words)));
    ImmutableConciseSet.WordIterator itr = res.newWordIterator();

    Assert.assertEquals(new Integer(0x80000003), itr.next());
    Assert.assertEquals(new Integer(0x80000000), itr.next());
    Assert.assertEquals(new Integer(-1), itr.next());
    Assert.assertEquals(itr.hasNext(), false);
  }

  
  public static void testCompactSingleOneBitLitPureZeroFill()
  {
    int[] words = {0x80000001, 0x00000004, -1};

    ImmutableConciseSet res = ImmutableConciseSet.compact(new ImmutableConciseSet(IntBuffer.wrap(words)));
    ImmutableConciseSet.WordIterator itr = res.newWordIterator();

    Assert.assertEquals(new Integer(0x02000005), itr.next());
    Assert.assertEquals(new Integer(-1), itr.next());
    Assert.assertEquals(itr.hasNext(), false);
  }

  
  public static void testCompactDoubleOneBitLitPureZeroFill()
  {
    int[] words = {0x80000003, 0x00000004, -1};

    ImmutableConciseSet res = ImmutableConciseSet.compact(new ImmutableConciseSet(IntBuffer.wrap(words)));
    ImmutableConciseSet.WordIterator itr = res.newWordIterator();

    Assert.assertEquals(new Integer(0x80000003), itr.next());
    Assert.assertEquals(new Integer(0x00000004), itr.next());
    Assert.assertEquals(new Integer(-1), itr.next());
    Assert.assertEquals(itr.hasNext(), false);
  }

  
  public static void testCompactSingleOneBitLitDirtyZeroFill()
  {
    int[] words = {0x80000001, 0x02000004, -1};

    ImmutableConciseSet res = ImmutableConciseSet.compact(new ImmutableConciseSet(IntBuffer.wrap(words)));
    ImmutableConciseSet.WordIterator itr = res.newWordIterator();

    Assert.assertEquals(new Integer(0x80000001), itr.next());
    Assert.assertEquals(new Integer(0x02000004), itr.next());
    Assert.assertEquals(new Integer(-1), itr.next());
    Assert.assertEquals(itr.hasNext(), false);
  }

  
  public static void testCompactSingleZeroBitLitOneLit()
  {
    int[] words = {0xFFFFFFFE, -1};

    ImmutableConciseSet res = ImmutableConciseSet.compact(new ImmutableConciseSet(IntBuffer.wrap(words)));
    ImmutableConciseSet.WordIterator itr = res.newWordIterator();

    Assert.assertEquals(new Integer(0x42000001), itr.next());
    Assert.assertEquals(itr.hasNext(), false);
  }

  
  public static void testCompactDoubleZeroBitLitOneLit()
  {
    int[] words = {0xFFFFFFEE, -1};

    ImmutableConciseSet res = ImmutableConciseSet.compact(new ImmutableConciseSet(IntBuffer.wrap(words)));
    ImmutableConciseSet.WordIterator itr = res.newWordIterator();

    Assert.assertEquals(new Integer(0xFFFFFFEE), itr.next());
    Assert.assertEquals(new Integer(-1), itr.next());
    Assert.assertEquals(itr.hasNext(), false);
  }

  
  public static void testCompactSingleZeroBitLitPureOneFill()
  {
    int[] words = {0xFFFFFFFE, 0x40000004};

    ImmutableConciseSet res = ImmutableConciseSet.compact(new ImmutableConciseSet(IntBuffer.wrap(words)));
    ImmutableConciseSet.WordIterator itr = res.newWordIterator();

    Assert.assertEquals(new Integer(0x42000005), itr.next());
    Assert.assertEquals(itr.hasNext(), false);
  }

  
  public static void testCompactDoubleZeroBitLitPureOneFill()
  {
    int[] words = {0xFFFFFFFC, 0x40000004};

    ImmutableConciseSet res = ImmutableConciseSet.compact(new ImmutableConciseSet(IntBuffer.wrap(words)));
    ImmutableConciseSet.WordIterator itr = res.newWordIterator();

    Assert.assertEquals(new Integer(0xFFFFFFFC), itr.next());
    Assert.assertEquals(new Integer(0x40000004), itr.next());
    Assert.assertEquals(itr.hasNext(), false);
  }

  
  public static void testCompactSingleZeroBitLitDirtyOneFill()
  {
    int[] words = {0xFFFFFFFE, 0x42000004};

    ImmutableConciseSet res = ImmutableConciseSet.compact(new ImmutableConciseSet(IntBuffer.wrap(words)));
    ImmutableConciseSet.WordIterator itr = res.newWordIterator();

    Assert.assertEquals(new Integer(0xFFFFFFFE), itr.next());
    Assert.assertEquals(new Integer(0x42000004), itr.next());
    Assert.assertEquals(itr.hasNext(), false);
  }

  
  public static void testCompactTwoLiterals()
  {
    int[] words = {0xFFFFFFFE, 0xFFEFFEFF};

    ImmutableConciseSet res = ImmutableConciseSet.compact(new ImmutableConciseSet(IntBuffer.wrap(words)));
    ImmutableConciseSet.WordIterator itr = res.newWordIterator();

    Assert.assertEquals(new Integer(0xFFFFFFFE), itr.next());
    Assert.assertEquals(new Integer(0xFFEFFEFF), itr.next());
    Assert.assertEquals(itr.hasNext(), false);
  }


  
  public static void testUnion1()
  {
    final int[] ints1 = {33, 100};
    final int[] ints2 = {24};
    List<Integer> expected = Arrays.asList(33, 24, 100000);

    ConciseSet set1 = new ConciseSet();
    for (int i : ints1) {
      set1.add(i);
    }
    ConciseSet set2 = new ConciseSet();
    for (int i : ints2) {
      set2.add(i);
    }
    
    for(int i = 0;i<31;i++)
    {
    	set1.add(i);
    }
    
    for(int i = 31; i<93;i++)
    {
    	set2.add(i);
    }
    set2.add(101);
    List<ImmutableConciseSet> sets = Arrays.asList(
        ImmutableConciseSet.newImmutableFromMutable(set1),
        ImmutableConciseSet.newImmutableFromMutable(set2)
    );

    verifyUnion(expected, sets);
  }


  
  public static void testUnion2()
  {
    final int[] ints1 = {33, 100000};
    final int[] ints2 = {34, 200000};
    List<Integer> expected = Arrays.asList(33, 34, 100000, 200000);

    ConciseSet set1 = new ConciseSet();
    for (int i : ints1) {
      set1.add(i);
    }
    ConciseSet set2 = new ConciseSet();
    for (int i : ints2) {
      set2.add(i);
    }
    for(int i = 0; i<31 ;i++)
    {
    	set1.add(i);
    }

    ImmutableConciseSet s1 = ImmutableConciseSet.compact(set1);
    ImmutableConciseSet s2 = ImmutableConciseSet.compact(set2);
    List<ImmutableConciseSet> sets = Arrays.asList(
        s1,s2);

    verifyUnion(expected, sets);
  }


  
  public static void testUnion3()
  {
    List<Integer> expected = Lists.newArrayList();
    ConciseSet set1 = new ConciseSet();
    for (int i = 62; i < 10001; i++) {
      set1.add(i);
    }
    ConciseSet set2 = new ConciseSet();
    for (int i = 63; i < 10002; i++) {
      set2.add(i);
    }
    List<ImmutableConciseSet> sets = Arrays.asList(
        ImmutableConciseSet.newImmutableFromMutable(set1),
        ImmutableConciseSet.newImmutableFromMutable(set2)
    );

    for (int i = 62; i < 10002; i++) {
      expected.add(i);
    }

    verifyUnion(expected, sets);
  }

  
  public static void testUnion4()
  {
    List<Integer> expected = Lists.newArrayList();
    ConciseSet set1 = new ConciseSet();
    for (int i = 63; i < 1001; i++) {
      set1.add(i);
    }
    ConciseSet set2 = new ConciseSet();
    for (int i = 64; i < 1002; i++) {
      set2.add(i);
    }
    List<ImmutableConciseSet> sets = Arrays.asList(
        ImmutableConciseSet.newImmutableFromMutable(set1),
        ImmutableConciseSet.newImmutableFromMutable(set2)
    );

    for (int i = 63; i < 1002; i++) {
      expected.add(i);
    }


    ConciseSet blah = new ConciseSet();
    for (int i : expected) {
      blah.add(i);
    }
    verifyUnion(expected, sets);
  }


  
  public static void testUnion5()
  {
    final int[] ints1 = {1, 2, 3, 4, 5};
    final int[] ints2 = {100000, 2405983, 33};
    final int[] ints3 = {0, 4, 5, 34, 333333};
    final List<Integer> expected = Arrays.asList(0, 1, 2, 3, 4, 5, 33, 34, 100000, 333333, 2405983);

    ConciseSet set1 = new ConciseSet();
    for (int i : ints1) {
      set1.add(i);
    }
    ConciseSet set2 = new ConciseSet();
    for (int i : ints2) {
      set2.add(i);
    }
    ConciseSet set3 = new ConciseSet();
    for (int i : ints3) {
      set3.add(i);
    }

    List<ImmutableConciseSet> sets = Arrays.asList(
        ImmutableConciseSet.newImmutableFromMutable(set1),
        ImmutableConciseSet.newImmutableFromMutable(set2),
        ImmutableConciseSet.newImmutableFromMutable(set3)
    );

    verifyUnion(expected, sets);
  }


  
  public static void testUnion6()
  {
    List<Integer> expected = Lists.newArrayList();
    ConciseSet set1 = new ConciseSet();
    for (int i = 0; i < 30; i++) {
      if (i != 28) {
        set1.add(i);
      }
    }
    ConciseSet set2 = new ConciseSet();
    for (int i = 0; i < 30; i++) {
      if (i != 27) {
        set2.add(i);
      }
    }
    List<ImmutableConciseSet> sets = Arrays.asList(
        ImmutableConciseSet.newImmutableFromMutable(set1),
        ImmutableConciseSet.newImmutableFromMutable(set2)
    );

    for (int i = 0; i < 30; i++) {
      expected.add(i);
    }

    verifyUnion(expected, sets);
  }


  
  public static void testUnion7()
  {
    List<Integer> expected = Lists.newArrayList();
    ConciseSet set1 = new ConciseSet();
    for (int i = 64; i < 1005; i++) {
      set1.add(i);
    }
    ConciseSet set2 = new ConciseSet();
    for (int i = 63; i < 99; i++) {
      set2.add(i);
    }
    List<ImmutableConciseSet> sets = Arrays.asList(
        ImmutableConciseSet.newImmutableFromMutable(set1),
        ImmutableConciseSet.newImmutableFromMutable(set2)
    );

    for (int i = 63; i < 1005; i++) {
      expected.add(i);
    }

    verifyUnion(expected, sets);
  }


  
  public static void testUnion8()
  {
    List<Integer> expected = Lists.newArrayList();
    ConciseSet set1 = new ConciseSet();
    for (int i = 0; i < 1000; i++) {
      if (i != 27) {
        set1.add(i);
      }
    }
    ConciseSet set2 = new ConciseSet();
    for (int i = 0; i < 1000; i++) {
      if (i != 28) {
        set2.add(i);
      }
    }
    List<ImmutableConciseSet> sets = Arrays.asList(
        ImmutableConciseSet.newImmutableFromMutable(set1),
        ImmutableConciseSet.newImmutableFromMutable(set2)
    );

    for (int i = 0; i < 1000; i++) {
      expected.add(i);
    }

    verifyUnion(expected, sets);
  }


  public static void testUnion9()
  {
    List<Integer> expected = Lists.newArrayList();
    ConciseSet set1 = new ConciseSet();
    for (int i = 0; i < 1000; i++) {
      if (!(i == 27 || i == 28)) {
        set1.add(i);
      }
    }
    ConciseSet set2 = new ConciseSet();
    for (int i = 0; i < 1000; i++) {
      if (i != 28) {
        set2.add(i);
      }
    }
    List<ImmutableConciseSet> sets = Arrays.asList(
        ImmutableConciseSet.newImmutableFromMutable(set1),
        ImmutableConciseSet.newImmutableFromMutable(set2)
    );

    for (int i = 0; i < 1000; i++) {
      if (i != 28) {
        expected.add(i);
      }
    }

    verifyUnion(expected, sets);
  }


  public static void testUnion10()
  {
    List<Integer> expected = Lists.newArrayList();
    ConciseSet set1 = new ConciseSet();
    for (int i = 0; i < 1000; i += 2) {
      set1.add(i);
    }
    ConciseSet set2 = new ConciseSet();
    for (int i = 1; i < 1000; i += 2) {
      set2.add(i);
    }
    List<ImmutableConciseSet> sets = Arrays.asList(
        ImmutableConciseSet.newImmutableFromMutable(set1),
        ImmutableConciseSet.newImmutableFromMutable(set2)
    );

    for (int i = 0; i < 1000; i++) {
      expected.add(i);
    }

    verifyUnion(expected, sets);
  }


  
  public static void testUnion11()
  {
    List<Integer> expected = Lists.newArrayList();
    ConciseSet set1 = new ConciseSet();
    for (int i = 0; i < 1000; i += 2) {
      set1.add(i);
    }
    ConciseSet set2 = new ConciseSet();
    set2.add(10000);

    List<ImmutableConciseSet> sets = Arrays.asList(
        ImmutableConciseSet.newImmutableFromMutable(set1),
        ImmutableConciseSet.newImmutableFromMutable(set2)
    );

    for (int i = 0; i < 1000; i += 2) {
      expected.add(i);
    }
    expected.add(10000);

    verifyUnion(expected, sets);
  }


  
  public static void testUnion12()
  {
    final int[] ints1 = {1, 2, 3, 4};
    final int[] ints2 = {5, 1000};
    final List<Integer> expected = Arrays.asList(1, 2, 3, 4, 5, 1000);

    ConciseSet set1 = new ConciseSet();
    for (int i : ints1) {
      set1.add(i);
    }
    ConciseSet set2 = new ConciseSet();
    for (int i : ints2) {
      set2.add(i);
    }

    List<ImmutableConciseSet> sets = Arrays.asList(
        ImmutableConciseSet.newImmutableFromMutable(set1),
        ImmutableConciseSet.newImmutableFromMutable(set2)
    );

    verifyUnion(expected, sets);
  }

  public static void testUnion13()
  {
    List<Integer> expected = Lists.newArrayList();
    final int[] ints1 = {0};

    ConciseSet set1 = new ConciseSet();
    for (int i : ints1) {
      set1.add(i);
    }
    ConciseSet set2 = new ConciseSet();
    for (int i = 1; i < 100; i++) {
      set2.add(i);
    }

    List<ImmutableConciseSet> sets = Arrays.asList(
        ImmutableConciseSet.newImmutableFromMutable(set1),
        ImmutableConciseSet.newImmutableFromMutable(set2)
    );

    for (int i = 0; i < 100; i++) {
      expected.add(i);
    }

    verifyUnion(expected, sets);
  }


  
  public static void testUnion14()
  {
    List<Integer> expected = Lists.newArrayList();
    final int[] ints1 = {0};

    ConciseSet set1 = new ConciseSet();
    for (int i : ints1) {
      set1.add(i);
    }
    ConciseSet set2 = new ConciseSet();
    for (int i = 1; i < 100; i++) {
      set2.add(i);
    }

    List<ImmutableConciseSet> sets = Arrays.asList(
        ImmutableConciseSet.newImmutableFromMutable(set1),
        ImmutableConciseSet.newImmutableFromMutable(set2)
    );

    for (int i = 0; i <= 100; i++) {
      expected.add(i);
    }

    verifyUnion(expected, sets);
  }


  
  public static void testUnion15()
  {
    List<Integer> expected = Lists.newArrayList();
    final int[] ints1 = {1, 100};
    final int[] ints2 = {0};

    ConciseSet set1 = new ConciseSet();
    for (int i : ints1) {
      set1.add(i);
    }
    ConciseSet set2 = new ConciseSet();
    for (int i : ints2) {
      set2.add(i);
    }
    ConciseSet set3 = new ConciseSet();
    for (int i = 1; i < 100; i++) {
      set3.add(i);
    }

    List<ImmutableConciseSet> sets = Arrays.asList(
        ImmutableConciseSet.newImmutableFromMutable(set1),
        ImmutableConciseSet.newImmutableFromMutable(set2),
        ImmutableConciseSet.newImmutableFromMutable(set3)
    );

    for (int i = 0; i <= 100; i++) {
      expected.add(i);
    }

    verifyUnion(expected, sets);
  }


  
  public static void testUnion16()
  {
    final int[] ints1 = {1001, 1002, 1003};
    final int[] ints2 = {1034, 1035, 1036};
    List expected = Arrays.asList(1001, 1002, 1003, 1034, 1035, 1036);

    ConciseSet set1 = new ConciseSet();
    for (int i : ints1) {
      set1.add(i);
    }
    ConciseSet set2 = new ConciseSet();
    for (int i : ints2) {
      set2.add(i);
    }

    List<ImmutableConciseSet> sets = Arrays.asList(
        ImmutableConciseSet.newImmutableFromMutable(set1),
        ImmutableConciseSet.newImmutableFromMutable(set2)
    );

    verifyUnion(expected, sets);
  }

 
  
  public static void testUnion17()
  {
    final int[] ints1 = {1, 2, 3, 4, 5};
    final int[] ints2 = {1, 2, 3, 4, 5};
    List expected = Arrays.asList(1, 2, 3, 4, 5);

    ConciseSet set1 = new ConciseSet();
    for (int i : ints1) {
      set1.add(i);
    }
    ConciseSet set2 = new ConciseSet();
    for (int i : ints2) {
      set2.add(i);
    }

    List<ImmutableConciseSet> sets = Arrays.asList(
        ImmutableConciseSet.newImmutableFromMutable(set1),
        ImmutableConciseSet.newImmutableFromMutable(set2)
    );

    verifyUnion(expected, sets);
  }

  
  public static void testUnion18()
  {
    List<Integer> expected = Lists.newArrayList();
    ConciseSet set1 = new ConciseSet();
    for (int i = 0; i < 1000; i++) {
      set1.add(i);
    }
    ConciseSet set2 = new ConciseSet();
    set2.add(1000);
    set2.add(10000);

    List<ImmutableConciseSet> sets = Arrays.asList(
        ImmutableConciseSet.newImmutableFromMutable(set1),
        ImmutableConciseSet.newImmutableFromMutable(set2)
    );

    for (int i = 0; i < 1001; i++) {
      expected.add(i);
    }
    expected.add(10000);

    verifyUnion(expected, sets);
  }

  
  public static void testUnion19()
  {
    List<Integer> expected = Lists.newArrayList();
    ConciseSet set1 = new ConciseSet();
    for (int i = 0; i < 93; i++) {
      set1.add(i);
    }
    ConciseSet set2 = new ConciseSet();
    for (int i = 62; i < 1000; i++) {
      set2.add(i);
    }

    List<ImmutableConciseSet> sets = Arrays.asList(
        ImmutableConciseSet.newImmutableFromMutable(set1),
        ImmutableConciseSet.newImmutableFromMutable(set2)
    );

    for (int i = 0; i < 1000; i++) {
      expected.add(i);
    }

    verifyUnion(expected, sets);
  }

  
  public static void testUnion20()
  {
    List<Integer> expected = Lists.newArrayList();
    ConciseSet set1 = new ConciseSet();
    for (int i = 0; i < 5; i++) {
      set1.add(i);
    }
    for (int i = 31; i < 1000; i++) {
      set1.add(i);
    }

    ConciseSet set2 = new ConciseSet();
    for (int i = 62; i < 68; i++) {
      set2.add(i);
    }
    for (int i = 800; i < 1000; i++) {
      set2.add(i);
    }

    List<ImmutableConciseSet> sets = Arrays.asList(
        ImmutableConciseSet.newImmutableFromMutable(set1),
        ImmutableConciseSet.newImmutableFromMutable(set2)
    );

    for (int i = 0; i < 5; i++) {
      expected.add(i);
    }
    for (int i = 31; i < 1000; i++) {
      expected.add(i);
    }

    verifyUnion(expected, sets);
  }

  
  public static void testUnion21()
  {
    ConciseSet set1 = new ConciseSet();
    for (int i = 32; i < 93; i++) {
      set1.add(i);
    }

    ConciseSet set2 = new ConciseSet();
    for (int i = 0; i < 62; i++) {
      set2.add(i);
    }

    List<ImmutableConciseSet> sets = Arrays.asList(
        ImmutableConciseSet.newImmutableFromMutable(set1),
        ImmutableConciseSet.newImmutableFromMutable(set2)
    );

    List<Integer> expected = Lists.newArrayList();
    for (int i = 0; i < 93; i++) {
      expected.add(i);
    }

    verifyUnion(expected, sets);
  }

  
  public static void testUnion22()
  {
    ConciseSet set1 = new ConciseSet();
    for (int i = 93; i < 1000; i++) {
      set1.add(i);
    }

    ConciseSet set2 = new ConciseSet();
    for (int i = 0; i < 32; i++) {
      set2.add(i);
    }

    List<ImmutableConciseSet> sets = Arrays.asList(
        ImmutableConciseSet.newImmutableFromMutable(set1),
        ImmutableConciseSet.newImmutableFromMutable(set2)
    );

    List<Integer> expected = Lists.newArrayList();
    for (int i = 0; i < 32; i++) {
      expected.add(i);
    }
    for (int i = 93; i < 1000; i++) {
      expected.add(i);
    }

    verifyUnion(expected, sets);
  }

  private static void verifyUnion(List<Integer> expected, List<ImmutableConciseSet> sets)
  {
    List<Integer> actual = Lists.newArrayList();
    ImmutableConciseSet set = ImmutableConciseSet.union(sets);
    IntSet.IntIterator itr = set.iterator();
    String str = set.toString();
    //int size = set.calcSize();
    while (itr.hasNext()) {
      actual.add(itr.next());
    }
    Assert.assertEquals(expected, actual);
  }

  
  public static void testIntersection1()
  {
	    List<Integer> expected = Arrays.asList(33, 24, 100000);
	    ConciseSet set1 = new ConciseSet();
	    ConciseSet set2 = new ConciseSet();
	    for(int i =0;i<93;i++)
	    {
	    	set1.add(i);
	    }
	    set1.add(100);
	    set1.add(200);
	    
	    set2.add(20);
	    set2.add(100);
    List<ImmutableConciseSet> sets = Arrays.asList(
        ImmutableConciseSet.newImmutableFromMutable(set1),
        ImmutableConciseSet.newImmutableFromMutable(set2)
    );

    verifyIntersection(expected, sets);
  }

  
  public static void testIntersection2()
  {
    final int[] ints1 = {33, 100000};
    final int[] ints2 = {34, 100000};
    List<Integer> expected = Arrays.asList(100000);

    ConciseSet set1 = new ConciseSet();
    for (int i : ints1) {
      set1.add(i);
    }
    ConciseSet set2 = new ConciseSet();
    for (int i : ints2) {
      set2.add(i);
    }
    ImmutableConciseSet set_1 = ImmutableConciseSet.newImmutableFromMutable(set1);
    ImmutableConciseSet set_2 = ImmutableConciseSet.newImmutableFromMutable(set2);
    ImmutableConciseSet s1 = ImmutableConciseSet.compact(set_1);
    ImmutableConciseSet s2 = ImmutableConciseSet.compact(set_2);
    List<ImmutableConciseSet> sets = Arrays.asList(
        s1,s2
    );
    	
    verifyIntersection(expected, sets);
  }

  
  public static void testIntersection3()
  {
    List<Integer> expected = Lists.newArrayList();
    ConciseSet set1 = new ConciseSet();
    ConciseSet set2 = new ConciseSet();
    for (int i = 0; i < 1000; i++) {
      set1.add(i);
      set2.add(i);
      expected.add(i);
    }

    List<ImmutableConciseSet> sets = Arrays.asList(
        ImmutableConciseSet.newImmutableFromMutable(set1),
        ImmutableConciseSet.newImmutableFromMutable(set2)
    );

    verifyIntersection(expected, sets);
  }

  
  public static void testIntersection4()
  {
    List<Integer> expected = Lists.newArrayList();
    ConciseSet set1 = new ConciseSet();
    ConciseSet set2 = new ConciseSet();
    for (int i = 0; i < 1000; i++) {
      set1.add(i);
      if (i != 500) {
        set2.add(i);
        expected.add(i);
      }
    }

    List<ImmutableConciseSet> sets = Arrays.asList(
        ImmutableConciseSet.newImmutableFromMutable(set1),
        ImmutableConciseSet.newImmutableFromMutable(set2)
    );

    verifyIntersection(expected, sets);
  }


  public static void testIntersection5()
  {
    final int[] ints1 = {33, 100000};
    final int[] ints2 = {34, 200000};
    List<Integer> expected = Lists.newArrayList();

    ConciseSet set1 = new ConciseSet();
    for (int i : ints1) {
      set1.add(i);
    }
    ConciseSet set2 = new ConciseSet();
    for (int i : ints2) {
      set2.add(i);
    }
    List<ImmutableConciseSet> sets = Arrays.asList(
        ImmutableConciseSet.newImmutableFromMutable(set1),
        ImmutableConciseSet.newImmutableFromMutable(set2)
    );

    verifyIntersection(expected, sets);
  }


  
  public static void testIntersection6()
  {
    List<Integer> expected = Lists.newArrayList();
    ConciseSet set1 = new ConciseSet();
    for (int i = 0; i < 5; i++) {
      set1.add(i);
    }
    for (int i = 1000; i < 1005; i++) {
      set1.add(i);
    }

    ConciseSet set2 = new ConciseSet();
    for (int i = 800; i < 805; i++) {
      set2.add(i);
    }
    for (int i = 806; i < 1005; i++) {
      set2.add(i);
    }

    List<ImmutableConciseSet> sets = Arrays.asList(
        ImmutableConciseSet.newImmutableFromMutable(set1),
        ImmutableConciseSet.newImmutableFromMutable(set2)
    );

    for (int i = 1000; i < 1005; i++) {
      expected.add(i);
    }

    verifyIntersection(expected, sets);
  }

  
  public static void testIntersection7()
  {
    ConciseSet set1 = new ConciseSet();
    for (int i = 0; i < 3100; i++) {
      set1.add(i);
    }

    ConciseSet set2 = new ConciseSet();
    set2.add(100);
    set2.add(500);
    for (int i = 600; i < 700; i++) {
      set2.add(i);
    }

    List<ImmutableConciseSet> sets = Arrays.asList(
        ImmutableConciseSet.newImmutableFromMutable(set1),
        ImmutableConciseSet.newImmutableFromMutable(set2)
    );

    List<Integer> expected = Lists.newArrayList();
    expected.add(100);
    expected.add(500);
    for (int i = 600; i < 700; i++) {
      expected.add(i);
    }

    verifyIntersection(expected, sets);
  }

  
  public static void testIntersection8()
  {
    ConciseSet set1 = new ConciseSet();
    for (int i = 0; i < 3100; i++) {
      set1.add(i);
    }
    set1.add(4001);

    ConciseSet set2 = new ConciseSet();
    set2.add(100);
    set2.add(500);
    for (int i = 600; i < 700; i++) {
      set2.add(i);
    }
    set2.add(4001);

    List<ImmutableConciseSet> sets = Arrays.asList(
        ImmutableConciseSet.newImmutableFromMutable(set1),
        ImmutableConciseSet.newImmutableFromMutable(set2)
    );

    List<Integer> expected = Lists.newArrayList();
    expected.add(100);
    expected.add(500);
    for (int i = 600; i < 700; i++) {
      expected.add(i);
    }
    expected.add(4001);

    verifyIntersection(expected, sets);
  }

  
  public static void testIntersection9()
  {
    ConciseSet set1 = new ConciseSet();
    set1.add(2005);
    set1.add(3005);
    set1.add(3008);

    ConciseSet set2 = new ConciseSet();
    for (int i = 0; i < 3007; i++) {
      set2.add(i);
    }

    List<ImmutableConciseSet> sets = Arrays.asList(
        ImmutableConciseSet.newImmutableFromMutable(set1),
        ImmutableConciseSet.newImmutableFromMutable(set2)
    );

    List<Integer> expected = Lists.newArrayList();
    expected.add(2005);
    expected.add(3005);

    verifyIntersection(expected, sets);
  }

  
  public static void testIntersection10()
  {
    ConciseSet set1 = new ConciseSet();
    for (int i = 0; i < 3100; i++) {
      set1.add(i);
    }

    ConciseSet set2 = new ConciseSet();

    set2.add(500);
    set2.add(600);
    set2.add(4001);

    List<ImmutableConciseSet> sets = Arrays.asList(
        ImmutableConciseSet.newImmutableFromMutable(set1),
        ImmutableConciseSet.newImmutableFromMutable(set2)
    );

    List<Integer> expected = Lists.newArrayList();
    expected.add(500);
    expected.add(600);

    verifyIntersection(expected, sets);
  }

  
  public static void testIntersection11()
  {
    ConciseSet set1 = new ConciseSet();
    set1.add(2005);
    for (int i = 2800; i < 3500; i++) {
      set1.add(i);
    }

    ConciseSet set2 = new ConciseSet();
    for (int i = 0; i < 3007; i++) {
      set2.add(i);
    }

    List<ImmutableConciseSet> sets = Arrays.asList(
        ImmutableConciseSet.newImmutableFromMutable(set1),
        ImmutableConciseSet.newImmutableFromMutable(set2)
    );

    List<Integer> expected = Lists.newArrayList();
    expected.add(2005);
    for (int i = 2800; i < 3007; i++) {
      expected.add(i);
    }

    verifyIntersection(expected, sets);
  }

  
  public static void testIntersection12()
  {
    ConciseSet set1 = new ConciseSet();
    set1.add(2005);
    for (int i = 2800; i < 3500; i++) {
      set1.add(i);
    }
    set1.add(10005);

    ConciseSet set2 = new ConciseSet();
    for (int i = 0; i < 3007; i++) {
      set2.add(i);
    }
    set2.add(10005);

    List<ImmutableConciseSet> sets = Arrays.asList(
        ImmutableConciseSet.newImmutableFromMutable(set1),
        ImmutableConciseSet.newImmutableFromMutable(set2)
    );

    List<Integer> expected = Lists.newArrayList();
    expected.add(2005);
    for (int i = 2800; i < 3007; i++) {
      expected.add(i);
    }
    expected.add(10005);

    verifyIntersection(expected, sets);
  }

  
  public static void testIntersection13()
  {
    ConciseSet set1 = new ConciseSet();
    set1.add(2005);

    ConciseSet set2 = new ConciseSet();
    for (int i = 0; i < 100; i++) {
      set2.add(i);
    }

    List<ImmutableConciseSet> sets = Arrays.asList(
        ImmutableConciseSet.newImmutableFromMutable(set1),
        ImmutableConciseSet.newImmutableFromMutable(set2)
    );

    List<Integer> expected = Lists.newArrayList();

    verifyIntersection(expected, sets);
  }

  
  public static void testIntersection14()
  {
    ConciseSet set1 = new ConciseSet();
    for (int i = 0; i < 1000; i++) {
      set1.add(i);
    }

    ConciseSet set2 = new ConciseSet();
    set2.add(0);
    set2.add(3);
    set2.add(5);
    set2.add(100);
    set2.add(101);

    List<ImmutableConciseSet> sets = Arrays.asList(
        ImmutableConciseSet.newImmutableFromMutable(set1),
        ImmutableConciseSet.newImmutableFromMutable(set2)
    );

    List<Integer> expected = Lists.newArrayList();
    expected.add(0);
    expected.add(3);
    expected.add(5);
    expected.add(100);
    expected.add(101);

    verifyIntersection(expected, sets);
  }

  
  public static void testIntersection15()
  {
    ConciseSet set1 = new ConciseSet();
    for (int i = 0; i < 1000; i++) {
      set1.add(i);
    }

    ConciseSet set2 = new ConciseSet();
    set2.add(0);
    set2.add(3);
    set2.add(5);
    for (int i = 100; i < 500; i++) {
      set2.add(i);
    }

    List<ImmutableConciseSet> sets = Arrays.asList(
        ImmutableConciseSet.newImmutableFromMutable(set1),
        ImmutableConciseSet.newImmutableFromMutable(set2)
    );

    List<Integer> expected = Lists.newArrayList();
    expected.add(0);
    expected.add(3);
    expected.add(5);
    for (int i = 100; i < 500; i++) {
      expected.add(i);
    }

    verifyIntersection(expected, sets);
  }

  
  public static void testIntersection16()
  {
    ConciseSet set1 = new ConciseSet();
    set1.add(2005);

    ConciseSet set2 = new ConciseSet();
    set2.add(0);
    set2.add(3);
    set2.add(5);
    set2.add(100);
    set2.add(101);

    List<ImmutableConciseSet> sets = Arrays.asList(
        ImmutableConciseSet.newImmutableFromMutable(set1),
        ImmutableConciseSet.newImmutableFromMutable(set2)
    );

    List<Integer> expected = Lists.newArrayList();

    verifyIntersection(expected, sets);
  }

  
  public static void testIntersection17()
  {
    ConciseSet set1 = new ConciseSet();
    for (int i = 0; i < 4002; i++) {
      set1.add(i);
    }

    ConciseSet set2 = new ConciseSet();
    set2.add(4001);

    List<ImmutableConciseSet> sets = Arrays.asList(
        ImmutableConciseSet.newImmutableFromMutable(set1),
        ImmutableConciseSet.newImmutableFromMutable(set2)
    );

    List<Integer> expected = Lists.newArrayList();
    expected.add(4001);

    verifyIntersection(expected, sets);
  }

  
  public static void testIntersection18()
  {
    ConciseSet set1 = new ConciseSet();
    for (int i = 32; i < 93; i++) {
      set1.add(i);
    }

    ConciseSet set2 = new ConciseSet();
    for (int i = 0; i < 62; i++) {
      set2.add(i);
    }

    List<ImmutableConciseSet> sets = Arrays.asList(
        ImmutableConciseSet.newImmutableFromMutable(set1),
        ImmutableConciseSet.newImmutableFromMutable(set2)
    );

    List<Integer> expected = Lists.newArrayList();
    for (int i = 32; i < 62; i++) {
      expected.add(i);
    }

    verifyIntersection(expected, sets);
  }

  
  public static void testIntersection19()
  {
    ConciseSet set1 = new ConciseSet();
    set1.add(2005);

    ConciseSet set2 = new ConciseSet();
    for (int i = 0; i < 10000; i++) {
      set2.add(i);
    }

    List<ImmutableConciseSet> sets = Arrays.asList(
        ImmutableConciseSet.newImmutableFromMutable(set1),
        ImmutableConciseSet.newImmutableFromMutable(set2)
    );

    List<Integer> expected = Lists.newArrayList();
    expected.add(2005);

    verifyIntersection(expected, sets);
  }

  
  public static void testIntersectionTerminates() throws Exception
  {
    verifyIntersection(Arrays.<Integer>asList(), Arrays.asList(new ImmutableConciseSet(), new ImmutableConciseSet()));
  }

  private static void verifyIntersection(List<Integer> expected, List<ImmutableConciseSet> sets)
  {
    List<Integer> actual = Lists.newArrayList();
    ImmutableConciseSet set = ImmutableConciseSet.intersection(sets);
    IntSet.IntIterator itr = set.iterator();
    String str = itr.toString();
    while (itr.hasNext()) {
      actual.add(itr.next());
    }
    Assert.assertEquals(expected, actual);
  }

  
  public static void testComplement1()
  {
    final int[] ints = {1, 100};
    List<Integer> expected = Lists.newArrayList();

    ConciseSet set = new ConciseSet();
    for (int i : ints) {
      set.add(i);
    }

    for (int i = 0; i <= 100; i++) {
      if (i != 1 && i != 100) {
        expected.add(i);
      }
    }

    ImmutableConciseSet testSet = ImmutableConciseSet.newImmutableFromMutable(set);

    verifyComplement(expected, testSet, NO_COMPLEMENT_LENGTH);
  }

  
  public static void testComplement2()
  {
    List<Integer> expected = Lists.newArrayList();

    ConciseSet set = new ConciseSet();
    for (int i = 0; i < 15; i++) {
      set.add(i);
    }

    ImmutableConciseSet testSet = ImmutableConciseSet.newImmutableFromMutable(set);

    verifyComplement(expected, testSet, NO_COMPLEMENT_LENGTH);
  }

  
  public static void testComplement3()
  {
    List<Integer> expected = Lists.newArrayList();
    final int length = 21;

    ConciseSet set = new ConciseSet();
    for (int i = 0; i < 15; i++) {
      set.add(i);
    }
    for (int i = 15; i < length; i++) {
      expected.add(i);
    }

    ImmutableConciseSet testSet = ImmutableConciseSet.newImmutableFromMutable(set);

    verifyComplement(expected, testSet, length);
  }

  public static void testComplement4()
  {
    List<Integer> expected = Lists.newArrayList();
    final int length = 41;

    ConciseSet set = new ConciseSet();
    for (int i = 0; i < 15; i++) {
      set.add(i);
    }
    for (int i = 15; i < length; i++) {
      expected.add(i);
    }

    ImmutableConciseSet testSet = ImmutableConciseSet.newImmutableFromMutable(set);

    verifyComplement(expected, testSet, length);
  }

  
  public static void testComplement5()
  {
    List<Integer> expected = Lists.newArrayList();
    final int length = 1001;

    ConciseSet set = new ConciseSet();
    for (int i = 0; i < 15; i++) {
      set.add(i);
    }
    for (int i = 15; i < length; i++) {
      expected.add(i);
    }

    ImmutableConciseSet testSet = ImmutableConciseSet.newImmutableFromMutable(set);

    verifyComplement(expected, testSet, length);
  }

  public static void testComplement6()
  {
    List<Integer> expected = Lists.newArrayList();
    final int length = 1001;

    ConciseSet set = new ConciseSet();
    for (int i = 65; i <= 100; i++) {
      set.add(i);
    }
    for (int i = 0; i < length; i++) {
      if (i < 65 || i > 100) {
        expected.add(i);
      }
    }

    ImmutableConciseSet testSet = ImmutableConciseSet.newImmutableFromMutable(set);

    verifyComplement(expected, testSet, length);
  }

  public static void testComplement7()
  {
    List<Integer> expected = Lists.newArrayList();
    final int length = 37;

    ConciseSet set = new ConciseSet();
    for (int i = 0; i <= 35; i++) {
      set.add(i);
    }
    expected.add(36);

    ImmutableConciseSet testSet = ImmutableConciseSet.newImmutableFromMutable(set);

    verifyComplement(expected, testSet, length);
  }


  public static void testComplement8()
  {
    List<Integer> expected = Lists.newArrayList();
    final int length = 32;

    ConciseSet set = new ConciseSet();
    for (int i = 0; i <= 30; i++) {
      set.add(i);
    }
    expected.add(31);

    ImmutableConciseSet testSet = ImmutableConciseSet.newImmutableFromMutable(set);

    verifyComplement(expected, testSet, length);
  }


  public static void testComplement9()
  {
    List<Integer> expected = Lists.newArrayList();
    final int length = 35;

    for (int i = 0; i < length; i++) {
      expected.add(i);
    }

    ImmutableConciseSet testSet = new ImmutableConciseSet();

    verifyComplement(expected, testSet, length);
  }

  
  public static void testComplement10()
  {
    List<Integer> expected = Lists.newArrayList();
    final int length = 93;

    for (int i = 0; i < length; i++) {
      expected.add(i);
    }

    ImmutableConciseSet testSet = new ImmutableConciseSet();

    verifyComplement(expected, testSet, length);
  }


  
  public static void testComplement11()
  {
    List<Integer> expected = Lists.newArrayList();
    int length = 18930;
    for (int i = 0; i < 500; i++) {
      expected.add(i);
    }
    for (int i = 18881; i < length; i++) {
      expected.add(i);
    }

    ConciseSet set = new ConciseSet();
    for (int i = 500; i <= 18880; i++) {
      set.add(i);
    }
    ImmutableConciseSet testSet = ImmutableConciseSet.newImmutableFromMutable(set);

    verifyComplement(expected, testSet, length);
  }

  public static void testComplement12()
  {
    List<Integer> expected = Lists.newArrayList();
    int length = 10;
    for (int i = 0; i < 10; i++) {
      expected.add(i);
    }

    ImmutableConciseSet testSet = new ImmutableConciseSet();

    verifyComplement(expected, testSet, length);
  }

  public static void testComplement13()
  {
    List<Integer> expected = Lists.newArrayList();
    int length = 10;
    for (int i = 0; i < length; i++) {
      expected.add(i);
    }
    ImmutableConciseSet testSet = new ImmutableConciseSet();

    verifyComplement(expected, testSet, length);
  }

  private static void verifyComplement(List<Integer> expected, ImmutableConciseSet set, int endIndex)
  {
    List<Integer> actual = Lists.newArrayList();

    ImmutableConciseSet res;
    if (endIndex == NO_COMPLEMENT_LENGTH) {
      res = ImmutableConciseSet.complement(set);
    } else {
      res = ImmutableConciseSet.complement(set, endIndex);
    }

    IntSet.IntIterator itr = res.iterator();
    while (itr.hasNext()) {
      actual.add(itr.next());
    }
    Assert.assertEquals(expected, actual);
  }
}
