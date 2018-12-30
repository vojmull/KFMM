using MessengerApi.Models;
using MessengerApi.Models.Tokens;
using MySql.Data.MySqlClient;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Http;
using System.Web.Mvc;

namespace MessengerApi.Controllers
{
    public class NewInitializationTokenController : ApiController
    {
        [System.Web.Http.Route("api/newinitializationtoken")]
        [System.Web.Http.HttpPost]
        public string Post()
        {
            MySqlConnection Connection = WebApiConfig.Connection();

            string r;
            try
            {
                Token tok = Token.GenerateNewInicializationToken();
                if (tok != null)
                    r = tok.Value;
                else
                    r= "ERROR3";
            }
            catch (MySql.Data.MySqlClient.MySqlException ex)
            {
                r="ERROR4";
            }
            Connection.Close();

            return r;
        }
    }
}