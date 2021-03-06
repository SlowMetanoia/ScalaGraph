#Описание архитектуры и идеологии?
В общем, постараюсь быть краток. Пишем мы на scala.
Пишем в смешанном объектно-функциональном стиле:
1. Структуры данных должны быть неизменяемыми. (Например, Seq вместо Array)
2. У структур могут быть методы.
   1. Если метод возвращает Boolean, он должен начинаться со слов "is","has" и т.д. (Например, isConnected, hasEdge, и т.д.)
   2. Если метод возвращает значение, непосредственно связанное со структурой, но называться он должен так же, как это значение (Например, connectedParts вместо getConnectedParts или length вместо getLength)
   3. Если метод возвращает значение, не касающееся структуры напрямую, он должен иметь в названии глагол

Что касается архитектуры: BDUF - Big Design Up Front, то есть мы изначально пытаемся учесть всё, 
что может нам потребоваться в будущем, а принятие любых решений стараемся отложить до тех пор, пока ответ не станет очевидным.

Пример использования: 
Допустим, нам нужна какая-то модель графа, сразу возникают вопросы:
1. Какого графа это модель? (с точки зрения математики)
2. Как мы его храним? (схема хранения данных)
   1. Как мы получаем доступ к графу/элементам графа? (Интерфейсы, устройство классов, UML)
   2. Насколько эффективна схема хранения по отношению к некоторому алгоритму? (асимптотики доступа к частям и зависимость затрат по времени и памяти)

Но главное: можем ли мы отложить решение этих вопросов?
И ответ в начале проектирования - да!

Для этого нам нужная общая абстракция для любого графа и набор алгоритмов, работающих для графов схожим образом.


1. Тема:"Программная реализация генераторов граф-моделей на платформе JVM средствами Scala" (КР по ФиЛП, ПОВС)
2. Тема:"Исследование алгоритмов построения сетевых РПД на основе распределенной графовой базы данных" (КР по ФиЛП, ПОВС)
3. Тема:"Пакет алгоритмов вычисления центральности на языке Scala" (КР по ФиЛП)
4. Тема:"Пакет алгоритмов выделения сообществ на языке Scala" (КР по ФиЛП)