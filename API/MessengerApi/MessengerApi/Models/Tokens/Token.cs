using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Web;
using MySql.Data.MySqlClient;
using System.Web.Http;

namespace MessengerApi.Models
{
    public class Token
    {
        //Token t = Token.Exists(token); // tahle se to pak bude používat v controllerech
        //    if (t == null)
        //    {
        //        //token není v databázi  
        //        return error;
        //    }
        //    if (!t.IsUser)
        //    {
        //        //token nepatří userovi  
        //        return error;
        //    }
        /// <summary>
        /// Zkontroluje databázi zda obsahuje token a vrátí instanci třídy Token pro další použití, nebo null pokud v databázi token není.
        /// </summary>
        /// <param name="token">Token který se kontroluje</param>
        /// <returns>Instanci třídy Token nebo null</returns>
        public static Token Exists(string token)
        {
            Token result = null;

            using (MySqlConnection connection = WebApiConfig.Connection())
            {
                try
                {
                    connection.Open();

                    string sql =
                    "SELECT Tokens.Id,IdUser " +
                    "FROM Tokens " +
                    "LEFT JOIN TokensUsers ON Tokens.Id = TokensUsers.IdToken " +
                    "LEFT JOIN Users ON TokensUsers.IdUser = Users.Id " +
                    "WHERE Token=@token AND Status='current' LIMIT 1";

                    MySqlCommand query = new MySqlCommand(sql, connection);
                    query.Parameters.AddWithValue("@token", token);

                    MySqlDataReader reader = query.ExecuteReader();
                    if (reader.Read())
                    {
                        result = new Token(token, Convert.ToInt32(reader["Id"]), reader["IdUser"]);
                    }
                }
                catch (Exception)
                {
                }
            }

            return result;
        }
        /// <summary>
        /// Generuje nový token pro admina. Vrací instanci třídy Token, nebo null při vyjímce.
        /// </summary>
        /// <param name="idAdmin">Id admina</param>
        /// <returns>Instance třídy Token nebo null</returns>
        public static Token GenerateNewTokenForUser(int IdUser)
        {
            Token result = null;
            using (MySqlConnection connection = WebApiConfig.Connection())
            {
                try
                {
                    string newToken = Token.GenerateNewToken();
                    connection.Open();

                    string sqlUpdate =
                    "UPDATE Tokens INNER JOIN TokensUsers ON Tokens.Id = TokensUsers.IdToken " +
                    "SET Tokens.Token = @newToken " +
                    "WHERE TokensUsers.IdUser = @UserId AND status='current'";

                    MySqlCommand queryUpdate = new MySqlCommand(sqlUpdate, connection);
                    queryUpdate.Parameters.AddWithValue("@newToken", newToken);
                    queryUpdate.Parameters.AddWithValue("@UserId", IdUser);

                    int rows = queryUpdate.ExecuteNonQuery();

                    if (rows <= 0)
                    {
                        string sqlInsertIntoTokens =
                        "INSERT INTO Tokens(Token,Status) VALUES(@token,'current');" +
                        "SELECT last_insert_id();";

                        MySqlCommand queryInsertIntoTokens = new MySqlCommand(sqlInsertIntoTokens, connection);
                        queryInsertIntoTokens.Parameters.AddWithValue("@token", newToken);


                        int id = Convert.ToInt32(queryInsertIntoTokens.ExecuteScalar());

                        string sqlInsertIntoTokensUsers = "INSERT INTO TokensUsers(IdToken,IdUser) VALUES(@IdToken,@IdUser);";

                        MySqlCommand queryInsertIntoTokensAdmins = new MySqlCommand(sqlInsertIntoTokensUsers, connection);
                        queryInsertIntoTokensAdmins.Parameters.AddWithValue("@IdUser", IdUser);
                        queryInsertIntoTokensAdmins.Parameters.AddWithValue("@IdToken", id);
                        queryInsertIntoTokensAdmins.ExecuteNonQuery();


                        MySqlCommand queryGetAdminType = new MySqlCommand("SELECT type FROM Users WHERE Id = @UserId", connection);
                        queryGetAdminType.Parameters.AddWithValue("@UserId", IdUser);
                        string type = queryGetAdminType.ExecuteScalar().ToString();

                        result = new Token(newToken, id, IdUser);
                    }
                    else
                    {
                        result = Exists(newToken);
                    }
                }
                catch (Exception)
                {

                }
            }

            return result;
        }

        public static Token GenerateNewInicializationToken()
        {
            Token result = new Token(null, 0, 0);
            using (MySqlConnection connection = WebApiConfig.Connection())
            {
                try
                {
                    string newToken = Token.GenerateNewToken();
                    connection.Open();

                    DateTime expirationdate = DateTime.Now.AddDays(1).Date;

                    string sqlInsertIntoTokens =
                        "INSERT INTO Tokens(Token,Status,Expiration) VALUES(@token,'current',@expirationdate);" +
                        "SELECT last_insert_id();";

                    MySqlCommand queryInsertIntoTokens = new MySqlCommand(sqlInsertIntoTokens, connection);
                    queryInsertIntoTokens.Parameters.AddWithValue("@token", newToken);
                    queryInsertIntoTokens.Parameters.AddWithValue("@expirationdate", expirationdate);


                    int id = Convert.ToInt32(queryInsertIntoTokens.ExecuteScalar());

                    result = new Token(newToken, id, 0);
                }
                catch (Exception)
                {

                }
            }

            return result;
        }

        private static string GenerateNewToken()
        {
            const int length = 32;
            const string valid = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890,-";
            char[] result = new char[length];

            using (RNGCryptoServiceProvider rng = new RNGCryptoServiceProvider())
            {
                byte[] uintBuffer = new byte[sizeof(uint)];

                for (int i = 0; i < length; i++)
                {
                    rng.GetBytes(uintBuffer);
                    uint rndNum = BitConverter.ToUInt32(uintBuffer, 0);
                    result[i] = valid[(int)(rndNum % (uint)valid.Length)];
                }
            }

            return new string(result);
        }

        public int ID { get; private set; }
        public string Value { get; private set; }
        public int UserID { get; private set; }
        public bool IsUser
        {
            get { return this.UserID > 0; }
        }

        private Token(string token, int id, object idUser)
        {
            this.ID = id;
            this.Value = token;
            this.UserID = idUser == DBNull.Value ? 0 : ((int)idUser);
        }
    }
}