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
    public class UsersController : ApiController
    {
        private dbContext _database = new dbContext();

        [System.Web.Http.Route("api/users/{token}")]
        [System.Web.Http.HttpGet]
        public string GetAllUsers(string token)
        {
            Token t = Token.Exists(token);
            if (t == null || !t.IsUser)
            {
                return "TokenERROR";
            }

            List<Users> toRet = this._database.Users.ToList();

            foreach (Users item in toRet)
            {
                item.Password = "";
            }

            return JsonConvert.SerializeObject(toRet);
        }
    }
}
