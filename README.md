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

#### Sorts recap

| x | In-Place | Stable | Worst | Avg | Best | Remarks |
|-----|:---:|:---:|:---:|:---:|:---:|---|
|Selection| Y   | N | `n*n/2` | `n*n/2` | `n*n/2` | n exchanges |
|Insertion| Y   | Y | `n*n/2` | `n*n/4` | `n` | use for partially sorted |
|Shell| Y | Y | `?` | `?` | `n` | subquadratic |
|Merge| N       | Y | `nlog(n)` | `nlog(n)` | `nlog(n)` | extra space |
|Quick| Y       | N | `n*n` | `nlog(n)` | `nlog(n)` | probabilistically fastest |
|3-way Quick| Y | N | `n*n` | `nlog(n)` | `n` | quickest with duplicates |
