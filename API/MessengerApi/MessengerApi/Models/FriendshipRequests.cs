using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace MessengerApi.Models
{
    public class FriendshipRequests
    {
        public int Id { get; set; }
        public int IdUserRequestor { get; set; }
        public int IdUser2 { get; set; }
        public DateTime TimeSent { get; set; }
        public bool Accepted { get; set; }
        public DateTime RequestExpiration { get; set; }
    }
}