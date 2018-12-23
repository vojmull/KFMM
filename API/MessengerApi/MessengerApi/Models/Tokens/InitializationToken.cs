using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace MessengerApi.Models.Tokens
{
    public class InicializationToken
    {
        public string Token { get; set; }
        public int Id { get; set; }
        public DateTime Expiration { get; set; }
    }
}