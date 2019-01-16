using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Web;

namespace MessengerApi.Utilities
{
    public class CheckOnlineUtility
    {
        private dbContext _database = new dbContext();
        //private Timer timer = new Timer()

        public async void ConfirmOnlineStatus(int userId)
        {
            Users us = _database.Users.Where(u => userId == u.Id).First();
            us.IsOnline = true;
            us.TimeLastOnline = DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss");
            _database.SaveChanges();
        }


    }
}