using MessengerApi.Models;
using MySql.Data.MySqlClient;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Http;
using System.Web.Mvc;

namespace MessengerApi.Controllers
{
    public class AppColorController : ApiController
    {
        [System.Web.Http.Route("api/color/{token}")]
        [System.Web.Http.HttpPost]
        public string PostColor(string token,[FromBody]AppColors appColors)
        {
            Token t = Token.Exists(token);
            if (t == null)
            {
                //token není v databázi  
                return "ERROR";
            }
            if (!t.IsUser)
            {
                //token nepatří userovi
                return "ERROR";
            }
            string response;

            using (MySqlConnection connection = WebApiConfig.Connection())
            {
                try
                {
                    connection.Open();

                    MySqlCommand QuerySelectAppColor = connection.CreateCommand();
                    QuerySelectAppColor.CommandText = "SELECT AppColors.Color from AppColors where IdUser=@iduser;";
                    QuerySelectAppColor.Parameters.AddWithValue("@iduser", appColors.IdUser);

                    string Color = Convert.ToString(QuerySelectAppColor.ExecuteScalar());

                    if (Color != "")
                    {
                        MySqlCommand QueryUpdateColor = connection.CreateCommand();
                        QueryUpdateColor.CommandText = "UPDATE AppColors SET Color=@color WHERE IdUser=@iduser;";
                        QueryUpdateColor.Parameters.AddWithValue("@color", appColors.Color);
                        QueryUpdateColor.Parameters.AddWithValue("@iduser", appColors.IdUser);

                        QueryUpdateColor.ExecuteNonQuery();
                        return response = "OK";
                    }


                    string sql =
                    "INSERT INTO AppColors (IdUser,Color) VALUES(@IdUser,@Color)";

                    

                    MySqlCommand query = new MySqlCommand(sql, connection);
                    query.Parameters.AddWithValue("@IdUser", appColors.IdUser);
                    query.Parameters.AddWithValue("@Color", appColors.Color);

                    query.ExecuteNonQuery();
                    response = "OK";
                }

                catch (Exception)
                {
                    response = "ConnectionWithDatabaseProblem";
                }
            }

            return response;
        }

        [System.Web.Http.Route("api/color/{token}-{iduser}")]
        [System.Web.Http.HttpGet]
        public string GetType(string token,int iduser)
        {
            Token t = Token.Exists(token);
            if (t == null)
            {
                //token není v databázi  
                return "TokenNotFound";
            }
            if (!t.IsUser)
            {
                //token nepatří adminovi  
                return "TokenIsNotMatched";
            }

            MySqlConnection Connection = WebApiConfig.Connection();

            MySqlCommand Query = Connection.CreateCommand();

            Query.CommandText = "SELECT AppColors.Color FROM AppColors where IdUser=@iduser";
            Query.Parameters.AddWithValue("@iduser", iduser);

            string r = "";

            try
            {
                Connection.Open();
                r = Convert.ToString(Query.ExecuteScalar());
            }
            catch (MySql.Data.MySqlClient.MySqlException ex)
            {
                r = "ConnectionWithDatabaseProblem";
            }
            Connection.Close();

            return r;
        }
    }
}
