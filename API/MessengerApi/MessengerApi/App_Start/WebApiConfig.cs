using MySql.Data.MySqlClient;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.Http;
using System.Web.Http.Cors;

namespace MessengerApi
{
    public static class WebApiConfig
    {
        public static MySqlConnection Connection()
        {
            
            string connection_string = "server=46.101.181.134;uid=goodboi;pwd=123456;database=kfmmmessenger";
            MySqlConnection Connection = new MySqlConnection(connection_string);

            return Connection;
        }
        public static void Register(HttpConfiguration config)
        {
            EnableCorsAttribute cors = new EnableCorsAttribute("*", "*", "GET, POST, DELETE");
            cors.SupportsCredentials = true;
            config.EnableCors(cors);
            // Web API configuration and services

            // Web API routes
            config.MapHttpAttributeRoutes();

            config.Routes.MapHttpRoute(
                name: "DefaultApi",
                routeTemplate: "api/{controller}/{id}",
                defaults: new { id = RouteParameter.Optional }
            );
        }
    }
}
