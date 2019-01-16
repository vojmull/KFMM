using MessengerApi.Models;
using MessengerApi.Utilities;
using MySql.Data.MySqlClient;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Http;
using System.Web.Mvc;

namespace MessengerApi.Controllers
{
    public class RegisterUserFaicController : ApiController
    {
        [System.Web.Http.Route("api/newuserfaic")]
        [System.Web.Http.HttpPost]
        public string Post([FromBody]string user)
        {


            Users userr = JsonConvert.DeserializeObject<Users>(user);
            MySqlConnection Connection = WebApiConfig.Connection();


            MySqlCommand QuerySelectUser = Connection.CreateCommand();
            QuerySelectUser.CommandText = "SELECT Users.Email from Users where Email=@email;";
            QuerySelectUser.Parameters.AddWithValue("@email", userr.Email);


            MySqlCommand QueryInsertUser = Connection.CreateCommand();

            QueryInsertUser.CommandText = "INSERT INTO Users (Name,Surname,Password,Email) VALUES (@name,@surname,@password,@email);" + " SELECT last_insert_id();";
            QueryInsertUser.Parameters.AddWithValue("@name", userr.Name);
            QueryInsertUser.Parameters.AddWithValue("@password", HashUtility.HashPassword(userr.Password));
            QueryInsertUser.Parameters.AddWithValue("@surname", userr.Surname);
            QueryInsertUser.Parameters.AddWithValue("@email", userr.Email);


            try
            {
                Connection.Open();

                string UserEmail = Convert.ToString(QuerySelectUser.ExecuteScalar());

                if (UserEmail == userr.Email)
                {
                    Connection.Close();
                    return "WrongEmail";
                }


                Token tok = Token.GenerateNewInicializationToken();

                MySqlCommand QueryInsertTokensUsers = Connection.CreateCommand();



                int UserId = Convert.ToInt32(QueryInsertUser.ExecuteScalar());

                QueryInsertTokensUsers.CommandText = "INSERT INTO TokensUsers (IdToken,IdUser) VALUES (@idToken,@idUser);";
                QueryInsertTokensUsers.Parameters.AddWithValue("@idToken", tok.ID);
                QueryInsertTokensUsers.Parameters.AddWithValue("@idUser", UserId);

                QueryInsertTokensUsers.ExecuteNonQuery();

            }
            catch (MySql.Data.MySqlClient.MySqlException ex)
            {
                return "ConnectionWithDatabaseProblem";
            }


            Connection.Close();


            return "OK";
        }
    }
}