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

            List<Users> toRet = new List<Users>();

            try
            {
                toRet = this._database.Users.ToList();
            }
            catch (MySql.Data.MySqlClient.MySqlException ex)
            {
                return "ConnectionWithDatabaseProblem";
            }

            foreach (Users item in toRet)
            {
                item.Password = "";
            }

            return JsonConvert.SerializeObject(toRet);
        }

        //vraci ID usera, popripade ID konverzace, kdyz existuje => kdyz ne, tak je tam 0
        //napr 1-15 (iduser 1, idconversation 15)
        //napr 0-0 (user ani conversation neexistuje)
        [System.Web.Http.Route("api/users/checkuser/{token}-{typedName}-{userId}")]
        [System.Web.Http.HttpGet]
        public string CheckUserExists(string token, string typedName, int userId)
        {
            //TODO nevratit, pokud se s nim nekamosi
            string toRet = "0";

            Token t = Token.Exists(token);
            if (t == null || !t.IsUser)
            {
                return "TokenERROR";
            }

            List<string> nameContent = typedName.Split(' ').ToList();

            foreach (Users item in this._database.Users.ToList())
            {

                if (item.Name + " " + item.Surname == typedName)
                {
                    toRet = item.Id.ToString();
                    break;
                }
            }

            if (toRet == "0")
                return "0-0";

            int uId = Convert.ToInt32(toRet);
            var query = from p in this._database.Participants
                        join c in this._database.Conversations
                        on p.IdConversation equals c.Id
                        join u in this._database.Users
                        on p.IdUser equals u.Id
                        where p.IdUser == uId
                        select c;

            foreach (Conversations item in query.ToList())
            {
                foreach (Participants par in this._database.Participants.Where(p => p.IdConversation == item.Id).ToList())
                {
                    if (par.IdUser == userId)
                        return $"{toRet}-{item.Id.ToString()}";
                }
            }

            return toRet + "-0";


            //int pId = Convert.ToInt32(toRet);
            ////check konverzace
            //List<Participants> p = this._database.Participants.OrderBy(p => p.IdConversation).ToList();

                //p.Where(x => x.)

                //foreach (Participants item in p)
                //{
                //    if (item.IdConversation == )
                //}

                return toRet.ToString();
        }
    }
}