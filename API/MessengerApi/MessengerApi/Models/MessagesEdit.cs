using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace MessengerApi
{
    public class MessagesEdit
    {
        [Key]
        public int Id { get; set; }
        public int IdMessage { get; set; }
        public string OldContent { get; set; }
        public string NewContent { get; set; }
        public int IdChangedBy { get; set; }
        public DateTime TimeChanged { get; set; }
    }
}