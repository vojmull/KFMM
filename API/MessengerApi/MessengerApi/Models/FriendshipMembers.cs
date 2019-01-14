using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace MessengerApi.Models
{
    public class FriendshipMembers
    {
        [Key]
        public int Id { get; set; }
        public int IdUser { get; set; }
        public int IdFriendship { get; set; }
    }
}