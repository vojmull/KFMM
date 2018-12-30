using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Http;
using Newtonsoft.Json;

namespace MessengerApi.Controllers
{
    public class MessagesController : ApiController
    {
        private dbContext _database = new dbContext();

        [System.Web.Http.Route("api/messages/{userId}")]
        [System.Web.Http.HttpGet]
        public string GetMessages(int userId)
        {


            //return JsonConvert.SerializeObject(this._database.Messages.Where(m => m.IdAuthor == userId).ToList());
            var query = from m in this._database.Messages
                        join f in this._database.Friendships
                        on m.IdFriendship equals f.Id
                        where f.IdUser1 == userId || f.IdUser2 == userId
                        select m;

            return JsonConvert.SerializeObject(query.ToList<Messages>());
        }
    }
}