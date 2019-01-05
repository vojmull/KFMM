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
        public int IdUser1 { get; set; }
        public int IdUser2 { get; set; }

        [NotMapped]
        public string LastMessage { get; set; }
        [NotMapped]
        public string LastMessageSent { get; set; }
        [NotMapped]
        public string Name { get; set; }
        [NotMapped]
        public string Surname { get; set; }
        [NotMapped]
        public string ImageServerPath { get; set; }
    }
}