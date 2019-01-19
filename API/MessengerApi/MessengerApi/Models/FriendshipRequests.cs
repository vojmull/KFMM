using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Web;

namespace MessengerApi.Models
{
    public class FriendshipRequests
    {
        [Key]
        public int Id { get; set; }
        public int IdUserRequestor { get; set; }
        public int IdUser2 { get; set; }
        public string TimeSent { get; set; }
        public bool Accepted { get; set; }
        public string RequestExpiration { get; set; }

        [NotMapped]
        public string RequestorName { get; set; }
    }
}