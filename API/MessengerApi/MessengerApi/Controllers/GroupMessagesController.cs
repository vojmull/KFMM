using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using MessengerApi.Models;

namespace MessengerApi.Controllers
{
    //public class GroupMessagesController : ApiController
    //{
    //    private dbContext _database = new dbContext();

    //    [System.Web.Http.Route("api/groupmessages/{token}-{userId}-{conversationCnt}")]
    //    [System.Web.Http.HttpGet]
    //    public string GetConversations(string token, int userId, int conversationCnt)
    //    {


    //    }

    //    public bool CheckToken(string token)
    //    {
    //        Token t = Token.Exists(token);
    //        if (t == null || !t.IsUser)
    //        {
    //            return false;
    //        }
    //        return true;
    //    }
    //}
}
