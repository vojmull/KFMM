﻿using MessengerApi.Models;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Http;

namespace MessengerApi.Controllers
{
    public class FriendshipsController : ApiController
    {
        private dbContext _database = new dbContext();

        // Poslani zadosti o pridani noveho pritele
        [System.Web.Http.Route("api/friends/adduser/{token}-{userId}-{requestedUserId}")]
        [System.Web.Http.HttpGet]
        public string AddFriend(string token, int userId, int requestedUserId)
        {
            string toRet = "OK";

            Token t = Token.Exists(token);
            if (t == null || !t.IsUser)
            {
                return "TokenERROR";
            }

            try
            {
                FriendshipRequests f = new FriendshipRequests()
                {
                    Accepted = false,
                    IdUserRequestor = userId,
                    IdUser2 = requestedUserId,
                    TimeSent = DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss"),
                    RequestExpiration = DateTime.Now.AddDays(7).ToString("yyyy-MM-dd HH:mm:ss")
                };

                this._database.FriendshipRequests.Add(f);
                this._database.SaveChanges();
            }
            catch
            {
                toRet = "ProblemWithDatabase";
            }

            return toRet;
        }

        //Potvrzeni zadosti o pridani do pratel
        [System.Web.Http.Route("api/friends/adduser/{token}-{userId}-{requestorUserId}-{accepted}")]
        [System.Web.Http.HttpGet]
        public string AnwserRequest(string token, int userId, int requestorUserId, int accepted)
        {
            bool bAccepted = (accepted == 0) ? false : true;
            string toRet = "OK";

            Token t = Token.Exists(token);
            if (t == null || !t.IsUser)
            {
                return "TokenERROR";
            }

            try
            {
                FriendshipRequests fr = this._database.FriendshipRequests.Where(f => f.IdUserRequestor == requestorUserId && userId == f.IdUser2).FirstOrDefault();
                fr.Accepted = bAccepted;
                this._database.SaveChanges();

                if (bAccepted)
                {
                    string now = DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss");
                    Friendships friendship = new Friendships()
                    {
                        TimeCreated = now,
                        IdUser1 = userId,
                        IdUser2 = requestorUserId
                    };

                    this._database.Friendships.Add(friendship);
                    this._database.SaveChanges();
                }
            }
            catch
            {
                toRet = "ERROR";
            }

            return toRet;
        }

        //Vraceni vsech svych pratel
        [System.Web.Http.Route("api/friends/{token}-{userId}")]
        [System.Web.Http.HttpGet]
        public string GetFriends(string token, int userId)
        {
            string toRet = "OK";

            Token t = Token.Exists(token);
            if (t == null || !t.IsUser)
            {
                return "TokenERROR";
            }

            try
            {
                List<Users> colToRet = new List<Users>();

                var query = from fr in this._database.Friendships
                            join us in this._database.Users
                            on fr.IdUser1 equals us.Id
                            where fr.IdUser2 == userId
                            select us;

                foreach (Users item in query.ToList<Users>())
                {
                    item.Password = "";
                    if (item.Id != userId)
                        colToRet.Add(item);
                }

                query = from fr in this._database.Friendships
                        join us in this._database.Users
                        on fr.IdUser2 equals us.Id
                        where fr.IdUser1 == userId
                        select us;

                foreach (Users item in query.ToList<Users>())
                {
                    item.Password = "";
                    if (item.Id != userId)
                        colToRet.Add(item);
                }

                return JsonConvert.SerializeObject(colToRet.Distinct());
            }
            catch
            {
                return "ERROR";
            }
        }

        //Odebrani pritele z pratel
        [System.Web.Http.Route("api/friends/remove/{token}-{userId}-{userToRemove}")]
        [System.Web.Http.HttpGet]
        public string RemoveFriend(string token, int userId, int userToRemove)
        {
            string toRet = "OK";

            Token t = Token.Exists(token);
            if (t == null || !t.IsUser)
            {
                return "TokenERROR";
            }

            Friendships fr = this._database.Friendships.Where(f => (f.IdUser1 == userId && f.IdUser2 == userToRemove) || (f.IdUser1 == userToRemove && f.IdUser2 == userId)).FirstOrDefault();

            if (fr == null)
                return "FriendshipDoesntExists";
            else
            {
                this._database.Friendships.Remove(fr);
                this._database.SaveChanges();
                return "OK";
            }
        }
    }
}