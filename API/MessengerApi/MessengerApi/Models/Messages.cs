using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Web;

namespace MessengerApi
{
    public class Messages
    {
        [Key]
        public int Id { get; set; }
        //public int IdFriendship { get; set; }
        public int IdAuthor { get; set; }
        public int IdConversation { get; set; }
        public string Content { get; set; }
        public bool Sent { get; set; }
        public bool Delievered { get; set; }
        public bool Seen { get; set; }
        public string TimeSent { get; set; }
        public string TimeDelievered { get; set; }
        public string TimeSeen { get; set; }
        public bool Edited { get; set; }

        [NotMapped]
        public string AuthorName { get; set; }
    }
}