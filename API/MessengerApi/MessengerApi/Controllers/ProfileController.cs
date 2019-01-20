using MessengerApi.Models;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace MessengerApi.Controllers
{
    public class ProfileController : ApiController
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
