﻿using System;
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
                item.ChatName = (par.Count > 1) ? "Group" : $"{user.Name} {user.Surname}";
                item.Name = user.Name;
                item.Surname = user.Surname;
                item.ImageServerPath = user.ImageServerPath;
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
    }
}