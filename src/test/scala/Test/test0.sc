Seq(1,2,3)
val seq = Vector(1,2,3)
def include[T](seq:Seq[T],included:Seq[T],position:Int) =
  seq.take(position) ++ included ++ seq.drop(position)
include(seq,seq,2)
