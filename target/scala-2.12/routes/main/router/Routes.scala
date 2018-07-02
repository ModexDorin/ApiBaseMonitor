
// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/dorin/workspace/java/ApiBaseCurrency/conf/routes
// @DATE:Mon Jun 25 01:14:46 EEST 2018

package router

import play.core.routing._
import play.core.routing.HandlerInvokerFactory._

import play.api.mvc._

import _root_.controllers.Assets.Asset
import _root_.play.libs.F

class Routes(
  override val errorHandler: play.api.http.HttpErrorHandler, 
  // @LINE:3
  ExternalApiController_0: ro.iss.ApiBaseMonitor.controllers.ExternalApiController,
  val prefix: String
) extends GeneratedRouter {

   @javax.inject.Inject()
   def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:3
    ExternalApiController_0: ro.iss.ApiBaseMonitor.controllers.ExternalApiController
  ) = this(errorHandler, ExternalApiController_0, "/")

  def withPrefix(prefix: String): Routes = {
    router.RoutesPrefix.setPrefix(prefix)
    new Routes(errorHandler, ExternalApiController_0, prefix)
  }

  private[this] val defaultPrefix: String = {
    if (this.prefix.endsWith("/")) "" else "/"
  }

  def documentation = List(
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """""" + "$" + """requestId<[^/]+>""", """ro.iss.ApiBaseMonitor.controllers.ExternalApiController.requestId(requestId:Int)"""),
    Nil
  ).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
    case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
    case l => s ++ l.asInstanceOf[List[(String,String,String)]]
  }}


  // @LINE:3
  private[this] lazy val ro_iss_ApiBaseMonitor_controllers_ExternalApiController_requestId0_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), DynamicPart("requestId", """[^/]+""",true)))
  )
  private[this] lazy val ro_iss_ApiBaseMonitor_controllers_ExternalApiController_requestId0_invoker = createInvoker(
    ExternalApiController_0.requestId(fakeValue[Int]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "ro.iss.ApiBaseMonitor.controllers.ExternalApiController",
      "requestId",
      Seq(classOf[Int]),
      "GET",
      this.prefix + """""" + "$" + """requestId<[^/]+>""",
      """""",
      Seq()
    )
  )


  def routes: PartialFunction[RequestHeader, Handler] = {
  
    // @LINE:3
    case ro_iss_ApiBaseMonitor_controllers_ExternalApiController_requestId0_route(params@_) =>
      call(params.fromPath[Int]("requestId", None)) { (requestId) =>
        ro_iss_ApiBaseMonitor_controllers_ExternalApiController_requestId0_invoker.call(ExternalApiController_0.requestId(requestId))
      }
  }
}
