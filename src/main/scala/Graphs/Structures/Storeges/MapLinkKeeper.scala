package Graphs.Structures.Storeges

class MapLinkKeeper[ L, R ]( lMap: Map[ L, R ], rMap: Map[ R, L ] ) extends ILinkKeeper[ L, R ] {
  override def left2Right( l: L ): R = lMap(l)
  
  override def right2Left( r: R ): L = rMap(r)
  
  override def add( p1: L, p2: R ): MapLinkKeeper[L,R] = MapLinkKeeper(lMap + ( p1 -> p2 ))
  
  override def addAll( cc: Iterable[ (L, R) ] ): MapLinkKeeper[L,R] = MapLinkKeeper(lMap ++ cc)
  
  val size: Int = lMap.size
}

object MapLinkKeeper {
  private def mapReverse[ T1, T2 ]: Map[ T1, T2 ] => Map[ T2, T1 ] = for ((k, v) <- _) yield (v, k)
  
  def apply[ T1, T2 ]( map: Map[ T1, T2 ] ): MapLinkKeeper[ T1, T2 ] = new MapLinkKeeper(map, mapReverse(map))
}
