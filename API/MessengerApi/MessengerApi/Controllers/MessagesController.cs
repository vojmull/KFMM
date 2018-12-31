using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Http;
using MessengerApi.Models;
using Newtonsoft.Json;

namespace MessengerApi.Controllers
{
    public class MessagesController : ApiController
    {
        private dbContext _database = new dbContext();

        // Vraci posledních X konverzaci (parametr) s obsahem jedne (posledni zpravy) => pro prehled vsech konverzaci
        [System.Web.Http.Route("api/messages/{userId}-{conversationCnt}")]
        [System.Web.Http.HttpGet]
        public string GetMessages(int userId, int conversationCnt)
        {
            List<Conversations> Conversations = this._database.Conversations.Where(c => c.IdUser1 == userId || c.IdUser2 == userId).Take(conversationCnt).ToList<Conversations>();
            foreach (Conversations item in Conversations)
            {
                item.LastMessage = this._database.Messages.Where(m => m.IdConversation == item.Id).Select(m => m.Content).FirstOrDefault().ToString();
            }

            return JsonConvert.SerializeObject(Conversations);
        }
    }
}