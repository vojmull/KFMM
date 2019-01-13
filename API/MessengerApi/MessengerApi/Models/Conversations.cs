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
        public string LastMessageSentAt { get; set; }
        [NotMapped]
        public bool LastMessageSent { get; set; }
        [NotMapped]
        public int IdLastMessageAuthor { get; set; }
        [NotMapped]
        public string ChatName { get; set; }
        [NotMapped]
        public string Name { get; set; }
        [NotMapped]
        public string Surname { get; set; }
        [NotMapped]
        public string ImageServerPath { get; set; }
        [NotMapped]
        public bool LastMessageRead { get; set; }
    }
}