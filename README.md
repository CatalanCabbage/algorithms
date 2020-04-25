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

#### Priority Queues
* Ordered based on priority, structurally a tree in an array
* Removal of max/min from the heap is constant time
* For min-heap, each parent is less than it's children, and vice versa
* Completely balanced tree: Has all values filled except for bottom level
* When starting entries from index 1:
    * Parent(`n`) = `n/2`
    * Children(`n`) = `2n`, `2n + 1`
* Some applications: 
    * A* algorithm, like Game trees 
    * Maintaining top x entries from a large dataset, for eg., by Timestamp
    * Data compression, Huffman codes (todo)
    * Dijkstra's shortest path (todo with graphs)
* Operations: 
    * `sink`, dropping a value down the tree to correct position
    * `swim`, raising a value up the tree to correct position
* HeapSort:
    * In-place sort with `nlog(n)` worst case
    * Operations:
        * Heap construction: at most `2n` compares and exchanges (mathematical proof)
        * Heap sort: at most `2nlog(n)` compares and exchanges
    * Disadvantages:
        * Inner loop is longer than QuickSort's
        * Poor use of cache, since references are all over the place
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
