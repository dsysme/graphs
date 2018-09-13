package com.dsysme.graphs

//import org.scalacheck.{Arbitrary, Gen}
//
//object Generators {
//
//  implicit val arbDirectedGraph: Arbitrary[DirectedGraph] = Arbitrary(genDirectedGraph)
//
//  val MAX_VERTEXES = 50
//  val MIN_VERTEXES = 0
//
//  def genEdge(g: Gen[Int]): Gen[(Int, Int)] = Gen.sized { size =>
//    for {
//      from <- Gen.chooseNum[Int](0, size)
//      to <- Gen.chooseNum[Int](0, size)
//    } yield (from, to)
//  }
//
//  def genGraphEdges(vertexCount: Int, genEdge: Gen[(Int, Int)]) = {
//    Gen.chooseNum { edgesCount: Int =>
//      for (i <- 0 to edgesCount) yield {
//    }
//  }
//
//  def genDirectedGraph(n: Int, get: Gen[Int]) = Gen.listOfN()
//  }
//
//}
