package Graphs.Structures.Storeges.CourseLinkKeepers

import Databases.Models.Domain.IKSA
import Graphs.Structures.Storeges.MapLinkKeeper

case
class KSALinkKeeper(
                     fromKSA: Map[ IKSA, Int ],
                     toKSA: Map[ Int, IKSA ]
                   )
  extends MapLinkKeeper(fromKSA, toKSA) {
  def apply( ksa: IKSA ): Int = fromKSA(ksa)
  
  def apply( n: Int ): IKSA = toKSA(n)
}

object KSALinkKeeper {
  private def mapReverse[ T1, T2 ]: Map[ T1, T2 ] => Map[ T2, T1 ] = for ((k, v) <- _) yield (v, k)
  
  def apply( fromKSA: Map[ IKSA, Int ] ): KSALinkKeeper = new KSALinkKeeper(fromKSA, mapReverse(fromKSA))
}