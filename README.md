# Algorithms-princeton
Follows progress of course: 
[Algorithms, Part I](https://www.coursera.org/learn/algorithms-part1)  by Princeton University.
<br>Instructors: Robert Sedgewick, Kevin Wayne 

-----

#### Union-Find
* Addresses dynamic connectivity problem
* **Implementation**: Parent-link representation of tree using an array
* Improvements: 
    * **Weighted Union-Find** (Link smaller tree to larger tree on Union)
    * **Path compression** (Decrease tree height whenever possible)
* Possible applications: Percolation, connectivity problems

#### Stacks and Queues
* Implemented custom **Iterator**, need for **Generics**
* **Resizing array** implementation:
    * Constant amortized time
    * Worst case, could end up resizing at a critical time 
* **Linked list** implementation: 
    * Operations take constant time but links take extra space and time
    * Less wasted apace
* **Randomised Queue** implemented
* **Reservoir sampling**: Sample a large dataset with limited available space

#### Elementary sorts
* **Selection sort**
    * At most n exchanges; can be used when exchanges are expensive
* **Insertion sort**
    * Exchanges on almost every compare
    * Almost like bubble sort
* **Shell sort**
    * h-sort; insertion sort, but with greater index difference
    * Time and space complexities are not definite
* **Knuth shuffle**
    * Importance of uniform randomness
    * Subtle bugs and correctness analysis

#### MergeSort, QuickSort
* **Merge sort**
    * Recursively sort small arrays and merge them into larger sorted arrays
    * Needs an extra auxiliary array of size n
    * Used by Java lib on Objects
    * Guarantees linearitmetic performance in the worst case
    * **Improvements**: 
        <br>&#x25B8; Insertion sort for smaller subarrays 
        <br>&#x25B8; Check if already sorted(end and start of sub-arrays)
* **Quick sort**
    * Recursively sort using a partitioning element
    * In-place sort
    * Used by Java lib on Primitive data types 
    * **Improvements**: 
        <br>&#x25B8; Insertion sort for smaller subarrays 
        <br>&#x25B8; Median of 3 (ninthers) for optimal partitioning element
* **Find Collinear points** given a set of points: Sort by slope, and count
* **Dijkstra's 3 way partitioning**: Quicksort mod for inputs with duplicate keys

#### Priority Queues (min-max heap)
* Methods needed for a MaxPQ implementation:
    * `insert()`
    * `max()`
    * `delMax()`
    * `isEmpty()`
    * `size()`
* Ordered based on priority, structurally a tree in an array
* Removal of max/min from the heap is constant time
* For **min-heap**, **each parent is less than it's children**, and vice versa
* **Completely balanced tree**: Has all values filled except for bottom level
* When starting entries from index 1:
    * Parent(`n`) = `n/2`
    * Children(`n`) = `2n`, `2n + 1`
* **Some applications**: 
    * A* algorithm, like Game trees 
    * Maintaining **top x entries from a large dataset**, for eg., by Timestamp
    * Data compression, Huffman codes (todo)
    * Dijkstra's shortest path (todo with graphs)
* **Operations**: 
    * `sink`, dropping a value down the tree to correct position
    * `swim`, raising a value up the tree to correct position
* **HeapSort**:
    * In-place sort with `nlog(n)` worst case
    * Operations:
        * **Heap construction**: at most `2n` compares and exchanges (mathematical proof)
        * **Heap sort**: at most `2nlog(n)` compares and exchanges
    * Disadvantages:
        * Inner loop is longer than QuickSort's
        * **Poor use of cache**, since references are all over the place
        * Unstable sort
        
        
#### Sorts recap

| x | In-Place | Stable | Worst | Avg | Best | Remarks |
|-----|:---:|:---:|:---:|:---:|:---:|---|
|Selection| Y   | N | `n*n/2` | `n*n/2` | `n*n/2` | n exchanges |
|Insertion| Y   | Y | `n*n/2` | `n*n/4` | `n` | use for partially sorted |
|Shell| Y | Y | `?` | `?` | `n` | subquadratic |
|Merge| N       | Y | `nlog(n)` | `nlog(n)` | `nlog(n)` | extra space |
|Quick| Y       | N | `n*n` | `nlog(n)` | `nlog(n)` | probabilistically fastest |
|3-way Quick| Y | N | `n*n` | `nlog(n)` | `n` | quickest with duplicates |
|HeapSort| Y | N | `nlog(n)` | `nlog(n)` | `nlog(n)` | poor caching |

#### Symbol Tables
* Data structure **for key-value pairs**, where **keys are unique**
* Cases where lookups need to happen quickly
* General: **Conditions for Equality**, for `.equals(Obj)`
    * Reflexive: `x.equals(x)` is `true`
    * Symmetric: if `x.equals(y)` then `y.equals(x)`
    * Transitive: if `x.equals(y)` and `y.equals(z)`, `x.equals(z)`
    * (Java specific): `x.equals(null)` is `false`
* **Binary Search Trees**
    * **Invariant for each node**: 
        * Subtrees to the left are smaller
        * Subtrees to the right are larger
    * General operations:
        * `find(key)` : Look left if smaller than current, right if bigger
        * `put(key)` : If key already present, change value. Else insert
    * When maintained in order, more operations can be performed efficiently
        * `floor(key)`, `ceil(key)`
        * `rank(key)` : Number of keys smaller than `key` == `size(left) + rank(right)`
        * `select(n)` : `n`th largest key
    * **Tree shape depends on insertion order**
    * In-order traversal: `inorder(left)`, `enqueue(key)`, `inorder(right)`
    * `delete(key)` : Hibbard deletion
        * Replace node with minimum of node's right subtree
        * Thus, the replacement is:
            * More than left child(since it's taken from right) 
            * Less than right child(since it's right's minimum)
        * **Eventually unbalances tree**; `h` becomes `sqrt(n)` instead of `log(n)`
*   **2-3 Trees**
    * Allows 1 or 2 keys per node, and max 3 children
        * 2-node: 1 key, 2 nodes
            * `Node left` < `key a` < `Node left`
        * 3-node: 2 keys, 3 nodes
            * `Node left` < `key a` < `Node middle` < `key b` < `Node right` 
    * Perfectly balanced: Every path from root to null link has the same length
    * Adding keys, general behaviour:
        * Add `b` to `ac`: 
            * Becomes `abc` 
            * `abc` splits; middle `b` becomes parent, `a` and `c` children
        * Add `b` to `ac` with parent `d`: 
            * Becomes `abc` with parent `d`
            * `abc` splits; middle `b` joins `d`; `bd` becomes parent, `a` and `c` children
*   **Red-Black Trees**
    * Red links are internal links. <br>For a 3-node `ab`, link between
      `a` and `b` is the red link.
    * Red links are left leaning
    * Basic internal operations: 
        * `rotateLeft` : Insertion, when new `Node` ends up on the right of a 3-node,
          <br>the link is a red link. Red links should be left leaning; this, rotate.
        * `rotateRight` : When left and left.left are red
        * `flipColor` : Used to split 4-node into 3-node
    * Applications: File systems, memory, SQL, Java's TreeMap, TreeSet
*   **Kd Trees**

#### Symbol Tables recap

|Time, worst and avg|Search(worst)|Insert(worst)|Delete(worst)|Search(avg)|Insert(avg)|Delete(avg)|Remarks|
|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|
|Sequential search|`n`|`n`|`n`|`n/2`|`n`| `n/2`| Insert indicates search(miss)|
|Binary search|`log(n)`|`n`|`n`|`log(n)`|`n/2`|`n/2`|Array resizing to be considered|
|BST|`n`|`n`|`n`|`1.4log(n)`|`n/2`|`?`|Deletion/insertion order affects complexity|
|2-3 Tree|`clog(n)`|`clog(n)`|`clog(n)`|`clog(n)`|`clog(n)`|`clog(n)`|Always balanced|
|Red-Black Tree|`2log(n)`|`2log(n)`|`2log(n)`|`log(n)`|`log(n)`|`log(n)`|Impl of 2-3 Tree|

#### 1-d range search, efficiency

|Data-structure|Insert|Range-count|Range-search|
|:---:|:---:|:---:|:---:|
|Unordered array|`1`|`n`|`n`|
|Ordered array|`n`|`log(n)`|`matches + log(n)`|
|Trees|`log(n)`|`log(n)`|`matches + log(n)`| 
```java
//Get Range count between (high, low) in trees 
if(treeContains(high))
    rank(high) - rank(low) + 1;
else
    rank(high) - rank(low);
```

