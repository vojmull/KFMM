using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Web;

namespace MessengerApi.Models
{
    public class Conversations
    {
        [Key]
        public int Id { get; set; }
        public string Nickname { get; set; }
        public string TimeCreated { get; set; }

        [NotMapped]
        public string LastMessage { get; set; }
        [NotMapped]
        public string LastMessageSent { get; set; }
        [NotMapped]
        public string ChatName { get; set; }
        [NotMapped]
        public string ImageServerPath { get; set; }
    }
}