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
        public string GetMessages(string token,int userId, int conversationCnt)
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
            List<Conversations> Conversations = this._database.Conversations.Where(c => c.IdUser1 == userId || c.IdUser2 == userId).Take(conversationCnt).ToList<Conversations>();
            foreach (Conversations item in Conversations)
            {
                Messages lastMessage = this._database.Messages.Where(m => m.IdConversation == item.Id).FirstOrDefault();
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
        public HttpResponseMessage GetImage(string token,string imgPath)
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
    }
}