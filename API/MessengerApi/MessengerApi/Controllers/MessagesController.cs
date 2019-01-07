using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Web;
using System.Web.Http;
using MessengerApi.Models;
using Newtonsoft.Json;

namespace MessengerApi.Controllers
{
    public class MessagesController : ApiController
    {
        private dbContext _database = new dbContext();
        //D:\vojta\Knihovny\Desktop\git_desktop\KFMM\API\MessengerApi\MessengerApi
        private string _imgStorage = @"D:\vojta\Knihovny\Desktop\git_desktop\KFMM\API\MessengerApi\MessengerApi" + "/_imgStorage/";

        // Vraci posledních X konverzaci (parametr) s obsahem jedne (posledni zpravy) => pro prehled vsech konverzaci
        [System.Web.Http.Route("api/messages/{token}-{userId}-{conversationCnt}")]
        [System.Web.Http.HttpGet]
        public string GetConversations(string token, int userId, int conversationCnt)
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

            if (conversationCnt == 0)
                conversationCnt = 100000;

            List<Conversations> Conversations = this._database.Conversations.Where(c => c.IdUser1 == userId || c.IdUser2 == userId).Take(conversationCnt).ToList<Conversations>();
            foreach (Conversations item in Conversations)
            {
                Messages lastMessage = this._database.Messages.Where(m => m.IdConversation == item.Id).OrderByDescending(m => m.TimeSent).FirstOrDefault();
                item.LastMessage = lastMessage.Content;
                item.LastMessageSent = lastMessage.TimeSent;
                int userIdWith = (userId == item.IdUser1) ? item.IdUser2 : item.IdUser1;
                Users user = this._database.Users.Where(u => u.Id == userIdWith).FirstOrDefault();
                item.Name = user.Name;
                item.Surname = user.Surname;
                item.ImageServerPath = user.ImageServerPath;
            }

            return JsonConvert.SerializeObject(Conversations);
        }

        [System.Web.Http.Route("api/messages/getimage/{token}-{imgPath}")]
        [System.Web.Http.HttpGet]
        public HttpResponseMessage GetImage(string token, string imgPath)
        {
            Token t = Token.Exists(token);
            if (t == null)
            {
                //token není v databázi  
                return null; //error
            }
            if (!t.IsUser)
            {
                //token nepatří userovi  
                return null; //error
            }
            HttpResponseMessage response = new HttpResponseMessage();
            response.Content = new StreamContent(new FileStream(this._imgStorage + imgPath + ".png", FileMode.Open)); // this file stream will be closed by lower layers of web api for you once the response is completed.
            response.Content.Headers.ContentType = new MediaTypeHeaderValue("image/png");

            return response;
        }

        [System.Web.Http.Route("api/messages/getconversation/{token}-{messagesCnt}-{conversationId}")]
        [System.Web.Http.HttpGet]
        public string GetMessages(string token, int messagesCnt, int conversationId)
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

            if (messagesCnt == 0)
                messagesCnt = 100000;

            var query = from m in this._database.Messages
                        join c in this._database.Conversations
                        on m.IdConversation equals c.Id
                        where c.Id == conversationId
                        select m;

            return JsonConvert.SerializeObject(query.OrderBy(m => m.Id).Take(messagesCnt).ToList());
        }

        [System.Web.Http.Route("api/messages/postmessage/{token}-{userId}-{conversationId}")]
        [System.Web.Http.HttpPost]
        public string PostMessage(string token, int userId, int conversationId, [FromBody]string content)
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

            int user2Id = this._database.Conversations.Where(c => c.Id == conversationId).Select(c => (c.IdUser1 == userId) ? c.IdUser2 : c.IdUser1).FirstOrDefault();

            Messages m = new Messages()
            {
                Content = content,
                Delievered = false,
                Edited = false,
                IdAuthor = userId,
                IdConversation = conversationId,
                Seen = false,
                TimeSent = DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss"),
                TimeSeen = "",
                TimeDelievered = "",
                IdFriendship = this._database.Friendships.Where(f => (f.IdUser1 == userId && f.IdUser2 == user2Id) || (f.IdUser1 == user2Id && f.IdUser2 == userId)).Select(f => f.Id).First()
            };

            this._database.Messages.Add(m);
            this._database.SaveChanges();

            return "ok";
        }
    }
}