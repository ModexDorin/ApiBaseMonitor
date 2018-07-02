
// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/dorin/workspace/java/ApiBaseCurrency/conf/routes
// @DATE:Mon Jun 25 01:14:46 EEST 2018


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
