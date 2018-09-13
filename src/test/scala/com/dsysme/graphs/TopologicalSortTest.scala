//package com.dsysme.graphs
//
//import Generators._
//import org.scalatest.{FunSuite, Matchers}
//import org.scalatest.prop.GeneratorDrivenPropertyChecks
//
//class TopologicalSortTest extends FunSuite with GeneratorDrivenPropertyChecks with Matchers{
//
//  //https://youtu.be/AfSk24UTFS8?t=2864
//  test ("for any edg (u, v) v finishes before u finishes") {
//    forAll { (directedGraph: DirectedGraph) =>
//        GraphUtils.isTopologicalSort(directedGraph, TopologicalSort.sort(directedGraph)) should be (true)
//
//    }
//  }
//
//
//}
