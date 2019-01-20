using System;
using System.Collections.Generic;
using System.Linq;
using System.Timers;
using System.Web;

namespace MessengerApi.Utilities
{
    public static class CheckOnlineUtility
    {
        private static dbContext _database = new dbContext();
        private static Timer timer;

        public static async void ConfirmOnlineStatus(int userId)
        {
            if (timer == null)
            {
                timer = new Timer();
                timer.Interval = 1000 * 60; //1min * sec
                timer.Enabled = true;
                timer.Elapsed += new ElapsedEventHandler(CheckDatabase);
            }

            Users us = _database.Users.Where(u => userId == u.Id).First();
            us.IsOnline = true;
            us.TimeLastOnline = DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss");
            _database.SaveChanges();
        }

        private static void CheckDatabase(object source, ElapsedEventArgs e)
        {
            foreach (Users item in _database.Users.Where(u => u.IsOnline).ToList())
            {
                DateTime lastOnline = Convert.ToDateTime(item.TimeLastOnline);
                if (lastOnline.AddSeconds(60) < DateTime.Now)
                {
                    item.IsOnline = false;
                }
            }
            _database.SaveChanges();
        }
    }
}