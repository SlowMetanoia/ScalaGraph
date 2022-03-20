package Cousrses.Configurations

import scalikejdbc.config.DBs

object Configurations extends App {
  DBs.setupAll()
}
