using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Newtonsoft.Json;

namespace MessengerApi.Controllers
{
    public class ProfileControler
    {
        private dbContext _database = new dbContext();

        [System.Web.Http.Route("api/profile/{token}-{userId}")]
        [System.Web.Http.HttpGet]
        public string GetProfile(string token, int userId)
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

            return JsonConvert.SerializeObject(this._database.Users.Where(u => u.Id == userId).FirstOrDefault());
        }
    }
}