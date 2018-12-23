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
        [System.Web.Http.Route("api/newinitializationtoken/{token}")]
        public string Post(string token)
        {
            Token t = Token.Exists(token);
            if (t == null)
            {
                //token není v databázi  
                return "ERROR1";
            }
            if (!t.IsUser)
            {
                //token nepatří adminovi  
                return "ERROR2";
            }

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
        [System.Web.Http.Route("api/newinitializationtoken/{token}/{id}")]
        public string Delete(string token, int id)
        {
            Token t = Token.Exists(token);
            if (t == null)
            {
                //token není v databázi  
                return"ERROR";
            }
            if (!t.IsUser)
            {
                //token nepatří adminovi  
                return"ERROR";
            }

            MySqlConnection Connection = WebApiConfig.Connection();

            string r="OK";

            try
            {
                Connection.Open();

                MySqlCommand query = Connection.CreateCommand();
                query.CommandText = "DELETE FROM Tokens WHERE @id = Id";

                query.Parameters.AddWithValue("@id", id);

                query.ExecuteNonQuery();
            }
            catch (MySql.Data.MySqlClient.MySqlException ex)
            {
                r = "ERROR";
            }
            Connection.Close();

            return r;
        }
    }
}