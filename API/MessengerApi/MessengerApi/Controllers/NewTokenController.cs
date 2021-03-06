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
    public class NewTokenController : ApiController
    {
        public dbContext _database = new dbContext();

        [System.Web.Http.Route("api/newtoken/user")]
        [System.Web.Http.HttpPost]
        public string PostUser([FromBody]Users user)
        {
            string response;

            using (MySqlConnection connection = WebApiConfig.Connection())
            {
                try
                {
                    connection.Open();

                    string sql =
                    "SELECT Id,Password FROM Users WHERE Email=@Email LIMIT 1";

                    MySqlCommand query = new MySqlCommand(sql, connection);
                    query.Parameters.AddWithValue("@Email", user.Email);

                    MySqlDataReader reader = query.ExecuteReader();
                    if (reader.Read())
                    {
                        if (HashUtility.VerifyPassword(reader["Password"].ToString(), user.Password))
                        {
                            Token newToken = Token.GenerateNewTokenForUser(Convert.ToInt32(reader["Id"]));

                            if (newToken != null)
                            {
                                int idUser = Convert.ToInt32(reader["Id"]);
                                response = newToken.Value + "/" + idUser;

                                var eQuery = from m in this._database.Messages
                                             join c in this._database.Conversations
                                             on m.IdConversation equals c.Id
                                             join p in this._database.Participants
                                             on c.Id equals p.IdConversation
                                             where p.IdUser == idUser && m.IdAuthor != idUser
                                             select m;

                                foreach (Messages item in eQuery.ToList<Messages>())
                                {
                                    item.Delievered = true;
                                }
                                this._database.SaveChanges();
                            }
                            else
                                response = "TokenGenerationFailed";
                        }
                        else
                            response = "BadPassword";
                    }
                    else
                        response = "BadUserName";
                }
               catch (Exception)
                {
                    response = "ConnectionWithDatabaseProblem";
                }
            }

            return response;
        }
    }
}