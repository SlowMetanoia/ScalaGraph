new Array[Boolean](10)
//Seq.empty[Int].head
Seq(3,4,5).zipWithIndex.map(_.swap).toMap
Seq(1,2,3,4,5).combinations(2).forall(pair=> pair.head != pair.last)