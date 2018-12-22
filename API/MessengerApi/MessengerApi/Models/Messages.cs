using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace MessengerApi
{
    public class Messages
    {
        public int Id { get; set; }
        public int IdFriendship { get; set; }
        public int IdAuthor { get; set; }
        public string Content { get; set; }
        public bool Delievered { get; set; }
        public bool Seen { get; set; }
        public DateTime TimeSent { get; set; }
        public DateTime TimeDelievered { get; set; }
        public DateTime TimeSeen { get; set; }
        public bool Edited { get; set; }
    }
}