using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace MessengerApi
{
    public class Friendships
    {
        public int Id { get; set; }
        public int IdUser1 { get; set; }
        public int IdUser2 { get; set; }
        public DateTime TimeCreated { get; set; }
    }
}