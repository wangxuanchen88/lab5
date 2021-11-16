# Specifics/Description of Implementations
The aim of this document is to give you a brief overview of the specifics of this implementation. Hopefully specifying anything that may cause confusion with alternative implementations.

Any specifics not mentioned here can be assumed the same as a typical BTree data structure as defined by Donald Knuth's definition of a BTree.

## Mutable
This implementation is mutable.

## Root Node
This implementation keeps track of the root node of the BTree. Therefore, all child nodes have a reference to the root node of the BTree. More explanation is provided in the BTree.java file as to why we do this.

You are free to change this design such that each BTreeNode keeps track of the parent or any other design variation so long as you genuinely pass the tests.

## BTree and BTreeNode Classes
Within BTree.java, the BTree class refers to the BTree as a whole and contains a reference to the root node. The BTreeNode class refers to the individual nodes that make up the BTree data structure.

## BTree Basics to Help You Start
A BTree grows upwards by splitting. Please see
  https://www.cs.usfca.edu/~galles/visualization/BTree.html for a visualisation of this process.
