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
using MessengerApi.Utilities;

namespace MessengerApi.Controllers
{
    public class MessagesController : ApiController
    {
        private dbContext _database = new dbContext();
        //private CheckOnlineUtility onlineUtility = new CheckOnlineUtility();

        //D:\vojta\Knihovny\Desktop\git_desktop\KFMM\API\MessengerApi\MessengerApi
        private string _imgStorage = @"D:\vojta\Knihovny\Desktop\git_desktop\KFMM\API\MessengerApi\MessengerApi" + "/_imgStorage/";

        // Vraci posledních X konverzaci (parametr) s obsahem jedne (posledni zpravy) => pro prehled vsech konverzaci
        // trigger pro byti online
        [System.Web.Http.Route("api/messages/{token}-{userId}-{conversationCnt}")]
        [System.Web.Http.HttpGet]
        public string GetConversations(string token, int userId, int conversationCnt)
        {
            CheckOnlineUtility.ConfirmOnlineStatus(userId);

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

            var query = from c in this._database.Conversations
                        join p in this._database.Participants
                        on c.Id equals p.IdConversation
                        where p.IdUser == userId
                        select c;

            List<Conversations> Conversations = query.Take(conversationCnt).ToList();
            foreach (Conversations item in Conversations)
            {
                Messages lastMessage = this._database.Messages.Where(m => m.IdConversation == item.Id).OrderByDescending(m => m.TimeSent).FirstOrDefault();
                item.LastMessage = lastMessage.Content;
                item.LastMessageSentAt = lastMessage.TimeSent;
                item.LastMessageSent = lastMessage.Sent;
                item.LastMessageRead = lastMessage.Seen;
                item.IdLastMessageAuthor = lastMessage.IdAuthor;

                List<Participants> par = this._database.Participants.Where(p => p.IdConversation == item.Id && userId != p.IdUser).ToList();
                int idParticipant = par[0].IdUser;

                Users user = this._database.Users.Where(u => u.Id == idParticipant).FirstOrDefault();

                if (par.Count > 1)
                {
                    foreach (Participants p in par)
                    {
                        item.ChatName += this._database.Users.Where(u => u.Id == p.IdUser).Select(u => u.Name).First() + ", ";
                    }
                    item.ChatName = item.ChatName.Substring(0, 15) + "...";
                }
                else
                {
                    item.ChatName = $"{user.Name} {user.Surname}";
                }

                item.Name = user.Name;
                item.Surname = user.Surname;
                item.ImageServerPath = user.ImageServerPath;
                item.IdParticipant = user.Id;
                item.IsOnline = item.IsOnline;
                item.TimeLastOnline = item.TimeLastOnline;
            }

            return JsonConvert.SerializeObject(Conversations.OrderByDescending(c => c.LastMessageSentAt));
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

            foreach (Messages item in query.ToList())
            {
                item.AuthorName = this._database.Users.Where(u => u.Id == item.IdAuthor).Select(u => u.Name + " " + u.Surname).FirstOrDefault().ToString();
            }

            return JsonConvert.SerializeObject(query.OrderByDescending(m => m.Id).Take(messagesCnt).OrderBy(m => m.Id).ToList());
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
            };

            this._database.Messages.Add(m);
            this._database.SaveChanges();

            return "ok";
        }

        [System.Web.Http.Route("api/messages/startconversation/{token}-{userId}-{userToId}")]
        [System.Web.Http.HttpPost]
        public string StartNewConversation(string token, int userId, int userToId, [FromBody]string content)
        {
            Token t = Token.Exists(token);
            if (t == null || !t.IsUser)
            {
                return "ERROR";
            }

            try
            {
                string now = DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss");
                this._database.Conversations.Add(new Conversations()
                {
                    TimeCreated = now
                });
                this._database.SaveChanges();

                Conversations con = this._database.Conversations.Where(c => c.TimeCreated == now).First();
                this._database.Participants.Add(new Participants()
                {
                    IdConversation = con.Id,
                    IdUser = userId
                });
                this._database.Participants.Add(new Participants()
                {
                    IdConversation = con.Id,
                    IdUser = userToId
                });
                this._database.SaveChanges();

                Messages m = new Messages()
                {
                    Content = content,
                    Delievered = false,
                    Edited = false,
                    IdAuthor = userId,
                    IdConversation = con.Id,
                    Seen = false,
                    TimeSent = DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss"),
                    TimeSeen = "",
                    TimeDelievered = "",
                };

                this._database.Messages.Add(m);
                this._database.SaveChanges();
            }
            catch
            {
                return "ERROR";
            }

            return "OK";
        }

        [System.Web.Http.Route("api/messages/messageread/{token}-{userId}-{conversationId}")]
        [System.Web.Http.HttpGet]
        public string ConfirmConversationRead(string token, int userId, int conversationId)
        {
            Token t = Token.Exists(token);
            if (t == null || !t.IsUser)
            {
                return "ERROR";
            }

            foreach (Messages item in this._database.Messages.Where(m => m.IdConversation == conversationId && !m.Seen && m.IdAuthor != userId))
            {
                item.Seen = true;
            }

            this._database.SaveChanges();

            return "OK";
        }

        [System.Web.Http.Route("api/messages/getuserinfo/{token}-{userId}-{conversationId}")]
        [System.Web.Http.HttpGet]
        public string GetUserInfo(string token, int userId, int conversationId)
        {
            Token t = Token.Exists(token);
            if (t == null || !t.IsUser)
            {
                return "ERROR";
            }

            var query = from c in this._database.Conversations
                        join p in this._database.Participants
                        on c.Id equals p.IdConversation
                        where p.Id != userId && c.Id == conversationId
                        select c;

            return JsonConvert.SerializeObject(query.FirstOrDefault());
        }

        //GROUP MESSAGES
        [System.Web.Http.Route("api/messages/groups/adduser/{token}-{userToAdd}-{conversationId}")]
        [System.Web.Http.HttpGet]
        public string AddUserToConversation(string token, int userToAdd, int conversationId)
        {
            Token t = Token.Exists(token);
            if (t == null || !t.IsUser)
            {
                return "ERROR";
            }


            try
            {
                this._database.Participants.Add(new Participants()
                {
                    IdConversation = conversationId,
                    IdUser = userToAdd
                });

                this._database.SaveChanges();
                return "OK";
            }
            catch
            {
                return "DatabaseError";
            }
        }

        [System.Web.Http.Route("api/messages/groups/removeuser/{token}-{userToRemove}-{conversationId}")]
        [System.Web.Http.HttpGet]
        public string RemoveUserFromConversation(string token, int userToRemove, int conversationId)
        {
            Token t = Token.Exists(token);
            if (t == null || !t.IsUser)
            {
                return "ERROR";
            }


            try
            {
                this._database.Participants.Remove(this._database.Participants.Where(p => p.IdConversation == conversationId && p.IdUser == userToRemove).First());

                this._database.SaveChanges();
                return "OK";
            }
            catch
            {
                return "DatabaseError";
            }
        }
    }
}