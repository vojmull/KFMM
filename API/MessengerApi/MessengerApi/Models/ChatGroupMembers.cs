using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace MessengerApi.Models
{
    public class ChatGroupMemebers
    {
        public class ChatGroupMembers
        {
            [Key]
            public int Id { get; set; }
            public int IdUser { get; set; }
            public int IdGroup { get; set; }
            public string Nickname { get; set; }
            public string Permission { get; set; }
            public DateTime TimeAdded { get; set; }
        }
    }
}