using MessengerApi.Models;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Http;
using System.Web.Mvc;

namespace MessengerApi.Controllers
{
    public class ProfilesController : ApiController
    {
        private dbContext _database = new dbContext();

        [System.Web.Http.Route("api/profiles/{token}-{userId}")]
        [System.Web.Http.HttpGet]
        public string GetProfiles(string token, int userId)
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