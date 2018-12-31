﻿using MessengerApi.Models;
using MessengerApi.Utilities;
using MySql.Data.MySqlClient;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Http;
using System.Web.Mvc;

namespace MessengerApi.Controllers
{
    public class RegisterUserController : ApiController
    {
        [System.Web.Http.Route("api/newuser")]
        [System.Web.Http.HttpPost]
        public string Post([FromBody]Users user)
        {

            MySqlConnection Connection = WebApiConfig.Connection();
            string r = "";


            MySqlCommand QuerySelectUser = Connection.CreateCommand();
            QuerySelectUser.CommandText = "SELECT Users.Email from Users where Email=@email;";
            QuerySelectUser.Parameters.AddWithValue("@email", user.Email);


            MySqlCommand QueryInsertUser = Connection.CreateCommand();

            QueryInsertUser.CommandText = "INSERT INTO Users (Name,Surname,Password,Email) VALUES (@name,@surname,@password,@email);" + " SELECT last_insert_id();";
            QueryInsertUser.Parameters.AddWithValue("@name", user.Name);
            QueryInsertUser.Parameters.AddWithValue("@password", HashUtility.HashPassword(user.Password));
            QueryInsertUser.Parameters.AddWithValue("@surname", user.Surname);
            QueryInsertUser.Parameters.AddWithValue("@email", user.Email);


            try {
                Connection.Open();

                string UserEmail = Convert.ToString(QuerySelectUser.ExecuteScalar());

                if (UserEmail == user.Email)
                {
                    Connection.Close();
                    return r = "WrongEmail";
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
                r = "ConnectionWithDatabaseProblem";
            }
            

            Connection.Close();

            r = "OK";

            return r;
        }
    }
}