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

#### Elementary Symbol Tables
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