﻿using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace MessengerApi
{
    public class Friendships
    {
        [Key]
        public int Id { get; set; }
        public string TimeCreated { get; set; }
    }
}